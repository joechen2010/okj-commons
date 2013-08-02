/**
 * @(#)JSONConverter.java 2013-2-7
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
package org.okj.commons.web.json.outputter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;

/**
 * JSONת����
 * @author Administrator
 * @version $Id: JSONConverter.java, v 0.1 2013-2-7 ����10:13:24 Administrator Exp $
 */
public class JSONConverter {
    private static final Logger LOGGER = Logger.getLogger(JSONConverter.class);

    /**
     * �����϶���ת����JSON��ʽ�ַ�
     * @param source
     * @param keyName
     * @param valueName
     * @return
     */
    public String toJson(List<?> source, String keyName, String valueName) {
        Map<String, String> map = new TreeMap<String, String>();
        if (source != null && !source.isEmpty()) {
            try {
                for (Object val : source) {
                    Object key = PropertyUtils.getNestedProperty(val, keyName);
                    Object value = PropertyUtils.getNestedProperty(val, valueName);
                    if (key != null && value != null) {
                        map.put(String.valueOf(value), String.valueOf(key));
                    }
                }
            } catch (Exception ex) {
                LogUtils.error(LOGGER, "ת��jsonʱ�����쳣", ex);
            }
        }
        JSONObject json = JSONObject.fromObject(map);
        return json.toString();
    }

    /**
     * ��ö��ת����JSON��ʽ�ַ�
     * @param cls ö������
     * @param valueName ö������������
     * @return
     */
    public String toJson(Class<? extends Enum<?>> cls, String valueName) {
        Map<String, String> map = new HashMap<String, String>();
        if (cls != null) {
            try {
                Object obj = MethodUtils.invokeExactStaticMethod(cls, "values", new Object[] {});
                Object[] array = (Object[]) obj;
                for (int i = 0, n = array.length; i < n; i++) {
                    Object key = PropertyUtils.getProperty(array[i], valueName);
                    map.put(String.valueOf(array[i]), String.valueOf(key));
                }
            } catch (Exception ex) {
                LogUtils.error(LOGGER, "ת��jsonʱ�����쳣", ex);
            }
        }

        JSONObject json = JSONObject.fromObject(map);
        return json.toString();
    }
}
