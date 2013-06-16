package com.google.gwt.sample.stockwatcher.server.predicates;

import com.google.common.base.Predicate;
import com.google.gwt.sample.stockwatcher.modules.stockwatcher.shared.model.StockPrice;

import java.util.Set;

/**
 * Predicate that check, if a stock price has symbol container in the set of symbols.
 */
public class StockPriceHasSymbolFromSet implements Predicate<StockPrice> {
    Set<String> symbols;

    /**
     * Creates the predicate with the required set of symbols.
     *
     * @param symbols set of symbols to match against stock price
     */
    public StockPriceHasSymbolFromSet(Set<String> symbols) {
        this.symbols = symbols;
    }

    /**
     * Checks, if the given stock price is contained in the set of symbols.
     *
     * @param price stock price to be checked
     * @return true, if stock price has symbol in the set, false otherwise
     */
    @Override
    public boolean apply(StockPrice price) {
        return symbols.contains(price.getSymbol());
    }
}
