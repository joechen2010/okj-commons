/**
 * @(#)Functions.java 2013-1-30
 *
 * Copyright (c) 2004-2013 Lakala, Inc.
 * zhongjiang Road, building 22, Lane 879, shanghai, china 
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Lakala, Inc.  
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Lakala.
 */
package org.okj.commons.web.taglib;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * �Զ��庯���ǩ
 * @author Administrator
 * @version $Id: Functions.java, v 0.1 2013-1-30 ����5:26:48 Administrator Exp $
 */
public class Functions {
    /** logger */
    protected static final Logger logger = Logger.getLogger(Functions.class);

    /**
     * ���췽��
     */
    public Functions() {
    }

    /** �жϼ������Ƿ����ָ������ĺ��� */
    public static boolean exists(Collection<Object> c, Object o) {
        // log.debug("Functions:" + c + ":" + o);
        return c.contains(o);
    }

    public static boolean contains(String c, Object o, String separator) {
        if (logger.isDebugEnabled()) {
            logger.debug("��ȡ����c=" + c + ",o=" + o + ",separator=" + separator);
        }
        String s = StringUtils.isBlank(separator) ? "," : separator;
        String[] arr = StringUtils.split(c, s);
        return ArrayUtils.contains(arr, String.valueOf(o));
    }

    public static Object convert(String obj, String type) {
        if (type.equalsIgnoreCase("integer")) {
            return Integer.valueOf(obj);
        } else if (type.equalsIgnoreCase("long")) {
            return Long.valueOf(obj);
        } else {
            return obj;
        }
    }

    /**
     * ���÷����ĺ���
     * @param src
     * @param methodName
     * @param args
     * @return
     */
    public static Object invokeMethod(Object src, String methodName, Object args) {
        try {
            return MethodUtils.invokeMethod(src, methodName, args);
        } catch (NoSuchMethodException e) {
            logger.error(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
        } catch (InvocationTargetException e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * ����UUID
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static Long abs(Long a) {
        return Math.abs(a);
    }

    /**
     * �����顢���϶�����ָ���ķָ���ƴװ���ַ�ĸ�ʽ
     * @param array
     * @param separator
     * @param propertyName
     * @return
     */
    public static String join(Object array, String separator, String propertyName) {
        List arr = new ArrayList();
        if (array != null) {
            if (array.getClass().isArray()) {
                //���������
                arr.addAll(Arrays.asList(array));
            } else if (array instanceof List) {
                //�����List
                for (int i = 0, n = ((List) array).size(); i < n; i++) {
                    Object bean = ((List) array).get(i);
                    try {
                        arr.add(PropertyUtils.getNestedProperty(bean, propertyName));
                    } catch (Exception ex) {
                        logger.error(ex);
                    }
                }
            } else if (array instanceof Map) {
                //�����Map
                Set set = ((Map) array).entrySet();
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    Object bean = it.next();
                    try {
                        arr.add(PropertyUtils.getNestedProperty(bean, propertyName));
                    } catch (Exception ex) {
                        logger.error(ex);
                    }
                }
            } else if (array instanceof Set) {
                //�����Set
                Iterator it = ((Set) array).iterator();
                while (it.hasNext()) {
                    Object bean = it.next();
                    try {
                        arr.add(PropertyUtils.getNestedProperty(bean, propertyName));
                    } catch (Exception ex) {
                        logger.error(ex);
                    }
                }
            } else if (array instanceof String) {
                //������ַ�
                arr.addAll(Arrays.asList(StringUtils.split((String) array, separator)));
            }
        }
        return StringUtils.join(arr, separator);
    }

    /**
     * 
     * @param object
     * @param trueValue
     * @param falseValue
     * @return
     */
    public static String iff(Object object, String trueValue, String falseValue) {
        String valueStr = String.valueOf(object);
        if (StringUtils.equals("1", valueStr)) {
            valueStr = "true";
        }
        if (StringUtils.equals("0", valueStr)) {
            valueStr = "false";
        }
        boolean value = BooleanUtils.toBoolean(valueStr);
        if (value) {
            return trueValue;
        }
        return falseValue;
    }
}
