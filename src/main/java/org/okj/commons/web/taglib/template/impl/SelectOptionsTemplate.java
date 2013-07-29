/**
 * @(#)SelectOptionsTemplate.java 2013-1-30
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
package org.okj.commons.web.taglib.template.impl;

import java.util.List;

import org.apache.commons.collections.KeyValue;
import org.apache.commons.lang.StringUtils;
import org.okj.commons.web.taglib.template.FormTemplate;
import org.okj.commons.web.taglib.template.model.ControlAttribute;
import org.springframework.util.ObjectUtils;

/**
 * ������ģ��
 * @author Administrator
 * @version $Id: SelectOptionsTemplate.java, v 0.1 2013-1-30 ����5:15:55 Administrator Exp $
 */
public abstract class SelectOptionsTemplate implements FormTemplate {
    /* ��������� */
    protected ControlAttribute attribute;

    /**
     * ���췽��
     * @param attribute
     */
    public SelectOptionsTemplate(ControlAttribute attribute) {
        this.attribute = attribute;
    }

    /** 
     * @see com.lakala.bmcp.tags.template.FormTemplate#render()
     */
    @Override
    public String render() {
        StringBuffer sb = new StringBuffer();
        sb.append(createSelectHeader());
        sb.append(createOptions());
        sb.append(this.createSelectFooter());
        return sb.toString();
    }

    /**
     * ���select�ؼ��Ŀ�ʼhtml����
     * @return
     */
    protected String createSelectHeader() {
        StringBuffer sb = new StringBuffer();
        sb.append("<select ");
        if (StringUtils.isNotBlank(attribute.getId())) {
            sb.append("id=\"").append(attribute.getId()).append("\" ");
        }
        sb.append("name=\"").append(attribute.getName()).append("\" ");
        if (StringUtils.isNotBlank(attribute.getStyleClass())) {
            sb.append("class=\"").append(attribute.getStyleClass()).append("\" ");
        }
        if (StringUtils.isNotBlank(attribute.getStyle())) {
            sb.append("style=\"").append(attribute.getStyle()).append("\" ");
        }
        if (StringUtils.isNotBlank(attribute.getOnchange())) {
            sb.append("onchange=\"").append(attribute.getOnchange()).append("\" ");
        }
        if (StringUtils.isNotBlank(attribute.getOnclick())) {
            sb.append("onclick=\"").append(attribute.getOnclick()).append("\" ");
        }
        sb.append(">");
        return sb.toString();
    }

    /**
     * ���select�ؼ���optionsԪ��
     * @return
     * @throws Exception
     */
    protected String createOptions() {
        List<KeyValue> options = getOptions();

        StringBuffer sb = new StringBuffer();
        boolean alreadySelected = false;// �Ƿ��Ѿ��ҵ�Ĭ��ѡ�е�option
        if (attribute.getHeaderValue() != null && attribute.getHeaderKey() != null) {
            sb.append("<option value=\"").append(attribute.getHeaderValue()).append("\">")
                .append(attribute.getHeaderKey()).append("</option>");
        }

        if (options != null && options.size() > 0) {
            for (int i = 0, n = options.size(); i < n; i++) {
                KeyValue keyValue = options.get(i);
                sb.append("<option value=\"").append(keyValue.getValue());
                String str = ObjectUtils.nullSafeToString(attribute.getValue());

                if (StringUtils.isNotBlank(str)
                    && StringUtils.equalsIgnoreCase(str, String.valueOf(keyValue.getValue()))
                    && !alreadySelected) {
                    sb.append("\" selected >");
                    alreadySelected = true;
                } else {
                    sb.append("\">");
                }
                sb.append(keyValue.getKey()).append("</option>");
            }
        }
        return sb.toString();
    }

    protected String createSelectFooter() {
        return "</select>";
    }

}
