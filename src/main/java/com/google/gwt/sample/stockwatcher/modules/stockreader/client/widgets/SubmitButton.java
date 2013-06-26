package com.google.gwt.sample.stockwatcher.modules.stockreader.client.widgets;

import com.google.gwt.sample.stockwatcher.modules.stockreader.client.handlers.SubmitButtonHandler;
import com.sencha.gxt.widget.core.client.button.TextButton;

/**
 * Submit button to send a request
 */
public class SubmitButton extends TextButton {
    SubmitButton(CurrenciesTextField currenciesTextField) {
        this.setText("Submit Local Request");
        this.addSelectHandler(new SubmitButtonHandler(currenciesTextField));
    }
}
