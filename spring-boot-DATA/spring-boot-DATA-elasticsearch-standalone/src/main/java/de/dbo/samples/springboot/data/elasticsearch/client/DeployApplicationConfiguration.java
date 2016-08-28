package de.dbo.samples.springboot.data.elasticsearch.client;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration()
@EnableElasticsearchRepositories(basePackages = {
	  "de.dbo.samples.springboot.data.elasticsearch.client.domain"
	, "de.dbo.samples.springboot.data.elasticsearch.client.domain2"})
public class DeployApplicationConfiguration {
    	
}
