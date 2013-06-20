package com.google.gwt.sample.stockwatcher.modules.stockreader.client.handlers;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.sencha.gxt.widget.core.client.info.Info;

/**
 * Value change handler for currencies text field.
 */
public class CurrenciesTextFieldChangeHandler implements ValueChangeHandler<String> {
    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String value = event.getValue();
        boolean valueEmpty = value == null || "".equals(value);
        Info.display("Search For Currencies", valueEmpty ? "Search for all symbols?"
                : "Search for symbols: " + value + "?");
    }
}
