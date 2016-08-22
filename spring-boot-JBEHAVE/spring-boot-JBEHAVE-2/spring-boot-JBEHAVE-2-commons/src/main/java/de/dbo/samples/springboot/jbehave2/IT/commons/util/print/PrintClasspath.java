package de.dbo.samples.springboot.jbehave2.IT.commons.util.print;

import static de.dbo.samples.springboot.jbehave2.IT.commons.util.print.Print.lines;
import static de.dbo.samples.springboot.jbehave2.IT.commons.util.print.Print.padRight;
import static java.lang.System.getProperty;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PrintClasspath {

    private static final String   pathSeparator     = java.io.File.pathSeparator;
    private static final String   separator         = java.io.File.separator;
    private static final String   sourcesKey        = separator + "git" + separator;
    private static final String   repositoryKey     = separator + "repository" + separator;
    private static final String   repositoryItyxKey = repositoryKey + "de" + separator + "ityx" + separator;
    private static final String   snapshotKey       = "SNAPSHOT";

    private static final String[] classpath         = getProperty("java.class.path").split(pathSeparator);

    public static StringBuilder complete() {
        final StringBuilder sb = new StringBuilder();
        sb.append(printClasspathFromSources());
        sb.append(printClasspathFromRepositorySnapshot());
        sb.append(printClasspathFromRepositoryVersion());
        sb.append(printClasspathFromOthers());
        return sb;
    }

    public static StringBuilder ityx() {
        final StringBuilder sb = new StringBuilder();
        sb.append(printClasspathFromSources());
        sb.append(printClasspathFromRepositorySnapshot());
        sb.append(printClasspathFromRepositoryVersion());
        return sb;
    }

    public static StringBuilder printClasspathFromSources() {
        final List<String> names = rename(sortedSelectedItemsPositive(new String[]{sourcesKey}, null, classpath), sourcesKey);
        return print("\n##### ITYX SOURCES in " + sourcesKey + ": ", names);
    }

    public static StringBuilder printClasspathFromRepository() {
        final StringBuilder sb = new StringBuilder();
        sb.append(printClasspathFromRepositorySnapshot());
        sb.append(printClasspathFromRepositoryVersion());
        return sb;
    }

    private static StringBuilder printClasspathFromRepositorySnapshot() {
        final List<String> names = rename(sortedSelectedItemsPositive(new String[]{snapshotKey, repositoryItyxKey}, null, classpath), repositoryItyxKey);
        return print("\n##### ITYX " + snapshotKey + "S " + snapshotKey + " from maven local " + repositoryItyxKey + ": ", names);
    }

    private static StringBuilder printClasspathFromRepositoryVersion() {
        final String negativeKey = snapshotKey;
        final List<String> names = rename(sortedSelectedItemsPositive(new String[]{repositoryItyxKey}, negativeKey, classpath), repositoryItyxKey);
        return print("\n##### ITYX VERSIONS from maven local " + repositoryItyxKey + ": ", names);
    }

    public static StringBuilder printClasspathFromOthers() {
        final List<String> names = rename(sortedSelectedItemsNegative(new String[]{sourcesKey, repositoryItyxKey}, classpath), repositoryKey);
        return print("\n##### OTHRES: ", names);
    }

    private static final List<String> sortedSelectedItemsPositive(final String[] postiveKeys, final String negativeKey, final String[] classpath) {
        final List<String> ret = new ArrayList<String>();
        for (final String item : classpath) {
            if (null != negativeKey && -1 != item.indexOf(negativeKey)) {
                continue;
            }
            if (positive(item, postiveKeys)) {
                ret.add(item);
            }
        }
        Collections.sort(ret);
        return ret;
    }

    private static final List<String> sortedSelectedItemsNegative(final String[] negativeKeys, final String[] classpath) {
        final List<String> ret = new ArrayList<String>();
        for (final String item : classpath) {
            if (negative(item, negativeKeys)) {
                continue;
            }
            ret.add(item);
        }
        Collections.sort(ret);
        return ret;
    }

    /**
     * @return true only if all keys have occurrences in item
     */
    private static final boolean positive(final String item, final String[] keys) {
        if (null == keys || 0 == keys.length) {
            return false;
        }
        for (final String key : keys) {
            if (null == key || -1 == item.indexOf(key)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return true only if there are key-occurrences in the item
     */
    private static final boolean negative(final String item, final String[] keys) {
        if (null == keys || 0 == keys.length) {
            return false;
        }
        for (final String key : keys) {
            if (null != key && -1 != item.indexOf(key)) {
                return true;
            }
        }
        return false;
    }

    private static StringBuilder print(final String title, final List<String> names) {
        final StringBuilder sb = new StringBuilder(title);
        sb.append(lines(names));
        return sb;
    }

    private static final List<String> rename(final List<String> names, final String removeText) {
        if (null == removeText || 0 == removeText.trim().length()) {
            return names;
        }
        final List<String> ret = new ArrayList<String>();
        for (final String name : names) {
            final String timestamp = timestamp(name);
            final int lastIndex = name.lastIndexOf(removeText);
            final String nameRenamed;
            if (-1 != lastIndex) {
                final int beginIndex = lastIndex + removeText.length();
                nameRenamed = name.substring(beginIndex);
            }
            else {
                nameRenamed = name;
            }
            ret.add(padRight(nameRenamed, 125) + "  " + timestamp);
        }
        return ret;
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    private static final String timestamp(final String filepath) {
        final long timestamp = new File(filepath).lastModified();
        return sdf.format(new Date(timestamp));
    }

}
