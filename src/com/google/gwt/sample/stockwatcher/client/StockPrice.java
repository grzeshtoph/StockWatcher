package com.google.gwt.sample.stockwatcher.client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Data object representing stock price.
 */
public class StockPrice implements Serializable {
    private String symbol;
    private BigDecimal price;
    private BigDecimal change;

    public StockPrice(String symbol, BigDecimal price, BigDecimal change) {
        this.symbol = symbol;
        this.price = price;
        this.change = change;
    }

    public StockPrice() {
    }

    public String getSymbol() {
        return this.symbol;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public BigDecimal getChange() {
        return this.change;
    }

    public BigDecimal getChangePercent() {
        if (this.price != null && this.change != null) {
            return new BigDecimal(this.change.doubleValue() / this.price.doubleValue() * 100.00).setScale(2, RoundingMode.HALF_UP);
        }
        return new BigDecimal(0);
    }
}
