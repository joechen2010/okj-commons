/**
 * @(#)FtpFileUploadToolkit.java 2013-2-8
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
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.okj.commons.constants.CommonsKeys;
import org.okj.commons.logger.LogUtils;
import org.okj.commons.model.UploadFile;
import org.okj.commons.model.enums.UploadMode;
import org.okj.commons.net.FtpUtils;

/**
 * FTPģʽ���ϴ��ļ����ʵ��
 * @author Administrator
 * @version $Id: FtpFileUploadToolkit.java, v 0.1 2013-2-8 ����10:16:54 Administrator Exp $
 */
public class FtpFileUploadToolkit extends AbstractFileUploadToolkit {

    /** 
     * @see org.storevm.commons.web.fileupload.FileUploadToolkit#upload(org.storevm.commons.model.UploadFile, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void upload(UploadFile file, HttpServletRequest request) {
        //��ȡ�洢���ļ�������
        File storeFile = getStoreUploadFile(file, request);

        //��������
        file.setMode(UploadMode.FTP);
        file.setPath(storeFile.getPath());

        //�����ļ�����ת��
        if (fileConverter != null) {
            Object value = fileConverter.convert(file);
            if (value != null) {
                request.setAttribute(CommonsKeys.CONVERT_RESULT, value);
            }
        }

        FtpUtils ftpUtils = FtpUtils.getInstance();

        //���ӷ�����
        ftpUtils.connect();

        //��¼������
        ftpUtils.login();

        //�ϴ��ļ�
        try {
            InputStream in = FileUtils.openInputStream(file.getTmpFile());
            ftpUtils.uploadFile(in, file.getPath());
        } catch (IOException ex) {
            throw new FileUploadException(FileUploadErrorCode.SAVE_UPLOAD_FILE_FAIL);
        }

    }

    /** 
     * @see org.storevm.commons.web.fileupload.FileUploadToolkit#delete(org.storevm.commons.model.UploadFile)
     */
    @Override
    public void delete(UploadFile file) {
        FtpUtils ftpUtils = FtpUtils.getInstance();

        //���ӷ�����
        ftpUtils.connect();

        //��¼������
        ftpUtils.login();

        //ɾ���ļ�
        ftpUtils.deleteFile(file.getPath());
    }

}
