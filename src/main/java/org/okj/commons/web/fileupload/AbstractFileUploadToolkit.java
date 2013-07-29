/**
 * @(#)AbstractFileUploadToolkit.java 2013-2-8
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
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;
import org.okj.commons.model.UploadFile;

/**
 * ʵ��
 * @author Administrator
 * @version $Id: AbstractFileUploadToolkit.java, v 0.1 2013-2-8 ����9:48:38 Administrator Exp $
 */
public abstract class AbstractFileUploadToolkit implements FileUploadToolkit {
    /* logger */
    protected static final Logger LOGGER = Logger.getLogger(FileUploadToolkit.class);

    /* �ļ�����ת���� */
    protected FileConverter       fileConverter;

    /** 
     * @see org.storevm.commons.web.fileupload.FileUploadToolkit#setFileConverter(org.storevm.commons.web.fileupload.FileConverter)
     */
    @Override
    public void setFileConverter(FileConverter converter) {
        if (converter != null) {
            this.fileConverter = converter;
        }
    }

    /**
     * ���ر�����ϴ��ļ�����
     * @param file
     * @return
     */
    protected File getStoreUploadFile(UploadFile file, HttpServletRequest request) {
        String ext = FilenameUtils.getExtension(file.getFileName()); //��ȡ�ļ����׺
        FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd");
        String today = fdf.format(new Date());
        String path = StringUtils.replace(today, "-", File.separator);
        CreateRandomFile RandomFile = new CreateRandomFile(request);
        File storeFile = RandomFile.create(path, ext);
        LogUtils.info(LOGGER, "������������ļ�, storeFile={0}", storeFile);
        return storeFile;
    }
}
