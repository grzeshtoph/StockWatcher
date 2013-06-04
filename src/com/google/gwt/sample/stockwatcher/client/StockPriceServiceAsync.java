package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gregs
 * Date: 03.06.13
 * Time: 14:04
 * To change this template use File | Settings | File Templates.
 */
public interface StockPriceServiceAsync {
    void getUpdatedStockPrices(AsyncCallback<List<StockPrice>> async);

    void addSymbol(String symbol, AsyncCallback<StockPrice> async);

    void removeSymbol(String symbol, AsyncCallback<StockPrice> async);
}
