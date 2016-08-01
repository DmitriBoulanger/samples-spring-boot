package de.dbo.samples.springboot.rest.test.service;

import de.dbo.samples.springboot.rest.test.domain.VehicleIdentificationNumber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * {@link VehicleDetailsService} backed by a remote REST service.
 *
 */
@Service
public class RemoteVehicleDetailsService implements VehicleDetailsService {
    private static final Logger log = LoggerFactory.getLogger(RemoteVehicleDetailsService.class);

    private final RestTemplate restTemplate;

    public RemoteVehicleDetailsService(ServiceProperties properties, RestTemplateBuilder restTemplateBuilder) {
	this.restTemplate = restTemplateBuilder.rootUri(properties.getVehicleServiceRootUrl()).build();
    }

    @Override
    public VehicleDetails getVehicleDetails(VehicleIdentificationNumber vin) throws VehicleIdentificationNumberNotFoundException {
	Assert.notNull(vin, "VIN must not be null");
	log.debug("Retrieving vehicle data for: " + vin);
	try {
	    return this.restTemplate.getForObject("/vehicle/{vin}/details",  VehicleDetails.class, vin);
	}
	catch (HttpStatusCodeException ex) {
	    if (HttpStatus.NOT_FOUND.equals(ex.getStatusCode())) {
		throw new VehicleIdentificationNumberNotFoundException(vin, ex);
	    }
	    throw ex;
	}
    }

}
