package com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.callbacks.RemoveStockAsyncCallback;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.client.handlers.AddStockButtonHandler;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.StockPriceService;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.StockPriceServiceAsync;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.model.StockPrice;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Main vertical panel to add/remove currencies.
 */
public class StockListPanel extends VerticalPanel {
    private static final NumberFormat PRICE_FORMAT = NumberFormat.getFormat("#,##0.00");
    private static final NumberFormat PRICE_CHANGE_FORMAT = NumberFormat.getFormat("+#,##0.00;-#,##0.00");
    private final StockPriceServiceAsync stockPriceService = StockPriceService.App.getInstance();
    private final MessageLabel errorMessageLabel = new ErrorMessageLabel();
    private final MessageLabel infoMessageLabel = new InfoMessageLabel();
    private final StocksTable stocksTable = new StocksTable();
    private final NewSymbolTextBox newSymbolTextBox = new NewSymbolTextBox();
    private final AddStockButton addStockButton = new AddStockButton();
    private final Label lastUpdatedLabel = new Label();

    public StockListPanel build() {
        this.add(errorMessageLabel);
        this.add(infoMessageLabel);
        this.add(stocksTable);
        HorizontalPanel addPanel = new HorizontalPanel();
        addPanel.addStyleName("addPanel");
        addPanel.add(newSymbolTextBox);
        addPanel.add(addStockButton);
        this.add(addPanel);
        this.add(lastUpdatedLabel);

        this.addStockButton.addClickHandler(new AddStockButtonHandler(this));

        return this;
    }

    /**
     * Sets error message.
     *
     * @param message the error message to be set
     */
    public void setErrorMessage(String message) {
        errorMessageLabel.setMessage(message);
        infoMessageLabel.hide();
    }

    /**
     * Sets info message.
     *
     * @param message the info message to be set
     */
    public void setInfoMessage(String message) {
        infoMessageLabel.setMessage(message);
        errorMessageLabel.hide();
    }

    /**
     * Removes row from stocks table.
     *
     * @param index row index to be removed
     */
    public void removeRowFromStocksTable(int index) {
        stocksTable.removeRow(index);
    }

    /**
     * Updates the row with new stock values.
     *
     * @param stock stock with new values
     */
    public void updateRowWithNewStockPrice(StockPrice stock) {
        int row = stock.getIndex() + 1;

        // Format the data in the Price and Change fields.
        String priceText = PRICE_FORMAT.format(stock.getPrice());
        String changeText = PRICE_CHANGE_FORMAT.format(stock.getChange());
        String changePercentText = PRICE_CHANGE_FORMAT.format(stock.getChangePercent());

        // Populate the fields with new data
        stocksTable.setText(row, 1, priceText);
        Label changeWidget = (Label) stocksTable.getWidget(row, 2);
        changeWidget.setText(changeText + " (" + changePercentText + "%)");

        // Change the color of text in the Change field based on its value.
        String changeStyleName = "noChange";
        if (stock.getChangePercent().signum() == -1) {
            changeStyleName = "negativeChange";
        } else if (stock.getChangePercent().signum() == 1) {
            changeStyleName = "positiveChange";
        }
        changeWidget.setStyleName(changeStyleName);
    }

    /**
     * Adds a new row to stock table.
     *
     * @param stock new row to stocks table
     */
    public void addNewRowToStocksFlexTable(final StockPrice stock) {
        // Add the stock to the table.
        int row = stock.getIndex() + 1;
        stocksTable.setText(row, 0, stock.getSymbol());
        stocksTable.setWidget(row, 2, new Label());
        stocksTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
        stocksTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
        stocksTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");

        // Add a button to remove this stock from the table.
        Button removeButton = new Button("x");
        removeButton.addStyleDependentName("remove");
        removeButton.setTitle("Remove " + stock.getSymbol());
        removeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                stockPriceService.removeSymbol(stock.getSymbol(), new RemoveStockAsyncCallback(StockListPanel.this));
            }
        });
        stocksTable.setWidget(row, 3, removeButton);
    }

    /**
     * Sets the last updated label's text.
     *
     * @param text last updated label's text
     */
    public void setLastUpdatedLabel(String text) {
        lastUpdatedLabel.setText(text);
    }

    public NewSymbolTextBox getNewSymbolTextBox() {
        return newSymbolTextBox;
    }
}
