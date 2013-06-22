package com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.handlers;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.widgets.StockListPanel;

/**
 * Key press handler for new symbol text box.
 */
public class NewSymbolTextBoxHandler implements KeyPressHandler {
    private AddStockAction addStockAction;

    public NewSymbolTextBoxHandler(StockListPanel stockListPanel) {
        addStockAction = new AddStockAction(stockListPanel);
    }

    @Override
    public void onKeyPress(KeyPressEvent event) {
        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
            addStockAction.execute();
        }
    }
}
