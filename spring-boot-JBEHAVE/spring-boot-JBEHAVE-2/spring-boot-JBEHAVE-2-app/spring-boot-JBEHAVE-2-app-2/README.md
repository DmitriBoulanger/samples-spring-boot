Application 2 - To be Integrated and Tested
===========================================

**Special features**

The project is a WAR-project. Therefore, its maven war-plug-in has the following item in its configuration

				<configuration>
					<!-- Very important to access classes from other Maven projects!                       -->
					<!-- This artifact with classifier "classes" is deployed to the local maven-repository -->
					<attachClasses>true</attachClasses>
					<failOnMissingWebXml>false</failOnMissingWebXml>
				</configuration>

The above allows other maven projects to have dependencies on this project (typically WAR-projects cannot appear as dependencies in other maven-projects).

 An integration-test project (or any other) has to refer this project as follows:

    		<dependency>
    				<groupId>de.dbo.samples.springboot</groupId>
    				<artifactId>spring-boot-JBEHAVE-2-app-2</artifactId>
    				<version>0.0.0-SNAPSHOT</version>
    				<classifier>classes</classifier>
    		</dependency>


