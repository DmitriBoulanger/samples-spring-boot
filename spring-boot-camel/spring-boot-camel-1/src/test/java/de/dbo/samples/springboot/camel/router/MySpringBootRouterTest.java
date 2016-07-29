package de.dbo.samples.springboot.camel.router;

import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.NotifyBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MySpringBootRouter.class)
@WebIntegrationTest(randomPort = true)
public class MySpringBootRouterTest extends Assert {

    @Autowired
    CamelContext camelContext;

    @Test
    public void shouldProduceMessages() throws InterruptedException {
        // we expect that a number of messages is automatic done by the Camel
        // route as it uses a timer to trigger
        NotifyBuilder notify = new NotifyBuilder(camelContext).whenDone(4).create();

        assertTrue(notify.matches(10, TimeUnit.SECONDS));
    }

}
