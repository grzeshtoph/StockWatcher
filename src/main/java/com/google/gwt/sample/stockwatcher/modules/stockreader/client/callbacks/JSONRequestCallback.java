package com.google.gwt.sample.stockwatcher.modules.stockreader.client.callbacks;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.sample.stockwatcher.modules.stockreader.client.model.StockData;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;

/**
 * Callback for request for fetching JSON data from server.
 */
public class JSONRequestCallback implements RequestCallback {
    public void onError(Request request, Throwable exception) {
        CallbackUtils.displayError("Couldn't retrieve JSON");
    }

    public void onResponseReceived(Request request, Response response) {
        if (200 == response.getStatusCode()) {
            CallbackUtils.handleResultData("Currencies", asArrayOfStockData(response.getText()));
        } else {
            CallbackUtils.displayError("Couldn't retrieve JSON (" + response.getStatusText()
                    + ")");
        }
    }

    /**
     * Convert the string of JSON into JavaScript object.
     */
    private final native JsArray<StockData> asArrayOfStockData(String json) /*-{
        return eval(json);
    }-*/;
}
