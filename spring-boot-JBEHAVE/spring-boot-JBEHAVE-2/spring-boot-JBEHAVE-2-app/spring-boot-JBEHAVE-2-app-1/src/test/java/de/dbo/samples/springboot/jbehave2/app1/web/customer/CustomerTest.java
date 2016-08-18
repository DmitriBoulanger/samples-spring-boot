package de.dbo.samples.springboot.jbehave2.app1.web.customer;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import de.dbo.samples.springboot.jbehave2.app1.domain.Customer;
import de.dbo.samples.springboot.jbehave2.app1.web.WebConfiguration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {WebConfiguration.class})
@EnableAutoConfiguration
@DirtiesContext
public class CustomerTest {

    @LocalServerPort
    private int                  port;

    private RequestSpecification spec;

    @Before
    public void initSpec() {
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://localhost:" + port + "/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Test
    public void testCreateCustomer() {
        String customerName = "Testcustomer";

        //create new customer
        Customer newCustomer = given()
                .spec(spec)
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
        Customer newCustomer2 = given()
                .spec(spec)
                .when()
                .get("customers/" + newCustomer.getId())
                .then()
                .statusCode(200)
                .extract().as(Customer.class);

        assertThat(newCustomer.equals(newCustomer2));
    }

    @Test
    public void testCustomerNotFound() {
        given()
                .spec(spec)
                .when()
                .get("customers/unknownID")
                .then()
                .statusCode(404);
    }

}
