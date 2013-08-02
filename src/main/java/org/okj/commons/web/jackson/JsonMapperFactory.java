/**
 * 
 */
package org.okj.commons.web.jackson;

/**
 * 
 */
public class JsonMapperFactory {
    private static JsonMapper mapper = new JacksonMapper();

    /**
     * @param mapper
     *            the mapper to set
     */
    public static void setMapper(JsonMapper mapper) {
        JsonMapperFactory.mapper = mapper;
    }

    public static JsonMapper get() {
        return mapper;
    }

}
