package de.dbo.samples.springboot.jbehave2.test.app2;

import static com.jayway.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {JUnitTestApplication.class})
@EnableAutoConfiguration
@DirtiesContext
public class JUnitTest {

    @Autowired
    private JUnitTestServer             testServer;

    private static RequestSpecification spec;

    @Before
    public void initSpec() {
        spec = new RequestSpecBuilder().setContentType(ContentType.JSON).setBaseUri("http://" + testServer.getHost() + ":" + testServer.getPort() + "/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter()).build();
    }

    @Test
    public void collectionResourceOK() {
        given().spec(spec).when().get("/projects").then().statusCode(200);
    }

}
