package de.dbo.samples.springboot.jbehave2.IT.commons.server;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TestContainerAssertions {
    private static final Logger log = LoggerFactory.getLogger(TestContainerAssertions.class);

    /**
     * assert correctness of the  test-server initialization
     */
    public static void assertThatTestServerInitialized(final TestContainer testServer) {
        final int port = testServer.port();
        assertThat(testServer.name() + ": port is not as expected", port, greaterThan(1000));
        final String host = testServer.host();
        assertThat(testServer.name() + ": host is null", host, notNullValue());
        log.info("Server [" + testServer.getClass().getSimpleName() + "] available");
    }
    
    /**
     * asserts that the base URL of the container is available
     * 
     * @param testServer
     */
    public static void assertThatTestServerContextReplies(final TestContainer testServer) {
	//TODO
    }
    
    /**
     * asserts that the port of the container replies
     * 
     * @param testServer
     */
    public static void assertThatTestServerPortReplies(final TestContainer testServer) {
	//TODO
    }
  

}
