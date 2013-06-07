package com.google.gwt.sample.stockwatcher.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.gwt.sample.stockwatcher.client.StockPriceService;
import com.google.gwt.sample.stockwatcher.client.exceptions.ApplicationException;
import com.google.gwt.sample.stockwatcher.client.exceptions.DelistedException;
import com.google.gwt.sample.stockwatcher.client.exceptions.DuplicatedSymbolException;
import com.google.gwt.sample.stockwatcher.client.exceptions.NotFoundSymbolException;
import com.google.gwt.sample.stockwatcher.client.model.StockPrice;
import com.google.gwt.sample.stockwatcher.server.functions.UpdateStockPrice;
import com.google.gwt.sample.stockwatcher.server.predicates.StockPriceHasSymbol;
import com.google.gwt.sample.stockwatcher.server.utils.StockPriceUtils;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.List;
import java.util.Random;

import static com.google.gwt.sample.stockwatcher.server.constants.Constants.FORBIDDEN_SYMBOLS;

/**
 * The sole implementation of {@link StockPriceService}.
 */
public class StockPriceServiceImpl extends RemoteServiceServlet implements StockPriceService {
    @Override
    public StockPrice[] getUpdatedStockPrices() {
        List<StockPrice> prices = StockPriceUtils.getPricesFromStorage(this.getThreadLocalRequest().getSession(true));
        Random random = new Random();
        prices = ImmutableList.copyOf(Iterables.transform(prices, new UpdateStockPrice(random)));

        return prices.toArray(new StockPrice[prices.size()]);
    }

    @Override
    public StockPrice addSymbol(final String symbol) throws ApplicationException {
        if (FORBIDDEN_SYMBOLS.contains(symbol)) {
            throw new DelistedException(symbol);
        }

        List<StockPrice> prices = StockPriceUtils.getPricesFromStorage(this.getThreadLocalRequest().getSession(true));
        if (Iterables.any(prices, new StockPriceHasSymbol(symbol))) {
            throw new DuplicatedSymbolException(symbol);
        }

        Random random = new Random();
        StockPrice stockPrice = new UpdateStockPrice(random, prices.size()).apply(new StockPrice(symbol));
        prices.add(stockPrice);

        return stockPrice;
    }

    @Override
    public StockPrice removeSymbol(String symbol) throws NotFoundSymbolException {
        List<StockPrice> prices = StockPriceUtils.getPricesFromStorage(this.getThreadLocalRequest().getSession(true));

        if (!Iterables.any(prices, new StockPriceHasSymbol(symbol))) {
            throw new NotFoundSymbolException(symbol);
        }

        StockPrice priceToRemove = new StockPrice(symbol);
        int index = prices.indexOf(priceToRemove);
        prices.remove(index);
        priceToRemove.setIndex(index);
        return priceToRemove;
    }
}