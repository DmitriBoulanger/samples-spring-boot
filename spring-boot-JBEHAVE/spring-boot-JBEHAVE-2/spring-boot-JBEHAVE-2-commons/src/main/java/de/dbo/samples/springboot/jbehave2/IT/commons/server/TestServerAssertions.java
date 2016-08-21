package de.dbo.samples.springboot.jbehave2.IT.commons.server;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TestServerAssertions {
    private static final Logger log = LoggerFactory.getLogger(TestServerAssertions.class);
    
    /**
     * assert test-server initialization
     */
    public static void assertThatTestServerInitialized(final TestServer testServer) {
        final int port = testServer.getPort();
        assertThat(testServer.name() + ": port is not as expected", port, greaterThan(9999));
        final String host = testServer.getHost();
        assertThat(testServer.name() + ": host is null", host, notNullValue());
        log.info("Server ["+ testServer.getClass().getSimpleName() +"] available");
    }

}
