package de.dbo.samples.springboot.jbehave2.IT.commons.configuration;

import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;

import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.PrintStreamStepdocReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.SilentStepMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationJBehaveDefault extends MostUsefulConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationJBehaveDefault.class);

    @Autowired
    private ApplicationContext  applicationContext;
    
    public ConfigurationJBehaveDefault() {
	
    }

    @PostConstruct
    public void initJBehaveConfiguration() {
	final Class<?> thisClass = this.getClass();
	useStoryLoader(new LoadFromClasspath(thisClass.getClassLoader()))
	//                .usePendingStepStrategy(new org.jbehave.core.failures.FailingUponPendingStep())
	.useStepdocReporter(new PrintStreamStepdocReporter())
	.useStoryReporterBuilder(
		new StoryReporterBuilder()
		.withCodeLocation(CodeLocations.codeLocationFromClass(thisClass))
		.withDefaultFormats()
		.withFormats(Format.CONSOLE, Format.TXT, Format.HTML, Format.XML, Format.STATS)
		.withCrossReference(new CrossReference())
		.withFailureTrace(true))
	.useParameterConverters(
		new ParameterConverters()
		.addConverters(new ParameterConverters.DateConverter(new SimpleDateFormat("yyyy-MM-dd"))))
	.useStepMonitor(new SilentStepMonitor());
    }

}
