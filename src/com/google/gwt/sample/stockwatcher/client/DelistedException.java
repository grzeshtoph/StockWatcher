package com.google.gwt.sample.stockwatcher.client;

import java.io.Serializable;

/**
 * An exemplary exception to handle an error, when a currency has been delisted.
 */
public class DelistedException extends Exception implements Serializable {
    private String symbol;

    public DelistedException() {
    }

    public DelistedException(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
