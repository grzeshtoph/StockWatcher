package com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.callbacks;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.StockWatcherWidgetsBuilder;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.model.StockPrice;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Date;

/**
 * Callback for refresh action for stock list on the left side of the page.
 */
public class RefreshStockListAsyncCallback implements AsyncCallback<StockPrice[]> {
    static final DateTimeFormat DATE_TIME_FORMAT = DateTimeFormat.getFormat(
            DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
    StockWatcherWidgetsBuilder stockWatcherWidgetsBuilder;

    /**
     * Creates the callback with the required widgets builder.
     *
     * @param stockWatcherWidgetsBuilder required widgets builder
     */
    public RefreshStockListAsyncCallback(StockWatcherWidgetsBuilder stockWatcherWidgetsBuilder) {
        this.stockWatcherWidgetsBuilder = stockWatcherWidgetsBuilder;
    }

    @Override
    public void onFailure(Throwable caught) {
        stockWatcherWidgetsBuilder.setErrorMessage(caught.getMessage());
    }

    @Override
    public void onSuccess(StockPrice[] result) {
        populateNewStocks(result);

        stockWatcherWidgetsBuilder.setLastUpdatedLabelTest("Last update : " + DATE_TIME_FORMAT.format(new Date()));
    }

    void populateNewStocks(StockPrice[] result) {
        for (StockPrice stock : result) {
            stockWatcherWidgetsBuilder.updateRowWithNewStockPrice(stock);
        }
    }
}
