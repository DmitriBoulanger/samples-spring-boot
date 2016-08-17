package de.dbo.samples.springboot.jbehave2.tests.jb.steps;

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

import de.dbo.samples.springboot.jbehave2.tests.TestServer;

@Component
public class ProjectSteps {
    private static final Logger log = LoggerFactory.getLogger(ProjectSteps.class);

    public ProjectSteps() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    @Autowired
    private TestServer           testServer;

    private RequestSpecification requestSpecification;

    @Given("server initialized 2")
    public void init() {
        // assert
        final int port = testServer.getPort();
        assertThat("Server port is not as expected", port, greaterThan(9999));
        final String host = testServer.getHost();
        assertThat("Server host is null", host, notNullValue());
        if (log.isDebugEnabled()) {
            log.debug("test server initialized: " + testServer.print());
        }

        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://" + host + ":" + port + "/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Then("data available")
    public void collectionResourceOK() {
        given().spec(requestSpecification).when().get("/projects").then().statusCode(200);
    }

}
