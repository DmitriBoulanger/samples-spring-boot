package de.dbo.samples.springboot.jbehave2.IT.steps;

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
import org.springframework.stereotype.Component;

import com.jayway.restassured.http.ContentType;

import de.dbo.samples.springboot.jbehave2.IT.commons.context.ContextThreadLocal;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestContainer;
import de.dbo.samples.springboot.jbehave2.IT.commons.stepsimpl.StepsBaseRestAssured;
import de.dbo.samples.springboot.jbehave2.IT.steps.ctx.A1_ShopperSteps_Data;
/* Application */
import de.dbo.samples.springboot.jbehave2.app1.domain.Shopper;

@Component
public class A1_ShopperSteps extends StepsBaseRestAssured {
    private static final Logger log = LoggerFactory.getLogger(A1_ShopperSteps.class);

    public A1_ShopperSteps() {
        log.info("created. HashCode=[" + hashCode() + "]");
    }

    @Given("A1 server initialized")
    public void init() {
        final TestContainer testServer = testServer("A1 server-clone");
        assertThatTestServerInitialized(testServer);
        ctx().setRequestSpecification(requestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(testServer.contextURL() + "/")
                .addFilter(requestLoggingFiler())
                .addFilter(responseLoggingFiler())
                .build());
    }

    @When("new shopper created")
    public void createShopper() {
        final String shopperName = "Test Shopper";
        final Shopper shopper = given()
                .spec(ctx().getRequestSpecification())
                .body("{\"name\":\"" + shopperName + "\"}")
                .when()
                .post("shoppers") // POST-Request
                .then()
                .statusCode(201)
                .extract()
                .as(Shopper.class);

        ctx().setShopper(shopper);

        assertThat("Shopper ID is null or empty string", ctx().getShopper().getId(), not(isEmptyOrNullString()));
        assertThat("Name of the found customer is not as expected", ctx().getShopper().getName(), equalTo((shopperName)));
    }

    @Then("created shopper found")
    public void foundShopper() {

        final Shopper newShoperClone = given()
                .spec(ctx().getRequestSpecification())
                .when()
                .get("shoppers/" + ctx().getShopper().getId())
                .then()
                .statusCode(200)
                .extract()
                .as(Shopper.class);

        assertThat("Found customer-clone is not the same as origin", ctx().getShopper(), equalTo(newShoperClone));
    }

    @Then("unknown shopper not found")
    public void testShopperNotFound() {
        given().spec(ctx().getRequestSpecification())
                .when()
                .get("shoppers/unknownID")
                .then()
                .statusCode(404);
    }

    // ==================================================================================================================
    //                                   ASSERTIONS
    // ==================================================================================================================

    /**
     * assert test-server initialization
     */
    private void assertThatTestServerInitialized(final TestContainer testServer) {
        final int port = testServer.port();
        assertThat("A1 Server port is not as expected", port, greaterThan(9999));
        final String host = testServer.host();
        assertThat("A1 Server host is null", host, notNullValue());
    }

    // ==================================================================================================================
    //                                   CONTEXT DATA
    // ==================================================================================================================

    private static A1_ShopperSteps_Data ctx() {
        return (A1_ShopperSteps_Data) ContextThreadLocal.contextData(A1_ShopperSteps_Data.class);
    }

}
