/**
 * @(#)AbstractModel.java 2013-1-30
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
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * ����ģ��
 * @author Administrator
 * @version $Id: AbstractModel.java, v 0.1 2013-1-30 ����10:41:57 Administrator Exp $
 */
public abstract class AbstractModel<E> implements Serializable {
    /** UID */
    private static final long     serialVersionUID = 4786352989114004798L;

    /* logger */
    protected static final Logger LOGGER           = Logger.getLogger(AbstractModel.class);

    /* ��ʶ�� */
    private E                     id;

    /* �������� */
    private Date                  createTm;

    /* ����޸����� */
    private Date                  lastModifyTm;

    /**
     * Ĭ�Ϲ���
     */
    public AbstractModel() {
    }

    /**
     * Getter method for property <tt>id</tt>.
     * 
     * @return property value of id
     */
    public E getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     * 
     * @param id value to be assigned to property id
     */
    public void setId(E id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>createTm</tt>.
     * 
     * @return property value of createTm
     */
    public Date getCreateTm() {
        return createTm;
    }

    /**
     * Setter method for property <tt>createTm</tt>.
     * 
     * @param createTm value to be assigned to property createTm
     */
    public void setCreateTm(Date createTm) {
        this.createTm = createTm;
    }

    /**
     * Getter method for property <tt>lastModifyTm</tt>.
     * 
     * @return property value of lastModifyTm
     */
    public Date getLastModifyTm() {
        return lastModifyTm;
    }

    /**
     * Setter method for property <tt>lastModifyTm</tt>.
     * 
     * @param lastModifyTm value to be assigned to property lastModifyTm
     */
    public void setLastModifyTm(Date lastModifyTm) {
        this.lastModifyTm = lastModifyTm;
    }
}
