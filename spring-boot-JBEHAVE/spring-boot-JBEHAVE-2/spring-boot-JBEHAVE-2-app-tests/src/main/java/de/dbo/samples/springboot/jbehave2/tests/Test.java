package de.dbo.samples.springboot.jbehave2.tests;

import java.text.SimpleDateFormat;
import java.util.List;
// JBehave
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.PrintStreamStepdocReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.core.steps.spring.SpringStepsFactory;
// JUnit
import org.junit.runner.RunWith;
// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * TestApplication has a lot of moving parts 
 * so it made sense to wrap some of the operations in an integration test session.
 *
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and
 *           only incidentally for computers to execute
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes={TestApplication.class})
public class Test extends JUnitStories {

    @Autowired
    private ApplicationContext applicationContext;

    public Test() {
        initJBehaveConfiguration();
    }

    private void initJBehaveConfiguration() {
        Class<?> thisClass = this.getClass();
        useConfiguration(new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath(thisClass.getClassLoader()))
//                .usePendingStepStrategy(new FailingUponPendingStep())
                .useStepdocReporter(new PrintStreamStepdocReporter())
                .useStoryReporterBuilder(
                        new StoryReporterBuilder()
                                .withCodeLocation(CodeLocations.codeLocationFromClass(thisClass))
                                .withDefaultFormats()
                                .withFormats(Format.CONSOLE, Format.TXT, Format.HTML, Format.XML, Format.STATS)
                                .withCrossReference(new CrossReference())
                                .withFailureTrace(true)
                                )
                .useParameterConverters(
                        new ParameterConverters()
                                .addConverters(new ParameterConverters.DateConverter(new SimpleDateFormat("yyyy-MM-dd"))))
                .useStepMonitor(new SilentStepMonitor()));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new SpringStepsFactory(configuration(), applicationContext);
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(CodeLocations.codeLocationFromClass(this.getClass()), "**/*.story", "**/excluded*.story");
    }

}
