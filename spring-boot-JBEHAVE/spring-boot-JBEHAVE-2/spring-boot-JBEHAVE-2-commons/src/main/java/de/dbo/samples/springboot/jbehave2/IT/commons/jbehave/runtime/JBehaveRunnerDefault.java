package de.dbo.samples.springboot.jbehave2.IT.commons.jbehave.runtime;

import java.util.List;

import javax.annotation.PostConstruct;

import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import de.dbo.samples.springboot.jbehave2.IT.commons.jbehave.configuration.ConfigurationJBehaveDefault;
import de.dbo.samples.springboot.jbehave2.IT.commons.jbehave.configuration.ConfigurationJBehaveProperties;
import de.dbo.samples.springboot.jbehave2.IT.commons.server.TestContainerProperties;
import de.dbo.samples.springboot.jbehave2.IT.commons.stories.StoriesProvider;

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
    private static final Logger              log               = LoggerFactory.getLogger(JBehaveRunnerDefault.class);

    @Autowired
    protected ApplicationContext             applicationContext;

    @Autowired
    protected ConfigurationJBehaveDefault    jbehaveDefaultConfiguration;

    @Autowired
    protected StoriesProvider                jbehaveStoriesProvider;

    @Autowired
    protected ConfigurationJBehaveProperties jbehaveProperties;
    
    @Autowired
    protected TestContainerProperties 	     testContainerProperties;

    private boolean                          initilizationDone = false;

    private final Class<?>                   location;

    /**
     *
     * @param locationClass is the sub-class using this class as a super-class
     */
    public JBehaveRunnerDefault(final Class<?> location) {
        this.location = location;
        if (log.isDebugEnabled()) {
            log.debug("created. HashCode=[" + hashCode() + "]");
        }
    }

    @PostConstruct
    private void init() {
        checkApplicationContext();
        if (initilizationDone) {
            return;
        }
        useConfiguration(jbehaveDefaultConfiguration.initForLocation(location));

        final EmbedderControls embedderControls = configuredEmbedder().embedderControls();
        embedderControls.useThreads(jbehaveProperties.getStoriesThreadCnt());
        embedderControls.useStoryTimeouts(Integer.toString(jbehaveProperties.getStoriesTimeout()));

        initilizationDone = true;
        if (log.isDebugEnabled()) {
            log.debug("initialized. HashCode=[" + hashCode() + "]. ");
        }
        log.info("Using " + jbehaveProperties.print());
        log.info("Using " + testContainerProperties.print());

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
        return jbehaveStoriesProvider.defaultStoryPaths(location);
    }

    private final void checkApplicationContext() {
        if (null == applicationContext) {
            throw new IllegalStateException("Application Context is null!");
        }
        if (null == jbehaveDefaultConfiguration) {
            throw new IllegalStateException("JBehave deafult configuration is null!");
        }
        if (null == jbehaveStoriesProvider) {
            throw new IllegalStateException("JBehave stories provider is null!");
        }
        if (null == jbehaveProperties) {
            throw new IllegalStateException("JBehave properties is null!");
        }
        if (null == testContainerProperties) {
            throw new IllegalStateException("JBehave properties is null!");
        }
    }

}
