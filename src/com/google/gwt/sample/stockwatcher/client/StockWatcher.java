package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.sample.stockwatcher.client.exceptions.DelistedException;
import com.google.gwt.sample.stockwatcher.client.exceptions.DuplicatedSymbolException;
import com.google.gwt.sample.stockwatcher.client.exceptions.NotFoundSymbolException;
import com.google.gwt.sample.stockwatcher.client.model.StockPrice;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Date;

public class StockWatcher implements EntryPoint {
    private static final int REFRESH_INTERVAL = 5000;
    private final StockWatcherWidgetsBuilder widgetsBuilder = new StockWatcherWidgetsBuilder();
    private final StockPriceServiceAsync stockPriceService = StockPriceService.App.getInstance();

    /**
     * Entry point method.
     */
    public void onModuleLoad() {
        // set up all UI widgets
        widgetsBuilder.setUpErrorMessageLabel()
                .setUpInfoMessageLabel()
                .setUpStocksFlexTable()
                .setUpAddPanel()
                .setUpMainPanel()
                .setUpRootPanel()
                .setUpNewSymbolTextBox();

        // startup stock table
        stockPriceService.getUpdatedStockPrices(new StartupStockTableAsyncCall());

        // Listen for mouse events on the Add button.
        widgetsBuilder.getAddStockButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addStock();
            }
        });

        // Listen for keyboard events in the input box.
        widgetsBuilder.getNewSymbolTextBox().addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                    addStock();
                }
            }
        });
    }

    private void addStock() {
        final String symbol = widgetsBuilder.setUpNewSymbolTextBox()
                .getNewSymbolTextBox().getText().toUpperCase().trim();

        // Stock code must be between 1 and 10 chars that are numbers, letters, or dots.
        if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
            Window.alert("'" + symbol + "' is not a valid symbol.");
            widgetsBuilder.getNewSymbolTextBox().selectAll();
            return;
        }

        stockPriceService.addSymbol(symbol, new AddStockAsyncCallback());
    }

    private void addStockToTheTable(final StockPrice stock) {
        widgetsBuilder.addNewRowToStocksFlexTable(stock, new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                stockPriceService.removeSymbol(stock.getSymbol(), new RemoveStockAsyncCallback());
            }
        });

        refreshStockWithNewPrice(stock);
    }

    private void refreshStockWithNewPrice(StockPrice stock) {
        widgetsBuilder.updateRowWithNewStockPrice(stock);
    }

    private void refreshWatchList() {
        stockPriceService.getUpdatedStockPrices(new RefreshStockAsyncCallback());
    }

    private void updateStockTable(StockPrice[] stocks, boolean addStocks) {
        if (addStocks) {
            for (StockPrice stock : stocks) {
                addStockToTheTable(stock);
            }
        } else {
            for (StockPrice stock : stocks) {
                refreshStockWithNewPrice(stock);
            }
        }

        widgetsBuilder.setLastUpdatedLabelTest("Last update : "
                + DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM).format(new Date()));
    }

    private class RefreshStockAsyncCallback implements AsyncCallback<StockPrice[]> {

        @Override
        public void onFailure(Throwable caught) {
            widgetsBuilder.setErrorMessage(caught.getMessage());
        }

        @Override
        public void onSuccess(StockPrice[] result) {
            updateStockTable(result, false);
        }
    }

    private class RemoveStockAsyncCallback implements AsyncCallback<StockPrice> {

        @Override
        public void onFailure(Throwable caught) {
            String errorMessage;
            if (caught instanceof NotFoundSymbolException) {
                errorMessage = ((NotFoundSymbolException) caught).getSymbol() + " not found. Cannot be deleted.";
            } else {
                errorMessage = caught.getMessage();
            }

            widgetsBuilder.setErrorMessage(errorMessage);
        }

        @Override
        public void onSuccess(StockPrice result) {
            widgetsBuilder.removeRowFromStocksFlexTable(result.getIndex() + 1)
                    .setInfoMessage("Currency " + result.getSymbol() + " removed successfully.");
        }
    }

    private class AddStockAsyncCallback implements AsyncCallback<StockPrice> {
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

            widgetsBuilder.setErrorMessage(errorMessage);
        }

        @Override
        public void onSuccess(final StockPrice stockPrice) {
            updateStockTable(new StockPrice[]{stockPrice}, true);

            widgetsBuilder
                    .setInfoMessage("Currency " + stockPrice.getSymbol() + " added successfully.")
                    .getNewSymbolTextBox().setText("");
        }
    }

    private class StartupStockTableAsyncCall implements AsyncCallback<StockPrice[]> {

        @Override
        public void onFailure(Throwable caught) {
            widgetsBuilder.setErrorMessage(caught.getMessage());
        }

        @Override
        public void onSuccess(StockPrice[] result) {
            updateStockTable(result, true);

            // Setup timer to refresh list automatically.
            Timer refreshTimer = new Timer() {
                @Override
                public void run() {
                    refreshWatchList();
                }
            };
            refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
        }
    }
}