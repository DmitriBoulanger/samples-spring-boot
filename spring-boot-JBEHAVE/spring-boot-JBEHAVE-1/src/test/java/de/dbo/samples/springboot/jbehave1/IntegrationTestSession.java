package de.dbo.samples.springboot.jbehave1;

import de.dbo.samples.springboot.jbehave1.ApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Application has a lot of moving parts so it made sense to wrap some of the operations in an integration test session.
 * 
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IntegrationTestSession {

    @Autowired
    private ApplicationService applicationService;

    private int x;

    public void setX(int x) {
        this.x = x;
    }

    public int multiply(int y) {
        return applicationService.multiply(x, y);
    }

}