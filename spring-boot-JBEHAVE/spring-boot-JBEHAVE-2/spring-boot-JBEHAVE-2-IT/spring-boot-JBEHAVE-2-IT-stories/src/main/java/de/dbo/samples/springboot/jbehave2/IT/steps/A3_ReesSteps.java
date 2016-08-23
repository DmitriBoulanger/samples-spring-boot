package de.dbo.samples.springboot.jbehave2.IT.steps;

import static de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServerAssertions.assertThatTestServerInitialized;
/* Hamcrest */
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.net.URI;
import java.net.URISyntaxException;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
/* SLF4J */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import de.dbo.samples.springboot.jbehave2.IT.commons.context.ContextThreadLocal;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServer;
import de.dbo.samples.springboot.jbehave2.IT.commons.stepsimpl.StepsBase;
import de.dbo.samples.springboot.jbehave2.IT.steps.ctx.A3_ReesSteps_Data;
import de.dbo.samples.springboot.jbehave2.app3.domain.Greeting;

@Component
public class A3_ReesSteps extends StepsBase {
    private static final Logger log = LoggerFactory.getLogger(A3_ReesSteps.class);

    public A3_ReesSteps() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    @Given("A3 server initialized")
    public void init() {
        final TestServer testServer = testServer("A3 server-clone");
        assertThatTestServerInitialized(testServer);
        ctx().setTestSever(testServer);
    }

    @Then("greeting")
    public void greeting() throws Exception {
        final TestRestTemplate restTemplate = new TestRestTemplate();
        final URI uri = toURI(HELLO);
        log.info("request " + uri + " ...");

        final ResponseEntity<Greeting> entity = restTemplate.getForEntity(uri, Greeting.class);
        assertThatHttpStatus(HttpStatus.OK, entity);

        final Greeting greeting = entity.getBody();
        final int cnt = Long.valueOf(greeting.getCnt()).intValue();
        // Repository has been pre-loaded with 2 Shoppers. This visit has to be the third visit
        assertThat("Greeting counter is not as expected. Not the first shopper?", cnt, equalTo(3));
    }

    @Then("admin")
    @Pending // admin request should be secure but it is not
    public void admin() throws Exception {
        final TestRestTemplate restTemplate = new TestRestTemplate();
        final URI uri = toURI(INFO);
        log.info("request " + uri + " ...");
        final ResponseEntity<Greeting> entity = restTemplate.getForEntity(uri, Greeting.class);
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
                    return new URI(ctx().contextURL() + "/" + path);
                case INFO:
                    return new URI(ctx().contextURL() + "/" + path);
                default:
                    throw new IllegalArgumentException("Path [" + path + "] is unknown");
            }
        }
        catch(URISyntaxException e) {
            throw new IllegalStateException("Should never happen error: non-parsable hard-coded URI");
        }
    }

    // ==================================================================================================================
    //                                   CONTEXT DATA
    // ==================================================================================================================

    private static A3_ReesSteps_Data ctx() {
        return (A3_ReesSteps_Data) ContextThreadLocal.contextData(A3_ReesSteps_Data.class);
    }

    // ==================================================================================================================
    //                                   ASSERTIONS
    // ==================================================================================================================

    private static final void assertThatHttpStatus(final HttpStatus expectedHttpStatus, final ResponseEntity<Greeting> entity) {
        final HttpStatus responseHttpStatus = entity.getStatusCode();
        assertThat("HTTP response code is not as expected", expectedHttpStatus, equalTo(responseHttpStatus));
    }

    // ==================================================================================================================
    //                                   HELPERS
    // ==================================================================================================================

}
