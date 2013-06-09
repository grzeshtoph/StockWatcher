package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.sample.stockwatcher.client.callbacks.stocklist.AddStockAsyncCallback;
import com.google.gwt.sample.stockwatcher.client.callbacks.stocklist.StartupStockListAsyncCallback;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class StockWatcher implements EntryPoint {
    private static final int REFRESH_INTERVAL = 5000;
    private final StockListBuilder stockListBuilder = new StockListBuilder();
    private final StockPriceServiceAsync stockPriceService = StockPriceService.App.getInstance();

    /**
     * Entry point method.
     */
    public void onModuleLoad() {
        setUpStockListPanel();
    }

    private void setUpStockListPanel() {
        // set up all UI widgets on the left side of page
        stockListBuilder.setUpErrorMessageLabel()
                .setUpInfoMessageLabel()
                .setUpStocksFlexTable()
                .setUpAddPanel()
                .setUpMainPanel()
                .setUpNewSymbolTextBox();

        getRootPanel().add(stockListBuilder.getMainPanel());

        // startup stock table
        stockPriceService.getUpdatedStockPrices(new StartupStockListAsyncCallback(stockListBuilder));

        // Listen for mouse events on the Add button.
        stockListBuilder.getAddStockButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addStock();
            }
        });

        // Listen for keyboard events in the input box.
        stockListBuilder.getNewSymbolTextBox().addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                    addStock();
                }
            }
        });
    }

    private RootPanel getRootPanel() {
        return RootPanel.get("stockList");
    }

    private void addStock() {
        final String symbol = stockListBuilder.setUpNewSymbolTextBox()
                .getNewSymbolTextBox().getText().toUpperCase().trim();

        // Stock code must be between 1 and 10 chars that are numbers, letters, or dots.
        if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
            Window.alert("'" + symbol + "' is not a valid symbol.");
            stockListBuilder.getNewSymbolTextBox().selectAll();
            return;
        }

        stockPriceService.addSymbol(symbol, new AddStockAsyncCallback(stockListBuilder));
    }
}