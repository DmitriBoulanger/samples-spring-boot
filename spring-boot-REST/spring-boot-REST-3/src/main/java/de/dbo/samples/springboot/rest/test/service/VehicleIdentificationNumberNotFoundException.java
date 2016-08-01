package de.dbo.samples.springboot.rest.test.service;

import de.dbo.samples.springboot.rest.test.domain.VehicleIdentificationNumber;

/**
 * Exception thrown when a {@link VehicleIdentificationNumber} is not found.
 */
public class VehicleIdentificationNumberNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -3758262737421541933L;

    private VehicleIdentificationNumber vehicleIdentificationNumber;

    public VehicleIdentificationNumberNotFoundException(VehicleIdentificationNumber vin) {
	this(vin, null);
    }

    public VehicleIdentificationNumberNotFoundException(VehicleIdentificationNumber vin, Throwable cause) {
	super("Unable to find VehicleIdentificationNumber " + vin, cause);
	this.vehicleIdentificationNumber = vin;
    }

    public VehicleIdentificationNumber getVehicleIdentificationNumber() {
	return this.vehicleIdentificationNumber;
    }
}
