/**
 * @(#)CommonsFileUploadConverter.java 2013-2-8
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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;
import org.okj.commons.model.UploadFile;

/**
 * Commons Fileuploadʵ��
 * @author Administrator
 * @version $Id: CommonsFileUploadConverter.java, v 0.1 2013-2-8 ����10:40:47 Administrator Exp $
 */
public class CommonsFileUploadConverter implements FileUploadConverter<FileItem> {
    /* logger */
    private static final Logger LOGGER = Logger.getLogger(FileUploadConverter.class);

    /** 
     * @see org.storevm.commons.web.fileupload.FileUploadConverter#convert(java.lang.Object)
     */
    @Override
    public <T> UploadFile<T> convert(FileItem fileItem) {
        if (fileItem instanceof DiskFileItem) {
            //ת���ɴ����ļ�����
            DiskFileItem diskFile = (DiskFileItem) fileItem;

            if (StringUtils.isNotBlank(diskFile.getName()) && diskFile.getSize() > 0) {
                UploadFile<T> uploadFile = new UploadFile<T>();
                uploadFile.setContentType(diskFile.getContentType());
                uploadFile.setFieldName(diskFile.getFieldName());
                uploadFile.setFileName(FilenameUtils.getName(diskFile.getName()));

                try {
                    File tmpFile = createTempFile(diskFile.getInputStream());
                    if (tmpFile != null) {
                        uploadFile.setTmpFile(tmpFile);
                    }
                    return uploadFile;
                } catch (IOException ex) {
                    LogUtils.error(LOGGER, "ת���ļ�ʱ�����쳣", ex);
                }
            }
        }
        return null;
    }

    /**
     * 
     * @param in
     * @return
     */
    protected File createTempFile(InputStream in) {
        //����һ����ʱ�ļ�
        try {
            File tmp = File.createTempFile("STOREVM_", ".tmp");

            //��������д���ļ�
            byte[] data = IOUtils.toByteArray(in);
            FileUtils.writeByteArrayToFile(tmp, data);
            return tmp;
        } catch (IOException ex) {
            LogUtils.error(LOGGER, "������ʱ�ļ�ʱ�����쳣", ex);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return null;
    }
}
