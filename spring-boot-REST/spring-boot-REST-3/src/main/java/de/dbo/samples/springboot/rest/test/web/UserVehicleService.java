package de.dbo.samples.springboot.rest.test.web;

import de.dbo.samples.springboot.rest.test.domain.User;
import de.dbo.samples.springboot.rest.test.domain.UserRepository;
import de.dbo.samples.springboot.rest.test.service.VehicleDetails;
import de.dbo.samples.springboot.rest.test.service.VehicleDetailsService;
import de.dbo.samples.springboot.rest.test.service.VehicleIdentificationNumberNotFoundException;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Controller service used to provide vehicle information for a given user.
 */
@Component
public class UserVehicleService {

    private final UserRepository userRepository;

    private final VehicleDetailsService vehicleDetailsService;

    public UserVehicleService(UserRepository userRepository, VehicleDetailsService vehicleDetailsService) {
	this.userRepository = userRepository;
	this.vehicleDetailsService = vehicleDetailsService;
    }

    public VehicleDetails getVehicleDetails(String username) 
	    throws UserNameNotFoundException, VehicleIdentificationNumberNotFoundException {
	Assert.notNull(username, "Username must not be null");
	User user = this.userRepository.findByUsername(username);
	if (user == null) {
	    throw new UserNameNotFoundException(username);
	}
	return this.vehicleDetailsService.getVehicleDetails(user.getVin());
    }

}
