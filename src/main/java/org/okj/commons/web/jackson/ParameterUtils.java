package org.okj.commons.web.jackson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * Utilities of common usage of parameter parser.
 * 
 * @author ehanrli
 * 
 */
public final class ParameterUtils {

    private static final String UTF_8 = "utf-8";

    private static JsonMapper jsonMapper = JsonMapperFactory.get();

    private ParameterUtils() {

    }

    /**
     * Get Parameter from a encoding range string. a range string is just like [start, end] or [para1, para2, para3]. We
     * use this to transport the complicated parameter via url.
     * 
     * @param rangeParameter
     * @return
     */
    public static String[] getParameters(String rangeParameter) {
        String temp = rangeParameter;
        if (!(rangeParameter.startsWith("[") && rangeParameter.endsWith("]"))) {
            try {
                temp = URLDecoder.decode(rangeParameter, UTF_8);
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException(e);
            }
        }
        temp = temp.replace("[", "").replace("]", "");
        String[] parameters = temp.split(",");
        for (int j = 0; j < parameters.length; j++) {
            parameters[j] = parameters[j].trim();
        }
        return parameters;
    }

    /**
     * bind the paramters to a range string.
     * 
     * @param args
     * @return
     */
    public static String createParameter(Object... args) {
        StringBuilder builder = new StringBuilder();
        for (Object object : args) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(object);
        }
        return "[" + builder.toString() + "]";
    }

    /**
     * encode the paramter to a range string.
     * 
     * @param args
     * @return
     */
    public static String encodeParameter(Object... args) {
        try {
            return URLEncoder.encode(createParameter(args), UTF_8);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static int getInt(String value, int defaulValue) {
        return StringUtils.isNotBlank(value) ? Integer.parseInt(value) : defaulValue;
    }

    public static long getLong(String value, long defaulValue) {
        return StringUtils.isNotBlank(value) ? Long.parseLong(value) : defaulValue;
    }

    public static boolean getBool(String value, boolean defaulValue) {
        return StringUtils.isNotBlank(value) ? value.toLowerCase().equals("true") : defaulValue;
    }

    public static String getXmlParams(String params) {
        Map<String, String> xmlMap = jsonMapper.stringMapfromJson(params);
        StringBuilder paramXml = new StringBuilder();
        Set<Entry<String, String>> entrySet = xmlMap.entrySet();
        Iterator<Entry<String, String>> it = entrySet.iterator();
        while (it.hasNext()) {
            Entry<String, String> entry = (Entry<String, String>) it.next();
            String value = entry.getValue();
            if (StringUtils.isBlank(entry.getValue())) {
                value = "";
            }
            paramXml.append("<").append(entry.getKey()).append(">");
            paramXml.append(value).append("</").append(entry.getKey()).append(">");
        }
        return paramXml.toString();
    }

    public static long[] getPairValue(String value, String separator) {
        long[] pair = { 0, 0 };
        String[] seqs = StringUtils.split(separator);
        try {
            pair[0] = Long.parseLong(seqs[0]);
            pair[1] = Long.parseLong(seqs[1]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return pair;
    }
}
