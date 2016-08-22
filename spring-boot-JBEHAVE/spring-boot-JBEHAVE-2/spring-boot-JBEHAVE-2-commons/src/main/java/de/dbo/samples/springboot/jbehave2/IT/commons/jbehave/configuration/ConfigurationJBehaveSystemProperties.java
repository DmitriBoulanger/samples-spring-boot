package de.dbo.samples.springboot.jbehave2.IT.commons.jbehave.configuration;

public interface ConfigurationJBehaveSystemProperties {

    public static final String JBEHAVE_PREFIX                                        = "jbehave.";

    // =================================================================================================================================
    //                JBehave/Steps properties
    // =================================================================================================================================

    /* jbehave.properties key */
    public static final String FAIL_IF_PENDING_STEP_FOUND                            = "steps.failIfPendingStepFound";

    /* system-property key */
    public static final String SYSTEM_PROPERTY_JBEHAVE_FAIL_IF_PENDING_STEP_FOUND    = JBEHAVE_PREFIX + FAIL_IF_PENDING_STEP_FOUND;

    // =================================================================================================================================
    //                JBehave/Stories properties
    // =================================================================================================================================

    /* jbehave.properties keys */
    public static final String STORIES_THREAD_CNT                                    = "stories.thread.cnt";
    public static final String STORIES_CONSOLE                                       = "stories.console";
    public static final String STORIES_TIMEOUT                                       = "stories.timeout";

    /* system-property keys */
    public static final String SYSTEM_PROPERTY_JBEHAVE_STORIES_THREAD_CNT            = JBEHAVE_PREFIX + STORIES_THREAD_CNT;
    public static final String SYSTEM_PROPERTY_JBEHAVE_STORIES_CONSOLE               = JBEHAVE_PREFIX + STORIES_CONSOLE;
    public static final String SYSTEM_PROPERTY_JBEHAVE_STORIES_TIMEOUT               = JBEHAVE_PREFIX + STORIES_TIMEOUT;

    // =================================================================================================================================
    //                Logging
    // =================================================================================================================================

    /* jbehave.properties key */
    public static final String LOGGING_THRID_PARTIES_SILENT                          = "logging.thridparties.silent";

    /* system-property key */
    public static final String SYSTEM_PROPERTY_JBEHAVE_LOGGING_THRID_PARTIES__SILENT = JBEHAVE_PREFIX + LOGGING_THRID_PARTIES_SILENT;

}
