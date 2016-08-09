package XXXXXXXXXXXXXX.services.rcase;

import static com.jayway.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;

/**
 *
 * @author Dmitri Boulanger
 *
 * Programs are meant to be read by humans and only incidentally for computers to execute (D. Knuth)
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CaseServiceTest {

    @LocalServerPort
    private int                  port;

    private RequestSpecification spec;

    @Before
    public void initSpec() {
        spec = new RequestSpecBuilder().setContentType(ContentType.JSON).setBaseUri("http://localhost:" + port + "/")
                //                .addFilter(new ResponseLoggingFilter())
                //                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Test
    public void testBasic() {
        given().spec(spec).when().get("/cases").then().statusCode(200);
    }

}
