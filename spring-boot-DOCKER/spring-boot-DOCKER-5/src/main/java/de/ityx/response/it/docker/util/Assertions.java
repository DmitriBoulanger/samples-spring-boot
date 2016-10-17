package de.ityx.response.it.docker.util;

import static de.ityx.response.it.docker.util.Utils.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

public class Assertions {
    
    
    public static final void assertThatCovers( final String msg, final List<String> cover, final List<String> data) {
        for (final String dataItem : data) {
            assertThat( String.format(msg, dataItem), occurs(dataItem,cover));
        }
    }
    
}
