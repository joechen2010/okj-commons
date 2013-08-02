/**
 * 
 */
package org.okj.commons.web.jackson;

import java.util.List;
import java.util.Map;

/**
 * 
 */
public interface JsonMapper {

    /**
     * 
     * @param object
     * @return
     */
    public abstract String toJson(Object object);

    public abstract String toJson(@SuppressWarnings("rawtypes") List objects);

    public abstract <T> T fromJson(String json, Class<T> classOfT);

    public abstract <T> List<T> listFromJson(String json, Class<?> objectClass);

    /**
     * Conver a simple json string to HashMap. it don't support the complicated structure.
     * 
     * @param json
     * @return
     */
    public abstract Map<String, String> stringMapfromJson(String json);

    public abstract Map<String, Object> mapfromJson(String json);

}
