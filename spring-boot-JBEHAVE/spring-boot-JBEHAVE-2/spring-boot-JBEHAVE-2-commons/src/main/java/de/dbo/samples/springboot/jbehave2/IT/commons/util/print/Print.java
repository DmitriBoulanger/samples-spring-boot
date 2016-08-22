package de.dbo.samples.springboot.jbehave2.IT.commons.util.print;

import static de.dbo.samples.springboot.jbehave2.IT.commons.util.print.PrintConversions.sortedKeys;
import static de.dbo.samples.springboot.jbehave2.IT.commons.util.print.PrintConversions.toPrintable;
import static de.dbo.samples.springboot.jbehave2.IT.commons.util.print.PrintConversions.toSortedList;
import static java.util.Arrays.asList;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

/**
 * Pretty-print for debugging/logging
 *
 * @author Dmitri Boulanger, Hombach
 *
 */
public final class Print {
    public static final StringBuilder NULL  = new StringBuilder("NULL");
    public static final StringBuilder EMPTY = new StringBuilder("EMPTY");

    // printing constants
    public static final String        NL    = "\n\t - ";
    public static final String        NL2   = "\n\t\t -- ";
    public static final String        EQ    = " = ";
    public static final String        NB    = ": ";
    public static final String        SP    = " ";

    private Print() {
        // should be never initialized as an instance
    }

    public static final String q2(final String x) {
        final StringBuilder ret = new StringBuilder();
        ret.append("\"");
        ret.append(x);
        ret.append("\"");
        return ret.toString();
    }

