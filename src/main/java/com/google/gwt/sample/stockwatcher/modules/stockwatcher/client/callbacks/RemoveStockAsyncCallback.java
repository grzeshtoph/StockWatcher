package com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.callbacks;

import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.widgets.StockListPanel;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.exceptions.NotFoundSymbolException;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.model.StockPrice;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Async callback for removing the existing stock.
 */
public class RemoveStockAsyncCallback implements AsyncCallback<StockPrice> {
    private StockListPanel stockListPanel;

    /**
     * Creates the callback with the required stock list panel.
     *
     * @param stockListPanel the required stock list panel
     */
    public RemoveStockAsyncCallback(StockListPanel stockListPanel) {
        this.stockListPanel = stockListPanel;
    }

    @Override
    public void onFailure(Throwable caught) {
        String errorMessage;
        if (caught instanceof NotFoundSymbolException) {
            errorMessage = ((NotFoundSymbolException) caught).getSymbol() + " not found. Cannot be deleted.";
        } else {
            errorMessage = caught.getMessage();
        }

        stockListPanel.setErrorMessage(errorMessage);
    }

    @Override
    public void onSuccess(StockPrice result) {
        stockListPanel.removeRowFromStocksTable(result.getIndex() + 1);
        stockListPanel.setInfoMessage("Currency " + result.getSymbol() + " removed successfully.");
    }
}
