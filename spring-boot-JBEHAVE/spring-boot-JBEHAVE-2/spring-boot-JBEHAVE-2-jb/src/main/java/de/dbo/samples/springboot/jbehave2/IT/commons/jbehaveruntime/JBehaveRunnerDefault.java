package de.dbo.samples.springboot.jbehave2.IT.commons.jbehaveruntime;

import de.dbo.samples.springboot.jbehave2.IT.commons.configuration.ConfigurationJBehaveDefault;
import de.dbo.samples.springboot.jbehave2.IT.commons.stories.StoriesProvider;

import java.util.List;

import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class JBehaveRunnerDefault extends JUnitStories {
    private static final Logger log = LoggerFactory.getLogger(JBehaveRunnerDefault.class);
    
    private ApplicationContext  applicationContext;
    
    private final String name;
    
    public JBehaveRunnerDefault(final String name) {
	this.name = name;
	log.info("created  "+ name + ". HashCode=[" + hashCode() + "]");
    }
    
    protected void init(final ApplicationContext  applicationContext) {
	this.applicationContext = applicationContext;
	useConfiguration(applicationContext.getBean(ConfigurationJBehaveDefault.class));
        configuredEmbedder().embedderControls().useThreads(10);
        log.info("initialized  "+ name + ". HashCode=[" + hashCode() + "]");
    }

    @Override
    public final InjectableStepsFactory stepsFactory() {
        return new SpringStepsFactory(configuration(), applicationContext);
    }

    @Override
    protected final List<String> storyPaths() {
	return applicationContext.getBean(StoriesProvider.class).defaultStoryPaths(this.getClass());
    }

}
