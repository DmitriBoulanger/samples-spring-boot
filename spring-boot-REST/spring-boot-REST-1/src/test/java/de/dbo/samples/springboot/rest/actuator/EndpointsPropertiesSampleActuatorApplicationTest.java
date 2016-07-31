package de.dbo.samples.springboot.rest.actuator;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for endpoints configuration.
 *
 * @author Dave Syer
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
@ActiveProfiles("endpoints")
public class EndpointsPropertiesSampleActuatorApplicationTest {

	@Autowired
	private SecurityProperties security;

	@LocalServerPort
	private int port;

	@Test
	public void testCustomErrorPath() throws Exception {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = new TestRestTemplate("user", getPassword())
				.getForEntity("http://localhost:" + this.port + "/oops", Map.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		@SuppressWarnings("unchecked")
		Map<String, Object> body = entity.getBody();
		assertThat(body.get("error")).isEqualTo("None");
		assertThat(body.get("status")).isEqualTo(999);
	}

	@Test
	public void testCustomContextPath() throws Exception {
		ResponseEntity<String> entity = new TestRestTemplate("user", getPassword())
				.getForEntity("http://localhost:" + this.port + "/admin/health",
						String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("\"status\":\"UP\"");
		assertThat(entity.getBody()).contains("\"hello\":\"world\"");
	}

	private String getPassword() {
		return this.security.getUser().getPassword();
	}

}