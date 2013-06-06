package com.google.gwt.sample.stockwatcher.server.predicates;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.gwt.sample.stockwatcher.client.model.StockPrice;

/**
 * Predicate that checks, if an instance of {@link StockPrice} has the give symbol.
 */
public class StockPriceHasSymbol implements Predicate<StockPrice> {
    private String symbol;

    /**
     * Creates new object with the target symbol.
     *
     * @param symbol target symbol
     */
    public StockPriceHasSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Checks, if the stock price has the given symbol.
     *
     * @param stockPrice stock price to be checked
     * @return true, if stock price has the symbol, false otherwise
     */
    @Override
    public boolean apply(StockPrice stockPrice) {
        return Objects.equal(symbol, stockPrice.getSymbol());
    }
}
