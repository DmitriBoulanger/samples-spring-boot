package de.dbo.samples.springboot.data.elasticsearch.client;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration()
@EnableElasticsearchRepositories(basePackages = "com.javacodegeeks.spring.elasticsearch.domain")
public class ApplicationConfiguration {
    
//	@Autowired
//	private EmployeeRepository repository;
//
//	@Autowired
//	private ElasticsearchTemplate elasticsearchTemplate;
//
//	public EmployeeRepository getRepository() {
//	    return repository;
//	}
//
//	public ElasticsearchTemplate getElasticsearchTemplate() {
//	    return elasticsearchTemplate;
//	}
	
}
