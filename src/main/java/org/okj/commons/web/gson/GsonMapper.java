package org.okj.commons.web.gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.okj.commons.web.jackson.JsonMapper;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonMapper implements JsonMapper {

    private Gson gson;

    static {
        System.out.println("\n\n\n   Your are beast \n\n\n");
    }

    public GsonMapper() {
        gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    }

    /**
     * 
     * @param object
     * @return
     */
    @Override
    public String toJson(Object object) {
        return gson.toJson(object);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public String toJson(List objects) {
        Type type = new TypeToken<List>() {
        }.getType();
        return gson.toJson(objects, type);
    }

    @Override
    public <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    /**
     * Conver a simple json string to HashMap. it don't support the complicated structure.
     * 
     * @param json
     * @return
     */
    @Override
    public Map<String, String> stringMapfromJson(String json) {
        Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public Map<String, Object> mapfromJson(String json) {
        Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    /**
     * @param json
     * @param objClass
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public <T> List<T> listFromJson(String json, Class<?> objClass) {
        Type type = new TypeToken<List<Map<String, Object>>>() {
        }.getType();
        Type mapType = new TypeToken<Map<String, Object>>() {
        }.getType();

        List<Map<String, Object>> temp = gson.fromJson(json, type);
        // dirty method since we don't want call TypeToken from outside, it will
        // break the common interface with
        // jackson implementation.
        if (temp != null && temp.size() > 0) {
            List result = new ArrayList();
            for (Map<String, Object> object : temp) {
                String j = gson.toJson(object, mapType);
                Object d = gson.fromJson(j, objClass);
                result.add(d);
            }
            return result;
        }
        return null;
    }

}
