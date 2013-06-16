package com.google.gwt.sample.stockwatcher.modules.stockreader.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * Stock reader module that uses GXT form to send HTTP request to the server and receive the reply
 * in form of a JSON response.
 */
public class StockReader implements EntryPoint {
    private TextField currenciesField = new TextField();
    private TextButton submitButton = new TextButton("Submit");

    public void onModuleLoad() {
        RootPanel.get("stockForm").add(asWidget());
    }

    private Widget asWidget() {
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

        return framedPanel;
    }
}
