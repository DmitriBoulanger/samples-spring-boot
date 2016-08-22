package de.dbo.samples.springboot.jbehave2.IT.commons.util.print;

import static de.dbo.samples.springboot.jbehave2.IT.commons.util.print.Print.line;
import static de.dbo.samples.springboot.jbehave2.IT.commons.util.print.Print.lines;
import static de.dbo.samples.springboot.jbehave2.IT.commons.util.print.PrintConversions.toColllectionOfPrintables;
import static de.dbo.samples.springboot.jbehave2.IT.commons.util.print.PrintConversions.toMapOfPrintables;

import java.util.Collection;
import java.util.Map;

/**
 * Internal (private) class to be used in the Print-algorithms
 *
 * @author Dmitri Boulanger, Hombach
 *
 */
final class PrintableObject implements Printable {

    private final Printable              printable;
    private final Map<String, Printable> mapOfPrintables;
    private final Collection<Printable>  collectionOfPrintables;
    private final StringBuilder          stringBuilder;

    /**
     * Printable NULL
     */
    PrintableObject() {
        this.printable = null;
        this.mapOfPrintables = null;
        this.collectionOfPrintables = null;
        this.stringBuilder = null;
    }

    PrintableObject(final String o) {
        this.printable = null;
        this.mapOfPrintables = null;
        this.collectionOfPrintables = null;
        this.stringBuilder = nn(o);
    }

    PrintableObject(final StringBuilder o) {
        this.printable = null;
        this.mapOfPrintables = null;
        this.collectionOfPrintables = null;
        this.stringBuilder = nn(o);
    }

    PrintableObject(final int o) {
        this.printable = null;
        this.mapOfPrintables = null;
        this.collectionOfPrintables = null;
        this.stringBuilder = nn(o);
    }

    PrintableObject(final Integer o) {
        this.printable = null;
        this.mapOfPrintables = null;
        this.collectionOfPrintables = null;
        this.stringBuilder = nn(o);
    }

    PrintableObject(final Integer[] o) {
        this.printable = null;
        this.mapOfPrintables = null;
        this.collectionOfPrintables = null;
        this.stringBuilder = nn(o);
    }

    PrintableObject(final int[] o) {
        this.printable = null;
        this.mapOfPrintables = null;
        this.collectionOfPrintables = null;
        this.stringBuilder = nn(o);
    }

    PrintableObject(final String[] o) {
        this.printable = null;
        this.mapOfPrintables = null;
        this.collectionOfPrintables = null;
        this.stringBuilder = nn(o);
    }

    PrintableObject(final Map<?, ?> o) {
        this.printable = null;
        this.mapOfPrintables = nn(o);
        this.collectionOfPrintables = null;
        this.stringBuilder = null;
    }

    PrintableObject(final Collection<?> o) {
        this.printable = null;
        this.mapOfPrintables = null;
        this.collectionOfPrintables = nn(o);
        this.stringBuilder = null;
    }

    PrintableObject(final Printable o) {
        this.printable = nn(o);
        this.mapOfPrintables = null;
        this.collectionOfPrintables = null;
        this.stringBuilder = null;
    }

    // HELPERS

    private static final StringBuilder nn(final String value) {
        return null == value ? null
                : new StringBuilder(value);
    }

    private static final StringBuilder nn(final StringBuilder value) {
        return null == value ? null
                : value;
    }

    private static final StringBuilder nn(final Integer value) {
        return value == null ? null
                : new StringBuilder(Integer.toString(value.intValue()));
    }

    private static final StringBuilder nn(final int value) {
        return new StringBuilder(Integer.toString(value));
    }

    private static final StringBuilder nn(final int[] value) {
        return value == null ? null
                : new StringBuilder(line(value));
    }

    private static final StringBuilder nn(final Integer[] value) {
        return value == null ? null
                : new StringBuilder(line(value));
    }

    private static final StringBuilder nn(final String[] value) {
        return value == null ? null
                : new StringBuilder(line(value));
    }

    private static final Map<String, Printable> nn(final Map<?, ?> value) {
        return value == null ? null
                : toMapOfPrintables(value);
    }

    private static final Collection<Printable> nn(final Collection<?> value) {
        return value == null ? null
                : toColllectionOfPrintables(value);
    }

    private static final Printable nn(final Printable value) {
        return value == null ? null : value;
    }

    @Override
    public StringBuilder printline() {
        if (null != printable) {
            return printable.printline();
        }
        else if (null != mapOfPrintables) {
            return line(mapOfPrintables);
        }
        else if (null != collectionOfPrintables) {
            return line(collectionOfPrintables);
        }
        else if (null != stringBuilder) {
            return stringBuilder;
        }
        else {
            return Print.NULL;
        }
    }

    @Override
    public StringBuilder printlines() {
        if (null != printable) {
            return printable.printlines();
        }
        else if (null != mapOfPrintables) {
            return lines(mapOfPrintables);
        }
        else if (null != stringBuilder) {
            return stringBuilder;
        }
        else {
            return Print.NULL;
        }
    }
}
