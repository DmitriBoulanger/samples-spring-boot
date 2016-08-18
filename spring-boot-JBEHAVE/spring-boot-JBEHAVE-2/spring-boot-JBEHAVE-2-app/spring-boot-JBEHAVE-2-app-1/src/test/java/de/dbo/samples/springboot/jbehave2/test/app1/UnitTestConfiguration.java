package de.dbo.samples.springboot.jbehave2.test.app1;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"de.dbo.samples.springboot.jbehave2.app1"})
public class UnitTestConfiguration {

    //    @Bean
    //    public UnitTestServer testServer() {
    //        return new UnitTestServer();
    //    }
}
