/**
 * @(#)JexlExpression.java 2013-1-30
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
package org.okj.commons.jexl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.lang.StringUtils;
import org.okj.commons.jexl.functions.DateFormatFunction;
import org.okj.commons.jexl.functions.DecodeFunction;
import org.okj.commons.jexl.functions.NumberFormatFunction;
import org.okj.commons.jexl.functions.StringFunction;
import org.springframework.beans.factory.InitializingBean;

/**
 * JEXL
 * @author Administrator
 * @version $Id: JexlExpression.java, v 0.1 2013-1-30 ����3:36:47 Administrator Exp $
 */
public class JexlExpression implements InitializingBean {
    /* ʵ��JEXL���� */
    private static final JexlEngine JEXL_ENGINE = new JexlEngine();
    static {
        JEXL_ENGINE.setCache(512);
        JEXL_ENGINE.setLenient(false);
        JEXL_ENGINE.setSilent(false);
    }

    /* �����Ļ��� */
    private JexlContext             context     = new MapContext();

    /* ����ӳ��� */
    private Map<String, Object>     functions;

    /** 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.functions == null) {
            this.functions = new HashMap<String, Object>();
        }
        //����һЩ���õ�Ĭ�Ϻ�����뵽��������
        this.functions.put("StringUtils", StringUtils.class);
        this.functions.put("DateFormat", new DateFormatFunction());
        this.functions.put("String", new StringFunction());
        this.functions.put("NumberFormat", new NumberFormatFunction());
        this.functions.put("Decode", new DecodeFunction());
        JEXL_ENGINE.setFunctions(this.functions);
    }

    /**
     * Setter method for property <tt>functions</tt>.
     * 
     * @param functions value to be assigned to property functions
     */
    public void setFunctions(Map<String, Object> functions) {
        JEXL_ENGINE.setFunctions(functions);
    }

    /**
     * ����������
     * @param name
     * @param value
     */
    public void addContext(String name, Object value) {
        context.set(name, value);
    }

    /**
     * ִ�б��ʽ
     * @param expression
     * @return
     */
    public Object evaluate(String expression) {
        //�������ʽ
        Expression exp = JEXL_ENGINE.createExpression(expression);
        return exp.evaluate(context);
    }

}
