package de.dbo.samples.springboot.mvc.greeting.application;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


@SpringBootApplication
public class MyWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    private static final Logger log = LoggerFactory.getLogger(MyWebInitializer.class);

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { SpringWebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}
	
	 public static void main(String[] args) {
		final ConfigurableApplicationContext ctx = SpringApplication.run(MyWebInitializer.class, args);
		ctx.registerShutdownHook();
		log.info("...");
	    }

	    public MyWebInitializer() {
		log.info("created. HashCode=[" + hashCode() + "]");
	    }


}