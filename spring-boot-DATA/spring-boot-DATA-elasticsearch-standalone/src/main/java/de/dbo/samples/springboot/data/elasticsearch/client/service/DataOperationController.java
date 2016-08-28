package de.dbo.samples.springboot.data.elasticsearch.client.service;

import de.dbo.samples.springboot.data.elasticsearch.client.domain.Employee;
import de.dbo.samples.springboot.data.elasticsearch.client.domain.EmployeePrint;
import de.dbo.samples.springboot.data.elasticsearch.client.domain.EmployeeRepository;
import de.dbo.samples.springboot.data.elasticsearch.client.domain.EmployeeRepositoryIndexAndType;
import de.dbo.samples.springboot.data.elasticsearch.client.domain.Skill;
import de.dbo.samples.springboot.data.elasticsearch.client.domain2.Customer;
import de.dbo.samples.springboot.data.elasticsearch.client.domain2.CustomerPrint;
import de.dbo.samples.springboot.data.elasticsearch.client.domain2.CustomerRepository;
import de.dbo.samples.springboot.data.elasticsearch.client.service.data.DataGenerator;

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

    private static final String CONFIRMATION_UPLOAD  = "Data uploaded. There are %s employees in %s";
    private static final String CONFIRMATION_CLEANUP = "Data cleaned-up for %s";
    private static final String CONFIRMATION_ERROR   = "Data operation failure:  %s";

    private final AtomicLong    counter  = new AtomicLong();

    @Autowired
    private ApplicationContext  applicationContext;

    private EmployeeRepository          employeeRepository;
    
    private CustomerRepository          customerRepository;

    private ElasticsearchTemplate  elasticsearchTemplate;
    
    private DataGenerator dataGenerator;

    public DataOperationController() {
	log.info("Data-Operation controller created");
    }

    @PostConstruct
    public void init() {
	employeeRepository = applicationContext.getBean(EmployeeRepository.class);
	customerRepository = applicationContext.getBean(CustomerRepository.class);
	elasticsearchTemplate = applicationContext.getBean(ElasticsearchTemplate.class);
	dataGenerator = applicationContext.getBean(DataGenerator.class);
	log.info("Data-Operation controller initialized");
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody DataOperationConfirmation dataOperationAtElasticsearchServer(
	    @RequestParam(value = "index", required = false, defaultValue = "ERROR_NO_INDEX_SPECIFIED") String index,
	    @RequestParam(value = "operation", required = false, defaultValue = "ERROR_NO_OPERATION_SPECIFIED") String operation) {

	log.info("running data-operation [" + operation + "] for index ["+index+"] +  at Elasticsearch server ...");
	
	final DataOperationIndex indexType = index(index);
	
	switch(indexType) {
	
		case ERROR_NO_INDEX_SPECIFIED:
		    
		case ERROR_UNKNOWN_INDEX:
		    return new DataOperationConfirmation(counter.incrementAndGet(), 
			    String.format(CONFIRMATION_ERROR, indexType.name()), -1);
		    
		default:
	
	}
	
	final DataOperationType operationType = operation(operation);
	
	switch(operationType) {

	case UPLOAD:
	    try {
		final long cnt = uploadDataToElasticsearchServer(indexType);;
		return new DataOperationConfirmation(counter.incrementAndGet(),  String.format(CONFIRMATION_UPLOAD,cnt,index), cnt);
	    } catch (Exception e) {
		log.error("Failue while uploading data: ",e);
		return new DataOperationConfirmation(counter.incrementAndGet(),  String.format(CONFIRMATION_ERROR, e.toString()), -1);
	    }

	case CLEANUP:
	    try {
		cleanUpDataAtElasticsearchServer(index);
		return new DataOperationConfirmation(counter.incrementAndGet(), String.format(CONFIRMATION_CLEANUP,index), -1);
	    } catch (Exception e) {
		log.error("Failue while cleaning-up: ",e);
		return new DataOperationConfirmation(counter.incrementAndGet(),  String.format(CONFIRMATION_ERROR, e.toString()), -1);
	    }

	default:
	    return new DataOperationConfirmation(counter.incrementAndGet(), 
		    String.format(CONFIRMATION_ERROR, operationType.name()), -1);
	}
    }
    
    private static final DataOperationIndex index(final String index) {
 	if (StringUtils.isEmpty(index)) {
 	    return DataOperationIndex.ERROR_NO_INDEX_SPECIFIED;
 	}
 	DataOperationIndex ret = null;
 	try {
 	    ret = DataOperationIndex.valueOf(index.toUpperCase());
 	} catch (Exception e) {
 	   log.warn("Incorrect value ["+index+"] for " + DataOperationIndex.class.getName() + ": ",e);
 	   ret = null;
 	}
 	
 	if (null==ret) {
 	    return DataOperationIndex.ERROR_UNKNOWN_INDEX;
 	}
 	return ret;
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

    private long uploadDataToElasticsearchServer(final DataOperationIndex index) {
	log.info("uploadDataToServer() for index=["+ index +"]  ...");
	switch(index) {
	
	case DEPARTMENT:
	    dataGenerator.addEmployees();
	    findAllEmployees();
	    findEmployeesByAge(32);
	    findEmployee("John");
	    return employeeRepository.count();

	case CUSTOMERS: 
	    dataGenerator.addCustomers();
	    findAllCustomers();
	    findCustomersByAge(66);
	    findCustomer("Bob");
	    return customerRepository.count();

	default: 

	    return -1;
	}
    }

    private void cleanUpDataAtElasticsearchServer(final String index) {
	final String indexLowerCase = index.toLowerCase();
	if (!elasticsearchTemplate.indexExists(indexLowerCase) ) {
	    log.info("index " + index + " doesn't exist");
	}

	log.info("cleaning-up index " + index + " ...");
	final boolean indexDeleted = elasticsearchTemplate.deleteIndex(indexLowerCase);
	log.info("index ["+index+"] deleted = " + indexDeleted);
	final boolean indexCreated = elasticsearchTemplate.createIndex(indexLowerCase);
	log.info("index ["+index+"] created = " + indexCreated);

    }
   
    // =====================================================================================================
    // Department
    // =====================================================================================================
    
    private void findAllEmployees() {
	log.info("Employee all:" + EmployeePrint.print(employeeRepository.findAll()));
    }
    
    private void findEmployeesByAge(int age) {
   	log.info("Employee list by age [" + age + "]:"+ EmployeePrint.print(employeeRepository.findEmployeesByAge(age)));
       }

    private void findEmployee(String name) {
	log.info("Employee list by name ["+name+"]:" + EmployeePrint.print(employeeRepository.findEmployeesByName(name)));
    }
    
    // =====================================================================================================
    // Customers
    // =====================================================================================================
    
    
    private void findAllCustomers() {
	log.info("Customers all:" + CustomerPrint.print(customerRepository.findAll()));
    }

    private void findCustomersByAge(int age) {
	log.info("Customer list by age [" + age + "]:"+ CustomerPrint.print(customerRepository.findCustomersByAge(age)));
    }

    private void findCustomer(String name) {
	log.info("Customer list by name ["+name+"]:" + CustomerPrint.print(customerRepository.findCustomersByName(name)));
    }

   
    

}