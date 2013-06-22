package com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.callbacks;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.widgets.StockListPanel;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.model.StockPrice;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Date;

/**
 * Callback for refresh action for stock list on the left side of the page.
 */
public class RefreshStockListAsyncCallback implements AsyncCallback<StockPrice[]> {
    static final DateTimeFormat DATE_TIME_FORMAT = DateTimeFormat.getFormat(
            DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
    StockListPanel stockListPanel;

    /**
     * Creates the callback with the required panel.
     *
     * @param stockListPanel required stock list panel
     */
    public RefreshStockListAsyncCallback(StockListPanel stockListPanel) {
        this.stockListPanel = stockListPanel;
    }

    @Override
    public void onFailure(Throwable caught) {
        stockListPanel.setErrorMessage(caught.getMessage());
    }

    @Override
    public void onSuccess(StockPrice[] result) {
        populateNewStocks(result);

        stockListPanel.setLastUpdatedLabel("Last update : " + DATE_TIME_FORMAT.format(new Date()));
    }

    void populateNewStocks(StockPrice[] result) {
        for (StockPrice stock : result) {
            stockListPanel.updateRowWithNewStockPrice(stock);
        }
    }
}
