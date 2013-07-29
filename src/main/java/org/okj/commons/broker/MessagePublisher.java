/**
 * @(#)MessagePublisher.java 2013-1-30
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
package org.okj.commons.broker;

public interface MessagePublisher {
    /* �������� */
    public static final String EVENT_CODE = "EVENT_CODE";
    public static final String SERVER_ID  = "SERVER_ID";
    public static final String DEST_HOST  = "DEST_HOST";
    public static final String TOPIC_NAME = "TOPIC_NAME";

    public void publishMessage(MessageEvent event);
}
