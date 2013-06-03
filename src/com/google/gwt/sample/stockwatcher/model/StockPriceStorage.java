package com.google.gwt.sample.stockwatcher.model;

/**
 * Singleton storage holder for stock prices.
 */
public class StockPriceStorage {
    /**
     * Gets singleton instance of {@link StockPriceStorage}.
     *
     * @return singleton instance of {@link StockPriceStorage}
     */
    public static StockPriceStorage getInstance() {
        return StockPriceStorageHolder.INSTANCE;
    }

    private static class StockPriceStorageHolder {
        private static final StockPriceStorage INSTANCE = new StockPriceStorage();
    }
}
