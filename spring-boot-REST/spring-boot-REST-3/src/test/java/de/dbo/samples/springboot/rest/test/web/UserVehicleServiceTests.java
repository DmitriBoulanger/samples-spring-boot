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

package de.dbo.samples.springboot.rest.test.web;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

import de.dbo.samples.springboot.rest.test.domain.User;
import de.dbo.samples.springboot.rest.test.domain.UserRepository;
import de.dbo.samples.springboot.rest.test.domain.VehicleIdentificationNumber;
import de.dbo.samples.springboot.rest.test.service.VehicleDetails;
import de.dbo.samples.springboot.rest.test.service.VehicleDetailsService;
import de.dbo.samples.springboot.rest.test.web.UserNameNotFoundException;
import de.dbo.samples.springboot.rest.test.web.UserVehicleService;

/**
 * Tests for {@link UserVehicleService}.
 *
 * @author Phillip Webb
 */
public class UserVehicleServiceTests {

	private static final VehicleIdentificationNumber VIN = new VehicleIdentificationNumber(
			"00000000000000000");

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private VehicleDetailsService vehicleDetailsService;

	@Mock
	private UserRepository userRepository;

	private UserVehicleService service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.service = new UserVehicleService(this.userRepository,
				this.vehicleDetailsService);
	}

	@Test
	public void getVehicleDetailsWhenUsernameIsNullShouldThrowException()
			throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Username must not be null");
		this.service.getVehicleDetails(null);
	}

	@Test
	public void getVehicleDetailsWhenUsernameNotFoundShouldThrowException()
			throws Exception {
		given(this.userRepository.findByUsername(anyString())).willReturn(null);
		this.thrown.expect(UserNameNotFoundException.class);
		this.service.getVehicleDetails("sboot");
	}

	@Test
	public void getVehicleDetailsShouldReturnMakeAndModel() throws Exception {
		given(this.userRepository.findByUsername(anyString()))
				.willReturn(new User("sboot", VIN));
		VehicleDetails details = new VehicleDetails("Honda", "Civic");
		given(this.vehicleDetailsService.getVehicleDetails(VIN)).willReturn(details);
		VehicleDetails actual = this.service.getVehicleDetails("sboot");
		assertThat(actual).isEqualTo(details);
	}

}
