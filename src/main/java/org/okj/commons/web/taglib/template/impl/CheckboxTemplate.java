/**
 * @(#)CheckboxTemplate.java 2013-1-30
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.KeyValue;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.okj.commons.web.taglib.template.FormTemplate;
import org.okj.commons.web.taglib.template.model.ControlAttribute;

/**
 * ��ѡ��ģ��
 * @author Administrator
 * @version $Id: CheckboxTemplate.java, v 0.1 2013-1-30 ����5:18:19 Administrator Exp $
 */
public abstract class CheckboxTemplate implements FormTemplate {
    /* logger */
    private static final Logger LOGGER = Logger.getLogger(FormTemplate.class);

    /* ��������� */
    private ControlAttribute    attribute;

    /**
     * ���췽��
     * @param attribute
     */
    public CheckboxTemplate(ControlAttribute attribute) {
        this.attribute = attribute;
    }

    /** 
     * @see com.lakala.bmcp.tags.template.FormTemplate#render()
     */
    @Override
    public String render() {
        StringBuffer sb = new StringBuffer();
        List<KeyValue> options = getOptions();
        for (int i = 0, n = options.size(); i < n; i++) {
            sb.append(create(options.get(i), i));
        }
        return sb.toString();
    }

    protected String create(KeyValue option, int index) {
        StringBuffer sb = new StringBuffer();
        sb.append("<div ");
        if (StringUtils.isNotBlank(attribute.getStyle())) {
            sb.append("style=\"").append(attribute.getStyle()).append("\" ");
        } else {
            sb.append("style=\"float:left; padding-left:2px;\" ");
        }
        sb.append(">");
        sb.append(createCheckbox(option, index)).append(createCheckboxLabel(option, index));
        return sb.append("</div>").toString();
    }

    protected String createCheckbox(KeyValue option, int index) {
        StringBuffer sb = new StringBuffer();
        sb.append("<input type=\"checkbox\" ");
        //���id����
        if (StringUtils.isNotBlank(attribute.getId())) {
            sb.append("id=\"").append(attribute.getId()).append("_").append(index).append("\" ");
        }
        //���name����
        sb.append("name=\"").append(getIndexName(attribute.getName(), index)).append("\" ");
        //���class����
        if (StringUtils.isNotBlank(attribute.getStyleClass())) {
            sb.append("class=\"").append(attribute.getStyleClass()).append("\" ");
        }
        sb.append("value=\"").append(option.getValue()).append("\" ");
        //�Ƿ�Ĭ��ѡ��
        if (isChecked(option)) {
            sb.append("checked=\"true\" ");
        }
        sb.append("/>");
        return sb.toString();
    }

    /**
     * ���checkbox����������
     * @return
     */
    protected String createCheckboxLabel(KeyValue option, int index) {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotBlank(attribute.getId())) {
            sb.append("&nbsp;<label for=\"").append(attribute.getId()).append("_").append(index)
                .append("\">");
        }
        sb.append(option.getKey());
        if (StringUtils.isNotBlank(attribute.getId())) {
            sb.append("</label>");
        }
        return sb.toString();
    }

    /**
     * ��鸴ѡ���Ƿ�ѡ��
     * @param option
     * @return
     */
    private boolean isChecked(KeyValue option) {
        //��ȡvalue����
        Object value = attribute.getValue();
        if (value == null) {
            return false;
        }

        List<Object> values = new ArrayList<Object>();

        if (value.getClass().isArray()) {
            //���������
            values.addAll(Arrays.asList((Object[]) value));
        } else if (value instanceof Collection) {
            //����Ǽ���
            values.addAll((Collection<?>) value);
        } else if (value.getClass().isAssignableFrom(String.class)) {
            //������ַ�
            values.add(value);
        }

        Object val = null;
        for (Object obj : values) {
            //���listValue��Ϊ����Ԫ�ز�ΪString���ͣ�����ȡ����ֵ
            if (StringUtils.isNotBlank(attribute.getListValue())
                && !obj.getClass().isAssignableFrom(String.class)) {
                try {
                    val = BeanUtils.getNestedProperty(obj, attribute.getListValue());
                } catch (Exception ex) {
                    LOGGER.error(ex);
                }
            }

            if (obj.getClass().isAssignableFrom(String.class)) {
                val = obj;
            }

            //�Ƚ�ֵ�Ƿ�һ��
            if (StringUtils
                .equalsIgnoreCase(String.valueOf(val), String.valueOf(option.getValue()))) {
                return true;
            }
        }

        return false;
    }

    /**
     * ���ش������name
     * @param name
     * @param index
     * @return
     */
    private String getIndexName(String name, int index) {
        String indexName = "[" + index + "]";
        String checkboxName = "";
        //Ѱ���ַ����Ƿ��Ѿ������������
        if (StringUtils.indexOf(name, "[]") >= 0) {
            checkboxName = StringUtils.replace(name, "[]", indexName);
        }
        checkboxName = name + indexName;
        if (StringUtils.isNotBlank(attribute.getListValue())) {
            checkboxName += "." + attribute.getListValue();
        }
        return checkboxName;
    }

}
