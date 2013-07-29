/**
 * @(#)FileUploadErrorCode.java 2013-2-8
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

/**
 * ������ʵ�ֽӿ�
 * @author Administrator
 * @version $Id: FileUploadErrorCode.java, v 0.1 2013-2-8 ����10:05:08 Administrator Exp $
 */
public interface FileUploadErrorCode {
    static final String SYSTEM_ERROR_CODE          = "SYSTEM_ERROR";
    static final String SYSTEM_ERROR_DESC          = "ϵͳ�쳣������ϵͳ����Ա��ϵ";

    static final String SAVE_UPLOAD_FILE_FAIL      = "SAVE_UPLOAD_FILE_FAIL";
    static final String SAVE_UPLOAD_FILE_FAIL_DESC = "�����ļ�ʧ��";

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
