
package de.dbo.samples.springboot.logback.additivityexample.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Engineer extends Employee {

	private static final Logger	LOGGER	= LoggerFactory.getLogger( "employee.director.manager.engineer" );

	public Engineer( final String name ) {
		super( name );

		LOGGER.info( "New Engineer is created. His/her name is : {}", super.name );
	}
}
