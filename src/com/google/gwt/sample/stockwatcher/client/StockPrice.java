package com.google.gwt.sample.stockwatcher.client;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Data object representing stock price.
 */
public class StockPrice implements Serializable {
    private int index;
    private String symbol;
    private BigDecimal price;
    private BigDecimal change;

    public StockPrice(int index, String symbol, BigDecimal price, BigDecimal change) {
        this.index = index;
        this.symbol = symbol;
        this.price = price;
        this.change = change;
    }

    public StockPrice(String symbol) {
        this.symbol = symbol;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getChangePercent() {
        if (this.price != null && this.change != null) {
            return new BigDecimal(this.change.doubleValue() / this.price.doubleValue() * 100.00).setScale(2, RoundingMode.HALF_UP);
        }
        return new BigDecimal(0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof StockPrice)) {
            return false;
        }
        if (this == obj) {
            return true;
        }

        StockPrice objCasted = (StockPrice) obj;

        return this.symbol != null && this.symbol.equals(objCasted.symbol);
    }

    @Override
    public int hashCode() {
        return this.symbol != null ? this.symbol.hashCode() : 1;
    }

    @Override
    public String toString() {
        return "[" + this.index + ", " + this.symbol + "]";
    }
}
