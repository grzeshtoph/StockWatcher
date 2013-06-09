package com.google.gwt.sample.stockwatcher.client.callbacks.stocklist;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.sample.stockwatcher.client.StockListBuilder;
import com.google.gwt.sample.stockwatcher.client.model.StockPrice;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Date;

/**
 * Callback for refresh action for stock list on the left side of the page.
 */
public class RefreshStockListAsyncCallback implements AsyncCallback<StockPrice[]> {
    static final DateTimeFormat DATE_TIME_FORMAT = DateTimeFormat.getFormat(
            DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
    StockListBuilder stockListBuilder;

    /**
     * Creates the callback with the required widgets builder.
     *
     * @param stockListBuilder required widgets builder
     */
    public RefreshStockListAsyncCallback(StockListBuilder stockListBuilder) {
        this.stockListBuilder = stockListBuilder;
    }

    @Override
    public void onFailure(Throwable caught) {
        stockListBuilder.setErrorMessage(caught.getMessage());
    }

    @Override
    public void onSuccess(StockPrice[] result) {
        populateNewStocks(result);

        stockListBuilder.setLastUpdatedLabelTest("Last update : " + DATE_TIME_FORMAT.format(new Date()));
    }

    void populateNewStocks(StockPrice[] result) {
        for (StockPrice stock : result) {
            stockListBuilder.updateRowWithNewStockPrice(stock);
        }
    }
}
