package de.dbo.samples.springboot.jbehave2.IT.web;

import de.dbo.samples.springboot.jbehave2.IT.commons.jbehave.runtime.JBehaveRunnerDefault;

import java.util.List;
/* JUnit */
import org.junit.runner.RunWith;
/* Spring-Boot */
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Default Integration Test Runner
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and only incidentally for computers to execute
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ITest extends JBehaveRunnerDefault {
    
    public ITest() {
	super(ITest.class);
    }
    
    @Override
    protected final List<String> storyPaths() {
	return defaultStoryPaths();
    }
}
