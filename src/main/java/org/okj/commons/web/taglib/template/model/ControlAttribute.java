/**
 * @(#)ControlAttribute.java 2013-1-30
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
package org.okj.commons.web.taglib.template.model;

import java.io.Serializable;

/**
 * �������
 * @author Administrator
 * @version $Id: ControlAttribute.java, v 0.1 2013-1-30 ����5:14:20 Administrator Exp $
 */
public class ControlAttribute implements Serializable {
    /** UID */
    private static final long serialVersionUID = -6001550757372784354L;

    /* id���ԣ���ʶ�� */
    private String            id;

    /* name���� */
    private String            name;

    /* class���� */
    private String            styleClass;

    /* style���� */
    private String            style;

    /* list�б��ж�������option��Key������� */
    private String            listKey;

    /* list�б��ж�������option��value������� */
    private String            listValue;

    /* value���� */
    private Object            value;

    /* select�ؼ���ͷ��key */
    private String            headerKey;

    /* select�ؼ���ͷ��value */
    private String            headerValue;

    /* select�ؼ���onchange�¼� */
    private String            onchange;

    /* select�ؼ���onclick�¼� */
    private String            onclick;

    /**
     * Getter method for property <tt>id</tt>.
     * 
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     * 
     * @param id value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>name</tt>.
     * 
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     * 
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>styleClass</tt>.
     * 
     * @return property value of styleClass
     */
    public String getStyleClass() {
        return styleClass;
    }

    /**
     * Setter method for property <tt>styleClass</tt>.
     * 
     * @param styleClass value to be assigned to property styleClass
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * Getter method for property <tt>style</tt>.
     * 
     * @return property value of style
     */
    public String getStyle() {
        return style;
    }

    /**
     * Setter method for property <tt>style</tt>.
     * 
     * @param style value to be assigned to property style
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Getter method for property <tt>listKey</tt>.
     * 
     * @return property value of listKey
     */
    public String getListKey() {
        return listKey;
    }

    /**
     * Setter method for property <tt>listKey</tt>.
     * 
     * @param listKey value to be assigned to property listKey
     */
    public void setListKey(String listKey) {
        this.listKey = listKey;
    }

    /**
     * Getter method for property <tt>listValue</tt>.
     * 
     * @return property value of listValue
     */
    public String getListValue() {
        return listValue;
    }

    /**
     * Setter method for property <tt>listValue</tt>.
     * 
     * @param listValue value to be assigned to property listValue
     */
    public void setListValue(String listValue) {
        this.listValue = listValue;
    }

    /**
     * Getter method for property <tt>value</tt>.
     * 
     * @return property value of value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Setter method for property <tt>value</tt>.
     * 
     * @param value value to be assigned to property value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Getter method for property <tt>headerKey</tt>.
     * 
     * @return property value of headerKey
     */
    public String getHeaderKey() {
        return headerKey;
    }

    /**
     * Setter method for property <tt>headerKey</tt>.
     * 
     * @param headerKey value to be assigned to property headerKey
     */
    public void setHeaderKey(String headerKey) {
        this.headerKey = headerKey;
    }

    /**
     * Getter method for property <tt>headerValue</tt>.
     * 
     * @return property value of headerValue
     */
    public String getHeaderValue() {
        return headerValue;
    }

    /**
     * Setter method for property <tt>headerValue</tt>.
     * 
     * @param headerValue value to be assigned to property headerValue
     */
    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    /**
     * Getter method for property <tt>onchange</tt>.
     * 
     * @return property value of onchange
     */
    public String getOnchange() {
        return onchange;
    }

    /**
     * Setter method for property <tt>onchange</tt>.
     * 
     * @param onchange value to be assigned to property onchange
     */
    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    /**
     * Getter method for property <tt>onclick</tt>.
     * 
     * @return property value of onclick
     */
    public String getOnclick() {
        return onclick;
    }

    /**
     * Setter method for property <tt>onclick</tt>.
     * 
     * @param onclick value to be assigned to property onclick
     */
    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }
}
