/**
 * @(#)ListJsonOutputter.java 2013-1-30
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
package org.okj.commons.web.json.outputter;

import java.util.Collection;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.okj.commons.logger.LogUtils;
import org.okj.commons.model.FieldSet.Field;

/**
 * ���������ΪJSON��ʽ
 * @author Administrator
 * @version $Id: ListJsonOutputter.java, v 0.1 2013-1-30 ����2:27:54 Administrator Exp $
 */
public class ListJsonOutputter extends AbstractJsonOutputter {

    /* Ĭ����ҳ�� */
    private static final Integer DEFAULT_PAGE_COUNT   = 1;

    /* Ĭ�ϵ�ǰҳ�� */
    private static final Integer DEFAULT_CURRENT_PAGE = 1;

    /** 
     * @see org.storevm.commons.web.json.outputter.JsonOutputter#output(java.lang.Object)
     */
    @Override
    public String output(Object result) {
        if (!(result instanceof Collection)) {
            //������Ǽ��ϣ����˳�
            return "{}";
        }

        Collection<?> collection = (Collection<?>) result; //ת������
        JSONObject json = getJSON(collection); //����JSON�ṹ

        return json.toString();
    }

    /**
     * �����ܵ�JSON���
     * @return
     */
    protected JSONObject getJSON(Collection<?> collection) {
        JSONObject root = new JSONObject(); // ����һ��JSON����
        //���÷�ҳ����
        Integer curPage = pager != null ? pager.getCurrentPage() : DEFAULT_CURRENT_PAGE;
        Integer recordCount = pager != null ? pager.getRecordCount() : collection.size();
        Integer pageCount = pager != null ? pager.getPageCount() : DEFAULT_PAGE_COUNT;
        Integer perPage = pager != null ? pager.getRecordPerPage() : recordCount;
        root.put("page", curPage);
        root.put("total", pageCount);
        root.put("records", recordCount);
        root.put("rowNum", perPage);

        JSONArray rows = getRows(collection);
        root.put("rows", rows); //�������м��뵽��JSON��
        return root;
    }

    /**
     * �����϶���ת����Json����
     * @param collection
     * @return
     */
    protected JSONArray getRows(Collection<?> collection) {
        JSONArray rows = new JSONArray();
        //������ת���ɶ�������
        Object[] result = collection.toArray(new Object[] {});
        for (int i = 0, n = result.length; i < n; i++) {
            JSONObject row = getRow(result[i]);
            rows.add(row);
        }
        return rows;
    }

    /**
     * ����һ����ݵ�JSON�ṹ
     * @param object
     * @return
     */
    protected JSONObject getRow(Object object) {
        JSONObject entry = new JSONObject(); // ����һ��JSON����
        String id = getId(object); //���ر�ʶ
        entry.put("id", (StringUtils.isNotBlank(fields.getName()) ? fields.getName() : "row") + id); //��ID

        JSONArray cells = getCells(object);
        entry.put("cell", cells);
        return entry;
    }

    /**
     * ����ID��ʶ
     * @param object
     * @return
     */
    protected String getId(Object object) {
        String id = "";
        //��ȡ����ı�ʶ��ID
        try {
            id = String.valueOf(PropertyUtils.getProperty(object, "id"));
        } catch (Exception ex) {
            LOGGER.warn("��ǰ������ID����ֵ����ʹ��Ĭ��IDֵ");
        }

        //���IDֵΪ�գ������ȡUUID
        if (StringUtils.isBlank(id) || StringUtils.equalsIgnoreCase("null", id)) {
            id = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
        }

        return id;
    }

    /**
     * ����һ����ݵ��ֶμ���
     * @return
     */
    protected JSONArray getCells(Object object) {
        //������Ķ������������
        expression.addContext(StringUtils.uncapitalize(object.getClass().getSimpleName()), object);
        JSONArray cells = new JSONArray();
        for (Field field : fields.getFields()) {
            try {
                //��ȡ������ʽ����
                Object value = expression.evaluate(field.getName());
                cells.add(String.valueOf(value));
            } catch (Exception ex) {
                LogUtils.warn(LOGGER, "�����ֶ�����ʱ�����쳣", ex);
                cells.add(StringUtils.EMPTY);
            }
        }
        return cells;
    }
}
