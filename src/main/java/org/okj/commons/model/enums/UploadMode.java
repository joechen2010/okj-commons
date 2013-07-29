/**
 * @(#)UploadMode.java 2013-2-8
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
package org.okj.commons.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 上传模型的枚举
 * @author Administrator
 * @version $Id: UploadMode.java, v 0.1 2013-2-8 上午9:34:49 Administrator Exp $
 */
public enum UploadMode {
    /* 本地文件模式 */
    LOCAL("LOCAL"),

    /* FTP模式 */
    FTP("FTP"),

    ;

    /** 常量描述 */
    private String description;

    /**
     * 构造方法
     * @param description
     */
    UploadMode(String description) {
        this.description = description;
    }

    /**
     * Getter method for property <tt>description</tt>.
     * 
     * @return property value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 根据名称返回枚举类型
     * @param name
     * @return
     */
    public static UploadMode getEnums(String name) {
        for (UploadMode enums : values()) {
            if (StringUtils.equalsIgnoreCase(name, enums.name())) {
                return enums;
            }
        }
        return null;
    }
}
