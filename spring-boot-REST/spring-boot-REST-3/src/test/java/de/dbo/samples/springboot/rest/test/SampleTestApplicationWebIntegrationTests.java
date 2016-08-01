/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.dbo.samples.springboot.rest.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.given;

import de.dbo.samples.springboot.rest.test.SampleTestApplication;
import de.dbo.samples.springboot.rest.test.domain.VehicleIdentificationNumber;
import de.dbo.samples.springboot.rest.test.service.VehicleDetails;
import de.dbo.samples.springboot.rest.test.service.VehicleDetailsService;

/**
 * {@code @SpringBootTest} with a random port for {@link SampleTestApplication}.
 *
 * @author Phillip Webb
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class SampleTestApplicationWebIntegrationTests {

	private static final VehicleIdentificationNumber VIN = new VehicleIdentificationNumber(
			"01234567890123456");

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private VehicleDetailsService vehicleDetailsService;

	@Before
	public void setup() {
		given(this.vehicleDetailsService.getVehicleDetails(VIN))
				.willReturn(new VehicleDetails("Honda", "Civic"));
	}

	@Test
	public void test() {
		this.restTemplate.getForEntity("/{username}/vehicle", String.class, "sframework");
	}

}
