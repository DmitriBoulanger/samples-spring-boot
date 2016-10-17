package de.ityx.response.it.docker.util;

import java.util.List;

/**
 * Primitive algorithms
 * 
 * @author boulanger
 *
 */
public final class Utils {
    
    private Utils() {
        
    }
    
    public static boolean occurs(final String dataItem, final String[] cover) {
        if (null==dataItem || 0==dataItem.trim().length()) {
            return false;
        }
        
        for (final String coverItem:cover) {
            if (-1!=coverItem.indexOf(dataItem)) {
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
  

}
