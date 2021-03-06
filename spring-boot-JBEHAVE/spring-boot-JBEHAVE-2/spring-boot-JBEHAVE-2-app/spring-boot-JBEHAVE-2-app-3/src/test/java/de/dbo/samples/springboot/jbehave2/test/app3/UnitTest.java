package de.dbo.samples.springboot.jbehave2.test.app3;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

//
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
//
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Basic integration tests for service demo application.
 *
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and
 *           only incidentally for computers to execute
 *
 */
//
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {UnitTestApplication.class})
@DirtiesContext
//
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SuppressWarnings("rawtypes")
public class UnitTest {
    private static final Logger log = LoggerFactory.getLogger(UnitTest.class);

    @Autowired
    private UnitTestServer      testServer;

    @Test
    public void test00_Greeting() throws Exception {

        final TestRestTemplate restTemplate = new TestRestTemplate();
        final URI uri = toURI(HELLO);
        log.info("request " + uri + " ...");
        final ResponseEntity<Map> entity = restTemplate.getForEntity(uri, Map.class);
        assertThatHttpStatus(HttpStatus.OK, entity);
    }

    @Test
    @Ignore
    public void test10_Admin() throws Exception {
        final TestRestTemplate restTemplate = new TestRestTemplate();
        final URI uri = toURI(INFO);
        log.info("request " + uri + " ...");
        final ResponseEntity<Map> entity = restTemplate.getForEntity(uri, Map.class);
        assertThatHttpStatus(HttpStatus.OK, entity);
    }

    // ========================
    // Test-specific assertions
    // ========================

    private static final void assertThatHttpStatus(final HttpStatus expectedHttpStatus, final ResponseEntity<Map> entity) {
        final HttpStatus responseHttpStatus = entity.getStatusCode();
        assertThat("HTTP response code is not as expected", expectedHttpStatus, equalTo(responseHttpStatus));
    }

    // ========================
    // Test implementations
    // ========================

    private static final String HELLO = "hello-world";
    private static final String INFO  = "info";

    private final URI toURI(final String path) {
        String urlAsString = null;
        try {

            switch (path) {

                case HELLO:
                    urlAsString = "http://" + testServer.getHost() + ":" + testServer.getPort() + "/" + path;
                    break;

                case INFO:
                    urlAsString = "http://" + testServer.getHost() + ":" + testServer.getPort() + "/" + path;
                    break;

                default:
                    throw new IllegalArgumentException("Path [" + path + "] is unknown");
            }

            return new URI(urlAsString);

        }
        catch(URISyntaxException e) {
            throw new IllegalStateException("Non-parsable hard-coded URI: " + urlAsString);
        }
    }

    // =======================
    //   HELPERS
    // =======================

}
