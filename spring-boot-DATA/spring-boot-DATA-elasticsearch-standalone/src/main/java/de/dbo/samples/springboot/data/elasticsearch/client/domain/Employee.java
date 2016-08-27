package de.dbo.samples.springboot.data.elasticsearch.client.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Document(indexName = "department", type = "employees")
public class Employee {
    
    @Id
    private final String id;
    
    @Field( type = FieldType.Date)
    private Date created;
    
    private final String name;
    
    private Integer age;

    /* This type is recognized in Kibana! */
    @Field( type = FieldType.Nested)
    private List<Skill> skills;

    public Employee(){
	this.name = "Nobody";
	this.age = -1;
	this.id = UUID.randomUUID().toString();
    }

    public Employee(final String name, final int age) {
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

    public List<Skill> getSkills() {
	return skills;
    }

    public void setSkills(List<Skill> skills) {
	this.skills = skills;
    }

    public String toString() {
	return "Employee [(" + getId() + ", " + getName() + ", " + age + "), skills: " + getSkills() +", created: " + getCreated()+ "]";
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
