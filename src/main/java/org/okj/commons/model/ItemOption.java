/**
 * @(#)ItemOption.java 2013-1-30
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
 * ����ѡ����Ŀ�������
 * @author Administrator
 * @version $Id: ItemOption.java, v 0.1 2013-1-30 ����11:37:15 Administrator Exp $
 */
public class ItemOption implements Serializable {
    /** UID */
    private static final long   serialVersionUID = 4162389671243193759L;

    /** ��Ŀֵ */
    private String              id;

    /** ��Ŀ�ı� */
    private String              text;

    /** ��Ŀ��չ���� */
    private Map<String, String> attributes;

    /**
     * Ĭ�Ϲ���
     */
    public ItemOption() {
        attributes = new HashMap<String, String>();
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ItemSelectorOption [id=" + id + ", text=" + text + ", attributes=" + attributes
               + "]";
    }

    /** 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /** 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ItemOption other = (ItemOption) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

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
     * Getter method for property <tt>text</tt>.
     * 
     * @return property value of text
     */
    public String getText() {
        return text;
    }

    /**
     * Setter method for property <tt>text</tt>.
     * 
     * @param text value to be assigned to property text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Getter method for property <tt>attributes</tt>.
     * 
     * @return property value of attributes
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * Setter method for property <tt>attributes</tt>.
     * 
     * @param attributes value to be assigned to property attributes
     */
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
