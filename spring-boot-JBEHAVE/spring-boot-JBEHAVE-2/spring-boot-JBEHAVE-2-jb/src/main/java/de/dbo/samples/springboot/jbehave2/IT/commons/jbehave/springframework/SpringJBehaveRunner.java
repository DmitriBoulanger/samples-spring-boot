package de.dbo.samples.springboot.jbehave2.IT.commons.jbehave.springframework;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.embedder.StoryRunner;
import org.jbehave.core.io.StoryPathResolver;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.NullStepMonitor;
import org.jbehave.core.steps.StepMonitor;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.internal.runners.statements.Fail;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.test.annotation.ProfileValueUtils;
import org.springframework.test.annotation.TestAnnotationUtils;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.context.junit4.statements.RunAfterTestClassCallbacks;
import org.springframework.test.context.junit4.statements.RunAfterTestMethodCallbacks;
import org.springframework.test.context.junit4.statements.RunBeforeTestClassCallbacks;
import org.springframework.test.context.junit4.statements.RunBeforeTestMethodCallbacks;
import org.springframework.test.context.junit4.statements.SpringFailOnTimeout;
import org.springframework.test.context.junit4.statements.SpringRepeat;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import de.codecentric.jbehave.junit.monitoring.JUnitDescriptionGenerator;
import de.codecentric.jbehave.junit.monitoring.JUnitScenarioReporter;

/**
 *  TODO
 *  
 *  Nice JUnit-based UI with running stories and scenarios
 * 
 * https://blog.codecentric.de/en/2012/06/jbehave-configuration-tutorial/
 * https://github.com/codecentric/jbehave-junit-runner
 * 
 * Problem: 
 * 
 * should be started with JUnitReportingRunner but the annotation @RunWith
 * is already used to activate the SpringBoot runner org.springframework.test.context.junit4.SpringRunner
 * 
 * Possible solution: 
 * 
 * Merging of:
 * 	- org.springframework.test.context.junit4.SpringRunner
 *      - de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner
 *      
 * The both are using the same super-class org.junit.runners.BlockJUnit4ClassRunner
 *  
 *
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */

public class SpringJBehaveRunner extends BlockJUnit4ClassRunner  {
    private static final Logger log = LoggerFactory.getLogger(SpringJBehaveRunner.class);

    /* Spring-Boot */
    private static final Method withRulesMethod;

    static {
	if (!ClassUtils.isPresent("org.junit.internal.Throwables", SpringJUnit4ClassRunner.class.getClassLoader())) {
	    throw new IllegalStateException("SpringJUnit4ClassRunner requires JUnit 4.12 or higher.");
	}

	withRulesMethod = ReflectionUtils.findMethod(SpringJUnit4ClassRunner.class, "withRules",
		FrameworkMethod.class, Object.class, Statement.class);
	if (withRulesMethod == null) {
	    throw new IllegalStateException("SpringJUnit4ClassRunner requires JUnit 4.12 or higher.");
	}
	ReflectionUtils.makeAccessible(withRulesMethod);
    }

    // 
    //       INSTANCE
    //

    // JBehave
    private List<Description> storyDescriptions;
    private Embedder configuredEmbedder;
    private List<String> storyPaths;
    private Configuration configuration;
    private int numberOfTestCases;
    private Description rootDescription;
    List<CandidateSteps> candidateSteps;
    private ConfigurableEmbedder configurableEmbedder;
    // Spring-Boot
    private final TestContextManager testContextManager;

    public SpringJBehaveRunner(Class<? extends ConfigurableEmbedder> clazz) 
	    throws InitializationError, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
	super(clazz);
	if (log.isDebugEnabled()) {
	    log.debug("SpringJBehaveRunner constructor called with [" + clazz + "]");
	}

	// ============================================================================================
	//                      Spring-Boot
	// ============================================================================================

	ensureSpringRulesAreNotPresent(clazz);
	this.testContextManager = createTestContextManager(clazz);

	// ============================================================================================
	//                            JBehave
	// ============================================================================================

	configurableEmbedder = clazz.newInstance();

