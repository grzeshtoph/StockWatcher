package com.google.gwt.sample.stockwatcher.client.callbacks.stocklist;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.sample.stockwatcher.client.StockListBuilder;
import com.google.gwt.sample.stockwatcher.client.StockPriceService;
import com.google.gwt.sample.stockwatcher.client.StockPriceServiceAsync;
import com.google.gwt.sample.stockwatcher.client.exceptions.DelistedException;
import com.google.gwt.sample.stockwatcher.client.exceptions.DuplicatedSymbolException;
import com.google.gwt.sample.stockwatcher.client.model.StockPrice;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Async callback for adding a new stock.
 */
public class AddStockAsyncCallback implements AsyncCallback<StockPrice> {
    private final StockPriceServiceAsync stockPriceService = StockPriceService.App.getInstance();
    private StockListBuilder stockListBuilder;

    /**
     * Creates async callback with the required widgets builder.
     *
     * @param stockListBuilder the required widgets builder
     */
    public AddStockAsyncCallback(StockListBuilder stockListBuilder) {
        this.stockListBuilder = stockListBuilder;
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

        stockListBuilder.setErrorMessage(errorMessage);
    }

    @Override
    public void onSuccess(final StockPrice stockPrice) {
        stockListBuilder.addNewRowToStocksFlexTable(stockPrice, new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                stockPriceService.removeSymbol(stockPrice.getSymbol(), new RemoveStockAsyncCallback(stockListBuilder));
            }
        });

        stockListBuilder.updateRowWithNewStockPrice(stockPrice);

        stockListBuilder
                .setInfoMessage("Currency " + stockPrice.getSymbol() + " added successfully.")
                .getNewSymbolTextBox().setText("");
    }
}
