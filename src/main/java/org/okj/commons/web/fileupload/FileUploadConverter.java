/**
 * @(#)FileUploadConverter.java 2013-2-8
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
 * �ϴ��ļ�ת�����ӿ�
 * @author Administrator
 * @version $Id: FileUploadConverter.java, v 0.1 2013-2-8 ����10:38:15 Administrator Exp $
 */
public interface FileUploadConverter<E> {
    /**
     * ������ΪE�Ķ���ת����UploadFile����
     * @param fileItem
     * @return
     */
    <T> UploadFile<T> convert(E fileItem);
}
