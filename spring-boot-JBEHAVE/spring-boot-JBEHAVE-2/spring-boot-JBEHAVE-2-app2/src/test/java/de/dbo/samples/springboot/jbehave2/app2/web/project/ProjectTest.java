package de.dbo.samples.springboot.jbehave2.app2.web.project;

import static com.jayway.restassured.RestAssured.given;

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

import de.dbo.samples.springboot.jbehave2.app2.web.Web2Configuration;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {Web2Configuration.class})
@EnableAutoConfiguration
@DirtiesContext
public class ProjectTest {

    @LocalServerPort
    private int                         port;

    private static RequestSpecification spec;

    @Before
    public void initSpec() {
        spec = new RequestSpecBuilder().setContentType(ContentType.JSON).setBaseUri("http://localhost:" + port + "/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter()).build();
    }

    @Test
    public void collectionResourceOK() {
        given().spec(spec).when().get("/projects").then().statusCode(200);
    }

}
