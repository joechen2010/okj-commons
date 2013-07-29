/**
 * @(#)StringFunction.java 2013-1-30
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

import java.text.MessageFormat;

/**
 * �ַ����������ʽ
 * @author Administrator
 * @version $Id: StringFunction.java, v 0.1 2013-1-30 ����3:43:37 Administrator Exp $
 */
public class StringFunction {
    /**
     * ��ʽ���ַ�
     * @param message
     * @param args
     * @return
     */
    public String format(String message, Object... args) {
        MessageFormat mf = new MessageFormat(message);
        if (args != null && args.length > 0) {
            return mf.format(args);
        }
        return message;
    }

    /**
     * ƴ���ַ�
     * @param strings
     * @return
     */
    public String concat(Object... strings) {
        StringBuffer sb = new StringBuffer();
        if (strings != null && strings.length > 0) {
            for (Object string : strings) {
                sb.append(string);
            }
        }
        return sb.toString();
    }

    /**
     * ȥ��ն���
     * @param obj
     * @return
     */
    public Object trimNull(Object obj) {

        if (null == obj) {
            return "";
        }
        return obj;
    }
}
