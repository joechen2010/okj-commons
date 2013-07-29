package org.okj.commons.encode;

/**
 * Utilities of common usage of UTF encoding.
 * 
 * @author ehanrli
 * 
 */
public final class UTF8Utils {

    private UTF8Utils() {

    }

    public static String buildUTF8String(byte[] obj) {
        String result;
        try {
            result = new String(obj, "UTF-8");
        } catch (Exception e) {
            throw new IllegalArgumentException("Wrong text for utf-8 encoding:" + e.getMessage()); // NOSONAR
        }
        if (result != null && result.trim().equals("")) {
            result = null;
        }
        return result;
    }

    public static byte[] getByte(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (Exception e) {
            throw new IllegalArgumentException("Wrong text for utf-8 encoding:" + e.getMessage()); // NOSONAR
        }
    }

}
