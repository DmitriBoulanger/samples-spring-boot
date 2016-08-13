
package de.dbo.samples.springboot.logback.additivityexample.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Director extends Employee {

	private static final Logger	LOGGER	= LoggerFactory.getLogger( "employee.director" );

	public Director( final String name ) {
		super( name );

		LOGGER.info( "New Director is created. His/her name is : {}", super.name );
	}
}
