/**
 * @(#)SimpleActionsExecutor.java 2013-1-30
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * ʵ��
 * @author Administrator
 * @version $Id: SimpleActionsExecutor.java, v 0.1 2013-1-30 ����11:30:09 Administrator Exp $
 */
public class SimpleActionsExecutor implements ActionsExecutor {
    /* logger */
    private static final Logger  LOGGER = Logger.getLogger(ActionsExecutor.class);

    /* Action���� */
    private Map<String, Actions> actions;

    /* ����ģ�� */
    private TransactionTemplate  transactionTemplate;

    /**
     * ���캯��
     */
    public SimpleActionsExecutor() {
        this.actions = new HashMap<String, Actions>();
    }

    /** 
     * @see org.storevm.commons.service.action.ActionsExecutor#execute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public void execute(ActionContext context) throws ActionException {
        Actions actions = this.actions.get(context.getBizCode());
        if (actions != null) {
            if (isTransaction(actions)) {
                doExecuteWithTransaction(actions, context); //������ִ��actions
            } else {
                doExecuteWithoutTransaction(actions, context); //��������ִ��actions
            }
        }
    }

    /**
     * ���������ִ��action��
     * 
     * @param context
     */
    protected void doExecuteWithoutTransaction(Actions actions, ActionContext context) {
        actions.executeActions(context);
    }

    /**
     * �������ִ��action��
     * 
     * @param context
     */
    protected void doExecuteWithTransaction(final Actions actions, final ActionContext context) {
        transactionTemplate.execute(new TransactionCallback() {
            /** 
             * @see org.springframework.transaction.support.TransactionCallback#doInTransaction(org.springframework.transaction.TransactionStatus)
             */
            @Override
            public Object doInTransaction(TransactionStatus status) {
                try {
                    doExecuteWithoutTransaction(actions, context);
                } catch (ActionException ex) {
                    context.occurError(ex); //���������б�ע�쳣
                    status.setRollbackOnly(); //�ع�
                } catch (Exception ex) {
                    LogUtils.error(LOGGER, "ִ�д������action��ʱ�����쳣", ex);
                    context.occurError(new ActionException(ActionErrorCode.SYSTEM_ERROR_CODE)); //���������б�ע�쳣
                    status.setRollbackOnly(); //�ع�
                }
                return null;
            }
        });

        //�����׳��쳣
        rethrowExcetpion(context);
    }

    /**
     * �����׳��쳣
     */
    protected void rethrowExcetpion(ActionContext context) {
        if (context.isHasError()) {
            throw context.getException();
        }
    }

    /**
     * �Ƿ������
     * 
     * @param actions
     * @return
     */
    protected boolean isTransaction(Actions actions) {
        if (actions != null) {
            return actions.isTransaction();
        }
        return false;
    }

    /**
     * Setter method for property <tt>actions</tt>.
     * 
     * @param actions value to be assigned to property actions
     */
    public void setActions(List<Actions> actions) {
        if (actions != null && !actions.isEmpty()) {
            for (Actions action : actions) {
                this.actions.put(action.getBizCode(), action);
            }
        }
    }

    /**
     * Setter method for property <tt>transactionTemplate</tt>.
     * 
     * @param transactionTemplate value to be assigned to property transactionTemplate
     */
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
}
