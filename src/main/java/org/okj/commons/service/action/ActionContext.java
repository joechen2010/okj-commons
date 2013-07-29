/**
 * @(#)ActionContext.java 2013-1-30
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
package org.okj.commons.service.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Action上下文容器
 * @author Administrator
 * @version $Id: ActionContext.java, v 0.1 2013-1-30 上午11:17:39 Administrator Exp $
 */
public class ActionContext implements Serializable {
    /** UID */
    private static final long   serialVersionUID = 8804328701557488099L;

    /* 发生错误的键值 */
    public static final String  ERROR_KEY        = "ERROR_KEY";

    /* 上下文的标识符 */
    private String              uid;

    /* 业务码 */
    private String              bizCode;

    /* 业务负荷 */
    private Object              bizLoader;

    /* 是否发生错误 */
    private boolean             hasError         = false;

    /* 上下文 */
    private Map<String, Object> context          = new HashMap<String, Object>();

    /**
     * 构造方法
     * @param bizCode
     */
    public ActionContext(String bizCode) {
        this.uid = UUID.randomUUID().toString();
        this.bizCode = bizCode;
    }

    /**
     * 添加指定键值的属性值
     * 
     * @param name
     * @param attri
     */
    public void addAttribute(String name, Object attri) {
        context.put(name, attri);
    }

    /**
     * 添加所有的属性值
     * 
     * @param others
     */
    public void addAttributes(Map<String, Object> others) {
        context.putAll(others);
    }

    /**
     * 返回指定键值的属性值
     * 
     * @param name
     * @return
     */
    public Object getAttribute(String name) {
        return context.get(name);
    }

    /**
     * 删除指定键值的属性值
     * 
     * @param name
     * @return
     */
    public Object removeAttribute(String name) {
        return context.remove(name);
    }

    /**
     * 清空属性值
     */
    public void removeAllAttributes() {
        context.clear();
    }

    /**
     * 发生异常
     * 
     * @param t
     */
    public void occurError(Throwable t) {
        this.hasError = true;
        context.put(ERROR_KEY, t);
    }

    public ActionException getException() {
        return (ActionException) context.get(ERROR_KEY);
    }

    /**
     * Getter method for property <tt>hasError</tt>.
     * 
     * @return property value of hasError
     */
    public boolean isHasError() {
        return hasError;
    }

    /**
     * Getter method for property <tt>bizCode</tt>.
     * 
     * @return property value of bizCode
     */
    public String getBizCode() {
        return bizCode;
    }

    /**
     * Getter method for property <tt>bizLoader</tt>.
     * 
     * @return property value of bizLoader
     */
    public Object getBizLoader() {
        return bizLoader;
    }

    /**
     * Setter method for property <tt>bizLoader</tt>.
     * 
     * @param bizLoader value to be assigned to property bizLoader
     */
    public void setBizLoader(Object bizLoader) {
        this.bizLoader = bizLoader;
    }

    /**
     * Getter method for property <tt>uid</tt>.
     * 
     * @return property value of uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * Setter method for property <tt>uid</tt>.
     * 
     * @param uid value to be assigned to property uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Getter method for property <tt>context</tt>.
     * 
     * @return property value of context
     */
    public Map<String, Object> getContext() {
        return context;
    }
}
