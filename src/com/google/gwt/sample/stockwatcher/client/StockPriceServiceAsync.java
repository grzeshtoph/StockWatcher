package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.sample.stockwatcher.client.model.StockPrice;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Asynchronous version of interface required by GWT services.
 */
public interface StockPriceServiceAsync {
    void getUpdatedStockPrices(AsyncCallback<StockPrice[]> callback);

    void addSymbol(String symbol, AsyncCallback<StockPrice> callback);

    void removeSymbol(String symbol, AsyncCallback<StockPrice> callback);
}
