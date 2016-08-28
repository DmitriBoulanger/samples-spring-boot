package de.dbo.samples.springboot.data.elasticsearch.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
//
import de.dbo.samples.springboot.data.elasticsearch.client.service.DataOperation;
import de.dbo.samples.springboot.data.elasticsearch.client.service.DataOperationConfirmation;
import de.dbo.samples.springboot.data.elasticsearch.client.service.DataOperationIndex;
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
 * Unit-Test is designed to use embedded TOMACT-container and a STANDALONE Elasticsearch server.
 * The Elasticsearch server is configured in the test application.propertes.
 * However, the default test-configuration uses the embedded elasticsearch server.
 * Edit the the test application.propertes and start the corresponding standalone embedded elasticsearch server
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
    
    private static  final URI uri(final DataOperationType operation, final int port, final String index) {
        try {
            switch (operation) {

                case UPLOAD:
                    return new URI(baseUri(port) + DataOperationType.UPLOAD + "&" + INDEX_PARAMETER_NANE + "=" + index);

                case CLEANUP:
                    return new URI(baseUri(port) + DataOperationType.CLEANUP + "&" + INDEX_PARAMETER_NANE + "=" + index);

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
    public void test01_CleanUpDataAtElasticsearchServer() throws Exception {
	doCleanUpDataAtElasticsearchServer(DataOperationIndex.DEPARTMENT.name() );
        
    }
    
    @Test
    public void test02_CleanUpDataAtElasticsearchServer() throws Exception {
	doCleanUpDataAtElasticsearchServer(DataOperationIndex.CUSTOMERS.name() );
        
    }

    @Test
    public void test11_UploadDepartmentDataToElasticsearchServer() throws Exception {
	 doUloadCustomerDataToElasticsearchServer(DataOperationIndex.DEPARTMENT.name(), 4);
	
    }
    
    @Test
    public void test12_UploadCustomerDataToElasticsearchServer() throws Exception {
	 doUloadCustomerDataToElasticsearchServer(DataOperationIndex.CUSTOMERS.name(), 1);
	
    }
    
    // ========================
    // Implementations
    // ========================
    
    private void doCleanUpDataAtElasticsearchServer(final String index) throws Exception {
	 final URI uri = uri(DataOperationType.CLEANUP, port, index);
      
       log.info("request " + uri + " ...");
       final TestRestTemplate restTemplate = new TestRestTemplate();
	final ResponseEntity<DataOperationConfirmation> entity = restTemplate.getForEntity(uri, DataOperationConfirmation.class);
	final int actualCnt = (int )entity.getBody().getCnt();
       assertThatConfirmation(HttpStatus.OK,  entity.getStatusCode(), 
       	-1 /* unknown: since Elasticsearch server needs time to relax after index-deletion */, actualCnt );
   }
    
    private void doUloadCustomerDataToElasticsearchServer(final String index, int expectedCnt) throws Exception {
	 final URI uri = uri(DataOperationType.UPLOAD,port, index);
	 log.info("request " + uri + " ...");
	 final TestRestTemplate restTemplate = new TestRestTemplate();
        final ResponseEntity<DataOperationConfirmation> entity = restTemplate.getForEntity(uri, DataOperationConfirmation.class);
        final int actualCnt = (int )entity.getBody().getCnt();
        assertThatConfirmation(HttpStatus.OK,  entity.getStatusCode(), expectedCnt,  actualCnt);
    }

    // ========================
    // Test-specific assertions
    // ========================

    private static final void assertThatConfirmation(final HttpStatus expectedHttpStatus, final HttpStatus actualHttpStatus
	    , final int expectedCnt, final int actualCnt) {
        assertThat("HTTP response code is not as expected", actualHttpStatus, equalTo(expectedHttpStatus));
        assertThat("Repository counter is not as expected", actualCnt, equalTo(expectedCnt));
    }
 
}
