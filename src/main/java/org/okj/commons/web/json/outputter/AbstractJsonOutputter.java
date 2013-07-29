/**
 * @(#)DefaultJsonOutputter.java 2013-1-30
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

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.okj.commons.jexl.JexlExpression;
import org.okj.commons.logger.LogUtils;
import org.okj.commons.model.FieldSet;
import org.okj.commons.model.Pagination;

/**
 * ����
 * @author Administrator
 * @version $Id: DefaultJsonOutputter.java, v 0.1 2013-1-30 ����3:06:09 Administrator Exp $
 */
public abstract class AbstractJsonOutputter implements JsonOutputter {
    /* log4j */
    protected static final Logger LOGGER = Logger.getLogger(JsonOutputter.class);

    /* ��ҳ */
    protected Pagination          pager;

    /* �ֶμ��� */
    protected FieldSet            fields;

    /* JEXL���ʽ���� */
    protected JexlExpression      expression;

    /** 
     * @see org.storevm.commons.web.json.outputter.JsonOutputter#output(java.lang.Object, java.io.OutputStream)
     */
    @Override
    public void output(Object result, OutputStream out) {
        String jsonString = output(result);
        try {
            IOUtils.write(jsonString, out, "UTF-8");
        } catch (IOException ex) {
            LogUtils.error(LOGGER, "д��JSON���ʱ�����쳣", ex);
        }
    }

    /** 
     * @see org.storevm.commons.web.json.outputter.JsonOutputter#setFields(org.storevm.commons.model.FieldSet)
     */
    @Override
    public void setFields(FieldSet fields) {
        this.fields = fields;
    }

    /** 
     * @see org.storevm.commons.web.json.outputter.JsonOutputter#setPager(org.storevm.commons.model.Pagination)
     */
    @Override
    public void setPager(Pagination pager) {
        this.pager = pager;
    }

    /** 
     * @see org.storevm.commons.web.json.outputter.JsonOutputter#setJexlExpression(org.storevm.commons.jexl.JexlExpression)
     */
    @Override
    public void setJexlExpression(JexlExpression expression) {
        this.expression = expression;
    }

}
