package com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.callbacks;

import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.StockWatcherWidgetsBuilder;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.exceptions.NotFoundSymbolException;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.model.StockPrice;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Async callback for removing the existing stock.
 */
public class RemoveStockAsyncCallback implements AsyncCallback<StockPrice> {
    private StockWatcherWidgetsBuilder stockWatcherWidgetsBuilder;

    /**
     * Creates the callback with the required stock list builder.
     *
     * @param stockWatcherWidgetsBuilder the required stock list builder
     */
    public RemoveStockAsyncCallback(StockWatcherWidgetsBuilder stockWatcherWidgetsBuilder) {
        this.stockWatcherWidgetsBuilder = stockWatcherWidgetsBuilder;
    }

    @Override
    public void onFailure(Throwable caught) {
        String errorMessage;
        if (caught instanceof NotFoundSymbolException) {
            errorMessage = ((NotFoundSymbolException) caught).getSymbol() + " not found. Cannot be deleted.";
        } else {
            errorMessage = caught.getMessage();
        }

        stockWatcherWidgetsBuilder.setErrorMessage(errorMessage);
    }

    @Override
    public void onSuccess(StockPrice result) {
        stockWatcherWidgetsBuilder.removeRowFromStocksFlexTable(result.getIndex() + 1)
                .setInfoMessage("Currency " + result.getSymbol() + " removed successfully.");
    }
}
