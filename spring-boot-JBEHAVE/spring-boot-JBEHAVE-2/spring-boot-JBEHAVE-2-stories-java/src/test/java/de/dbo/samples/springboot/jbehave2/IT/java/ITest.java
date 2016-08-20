package de.dbo.samples.springboot.jbehave2.IT.java;


import de.dbo.samples.springboot.jbehave2.IT.commons.configuration.DefaultJBehaveConfiguration;
import de.dbo.samples.springboot.jbehave2.IT.commons.stories.StoriesProvider;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;

/* JBehave */
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.PrintStreamStepdocReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.core.steps.spring.SpringStepsFactory;
/* JUnit */
import org.junit.runner.RunWith;
/* SLF4J */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/* Spring */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
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
@SpringBootTest(classes={ITestApplication.class})
public class ITest extends JUnitStories {
    private static final Logger log = LoggerFactory.getLogger(ITestApplication.class);

    @Autowired
    private ApplicationContext  applicationContext;

    public ITest() {
        log.info("created");
    }

    // =====================================================================================================================================
    //                                   JBEHAVE CONFIGURATION
    // =====================================================================================================================================

    @PostConstruct
    public void init() {
	useConfiguration(applicationContext.getBean(DefaultJBehaveConfiguration.class));
        configuredEmbedder().embedderControls().useThreads(10);
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new SpringStepsFactory(configuration(), applicationContext);
    }

    @Override
    protected List<String> storyPaths() {
	return applicationContext.getBean(StoriesProvider.class).defaultStoryPaths(this.getClass());
    }
}
