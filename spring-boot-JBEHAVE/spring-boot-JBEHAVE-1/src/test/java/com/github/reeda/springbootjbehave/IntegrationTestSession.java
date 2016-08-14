package com.github.reeda.springbootjbehave;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Application has a lot of moving parts so it made sense to wrap some of the operations in an integration test session.
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
