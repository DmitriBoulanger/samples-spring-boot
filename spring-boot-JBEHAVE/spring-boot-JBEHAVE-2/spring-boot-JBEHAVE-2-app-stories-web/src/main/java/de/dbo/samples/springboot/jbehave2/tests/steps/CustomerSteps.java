package de.dbo.samples.springboot.jbehave2.tests.steps;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
/* Hamcrest */
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.*;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
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

import de.dbo.samples.springboot.jbehave2.app.web.customer.Customer;
import de.dbo.samples.springboot.jbehave2.tests.TestServer;

@Component
public class CustomerSteps {
    private static final Logger log = LoggerFactory.getLogger(CustomerSteps.class);

    public CustomerSteps() {
        log.info("created");
    }

    @Autowired
    private TestServer           testServer;

    private RequestSpecification requestSpecification;

    @When("server initialized")
    public void init() {
        final int port = testServer.getPort();
        assertThat("Server port is not as expected", port, greaterThan(9999));
        final String host = testServer.getHost();
        assertThat("Server host is null", host ,notNullValue());
        log.debug("test server initialized: " + testServer.print());
        
        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://"+host +":" + port + "/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Then("customer created")
    public void createCustomer() {
        String customerName = "Testcustomer";

        final Customer newCustomer = given()
                .spec(requestSpecification)
                .body("{\"name\":\"" + customerName + "\"}")
                .when()
                .post("customers")
                .then()
                .statusCode(201)
                .extract().as(Customer.class);

        assertThat("Customer ID is null or empty string", newCustomer.getId(), not(isEmptyOrNullString()));
        assertThat("Name of the found customer is not as expected", newCustomer.getName(), equalTo((customerName)));

        // get the created customer
        final Customer newCustomer2 = given()
                .spec(requestSpecification)
                .when()
                .get("customers/" + newCustomer.getId())
                .then()
                .statusCode(200)
                .extract().as(Customer.class);

        assertThat("Found customer is not the same as just created",  newCustomer, equalTo(newCustomer2));
    }

    @Then("unknown customer not found")
    public void testCustomerNotFound() {
        given()
                .spec(requestSpecification)
                .when()
                .get("customers/unknownID")
                .then()
                .statusCode(404);
    }

}
