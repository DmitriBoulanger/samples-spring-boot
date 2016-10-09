package de.dbo.samples.springboot.microservices.it;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.with;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;

/**
 * Integration test for Micro-Services.
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Microservices_IT {
    private static final Logger LOG = LoggerFactory.getLogger(Microservices_IT.class);
    
    private static final String DOCKER_HTTP_DEFAULT = "http://192.168.99.100:";
    
    /**
     * 
     * @param systemProperty that is possibly set in the Maven-plugin
     * @param defaultPort default port of the service
     * @return base HTTP URL of the service
     */
    private static final String url(final String systemProperty, final int defaultPort) {
	 final String url;
	if (null!=System.getProperty(systemProperty)) {
            url = System.getProperty(systemProperty);
        } else {
            url = DOCKER_HTTP_DEFAULT + defaultPort;
        }
	return url;
    }
    
    private static final String getResponseAsString() {
	 try {
	    return  given().param("mimeType", "application/json")
	             .get()
	             .then().statusCode(200)
	             .extract().asString();
	} catch (Exception e) {
	   assertThat("Can't get response from ["+ RestAssured.baseURI+"]: " + e.toString(), e, nullValue());
	   return null;
	}  
    }
    
    //
    // First level
    //
    
    @Test
    public void t00_discovery() {
	final String url = url("discovery.url", 8761) + "/eureka/apps";
	LOG.info("Discovery URL: " + url + "...");
        RestAssured.baseURI = url;
        RestAssured.defaultParser = Parser.XML;

       final String data = getResponseAsString();
       assertThat("Eureka application-data is empty or null", data, not(isEmptyOrNullString()));
       LOG.info("Discovery available");
       final XmlPath xml = new XmlPath(data);
       xml.prettyPrint();
       LOG.info("Eureka applications:\n" + xml.prettyPrint());
    }
    
    @Test
    public void t01_hystrix() {
	final String url = url("hystrix.url", 7979);
	LOG.info("Hystrix URL: " + url + "...");
        RestAssured.baseURI = url;
        RestAssured.defaultParser = Parser.JSON;
        final String dashboard =  getResponseAsString();
        assertThat("Hystrix dashboard is empty or null", dashboard, not(isEmptyOrNullString()));
        LOG.info("Hystrix available");
    }
    
    //
    // Second level
    //
    
    @Test
    public void t02_configuration() {
	final String url = url("configuration.url", 8888);
	LOG.info("Configuration URL: " + url + "...");
        RestAssured.baseURI = url;
        RestAssured.defaultParser = Parser.JSON;

       final String data =  getResponseAsString();
       assertThat("Configuration is empty or null", data, not(isEmptyOrNullString()));
       LOG.info("Configuration available");
    }
    
    
    //
    // Third level
    //
    
    @Test
    public void t12_users() {
	final String url = url("users.url", 9000);
	LOG.info("Users URL: " + url + "...");
        RestAssured.baseURI = url;
        RestAssured.defaultParser = Parser.JSON;

       final String response =  getResponseAsString();  
       assertThat("Users response is empty or null", response, not(isEmptyOrNullString()));
       LOG.info("Users available");
    }
    
    @Test
    public void t11_moviesui() {
	final String url = url("moviesui.url", 9006);
	LOG.info("Moviesui URL: " + url + "...");
        RestAssured.baseURI = url;
        RestAssured.defaultParser = Parser.JSON;

       final String response =  getResponseAsString(); 
       assertThat("Movies UI response is empty or null", response, not(isEmptyOrNullString()));
       LOG.info("Moviesui available");
    }
    
    @Test
    public void t10_movie() {
	final String url = url("movie.url", 9005);
	LOG.info("Movie URL: " + url + "...");
        RestAssured.baseURI = url;
        RestAssured.defaultParser = Parser.JSON;

       final String response = getResponseAsString(); 
       assertThat("Movie response is empty or null", response, not(isEmptyOrNullString()));
       LOG.info("Movie response available");
    }
    
    //
    // Last level
    //
    
    @Test
    public void t98_recomendation() {
	final String url = url("recomendation.url", 9003);
	LOG.info("Recomendation URL: " + url + "...");
        RestAssured.baseURI = url;
        RestAssured.defaultParser = Parser.JSON;
        final String response = getResponseAsString();
        assertThat("Recomendation response is empty or null", response, not(isEmptyOrNullString()));
        LOG.info("Recomendation available");
    }
    
    @Test
    public void t99_gateway() {
	final String url = url("geteway.url", 10000) + "/health";
	LOG.info("Gateway URL: " + url + "...");
        RestAssured.baseURI = url;
        RestAssured.defaultParser = Parser.JSON;
        
        final String data = getResponseAsString(); 
        assertThat("Gateway health-data is empty or null", data, not(isEmptyOrNullString()));
        LOG.info("Gateway available");
       
        final JsonPath json = with(data);
        LOG.info("Microservices health:\n" + json.prettyPrint());
    }

}
