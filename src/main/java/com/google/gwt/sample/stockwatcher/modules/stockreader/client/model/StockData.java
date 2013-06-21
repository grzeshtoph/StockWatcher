package com.google.gwt.sample.stockwatcher.modules.stockreader.client.model;

import com.google.gwt.core.client.JavaScriptObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The overlay type for JSON data fetched from server.
 */
public class StockData extends JavaScriptObject {
    /**
     * Protected, zero-arg constructor is mandatory.
     */
    protected StockData() {
    }

    // JSNI methods to get stock data.
    public final native String getSymbol() /*-{
        return this.symbol;
    }-*/;

    public final native BigDecimal getPrice() /*-{
        return this.price;
    }-*/;

    public final native BigDecimal getChange() /*-{
        return this.change;
    }-*/;

    // Non-JSNI method to return change percentage.
    public final BigDecimal getChangePercent() {
        return new BigDecimal(getChange().doubleValue() / getPrice().doubleValue() * 100.00)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
