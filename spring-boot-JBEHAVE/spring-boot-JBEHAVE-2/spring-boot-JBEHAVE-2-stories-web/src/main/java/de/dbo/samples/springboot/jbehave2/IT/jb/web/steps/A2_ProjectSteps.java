package de.dbo.samples.springboot.jbehave2.IT.jb.web.steps;

import static com.jayway.restassured.RestAssured.given;

import static de.dbo.samples.springboot.jbehave2.IT.commons.TestServerAssertions.assertThatTestServerInitialized;

import de.dbo.samples.springboot.jbehave2.IT.commons.TestServer;

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

@Component
public class A2_ProjectSteps {
    private static final Logger log = LoggerFactory.getLogger(A2_ProjectSteps.class);

    public A2_ProjectSteps() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    @Autowired
    private TestServer          testServer;

    private RequestSpecification requestSpecification;

    @Given("A2 server initialized")
    public void init() {
        assertThatTestServerInitialized(testServer);
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

  
}
