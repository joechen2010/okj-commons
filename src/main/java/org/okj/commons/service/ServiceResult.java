/**
 * @(#)ServiceResult.java 2013-1-30
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
package org.okj.commons.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.okj.commons.service.action.ActionContext;

/**
 * ����ִ�н��
 * @author Administrator
 * @version $Id: ServiceResult.java, v 0.1 2013-1-30 ����11:15:58 Administrator Exp $
 */
public class ServiceResult implements Serializable {
    /** UID */
    private static final long   serialVersionUID = 5040628880993221681L;

    /* ִ���Ƿ�ɹ� */
    private boolean             success;

    /* �������ַ� */
    private String              resultCode;

    /* ���������� */
    private String              resultDesc;

    /* ���ص�ֵӳ�� */
    private Map<String, Object> attributes       = new HashMap<String, Object>();

    /**
     * ���캯��
     */
    public ServiceResult() {
    }

    /**
     * ���캯��
     * @param contex
     */
    public ServiceResult(ActionContext contex) {
        setSuccess(!contex.isHasError());
        if (!isSuccess()) {
            setResultCode(contex.getException().getErrorCode());
            setResultDesc(contex.getException().getErrorDesc());
        }
        setAttributes(contex.getContext());
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes.putAll(attributes);
    }

    /**
     * ��������ֵ
     * 
     * @param key
     * @return
     */
    public <T> T getAttribute(String key) {
        return (T) attributes.get(key);
    }

    public void addAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    /**
     * Getter method for property <tt>success</tt>.
     * 
     * @return property value of success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Setter method for property <tt>success</tt>.
     * 
     * @param success value to be assigned to property success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Getter method for property <tt>resultCode</tt>.
     * 
     * @return property value of resultCode
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * Setter method for property <tt>resultCode</tt>.
     * 
     * @param resultCode value to be assigned to property resultCode
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * Getter method for property <tt>resultDesc</tt>.
     * 
     * @return property value of resultDesc
     */
    public String getResultDesc() {
        return resultDesc;
    }

    /**
     * Setter method for property <tt>resultDesc</tt>.
     * 
     * @param resultDesc value to be assigned to property resultDesc
     */
    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }
}
