package com.google.gwt.sample.stockwatcher.modules.stockreader.client.handlers;

import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.sample.stockwatcher.modules.stockreader.client.callbacks.JSONPRequestCallback;
import com.google.gwt.sample.stockwatcher.modules.stockreader.client.widgets.CurrenciesTextField;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/**
 * Remote submit button click.
 */
public class RemoteSubmitButtonHandler implements SelectEvent.SelectHandler {
    private static final String JSON_URL = "http://localhost:8082/stockPrices.php?q=";
    private int jsonRequestId;
    private CurrenciesTextField currenciesTextField;

    public RemoteSubmitButtonHandler(CurrenciesTextField currenciesTextField) {
        this.currenciesTextField = currenciesTextField;
    }

    @Override
    public void onSelect(SelectEvent event) {
        StringBuilder urlBuilder = new StringBuilder(JSON_URL);
        String currenciesTextFieldValue = currenciesTextField.getValue();
        if (currenciesTextFieldValue != null && !currenciesTextFieldValue.isEmpty()) {
            urlBuilder.append(currenciesTextFieldValue);
            urlBuilder.append("&callback=callback").append(jsonRequestId++);

            String url = URL.encode(urlBuilder.toString());

            // Send request to server and catch any errors.
            JsonpRequestBuilder builder = new JsonpRequestBuilder();
            builder.requestObject(url, new JSONPRequestCallback());
        } else {
            AlertMessageBox msgBox = new AlertMessageBox("Input Error", "Input currencies are required");
            msgBox.show();
        }
    }
}
