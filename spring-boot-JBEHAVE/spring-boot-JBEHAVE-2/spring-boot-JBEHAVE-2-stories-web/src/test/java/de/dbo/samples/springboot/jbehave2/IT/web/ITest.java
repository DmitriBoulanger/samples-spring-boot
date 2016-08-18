package de.dbo.samples.springboot.jbehave2.IT.web;

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
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
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
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class ITest extends JUnitStories {
    private static final Logger log = LoggerFactory.getLogger(ITestApplication.class);

    @Autowired
    private ApplicationContext  applicationContext;

    public ITest() {
        log.info("created");
    }

    @PostConstruct
    public void init() {
        initJBehaveConfiguration();
    }

    // =====================================================================================================================================
    // JBEHAVE CONFIGURATION  - VERY BAD: HAS TO BE DONE IN ANOTHER WAY ....
    // =====================================================================================================================================

    private void initJBehaveConfiguration() {
        final Class<?> thisClass = this.getClass();
        useConfiguration(new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath(thisClass.getClassLoader()))
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
                .useStepMonitor(new SilentStepMonitor()));
        configuredEmbedder().embedderControls().useThreads(10);

    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new SpringStepsFactory(configuration(), applicationContext);
    }

    @Override
    protected List<String> storyPaths() {
        final List<String> storyPaths =
            new StoryFinder().findPaths(CodeLocations.codeLocationFromClass(this.getClass()), "**/*.story", "**/excluded/*.story");
        final StringBuilder sb = new StringBuilder("Stories found:");
        for (final String path : storyPaths) {
            sb.append("\n\t - " + path);
        }
        log.info(sb.toString());
        return storyPaths;
    }

}
