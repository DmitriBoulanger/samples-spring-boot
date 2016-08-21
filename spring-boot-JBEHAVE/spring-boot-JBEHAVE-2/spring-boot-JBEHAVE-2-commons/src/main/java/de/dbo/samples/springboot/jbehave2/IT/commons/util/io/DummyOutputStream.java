package de.dbo.samples.springboot.jbehave2.IT.commons.util.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Helper to shut-down console print-streams.
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
public final class DummyOutputStream extends OutputStream {
	
	@Override
	public void write(int b) throws IOException {
	     
	}
}
