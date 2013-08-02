/**
 * @(#)SimpleWebQQEventPublisher.java 2013-1-24
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;
import org.springframework.stereotype.Component;


public class SimpleEventPublisher implements EventPublisher {
    /* logger */
    private static final Logger     LOGGER    = Logger.getLogger(EventPublisher.class);

    /* 监听器集合 */
    private Set<EventListener> listeners = Collections
                                                  .synchronizedSet(new HashSet<EventListener>());

    /**
     * 构造函数
     */
    public SimpleEventPublisher() {
    }

    /** 
     * @see org.okj.EventPublisher.core.event.WebQQEventPublisher#publish(org.okj.Event.core.event.WebQQEvent)
     */
    @Override
    public void publish(Event event) {
        if (event != null) {
            for (EventListener listener : listeners) {
                listener.onEvent(event);
            }
        }
    }

    /** 
     * @see org.okj.EventPublisher.core.event.WebQQEventPublisher#register(org.okj.EventListener.core.event.WebQQEventListener)
     */
    @Override
    public void register(EventListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
        LogUtils.info(LOGGER, "注册监听器完成, 当前监听器集合大小, listener.size={0}", listeners.size());
    }

    /** 
     * @see org.okj.EventPublisher.core.event.WebQQEventPublisher#unregister(org.okj.EventListener.core.event.WebQQEventListener)
     */
    @Override
    public void unregister(EventListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
        LogUtils.info(LOGGER, "注销监听器完成, 当前监听器集合大小, listener.size={0}", listeners.size());
    }

    /** 
     * @see org.okj.EventPublisher.core.event.WebQQEventPublisher#unregisterAll()
     */
    @Override
    public void unregisterAll() {
        listeners.clear();
    }

	public Set<EventListener> getListeners() {
		return listeners;
	}

	public void setListeners(Set<EventListener> listeners) {
		this.listeners = listeners;
	}
}
