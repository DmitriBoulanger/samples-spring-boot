package de.dbo.samples.test.springboot.data.elasticsearch.datagenerator;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;

import de.dbo.samples.springboot.data.elasticsearch.domain.Customer;

@Component
public class CustomerDataGenerator extends JsonGenerator {

    private static final Logger LOG        = LoggerFactory.getLogger(CustomerDataGenerator.class);

    private static final int    BATCH_SIZE = 100;

    @Autowired
    private Store<Customer>     customerStore;

    public CustomerDataGenerator() {

    }

    public void generate(int size) {
        for (int i = 1; i <= size; i++) {
            Customer m = new Customer(UUID.randomUUID().toString(), UUID.randomUUID().toString());
            customerStore.store(m);

            if (i % BATCH_SIZE == 0) {
                LOG.info("{}/{} iterations done", i, size);
            }
        }

    }

    @Override
    public JsonGenerator setCodec(ObjectCodec oc) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ObjectCodec getCodec() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Version version() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JsonGenerator enable(Feature f) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JsonGenerator disable(Feature f) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isEnabled(Feature f) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getFeatureMask() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public JsonGenerator setFeatureMask(int values) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void writeStartArray() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeEndArray() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeStartObject() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeEndObject() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeFieldName(String name) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeFieldName(SerializableString name) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeString(String text) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeString(char[] text, int offset, int len) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeString(SerializableString text) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeRawUTF8String(byte[] text, int offset, int length) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeUTF8String(byte[] text, int offset, int length) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeRaw(String text) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeRaw(String text, int offset, int len) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeRaw(char[] text, int offset, int len) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeRaw(char c) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeRawValue(String text) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeRawValue(String text, int offset, int len) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeRawValue(char[] text, int offset, int len) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeBinary(Base64Variant bv, byte[] data, int offset, int len) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public int writeBinary(Base64Variant bv, InputStream data, int dataLength) throws IOException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeNumber(int v) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeNumber(long v) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeNumber(BigInteger v) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeNumber(double v) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeNumber(float v) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeNumber(BigDecimal v) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeNumber(String encodedValue) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeBoolean(boolean state) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeNull() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeObject(Object pojo) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void writeTree(TreeNode rootNode) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public JsonStreamContext getOutputContext() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void flush() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isClosed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub

    }

}
