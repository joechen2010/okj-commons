/**
 * @(#)MessageResultJsonOutputter.java 2013-1-30
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

import net.sf.json.JSONObject;

import org.okj.commons.model.MessageResult;

/**
 * ��Ϣ����JSON���������
 * @author Administrator
 * @version $Id: MessageResultJsonOutputter.java, v 0.1 2013-1-30 ����3:10:41 Administrator Exp $
 */
public class MessageResultJsonOutputter extends AbstractJsonOutputter {

    /** 
     * @see org.storevm.commons.web.json.outputter.JsonOutputter#output(java.lang.Object)
     */
    @Override
    public String output(Object result) {
        if (!(result instanceof MessageResult)) {
            //������Ǽ��ϣ����˳�
            return "{}";
        }

        MessageResult message = (MessageResult) result; //ת������
        JSONObject root = new JSONObject(); // ����һ��JSON����
        root.put("success", message.isSuccess());
        root.put("resultCode", message.getResultCode());
        root.put("message", message.getMessage());
        root.putAll(message.getProperties());
        return root.toString();
    }

}
