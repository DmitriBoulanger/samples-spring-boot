package de.dbo.samples.springboot.jbehave2.IT.commons.jbehave.configuration;

import java.text.SimpleDateFormat;

import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.failures.PassingUponPendingStep;
import org.jbehave.core.failures.PendingStepStrategy;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.PrintStreamStepdocReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.NullStepMonitor;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.core.steps.StepMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dbo.samples.springboot.jbehave2.IT.commons.util.io.DummyPrintStream;

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
    private static final Logger              log         = LoggerFactory.getLogger(ConfigurationJBehaveDefault.class);

    private static final String              DATE_FORMAT = "yyyy-MM-dd";

    /** Actual property-values obtained from the jbehave.properties and/or from the system properties */
    @Autowired
    protected ConfigurationJBehaveProperties jbehaveProperties;

    public ConfigurationJBehaveDefault() {
        if (log.isDebugEnabled()) {
            log.debug("created. HashCode=[" + hashCode() + "]");
        }
    }

    /**
     *
     * @param locationClass is a class of the origin JBehave Runner
     *
     * @return this configuration adapted to the location class
     */
    public final org.jbehave.core.configuration.Configuration initForLocation(final Class<?> location) {
        checkApplicationContext();

        // =============================================
        //          Class loader for location
        // =============================================
        final LoadFromClasspath classLoader = new LoadFromClasspath(location.getClassLoader());

        // =============================================
        //          Pending Steps Strategy
        // =============================================

        final PendingStepStrategy pendingStepStrategy;
        if (jbehaveProperties.isStepsFailIfPendingStepFound()) {
            pendingStepStrategy = new FailingUponPendingStep();
            log.info("Pending steps are considerd as test-failures!");
        }
        else {
            pendingStepStrategy = new PassingUponPendingStep();
            log.info("Pending steps do not trigger test-failures!");
        }

        // =============================================
        //         Report Builder
        // =============================================

        final CrossReference crossReference = new CrossReference();
        final StoryReporterBuilder storyReporterBuilder = new StoryReporterBuilder();

        storyReporterBuilder
                .withCodeLocation(CodeLocations.codeLocationFromClass(location))
                .withDefaultFormats()
                .withCrossReference(crossReference)
                .withFailureTrace(true)
                .withFormats(Format.TXT, Format.HTML, Format.XML, Format.STATS);

        if (jbehaveProperties.isStoriesConsole()) {
            storyReporterBuilder.withFormats(Format.CONSOLE);
        }

        // =============================================
        //         Parameter Conversion
        // =============================================

        final ParameterConverters parameterConverters = new ParameterConverters();
        parameterConverters
                .addConverters(new ParameterConverters.DateConverter(new SimpleDateFormat(DATE_FORMAT)));

        // =============================================
        //         Console Printout
        // =============================================

        final PrintStreamStepdocReporter printStreamStepdocReporter;
        final StepMonitor stepMonitor;
        if (jbehaveProperties.isStoriesConsole()) {
            printStreamStepdocReporter = new PrintStreamStepdocReporter();
            stepMonitor = new SilentStepMonitor();
        }
        else {
            printStreamStepdocReporter = new PrintStreamStepdocReporter(new DummyPrintStream());
            stepMonitor = new NullStepMonitor();
        }

        // =============================================
        //              CONFIGURE
        // =============================================

        super.useStoryLoader(classLoader);
        super.usePendingStepStrategy(pendingStepStrategy);
        super.useStoryReporterBuilder(storyReporterBuilder);
        super.useParameterConverters(parameterConverters);
        super.useStepdocReporter(printStreamStepdocReporter);
        super.useStepMonitor(stepMonitor);

        if (log.isDebugEnabled()) {
            log.debug("initialized. HashCode=[" + hashCode() + "].  Location=[" + location.getName() + "]");
        }
        return this;
    }

    private final void checkApplicationContext() {
        if (null == jbehaveProperties) {
            throw new IllegalStateException("JBehave stories provider is null!");
        }
    }
}
