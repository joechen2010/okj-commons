/**
 * @(#)ActionException.java 2013-1-30
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

import java.io.PrintWriter;
import java.text.MessageFormat;

/**
 * Action�쳣
 * @author Administrator
 * @version $Id: ActionException.java, v 0.1 2013-1-30 ����11:25:07 Administrator Exp $
 */
public class ActionException extends RuntimeException {
    /** UID */
    private static final long serialVersionUID = 2848728030880817822L;

    /* ������ */
    private ActionErrorCode   errorCode;

    /**
     * @param message
     * @param cause
     */
    public ActionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public ActionException(String message) {
        super(message);
    }

    /**
     * ���췽��
     * @param errorCode
     */
    public ActionException(ActionErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    /**
     * ���췽��
     * @param errorCode
     * @param t
     */
    public ActionException(ActionErrorCode errorCode, Throwable t) {
        super(errorCode.getDescription(), t);
        this.errorCode = errorCode;
    }

    /**
     * ���췽��
     * @param errorCode
     * @param args
     */
    public ActionException(ActionErrorCode errorCode, Object... args) {
        super((new MessageFormat(errorCode.getDescription())).format(args));
        this.errorCode = errorCode;
    }

    /**
     * ���췽��
     * @param errorCode
     * @param args
     */
    public ActionException(ActionErrorCode errorCode, Throwable t, Object... args) {
        super((new MessageFormat(errorCode.getDescription())).format(args), t);
        this.errorCode = errorCode;
    }

    /**
     * Getter method for property <tt>errorCode</tt>.
     * 
     * @return property value of errorCode
     */
    public String getErrorCode() {
        if (errorCode != null) {
            return errorCode.getCode();
        }
        return ActionErrorCode.SYSTEM_ERROR_CODE;
    }

    public String getErrorDesc() {
        if (errorCode != null) {
            return getMessage();
        }
        return ActionErrorCode.SYSTEM_ERROR_DECC;
    }

    /**
     * 
     * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
     */
    @Override
    public void printStackTrace(PrintWriter writer) {
        if (errorCode != null) {
            writer.print("[" + errorCode.getCode() + "]" + getMessage());
        } else {
            writer.print(super.getLocalizedMessage());
        }
        super.printStackTrace(writer);

    }
}
