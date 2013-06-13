package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.sample.stockwatcher.client.callbacks.stocklist.AddStockAsyncCallback;
import com.google.gwt.sample.stockwatcher.client.callbacks.stocklist.StartupStockListAsyncCallback;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

public class StockWatcher implements EntryPoint {
    private final StockListBuilder stockListBuilder = new StockListBuilder();
    private final StockPriceServiceAsync stockPriceService = StockPriceService.App.getInstance();
    private TextField currenciesField = new TextField();
    private TextButton submitButton = new TextButton("Submit");

    /**
     * Entry point method.
     */
    public void onModuleLoad() {
        RootPanel.get("stockList").add(asWidget());
    }

    private Widget asWidget() {
        TabPanel tabPanel = new TabPanel();
        tabPanel.setWidth(450);
        tabPanel.add(createStockListPanel(), "Stock List");

        FramedPanel framedPanel = new FramedPanel();
        framedPanel.setHeadingText("Stock Currencies Reader");
        framedPanel.setWidth(350);
        framedPanel.setBodyStyle("background: none; padding: 5px;");

        currenciesField.setEmptyText("Type in list of currencies...");
        currenciesField.setToolTip("Comma separated list of currencies");
        currenciesField.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                String value = event.getValue();
                boolean valueEmpty = value == null || "".equals(value);
                Info.display("Search For Currencies", valueEmpty ? "Search for all symbols?"
                        : "Search for symbols: " + value + "?");
            }
        });
        VerticalLayoutContainer verticalLayout = new VerticalLayoutContainer();
        verticalLayout.add(new FieldLabel(currenciesField, "Currencies"), new VerticalLayoutData(1, -1));
        framedPanel.add(verticalLayout);

        framedPanel.addButton(submitButton);

        tabPanel.add(framedPanel, "Stock Reader");

        return tabPanel;
    }

    private Widget createStockListPanel() {
        // set up all UI widgets on the left side of page
        stockListBuilder.setUpErrorMessageLabel()
                .setUpInfoMessageLabel()
                .setUpStocksFlexTable()
                .setUpAddPanel()
                .setUpStockListPanel()
                .setUpNewSymbolTextBox();

        // startup stock table
        stockPriceService.getUpdatedStockPrices(new StartupStockListAsyncCallback(stockListBuilder));

        // Listen for mouse events on the Add button.
        stockListBuilder.getAddStockButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addStock();
            }
        });

        // Listen for keyboard events in the input box.
        stockListBuilder.getNewSymbolTextBox().addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                    addStock();
                }
            }
        });

        return stockListBuilder.getStockListPanel();
    }

    private void addStock() {
        final String symbol = stockListBuilder.setUpNewSymbolTextBox()
                .getNewSymbolTextBox().getText().toUpperCase().trim();

        // Stock code must be between 1 and 10 chars that are numbers, letters, or dots.
        if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
            Window.alert("'" + symbol + "' is not a valid symbol.");
            stockListBuilder.getNewSymbolTextBox().selectAll();
            return;
        }

        stockPriceService.addSymbol(symbol, new AddStockAsyncCallback(stockListBuilder));
    }
}