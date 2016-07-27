package de.dbo.samples.springboot.rest.actuator;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration tests for endpoints configuration.
 *
 * @author Dave Syer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SampleActuatorApplication.class)
@WebIntegrationTest(randomPort = true)
@DirtiesContext
@ActiveProfiles("endpoints")
public class EndpointsPropertiesSampleActuatorApplicationTests {

    @Autowired
    private SecurityProperties security;

    @Value("${local.server.port}")
    private int                port;

    @Test
    public void testCustomErrorPath() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = new TestRestTemplate("user", getPassword())
                .getForEntity("http://localhost:" + this.port + "/oops", Map.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, entity.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = entity.getBody();
        assertEquals("None", body.get("error"));
        assertEquals(999, body.get("status"));
    }

    @Test
    public void testCustomContextPath() throws Exception {
        ResponseEntity<String> entity = new TestRestTemplate("user", getPassword())
                .getForEntity("http://localhost:" + this.port + "/admin/health",
                        String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertTrue("Wrong body: " + entity.getBody(),
                entity.getBody().contains("\"status\":\"UP\""));
        System.err.println(entity.getBody());
        assertTrue("Wrong body: " + entity.getBody(),
                entity.getBody().contains("\"hello\":\"world\""));
    }

    private String getPassword() {
        return this.security.getUser().getPassword();
    }

}
