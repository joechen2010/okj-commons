/**
 * @(#)NumberFormatFunction.java 2013-1-30
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

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * ���ָ�ʽ������
 * @author Administrator
 * @version $Id: NumberFormatFunction.java, v 0.1 2013-1-30 ����3:44:22 Administrator Exp $
 */
public class NumberFormatFunction {

    /* Ĭ�ϵ����ָ�ʽ��ģʽ */
    private static final String DEFAULT_NUMBER_FORMAT_PATTERN = "#,#00";

    /**
     * ��ʽ������
     * @param number
     * @return
     */
    public String format(String number) {
        return format(NumberUtils.toDouble(number), null);
    }

    /**
     * ��ʽ������
     * @param number
     * @param pattern
     * @return
     */
    public String format(String number, String pattern) {
        return format(NumberUtils.toDouble(number), pattern);
    }

    /**
     * ��ʽ������
     * @param number
     * @param pattern
     * @return
     */
    public String format(double number, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = DEFAULT_NUMBER_FORMAT_PATTERN;
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(number);
    }
}
