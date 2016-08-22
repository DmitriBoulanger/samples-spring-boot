package de.dbo.samples.springboot.jbehave2.IT.commons.util.print;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  Converter for elapsed duration in milliseconds
 *
 * @author Dmitri Boulanger, Hombach
 *
 */

public final class Profiler {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String DF2 = "00";
    private static final String DF3 = "000";

    private Profiler() {
        // should be never initialized as an instance
    }

    public static String formatDate(final long time) {
        return new SimpleDateFormat(DATE_FORMAT).format(new Date(time));
    }

    /**
     * Convert a millisecond duration to a string format
     *
     * @param timeintervalMs A duration in milliseconds to convert to a string form
     * @return A string of the form "LicenseStoriesPropertiesJUnit h. Y min. Z sec. W ms. ".
     */
    public static String formatMs(final long timeintervalMs) {
        if (timeintervalMs < 0) {
            return "?";
        }
        final DecimalFormat df2 = new DecimalFormat(DF2);
        final DecimalFormat df3 = new DecimalFormat(DF3);
        long millliseconds = timeintervalMs;

        final long hours = MILLISECONDS.toHours(millliseconds);
        millliseconds -= HOURS.toMillis(hours);

        final long minutes = MILLISECONDS.toMinutes(millliseconds);
        millliseconds -= MINUTES.toMillis(minutes);

        final long seconds = MILLISECONDS.toSeconds(millliseconds);
        millliseconds -= SECONDS.toMillis(seconds);

        final StringBuilder sb = new StringBuilder(64);
        if (0 < hours) {
            sb.append(df2.format(hours));
            sb.append(" h. ");
        }
        if (0 < minutes) {
            sb.append(df2.format(minutes));
            sb.append(" min. ");
        }
        if (0 < seconds) {
            sb.append(df2.format(seconds));
            sb.append(" sec. ");
        }
        sb.append(df3.format(millliseconds));
        sb.append(" ms. ");

        return (sb.toString());
    }

    /**
     * Convert elapsed  duration to a string format
     *
     * @param time A duration in milliseconds to convert to a string form
     * @return A string of the form "Elapsed: 2 h. 10 min. 29 sec. 130 ms. ".
     */
    public static String elapsed(final long start) {
        return new String("Elapsed: " + formatMs(currentTimeMillis() - start));
    }

}
