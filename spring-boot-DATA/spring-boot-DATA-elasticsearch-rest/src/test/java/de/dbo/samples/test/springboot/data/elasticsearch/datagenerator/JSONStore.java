package de.dbo.samples.test.springboot.data.elasticsearch.datagenerator;

import java.io.IOException;

import org.apache.lucene.util.IOUtils;

import com.fasterxml.jackson.core.JsonGenerator;

public class JSONStore<T> implements Store<T> {

    private final JsonGenerator generator;

    public JSONStore(JsonGenerator generator) throws IOException {
        this.generator = generator;
        generator.writeStartArray();
    }

    @Override
    public void store(T t) {
        try {
            generator.writeObject(t);
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void flush() {
        try {
            generator.writeEndArray();
            generator.flush();
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        IOUtils.closeWhileHandlingException(generator);
    }

}
