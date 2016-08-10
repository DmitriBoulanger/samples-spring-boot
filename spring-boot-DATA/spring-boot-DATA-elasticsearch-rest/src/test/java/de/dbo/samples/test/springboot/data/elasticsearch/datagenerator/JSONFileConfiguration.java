package de.dbo.samples.test.springboot.data.elasticsearch.datagenerator;

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.dbo.samples.springboot.data.elasticsearch.domain.Customer;

@Configuration
@ConditionalOnProperty(name = "dummy.store", havingValue = "json")
public class JSONFileConfiguration {

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.configure(Feature.AUTO_CLOSE_TARGET, false);
        return objectMapper;
    }

    @Bean
    public JsonFactory factory() {
        return new JsonFactory(mapper())
                .configure(Feature.AUTO_CLOSE_JSON_CONTENT, true)
                .configure(Feature.AUTO_CLOSE_TARGET, false);
    }

    @Bean
    public Store<Customer> customerStore() throws IOException {
        JsonGenerator generator = factory().createGenerator(new FileOutputStream("target/data/customer.data"));
        return new JSONStore<>(generator);
    }

}
