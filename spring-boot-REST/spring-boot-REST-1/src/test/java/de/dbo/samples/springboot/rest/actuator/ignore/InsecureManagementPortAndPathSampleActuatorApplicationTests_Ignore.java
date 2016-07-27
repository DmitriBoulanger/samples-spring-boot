package de.dbo.samples.springboot.rest.actuator.ignore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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
@WebIntegrationTest(value = {"management.port=0", "management.context-path=/admin",
        "management.security.enabled=false"}, randomPort = true)
@DirtiesContext
public class InsecureManagementPortAndPathSampleActuatorApplicationTests_Ignore {

    @Autowired
    private SecurityProperties security;

    @Value("${local.server.port}")
    private final int          port           = 9010;

    @Value("${local.management.port}")
    private final int          managementPort = 9011;

    @Test
    public void testHome() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = new TestRestTemplate("user", getPassword())
                .getForEntity("http://localhost:" + this.port, Map.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = entity.getBody();
        assertEquals("Hello Phil", body.get("message"));
    }

    @Test
    public void testMetrics() throws Exception {
        testHome(); // makes sure some requests have been made
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = new TestRestTemplate().getForEntity(
                "http://localhost:" + this.managementPort + "/admin/metrics", Map.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
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

    @Test
    public void testMissing() throws Exception {
        ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
                "http://localhost:" + this.managementPort + "/admin/missing",
                String.class);
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
        assertTrue("Wrong body: " + entity.getBody(),
                entity.getBody().contains("\"status\":404"));
    }

    private String getPassword() {
        return this.security.getUser().getPassword();
    }

}
