/**
 * @(#)AbstractSpringTestCase.java 2013-1-30
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
package org.okj.commons.test;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ���Ի���
 * @author Administrator
 * @version $Id: AbstractSpringTestCase.java, v 0.1 2013-1-30 ����11:32:28 Administrator Exp $
 */
public abstract class AbstractSpringTestCase extends TestCase {
    /** Spring������ */
    protected ApplicationContext context;

    /** 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        //��ʼ��Spring������
        context = new ClassPathXmlApplicationContext(getSpringConfigLocation());
    }

    /**
     * ����Spring�������ļ���ѯ·��
     * 
     * @return
     */
    protected String getSpringConfigLocation() {
        return "classpath*:applicationContext.xml";
    }
}
