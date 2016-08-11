
package de.ityx.response.services.rcase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CaseServiceApplication {

    //    @Bean
    //    @ConditionalOnProperty(name = "populate", havingValue = "true", matchIfMissing = false)
    //    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {
    //        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
    //        factory.setResources(new Resource[]{new ClassPathResource("data/cases.json")});
    //        return factory;
    //    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CaseServiceTestApplication.class, args);
    }

}
