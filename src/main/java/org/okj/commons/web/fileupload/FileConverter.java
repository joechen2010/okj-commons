/**
 * @(#)FileConverter.java 2013-2-8
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

import org.okj.commons.model.UploadFile;

/**
 * <p>�ļ�����ת���������ڽ�A���͵��ļ�ת����B���͵��ļ�</p>
 * 
 * <p>���磺��gif\png\jpg��ʽ���ļ�ת����tiff��ʽ���ļ�</p>
 * @author Administrator
 * @version $Id: FileConverter.java, v 0.1 2013-2-8 ����9:46:29 Administrator Exp $
 */
public interface FileConverter<E> {
    /**
     * �ļ�����ת��
     * @param file
     */
    public E convert(UploadFile file);
}
