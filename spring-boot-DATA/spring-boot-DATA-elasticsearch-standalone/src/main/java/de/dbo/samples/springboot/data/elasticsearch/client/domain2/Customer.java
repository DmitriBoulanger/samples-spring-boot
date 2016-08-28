package de.dbo.samples.springboot.data.elasticsearch.client.domain2;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Document(indexName = CustomerRepositoryIndexAndType.INDEX, type = CustomerRepositoryIndexAndType.TYPE)
public class Customer {
    
    @Id
    private final String id;
    
    @Field( type = FieldType.Date)
    private Date created;
    
    private final String name;
    
    private Integer age;

   
    public Customer(){
	this.name = "Nobody";
	this.age = -1;
	this.id = UUID.randomUUID().toString();
    }

    public Customer(final String name, final int age) {
	this.name = name;
	this.age = age;
	this.id = UUID.randomUUID().toString();
    }

    public String getId() {
	return id;
    }


    public String getName() {
	return name;
    }


    public Integer getAge() {
	return age;
    }

    public void setAge(Integer age) {
	this.age = age;
    }	

    

    public String toString() {
	return "Customer [(" + getId() + ", " + getName() + ", " + age + "), created: " + getCreated()+ "]";
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
