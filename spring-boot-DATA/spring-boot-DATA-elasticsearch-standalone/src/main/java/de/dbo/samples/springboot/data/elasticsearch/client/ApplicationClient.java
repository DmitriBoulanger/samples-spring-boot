package de.dbo.samples.springboot.data.elasticsearch.client;

import de.dbo.samples.springboot.data.elasticsearch.client.domain.Employee;
import de.dbo.samples.springboot.data.elasticsearch.client.domain.EmployeeRepository;
import de.dbo.samples.springboot.data.elasticsearch.client.domain.Skill;

import java.util.Arrays;

import javax.annotation.PostConstruct;

/* SLF4J */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/* Spring */
import org.springframework.beans.factory.annotation.Autowired;
/* Spring-Data */
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Component;

@Component
public class ApplicationClient {
    private static final Logger log = LoggerFactory.getLogger(ApplicationClient.class);

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    
    @Autowired
    private EmployeeRepository repository;

    public ApplicationClient() {
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
	findEmployeesByAge(32);
	findEmployee("John");
    }

    public void addEmployees() {
	
	log.info("addEmployees() ...");
	final Employee joe = new Employee("01", "Joe", 32);
	final Skill javaSkill = new Skill("Java", 10);
	final Skill db = new Skill("Oracle", 5);
	joe.setSkills(Arrays.asList(javaSkill, db));
	
	final Employee johnS = new Employee("02", "John S", 32);
	
	final Employee johnP = new Employee("03", "John P", 42);
	
	final Employee sam = new Employee("04", "Sam", 30);

	elasticsearchTemplate.putMapping(Employee.class);
	
	final IndexQuery indexQuery = new IndexQuery();
	indexQuery.setId(joe.getId());
	indexQuery.setObject(joe);
	elasticsearchTemplate.index(indexQuery);
	elasticsearchTemplate.refresh(Employee.class);
	
	repository.save(johnS);
	repository.save(johnP);
	repository.save(sam);
    }

    public void findAllEmployees() {
	log.info("\nEmployee all: ");
	repository.findAll().forEach(x -> log.info("\n\t - " + x.toString()));
    }

    public void findEmployee(String name) {
	log.info("\nEmployee list by name ["+name+"]:");
	repository.findEmployeesByName(name).forEach(x -> log.info("\n\t - " + x.toString()));
	
    }

    public void findEmployeesByAge(int age) {
	log.info("\nEmployee list by age [" + age + "]:");
	repository.findEmployeesByAge(age).forEach(x -> log.info("\n\t - " + x.toString()));
    }

}
