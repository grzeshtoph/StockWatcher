package com.google.gwt.sample.stockwatcher.server;

import com.google.gwt.sample.stockwatcher.client.DelistedException;
import com.google.gwt.sample.stockwatcher.client.StockPrice;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.sample.stockwatcher.client.StockPriceService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: gregs
 * Date: 03.06.13
 * Time: 14:04
 * To change this template use File | Settings | File Templates.
 */
public class StockPriceServiceImpl extends RemoteServiceServlet implements StockPriceService {
    public static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_UP;
    private static final BigDecimal MAX_PRICE = new BigDecimal("100.00"); // $100.00
    private static final BigDecimal MAX_PRICE_CHANGE = new BigDecimal("0.02"); // +/- 2%
    private final Set<String> delistedSymbols = new HashSet<String>();

    {
        delistedSymbols.add("ERR");
        delistedSymbols.add("RBL");
        delistedSymbols.add("POS");
        delistedSymbols.add("BAL");
    }

    public StockPrice[] getPrices(String[] symbols) throws DelistedException {
        StockPrice[] prices = new StockPrice[symbols.length];
        Random random = new Random();

        for (int i = 0; i < symbols.length; i++) {
            if (delistedSymbols.contains(symbols[i])) {
                throw new DelistedException(symbols[i]);
            }

            BigDecimal randomNum = new BigDecimal(random.nextDouble()).setScale(4, DEFAULT_ROUNDING);
            BigDecimal price = MAX_PRICE.multiply(randomNum).setScale(2, DEFAULT_ROUNDING);
            randomNum = new BigDecimal(random.nextDouble() * 2.0 - 1.0).setScale(4, DEFAULT_ROUNDING);
            BigDecimal change = price.multiply(MAX_PRICE_CHANGE).multiply(randomNum).setScale(2, DEFAULT_ROUNDING);

            prices[i] = new StockPrice(symbols[i], price, change);
        }

        return prices;
    }
}