package com.google.gwt.sample.stockwatcher.client.callbacks.stocklist;

import com.google.gwt.sample.stockwatcher.client.StockListBuilder;
import com.google.gwt.sample.stockwatcher.client.exceptions.NotFoundSymbolException;
import com.google.gwt.sample.stockwatcher.client.model.StockPrice;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Async callback for removing the existing stock.
 */
public class RemoveStockAsyncCallback implements AsyncCallback<StockPrice> {
    private StockListBuilder stockListBuilder;

    /**
     * Creates the callback with the required stock list builder.
     *
     * @param stockListBuilder the required stock list builder
     */
    public RemoveStockAsyncCallback(StockListBuilder stockListBuilder) {
        this.stockListBuilder = stockListBuilder;
    }

    @Override
    public void onFailure(Throwable caught) {
        String errorMessage;
        if (caught instanceof NotFoundSymbolException) {
            errorMessage = ((NotFoundSymbolException) caught).getSymbol() + " not found. Cannot be deleted.";
        } else {
            errorMessage = caught.getMessage();
        }

        stockListBuilder.setErrorMessage(errorMessage);
    }

    @Override
    public void onSuccess(StockPrice result) {
        stockListBuilder.removeRowFromStocksFlexTable(result.getIndex() + 1)
                .setInfoMessage("Currency " + result.getSymbol() + " removed successfully.");
    }
}
