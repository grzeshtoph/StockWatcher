package com.google.gwt.sample.stockwatcher.modules.stockreader.client.callbacks;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.sample.stockwatcher.modules.stockreader.client.model.StockData;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;

/**
 * Common methods to handle response results.
 */
public final class CallbackUtils {
    public static void displayError(String message) {
        AlertMessageBox msgBox = new AlertMessageBox("Request Error", message);
        msgBox.show();
    }

    public static void handleResultData(String header, JsArray<StockData> stockData) {
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

        AlertMessageBox msgBox =
                new AlertMessageBox(header, sb.toString());
        msgBox.setIcon(AlertMessageBox.ICONS.info());
        msgBox.show();
    }
}
