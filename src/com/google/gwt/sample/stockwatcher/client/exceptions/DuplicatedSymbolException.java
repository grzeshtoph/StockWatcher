package com.google.gwt.sample.stockwatcher.client.exceptions;

/**
 * Exception supporting errors, when attempting to add already listed currency.
 */
public class DuplicatedSymbolException extends ApplicationException {
    /**
     * Empty constructor. For serialization only.
     */
    public DuplicatedSymbolException() {
    }

    /**
     * Main constructor with the currency symbol to provide.
     *
     * @param symbol currency symbol
     */
    public DuplicatedSymbolException(String symbol) {
        super(symbol);
    }
}
