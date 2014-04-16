/**
 * @(#)SystemPropertiesLoader.java 2013-2-16
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 系统属性资源文件加载器实现
 * @author Jack Tan
 * @version $Id: SystemPropertiesLoader.java, v 0.1 2013-2-16 下午3:08:32 Jack Tan Exp $
 */
public class SystemPropertiesLoader implements PropertiesLoader, InitializingBean, DisposableBean {
    /* logger */
    private static final Logger LOGGER            = Logger.getLogger(PropertiesLoader.class);

    /* 默认的配置文件名称 */
    private static final String SYSTEM_PROPS_FILE = "system.properties";

    /* 工作目录的名称 */
    private static final String WORK_HOME_NAME    = "work.home";

    /* 配置文件路径 */
    private String              location;

    /** 
     * @see org.storevm.eosgi.properties.loader.PropertiesLoader#load()
     */
    @Override
    public void load() {
        //1. 从系统属性中读取工作目录
        if (StringUtils.isNotBlank(System.getProperty(WORK_HOME_NAME))) {
            this.location = System.getProperty(WORK_HOME_NAME);
        }

        //2. 从用户目录中读取工作目录
        if (StringUtils.isBlank(location)) {
            this.location = System.getProperty("user.dir");
        }

        //3. 实例化文件句柄
        File config = new File(this.location);

        //检查是否是文件，如果是文件则直接加载
        if (!config.isFile()) {
            config = new File(config, SYSTEM_PROPS_FILE);
            LogUtils.info(LOGGER, "读取到全局配置文件路径, location={0}", config.getAbsolutePath());
        }

        try {
            if (!config.exists()) {
                config.createNewFile(); //不存在，则创建
            }

            //读取属性值
            Properties props = new Properties();
            props.load(new FileInputStream(config));
            Set<String> names = props.stringPropertyNames();
            for (String name : names) {
                System.setProperty(name, props.getProperty(name));
            }
        } catch (IOException ex) {
            LogUtils.error(LOGGER, "读取全局配置文件时发生异常", ex);
        }
    }

    /** 
     * @see org.storevm.eosgi.properties.loader.PropertiesLoader#unload()
     */
    @Override
    public void unload() {
    }

    /** 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        load();
    }

    /** 
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    @Override
    public void destroy() throws Exception {
        unload();
    }

    /** 
     * @see org.storevm.eosgi.properties.loader.PropertiesLoader#getProperty(java.lang.String)
     */
    @Override
    public String getProperty(String name) {
        return System.getProperty(name);
    }

    /** 
     * @see org.storevm.eosgi.properties.loader.PropertiesLoader#getProperties()
     */
    @Override
    public Properties getProperties() {
        return System.getProperties();
    }

    /**
     * Setter method for property <tt>location</tt>.
     * 
     * @param location value to be assigned to property location
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
