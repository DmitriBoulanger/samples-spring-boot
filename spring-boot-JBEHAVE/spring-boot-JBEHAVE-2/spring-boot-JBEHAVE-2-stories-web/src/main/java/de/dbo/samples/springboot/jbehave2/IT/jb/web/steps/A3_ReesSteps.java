package de.dbo.samples.springboot.jbehave2.IT.jb.web.steps;

import static de.dbo.samples.springboot.jbehave2.IT.commons.TestServerAssertions.assertThatTestServerInitialized;
/* Hamcrest */
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
// SLF4J
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import de.dbo.samples.springboot.jbehave2.IT.commons.TestServer;

@Component
public class A3_ReesSteps {
    private static final Logger log = LoggerFactory.getLogger(A3_ReesSteps.class);

    public A3_ReesSteps() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    @Autowired
    private TestServer testServer;

    @Given("A3 server initialized")
    public void init() {
        assertThatTestServerInitialized(testServer);
    }

    @Then("greeting")
    public void greeting() throws Exception {
        final TestRestTemplate restTemplate = new TestRestTemplate();
        final URI uri = toURI(HELLO);
        log.info("request " + uri + " ...");
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = restTemplate.getForEntity(uri, Map.class);
        assertThatHttpStatus(HttpStatus.OK, entity);
    }

    @Then("admin")
    @Pending
    public void admin() throws Exception {
        final TestRestTemplate restTemplate = new TestRestTemplate();
        final URI uri = toURI(INFO);
        log.info("request " + uri + " ...");
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = restTemplate.getForEntity(uri, Map.class);
        assertThatHttpStatus(HttpStatus.OK, entity);
    }

    // ==================================================================================================================
    //                                   Implementations
    // ==================================================================================================================

    private static final String HELLO = "hello-world";
    private static final String INFO  = "info";

    private final URI toURI(final String path) {
        try {
            switch (path) {

                case HELLO:
                    return new URI("http://" + testServer.getHost() + ":" + testServer.getPort() + "/" + path);

                case INFO:
                    return new URI("http://" + testServer.getHost() + ":" + testServer.getPort() + "/" + path);

                default:
                    throw new IllegalArgumentException("Path [" + path + "] is unknown");
            }

        }
        catch(URISyntaxException e) {
            throw new IllegalStateException("Should never happen error: non-parsable hard-coded URI");
        }
    }

    // ==================================================================================================================
    //                                   ASSERTIONS
    // ==================================================================================================================

    private static final void assertThatHttpStatus(final HttpStatus expectedHttpStatus, @SuppressWarnings("rawtypes") final ResponseEntity<Map> entity) {
        final HttpStatus responseHttpStatus = entity.getStatusCode();
        assertThat("HTTP response code is not as expected", expectedHttpStatus, equalTo(responseHttpStatus));
    }

    // ==================================================================================================================
    //                                   HELPERS
    // ==================================================================================================================
}
