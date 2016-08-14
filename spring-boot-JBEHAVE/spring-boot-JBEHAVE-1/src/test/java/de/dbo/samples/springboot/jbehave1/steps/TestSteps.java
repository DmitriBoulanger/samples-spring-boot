package de.dbo.samples.springboot.jbehave1.steps;

import de.dbo.samples.springboot.jbehave1.IntegrationTestSession;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
@Component
public class TestSteps {
    private static final Logger log = LoggerFactory.getLogger(TestSteps.class);

    @Autowired
    private ApplicationContext applicationContext;

    private IntegrationTestSession testSession;
    private int result;

    @Given("a variable x with value $value")
    public void givenXValue(@Named("value") int value) {
	log.info("givenXValue "  + value + " ...");
        testSession = applicationContext.getBean(IntegrationTestSession.class);
        testSession.setX(value);
    }

    @When("I multiply x by $value")
    public void whenImultiplyXBy(@Named("value") int value) {
	log.info("whenImultiplyXBy "  + value + " ...");
        result = testSession.multiply(value);
    }

    @Then("result should equal $value")
    public void thenXshouldBe(@Named("value") int value) {
	log.info("thenXshouldBe "  + value + " ...");
        if (value != result)
            throw new RuntimeException("result is " + result + ", but should be " + value);
    }
}
