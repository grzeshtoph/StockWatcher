package com.google.gwt.sample.stockwatcher.modules.stockreader.client.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.NumberFormat;

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

    public final native double getPrice() /*-{
        return this.price;
    }-*/;

    public final native double getChange() /*-{
        return this.change;
    }-*/;

    // Non-JSNI method to return change percentage.
    public final String getChangePercent() {
        return NumberFormat.getFormat("##0.00%").format(getChange() / getPrice());
    }
}
