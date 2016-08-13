
package de.dbo.samples.springboot.logback.additivityexample.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Manager extends Employee {

	private static final Logger	LOGGER	= LoggerFactory.getLogger( "employee.director.manager" );

	public Manager( final String name ) {
		super( name );

		LOGGER.info( "New Manager is created. His/her name is : {}", super.name );
	}
}
