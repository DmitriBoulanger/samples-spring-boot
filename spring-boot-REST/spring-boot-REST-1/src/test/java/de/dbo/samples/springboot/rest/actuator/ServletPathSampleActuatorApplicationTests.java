package de.dbo.samples.springboot.rest.actuator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

/**
 * Integration tests for endpoints configuration.
 *
 * @author Dave Syer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SampleActuatorApplication.class)
@WebIntegrationTest(value = {"server.servletPath=/spring"}, randomPort = true)
@DirtiesContext
public class ServletPathSampleActuatorApplicationTests {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void testErrorPath() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = new TestRestTemplate("user", "password")
                .getForEntity("http://localhost:" + this.port + "/spring/error",
                        Map.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, entity.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = entity.getBody();
        assertEquals("None", body.get("error"));
        assertEquals(999, body.get("status"));
    }

    @Test
    public void testHealth() throws Exception {
        ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
                "http://localhost:" + this.port + "/spring/health", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertTrue("Wrong body: " + entity.getBody(),
                entity.getBody().contains("\"status\":\"UP\""));
    }

    @Test
    public void testHomeIsSecure() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = new TestRestTemplate()
                .getForEntity("http://localhost:" + this.port + "/spring/", Map.class);
        assertEquals(HttpStatus.UNAUTHORIZED, entity.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = entity.getBody();
        assertEquals("Wrong body: " + body, "Unauthorized", body.get("error"));
        assertFalse("Wrong headers: " + entity.getHeaders(),
                entity.getHeaders().containsKey("Set-Cookie"));
    }

}
