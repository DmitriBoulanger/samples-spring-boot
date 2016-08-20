package de.dbo.samples.springboot.jbehave2.IT.java;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"de.dbo.samples.springboot.jbehave2.IT.commons"
	
        /* Steps-classes for all Applications to be integrated */
        ,"de.dbo.samples.springboot.jbehave2.IT.jb.java"
        
        /* Application 1 -- only non-WEB resources */
        , "de.dbo.samples.springboot.jbehave2.app1.domain", "de.dbo.samples.springboot.jbehave2.app1.infrastructure"

})
public class ITestApplicationConfiguration {

}
