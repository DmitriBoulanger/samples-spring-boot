package de.dbo.samples.springboot.jbehave2.IT.jb.web.steps;

import static com.jayway.restassured.RestAssured.given;
/* Hamcrest */
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
// SLF4J
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;

import de.dbo.samples.springboot.jbehave2.IT.web.ITestServer;

@Component
public class A2_ProjectSteps {
    private static final Logger log = LoggerFactory.getLogger(A2_ProjectSteps.class);

    public A2_ProjectSteps() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    @Autowired
    private ITestServer          testServer;

    private RequestSpecification requestSpecification;

    @Given("A2 server initialized")
    public void init() {
        assertThatTestServerInitialized();
        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://" + testServer.getHost() + ":" + testServer.getPort() + "/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Then("data available")
    public void collectionResourceOK() {
        given().spec(requestSpecification).when().get("/projects").then().statusCode(200);
    }

    // ==================================================================================================================
    //                                   ASSERTIONS
    // ==================================================================================================================

    /**
     * assert test-server initialization
     */
    private void assertThatTestServerInitialized() {
        final int port = testServer.getPort();
        assertThat("A2 Server port is not as expected", port, greaterThan(9999));
        final String host = testServer.getHost();
        assertThat("A2 Server host is null", host, notNullValue());
        log.info("A2 server available");
    }

}
