/**
 * @(#)JsonOutputter.java 2013-1-30
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

import java.io.OutputStream;

import org.okj.commons.jexl.JexlExpression;
import org.okj.commons.model.FieldSet;
import org.okj.commons.model.Pagination;

/**
 * p>Json��ʽ�ַ�������ӿڶ���</p>
 * @author Administrator
 * @version $Id: JsonOutputter.java, v 0.1 2013-1-30 ����12:11:00 Administrator Exp $
 */
public interface JsonOutputter {
    /**
     * ����JSON�ַ��ʽ
     * @param resultSet
     * @return
     */
    String output(Object result);

    /**
     * ��JSON�����IO�������
     * @param resultSet
     * @param out
     */
    void output(Object result, OutputStream out);

    /**
     * �����ֶ�
     * @param fields
     */
    void setFields(FieldSet fields);

    /**
     * ���÷�ҳ����
     * @param pager
     */
    void setPager(Pagination pager);

    /**
     * ����JEXL���ʽ����
     * @param expression
     */
    void setJexlExpression(JexlExpression expression);
}
