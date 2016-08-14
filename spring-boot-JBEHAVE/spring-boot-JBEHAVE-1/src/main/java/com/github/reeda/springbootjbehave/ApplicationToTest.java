package com.github.reeda.springbootjbehave;

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
@SpringBootApplication
public class ApplicationToTest {

    public static void main(String[] args) {
        final SpringApplicationBuilder builder = new SpringApplicationBuilder(ApplicationToTest.class);
        builder.headless(false).run(args);
    }

}
