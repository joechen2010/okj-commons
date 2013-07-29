/**
 * @(#)FormTemplate.java 2013-1-30
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
package org.okj.commons.web.taglib.template;

import java.util.List;

import org.apache.commons.collections.KeyValue;

/**
 * �?�����ģ��ӿڶ���
 * @author Administrator
 * @version $Id: FormTemplate.java, v 0.1 2013-1-30 ����5:13:03 Administrator Exp $
 */
public interface FormTemplate {
    /**
     * ��Ⱦ
     * @return
     */
    public String render();

    /**
     * 
     * @return
     */
    public List<KeyValue> getOptions();
}
