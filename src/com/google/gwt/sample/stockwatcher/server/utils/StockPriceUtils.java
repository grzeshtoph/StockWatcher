package com.google.gwt.sample.stockwatcher.server.utils;

import com.google.common.collect.Lists;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.model.StockPrice;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import static com.google.gwt.sample.stockwatcher.server.constants.Constants.DEFAULT_ROUNDING;
import static com.google.gwt.sample.stockwatcher.server.constants.Constants.MAX_PRICE;
import static com.google.gwt.sample.stockwatcher.server.constants.Constants.MAX_PRICE_CHANGE;

/**
 * Helper object to provide price operations on stock prices.
 */
public final class StockPriceUtils {
    private StockPriceUtils() {
    }

    public static StockPrice updateStockPrice(StockPrice stockPrice, int index, Random random) {
        BigDecimal randomNum = new BigDecimal(random.nextDouble()).setScale(4, DEFAULT_ROUNDING);
        BigDecimal price = MAX_PRICE.multiply(randomNum).setScale(2, DEFAULT_ROUNDING);
        randomNum = new BigDecimal(random.nextDouble() * 2.0 - 1.0).setScale(4, DEFAULT_ROUNDING);
        BigDecimal change = price.multiply(MAX_PRICE_CHANGE).multiply(randomNum).setScale(2, DEFAULT_ROUNDING);

        stockPrice.setIndex(index);
        stockPrice.setChange(change);
        stockPrice.setPrice(price);

        return stockPrice;
    }

    public static List<StockPrice> getPricesFromStorage(HttpSession session) {
        List<StockPrice> prices = (List<StockPrice>) session.getAttribute("prices");

        if (prices == null) {
            prices = Lists.newArrayList();
            session.setAttribute("prices", prices);
        }

        return prices;
    }
}
