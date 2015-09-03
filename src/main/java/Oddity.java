import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.*;

/**
 *  [JP, 2.1]
 */
public class Oddity {
    private static final Random RANDOM = new Random();

    public boolean isOdd(int i) {
        return i % 2 == 1;
    }

    /*
        So, is isOdd method is working correctly?
     */
    @Test
    public void testIsOdd() {
        Assert.assertThat(isOdd(2), is(false));
        Assert.assertThat(isOdd(1), is(true));
        Assert.assertThat(isOdd(-2), is(false));
        Assert.assertThat(isOdd(-1), is(true)); // Error
    }

    /*
        It seems not.
        It is defined to satisfy the following identity for all int values a and all nonzero int values b:
        (a / b) * b + (a % b) == a
        In other words, if you divide a by b, multiply the result by b, and add the remainder,
        you are back where you started [JLS 15.17.3].
        This identity makes perfect sense, but in combination
        with Java's truncating integer division operator [JLS 15.17.2],
        it implies that when the remainder operation returns a nonzero result, it has the same sign as its left operand.
    */

    public boolean goodIsOdd(int i) {
        return i % 2 != 0;
    }

    @Test
    public void testGoodIsOdd() {
        Assert.assertThat(goodIsOdd(2), is(false));
        Assert.assertThat(goodIsOdd(1), is(true));
        Assert.assertThat(goodIsOdd(-2), is(false));
        Assert.assertThat(goodIsOdd(-1), is(true));
    }

    public boolean fastGoodIsOdd(int i) {
        return (i & 1) != 0;
    }

    @Test
    public void testFastGoodIsOdd() {
        Assert.assertThat(fastGoodIsOdd(2), is(false));
        Assert.assertThat(fastGoodIsOdd(1), is(true));
        Assert.assertThat(fastGoodIsOdd(-2), is(false));
        Assert.assertThat(fastGoodIsOdd(-1), is(true));
    }

    @Test
    public void compareTime() {
        Utils.timeIt(() -> goodIsOdd(RANDOM.nextInt()), "goodIsOdd");
        Utils.timeIt(() -> fastGoodIsOdd(RANDOM.nextInt()), "fastGoodIsOdd");
    }

}
