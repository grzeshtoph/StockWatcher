package com.google.gwt.sample.stockwatcher.client;

import com.google.common.base.Strings;
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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

public class StockWatcher implements EntryPoint {
    private final StockListBuilder stockListBuilder = new StockListBuilder();
    private final StockPriceServiceAsync stockPriceService = StockPriceService.App.getInstance();
    private HorizontalPanel mainPanel;
    private TextField currenciesField = new TextField();
    private FramedPanel framedPanel = new FramedPanel();
    private VerticalLayoutContainer verticalLayout = new VerticalLayoutContainer();

    /**
     * Entry point method.
     */
    public void onModuleLoad() {
        RootPanel.get("stockList").add(asWidget());
    }

    private Widget asWidget() {
        if (mainPanel == null) {
            mainPanel = new HorizontalPanel();

            RootPanel.get("stockList").add(mainPanel);

            setUpStockListPanel();

            FlowPanel interimPanel = new FlowPanel();
            interimPanel.setWidth("100px");
            mainPanel.add(interimPanel);

            framedPanel.setHeadingText("Stock Currencies Reader");
            framedPanel.setWidth(350);
            framedPanel.setBodyStyle("background: none; padding: 5px;");
            framedPanel.add(verticalLayout);

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
            verticalLayout.add(new FieldLabel(currenciesField, "Currencies"), new VerticalLayoutData(1, -1));

            mainPanel.add(framedPanel);
        }

        return mainPanel;
    }

    private void setUpStockListPanel() {
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

        mainPanel.add(stockListBuilder.getStockListPanel());
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