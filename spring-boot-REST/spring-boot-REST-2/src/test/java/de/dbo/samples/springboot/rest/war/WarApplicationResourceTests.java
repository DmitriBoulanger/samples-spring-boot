package de.dbo.samples.springboot.rest.war;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WarApplicationResourceTests {

	// gh-6371

	@Value("${demo.string.value}")
	private String demoStringValue;

	@Test
	public void contextLoads() {
		assertThat(this.demoStringValue).isEqualTo("demo");
	}

}
