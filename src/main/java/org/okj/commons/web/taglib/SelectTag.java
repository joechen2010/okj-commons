/**
 * @(#)SelectTag.java 2013-1-30
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
package org.okj.commons.web.taglib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import net.sf.cglib.beans.BeanCopier;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.KeyValue;
import org.apache.commons.collections.keyvalue.DefaultKeyValue;
import org.apache.log4j.Logger;
import org.okj.commons.web.taglib.template.impl.SelectOptionsTemplate;
import org.okj.commons.web.taglib.template.model.ControlAttribute;

/**
 * ����������JSP��ǩʵ��
 * @author Administrator
 * @version $Id: SelectTag.java, v 0.1 2013-1-30 ����5:29:22 Administrator Exp $
 */
public class SelectTag extends TagSupport {
    /** UID */
    private static final long   serialVersionUID = -1596826866227915117L;

    /* logger */
    private static final Logger LOGGER           = Logger.getLogger(SelectTag.class);
    /* select�ؼ���name���� */
    protected String            name;

    /* select�ؼ���ͷ��key */
    protected String            headerKey;

    /* select�ؼ���ͷ��value */
    protected String            headerValue;

    /* ��ѡ�е�option��valueֵ */
    protected Object            value;

    /* list�б��ж�������option��Key������� */
    protected String            listKey          = "key";

    /* list�б��ж�������option��value������� */
    protected String            listValue        = "value";

    /* ���Դ���ϣ�������ʾselect����������ṩ2��ֵ��һ������ƣ�������request�����в������Դ���ϣ�����һ����JSON�ַ����������Դ���� */
    protected String            list;

    /* select�ؼ���class���� */
    protected String            styleClass;

    /* select�ؼ���style���� */
    protected String            style;

    /* select�ؼ���onchange�¼� */
    protected String            onchange;

    /* select�ؼ���onclick�¼� */
    protected String            onclick;

    /**
     *
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException {
        String content = "";
        try {
            ControlAttribute attribute = new ControlAttribute();

            //���Ը���
            BeanCopier copier = BeanCopier.create(this.getClass(), ControlAttribute.class, false);
            copier.copy(this, attribute, null);

            content = new SelectOptionsTemplate(attribute) {

                /** 
                 * @see com.lakala.bmcp.tags.template.FormTemplate#getOptions()
                 */
                @Override
                public List<KeyValue> getOptions() {
                    List<KeyValue> options = new ArrayList<KeyValue>();

                    try {
                        //�Ƚ���һ�£����Ƿ���Խ�����JSON���
                        JSONObject json = JSONObject.fromObject(list);
                        Set set = json.entrySet();
                        Iterator it = set.iterator();
                        while (it.hasNext()) {
                            Map.Entry entry = (Map.Entry) it.next();
                            options.add(new DefaultKeyValue(entry.getValue(), entry.getKey()));
                        }
                        return options;
                    } catch (JSONException ex) {
                        LOGGER.warn("list����Json��ݸ�ʽ���޷�������list=" + list + ", error="
                                    + ex.getMessage());
                    }

                    //�����Json��ݣ����request�л�ȡ���Դ����
                    Object obj = pageContext.getRequest().getAttribute(list);
                    if (obj instanceof List) {
                        for (Object o : (List) obj) {
                            Object key, value;

                            try {
                                key = BeanUtils.getNestedProperty(o, listKey);
                                value = BeanUtils.getNestedProperty(o, listValue);

                                options.add(new DefaultKeyValue(key, value));
                            } catch (Exception ex) {
                                LOGGER.warn("������ݼ���ʱ�����쳣��", ex);
                            }
                        }
                    }
                    return options;
                }

            }.render();

            pageContext.getOut().write(content);
            return SKIP_BODY;
        } catch (Exception e) {
            throw new JspException(e.toString());
        }
    }

    /**
     * Getter method for property <tt>name</tt>.
     * 
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     * 
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>headerKey</tt>.
     * 
     * @return property value of headerKey
     */
    public String getHeaderKey() {
        return headerKey;
    }

    /**
     * Setter method for property <tt>headerKey</tt>.
     * 
     * @param headerKey value to be assigned to property headerKey
     */
    public void setHeaderKey(String headerKey) {
        this.headerKey = headerKey;
    }

    /**
     * Getter method for property <tt>headerValue</tt>.
     * 
     * @return property value of headerValue
     */
    public String getHeaderValue() {
        return headerValue;
    }

    /**
     * Setter method for property <tt>headerValue</tt>.
     * 
     * @param headerValue value to be assigned to property headerValue
     */
    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    /**
     * Getter method for property <tt>value</tt>.
     * 
     * @return property value of value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Setter method for property <tt>value</tt>.
     * 
     * @param value value to be assigned to property value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Getter method for property <tt>listKey</tt>.
     * 
     * @return property value of listKey
     */
    public String getListKey() {
        return listKey;
    }

    /**
     * Setter method for property <tt>listKey</tt>.
     * 
     * @param listKey value to be assigned to property listKey
     */
    public void setListKey(String listKey) {
        this.listKey = listKey;
    }

    /**
     * Getter method for property <tt>listValue</tt>.
     * 
     * @return property value of listValue
     */
    public String getListValue() {
        return listValue;
    }

    /**
     * Setter method for property <tt>listValue</tt>.
     * 
     * @param listValue value to be assigned to property listValue
     */
    public void setListValue(String listValue) {
        this.listValue = listValue;
    }

    /**
     * Getter method for property <tt>list</tt>.
     * 
     * @return property value of list
     */
    public String getList() {
        return list;
    }

    /**
     * Setter method for property <tt>list</tt>.
     * 
     * @param list value to be assigned to property list
     */
    public void setList(String list) {
        this.list = list;
    }

    /**
     * Getter method for property <tt>styleClass</tt>.
     * 
     * @return property value of styleClass
     */
    public String getStyleClass() {
        return styleClass;
    }

    /**
     * Setter method for property <tt>styleClass</tt>.
     * 
     * @param styleClass value to be assigned to property styleClass
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * Getter method for property <tt>style</tt>.
     * 
     * @return property value of style
     */
    public String getStyle() {
        return style;
    }

    /**
     * Setter method for property <tt>style</tt>.
     * 
     * @param style value to be assigned to property style
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Getter method for property <tt>onchange</tt>.
     * 
     * @return property value of onchange
     */
    public String getOnchange() {
        return onchange;
    }

    /**
     * Setter method for property <tt>onchange</tt>.
     * 
     * @param onchange value to be assigned to property onchange
     */
    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    /**
     * Getter method for property <tt>onclick</tt>.
     * 
     * @return property value of onclick
     */
    public String getOnclick() {
        return onclick;
    }

    /**
     * Setter method for property <tt>onclick</tt>.
     * 
     * @param onclick value to be assigned to property onclick
     */
    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }
}
