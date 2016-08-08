package de.dbo.samples.springboot.data.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SampleElasticsearchApplication implements CommandLineRunner {

	@Autowired
	private SampleDataLoader sampleDataLoader;

	@Override
	public void run(String... args) throws Exception {
	        sampleDataLoader.clean();
	        sampleDataLoader.saveCustomers();
		sampleDataLoader.showAllCustomers();
		sampleDataLoader.showIndividualCustomers();
	}

	public static void main(String[] args) throws Exception {
		final ConfigurableApplicationContext ctx = SpringApplication.run(SampleElasticsearchApplication.class, "--debug"); 
//		Thread.sleep(300000);
		ctx.close();
	
	}

}
