package com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.exceptions;

import java.io.Serializable;

/**
 * Abstract application exception for various errors.
 */
public abstract class ApplicationException extends Exception implements Serializable {
    private String symbol;

    /**
     * Empty constructor. For serialization.
     */
    public ApplicationException() {
    }

    /**
     * The main constructor, with the currency symbol to provide.
     *
     * @param symbol currency symbol
     */
    public ApplicationException(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets the currency symbol from the exception.
     *
     * @return currency symbol
     */
    public String getSymbol() {
        return symbol;
    }
}
