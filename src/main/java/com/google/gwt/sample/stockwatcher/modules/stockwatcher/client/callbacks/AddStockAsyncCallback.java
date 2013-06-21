package com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.callbacks;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.StockWatcherWidgetsBuilder;
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
    private final StockPriceServiceAsync stockPriceService = StockPriceService.App.getInstance();
    private StockWatcherWidgetsBuilder stockWatcherWidgetsBuilder;

    /**
     * Creates async callback with the required widgets builder.
     *
     * @param stockWatcherWidgetsBuilder the required widgets builder
     */
    public AddStockAsyncCallback(StockWatcherWidgetsBuilder stockWatcherWidgetsBuilder) {
        this.stockWatcherWidgetsBuilder = stockWatcherWidgetsBuilder;
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

        stockWatcherWidgetsBuilder.setErrorMessage(errorMessage);
    }

    @Override
    public void onSuccess(final StockPrice stockPrice) {
        stockWatcherWidgetsBuilder.addNewRowToStocksFlexTable(stockPrice, new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                stockPriceService.removeSymbol(stockPrice.getSymbol(), new RemoveStockAsyncCallback(stockWatcherWidgetsBuilder));
            }
        });

        stockWatcherWidgetsBuilder.updateRowWithNewStockPrice(stockPrice);

        stockWatcherWidgetsBuilder
                .setInfoMessage("Currency " + stockPrice.getSymbol() + " added successfully.")
                .getNewSymbolTextBox().setText("");
    }
}
