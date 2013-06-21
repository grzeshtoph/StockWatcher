package com.google.gwt.sample.stockwatcher.modules.stockreader.client.widgets;

import com.google.gwt.sample.stockwatcher.modules.stockreader.client.handlers.CurrenciesTextFieldChangeHandler;
import com.sencha.gxt.widget.core.client.form.TextField;

/**
 * Currencies field.
 */
public class CurrenciesTextField extends TextField {
    CurrenciesTextField() {
        this.setEmptyText("Type in list of currencies...");
        this.setToolTip("Comma separated list of currencies");
        this.addValueChangeHandler(new CurrenciesTextFieldChangeHandler());
    }
}
