package com.google.gwt.sample.stockwatcher.modules.stockreader.client.widgets;

import com.google.gwt.sample.stockwatcher.modules.stockreader.client.handlers.RemoteSubmitButtonHandler;
import com.sencha.gxt.widget.core.client.button.TextButton;

/**
 * Submit button to send to a remote server.
 */
public class RemoteSubmitButton extends TextButton {
    public RemoteSubmitButton(CurrenciesTextField currenciesTextField) {
        this.setText("Submit Remote Request");
        this.addSelectHandler(new RemoteSubmitButtonHandler(currenciesTextField));
    }
}
