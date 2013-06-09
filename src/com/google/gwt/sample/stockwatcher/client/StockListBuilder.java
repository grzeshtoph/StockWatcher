package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.sample.stockwatcher.client.model.StockPrice;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * UI builder for {@link StockWatcher} class to extract Widgets management from StockWatcher class.
 */
public class StockListBuilder {
    private static final NumberFormat PRICE_FORMAT = NumberFormat.getFormat("#,##0.00");
    private static final NumberFormat PRICE_CHANGE_FORMAT = NumberFormat.getFormat("+#,##0.00;-#,##0.00");
    private final VerticalPanel mainPanel = new VerticalPanel();
    private final FlexTable stocksFlexTable = new FlexTable();
    private final HorizontalPanel addPanel = new HorizontalPanel();
    private final TextBox newSymbolTextBox = new TextBox();
    private final Button addStockButton = new Button("Add");
    private final Label lastUpdatedLabel = new Label();
    private final Label errorMessageLabel = new Label();
    private final Label infoMessageLabel = new Label();

    public StockListBuilder setUpErrorMessageLabel() {
        // Create error message placeholder
        errorMessageLabel.setStyleName("errorMessage");
        errorMessageLabel.setVisible(false);
        return this;
    }

    public StockListBuilder setUpInfoMessageLabel() {
        // Create info message placeholder
        infoMessageLabel.setStyleName("infoMessage");
        infoMessageLabel.setVisible(false);
        return this;
    }

    public StockListBuilder setUpStocksFlexTable() {
        // Create table for stock data.
        stocksFlexTable.setText(0, 0, "Symbol");
        stocksFlexTable.setText(0, 1, "Price");
        stocksFlexTable.setText(0, 2, "Change");
        stocksFlexTable.setText(0, 3, "Remove");

        // Add styles to elements in the stock list table
        stocksFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
        stocksFlexTable.addStyleName("watchList");
        stocksFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
        stocksFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
        stocksFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");
        stocksFlexTable.setCellPadding(6);
        return this;
    }

    public StockListBuilder setUpAddPanel() {
        // Assemble Add Stock panel.
        addPanel.add(newSymbolTextBox);
        addPanel.add(addStockButton);
        addPanel.addStyleName("addPanel");
        return this;
    }

    public StockListBuilder setUpMainPanel() {
        // Assemble Main panel.
        mainPanel.add(infoMessageLabel);
        mainPanel.add(errorMessageLabel);
        mainPanel.add(stocksFlexTable);
        mainPanel.add(addPanel);
        mainPanel.add(lastUpdatedLabel);
        return this;
    }

    public StockListBuilder setUpNewSymbolTextBox() {
        // Move cursor focus to the input box.
        newSymbolTextBox.setFocus(true);
        return this;
    }

    public StockListBuilder setErrorMessage(String errorMessage) {
        errorMessageLabel.setText(errorMessage);
        errorMessageLabel.setVisible(true);
        infoMessageLabel.setText("");
        infoMessageLabel.setVisible(false);
        return this;
    }

    public StockListBuilder setInfoMessage(String infoMessage) {
        errorMessageLabel.setText("");
        errorMessageLabel.setVisible(false);
        infoMessageLabel.setText(infoMessage);
        infoMessageLabel.setVisible(true);
        return this;
    }

    public StockListBuilder setLastUpdatedLabelTest(String text) {
        lastUpdatedLabel.setText(text);
        return this;
    }

    public StockListBuilder addNewRowToStocksFlexTable(StockPrice stock, ClickHandler removeButtonHandler) {
        // Add the stock to the table.
        int row = stock.getIndex() + 1;
        stocksFlexTable.setText(row, 0, stock.getSymbol());
        stocksFlexTable.setWidget(row, 2, new Label());
        stocksFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
        stocksFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
        stocksFlexTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");

        // Add a button to remove this stock from the table.
        Button removeButton = new Button("x");
        removeButton.addStyleDependentName("remove");
        removeButton.setTitle("Remove " + stock.getSymbol());
        removeButton.addClickHandler(removeButtonHandler);
        stocksFlexTable.setWidget(row, 3, removeButton);

        return this;
    }

    public StockListBuilder updateRowWithNewStockPrice(StockPrice stock) {
        int row = stock.getIndex() + 1;

        // Format the data in the Price and Change fields.
        String priceText = PRICE_FORMAT.format(stock.getPrice());
        String changeText = PRICE_CHANGE_FORMAT.format(stock.getChange());
        String changePercentText = PRICE_CHANGE_FORMAT.format(stock.getChangePercent());

        // Populate the fields with new data
        stocksFlexTable.setText(row, 1, priceText);
        Label changeWidget = (Label) stocksFlexTable.getWidget(row, 2);
        changeWidget.setText(changeText + " (" + changePercentText + "%)");

        // Change the color of text in the Change field based on its value.
        String changeStyleName = "noChange";
        if (stock.getChangePercent().signum() == -1) {
            changeStyleName = "negativeChange";
        } else if (stock.getChangePercent().signum() == 1) {
            changeStyleName = "positiveChange";
        }
        changeWidget.setStyleName(changeStyleName);
        return this;
    }

    public StockListBuilder removeRowFromStocksFlexTable(int index) {
        stocksFlexTable.removeRow(index);
        return this;
    }

    public Button getAddStockButton() {
        return this.addStockButton;
    }

    public TextBox getNewSymbolTextBox() {
        return this.newSymbolTextBox;
    }

    public VerticalPanel getMainPanel() {
        return this.mainPanel;
    }
}
