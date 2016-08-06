package de.dbo.samples.springboot.rest.greeting;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import de.dbo.samples.springboot.rest.greeting.GreetingConfiguration;

/**
 * Basic integration tests for service demo application.
 *
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and
 *           only incidentally for computers to execute
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GreetingApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0", "management.port=0"})
@DirtiesContext
public class GreetingConfigurationTest {
    private static final Logger log = LoggerFactory.getLogger(GreetingConfigurationTest.class);

    @Value("${local.server.port}")
    private int                 port;

    @Value("${local.management.port}")
    private int                 mgt;

    @Value("${management.address}")
    private String              localhost;

    @Test
    public void testGreeting() throws Exception {
        final RestTemplate restTemplate = new TestRestTemplate();
        final URI uri = toURI(HELLO);
        log.warn("request " + uri + " ...");
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = restTemplate.getForEntity(uri, Map.class);
        assertThatHttpStatus(HttpStatus.OK, entity);
    }

    @Test
    public void testInfo() throws Exception {
        final RestTemplate restTemplate = new TestRestTemplate();
        final URI uri = toURI(INFO);
        log.info("request " + uri + " ...");
        @SuppressWarnings("rawtypes")
        final ResponseEntity<Map> entity = restTemplate.getForEntity(uri, Map.class);
        assertThatHttpStatus(HttpStatus.OK, entity);
    }
    
    @Test
    public void logging() {
	log.info("INFO");
	log.warn("WARN");
	log.error("ERROR");
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
                    return new URI("http://" + "localhost" + ":" + this.port + "/" + path);

                case INFO:
                    return new URI("http://localhost:" + this.mgt + "/" + path);

                default:
                    throw new IllegalArgumentException("Path [" + path + "] is unknown");
            }

        }
        catch(URISyntaxException e) {
            throw new IllegalStateException("Should never happen error: non-parsable hard-coded URI");
        }

    }

    // ========================
    // Test-specific assertions
    // ========================

    @SuppressWarnings("rawtypes")
    private static final void assertThatHttpStatus(final HttpStatus expectedHttpStatus, final ResponseEntity<Map> entity) {
        final HttpStatus responseHttpStatus = entity.getStatusCode();
        assertThat("HTTP response code is not as expected", expectedHttpStatus, equalTo(responseHttpStatus));
    }

}
