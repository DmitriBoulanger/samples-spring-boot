package de.dbo.samples.springboot.jbehave2.IT.jb.web.steps;

import static com.jayway.restassured.RestAssured.given;
/* Hamcrest */
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/* JBehave */
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
/* SLF4J */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/* Spring */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/* Jayway */
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;

import de.dbo.samples.springboot.jbehave2.IT.web.ITestServer;
import de.dbo.samples.springboot.jbehave2.app1.domain.Shoper;

@Component
public class A1_ShoperSteps {
    private static final Logger log = LoggerFactory.getLogger(A1_ShoperSteps.class);

    public A1_ShoperSteps() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    @Autowired
    private ITestServer         testServer;

    private RequestSpecification requestSpecification;

    @Given("A1 server initialized")
    public void init() {
        assertThatTestServerInitialized();
        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://" + testServer.getHost() + ":" + testServer.getPort() + "/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    private Shoper newCustomer;

    @When("new shoper created")
    public void createShoper() {
        final String shoperName = "Test Shoper";
        newCustomer = given()
                .spec(requestSpecification)
                .body("{\"name\":\"" + shoperName + "\"}")
                .when()
                .post("shopers")
                .then()
                .statusCode(201)
                .extract().as(Shoper.class);

        assertThat("Shoper ID is null or empty string", newCustomer.getId(), not(isEmptyOrNullString()));
        assertThat("Name of the found customer is not as expected", newCustomer.getName(), equalTo((shoperName)));
    }

    @Then("created shoper found")
    public void foundCustomer() {

        final Shoper newShoperClone = given()
                .spec(requestSpecification)
                .when()
                .get("shopers/" + newCustomer.getId())
                .then()
                .statusCode(200)
                .extract().as(Shoper.class);

        assertThat("Found customer-clone is not the same as origin", newCustomer, equalTo(newShoperClone));
    }

    @Then("unknown shoper not found")
    public void testShoperNotFound() {
        given()
                .spec(requestSpecification)
                .when()
                .get("shopers/unknownID")
                .then()
                .statusCode(404);
    }

    // ==================================================================================================================
    //                                   ASSERTIONS
    // ==================================================================================================================

    /**
     * assert test-server initialization
     */
    private void assertThatTestServerInitialized() {
        final int port = testServer.getPort();
        assertThat("A1 Server port is not as expected", port, greaterThan(9999));
        final String host = testServer.getHost();
        assertThat("A1 Server host is null", host, notNullValue());
    }
}
