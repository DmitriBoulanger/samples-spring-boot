package com.javacodegeeks.spring.elasticsearch;

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
