package com.google.gwt.sample.stockwatcher.modules.stockwatcher.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.callbacks.AddStockAsyncCallback;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.callbacks.StartupStockListAsyncCallback;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.widgets.StockListPanel;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.StockPriceService;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.StockPriceServiceAsync;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Main module of the app. It's being used to update the monitored currencies and fetch the value periodically
 * from the server.
 */
public class StockWatcher implements EntryPoint {
    private final StockPriceServiceAsync stockPriceService = StockPriceService.App.getInstance();

    /**
     * Entry point method.
     */
    public void onModuleLoad() {
        RootPanel.get("stockList").add(asWidget());
    }

    private Widget asWidget() {
        StockListPanel panel = new StockListPanel().build();

        // startup stock table
        stockPriceService.getUpdatedStockPrices(new StartupStockListAsyncCallback(panel));

        return panel;
    }
}