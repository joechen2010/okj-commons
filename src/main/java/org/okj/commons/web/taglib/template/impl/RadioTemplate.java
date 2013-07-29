/**
 * @(#)RadioTemplate.java 2013-1-30
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
import org.okj.commons.web.taglib.template.model.ControlAttribute;
import org.springframework.util.ObjectUtils;

/**
 * radio���ģ��
 * @author Administrator
 * @version $Id: RadioTemplate.java, v 0.1 2013-1-30 ����5:17:06 Administrator Exp $
 */
public abstract class RadioTemplate extends SelectOptionsTemplate {
    /**
     * ���췽��
     * @param attribute
     */
    public RadioTemplate(ControlAttribute attribute) {
        super(attribute);
    }

    /** 
     * @see com.lakala.bmcp.tags.template.impl.SelectOptionsTemplate#createSelectHeader()
     */
    @Override
    protected String createSelectHeader() {
        return "";
    }

    /** 
     * @see com.lakala.bmcp.tags.template.impl.SelectOptionsTemplate#createOptions()
     */
    @Override
    protected String createOptions() {
        List<KeyValue> options = getOptions();

        StringBuffer sb = new StringBuffer();
        if (options != null && options.size() > 0) {
            for (int i = 0, n = options.size(); i < n; i++) {
                sb.append("<input type=\"radio\" ");
                if (StringUtils.isNotBlank(attribute.getId())) {
                    sb.append("id=\"").append(attribute.getId()).append("_" + i).append("\" ");
                }
                sb.append("name=\"").append(attribute.getName()).append("\" ");
                if (StringUtils.isNotBlank(attribute.getStyleClass())) {
                    sb.append("class=\"").append(attribute.getStyleClass()).append("\" ");
                }
                if (StringUtils.isNotBlank(attribute.getStyle())) {
                    sb.append("style=\"").append(attribute.getStyle()).append("\" ");
                }
                if (StringUtils.isNotBlank(attribute.getOnclick())) {
                    sb.append("onclick=\"").append(attribute.getOnclick()).append("\" ");
                }
                KeyValue keyValue = options.get(i);
                sb.append(" value=\"").append(keyValue.getValue());

                String str = ObjectUtils.nullSafeToString(attribute.getValue());
                if (StringUtils.isNotBlank(str)
                    && StringUtils.equalsIgnoreCase(str, String.valueOf(keyValue.getValue()))) {
                    sb.append("\" checked=\"true\" />");
                } else {
                    sb.append("\"/>");
                }
                sb.append("<label for=\"").append(attribute.getId()).append("_" + i).append("\">");
                sb.append(keyValue.getKey()).append("</label>");
            }
        }
        return sb.toString();
    }

    /** 
     * @see com.lakala.bmcp.tags.template.impl.SelectOptionsTemplate#createSelectFooter()
     */
    @Override
    protected String createSelectFooter() {
        return "";
    }

}
