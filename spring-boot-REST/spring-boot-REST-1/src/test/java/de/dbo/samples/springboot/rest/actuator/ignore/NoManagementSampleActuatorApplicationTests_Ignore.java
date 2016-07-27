package de.dbo.samples.springboot.rest.actuator.ignore;

import static org.junit.Assert.assertEquals;

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
 * Integration tests for switching off management endpoints.
 *
 * @author Dave Syer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SampleActuatorApplication.class)
@WebIntegrationTest(value = {"management.port=-1"}, randomPort = true)
@DirtiesContext
public class NoManagementSampleActuatorApplicationTests_Ignore {

    @Autowired
    private SecurityProperties security;

    @Value("${local.server.port}")
    private final int          port = 0;

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
    public void testMetricsNotAvailable() throws Exception {
        testHome(); // makes sure some requests have been made
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = new TestRestTemplate("user", getPassword())
                .getForEntity("http://localhost:" + this.port + "/metrics", Map.class);
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }

    private String getPassword() {
        return this.security.getUser().getPassword();
    }

}
