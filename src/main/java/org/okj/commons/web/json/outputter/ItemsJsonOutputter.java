/**
 * @(#)ItemsJsonOutputter.java 2013-1-30
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.okj.commons.model.ItemOption;

/**
 * ���б���Ŀ���ΪJSON��ݸ�ʽ
 * @author Administrator
 * @version $Id: ItemsJsonOutputter.java, v 0.1 2013-1-30 ����3:26:07 Administrator Exp $
 */
public class ItemsJsonOutputter extends AbstractJsonOutputter {

    /** 
     * @see org.storevm.commons.web.json.outputter.JsonOutputter#output(java.lang.Object)
     */
    @Override
    public String output(Object result) {
        if (!(result instanceof Collection)) {
            //������Ǽ��ϣ����˳�
            return "{}";
        }

        Collection<ItemOption> collection = (Collection<ItemOption>) result; //ת������
        JSONArray options = new JSONArray();
        for (ItemOption option : collection) {
            JSONObject item = new JSONObject(); // ����һ��JSON����
            item.put("id", option.getId());
            item.put("text", option.getText());
            options.add(item);
        }
        return options.toString();
    }

}
