package com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared;

import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.exceptions.ApplicationException;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.exceptions.NotFoundSymbolException;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.model.StockPrice;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

/**
 * The main interface for the stock price remote service.
 */
@RemoteServiceRelativePath("stockPrices")
public interface StockPriceService extends RemoteService {
    /**
     * Gets the stocks from storage, updates them with new prices and returns as a list.
     *
     * @return stocks with new prices
     */
    StockPrice[] getUpdatedStockPrices();

    /**
     * Adds a new symbol as a stock. Validates, if it already exists or is it on delisted list.
     *
     * @param symbol symbol to be added
     * @return added stock to the storage
     * @throws ApplicationException Might be thrown in case of validation errors
     */
    StockPrice addSymbol(String symbol) throws ApplicationException;

    /**
     * Removes the existing symbol from the list. Validates, if symbol exists in the storage.
     *
     * @param symbol symbol to be deleted
     * @return if deleted, return the stock with index number
     * @throws NotFoundSymbolException Might be thrown, if symbol is not on the list
     */
    StockPrice removeSymbol(String symbol) throws NotFoundSymbolException;

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
}
