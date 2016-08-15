package de.dbo.samples.springboot.jbehave1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;

/**
 *
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and
 *           only incidentally for computers to execute
 *
 */
@Component
public class Service {
    private static final Logger log = LoggerFactory.getLogger(Service.class);

    public Service() {
        log.info("created");
    }

    public int multiply(int x, int y) {
        return x * y;
    }

}
