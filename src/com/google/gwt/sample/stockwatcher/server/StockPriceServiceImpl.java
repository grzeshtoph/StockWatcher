package com.google.gwt.sample.stockwatcher.server;

import com.google.gwt.sample.stockwatcher.client.exceptions.ApplicationException;
import com.google.gwt.sample.stockwatcher.client.exceptions.DelistedException;
import com.google.gwt.sample.stockwatcher.client.StockPrice;
import com.google.gwt.sample.stockwatcher.client.exceptions.DuplicatedSymbolException;
import com.google.gwt.sample.stockwatcher.client.exceptions.NotFoundSymbolException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.sample.stockwatcher.client.StockPriceService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * The sole implementation of {@link StockPriceService}.
 */
public class StockPriceServiceImpl extends RemoteServiceServlet implements StockPriceService {
    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_UP;
    private static final BigDecimal MAX_PRICE = new BigDecimal("100.00"); // $100.00
    private static final BigDecimal MAX_PRICE_CHANGE = new BigDecimal("0.02"); // +/- 2%
    private final Set<String> delistedSymbols = new HashSet<String>();

    {
        delistedSymbols.add("ERR");
        delistedSymbols.add("RBL");
        delistedSymbols.add("POS");
        delistedSymbols.add("BAL");
    }

    @Override
    public StockPrice[] getUpdatedStockPrices() {
        List<StockPrice> prices = getPricesFromStorage();
        Random random = new Random();

        int index = 0;
        for (StockPrice price : prices) {
            updateStockPrice(price, index++, random);
        }

        return prices.toArray(new StockPrice[prices.size()]);
    }

    @Override
    public StockPrice addSymbol(String symbol) throws ApplicationException {
        if (delistedSymbols.contains(symbol)) {
            throw new DelistedException(symbol);
        }

        List<StockPrice> prices = getPricesFromStorage();
        for (StockPrice price : prices) {
            if (price.getSymbol().equals(symbol)) {
                throw new DuplicatedSymbolException(symbol);
            }
        }

        StockPrice stockPrice = updateStockPrice(new StockPrice(symbol), prices.size(), new Random());
        prices.add(stockPrice);

        return stockPrice;
    }

    @Override
    public StockPrice removeSymbol(String symbol) throws NotFoundSymbolException {
        List<StockPrice> prices = getPricesFromStorage();
        StockPrice priceToRemove = new StockPrice(symbol);
        if (prices.contains(priceToRemove)) {
            int index = prices.indexOf(priceToRemove);
            prices.remove(index);
            priceToRemove.setIndex(index);
            return priceToRemove;
        }

        throw new NotFoundSymbolException(symbol);
    }

    private StockPrice updateStockPrice(StockPrice stockPrice, int index, Random random) {
        BigDecimal randomNum = new BigDecimal(random.nextDouble()).setScale(4, DEFAULT_ROUNDING);
        BigDecimal price = MAX_PRICE.multiply(randomNum).setScale(2, DEFAULT_ROUNDING);
        randomNum = new BigDecimal(random.nextDouble() * 2.0 - 1.0).setScale(4, DEFAULT_ROUNDING);
        BigDecimal change = price.multiply(MAX_PRICE_CHANGE).multiply(randomNum).setScale(2, DEFAULT_ROUNDING);

        stockPrice.setIndex(index);
        stockPrice.setChange(change);
        stockPrice.setPrice(price);

        return stockPrice;
    }

    private List<StockPrice> getPricesFromStorage() {
        HttpSession session = this.getThreadLocalRequest().getSession(true);
        List<StockPrice> prices = (List<StockPrice>) session.getAttribute("prices");
        if (prices == null) {
            prices = new ArrayList<StockPrice>();
            session.setAttribute("prices", prices);
        }
        return prices;
    }
}