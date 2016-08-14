package de.dbo.samples.springboot.jbehave1;

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

    public int multiply(int x, int y) {
        return x * y;
    }

}
