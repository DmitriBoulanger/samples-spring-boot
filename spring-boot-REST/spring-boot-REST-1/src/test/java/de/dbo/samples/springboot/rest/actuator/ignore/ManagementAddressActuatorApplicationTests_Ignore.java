package de.dbo.samples.springboot.rest.actuator.ignore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.dbo.samples.springboot.rest.actuator.SampleActuatorApplication;

/**
 * Integration tests for separate management and main service ports.
 *
 * @author Dave Syer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SampleActuatorApplication.class)
@WebIntegrationTest(value = {"management.port=0", "management.address=127.0.0.1",
        "management.context-path:/admin"}, randomPort = true)
@DirtiesContext
public class ManagementAddressActuatorApplicationTests_Ignore {

    @Value("${local.server.port}")
    private final int port           = 9010;

    @Value("${local.management.port}")
    private final int managementPort = 9011;

    @Test
    public void testHome() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = new TestRestTemplate()
                .getForEntity("http://localhost:" + this.port, Map.class);
        assertEquals(HttpStatus.UNAUTHORIZED, entity.getStatusCode());
    }

    @Test
    public void testHealth() throws Exception {
        ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
                "http://localhost:" + this.managementPort + "/admin/health",
                String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertTrue("Wrong body: " + entity.getBody(),
                entity.getBody().contains("\"status\":\"UP\""));
    }

}
