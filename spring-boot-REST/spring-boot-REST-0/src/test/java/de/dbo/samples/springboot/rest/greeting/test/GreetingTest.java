package de.dbo.samples.springboot.rest.greeting.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
//
import de.dbo.samples.springboot.rest.greeting.core.GreetingApplicationContextProvider;
//
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
//
import javax.annotation.PostConstruct;
//
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
//
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
//
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
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

@RunWith(SpringRunner.class)
@Profile("TEST")
@ComponentScan({"de.dbo.samples.springboot.rest.greeting.core, de.dbo.samples.springboot.rest.greeting.test"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
//
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SuppressWarnings("rawtypes")
public class GreetingTest {
    private static final Logger log = LoggerFactory.getLogger(GreetingTest.class);

    private static boolean PRINT_DONE = false;

    @Autowired
    private GreetingApplicationContextProvider greetingApplicationContextProvider;

    @LocalServerPort
    private int                 port;

    //    @Value("${management.port}") /* from application.properties */
    //TODO how-to use tomcat management port     @LocalServerPort
    private int                 mgt;

    @Value("${management.address}") /* from application.properties */
    private String              localhost;

    @PostConstruct
    public void init() {
	mgt = port;
	if (!PRINT_DONE) {
	    printPorts("PostConstruct");
	    log.trace(greetingApplicationContextProvider.printBeans().toString());
	    PRINT_DONE = true;
	}
    }

    @Test
    public void nr00_testGreeting() throws Exception {
	final TestRestTemplate restTemplate = new TestRestTemplate();
	final URI uri = toURI(HELLO);
	log.warn("request " + uri + " ...");
	final ResponseEntity<Map> entity = restTemplate.getForEntity(uri, Map.class);
	assertThatHttpStatus(HttpStatus.OK, entity);
    }

    @Test
    public void nr10_testAdmin() throws Exception {
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

    // =======================
    //   HELPERS
    // =======================
    
    private void printPorts(final String comment) {
   	final StringBuilder sb = new StringBuilder("Server ports in "+comment+":");
   	sb.append("\n\t - port = " + port);
   	sb.append("\n\t - mgt  = " + mgt);
   	log.info( sb.toString() );
       }
}
