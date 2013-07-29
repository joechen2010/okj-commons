/**
 * @(#)TreeNode.java 2013-1-30
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

import org.okj.commons.model.enums.TreeNodeType;


/**
 * ��� 
 * @author Administrator
 * @version $Id: TreeNode.java, v 0.1 2013-1-30 ����10:45:25 Administrator Exp $
 */
public class TreeNode<E> extends AbstractModel<E> {
    /** UID */
    private static final long serialVersionUID = 7867816608772753478L;

    /* �ڵ����� */
    private TreeNodeType      nodeType         = TreeNodeType.NODE;

    /* ���ڵ� */
    private TreeNode<E>       parent;

    /* �˵����� */
    private String            text;

    /* �˵����� */
    private String            url;

    /* �˵����� */
    private Integer           index            = -1;

    /**
     * ���캯��
     */
    public TreeNode() {
    }

    /**
     * Getter method for property <tt>nodeType</tt>.
     * 
     * @return property value of nodeType
     */
    public TreeNodeType getNodeType() {
        return nodeType;
    }

    /**
     * Setter method for property <tt>nodeType</tt>.
     * 
     * @param nodeType value to be assigned to property nodeType
     */
    public void setNodeType(TreeNodeType nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * Getter method for property <tt>parent</tt>.
     * 
     * @return property value of parent
     */
    public TreeNode<E> getParent() {
        return parent;
    }

    /**
     * Setter method for property <tt>parent</tt>.
     * 
     * @param parent value to be assigned to property parent
     */
    public void setParent(TreeNode<E> parent) {
        this.parent = parent;
    }

    /**
     * Getter method for property <tt>text</tt>.
     * 
     * @return property value of text
     */
    public String getText() {
        return text;
    }

    /**
     * Setter method for property <tt>text</tt>.
     * 
     * @param text value to be assigned to property text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Getter method for property <tt>url</tt>.
     * 
     * @return property value of url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter method for property <tt>url</tt>.
     * 
     * @param url value to be assigned to property url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Getter method for property <tt>index</tt>.
     * 
     * @return property value of index
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * Setter method for property <tt>index</tt>.
     * 
     * @param index value to be assigned to property index
     */
    public void setIndex(Integer index) {
        this.index = index;
    }
}
