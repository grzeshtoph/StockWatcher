package com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.widgets;

import com.google.gwt.user.client.ui.FlexTable;

/**
 * Main table holding information about stocks.
 */
public class StocksTable extends FlexTable {
    StocksTable() {
        this.setText(0, 0, "Symbol");
        this.setText(0, 1, "Price");
        this.setText(0, 2, "Change");
        this.setText(0, 3, "Remove");

        this.getRowFormatter().addStyleName(0, "watchListHeader");
        this.addStyleName("watchList");
        this.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
        this.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
        this.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");
        this.setCellPadding(6);
    }
}
