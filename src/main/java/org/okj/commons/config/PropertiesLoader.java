/**
 * @(#)PropertiesLoader.java 2013-2-16
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
package org.okj.commons.config;

import java.util.Properties;

/**
 * 属性资源文件加载器接口定义
 * @author Jack Tan
 * @version $Id: PropertiesLoader.java, v 0.1 2013-2-16 下午3:06:30 Jack Tan Exp $
 */
public interface PropertiesLoader {
    /**
     * 加载
     */
    void load();

    /**
     * 卸载
     */
    void unload();

    /**
     * 返回指定键值的属性值
     * @param name
     * @return
     */
    String getProperty(String name);

    /**
     * 返回所有的属性值
     * @return
     */
    Properties getProperties();
}
