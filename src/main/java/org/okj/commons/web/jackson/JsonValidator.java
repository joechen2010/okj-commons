package org.okj.commons.web.jackson;

import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * Utilities of common usage of data validation.
 * 
 * @author ehanrli
 * 
 */
public final class JsonValidator {

    private static JsonMapper jsonMapper = JsonMapperFactory.get();

    private JsonValidator() {

    }

    /**
     * confirm the content header is json .
     * 
     * @param contentType
     * @param msg
     */
    public static void isJsonHeader(String contentType, String msg) {
        Assert.isTrue(contentType != null && contentType.startsWith("application/json"),
                "Cannot find the 'application/json' in request header.");
    }

    /**
     * Confirm the message body is a json string.
     * 
     * @param value
     * @param msg
     */
    public static void isJsonBody(String value, String msg) {
        if (value == null || value.trim().length() == 0) {
            throw new IllegalArgumentException(msg);
        }

        try {
            if (value.startsWith("{")) {
                jsonMapper.fromJson(value, Map.class);
            } else {
                jsonMapper.fromJson(value, List.class);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(msg);
        }
    }

}
