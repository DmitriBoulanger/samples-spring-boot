package de.dbo.samples.springboot.hal.browser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class ApplicationLauncher extends SpringBootServletInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationLauncher.class);
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicationLauncher.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApplicationLauncher.class, args);
       
    }
    
    @Bean 
    public static X getX() {
	return new X();
	
    }

}
