package org.okj.commons.net;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.okj.commons.test.Assert;

/**
 * Utilities of common usage of url parser.
 * 
 * @author ehanrli
 * 
 */
public final class UrlUtils {

    private static final String ENCODING = "UTF-8";

    private static final int DEFAULT_HTTP_PORT = 80;

    private UrlUtils() {

    }

    /**
     * only support http url now.
     * 
     * @param httpUrl
     * @return
     */
    public static int parseHttpPort(String httpUrl) {
        Assert.notEmpty(httpUrl, "Thr url is valid !");
        try {
            URL url = new URL(httpUrl);
            int port = url.getPort();
            return port == -1 ? DEFAULT_HTTP_PORT : port;
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String mapToQueryString(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder(map.size() * 5);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            try {
                stringBuilder.append((entry.getKey() != null ? URLEncoder.encode(entry.getKey(), ENCODING) : ""));
                stringBuilder.append("=");
                stringBuilder.append(entry.getValue() != null ? URLEncoder.encode(entry.getValue(), ENCODING) : "");
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException("This method requires UTF-8 encoding support", e);
            }
        }

        return stringBuilder.toString();
    }

    // public static Map<String, String> queryStringToMap(String input) {
    // Map<String, String> map = new HashMap<String, String>();
    //
    // String[] nameValuePairs = input.split("&");
    // for (String nameValuePair : nameValuePairs) {
    // String[] nameValue = nameValuePair.split("=");
    // try {
    // map.put(URLDecoder.decode(nameValue[0], ENCODING),
    // nameValue.length > 1 ? URLDecoder.decode(nameValue[1],
    // ENCODING) : "");
    // } catch (UnsupportedEncodingException e) {
    // throw new RuntimeException(
    // "This method requires UTF-8 encoding support", e);
    // }
    // }
    //
    // return map;
    // }

    public static String expandUrl(String url, Map<String, String> params) {
        String temp = url;
        if (params != null && params.size() > 0) {
            String queryString = mapToQueryString(params);
            temp = temp.indexOf('?') > 0 ? temp + "&" + queryString : temp + "?" + queryString;
        }
        return temp;
    };

}
