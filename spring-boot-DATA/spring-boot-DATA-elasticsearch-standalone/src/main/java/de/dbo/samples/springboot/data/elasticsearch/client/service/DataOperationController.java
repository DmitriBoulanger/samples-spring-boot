package de.dbo.samples.springboot.data.elasticsearch.client.service;

import de.dbo.samples.springboot.data.elasticsearch.client.domain.Employee;
import de.dbo.samples.springboot.data.elasticsearch.client.domain.EmployeePrint;
import de.dbo.samples.springboot.data.elasticsearch.client.domain.EmployeeRepository;
import de.dbo.samples.springboot.data.elasticsearch.client.domain.EmployeeRepositoryIndexAndType;
import de.dbo.samples.springboot.data.elasticsearch.client.domain.Skill;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(DataOperation.CONTEXT)
@RestController
public class DataOperationController implements EmployeeRepositoryIndexAndType {
    private static final Logger log = LoggerFactory.getLogger(DataOperationController.class);

    private static final String CONFIRMATION_UPLOAD  = "Data uploaded. There are %s employees in the department";
    private static final String CONFIRMATION_CLEANUP = "Data cleaned-up";
    private static final String CONFIRMATION_ERROR   = "Data operation failure:  %s";

    private final AtomicLong    counter  = new AtomicLong();

    @Autowired
    private ApplicationContext  applicationContext;

    private EmployeeRepository          employeeRepository;

    private ElasticsearchTemplate  elasticsearchTemplate;

    public DataOperationController() {
	log.info("Data-Operation controller created");
    }

    @PostConstruct
    public void init() {
	employeeRepository = applicationContext.getBean(EmployeeRepository.class);
	elasticsearchTemplate = applicationContext.getBean(ElasticsearchTemplate.class);
	log.info("Data-Operation controller initialized");
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody DataOperationConfirmation dataOperationAtElasticsearchServer(
	    @RequestParam(value = "operation", required = false, defaultValue = "ERROR_NO_OPERATION_SPECIFIED") String operation) {

	log.info("running data-operation [" + operation + "] at Elasticsearch server ...");
	
	final DataOperationType operationType = operation(operation);
	switch(operationType) {

	case UPLOAD:
	    try {
		uploadDataToElasticsearchServer();
		final long cnt =  employeeRepository.count();
		return new DataOperationConfirmation(counter.incrementAndGet(),  String.format(CONFIRMATION_UPLOAD,cnt), cnt);
	    } catch (Exception e) {
		log.error("Failue while uploading data: ",e);
		return new DataOperationConfirmation(counter.incrementAndGet(),  String.format(CONFIRMATION_ERROR, e.toString()), -1);
	    }

	case CLEANUP:
	    try {
		cleanUpDataAtElasticsearchServer();
		return new DataOperationConfirmation(counter.incrementAndGet(), String.format(CONFIRMATION_CLEANUP), -1);
	    } catch (Exception e) {
		log.error("Failue while cleaning-up: ",e);
		return new DataOperationConfirmation(counter.incrementAndGet(),  String.format(CONFIRMATION_ERROR, e.toString()), -1);
	    }

	default:
	    return new DataOperationConfirmation(counter.incrementAndGet(), 
		    String.format(CONFIRMATION_ERROR, operationType.name()), -1);
	}
    }

    private static final DataOperationType operation(final String operation) {
	if (StringUtils.isEmpty(operation)) {
	    return DataOperationType.ERROR_NO_OPERATION_SPECIFIED;
	}
	DataOperationType ret = null;
	try {
	    ret = DataOperationType.valueOf(operation.toUpperCase());
	} catch (Exception e) {
	   log.warn("Incorrect value ["+operation+"] for " + DataOperationType.class.getName() + ": ",e);
	   ret = null;
	}
	
	if (null==ret) {
	    return DataOperationType.ERROR_UNKNOWN_OPERATION;
	}
	return ret;
    }

    private void uploadDataToElasticsearchServer() {
	log.info("uploadDataToServer() ...");
	addEmployees();
	findAllEmployees();
	findEmployeesByAge(32);
	findEmployee("John");
    }

    private void cleanUpDataAtElasticsearchServer() {
	if (!elasticsearchTemplate.indexExists(INDEX) ) {
	    log.info("index " + INDEX + " doesn't exist");
	}

	log.info("cleaning-up index " + INDEX + " ...");
	final boolean indexDeleted = elasticsearchTemplate.deleteIndex(INDEX);
	log.info("indexDeleted = " + indexDeleted);
	final boolean indexCreated = elasticsearchTemplate.createIndex(INDEX);
	log.info("indexCreated = " + indexCreated);

    }

    private void addEmployees() {

	log.info("addEmployees() ...");
	final Employee joe = new Employee("Joe", 32);
	final Skill javaSkill = new Skill("Java", 10);
	final Skill db = new Skill("Oracle", 5);
	joe.setCreated(new Date());
	joe.setSkills(Arrays.asList(javaSkill, db));

	final Employee johnS = new Employee("John S", 32);

	final Employee johnP = new Employee("John P", 42);

	final Employee sam = new Employee("Sam", 30);

	elasticsearchTemplate.putMapping(Employee.class);


	final IndexQuery indexQuery = new IndexQuery();
	indexQuery.setId(joe.getId());
	indexQuery.setObject(joe);
	elasticsearchTemplate.index(indexQuery);
	elasticsearchTemplate.refresh(Employee.class);


	johnS.setCreated(new Date());
	johnP.setCreated(new Date());
	sam.setCreated(new Date());
	employeeRepository.save(johnS);
	employeeRepository.save(johnP);
	employeeRepository.save(sam);
    }

    private void findAllEmployees() {
	log.info("Employee all:" + EmployeePrint.print(employeeRepository.findAll()));
    }

    private void findEmployee(String name) {
	log.info("Employee list by name ["+name+"]:" + EmployeePrint.print(employeeRepository.findEmployeesByName(name)));
    }

    private void findEmployeesByAge(int age) {
	log.info("Employee list by age [" + age + "]:"+ EmployeePrint.print(employeeRepository.findEmployeesByAge(age)));
    }

}