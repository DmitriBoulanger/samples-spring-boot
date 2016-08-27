package com.javacodegeeks.spring.elasticsearch;


import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.elasticsearch.common.settings.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import com.javacodegeeks.spring.elasticsearch.domain.Employee;
import com.javacodegeeks.spring.elasticsearch.domain.EmployeeRepository;
import com.javacodegeeks.spring.elasticsearch.domain.Skill;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
	final ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
	ctx.registerShutdownHook();
    }

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    
    @Autowired
    private EmployeeRepository repository;

    public Application() {
	log.info("created");
    }

    @PostConstruct 
    public void init() {
	log.info("init() ...");
	run();
    }
   
    public void run() {
	log.info("run() ...");
	addEmployees();
	findAllEmployees();
    }

    public void addEmployees() {
	
	log.info("addEmployees() ...");
	Employee joe = new Employee("01", "Joe", 32);
	Skill javaSkill = new Skill("Java", 10);
	Skill db = new Skill("Oracle", 5);
	joe.setSkills(Arrays.asList(javaSkill, db));
	Employee johnS = new Employee("02", "John S", 32);
	Employee johnP = new Employee("03", "John P", 42);
	Employee sam = new Employee("04", "Sam", 30);

	elasticsearchTemplate.putMapping(Employee.class);
	IndexQuery indexQuery = new IndexQuery();
	indexQuery.setId(joe.getId());
	indexQuery.setObject(joe);
	elasticsearchTemplate.index(indexQuery);
	elasticsearchTemplate.refresh(Employee.class);
	repository.save(johnS);
	repository.save(johnP);
	repository.save(sam);
    }

    public void findAllEmployees() {
	log.info("findAllEmployees() ...");
	repository.findAll().forEach(System.out::println);
    }

    public void findEmployee(String name) {
	List<Employee> empList = repository.findEmployeesByName(name);
	log.info("Employee list: " + empList);
    }

    public void findEmployeesByAge(int age) {
	List<Employee> empList = repository.findEmployeesByAge(age);
	log.info("Employee list: " + empList);
    }

}
