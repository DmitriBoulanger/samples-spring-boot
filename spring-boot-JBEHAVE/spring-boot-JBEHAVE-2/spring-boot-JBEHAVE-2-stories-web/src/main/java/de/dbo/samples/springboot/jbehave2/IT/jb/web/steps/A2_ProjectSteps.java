package de.dbo.samples.springboot.jbehave2.IT.jb.web.steps;

import static com.jayway.restassured.RestAssured.given;

import static de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServerAssertions.assertThatTestServerInitialized;

import de.dbo.samples.springboot.jbehave2.IT.commons.context.ContextThreadLocal;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestServer;
import de.dbo.samples.springboot.jbehave2.IT.commons.stepsimpl.StepsBase;
import de.dbo.samples.springboot.jbehave2.IT.commons.stepsimpl.StepsBaseRestAssured;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
// SLF4J
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.config.LogConfig;
import com.jayway.restassured.filter.log.LogDetail;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;

@Component
public class A2_ProjectSteps extends StepsBaseRestAssured {
    private static final Logger log = LoggerFactory.getLogger(A2_ProjectSteps.class);
    
    @Autowired
    private TestServer  testServer;

    public A2_ProjectSteps() {
	LogConfig.logConfig().enablePrettyPrinting(false);
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    @Given("A2 server initialized")
    public void init() {
        assertThatTestServerInitialized(testServer);
        ctx().setRequestSpecification(requestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://" + testServer.getHost() + ":" + testServer.getPort() + "/")
                .addFilter(requestLoggingFiler())
                .addFilter(responseLoggingFiler())
                .build());
    }

    @Then("data available")
    public void collectionResourceOK() {
        given().spec(ctx().getRequestSpecification()).when().get("/projects").then().statusCode(200);
    }

    // ==================================================================================================================
    //                                   CONTEXT DATA
    // ==================================================================================================================
 
    private static A2_ProjectSteps_Data ctx() {
        return (A2_ProjectSteps_Data) ContextThreadLocal.contextData(A2_ProjectSteps_Data.class);
    }
    
  
}
