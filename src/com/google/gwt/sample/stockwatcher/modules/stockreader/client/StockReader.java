package com.google.gwt.sample.stockwatcher.modules.stockreader.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.sample.stockwatcher.modules.stockreader.client.widgets.StockReaderPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Stock reader module that uses GXT form to send HTTP request to the server and receive the reply
 * in form of a JSON response.
 */
public class StockReader implements EntryPoint {
    public void onModuleLoad() {
        RootPanel.get("stockForm").add(new StockReaderPanel().build());
    }
}
