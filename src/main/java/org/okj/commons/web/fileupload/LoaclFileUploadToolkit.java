/**
 * @(#)LoaclFileUploadToolkit.java 2013-2-8
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

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.okj.commons.constants.CommonsKeys;
import org.okj.commons.logger.LogUtils;
import org.okj.commons.model.UploadFile;
import org.okj.commons.model.enums.UploadMode;

/**
 * �����ļ��ϴ����ʵ��
 * @author Administrator
 * @version $Id: LoaclFileUploadToolkit.java, v 0.1 2013-2-8 ����9:59:21 Administrator Exp $
 */
public class LoaclFileUploadToolkit extends AbstractFileUploadToolkit {
    /* Ĭ��·�� */
    private static final String DEFAULT_SHARE_DATA_PATH = System.getProperty("user.dir")
                                                          + File.separator + "share"
                                                          + File.separator + "data";

    /** 
     * @see org.storevm.commons.web.fileupload.FileUploadToolkit#upload(org.storevm.commons.model.UploadFile, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void upload(UploadFile file, HttpServletRequest request) {
        //��ȡ�洢���ļ�������
        File storeFile = getStoreUploadFile(file, request);

        //��������
        file.setMode(UploadMode.LOCAL);
        file.setPath(storeFile.getPath());

        //�����ļ�����ת��
        if (fileConverter != null) {
            Object value = fileConverter.convert(file);
            if (value != null) {
                request.setAttribute(CommonsKeys.CONVERT_RESULT, value);
            }
        }

        //��ȡ����·���Ĵ洢�ļ����
        File diskFile = new File(getRootPath(), file.getPath());
        //����
        try {
            FileUtils.copyFile(file.getTmpFile(), diskFile); //�����ļ�
        } catch (IOException ex) {
            throw new FileUploadException(FileUploadErrorCode.SAVE_UPLOAD_FILE_FAIL);
        }
    }

    /** 
     * @see org.storevm.commons.web.fileupload.FileUploadToolkit#delete(org.storevm.commons.model.UploadFile)
     */
    @Override
    public void delete(UploadFile file) {
        //��ȡ����·���Ĵ洢�ļ����
        File diskFile = new File(getRootPath(), file.getPath());
        FileUtils.deleteQuietly(diskFile);
    }

    /**
     * ���ظ�Ŀ¼
     * @return
     */
    protected File getRootPath() {
        //��Ŀ¼
        String rootPath = System.getProperty(CommonsKeys.SHARE_DATA_PATH);
        //���Ϊ�գ���ʹ��Ĭ�ϵĹ�����ݸ�Ŀ¼
        if (StringUtils.isBlank(rootPath)) {
            rootPath = DEFAULT_SHARE_DATA_PATH;
        }
        return new File(rootPath);
    }
}
