/**
 * @(#)FileUploadToolkits.java 2013-2-8
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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.okj.commons.model.enums.UploadMode;
import org.springframework.beans.factory.InitializingBean;

/**
 * �ϴ��ļ�������ʵ�������ʵ��
 * @author Administrator
 * @version $Id: FileUploadToolkits.java, v 0.1 2013-2-8 ����9:55:39 Administrator Exp $
 */
public class FileUploadToolkits implements InitializingBean {
    /* logger */
    private static final Logger            LOGGER = Logger.getLogger(FileUploadToolkits.class);

    /* ������ӳ�� */
    private Map<String, FileUploadToolkit> toolkits;

    /* �ϴ�ģʽ */
    private String                         mode;

    /** 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (toolkits == null) {
            this.toolkits = new HashMap<String, FileUploadToolkit>();
        }
        //����Ĭ�ϵ��ϴ��ļ����������
        this.toolkits.put(UploadMode.LOCAL.name(), new LoaclFileUploadToolkit());
        this.toolkits.put(UploadMode.FTP.name(), new FtpFileUploadToolkit());
    }

    public FileUploadToolkit getFileUploadToolkit() {
        if (StringUtils.isBlank(mode)) {
            return toolkits.get(UploadMode.LOCAL.name());
        }
        return toolkits.get(mode);
    }

    public FileUploadToolkit getFileUploadToolkit(String mode) {
        return toolkits.get(mode);
    }

    /**
     * Setter method for property <tt>toolkits</tt>.
     * 
     * @param toolkits value to be assigned to property toolkits
     */
    public void setToolkits(Map<String, FileUploadToolkit> toolkits) {
        this.toolkits = toolkits;
    }

    /**
     * Setter method for property <tt>mode</tt>.
     * 
     * @param mode value to be assigned to property mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }
}
