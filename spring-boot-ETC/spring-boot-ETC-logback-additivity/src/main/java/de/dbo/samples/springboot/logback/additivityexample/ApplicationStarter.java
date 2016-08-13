
package de.dbo.samples.springboot.logback.additivityexample;

import de.dbo.samples.springboot.logback.additivityexample.model.Director;
import de.dbo.samples.springboot.logback.additivityexample.model.Employee;
import de.dbo.samples.springboot.logback.additivityexample.model.Engineer;
import de.dbo.samples.springboot.logback.additivityexample.model.Intern;
import de.dbo.samples.springboot.logback.additivityexample.model.Manager;

public class ApplicationStarter {

	@SuppressWarnings( "unused" )
	public static void main( final String[] args ) {

		final Employee director = new Director( "Ali" );

		final Employee manager = new Manager( "Susan" );

		final Employee engineer = new Engineer( "Abony" );

		final Employee intern = new Intern( "Mehmet" );
	}
}
