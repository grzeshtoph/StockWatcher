package com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.callbacks;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.widgets.StockListPanel;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.StockPriceService;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.StockPriceServiceAsync;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.exceptions.DelistedException;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.exceptions.DuplicatedSymbolException;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.model.StockPrice;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Async callback for adding a new stock.
 */
public class AddStockAsyncCallback implements AsyncCallback<StockPrice> {
    private StockListPanel stockListPanel;

    /**
     * Creates async callback with the required panel.
     *
     * @param stockListPanel the required panel
     */
    public AddStockAsyncCallback(StockListPanel stockListPanel) {
        this.stockListPanel = stockListPanel;
    }

    @Override
    public void onFailure(Throwable caught) {
        String errorMessage;

        if (caught instanceof DelistedException) {
            errorMessage = ((DelistedException) caught).getSymbol()
                    + " has been delisted and shouldn't be used anymore.";
        } else if (caught instanceof DuplicatedSymbolException) {
            errorMessage = ((DuplicatedSymbolException) caught).getSymbol()
                    + " is already on the list. Cannot be added more, than once.";
        } else {
            errorMessage = caught.getMessage();
        }

        stockListPanel.setErrorMessage(errorMessage);
    }

    @Override
    public void onSuccess(final StockPrice stockPrice) {
        stockListPanel.addNewRowToStocksFlexTable(stockPrice);
        stockListPanel.updateRowWithNewStockPrice(stockPrice);
        stockListPanel.setInfoMessage("Currency " + stockPrice.getSymbol() + " added successfully.");
        stockListPanel.getNewSymbolTextBox().setText("");
    }
}
