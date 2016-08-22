package de.dbo.samples.springboot.jbehave2.IT.commons.jbehave.configuration;

import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JBehave-JIRA Configuration.
 * It is important to configure stories-downloading from JIRA and JBehave reports-uploading to JIRA
 *
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and
 *           only incidentally for computers to execute
 *
 */
@Component
public class ConfigurationJBehaveJIRA extends MostUsefulConfiguration {
    private static final Logger              log = LoggerFactory.getLogger(ConfigurationJBehaveJIRA.class);

    /* values from jbehave.properties and/or system properties */
    @Autowired
    protected ConfigurationJBehaveProperties jbehaveProperties;

    public ConfigurationJBehaveJIRA() {
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

        //TODO .... ?

        return this;
    }

    protected final void checkApplicationContext() {
        if (null == jbehaveProperties) {
            throw new IllegalStateException("JBehave stories provider is null!");
        }
    }
}
