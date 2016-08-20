package de.dbo.samples.springboot.jbehave2.IT.commons.jbehaveruntime;

import de.dbo.samples.springboot.jbehave2.IT.commons.configuration.ConfigurationJBehaveDefault;
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

public abstract class JBehaveRunnerDefault extends JUnitStories {
    private static final Logger log = LoggerFactory.getLogger(JBehaveRunnerDefault.class);
    
    @Autowired
    private ApplicationContext  applicationContext;
    
    public JBehaveRunnerDefault() {
	log.info("created. HashCode=[" + hashCode() + "]");
    }
    
    @PostConstruct
    protected void init() {
	useConfiguration(applicationContext.getBean(ConfigurationJBehaveDefault.class));
        configuredEmbedder().embedderControls().useThreads(10);
        log.info("initialized. HashCode=[" + hashCode() + "]");
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
