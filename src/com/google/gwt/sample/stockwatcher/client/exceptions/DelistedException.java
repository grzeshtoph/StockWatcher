package com.google.gwt.sample.stockwatcher.client.exceptions;

/**
 * An exemplary exception to handle an error, when a currency has been delisted.
 */
public class DelistedException extends ApplicationException {
    /**
     * Empty constructor. For serialization only.
     */
    public DelistedException() {
    }

    /**
     * Main constructor. With the currency symbol to provide.
     *
     * @param symbol currency symbol
     */
    public DelistedException(String symbol) {
        super(symbol);
    }
}
