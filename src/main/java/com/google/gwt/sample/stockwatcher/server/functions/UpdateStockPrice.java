package com.google.gwt.sample.stockwatcher.server.functions;

import com.google.common.base.Function;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.model.StockPrice;
import com.google.gwt.sample.stockwatcher.server.utils.StockPriceUtils;

import java.util.Random;

/**
 * Function that transforms the given instance of {@link StockPrice} with the updated trade values.
 */
public class UpdateStockPrice implements Function<StockPrice, StockPrice> {
    private final Random random;
    private int index = 0;

    /**
     * Optional constructor to create a function with custom index and external random.
     *
     * @param random random number generator
     * @param index  a custom index value
     */
    public UpdateStockPrice(Random random, int index) {
        this.random = random;
        this.index = index;
    }

    /**
     * Default constructor with the external random generator only.
     *
     * @param random random number generator
     */
    public UpdateStockPrice(Random random) {
        this.random = random;
    }

    /**
     * Updates the given stock price with new values and returns it.
     *
     * @param price stock price
     * @return stock price with updated values
     */
    @Override
    public StockPrice apply(StockPrice price) {
        StockPriceUtils.updateStockPrice(price, index++, random);
        return price;
    }
}
