package de.dbo.samples.springboot.rest.test.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.util.Assert;

/**
 * Details of a single vehicle.
 */
public class VehicleDetails {

	private final String make;

	private final String model;

	@JsonCreator
	public VehicleDetails(@JsonProperty("make") String make,
			@JsonProperty("model") String model) {
		Assert.notNull(make, "Make must not be null");
		Assert.notNull(model, "Model must not be null");
		this.make = make;
		this.model = model;
	}

	public String getMake() {
		return this.make;
	}

	public String getModel() {
		return this.model;
	}

	@Override
	public int hashCode() {
		return this.make.hashCode() * 31 + this.model.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		VehicleDetails other = (VehicleDetails) obj;
		return this.make.equals(other.make) && this.model.equals(other.model);
	}

}
