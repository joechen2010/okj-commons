package org.okj.commons.web;

import java.io.ByteArrayInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.entity.InputStreamEntity;

/**
 * The entity wrapper tools for apache HTTP client.
 * 
 * @author ehanrli
 * 
 */
public final class EntityFactory {

    private EntityFactory() {

    }

    /**
     * Create a binary entity for octet-stream commit;
     * 
     * @param body
     * @return
     */
    public static HttpEntity createEntity(byte[] body) {
        InputStreamEntity reqEntity = new InputStreamEntity(new ByteArrayInputStream(body), -1);
        reqEntity.setContentType("binary/octet-stream");
        reqEntity.setContentEncoding("UTF-8");
        reqEntity.setChunked(true);
        return reqEntity;
    }

}
