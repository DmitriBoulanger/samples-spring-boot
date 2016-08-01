package de.dbo.samples.springboot.rest.test.domain;

import org.springframework.util.Assert;

/**
 * A Vehicle Identification Number.
 */
public final class VehicleIdentificationNumber {

	private String vin;

	public VehicleIdentificationNumber(String vin) {
		Assert.notNull(vin, "VIN must not be null");
		Assert.isTrue(vin.length() == 17, "VIN must be exactly 17 characters");
		this.vin = vin;
	}

	@Override
	public int hashCode() {
		return this.vin.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		return this.vin.equals(((VehicleIdentificationNumber) obj).vin);
	}

	@Override
	public String toString() {
		return this.vin;
	}

}
