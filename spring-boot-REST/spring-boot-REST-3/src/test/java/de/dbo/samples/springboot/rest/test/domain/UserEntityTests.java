package de.dbo.samples.springboot.rest.test.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import de.dbo.samples.springboot.rest.test.domain.User;
import de.dbo.samples.springboot.rest.test.domain.VehicleIdentificationNumber;

/**
 * Data JPA tests for {@link User}.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserEntityTests {

	private static final VehicleIdentificationNumber VIN = new VehicleIdentificationNumber("00000000000000000");

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void createWhenUsernameIsNullShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Username must not be empty");
		new User(null, VIN);
	}

	@Test
	public void createWhenUsernameIsEmptyShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Username must not be empty");
		new User("", VIN);
	}

	@Test
	public void createWhenVinIsNullShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("VIN must not be null");
		new User("sboot", null);
	}

	@Test
	public void saveShouldPersistData() throws Exception {
		User user = this.entityManager.persistFlushFind(new User("sboot", VIN));
		assertThat(user.getUsername()).isEqualTo("sboot");
		assertThat(user.getVin()).isEqualTo(VIN);
	}

}
