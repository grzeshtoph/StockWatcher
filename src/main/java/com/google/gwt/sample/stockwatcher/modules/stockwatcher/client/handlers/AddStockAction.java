package com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.handlers;

import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.callbacks.AddStockAsyncCallback;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.widgets.StockListPanel;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.StockPriceService;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.StockPriceServiceAsync;
import com.google.gwt.user.client.Window;

/**
 * Add stock action, accessible only for the add stock event handlers.
 */
public class AddStockAction {
    private final StockPriceServiceAsync stockPriceService = StockPriceService.App.getInstance();
    private final StockListPanel stockListPanel;

    AddStockAction(StockListPanel stockListPanel) {
        this.stockListPanel = stockListPanel;
    }

    void execute() {
        final String symbol = stockListPanel.getNewSymbolTextBox().getText().toUpperCase().trim();

        // Stock code must be between 1 and 10 chars that are numbers, letters, or dots.
        if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
            Window.alert("'" + symbol + "' is not a valid symbol.");
            stockListPanel.getNewSymbolTextBox().selectAll();
            return;
        }

        stockPriceService.addSymbol(symbol, new AddStockAsyncCallback(stockListPanel));
    }
}
