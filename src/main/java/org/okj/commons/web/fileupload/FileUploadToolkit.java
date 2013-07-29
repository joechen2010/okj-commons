/**
 * @(#)FileUploadToolkit.java 2013-2-8
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

import javax.servlet.http.HttpServletRequest;

import org.okj.commons.model.UploadFile;

/**
 * �ļ��ϴ�������ӿڶ���
 * @author Administrator
 * @version $Id: FileUploadToolkit.java, v 0.1 2013-2-8 ����9:45:20 Administrator Exp $
 */
public interface FileUploadToolkit {
    /**
     * �ϴ��ļ�
     * @param file
     * @param request
     */
    public void upload(UploadFile file, HttpServletRequest request);

    /**
     * ɾ���ļ� 
     * @param file
     */
    public void delete(UploadFile file);

    /**
     * �����ļ�����ת����
     * @param converter
     */
    public void setFileConverter(FileConverter converter);
}
