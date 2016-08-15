package de.dbo.samples.springboot.jbehave1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and
 *           only incidentally for computers to execute
 *
 */
@Service
public class ApplicationService {
    private static final Logger log = LoggerFactory.getLogger(ApplicationService.class);

    public ApplicationService() {
        log.info("created");
    }

    public int multiply(int x, int y) {
        return x * y;
    }

}
