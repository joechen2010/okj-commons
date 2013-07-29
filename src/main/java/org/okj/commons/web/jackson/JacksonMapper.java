package org.okj.commons.web.jackson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

public class JacksonMapper implements JsonMapper {

    private ObjectMapper mapper = new ObjectMapper();

    public JacksonMapper() {
        mapper.setSerializationInclusion(Inclusion.NON_NULL);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
    }

    public JacksonMapper(String noUnderScores) {
        mapper.setSerializationInclusion(Inclusion.NON_NULL);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);

    }

    /*
     * (non-Javadoc)
     * @see com.ericsson.cgc.mic.utils.JsonMapper#toJson(java.lang.Object)
     */
    @Override
    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ericsson.cgc.mic.utils.JsonMapper#toJson(java.util.List)
     */
    @Override
    public String toJson(@SuppressWarnings("rawtypes") List objects) {
        try {
            return mapper.writeValueAsString(objects);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ericsson.cgc.mic.utils.JsonMapper#fromJson(java.lang.String, java.lang.Class)
     */
    @Override
    public <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return mapper.readValue(json, classOfT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ericsson.cgc.mic.utils.JsonMapper#listFromJson(java.lang.String, java.lang.Class)
     */
    @Override
    public <T> List<T> listFromJson(String json, Class<?> objectClass) {
        try {
            JavaType type = mapper.getTypeFactory().constructParametricType(List.class, objectClass);
            return mapper.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ericsson.cgc.mic.utils.JsonMapper#stringMapfromJson(java.lang.String)
     */
    @Override
    public Map<String, String> stringMapfromJson(String json) {
        try {

            return mapper.readValue(json, new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ericsson.cgc.mic.utils.JsonMapper#mapfromJson(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> mapfromJson(String json) {
        try {
            return mapper.readValue(json, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
