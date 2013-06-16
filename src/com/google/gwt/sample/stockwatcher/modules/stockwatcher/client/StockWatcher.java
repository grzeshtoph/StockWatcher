package com.google.gwt.sample.stockwatcher.modules.stockwatcher.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.callbacks.AddStockAsyncCallback;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.callbacks.StartupStockListAsyncCallback;
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
    private final StockWatcherWidgetsBuilder stockWatcherWidgetsBuilder = new StockWatcherWidgetsBuilder();
    private final StockPriceServiceAsync stockPriceService = StockPriceService.App.getInstance();

    /**
     * Entry point method.
     */
    public void onModuleLoad() {
        RootPanel.get("stockList").add(asWidget());
    }
//
//    private Widget asWidget() {
//        TabPanel tabPanel = new TabPanel();
//        tabPanel.setWidth(450);
//        tabPanel.add(asWidget(), "Stock List");
//
//        FramedPanel framedPanel = new FramedPanel();
//        framedPanel.setHeadingText("Stock Currencies Reader");
//        framedPanel.setWidth(350);
//        framedPanel.setBodyStyle("background: none; padding: 5px;");
//
//        currenciesField.setEmptyText("Type in list of currencies...");
//        currenciesField.setToolTip("Comma separated list of currencies");
//        currenciesField.addValueChangeHandler(new ValueChangeHandler<String>() {
//            @Override
//            public void onValueChange(ValueChangeEvent<String> event) {
//                String value = event.getValue();
//                boolean valueEmpty = value == null || "".equals(value);
//                Info.display("Search For Currencies", valueEmpty ? "Search for all symbols?"
//                        : "Search for symbols: " + value + "?");
//            }
//        });
//        VerticalLayoutContainer verticalLayout = new VerticalLayoutContainer();
//        verticalLayout.add(new FieldLabel(currenciesField, "Currencies"), new VerticalLayoutData(1, -1));
//        framedPanel.add(verticalLayout);
//
//        framedPanel.addButton(submitButton);
//
//        tabPanel.add(framedPanel, "Stock Reader");
//
//        return tabPanel;
//    }

    private Widget asWidget() {
        // set up all UI widgets on the left side of page
        stockWatcherWidgetsBuilder.setUpErrorMessageLabel()
                .setUpInfoMessageLabel()
                .setUpStocksFlexTable()
                .setUpAddPanel()
                .setUpStockListPanel()
                .setUpNewSymbolTextBox();

        // startup stock table
        stockPriceService.getUpdatedStockPrices(new StartupStockListAsyncCallback(stockWatcherWidgetsBuilder));

        // Listen for mouse events on the Add button.
        stockWatcherWidgetsBuilder.getAddStockButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addStock();
            }
        });

        // Listen for keyboard events in the input box.
        stockWatcherWidgetsBuilder.getNewSymbolTextBox().addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                    addStock();
                }
            }
        });

        return stockWatcherWidgetsBuilder.getStockListPanel();
    }

    private void addStock() {
        final String symbol = stockWatcherWidgetsBuilder.setUpNewSymbolTextBox()
                .getNewSymbolTextBox().getText().toUpperCase().trim();

        // Stock code must be between 1 and 10 chars that are numbers, letters, or dots.
        if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
            Window.alert("'" + symbol + "' is not a valid symbol.");
            stockWatcherWidgetsBuilder.getNewSymbolTextBox().selectAll();
            return;
        }

        stockPriceService.addSymbol(symbol, new AddStockAsyncCallback(stockWatcherWidgetsBuilder));
    }
}