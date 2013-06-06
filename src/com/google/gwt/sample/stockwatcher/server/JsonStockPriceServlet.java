package com.google.gwt.sample.stockwatcher.server;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.google.gwt.sample.stockwatcher.client.model.StockPrice;
import com.google.gwt.sample.stockwatcher.server.utils.StockPriceUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * HTTP-based servlet to retrieve data in JSON format.
 */
public class JsonStockPriceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<StockPrice> prices = StockPriceUtils.getPricesFromStorage(req.getSession(true));
        Random random = new Random();

        Set<String> symbols = Sets.newHashSet(Splitter.on(' ').omitEmptyStrings().split(req.getParameter("q")));

        int index = 0;
        if (symbols.isEmpty()) {
            for (StockPrice price : prices) {
                StockPriceUtils.updateStockPrice(price, index++, random);
            }
        } else {

        }

        PrintWriter out = resp.getWriter();
        out.println('[');
        out.println(']');
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new UnsupportedOperationException("POST not suppoted for this servlet.");
    }
}
