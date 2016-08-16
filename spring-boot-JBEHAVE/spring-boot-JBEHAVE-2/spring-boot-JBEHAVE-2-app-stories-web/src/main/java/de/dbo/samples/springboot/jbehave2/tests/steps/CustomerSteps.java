package de.dbo.samples.springboot.jbehave2.tests.steps;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import org.jbehave.core.annotations.Given;
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

    @Given("server initialized")
    public void init() {
        int port = testServer.getPort();
        assertThat("Server port is not as expected", port, greaterThan(1));
        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://localhost:" + port + "/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Given("customer created")
    public void createCustomer() {
        String customerName = "Testcustomer";

        //create new customer
        final Customer newCustomer = given()
                .spec(requestSpecification)
                .body("{\"name\":\"" + customerName + "\"}")
                .when()
                .post("customers")
                .then()
                .statusCode(201)
                .extract().as(Customer.class);

        assertThat(newCustomer.getId() != null);
        assertThat(!newCustomer.getId().isEmpty());
        assertThat(newCustomer.getName().equals(customerName));

        //get the created customer
        final Customer newCustomer2 = given()
                .spec(requestSpecification)
                .when()
                .get("customers/" + newCustomer.getId())
                .then()
                .statusCode(200)
                .extract().as(Customer.class);

        assertThat(newCustomer.equals(newCustomer2));
    }

    @Given("unknown customer not found")
    public void testCustomerNotFound() {
        given()
                .spec(requestSpecification)
                .when()
                .get("customers/unknownID")
                .then()
                .statusCode(404);
    }

}
