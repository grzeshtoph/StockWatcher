package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

/**
 * Asynchronous version of interface required by GWT services.
 */
public interface StockPriceServiceAsync {
    void getUpdatedStockPrices(AsyncCallback<StockPrice[]> callback);

    void addSymbol(String symbol, AsyncCallback<StockPrice> callback);

    void removeSymbol(String symbol, AsyncCallback<StockPrice> callback);
}
