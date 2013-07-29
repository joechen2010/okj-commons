/**
 * @(#)FileUploadException.java 2013-2-8
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
package org.okj.commons.web.fileupload;

import java.io.PrintWriter;
import java.text.MessageFormat;

/**
 * 
 * @author Administrator
 * @version $Id: FileUploadException.java, v 0.1 2013-2-8 ����10:04:15 Administrator Exp $
 */
public class FileUploadException extends RuntimeException {
    /** UID */
    private static final long   serialVersionUID = -6516492883954885141L;

    /* ������ */
    private FileUploadErrorCode errorCode;

    /**
     * @param message
     * @param cause
     */
    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public FileUploadException(String message) {
        super(message);
    }

    /**
     * ���췽��
     * @param errorCode
     */
    public FileUploadException(FileUploadErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    /**
     * ���췽��
     * @param errorCode
     * @param t
     */
    public FileUploadException(FileUploadErrorCode errorCode, Throwable t) {
        super(errorCode.getDescription(), t);
        this.errorCode = errorCode;
    }

    /**
     * ���췽��
     * @param errorCode
     * @param args
     */
    public FileUploadException(FileUploadErrorCode errorCode, Object... args) {
        super((new MessageFormat(errorCode.getDescription())).format(args));
        this.errorCode = errorCode;
    }

    /**
     * ���췽��
     * @param errorCode
     * @param args
     */
    public FileUploadException(FileUploadErrorCode errorCode, Throwable t, Object... args) {
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
        return FileUploadErrorCode.SYSTEM_ERROR_CODE;
    }

    public String getErrorDesc() {
        if (errorCode != null) {
            return getMessage();
        }
        return FileUploadErrorCode.SYSTEM_ERROR_DESC;
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
