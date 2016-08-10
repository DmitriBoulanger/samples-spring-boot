package de.dbo.samples.test.springboot.data.elasticsearch.datagenerator;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {ElasticsearchAutoConfiguration.class, ElasticsearchDataAutoConfiguration.class, ElasticsearchRepositoriesAutoConfiguration.class})
public class Main {

    //    @Bean(name="customerIDs")
    //    public String[] customerIDs(){
    //        String[] a = new String[1000];
    //        for (int i = 0; i < a.length; i++) {
    //            a[i] = RandomUtil.randomID();
    //        }   
    //        return a;
    //    }

    @Bean(name = "mimeTypes")
    public String[] mimeTypes() {
        return new String[]{"text/plain", "message/rfc822", "image/tiff", "image/jpeg"};
    }

    @Bean(name = "projectIDs")
    public UUID[] projectIDs() {
        UUID[] a = new UUID[200];
        for (int i = 0; i < a.length; i++) {
            a[i] = UUID.randomUUID();
        }
        return a;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