	if (configurableEmbedder instanceof JUnitStories) {
	    getStoryPathsFromJUnitStories(clazz);
	} else if (configurableEmbedder instanceof JUnitStory) {
	    getStoryPathsFromJUnitStory();
	}

	configuration = configuredEmbedder.configuration();

	StepMonitor originalStepMonitor = createCandidateStepsWithNoMonitor();
	storyDescriptions = buildDescriptionFromStories();
	createCandidateStepsWith(originalStepMonitor);

	initRootDescription();
    }

    // ============================================================================================
    //                            Spring-JBehave @Override
    // ============================================================================================

//    @Override
//    public Description getDescription() {
//	return rootDescription;
//    }
    
    /**
     * Return a description suitable for an ignored test class if the test is
     * disabled via {@code @IfProfileValue} at the class-level, and
     * otherwise delegate to the parent implementation.
     * @see ProfileValueUtils#isTestEnabledInThisEnvironment(Class)
     */
    @Override
    public Description getDescription() {
//	if (!ProfileValueUtils.isTestEnabledInThisEnvironment(getTestClass().getJavaClass())) {
//	    return Description.createSuiteDescription(getTestClass().getJavaClass());
//	}
//	return super.getDescription();
	
	return rootDescription;
    }

    // ============================================================================================
    //                            JBehave @Override
    // ============================================================================================



    @Override
    public int testCount() {
	return numberOfTestCases;
    }


    /**
     * Returns a {@link Statement}: Call {@link #runChild(Object, RunNotifier)}
     * on each object returned by {@link #getChildren()} (subject to any imposed
     * filter and sort)
     */
    @Override
    protected Statement childrenInvoker(final RunNotifier notifier) {
	return new Statement() {
	    @Override
	    public void evaluate() {
		JUnitScenarioReporter junitReporter = new JUnitScenarioReporter(
			notifier, numberOfTestCases, rootDescription, configuration.keywords());
		// tell the reporter how to handle pending steps
		junitReporter.usePendingStepStrategy(configuration
			.pendingStepStrategy());

		addToStoryReporterFormats(junitReporter);

		try {
		    configuredEmbedder.runStoriesAsPaths(storyPaths);
		} catch (Throwable e) {
		    throw new RuntimeException(e);
		} finally {
		    configuredEmbedder.generateCrossReference();
		}
	    }
	};
    }

    // ============================================================================================
    //                            Spring @Override
    // ============================================================================================


    /**
     * Check whether the test is enabled in the current execution environment.
     * <p>This prevents classes with a non-matching {@code @IfProfileValue}
     * annotation from running altogether, even skipping the execution of
     * {@code prepareTestInstance()} methods in {@code TestExecutionListeners}.
     * @see ProfileValueUtils#isTestEnabledInThisEnvironment(Class)
     * @see org.springframework.test.annotation.IfProfileValue
     * @see org.springframework.test.context.TestExecutionListener
     */
    @Override
    public void run(RunNotifier notifier) {
	if (!ProfileValueUtils.isTestEnabledInThisEnvironment(getTestClass().getJavaClass())) {
	    notifier.fireTestIgnored(getDescription());
	    return;
	}
	super.run(notifier);
    }

    /**
     * Wrap the {@link Statement} returned by the parent implementation with a
     * {@code RunBeforeTestClassCallbacks} statement, thus preserving the
     * default JUnit functionality while adding support for the Spring TestContext
     * Framework.
     * @see RunBeforeTestClassCallbacks
     */
    @Override
    protected Statement withBeforeClasses(Statement statement) {
	Statement junitBeforeClasses = super.withBeforeClasses(statement);
	return new RunBeforeTestClassCallbacks(junitBeforeClasses, getTestContextManager());
    }

    /**
     * Wrap the {@link Statement} returned by the parent implementation with a
     * {@code RunAfterTestClassCallbacks} statement, thus preserving the default
     * JUnit functionality while adding support for the Spring TestContext Framework.
     * @see RunAfterTestClassCallbacks
     */
    @Override
    protected Statement withAfterClasses(Statement statement) {
	Statement junitAfterClasses = super.withAfterClasses(statement);
	return new RunAfterTestClassCallbacks(junitAfterClasses, getTestContextManager());
    }

    /**
     * Delegate to the parent implementation for creating the test instance and
     * then allow the {@link #getTestContextManager() TestContextManager} to
     * prepare the test instance before returning it.
     * @see TestContextManager#prepareTestInstance
     */
    @Override
    protected Object createTest() throws Exception {
	Object testInstance = super.createTest();
	getTestContextManager().prepareTestInstance(testInstance);
	return testInstance;
    }

    /**
     * Perform the same logic as
     * {@link BlockJUnit4ClassRunner#runChild(FrameworkMethod, RunNotifier)},
     * except that tests are determined to be <em>ignored</em> by
     * {@link #isTestMethodIgnored(FrameworkMethod)}.
     */
    @Override
    protected void runChild(FrameworkMethod frameworkMethod, RunNotifier notifier) {
	Description description = describeChild(frameworkMethod);
	if (isTestMethodIgnored(frameworkMethod)) {
	    notifier.fireTestIgnored(description);
	}
	else {
	    Statement statement;
	    try {
		statement = methodBlock(frameworkMethod);
	    }
	    catch (Throwable ex) {
		statement = new Fail(ex);
	    }
	    runLeaf(statement, description, notifier);
	}
    }

    /**
     * Augment the default JUnit behavior
     * {@linkplain #withPotentialRepeat with potential repeats} of the entire
     * execution chain.
     * <p>Furthermore, support for timeouts has been moved down the execution
     * chain in order to include execution of {@link org.junit.Before @Before}
     * and {@link org.junit.After @After} methods within the timed execution.
     * Note that this differs from the default JUnit behavior of executing
     * {@code @Before} and {@code @After} methods in the main thread while
     * executing the actual test method in a separate thread. Thus, the net
     * effect is that {@code @Before} and {@code @After} methods will be
     * executed in the same thread as the test method. As a consequence,
     * JUnit-specified timeouts will work fine in combination with Spring
     * transactions. However, JUnit-specific timeouts still differ from
     * Spring-specific timeouts in that the former execute in a separate
     * thread while the latter simply execute in the main thread (like regular
     * tests).
     * @see #possiblyExpectingExceptions(FrameworkMethod, Object, Statement)
     * @see #withBefores(FrameworkMethod, Object, Statement)
     * @see #withAfters(FrameworkMethod, Object, Statement)
     * @see #withRulesReflectively(FrameworkMethod, Object, Statement)
     * @see #withPotentialRepeat(FrameworkMethod, Object, Statement)
     * @see #withPotentialTimeout(FrameworkMethod, Object, Statement)
     */
    @Override
    protected Statement methodBlock(FrameworkMethod frameworkMethod) {
	Object testInstance;
	try {
	    testInstance = new ReflectiveCallable() {
		@Override
		protected Object runReflectiveCall() throws Throwable {
		    return createTest();
		}
	    }.run();
	}
	catch (Throwable ex) {
	    return new Fail(ex);
	}

	Statement statement = methodInvoker(frameworkMethod, testInstance);
	statement = possiblyExpectingExceptions(frameworkMethod, testInstance, statement);
	statement = withBefores(frameworkMethod, testInstance, statement);
	statement = withAfters(frameworkMethod, testInstance, statement);
	statement = withRulesReflectively(frameworkMethod, testInstance, statement);
	statement = withPotentialRepeat(frameworkMethod, testInstance, statement);
	statement = withPotentialTimeout(frameworkMethod, testInstance, statement);
	return statement;
    }

    /**
     * Perform the same logic as
     * {@link BlockJUnit4ClassRunner#possiblyExpectingExceptions(FrameworkMethod, Object, Statement)}
     * except that the <em>expected exception</em> is retrieved using
     * {@link #getExpectedException(FrameworkMethod)}.
     */
    @Override
    protected Statement possiblyExpectingExceptions(FrameworkMethod frameworkMethod, Object testInstance, Statement next) {
	Class<? extends Throwable> expectedException = getExpectedException(frameworkMethod);
	return (expectedException != null ? new ExpectException(next, expectedException) : next);
    }

    /**
     * Perform the same logic as
     * {@link BlockJUnit4ClassRunner#withPotentialTimeout(FrameworkMethod, Object, Statement)}
     * but with additional support for Spring's {@code @Timed} annotation.
     * <p>Supports both Spring's {@link org.springframework.test.annotation.Timed @Timed}
     * and JUnit's {@link Test#timeout() @Test(timeout=...)} annotations, but not both
     * simultaneously.
     * @return either a {@link SpringFailOnTimeout}, a {@link FailOnTimeout},
     * or the supplied {@link Statement} as appropriate
     * @see #getSpringTimeout(FrameworkMethod)
     * @see #getJUnitTimeout(FrameworkMethod)
     */
    @Override
    @SuppressWarnings("deprecation")
    protected Statement withPotentialTimeout(FrameworkMethod frameworkMethod, Object testInstance, Statement next) {
	Statement statement = null;
	long springTimeout = getSpringTimeout(frameworkMethod);
	long junitTimeout = getJUnitTimeout(frameworkMethod);
	if (springTimeout > 0 && junitTimeout > 0) {
	    String msg = String.format("Test method [%s] has been configured with Spring's @Timed(millis=%s) and " +
		    "JUnit's @Test(timeout=%s) annotations, but only one declaration of a 'timeout' is " +
		    "permitted per test method.", frameworkMethod.getMethod(), springTimeout, junitTimeout);
	    log.error(msg);
	    throw new IllegalStateException(msg);
	}
	else if (springTimeout > 0) {
	    statement = new SpringFailOnTimeout(next, springTimeout);
	}
	else if (junitTimeout > 0) {
	    statement = FailOnTimeout.builder().withTimeout(junitTimeout, TimeUnit.MILLISECONDS).build(next);
	}
	else {
	    statement = next;
	}

	return statement;
    }

    /**
     * Wrap the {@link Statement} returned by the parent implementation with a
     * {@code RunBeforeTestMethodCallbacks} statement, thus preserving the
     * default functionality while adding support for the Spring TestContext
     * Framework.
     * @see RunBeforeTestMethodCallbacks
     */
    @Override
    protected Statement withBefores(FrameworkMethod frameworkMethod, Object testInstance, Statement statement) {
	Statement junitBefores = super.withBefores(frameworkMethod, testInstance, statement);
	return new RunBeforeTestMethodCallbacks(junitBefores, testInstance, frameworkMethod.getMethod(),
		getTestContextManager());
    }

    /**
     * Wrap the {@link Statement} returned by the parent implementation with a
     * {@code RunAfterTestMethodCallbacks} statement, thus preserving the
     * default functionality while adding support for the Spring TestContext
     * Framework.
     * @see RunAfterTestMethodCallbacks
     */
    @Override
    protected Statement withAfters(FrameworkMethod frameworkMethod, Object testInstance, Statement statement) {
	Statement junitAfters = super.withAfters(frameworkMethod, testInstance, statement);
	return new RunAfterTestMethodCallbacks(junitAfters, testInstance, frameworkMethod.getMethod(),
		getTestContextManager());
    }





    // ============================================================================================
    //                            JBehave
    // ============================================================================================

    private void createCandidateStepsWith(StepMonitor stepMonitor) {
	// reset step monitor and recreate candidate steps
	configuration.useStepMonitor(stepMonitor);
	getCandidateSteps();
	for (CandidateSteps step : candidateSteps) {
	    step.configuration().useStepMonitor(stepMonitor);
	}
    }

    private StepMonitor createCandidateStepsWithNoMonitor() {
	StepMonitor usedStepMonitor = configuration.stepMonitor();
	createCandidateStepsWith(new NullStepMonitor());
	return usedStepMonitor;
    }

    private void getStoryPathsFromJUnitStory() {
	configuredEmbedder = configurableEmbedder.configuredEmbedder();
	StoryPathResolver resolver = configuredEmbedder.configuration()
		.storyPathResolver();
	storyPaths = Arrays.asList(resolver.resolve(configurableEmbedder
		.getClass()));
    }

    @SuppressWarnings("unchecked")
    private void getStoryPathsFromJUnitStories(Class<? extends ConfigurableEmbedder> testClass)
	    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
	configuredEmbedder = configurableEmbedder.configuredEmbedder();
	Method method = makeStoryPathsMethodPublic(testClass);
	storyPaths = ((List<String>) method.invoke(
		(JUnitStories) configurableEmbedder, (Object[]) null));
    }

    private Method makeStoryPathsMethodPublic(
	    Class<? extends ConfigurableEmbedder> testClass)
		    throws NoSuchMethodException {
	Method method;
	try {
	    method = testClass.getDeclaredMethod("storyPaths", (Class[]) null);
	} catch (NoSuchMethodException e) {
	    method = testClass.getMethod("storyPaths", (Class[]) null);
	}
	method.setAccessible(true);
	return method;
    }

    private void getCandidateSteps() {
	// candidateSteps = configurableEmbedder.configuredEmbedder()
	// .stepsFactory().createCandidateSteps();
	InjectableStepsFactory stepsFactory = configurableEmbedder
		.stepsFactory();
	if (stepsFactory != null) {
	    candidateSteps = stepsFactory.createCandidateSteps();
	} else {
	    Embedder embedder = configurableEmbedder.configuredEmbedder();
	    candidateSteps = embedder.candidateSteps();
	    if (candidateSteps == null || candidateSteps.isEmpty()) {
		candidateSteps = embedder.stepsFactory().createCandidateSteps();
	    }
	}
    }

    private void initRootDescription() {
	rootDescription = Description
		.createSuiteDescription(configurableEmbedder.getClass());
	for (Description storyDescription : storyDescriptions) {
	    rootDescription.addChild(storyDescription);
	}
    }

    private void addToStoryReporterFormats(JUnitScenarioReporter junitReporter) {
	StoryReporterBuilder storyReporterBuilder = configuration
		.storyReporterBuilder();
	StoryReporterBuilder.ProvidedFormat junitReportFormat = new StoryReporterBuilder.ProvidedFormat(
		junitReporter);
	storyReporterBuilder.withFormats(junitReportFormat);
    }

    private List<Description> buildDescriptionFromStories() {
	JUnitDescriptionGenerator descriptionGenerator = new JUnitDescriptionGenerator(
		candidateSteps, configuration);
	StoryRunner storyRunner = new StoryRunner();
	List<Description> storyDescriptions = new ArrayList<Description>();

	addSuite(storyDescriptions, "BeforeStories");
	addStories(storyDescriptions, storyRunner, descriptionGenerator);
	addSuite(storyDescriptions, "AfterStories");

	numberOfTestCases += descriptionGenerator.getTestCases();

	return storyDescriptions;
    }

    private void addStories(List<Description> storyDescriptions,
	    StoryRunner storyRunner, JUnitDescriptionGenerator gen) {
	for (String storyPath : storyPaths) {
	    Story parseStory = storyRunner
		    .storyOfPath(configuration, storyPath);
	    Description descr = gen.createDescriptionFrom(parseStory);
	    storyDescriptions.add(descr);
	}
    }

    private void addSuite(List<Description> storyDescriptions, String name) {
	storyDescriptions.add(Description.createTestDescription(Object.class,
		name));
	numberOfTestCases++;
    }

    // ============================================================================================
    //                      Spring-Boot
    // ============================================================================================
    
	/**
	 * Get the {@link TestContextManager} associated with this runner.
	 */
	protected final TestContextManager getTestContextManager() {
		return this.testContextManager;
	}

    /**
     * Invoke JUnit's private {@code withRules()} method using reflection.
     */
    private Statement withRulesReflectively(FrameworkMethod frameworkMethod, Object testInstance, Statement statement) {
	return (Statement) ReflectionUtils.invokeMethod(withRulesMethod, this, frameworkMethod, testInstance, statement);
    }

    /**
     * Return {@code true} if {@link Ignore @Ignore} is present for the supplied
     * {@linkplain FrameworkMethod test method} or if the test method is disabled
     * via {@code @IfProfileValue}.
     * @see ProfileValueUtils#isTestEnabledInThisEnvironment(Method, Class)
     */
    protected boolean isTestMethodIgnored(FrameworkMethod frameworkMethod) {
	Method method = frameworkMethod.getMethod();
	return (method.isAnnotationPresent(Ignore.class) ||
		!ProfileValueUtils.isTestEnabledInThisEnvironment(method, getTestClass().getJavaClass()));
    }
    /**
     * Get the {@code exception} that the supplied {@linkplain FrameworkMethod
     * test method} is expected to throw.
     * <p>Supports JUnit's {@link Test#expected() @Test(expected=...)} annotation.
     * <p>Can be overridden by subclasses.
     * @return the expected exception, or {@code null} if none was specified
     */
    protected Class<? extends Throwable> getExpectedException(FrameworkMethod frameworkMethod) {
	Test test = frameworkMethod.getAnnotation(Test.class);
	return (test != null && test.expected() != Test.None.class ? test.expected() : null);
    }

    /**
     * Retrieve the configured JUnit {@code timeout} from the {@link Test @Test}
     * annotation on the supplied {@linkplain FrameworkMethod test method}.
     * @return the timeout, or {@code 0} if none was specified
     */
    protected long getJUnitTimeout(FrameworkMethod frameworkMethod) {
	Test test = frameworkMethod.getAnnotation(Test.class);
	return (test != null && test.timeout() > 0 ? test.timeout() : 0);
    }

    /**
     * Retrieve the configured Spring-specific {@code timeout} from the
     * {@link org.springframework.test.annotation.Timed @Timed} annotation
     * on the supplied {@linkplain FrameworkMethod test method}.
     * @return the timeout, or {@code 0} if none was specified
     * @see TestAnnotationUtils#getTimeout(Method)
     */
    protected long getSpringTimeout(FrameworkMethod frameworkMethod) {
	return TestAnnotationUtils.getTimeout(frameworkMethod.getMethod());
    }

    /**
     * Wrap the supplied {@link Statement} with a {@code SpringRepeat} statement.
     * <p>Supports Spring's {@link org.springframework.test.annotation.Repeat @Repeat}
     * annotation.
     * @see TestAnnotationUtils#getRepeatCount(Method)
     * @see SpringRepeat
     */
    protected Statement withPotentialRepeat(FrameworkMethod frameworkMethod, Object testInstance, Statement next) {
	return new SpringRepeat(next, frameworkMethod.getMethod());
    }

    /**
     * Create a new {@link TestContextManager} for the supplied test class.
     * <p>Can be overridden by subclasses.
     * @param clazz the test class to be managed
     */
    protected TestContextManager createTestContextManager(Class<?> clazz) {
	return new TestContextManager(clazz);
    }

    // ============================================================================================
    //                      Spring-Boot Helpers
    // ============================================================================================

    private static void ensureSpringRulesAreNotPresent(Class<?> testClass) {
	for (Field field : testClass.getFields()) {
	    if (SpringClassRule.class.isAssignableFrom(field.getType())) {
		throw new IllegalStateException(String.format("Detected SpringClassRule field in test class [%s], " +
			"but SpringClassRule cannot be used with the SpringJUnit4ClassRunner.", testClass.getName()));
	    }
	    if (SpringMethodRule.class.isAssignableFrom(field.getType())) {
		throw new IllegalStateException(String.format("Detected SpringMethodRule field in test class [%s], " +
			"but SpringMethodRule cannot be used with the SpringJUnit4ClassRunner.", testClass.getName()));
	    }
	}
    }

    // ============================================================================================
    //                      JBehave Helpers
    // ============================================================================================

    public static EmbedderControls recommandedControls(Embedder embedder) {
	return recommendedControls(embedder);
    }

    public static EmbedderControls recommendedControls(Embedder embedder) {
	return embedder.embedderControls()
		// don't throw an exception on generating reports for failing stories
		.doIgnoreFailureInView(true)
		// don't throw an exception when a story failed
		.doIgnoreFailureInStories(true)
		// .doVerboseFailures(true)
		.useThreads(1);
    }

}
