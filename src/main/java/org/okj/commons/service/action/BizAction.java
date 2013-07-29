/**
 * @(#)BizAction.java 2013-1-30
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

/**
 * <p>ҵ���߼���Action��һ��action����һ��ҵ���߼�ִ�е�Ԫ</p>
 * @author Administrator
 * @version $Id: BizAction.java, v 0.1 2013-1-30 ����11:17:10 Administrator Exp $
 */
public interface BizAction {
    /**
     * �Ƿ����ǰaction
     * @param context
     * @return
     */
    public boolean skip(ActionContext context);

    /**
     * ǰ�ô��?�������У���߼���
     * @param context
     * @throws ServiceException
     */
    public void before(ActionContext context) throws ActionException;

    /**
     * ���ô���
     * @param context
     * @throws ServiceException
     */
    public void after(ActionContext context) throws ActionException;

    /**
     * ִ��
     * @param context
     * @return
     * @throws ServiceException
     */
    public void execute(ActionContext context) throws ActionException;
}
