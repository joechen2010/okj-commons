/**
 * @(#)TreeNodeType.java 2013-1-30
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

/**
 * 树形节点类型枚举
 * @author Administrator
 * @version $Id: TreeNodeType.java, v 0.1 2013-1-30 上午10:44:21 Administrator Exp $
 */
public enum TreeNodeType {
    //叶子
    LEAF("叶子"),

    //节点
    NODE("节点"),

    //根
    ROOT("根");

    ;

    /* 常量描述 */
    private String description;

    /**
     * 构造方法
     * @param description
     */
    TreeNodeType(String description) {
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
}
