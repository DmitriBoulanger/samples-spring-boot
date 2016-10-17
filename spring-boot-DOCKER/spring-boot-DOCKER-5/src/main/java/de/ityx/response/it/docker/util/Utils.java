package de.ityx.response.it.docker.util;

import static de.ityx.response.it.docker.util.Utils.nn;
import static de.ityx.response.it.docker.util.Utils.occurs;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Primitive algorithms
 * 
 * @author boulanger
 *
 */
public final class Utils {
    
    private Utils() {
        
    }
    
    /**
     * @param x
     * 
     * @return only if not-null and not empty
     */
    public static boolean nn(final String x) {
        return null != x && 0 != x.trim().length();
    }
    
    public static boolean occurs(final String dataItem, final String[] cover) {
        if (null==dataItem || 0==dataItem.trim().length()) {
            return false;
        }
        if (null == cover) {
            return false;
        }
        
        for (final String coverItem:cover) {
            if (!nn(coverItem)) {
                continue;
            }
            else if (-1!=coverItem.indexOf(dataItem)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param dataItem
     * @param cover
     * @return true only if the dataItem occurs as a sub-string in an element of the cover
     */
    public static boolean occurs(final String dataItem, final List<String> cover) {
        if (null==dataItem || 0==dataItem.trim().length()) {
            return false;
        }
        
        for (final String coverItem:cover) {
            if (-1 != coverItem.indexOf(dataItem)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * @param keys
     * @param data
     * @return true only if at least one key occurs in the data
     */
    public static final boolean keyFound(final Set<String> keys, final String[] data) {
        if (null == keys || keys.isEmpty()) {
            return false;
        }

        for (final String key : keys) {
            if (occurs(key, data)) {
                return true;
            }
        }
        return false;
    }
  
    
    /**
     * Checks to see if a specific port is available.
     *
     * @param port
     *            the port to check for availability
     */
    public static Boolean isPortAvailable(final int port) {
        if (port < 1100 || port > 60000) {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }

        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        }
        catch(IOException ignored) {
            ignored.printStackTrace();
        }
        finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                }
                catch(IOException e) {
                    /* should not be thrown */
                }
            }
        }

        return false;
    }
    
    public static boolean isEmpty(@SuppressWarnings("rawtypes") final Set set) {
        return null == set || set.isEmpty();
    }

}
