/**
 * @(#)RadioTag.java 2013-1-30
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

import net.sf.cglib.beans.BeanCopier;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.KeyValue;
import org.apache.commons.collections.keyvalue.DefaultKeyValue;
import org.apache.log4j.Logger;
import org.okj.commons.web.taglib.template.impl.RadioTemplate;
import org.okj.commons.web.taglib.template.model.ControlAttribute;

/**
 * ��ѡ���ǩ
 * @author Administrator
 * @version $Id: RadioTag.java, v 0.1 2013-1-30 ����5:30:48 Administrator Exp $
 */
public class RadioTag extends SelectTag {
    /** UID */
    private static final long   serialVersionUID = -7578995800541600194L;

    /* logger */
    private static final Logger LOGGER           = Logger.getLogger(RadioTag.class);

    /** 
     * @see com.lakala.bmcp.tags.SelectTag#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException {
        String content = "";
        try {
            ControlAttribute attribute = new ControlAttribute();

            //���Ը���
            BeanCopier copier = BeanCopier.create(this.getClass(), ControlAttribute.class, false);
            copier.copy(this, attribute, null);

            content = new RadioTemplate(attribute) {

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
                            options.add(new DefaultKeyValue(entry.getKey(), entry.getValue()));
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
}
