package com.javacodegeeks.spring.elasticsearch;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

@Configuration
public class SpringElasticsearchExample {
	@Autowired
	private EmployeeRepository repository;

	@Autowired
	private ElasticsearchTemplate template;

	public static void main(String[] args) throws URISyntaxException, Exception {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		try {
			System.out.println("Load context");
			SpringElasticsearchExample s = (SpringElasticsearchExample) ctx
					.getBean("mainBean");
			System.out.println("Add employees");
			s.addEmployees();
			System.out.println("Find all employees");
			s.findAllEmployees();
			System.out.println("Find employee by name 'Joe'");
			s.findEmployee("Joe");
			System.out.println("Find employee by name 'John'");
			s.findEmployee("John");
			System.out.println("Find employees by age");
			s.findEmployeesByAge(32);
		} finally {
			ctx.close();
		}
	}

	public void addEmployees() {
		Employee joe = new Employee("01", "Joe", 32);
		Skill javaSkill = new Skill("Java", 10);
		Skill db = new Skill("Oracle", 5);
		joe.setSkills(Arrays.asList(javaSkill, db));
		Employee johnS = new Employee("02", "John S", 32);
		Employee johnP = new Employee("03", "John P", 42);
		Employee sam = new Employee("04", "Sam", 30);

		template.putMapping(Employee.class);
		IndexQuery indexQuery = new IndexQuery();
		indexQuery.setId(joe.getId());
		indexQuery.setObject(joe);
		template.index(indexQuery);
		template.refresh(Employee.class);
		repository.save(johnS);
		repository.save(johnP);
		repository.save(sam);
	}

	public void findAllEmployees() {
		repository.findAll().forEach(System.out::println);
	}

	public void findEmployee(String name) {
		List<Employee> empList = repository.findEmployeesByName(name);
		System.out.println("Employee list: " + empList);
	}

	public void findEmployeesByAge(int age) {
		List<Employee> empList = repository.findEmployeesByAge(age);
		System.out.println("Employee list: " + empList);
	}
}