    /**
     * pad with " " to the right to the given length (n)
     * x___
     * @param s string to be padded
     * @param n length of the output
     * @return padded string having the specified length
     */
    public static String padRight(final String s, final int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public static String padRight(final int s, final int n) {
        return padRight(Integer.toString(s), n);
    }

    /**
     * pad with " " to the left to the given length (n)
     * ___x
     * @param s string to be padded
     * @param n length of the output
     * @return padded string having the specified length
     */
    public static String padLeft(final String s, final int n) {
        return String.format("%1$" + n + "s", s);
    }

    public static String padLeft(final int s, final int n) {
        return padLeft(Integer.toString(s), n);
    }

    /**
     * considers map-values as collections and generates the
     * cardinality of the corresponding Cartesian product
     * @param map
     * @return cardinality of the map
     */
    public static final StringBuilder cpCardinality(final Map<?, ?> map) {
        long ret = 1;
        final StringBuilder sbCardinalities = new StringBuilder();
        final Collection<?> objects = map.values();
        for (final Object o : objects) {
            if (null == o) {
                sbCardinalities.append(0 + SP);
                continue;
            }
            if (o instanceof Collection<?>) {
                final Collection<?> collection = (Collection<?>) o;
                final int size = collection.size();
                sbCardinalities.append(size + " ");
                if (0 == size) {
                    continue;
                }
                ret = ret * (size);
            }
            else if (o instanceof Map<?, ?>) {
                final Map<?, ?> mapValue = (Map<?, ?>) o;
                final int size = mapValue.size();
                sbCardinalities.append(size + SP);
                if (0 == size) {
                    continue;
                }
                ret = ret * (size);
            }
            else {
                sbCardinalities.append(1 + SP);
            }
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("CP-cardinaliy");
        sb.append("[");
        sb.append(sbCardinalities.toString().trim().replace(SP, "x"));
        sb.append("]");
        sb.append(EQ);
        sb.append(new DecimalFormat(DF_CARDINALIY).format(ret).replace(".", SP));
        return sb;
    }
    private static final String DF_CARDINALIY = "000,000,000";

    /**
     * @param map to be printed as a sequence of lines. Lines are sorted using their keys
     * @param filter positive filter that is applied to the keys stored in the map
     * @param level of the print-out
     * @return pretty-print of the map
     */
    public static final StringBuilder lines(final Map<?, ?> map, final String filter, final int level) {
        if (null == map) {
            return NULL;
        }
        if (0 == map.size()) {
            return EMPTY;
        }
        final String nl = nl(level);
        final StringBuilder sb = new StringBuilder();
        for (final String key : sortedKeys(map)) {
            if (null != filter && filter.trim().length() > 0) {
                if (-1 == key.indexOf(filter)) {
                    continue;
                }
            }
            final StringBuilder value = toPrintable(map.get(key)).printline();
            sb.append(nl);
            if (0 == level) {
                sb.append(keyValue(key, value));
            }
            else {
                sb.append(eq(key, value));
            }

        }
        return sb;
    }

    /**
     * filter=null level=1
     * @return pretty-print of the map
     * @see #lines(Map,String,int)
     */
    public static StringBuilder lines(final Map<?, ?> map) {
        return lines(map, null, 1);
    }

    /**
     * level=1
     * @return pretty-print of the map
     * @see #lines(Map,String,int)
     */
    public static final StringBuilder lines(final Map<?, ?> map, final String filter) {
        return lines(map, filter, 1);
    }

    /**
     * filter=null level=0
     * @return pretty-print of the map
     * @see #lines(Map,String,int)
     */
    public static StringBuilder line(final Map<?, ?> map) {
        return lines(map, null, 0);
    }

    /**
     * @param collection to be printed as a sequence of lines
     * @param filter positive filter that is applied to printable objects obtained from the original values
     * @param level of the print-out
     * @return pretty-print of the map
     * @see PrintConversions#toPrintable(Object)
     */
    public static final StringBuilder lines(final Collection<?> collection, final String filter, final int offset) {
        if (null == collection) {
            return NULL;
        }
        if (0 == collection.size()) {
            return EMPTY;
        }
        final String nl = nl(offset);
        final StringBuilder sb = new StringBuilder();
        for (final Object o : collection) {
            final StringBuilder value = toPrintable(o).printline();
            if (null != filter && filter.trim().length() > 0) {
                if (-1 == value.indexOf(filter)) {
                    continue;
                }
            }
            sb.append(nl);
            sb.append(toPrintable(o).printline());
        }
        return sb;
    }

    public static final StringBuilder linesSorted(final Collection<?> collection, final String filter, final int offset) {
        if (null == collection) {
            return NULL;
        }
        if (0 == collection.size()) {
            return EMPTY;
        }
        final String nl = nl(offset);
        final StringBuilder sb = new StringBuilder();
        for (final Object o : toSortedList(collection)) {
            final StringBuilder value = toPrintable(o).printline();
            if (null != filter && filter.trim().length() > 0) {
                if (-1 == value.indexOf(filter)) {
                    continue;
                }
            }
            sb.append(nl);
            sb.append(toPrintable(o).printline());
        }
        return sb;
    }

    /**
     * filter=null level=0
     * @return pretty-print of the collection
     * @see #lines(Collection,String,int)
     */
    public static final StringBuilder line(final Collection<?> collection) {
        return lines(collection, null, 0);
    }

    /**
     * filter=null level=1
     * @return pretty-print of the collection
     * @see #lines(Collection,String,int)
     */
    public static final StringBuilder lines(final Collection<?> collection) {
        return lines(collection, null, 1);
    }

    /**
     * @param integers array of Integer-objects to be printed
     * @return pretty-print line of the integers
     * @see PrintConversions#toSortedList(Collection)
     */
    public static final StringBuilder line(final Integer[] integers) {
        if (null == integers) {
            return NULL;
        }
        if (0 == integers.length) {
            return EMPTY;
        }
        final StringBuilder sb = new StringBuilder();
        for (final Integer i : integers) {
            sb.append(SP);
            sb.append(i);
        }
        return sb;
    }

    public static final StringBuilder line(final int[] integers) {
        if (null == integers) {
            return NULL;
        }
        if (0 == integers.length) {
            return EMPTY;
        }
        final StringBuilder sb = new StringBuilder();
        for (final int i : integers) {
            sb.append(SP);
            sb.append(i);
        }
        return sb;
    }

    public static final StringBuilder lines(final String[] strings) {
        if (null == strings) {
            return NULL;
        }
        if (0 == strings.length) {
            return EMPTY;
        }
        final StringBuilder sb = new StringBuilder();
        for (final String string : strings) {
            sb.append(NL);
            sb.append(string);
        }
        return sb;
    }

    public static final StringBuilder line(final String[] strings) {
        if (null == strings) {
            return NULL;
        }
        if (0 == strings.length) {
            return EMPTY;
        }
        final StringBuilder sb = new StringBuilder();
        for (final String string : strings) {
            sb.append(SP);
            sb.append(string);
        }
        return sb;
    }

    /**
     * @param properties
     * @param filter
     * @param level
     * @return
     */
    public static final StringBuilder lines(final Properties properties, final String filter, final int level) {
        if (null == properties) {
            return NULL;
        }
        if (0 == properties.size()) {
            return EMPTY;
        }
        final String nl = nl(level);
        final StringBuilder sb = new StringBuilder();
        final String patterns = null != filter ? filter.trim() : null;
        final String[] pattern;
        if (null != patterns && patterns.length() > 0) {
            pattern = filter.trim().split("!");
        }
        else {
            pattern = null;
        }

        for (final String key : sortedKeys(properties)) {
            if (null != pattern) {
                if (null != pattern[0] && -1 == key.indexOf(pattern[0])) {
                    continue;
                }
                if (1 != pattern.length && null != pattern[1] && -1 != key.indexOf(pattern[1])) {
                    continue;
                }
            }
            sb.append(nl);
            final String value = properties.getProperty(key);
            if (0 == level) {
                sb.append(keyValue(key, value));
            }
            else {
                sb.append(eq(key, value));
            }
        }
        return sb;
    }

    public static final StringBuilder line(final Properties properties) {
        return lines(properties, null, 0);
    }

    public static final StringBuilder lines(final Properties properties) {
        return lines(properties, null, 1);
    }

    public static final StringBuilder line(final Properties properties, final String filter) {
        return lines(properties, filter, 0);
    }

    public static final StringBuilder lines(final Properties properties, final String filter) {
        return lines(properties, filter, 1);
    }

    /**
     * @param collection
     * @return pretty-print of the sorted collection
     * @see PrintConversions#toSortedList(Collection)
     */
    public static final StringBuilder linesSorted(final Collection<?> collection) {
        if (null == collection) {
            return NULL;
        }
        if (0 == collection.size()) {
            return EMPTY;
        }
        final StringBuilder sb = new StringBuilder();
        for (final String key : toSortedList(collection)) {
            sb.append(NL + key);
        }
        return sb;
    }

    /**
     * @param collection
     * @return pretty-print of the numbered collection
     * @see PrintConversions#toSortedList(Collection)
     */
    public static final StringBuilder linesNumbered(final Collection<?> collection) {
        if (null == collection) {
            return NULL;
        }
        if (0 == collection.size()) {
            return EMPTY;
        }
        final StringBuilder sb = new StringBuilder();
        int i = 0;
        for (final Object o : collection) {
            sb.append(NL + number(i++) + NB + toPrintable(o).printline());
        }
        return sb;
    }

    /**
     * @param string array of strings to be printed
     * @return pretty-print of the numbered strings
     * @see PrintConversions#toSortedList(Collection)
     */
    public static final StringBuilder linesNumbered(final String[] strings) {
        if (null == strings) {
            return NULL;
        }
        if (0 == strings.length) {
            return EMPTY;
        }
        return linesNumbered(asList(strings));
    }

    private static final StringBuilder eq(final String key, final StringBuilder value) {
        final StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append(EQ);
        sb.append(value);
        return sb;
    }

    private static final StringBuilder eq(final String key, final String value) {
        return eq(key, new StringBuilder(value));
    }

    private static final StringBuilder keyValue(final String key, final String value) {
        return keyValue(key, new StringBuilder(value));
    }

    private static final StringBuilder keyValue(final String key, final StringBuilder value) {
        final StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(eq(key, value));
        sb.append("]");
        return sb;
    }

    private static final String nl(final int offset) {
        switch (offset) {
            case 1:
                return NL;
            case 2:
                return NL2;
            default:
                return SP;
        }
    }

    private static final String DF3 = "000";

    private static final String number(final int number) {
        return new DecimalFormat(DF3).format(number);
    }
}
