package de.dbo.samples.springboot.jbehave2.tests;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 *
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and
 *           only incidentally for computers to execute
 *
 */
//@SpringBootApplication(exclude = {ElasticsearchAutoConfiguration.class})
//@Configuration
//@EnableAutoConfiguration(exclude = {ElasticsearchDataAutoConfiguration.class})
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        final SpringApplicationBuilder builder = new SpringApplicationBuilder(TestApplication.class);
        builder.headless(false).run(args);
    }

}
