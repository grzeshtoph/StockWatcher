package com.google.gwt.sample.stockwatcher.server;

import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gwt.sample.stockwatcher.client.model.StockPrice;
import com.google.gwt.sample.stockwatcher.server.functions.UpdateStockPrice;
import com.google.gwt.sample.stockwatcher.server.predicates.StockPriceHasSymbolFromSet;
import com.google.gwt.sample.stockwatcher.server.utils.StockPriceUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * HTTP-based servlet to retrieve data in JSON format.
 */
public class StockPriceJSONServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<StockPrice> prices = StockPriceUtils.getPricesFromStorage(req.getSession(true));
        Set<String> symbols = ImmutableSet.copyOf(Splitter.on(' ').omitEmptyStrings()
                .split(Objects.firstNonNull(req.getParameter("q"), "")));
        Random random = new Random();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Iterable<StockPrice> transformedPrices = symbols.isEmpty() ?
                Iterables.transform(prices, new UpdateStockPrice(random))
                : Iterables.transform(Iterables.filter(prices, new StockPriceHasSymbolFromSet(symbols)),
                new UpdateStockPrice(random));

        PrintWriter out = resp.getWriter();
        out.println(gson.toJson(ImmutableList.copyOf(transformedPrices)));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new UnsupportedOperationException("POST not suppoted for this servlet.");
    }
}
