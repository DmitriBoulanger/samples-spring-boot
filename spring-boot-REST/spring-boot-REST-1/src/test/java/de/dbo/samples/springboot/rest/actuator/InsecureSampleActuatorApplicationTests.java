package de.dbo.samples.springboot.rest.actuator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
 * Integration tests for insecured service endpoints (even with Spring Security on
 * classpath).
 *
 * @author Dave Syer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SampleActuatorApplication.class)
@WebIntegrationTest(value = {"security.basic.enabled:false"}, randomPort = true)
@DirtiesContext
public class InsecureSampleActuatorApplicationTests {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void testHome() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = new TestRestTemplate()
                .getForEntity("http://localhost:" + this.port, Map.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = entity.getBody();
        assertEquals("Hello Phil", body.get("message"));
        assertFalse("Wrong headers: " + entity.getHeaders(),
                entity.getHeaders().containsKey("Set-Cookie"));
    }

}
