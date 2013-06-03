package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.sample.stockwatcher.model.StockPrice;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created with IntelliJ IDEA.
 * User: gregs
 * Date: 03.06.13
 * Time: 14:04
 * To change this template use File | Settings | File Templates.
 */
public interface StockPriceServiceAsync {
    void getPrices(String[] symbols, AsyncCallback<StockPrice[]> async);
}
