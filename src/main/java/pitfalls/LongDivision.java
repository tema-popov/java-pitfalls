package pitfalls;

import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;


/**
 *  [JP, 2.3]
 *
 *  Let's try to divide number of microseconds per day to number of milliseconds per day (we should get 1000)
 */
public class LongDivision {

    @Test
    public void testLongDivision() {
        final long MICROS_PER_DAY = 24 * 60 * 60 * 1000 * 1000;
        final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
        Assert.assertThat(MICROS_PER_DAY / MILLIS_PER_DAY, equalTo(1000));
//      ERROR: Expected: <1000> but: was <5L>
    }

    /*
    The problem is that the computation of the constant MICROS_PER_DAY does overflow.
    Although the result of the computation fits in a long with room to spare, it doesn't fit in an int.
    The computation is performed entirely in int arithmetic, and only after the computation
    completes is the result promoted to a long.
    So why is the computation performed in int arithmetic?
    Because all the factors that are multiplied together are int values.
    When you multiply two int values, you get another int value.
    */

    @Test
    public void fixLongDivisionLiteral() {
        final long MICROS_PER_DAY = 24L * 60 * 60 * 1000 * 1000;
        final long MILLIS_PER_DAY = 24L * 60 * 60 * 1000;
        Assert.assertThat(MICROS_PER_DAY / MILLIS_PER_DAY, equalTo(1000L));
    }

    @Test
    public void fixLongDivisionVariables() {
        int hoursInDay = 24;
        int minutesInHour = 60;
        int secondsInMinute = 60;
        int millisecondsInSecond = 1000;
        int microsecondsInMillisecond = 1000;
        final long MICROS_PER_DAY = (
                (long) hoursInDay * minutesInHour * secondsInMinute * millisecondsInSecond * microsecondsInMillisecond
        );
        final long MILLIS_PER_DAY = hoursInDay * minutesInHour * secondsInMinute * millisecondsInSecond;
        Assert.assertThat(MICROS_PER_DAY / MILLIS_PER_DAY, equalTo(1000L));
    }

    // When working with large numbers, watch out for overflow — it's a silent killer.

}
