package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.sample.stockwatcher.model.StockPrice;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

/**
 * Created with IntelliJ IDEA.
 * User: gregs
 * Date: 03.06.13
 * Time: 14:04
 * To change this template use File | Settings | File Templates.
 */
@RemoteServiceRelativePath("StockPriceService")
public interface StockPriceService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use StockPriceService.App.getInstance() to access static instance of StockPriceServiceAsync
     */
    public static class App {
        private static final StockPriceServiceAsync INSTANCE = (StockPriceServiceAsync) GWT.create(StockPriceService.class);

        public static StockPriceServiceAsync getInstance() {
            return INSTANCE;
        }
    }

    StockPrice[] getPrices(String[] symbols) throws DelistedException;
}
