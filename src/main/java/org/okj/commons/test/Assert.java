package org.okj.commons.test;

/**
 * simple assert, create it to avoid depend on the spring lib.
 * 
 * @author ehanrli
 * 
 */
public final class Assert {

    private Assert() {
    }

    public static void notNull(Object obj) {
        notNull(obj, null);
    }

    public static void notNull(Object obj, String msg) {
        if (obj == null) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void notEmpty(String obj, String msg) {
        if (obj == null || obj.trim().length() == 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void assertTrue(boolean flag, String msg) {
        if (!flag) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void isTrue(boolean flag, String msg) {
        if (!flag) {
            throw new IllegalArgumentException(msg);
        }
    }

}
