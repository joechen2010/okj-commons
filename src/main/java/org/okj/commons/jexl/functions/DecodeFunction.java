/**
 * @(#)DecodeFunction.java 2013-1-30
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
package org.okj.commons.jexl.functions;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.BooleanUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author Administrator
 * @version $Id: DecodeFunction.java, v 0.1 2013-1-30 ����3:42:19 Administrator Exp $
 */
public class DecodeFunction {
    /* Logger */
    private static final Logger LOGGER = Logger.getLogger(DecodeFunction.class);

    /**
     * ����
     * @param val
     * @param json
     * @return
     */
    public String code(Object val, String json) {
        Map<String, String> mapper = getMapper(json);
        if (mapper != null) {
            return mapper.get(String.valueOf(val));
        }
        return null;
    }

    /**
     * �������
     * @param val
     * @param json
     * @return
     */
    public String code(boolean val, String trueValue, String falseValue) {
        if (val) {
            return trueValue;
        } else {
            return falseValue;
        }
    }

    /**
     * �������
     * @param val
     * @param trueValue
     * @param falseValue
     * @return
     */
    public String code(int val, String trueValue, String falseValue) {
        boolean b = BooleanUtils.toBoolean(val);
        if (b) {
            return trueValue;
        } else {
            return falseValue;
        }
    }

    /**
     * �������������Mapӳ��
     * @param argument
     * @return
     */
    protected Map<String, String> getMapper(String argument) {
        Map<String, String> mapper = new HashMap<String, String>();
        try {
            JSONObject json = JSONObject.fromObject(argument);
            mapper = (Map<String, String>) JSONObject.toBean(json, Map.class);
        } catch (JSONException ex) {
            LOGGER.warn("argument����Json��ݸ�ʽ���޷�������argument=" + argument + ", error="
                        + ex.getMessage());
        }
        return mapper;
    }
}
