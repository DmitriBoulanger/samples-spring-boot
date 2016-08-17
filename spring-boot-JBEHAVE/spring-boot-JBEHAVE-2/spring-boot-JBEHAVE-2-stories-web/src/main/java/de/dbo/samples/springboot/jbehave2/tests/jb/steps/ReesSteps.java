package de.dbo.samples.springboot.jbehave2.tests.jb.steps;

/* Hamcrest */
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import de.dbo.samples.springboot.jbehave2.tests.TestServer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
// SLF4J
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;

@Component
public class ReesSteps {
    private static final Logger log = LoggerFactory.getLogger(ReesSteps.class);

    public ReesSteps() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    @Autowired
    private TestServer           testServer;

    @Given("server initialized 3")
    public void init() {
        // assert
        final int port = testServer.getPort();
        assertThat("Server port is not as expected", port, greaterThan(9999));
        final String host = testServer.getHost();
        assertThat("Server host is null", host, notNullValue());
        if (log.isDebugEnabled()) {
            log.debug("test server initialized: " + testServer.print());
        }
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
    public void admin() throws Exception {
        final TestRestTemplate restTemplate = new TestRestTemplate();
        final URI uri = toURI(INFO);
        log.info("request " + uri + " ...");
        @SuppressWarnings("rawtypes")
	final ResponseEntity<Map> entity = restTemplate.getForEntity(uri, Map.class);
        assertThatHttpStatus(HttpStatus.OK, entity);
    }

    // ========================
    // Test-specific assertions
    // ========================

    private static final void assertThatHttpStatus(final HttpStatus expectedHttpStatus, @SuppressWarnings("rawtypes") final ResponseEntity<Map> entity) {
        final HttpStatus responseHttpStatus = entity.getStatusCode();
        assertThat("HTTP response code is not as expected", expectedHttpStatus, equalTo(responseHttpStatus));
    }

    // ========================
    // Test implementations
    // ========================

    private static final String HELLO = "hello-world";
    private static final String INFO  = "info";

    private final URI toURI(final String path) {
        try {
            switch (path) {

                case HELLO:
                    return new URI("http://" + testServer.getHost() + ":" +  testServer.getPort() + "/" + path);

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

    // =======================
    //   HELPERS
    // =======================

}
