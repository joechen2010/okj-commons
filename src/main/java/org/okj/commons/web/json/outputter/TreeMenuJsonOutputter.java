/**
 * @(#)TreeMenuJsonOutputter.java 2013-1-30
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
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.okj.commons.model.Node;

/**
 * ������Ŀ¼�ṹ���ΪJSON���
 * @author Administrator
 * @version $Id: TreeMenuJsonOutputter.java, v 0.1 2013-1-30 ����3:15:20 Administrator Exp $
 */
public class TreeMenuJsonOutputter extends AbstractJsonOutputter {

    /** 
     * @see org.storevm.commons.web.json.outputter.JsonOutputter#output(java.lang.Object)
     */
    @Override
    public String output(Object result) {
        if (!(result instanceof Collection)) {
            //������Ǽ��ϣ����˳�
            return "{}";
        }

        Collection<Node> collection = (Collection<Node>) result; //ת������
        JSONArray root = new JSONArray(); // ���������
        createJsonData(collection, root);
        return root.toString();
    }

    /**
     * ���JSON��ݸ�ʽ����(JQUERYר��)
     * 
     * @param nodes
     * @param root
     */
    private void createJsonData(Collection<Node> nodes, JSONArray root) {
        for (Node node : nodes) { // ���
            JSONObject data = new JSONObject(); // ����һ��JSON����
            // �����������
            data.put("id", node.getId());
            data.put("text", node.getText());
            boolean group = node.isGroup();
            if (node.getChildren().isEmpty() && !node.isLeaf()) {
                group = true;
            }
            data.put("group", group);
            if (!node.isLeaf()) {
                data.put("state", "closed");
            }
            List<Node> children = node.getChildren(); // ��ȡ�ӽڵ�
            if (children.size() > 0) { // �������ӽڵ㣬����еݹ鴦��
                Collections.sort(children); // �ȶ��ӽڵ��������
                JSONArray child = new JSONArray();
                createJsonData(children, child); // �ݹ����
                data.put("children", child);
            }
            root.add(data); // ���뼯����
        }
    }
}
