package com.google.gwt.sample.stockwatcher.server.constants;

import com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Static container for various constants required for server layer.
 */
public class Constants {
    public static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_UP;
    public static final BigDecimal MAX_PRICE = new BigDecimal("100.00"); // $100.00
    public static final BigDecimal MAX_PRICE_CHANGE = new BigDecimal("0.02"); // +/- 2%
    public static final ImmutableSet<String> FORBIDDEN_SYMBOLS = ImmutableSet.of("ERR", "RBL", "POS", "BAL");
}
