/**
 * @(#)DateFormatFunction.java 2013-1-30
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

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;

public class DateFormatFunction {
    
    private static final String DEFAULT_DATA_FORMAT_PATTERN = "yyy-MM-dd HH:mm:ss";

    /**
     * ��ʽ������
     * @param date
     * @return
     */
    public String format(Date date) {
        return format(date, null);
    }

    /**
     * ��ʽ������
     * @param date
     * @param pattern
     * @return
     */
    public String format(Date date, String pattern) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        if (StringUtils.isBlank(pattern)) {
            pattern = DEFAULT_DATA_FORMAT_PATTERN;
        }

        FastDateFormat fdf = FastDateFormat.getInstance(pattern);
        return fdf.format(date);
    }
}
