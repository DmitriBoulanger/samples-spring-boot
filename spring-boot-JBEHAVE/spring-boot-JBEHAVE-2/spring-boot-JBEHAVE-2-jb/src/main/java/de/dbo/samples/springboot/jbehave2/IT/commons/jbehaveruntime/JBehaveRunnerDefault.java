package de.dbo.samples.springboot.jbehave2.IT.commons.jbehaveruntime;

import de.dbo.samples.springboot.jbehave2.IT.commons.configuration.ConfigurationJBehaveDefault;
import de.dbo.samples.springboot.jbehave2.IT.commons.configuration.ConfigurationJBehaveProperties;
import de.dbo.samples.springboot.jbehave2.IT.commons.stories.StoriesProvider;

import java.util.List;

import javax.annotation.PostConstruct;

import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


/**
 * JUnit-runnable entry-point to run multiple stories that are 
 * specified in a sub-class implementing {@link JUnitStories#storyPaths()}.
 * Stories are expected to reside in the test-resource.
 * The method should return a list with complete resource paths for all needed stories
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */

public abstract class JBehaveRunnerDefault extends JUnitStories {
    private static final Logger log = LoggerFactory.getLogger(JBehaveRunnerDefault.class);

    @Autowired
    protected ApplicationContext applicationContext;

    @Autowired
    protected ConfigurationJBehaveDefault jbehaveDefaultConfiguration;

    @Autowired
    protected StoriesProvider jbehaveStoriesProvider;

    @Autowired
    protected ConfigurationJBehaveProperties jbehaveProperties;

    private boolean initilizationDone = false;
    
    private final Class<?> locationClass;

    /**
     * 
     * @param locationClass is the sub-class using this class as a super-class
     */
    public JBehaveRunnerDefault(final Class<?> locationClass) {
	this.locationClass = locationClass;
	log.info("created. HashCode=[" + hashCode() + "]");
    }

    @PostConstruct
    private void init() {
	checkApplicationContext();
	if (initilizationDone) {
	    return;
	}
	useConfiguration(jbehaveDefaultConfiguration.initForLocation(locationClass));
	configuredEmbedder().embedderControls().useThreads(jbehaveProperties.getStoriesThreadCnt());
	initilizationDone = true;
	log.info("initialized. HashCode=[" + hashCode() + "]. " + jbehaveProperties.print(log.isDebugEnabled()));
    }

    /**
     * indicates that steps-implementations are beans in the application context
     */
    @Override
    public final InjectableStepsFactory stepsFactory() {
	checkApplicationContext();
	return new SpringStepsFactory(configuration(), applicationContext);
    }

    protected final List<String> defaultStoryPaths() {
	checkApplicationContext();
	return jbehaveStoriesProvider.defaultStoryPaths(locationClass);
    }

    protected final void checkApplicationContext() {
	if (null==applicationContext) {
	    throw new IllegalStateException("Application Context is null!");
	}
	if (null==jbehaveDefaultConfiguration) {
	    throw new IllegalStateException("JBehave deafult configuration is null!");
	}
	if (null==jbehaveStoriesProvider) {
	    throw new IllegalStateException("JBehave stories provider is null!");
	}
	if (null==jbehaveProperties) {
	    throw new IllegalStateException("JBehave properties is null!");
	}
    }

}
