package org.okj.commons.web;

import java.io.ByteArrayInputStream;

import org.apache.http.entity.InputStreamEntity;

/**
 * A extender of InputStreamEntity Add binary/octest-stream content to the inputstream to avoid some receiver cannot
 * recognize the byte array.
 * 
 * @author ehanrli
 * 
 */
public class ByteArrayInputStreamEntity extends InputStreamEntity {

    public ByteArrayInputStreamEntity(byte[] data) {
        super(new ByteArrayInputStream(data), -1);
        setContentType("binary/octet-stream");
        setChunked(true);
    }

}
