package de.dbo.samples.springboot.rest.test.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.util.Assert;

/**
 * A user of the system.
 */
@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String username;

	private VehicleIdentificationNumber vin;

	protected User() {
	}

	public User(String username, VehicleIdentificationNumber vin) {
		Assert.hasLength(username, "Username must not be empty");
		Assert.notNull(vin, "VIN must not be null");
		this.username = username;
		this.vin = vin;
	}

	public Long getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	public VehicleIdentificationNumber getVin() {
		return this.vin;
	}

}
