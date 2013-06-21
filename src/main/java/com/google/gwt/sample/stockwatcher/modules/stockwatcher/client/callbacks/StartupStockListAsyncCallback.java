package com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.callbacks;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.StockWatcherWidgetsBuilder;
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
     * Creates the async callback with the required widgets builder.
     *
     * @param stockWatcherWidgetsBuilder the required widgets builder
     */
    public StartupStockListAsyncCallback(StockWatcherWidgetsBuilder stockWatcherWidgetsBuilder) {
        super(stockWatcherWidgetsBuilder);
    }

    @Override
    void populateNewStocks(StockPrice[] result) {
        for (final StockPrice stock : result) {
            stockWatcherWidgetsBuilder.addNewRowToStocksFlexTable(stock, new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    stockPriceService.removeSymbol(stock.getSymbol(), new RemoveStockAsyncCallback(stockWatcherWidgetsBuilder));
                }
            });

            stockWatcherWidgetsBuilder.updateRowWithNewStockPrice(stock);
        }

        // Setup timer to refresh list automatically.
        Timer refreshTimer = new Timer() {
            @Override
            public void run() {
                stockPriceService.getUpdatedStockPrices(new RefreshStockListAsyncCallback(stockWatcherWidgetsBuilder));
            }
        };
        refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
    }
}
