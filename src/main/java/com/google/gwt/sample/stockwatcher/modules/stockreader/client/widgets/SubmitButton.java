package com.google.gwt.sample.stockwatcher.modules.stockreader.client.widgets;

import com.google.gwt.sample.stockwatcher.modules.stockreader.client.handlers.SubmitButtonClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Submit button to send a request
 */
public class SubmitButton extends Button {
    SubmitButton(CurrenciesTextField currenciesTextField) {
        this.setText("Submit");
        this.addClickHandler(new SubmitButtonClickHandler(currenciesTextField));
    }
}
