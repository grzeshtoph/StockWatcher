package com.google.gwt.sample.stockwatcher.modules.stockreader.client.handlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;
import com.google.gwt.sample.stockwatcher.modules.stockreader.client.callbacks.JSONRequestCallback;
import com.google.gwt.sample.stockwatcher.modules.stockreader.client.widgets.CurrenciesTextField;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/**
 * Submit button click handler.
 */
public class SubmitButtonHandler implements SelectEvent.SelectHandler {
    private static final String JSON_URL = GWT.getHostPageBaseURL() + "stockPricesJSON";
    private CurrenciesTextField currenciesTextField;

    public SubmitButtonHandler(CurrenciesTextField currenciesTextField) {
        this.currenciesTextField = currenciesTextField;
    }

    @Override
    public void onSelect(SelectEvent event) {
        StringBuilder urlBuilder = new StringBuilder(JSON_URL);
        String currenciesTextFieldValue = currenciesTextField.getValue();
        if (currenciesTextFieldValue != null && !currenciesTextFieldValue.isEmpty()) {
            urlBuilder.append("?q=").append(currenciesTextFieldValue);
        }

        String url = URL.encode(urlBuilder.toString());

        // Send request to server and catch any errors.
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

        try {
            builder.sendRequest(null, new JSONRequestCallback());
        } catch (RequestException e) {
            AlertMessageBox msgBox = new AlertMessageBox("Request Error", e.getMessage());
            msgBox.show();
        }
    }
}
