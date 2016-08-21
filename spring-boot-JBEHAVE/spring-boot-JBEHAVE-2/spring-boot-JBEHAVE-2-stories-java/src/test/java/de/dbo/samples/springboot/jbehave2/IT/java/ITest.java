package de.dbo.samples.springboot.jbehave2.IT.java;

import de.dbo.samples.springboot.jbehave2.IT.commons.jbehaveruntime.JBehaveRunnerDefault;

import java.util.List;

/* JUnit */
import org.junit.runner.RunWith;
/* Spring-Boot */
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={ITestApplication.class})
public class ITest extends JBehaveRunnerDefault {
    
    public ITest() {
	super(ITest.class);
    }
    
    @Override
    protected final List<String> storyPaths() {
	return defaultStoryPaths();
    }
}

