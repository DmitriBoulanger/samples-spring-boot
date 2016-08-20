package de.dbo.samples.springboot.jbehave2.IT.web;

import de.dbo.samples.springboot.jbehave2.IT.commons.jbehaveruntime.JBehaveRunnerDefault;

/* JUnit */
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ITestApplication has a lot of moving parts
 * so it made sense to wrap some of the operations in an integration test session.
 *
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and
 *           only incidentally for computers to execute
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class ITest extends JBehaveRunnerDefault {
    
    public ITest() {

    }
}
