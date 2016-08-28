package de.dbo.samples.springboot.data.elasticsearch.client.service.data;

import de.dbo.samples.springboot.data.elasticsearch.client.domain.Employee;
import de.dbo.samples.springboot.data.elasticsearch.client.domain.EmployeeRepository;
import de.dbo.samples.springboot.data.elasticsearch.client.domain.EmployeeRepositoryIndexAndType;
import de.dbo.samples.springboot.data.elasticsearch.client.domain.Skill;
import de.dbo.samples.springboot.data.elasticsearch.client.domain2.Customer;
import de.dbo.samples.springboot.data.elasticsearch.client.domain2.CustomerRepository;

import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Component;

@Component
public class DataGenerator implements EmployeeRepositoryIndexAndType {
    private static final Logger log = LoggerFactory.getLogger(DataGenerator.class);

    @Autowired
    private ApplicationContext  applicationContext;

    private EmployeeRepository          employeeRepository;
    private CustomerRepository          customerRepository;

    private ElasticsearchTemplate  elasticsearchTemplate;

    public DataGenerator() {
	log.info("Data-Generator created");
    }

    @PostConstruct
    public void init() {
	employeeRepository = applicationContext.getBean(EmployeeRepository.class);
	customerRepository = applicationContext.getBean(CustomerRepository.class);
	elasticsearchTemplate = applicationContext.getBean(ElasticsearchTemplate.class);
	log.info("Data-Generator initialized");
    }

    public void addEmployees() {

	log.info("addEmployees() ...");
	elasticsearchTemplate.putMapping(Employee.class);
	
	final Employee joe = new Employee("Joe", 32);
	final Employee johnS = new Employee("John S", 32);
	final Employee johnP = new Employee("John P", 42);
	final Employee sam = new Employee("Sam", 30);
	
	joe.setCreated(new Date());
	johnS.setCreated(new Date());
	johnP.setCreated(new Date());
	sam.setCreated(new Date());
	
	final Skill javaSkill = new Skill("Java", 10);
	final Skill db = new Skill("Oracle", 5);
	joe.setSkills(Arrays.asList(javaSkill, db));
	final IndexQuery indexQuery = new IndexQuery();
	indexQuery.setId(joe.getId());
	indexQuery.setObject(joe);
	elasticsearchTemplate.index(indexQuery);
	elasticsearchTemplate.refresh(Employee.class);

	employeeRepository.save(johnS);
	employeeRepository.save(johnP);
	employeeRepository.save(sam);
    }

    public void addCustomers() {
	
 	log.info("addCustomers() ...");
 	elasticsearchTemplate.putMapping(Customer.class);
 	
 	final Customer bob = new Customer("Bob W", 66);
 	bob.setCreated(new Date());
 	
 	elasticsearchTemplate.refresh(Customer.class);

 	customerRepository.save(bob);
     }

}