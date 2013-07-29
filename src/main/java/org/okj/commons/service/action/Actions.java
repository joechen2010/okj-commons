/**
 * @(#)Actions.java 2013-1-30
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
import java.util.List;

/**
 * Action������
 * @author Administrator
 * @version $Id: Actions.java, v 0.1 2013-1-30 ����11:29:01 Administrator Exp $
 */
public class Actions implements Serializable {
    /** UID */
    private static final long serialVersionUID = -5945815115708274404L;

    /* ҵ���� */
    public String             bizCode;

    /* ִ�е�Ԫ���� */
    public List<BizAction>    actions;

    /* �Ƿ���Ҫ���� */
    private boolean           isTransaction;

    /**
     * ִ��actions
     */
    public void executeActions(ActionContext context) {
        for (BizAction action : actions) {
            action.execute(context);
        }
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
     * Setter method for property <tt>bizCode</tt>.
     * 
     * @param bizCode value to be assigned to property bizCode
     */
    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    /**
     * Getter method for property <tt>actions</tt>.
     * 
     * @return property value of actions
     */
    public List<BizAction> getActions() {
        return actions;
    }

    /**
     * Setter method for property <tt>actions</tt>.
     * 
     * @param actions value to be assigned to property actions
     */
    public void setActions(List<BizAction> actions) {
        this.actions = actions;
    }

    /**
     * Getter method for property <tt>isTransaction</tt>.
     * 
     * @return property value of isTransaction
     */
    public boolean isTransaction() {
        return isTransaction;
    }

    /**
     * Setter method for property <tt>isTransaction</tt>.
     * 
     * @param isTransaction value to be assigned to property isTransaction
     */
    public void setTransaction(boolean isTransaction) {
        this.isTransaction = isTransaction;
    }
}
