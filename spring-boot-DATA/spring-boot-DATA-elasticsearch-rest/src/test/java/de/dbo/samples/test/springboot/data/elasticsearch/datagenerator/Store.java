package de.dbo.samples.test.springboot.data.elasticsearch.datagenerator;

public interface Store<T> {

    void store(T t);

    void flush();

}
