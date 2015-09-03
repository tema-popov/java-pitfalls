import com.google.common.base.Stopwatch;

/**
 * Created by jambo on 03/09/15.
 */
public class Utils {

    public static void timeIt(Runnable runnable, String description, int times) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < times; i++) {
            runnable.run();
        }
        stopwatch.stop();
        System.out.println(description + ": took " + stopwatch + " for " + times + " iteration");
    }


    public static void timeIt(Runnable runnable, String description) {
        timeIt(runnable, description, 10000);
    }
}
