/**
 * @(#)ActionErrorCode.java 2013-1-30
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
 * Action������ӿ�
 * @author Administrator
 * @version $Id: ActionErrorCode.java, v 0.1 2013-1-30 ����11:25:41 Administrator Exp $
 */
public interface ActionErrorCode {
    static final String SYSTEM_ERROR_CODE = "SYSTEM_ERROR";
    static final String SYSTEM_ERROR_DECC = "";

    /**
     * ���ش���������
     * 
     * @return
     */
    String getDescription();

    /**
     * ���ش�����
     * 
     * @return
     */
    String getCode();
}
