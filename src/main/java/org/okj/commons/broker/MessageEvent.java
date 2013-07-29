/**
 * @(#)MessageEvent.java 2013-1-30
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
package org.okj.commons.broker;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 消息事件
 * @author Administrator
 * @version $Id: MessageEvent.java, v 0.1 2013-1-30 上午11:09:26 Administrator Exp $
 */
public class MessageEvent implements Serializable {
    /** UID */
    private static final long   serialVersionUID = 6999653803513324084L;

    /* 事件标识符 */
    private String              id;

    /* 事件码 */
    private String              eventCode;

    /* 有效业务负荷 */
    private Serializable        payload;

    /* 消息事件的附加属性 */
    private Map<String, String> attributes;

    /**
     * 构造方法
     * @param eventCode
     */
    public MessageEvent(String eventCode) {
        this.eventCode = eventCode;
        this.id = UUID.randomUUID().toString();
        this.attributes = new HashMap<String, String>();
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MessageEvent [id=" + id + ", eventCode=" + eventCode + ", attributes=" + attributes
               + "]";
    }

    /**
     * 添加一个属性
     * @param key
     * @param value
     */
    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    /**
     * 批量添加属性
     * @param attributes
     */
    public void addAttributes(Map<String, String> attributes) {
        attributes.putAll(attributes);
    }

    /**
     * 返回一个属性
     * @param key
     * @return
     */
    public String getAttribute(String key) {
        return attributes.get(key);
    }

    /**
     * 返回所有的属性
     * 
     * @return
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * 删除指定键值的属性值
     * 
     * @param key
     */
    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    /**
     * 清空属性值
     */
    public void removeAllAttributes() {
        attributes.clear();
    }

    /**
     * Getter method for property <tt>eventCode</tt>.
     * 
     * @return property value of eventCode
     */
    public String getEventCode() {
        return eventCode;
    }

    /**
     * Setter method for property <tt>eventCode</tt>.
     * 
     * @param eventCode value to be assigned to property eventCode
     */
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    /**
     * Getter method for property <tt>payload</tt>.
     * 
     * @return property value of payload
     */
    public Serializable getPayload() {
        return payload;
    }

    /**
     * Setter method for property <tt>payload</tt>.
     * 
     * @param payload value to be assigned to property payload
     */
    public void setPayload(Serializable payload) {
        this.payload = payload;
    }

    /**
     * Getter method for property <tt>id</tt>.
     * 
     * @return property value of id
     */
    public String getId() {
        return id;
    }
}
