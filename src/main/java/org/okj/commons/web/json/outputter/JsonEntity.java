package org.okj.commons.web.json.outputter;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

/**
 * A wrapper entity of json content.
 * 
 * @author ehanrli
 * 
 */
public class JsonEntity extends StringEntity {

    public JsonEntity(String string) throws UnsupportedEncodingException {
        super(string, "application/json", HTTP.UTF_8);
    }

}
