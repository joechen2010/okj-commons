/**
 * @(#)MessageResult.java 2013-1-30
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
package org.okj.commons.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * ���ؽ����������
 * @author Administrator
 * @version $Id: MessageResult.java, v 0.1 2013-1-30 ����11:38:32 Administrator Exp $
 */
public class MessageResult implements Serializable {
    /** UID */
    private static final long   serialVersionUID = -5373178385961519087L;

    /* �Ƿ�ɹ� */
    private boolean             success;

    /* ������ */
    private String              resultCode;

    /* ������Ϣ���� */
    private String              message;

    /* ����ֵ */
    private Map<String, String> properties;

    /**
     * ���췽��
     */
    public MessageResult() {
        properties = new HashMap<String, String>();
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MessageResult [success=" + success + ", resultCode=" + resultCode + ", message="
               + message + "]";
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
     * Getter method for property <tt>message</tt>.
     * 
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>message</tt>.
     * 
     * @param message value to be assigned to property message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public void addProperty(String key, String value) {
        properties.put(key, value);
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
