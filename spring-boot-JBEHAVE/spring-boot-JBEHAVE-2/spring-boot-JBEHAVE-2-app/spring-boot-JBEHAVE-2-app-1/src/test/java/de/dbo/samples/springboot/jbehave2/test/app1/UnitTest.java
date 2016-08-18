package de.dbo.samples.springboot.jbehave2.test.app1;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/* JUnit */
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
/* Spring-Boot */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

/* Jayway REST */
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;

/* App1 Domain*/
import de.dbo.samples.springboot.jbehave2.app1.domain.Shoper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {UnitTestApplication.class})
@DirtiesContext
public class UnitTest {

    @Autowired
    private UnitTestServer      testServer;

    private RequestSpecification spec;

    @Before
    public void initSpec() {
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://" + testServer.getHost() + ":" + testServer.getPort() + "/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Test
    public void testCreateShoper() {
        final String shoperName = "Test Shoper";

        //create new customer
        Shoper newShoper = given()
                .spec(spec)
                .body("{\"name\":\"" + shoperName + "\"}")
                .when()
                .post("shopers")
                .then()
                .statusCode(201)
                .extract().as(Shoper.class);

        assertThat(newShoper.getId() != null);
        assertThat(!newShoper.getId().isEmpty());
        assertThat(newShoper.getName().equals(shoperName));

        //get the created customer
        Shoper newCustomer2 = given()
                .spec(spec)
                .when()
                .get("shopers/" + newShoper.getId())
                .then()
                .statusCode(200)
                .extract().as(Shoper.class);

        assertThat(newShoper.equals(newCustomer2));
    }

    @Test
    public void testShoperNotFound() {
        given()
                .spec(spec)
                .when()
                .get("shopers/unknownID")
                .then()
                .statusCode(404);
    }

}
