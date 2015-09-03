package pitfalls;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.*;

/**
 *  [JP, 2.2]
 */
public class FloatMoney {

    @Test
    public void testFloat() {
        Assert.assertThat(2.00 - 1.10, equalTo(0.90));
        // ERROR: Expected: <0.9> but: was <0.8999999999999999>
    }


    /*
        "1.1" can't be represented exactly as a double, so it is represented by the closest double value.
        More generally, the problem is that not all decimals can be represented exactly using binary floating-point.
     */


    @Test
    public void firstFixTry() {
        Assert.assertThat(String.format("%.2f", 2.00 - 1.10), is("0.90"));
        // It worked, but it's poor solution - still uses binary floating-point.
        // Calculation error could actually accumulated during many floating-point arithmetic operations.

        // Using float instead of double for simpler demonstration of accumulating errors.
        float sum = 0;
        float x = 1 / 10.0F;
        for (int i = 0; i < 10000; i++) {
            sum += x;
        }

        Assert.assertThat(String.format("%.2f", sum), is("1000.00"));
        // ERROR: Expected: is "1000.00" but: was "999.90"
    }

    /*
        Conclusions:
        1) Binary floating-point is particularly ill-suited to monetary calculations
        2) We need some way to check two double values for equality
     */


    @Test
    public void correctWayOfRepresentingMoney() {
        // First way – use integer/long types for storing money number of smallest non-dividable parts (e.g. cents)
        Assert.assertThat(200 - 110, equalTo(90));

        // Second way – use exact decimal arithmetic types, for example BigDecimal in java.
        Assert.assertThat(new BigDecimal("2.00").subtract(new BigDecimal("1.10")), equalTo(new BigDecimal("0.90")));

    }


    @Test
    public void correctWayOfComparingFloatNumbers() {
        // Just compare difference between comparing numbers and compare it against some small value (epsilon)
        Assert.assertTrue(Math.abs((2.00 - 1.10) - (0.90)) < 1E-8);

        // Testing libraries provides default way of testing floating numbers
        Assert.assertEquals(2.00 - 1.10, 0.90, 1E-8);
    }


    /*
        HARDCORE WAY OF DEALING WITH ROUNDING ERRORS:

        https://en.wikipedia.org/wiki/Interval_arithmetic

        Applicable in fields where you are doing very large amount
        of floating number calculations and need very accurate result
     */


}
