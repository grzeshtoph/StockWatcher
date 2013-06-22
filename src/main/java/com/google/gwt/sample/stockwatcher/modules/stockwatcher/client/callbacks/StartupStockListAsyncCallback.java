package com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.callbacks;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.widgets.StockListPanel;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.StockPriceService;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.StockPriceServiceAsync;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.model.StockPrice;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Async callback to start-up the entire stock list on the left side on the page.
 */
public class StartupStockListAsyncCallback extends RefreshStockListAsyncCallback implements AsyncCallback<StockPrice[]> {
    private static final int REFRESH_INTERVAL = 5000;
    private final StockPriceServiceAsync stockPriceService = StockPriceService.App.getInstance();

    /**
     * Creates the async callback with the required panel.
     *
     * @param stockListPanel the stock list panel
     */
    public StartupStockListAsyncCallback(StockListPanel stockListPanel) {
        super(stockListPanel);
    }

    @Override
    void populateNewStocks(StockPrice[] result) {
        for (final StockPrice stock : result) {
            stockListPanel.addNewRowToStocksFlexTable(stock);
            stockListPanel.updateRowWithNewStockPrice(stock);
        }

        // Setup timer to refresh list automatically.
        Timer refreshTimer = new Timer() {
            @Override
            public void run() {
                stockPriceService.getUpdatedStockPrices(new RefreshStockListAsyncCallback(stockListPanel));
            }
        };
        refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
    }
}
