package de.dbo.samples.springboot.rest.test.domain;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import de.dbo.samples.springboot.rest.test.domain.User;
import de.dbo.samples.springboot.rest.test.domain.UserRepository;
import de.dbo.samples.springboot.rest.test.domain.VehicleIdentificationNumber;

/**
 * Tests for {@link UserRepository}.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTests {

	private static final VehicleIdentificationNumber VIN = new VehicleIdentificationNumber("00000000000000000");

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository repository;

	@Test
	public void findByUsernameShouldReturnUser() throws Exception {
		this.entityManager.persist(new User("sboot", VIN));
		User user = this.repository.findByUsername("sboot");
		assertThat(user.getUsername()).isEqualTo("sboot");
		assertThat(user.getVin()).isEqualTo(VIN);
	}

	@Test
	public void findByUsernameWhenNoUserShouldReturnNull() throws Exception {
		this.entityManager.persist(new User("sboot", VIN));
		User user = this.repository.findByUsername("mmouse");
		assertThat(user).isNull();
	}

}
