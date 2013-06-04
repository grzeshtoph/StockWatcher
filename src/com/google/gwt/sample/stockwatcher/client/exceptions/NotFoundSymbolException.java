package com.google.gwt.sample.stockwatcher.client.exceptions;

/**
 * Exception handling not found currency symbol, when attempting to delete
 */
public class NotFoundSymbolException extends ApplicationException {
    /**
     * Empty constructor. For serialization only.
     */
    public NotFoundSymbolException() {
    }

    /**
     * Main constructor, with the currency symbol to provide.
     *
     * @param symbol currency symbol
     */
    public NotFoundSymbolException(String symbol) {
        super(symbol);
    }
}
