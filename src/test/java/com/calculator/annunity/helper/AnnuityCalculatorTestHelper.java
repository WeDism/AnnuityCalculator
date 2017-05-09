package com.calculator.annunity.helper;

import org.apache.commons.math3.util.Precision;
import org.junit.Assert;

import java.math.BigDecimal;

/**
 * Created by WeDin on 30.04.2017.
 */
public class AnnuityCalculatorTestHelper {
    public static void assertEquals(String str, double expected, double actual) {
        expected = Precision.round(expected, 0, BigDecimal.ROUND_HALF_UP);
        actual = Precision.round(actual, 0, BigDecimal.ROUND_HALF_UP);
        Assert.assertEquals(String.format(str, expected, actual), actual, expected, 1.0);
    }

}
