package de.dbo.samples.springboot.test.mvc.greeting;

import com.jayway.restassured.RestAssured;

import de.dbo.samples.springboot.mvc.greeting.mvc.controller.AjaxController;
import de.dbo.samples.springboot.mvc.greeting.mvc.model.AjaxResponseBody;
import de.dbo.samples.springboot.mvc.greeting.mvc.model.SearchCriteria;

/* JUnit */
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/* SL4J */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* Spring-Boot */
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/* Test - Rest Assured */
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.ExtractableResponse;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT, classes={GreetingTestApplication.class} )
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GreetingTest {
    private static final Logger LOG = LoggerFactory.getLogger(AjaxController.class);

    @LocalServerPort
    int port = 8080;

    private String host = "localhost";

    private final String contextPath = "/spring-boot-mvc-ajax";
    private final String searchResult = "/search/api/getSearchResult";
    private final String open = "/";

    private  RequestSpecification requestSpecification;

    @Before
    public void initSpec() {
	requestSpecification = new RequestSpecBuilder()
		.setContentType(ContentType.JSON)
		.setBaseUri("http://" + host + ":" + port)
		.addFilter(new ResponseLoggingFilter())
		.addFilter(new RequestLoggingFilter())
		.build();
    }

    /**
     * test the reply from the context-path.
     * It is the welcome-page with the search-form
     * 
     * @throws Exception
     */
    @Test
    public void test00_Welcome() throws Exception{
	final ExtractableResponse<Response> welcomePage =  
		RestAssured.given().spec(requestSpecification)
			.basePath(contextPath)
			.when()
			.get(open)
			.then()
			.statusCode(200)
			.extract();
	
	LOG.info("welcome: " + welcomePage.getClass().getName());
	LOG.info("welcome: " + welcomePage);
	
	// .. parse the welcomePage! It is a HTML!
    }

    @Test
    public void test10_Search() throws Exception{

	final SearchCriteria searchCriteria = new SearchCriteria();
	searchCriteria.setUsername("dima");

	final AjaxResponseBody ajaxResponseBody = 
		RestAssured.given().spec(requestSpecification)
			.basePath(contextPath)
			.body(searchCriteria)
			.when()
			.post(searchResult)
			.then()
			.statusCode(200)
			.extract()
			.as(AjaxResponseBody.class);

	LOG.info("search ajax-response: " + ajaxResponseBody);
    }

}

