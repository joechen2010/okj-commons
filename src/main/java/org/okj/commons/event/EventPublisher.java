/**
 * @(#)WebQQEventListener.java 2013-1-24
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
package org.okj.commons.event;


 
public interface EventPublisher {
    /**
     * 发布一个事件
     * @param event
     */
    void publish(Event event);

    /**
     * 注册一个消息处理器
     * @param listener
     */
    void register(EventListener listener);

    /**
     * 注销一个消息处理器
     * @param handler
     */
    void unregister(EventListener listener);

    /**
     * 注销所有的消息处理器
     */
    void unregisterAll();
}
