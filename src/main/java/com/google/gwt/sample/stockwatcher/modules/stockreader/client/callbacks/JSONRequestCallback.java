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
        displayError("Couldn't retrieve JSON");
    }

    public void onResponseReceived(Request request, Response response) {
        if (200 == response.getStatusCode()) {
            AlertMessageBox msgBox =
                    new AlertMessageBox("Currencies", stockDataAsString(asArrayOfStockData(response.getText())));
            msgBox.setIcon(AlertMessageBox.ICONS.info());
            msgBox.show();
        } else {
            displayError("Couldn't retrieve JSON (" + response.getStatusText()
                    + ")");
        }
    }

    private void displayError(String message) {
        AlertMessageBox msgBox = new AlertMessageBox("Request Error", message);
        msgBox.show();
    }

    /**
     * Convert the string of JSON into JavaScript object.
     */
    private final native JsArray<StockData> asArrayOfStockData(String json) /*-{
        return eval(json);
    }-*/;

    private String stockDataAsString(JsArray<StockData> stockData) {
        StringBuilder sb = new StringBuilder();

        if (stockData.length() > 0) {
            for (int i = 0; i < stockData.length(); i++) {
                StockData item = stockData.get(i);
                sb.append(item.getSymbol()).append(": ")
                        .append(item.getPrice()).append(", ")
                        .append(item.getChange()).append(", ")
                        .append(item.getChangePercent()).append("<br />");
            }
        } else {
            sb.append("No currencies defined");
        }

        return sb.toString();
    }
}
