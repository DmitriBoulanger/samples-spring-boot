package com.opencredo.demo.hateoas.api;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.specification.RequestSpecification;
import com.opencredo.demo.hateoas.ApplicationLauncher;
import com.opencredo.demo.hateoas.domain.persistence.AuthorRepository;
import com.opencredo.demo.hateoas.domain.persistence.BookRepository;
import com.opencredo.demo.hateoas.domain.persistence.PublisherRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={ApplicationLauncher.class}, webEnvironment=WebEnvironment.RANDOM_PORT)  // Use a random free port
@DirtiesContext // Avoid Spring caching contexts
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class AbstractControllerTest {
    protected static final String CONTEXT_PATH = "/library-application";

    @LocalServerPort
    int port;
   
   @Autowired
   BookRepository bookRepository;
   
   @Autowired
   AuthorRepository authorRepository;
   
   @Autowired
   PublisherRepository publisherRepository;
   
   protected RequestSpecification spec;
   
   @PostConstruct
   public void init() {
       spec =  new RequestSpecBuilder()
	       .setBaseUri("http://localhost:" + port + CONTEXT_PATH)
	       .addFilter(new RequestLoggingFilter())
	       .addFilter(new ResponseLoggingFilter())
	       .build();
   }

}
