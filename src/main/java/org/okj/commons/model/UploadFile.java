/**
 * @(#)UploadFile.java 2013-2-8
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
package org.okj.commons.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.okj.commons.model.enums.UploadMode;



/**
 * 上传文件
 * @author Administrator
 * @version $Id: UploadFile.java, v 0.1 2013-2-8 上午9:30:00 Administrator Exp $
 */
public class UploadFile<E> extends AbstractModel<E> {
    /** UID */
    private static final long   serialVersionUID = -8469884075982551876L;

    /* 附件分类 */
    private String              kind;

    /* 附件类型 */
    private String              type;

    /* 参考编号 */
    private E                   referenceId;

    /* 附件的MIME类型 */
    private String              contentType;

    /* 上传的文件名(表单中的上传文件名称) */
    private String              fieldName;

    /* 保存的文件名 */
    private String              fileName;

    /* 文件路径 */
    private String              path;

    /* 临时文件，处理完成之后需要删除 */
    private File                tmpFile;

    /* 上传模式 */
    private UploadMode          mode;

    /* 附加属性映射 */
    private Map<String, Object> attributes;

    /**
     * 构造函数
     */
    public UploadFile() {
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "UploadFile [kind=" + kind + ", type=" + type + ", referenceId=" + referenceId
               + ", contentType=" + contentType + ", fieldName=" + fieldName + ", fileName="
               + fileName + ", path=" + path + ", tmpFile=" + tmpFile + ", mode=" + mode + "]";
    }

    /**
     * Getter method for property <tt>kind</tt>.
     * 
     * @return property value of kind
     */
    public String getKind() {
        return kind;
    }

    /**
     * Setter method for property <tt>kind</tt>.
     * 
     * @param kind value to be assigned to property kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * Getter method for property <tt>type</tt>.
     * 
     * @return property value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method for property <tt>type</tt>.
     * 
     * @param type value to be assigned to property type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter method for property <tt>referenceId</tt>.
     * 
     * @return property value of referenceId
     */
    public E getReferenceId() {
        return referenceId;
    }

    /**
     * Setter method for property <tt>referenceId</tt>.
     * 
     * @param referenceId value to be assigned to property referenceId
     */
    public void setReferenceId(E referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * Getter method for property <tt>contentType</tt>.
     * 
     * @return property value of contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Setter method for property <tt>contentType</tt>.
     * 
     * @param contentType value to be assigned to property contentType
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Getter method for property <tt>fieldName</tt>.
     * 
     * @return property value of fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Setter method for property <tt>fieldName</tt>.
     * 
     * @param fieldName value to be assigned to property fieldName
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Getter method for property <tt>fileName</tt>.
     * 
     * @return property value of fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Setter method for property <tt>fileName</tt>.
     * 
     * @param fileName value to be assigned to property fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Getter method for property <tt>path</tt>.
     * 
     * @return property value of path
     */
    public String getPath() {
        return path;
    }

    /**
     * Setter method for property <tt>path</tt>.
     * 
     * @param path value to be assigned to property path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Getter method for property <tt>tmpFile</tt>.
     * 
     * @return property value of tmpFile
     */
    public File getTmpFile() {
        return tmpFile;
    }

    /**
     * Setter method for property <tt>tmpFile</tt>.
     * 
     * @param tmpFile value to be assigned to property tmpFile
     */
    public void setTmpFile(File tmpFile) {
        this.tmpFile = tmpFile;
    }

    /**
     * Getter method for property <tt>mode</tt>.
     * 
     * @return property value of mode
     */
    public UploadMode getMode() {
        return mode;
    }

    /**
     * Setter method for property <tt>mode</tt>.
     * 
     * @param mode value to be assigned to property mode
     */
    public void setMode(UploadMode mode) {
        this.mode = mode;
    }

    /**
     * 添加属性
     * @param name
     * @param attri
     */
    public void addAttribute(String name, Object attri) {
        if (attributes == null) {
            attributes = new HashMap<String, Object>();
        }
        attributes.put(name, attri);
    }

    /**
     * 返回制定名称的附加属性值
     * @param name
     * @return
     */
    public Object getAttribute(String name) {
        if (attributes != null) {
            return attributes.get(name);
        }
        return null;
    }
}
