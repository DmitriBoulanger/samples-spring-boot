package de.dbo.samples.springboot.jbehave2.IT.commons.configuration;

import java.text.SimpleDateFormat;

import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.PrintStreamStepdocReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.core.steps.StepMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Default JBehave Configuration.
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
@Component
public class ConfigurationJBehaveDefault extends MostUsefulConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationJBehaveDefault.class);

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /* values from jbehave.properties and/or system properties */
    @Autowired
    protected ConfigurationJBehaveProperties jbehaveProperties;

    public ConfigurationJBehaveDefault() {
	log.info("created. HashCode=[" + hashCode() + "]");
    }

    /**
     * 
     * @param locationClass is a class of the origin JBehave Runner
     * 
     * @return this configuration adapted to the location class
     */
    public final org.jbehave.core.configuration.Configuration initForLocation(final Class<?> locationClass) {
	checkApplicationContext();

	final LoadFromClasspath classLoader = new LoadFromClasspath(locationClass.getClassLoader());
	
	final CrossReference crossReference = new CrossReference();

	final StoryReporterBuilder storyReporterBuilder =  new StoryReporterBuilder();
	storyReporterBuilder
		.withCodeLocation(CodeLocations.codeLocationFromClass(locationClass))
		.withDefaultFormats()
		.withCrossReference(crossReference)
		.withFailureTrace(true)
		.withFormats(Format.TXT, Format.HTML, Format.XML, Format.STATS);;
	
	 if (jbehaveProperties.isStoriesConsole()) {
	     storyReporterBuilder.withFormats(Format.CONSOLE);
	 } 

	final PrintStreamStepdocReporter printStreamStepdocReporter = new PrintStreamStepdocReporter();
	
	final StepMonitor stepMonitor = new SilentStepMonitor();

	final ParameterConverters parameterConverters = new ParameterConverters();
	parameterConverters
		.addConverters(new ParameterConverters.DateConverter(new SimpleDateFormat(DATE_FORMAT)));

	super.useStoryLoader(classLoader)
		.useStoryReporterBuilder(storyReporterBuilder)
		.useParameterConverters(parameterConverters);
	 if (jbehaveProperties.isStoriesConsole()) {
	     super.useStepdocReporter(printStreamStepdocReporter);
	     super.useStepMonitor(stepMonitor);
	 }

	if (jbehaveProperties.isStepsFailIfPendingStepFound()) {
	    super.usePendingStepStrategy(new FailingUponPendingStep());
	    log.info("Pending steps are considerd as test-failures!");
	} else {
	    log.info("Pending steps do not trigger test-failures!");
	}


	log.info("initialized. HashCode=[" + hashCode() + "] Location=" +locationClass.getName());
	return this;
    }

    protected final void checkApplicationContext() {
	if (null==jbehaveProperties) {
	    throw new IllegalStateException("JBehave stories provider is null!");
	}
    }
}
