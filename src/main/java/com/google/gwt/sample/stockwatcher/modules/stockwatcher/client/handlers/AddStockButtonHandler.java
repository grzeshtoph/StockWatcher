package com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.handlers;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.widgets.StockListPanel;

/**
 * Event handler for add stock button.
 */
public class AddStockButtonHandler implements ClickHandler {
    private AddStockAction addStockAction;

    public AddStockButtonHandler(StockListPanel stockListPanel) {
        addStockAction = new AddStockAction(stockListPanel);
    }

    @Override
    public void onClick(ClickEvent event) {
        addStockAction.execute();
    }
}
