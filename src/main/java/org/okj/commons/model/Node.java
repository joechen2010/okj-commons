/**
 * @(#)Node.java 2013-1-30
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.okj.commons.model.enums.TreeNodeType;

/**
 * ��ʾ�ڵ�,�������νڵ㡢�б���Ŀ��,���������JSON��ʽ�Ľڵ�ṹ
 * @author Administrator
 * @version $Id: Node.java, v 0.1 2013-1-30 ����10:37:44 Administrator Exp $
 */
public class Node implements Serializable, Comparable<Node> {
    /** UID */
    private static final long serialVersionUID  = -7353659477150443080L;

    /** �ڵ�ID */
    protected String          id;

    /** �ڵ��ı� */
    protected String          text;

    /** �ڵ�ͼ����ʽ */
    protected String          iconCls;

    /** �ڵ���ʽ */
    protected String          cls;

    /** չ���ĵ����ʽ��true[����չ��],false[˫��չ��] */
    protected boolean         singleClickExpand = true;

    /** �Ƿ�ʹ����ʽ */
    protected boolean         isClass           = true;

    /** �Ƿ���Ҷ�ӽڵ� */
    protected boolean         leaf              = false;

    /** ���ڵ��Ƿ���Ҫhref����*/
    protected boolean         hasParentHref     = true;

    /** ����url */
    protected String          href;

    /** ���ڵ��� */
    protected String          parent;

    /** �ӽڵ� */
    protected List<Node>      children;

    /** ���� */
    protected Integer         sort;

    /** �Ƿ��Ƿ���ڵ� */
    protected boolean         isGroup           = false;

    /**
     * Ĭ�Ϲ���
     */
    public Node() {
        children = new ArrayList<Node>();
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Node [text=" + text + ", children=" + children + "]";
    }

    /** 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Node o) {
        if (sort != null && o.sort != null) {
            return sort.compareTo(o.sort);
        }
        return text.compareTo(o.text);
    }

    /** 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (obj != null && Node.class.isAssignableFrom(obj.getClass())) {
            Node node = (Node) obj;
            equals = new EqualsBuilder().append(id, node.id).append(text, node.text)
                .append(parent, node.parent).isEquals();
        }
        return equals;
    }

    /**
     * ����ݲ�ѯ���ת��Node�����б�
     * 
     * @param rs
     * @return
     */
    public static List<Node> valuesOf(List<TreeNode<?>> tree) {
        if (tree != null && tree.size() > 0) {
            List<Node> nodes = new ArrayList<Node>();
            for (int i = 0, n = tree.size(); i < n; i++) { // ѭ�������
                TreeNode<?> row = tree.get(i);
                Node node = valueOf(row);
                if (node != null) {
                    nodes.add(node);
                }
            }
            return nodes;
        }
        return null;
    }

    /**
     * ����ѯ���ת����Node����
     * 
     * @param entry
     * @return
     */
    public static Node valueOf(TreeNode<?> entry) {
        if (entry != null) {
            Node node = new Node();
            // ʵ��
            node.setId(String.valueOf(entry.getId())); // ���ñ�ʶ��
            node.setLeaf(entry.getNodeType() == TreeNodeType.LEAF ? true : false);
            node.setText(String.valueOf(entry.getText()));
            node.setHref(String.valueOf(entry.getUrl()));
            String iconCls = node.isLeaf() ? "icon-static" : "icon-pkg";
            String cls = node.isLeaf() ? "cls" : "package";
            node.setIconCls(iconCls);
            node.setCls(cls);
            node.setSort(entry.getIndex());
            node.setParent(entry.getParent() != null ? String.valueOf(entry.getParent().getId())
                : null);
            return node;
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public boolean isSingleClickExpand() {
        return singleClickExpand;
    }

    public void setSingleClickExpand(boolean singleClickExpand) {
        this.singleClickExpand = singleClickExpand;
    }

    public boolean isClass() {
        return isClass;
    }

    public void setClass(boolean isClass) {
        this.isClass = isClass;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isHasParentHref() {
        return hasParentHref;
    }

    public void setHasParentHref(boolean hasParentHref) {
        this.hasParentHref = hasParentHref;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

}
