package de.dbo.samples.springboot.data.elasticsearch.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
//
import de.dbo.samples.springboot.data.elasticsearch.client.service.DataOperation;
import de.dbo.samples.springboot.data.elasticsearch.client.service.DataOperationConfirmation;
import de.dbo.samples.springboot.data.elasticsearch.client.service.DataOperationType;
//
import java.net.URI;
import java.net.URISyntaxException;
//
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
//
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit-Test uses embedded TOMACT-container and STANDALONE Elasticsearch server.
 *
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and  only incidentally for computers to execute
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {DeployApplication.class, DeployApplication.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UnitTest implements DataOperation {
    private static final Logger log = LoggerFactory.getLogger(UnitTest.class);
    
    private static final String HOST = "localhost";
    
    // ===========================================================================================================================================
    //   HELPERS
    // ===========================================================================================================================================
    
    private static final String baseUri(final int port) {
	return "http://" + HOST + ":" + port + CONTEXT + "?" + OPERATION_PARAMETER_NANE + "=";
    }
    
    private static  final URI uri(final DataOperationType operation, final int port) {
        try {
            switch (operation) {

                case UPLOAD:
                    return new URI(baseUri(port) + DataOperationType.UPLOAD);

                case CLEANUP:
                    return new URI(baseUri(port) + DataOperationType.CLEANUP);

                default:
                    return new URI(baseUri(port) + "unknow");
            }

        }
        catch(URISyntaxException e) {
            throw new IllegalStateException("Should never happen error: non-parsable hard-coded URI");
        }
    }

    @LocalServerPort
    private int port;

    @Test
    public void test00_CleanUpDataAtElasticsearchServer() throws Exception {
	 final URI uri = uri(DataOperationType.CLEANUP,port);
       
        log.info("request " + uri + " ...");
        final TestRestTemplate restTemplate = new TestRestTemplate();
	final ResponseEntity<DataOperationConfirmation> entity = restTemplate.getForEntity(uri, DataOperationConfirmation.class);
//        assertThatConfirmation(HttpStatus.OK, entity, -1);
    }

    @Test
    public void test01_UploadDataToElasticsearchServer() throws Exception {
	 final URI uri = uri(DataOperationType.UPLOAD,port);
	 log.info("request " + uri + " ...");
	 final TestRestTemplate restTemplate = new TestRestTemplate();
        final ResponseEntity<DataOperationConfirmation> entity = restTemplate.getForEntity(uri, DataOperationConfirmation.class);
//        assertThatConfirmation(HttpStatus.OK, entity, 4);
        
        
       
    }

    // ========================
    // Test-specific assertions
    // ========================

    private static final void assertThatConfirmation(final HttpStatus expectedHttpStatus, final ResponseEntity<DataOperationConfirmation> entity, final int expectedCnt) {
        final HttpStatus responseHttpStatus = entity.getStatusCode();
        assertThat("HTTP response code is not as expected", expectedHttpStatus, equalTo(responseHttpStatus));
        assertThat("Repository counter is not as expected", expectedCnt, equalTo(entity.getBody().getCnt()));
    }
 


}
