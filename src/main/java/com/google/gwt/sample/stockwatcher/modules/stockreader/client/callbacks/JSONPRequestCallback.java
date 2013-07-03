package com.google.gwt.sample.stockwatcher.modules.stockreader.client.callbacks;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.sample.stockwatcher.modules.stockreader.client.model.StockData;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Callback for processing remote JSONP requests.
 */
public class JSONPRequestCallback implements AsyncCallback<JsArray<StockData>> {
    @Override
    public void onFailure(Throwable caught) {
        CallbackUtils.displayError("Couldn't retrieve remote JSON");
    }

    @Override
    public void onSuccess(JsArray<StockData> result) {
        if (result == null) {
            CallbackUtils.displayError("Couldn't retrieve remote JSON");
            return;
        }

        CallbackUtils.handleResultData("Remote Currencies", result);
    }
}
