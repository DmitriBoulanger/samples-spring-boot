package de.dbo.samples.springboot.jbehave2.IT.commons.configuration;

public interface ConfigurationJBehaveSystemProperties {
    
    public static final String JBEHAVE_PREFIX = "jbehave.";
    

    // =================================================================================================================================
    //                Steps properties
    // =================================================================================================================================
    
    /* jbehave.properties key */
    public static final String FAIL_IF_PENDING_STEP_FOUND 	                        =  "steps.failIfPendingStepFound";
    
    /* system-property key */
    public static final String SYSTEM_PROPERTY_JBEHAVE_FAIL_IF_PENDING_STEP_FOUND 	=  JBEHAVE_PREFIX + FAIL_IF_PENDING_STEP_FOUND;
    
    // =================================================================================================================================
    //                Stories properties
    // =================================================================================================================================
    
    /* jbehave.properties keys */
    public static final String STORIES_THREAD_CNT 		                         	=  "stories.thread.cnt";
    public static final String STORIES_CONSOLE 		                         		=  "stories.console";
    
    
    /* system-property keys */
    public static final String SYSTEM_PROPERTY_JBEHAVE_STORIES_THREAD_CNT 		= JBEHAVE_PREFIX + STORIES_THREAD_CNT;
    public static final String SYSTEM_PROPERTY_JBEHAVE_STORIES_CONSOLE 			= JBEHAVE_PREFIX + STORIES_CONSOLE;

}
