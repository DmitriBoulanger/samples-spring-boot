package de.dbo.samples.springboot.jbehave2.IT.commons.util.print;

import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Internal (private) class to be used in the Print-algorithms
 * 
 * @author Dmitri Boulanger, Hombach
 *
 */
final class PrintConversions {

    private PrintConversions() {
        // should be never initialized as an instance
    }

    /**
     * collects keys and values from the given map and then
     * inserts them into a new map with string as keys and printable objects as values
     * @param map
     * @return map with string-keys and printable values
     * @see #keyToString(Object)
     * @see #toPrintable(Object)
     */
    static Map<String, Printable> toMapOfPrintables(final Map<?, ?> map) {
        final Map<String, Printable> ret = new HashMap<String, Printable>();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            ret.put(keyToString(entry.getKey()), toPrintable(entry.getValue()));
        }
        return ret;
    }

    /**
     * @param collection objects to be converted into printable objects
     * @return collection of printable objects
     */
    static Collection<Printable> toColllectionOfPrintables(final Collection<?> collection) {
        final Collection<Printable> ret = new ArrayList<Printable>();
        for (Object o : collection) {
            ret.add(toPrintable(o));
        }
        return ret;
    }

    /**
     * @param o object to be converted into a printable
     * @return printable object
     * @see PrintableObject
     */
    static final Printable toPrintable(final Object o) {
        if (null == o) {
            return new PrintableObject(Print.NULL);
        }
        else if (o instanceof Printable) {
            return (Printable) o;
        }
        else if (o instanceof String) {
            return new PrintableObject((String) o);
        }
        else if (o instanceof String[]) {
            return new PrintableObject((String[]) o);
        }
        else if (o instanceof StringBuilder) {
            return new PrintableObject((StringBuilder) o);
        }
        else if (o instanceof Integer) {
            return new PrintableObject((Integer) o);
        }
        else if (o instanceof Integer[]) {
            return new PrintableObject((Integer[]) o);
        }
        else if (o instanceof int[]) {
            return new PrintableObject((int[]) o);
        }
        else if (o instanceof Map<?, ?>) {
            return new PrintableObject(toMapOfPrintables((Map<?, ?>) o));
        }
        else if (o instanceof Collection<?>) {
            return new PrintableObject(toColllectionOfPrintables((Collection<?>) o));
        }
        else {
            return new PrintableObject(o.toString());
        }
    }

    /**
     * @param key key-object from a map
     * @return string representation of the key
     * @see #keyToString(Object)
     */
    protected static final String keyToString(final Object key) {
        final String keySring;
        if (key instanceof String) {
            keySring = (String) key;
        }
        else if (key instanceof Integer) {
            keySring = ((Integer) key).toString();
        }
        else if (key instanceof Printable) {
            keySring = ((Printable) key).printline().toString();
        }
        else {
            keySring = toPrintable(key).printline().toString();
        }
        return keySring;
    }

    /**
     * @param map source of the keys
     * @return new sorted list containing string-representations of the map-keys
     */
    static final List<String> sortedKeys(final Map<?, ?> map) {
        final List<String> keys = new ArrayList<String>();
        for (final Object o : map.keySet()) {
            keys.add(keyToString(o));
        }
        sort(keys);
        return keys;
    }

    /**
     * @param properties source of the values
     * @return new sorted list containing string-representations of the property-keys
     */
    static final List<String> sortedKeys(final Properties properties) {
        final List<String> keys = new ArrayList<String>();
        keys.addAll(new ArrayList<String>(properties.stringPropertyNames()));
        sort(keys);
        return keys;
    }

    static final List<String> toSortedList(final Collection<?> collection) {
        final List<String> values = new ArrayList<String>();
        for (final Object o : collection) {
            values.add(keyToString(o));
        }
        sort(values);
        return values;
    }
}
