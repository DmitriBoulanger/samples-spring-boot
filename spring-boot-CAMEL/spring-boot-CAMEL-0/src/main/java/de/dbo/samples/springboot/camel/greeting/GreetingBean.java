package de.dbo.samples.springboot.camel.greeting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * A bean that returns a message when you call the {@link #saySomething()} method.
 * <p/>
 * Uses <tt>@Component("greetingBean")</tt> to register this bean with the name <tt>myBean</tt>
 * that we use in the Camel route to lookup this bean.
 */
@Component("greetingBean")
public class GreetingBean {

    @Value("${greeting}")
    private String say;

    public String saySomething() {
        return say;
    }

}
