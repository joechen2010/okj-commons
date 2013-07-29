/**
 * @(#)SimpleMessageSubscriber.java 2013-1-30
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

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 简单的消息监听器实现
 * @author Administrator
 * @version $Id: SimpleMessageSubscriber.java, v 0.1 2013-1-30 上午11:13:16 Administrator Exp $
 */
public class SimpleMessageSubscriber implements MessageListener, InitializingBean, DisposableBean {
    /* logger */
    private static final Logger LOGGER = Logger.getLogger(SimpleMessageSubscriber.class);

    /* 调度任务执行器，用于执行具体的逻辑业务处理 */
    private TaskExecutor        executor;

    /* JMS连接工厂 */
    private ConnectionFactory   connectionFactory;

    /* 主题连接 */
    private TopicConnection     connection;

    /* 主题会话 */
    private TopicSession        session;

    /* 消息主题 */
    private Topic               topic;

    /* JMS目的地名称 */
    private String              topicName;

    /* 消息过滤器 */
    private String              selector;

    /** 
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    @Override
    public void destroy() throws Exception {
        if (this.connection != null) {
            try {
                this.connection.close();

                LogUtils.info(LOGGER, "关闭主题消息队列完成，topicName={0}", topicName);
            } catch (JMSException ex) {
                LogUtils.error(LOGGER, "关闭消息队列连接时发生异常", ex);
            }
        }
    }

    /** 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //当bean的属性被设置完成之后，做一些实例变量的初始化工作
        try {
            if (connectionFactory != null) {
                //1. 创建主题消息队列连接
                this.connection = ((TopicConnectionFactory) connectionFactory)
                    .createTopicConnection();

                //2. 创建主题消息队列会话
                this.session = this.connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

                //3. 创建主题
                this.topic = session.createTopic(topicName);

                //4. 创建消息监听器
                TopicSubscriber subscriber = null;
                if (StringUtils.isNotBlank(selector)) {
                    subscriber = session.createSubscriber(topic, selector, false); //消息过滤
                } else {
                    subscriber = session.createSubscriber(topic);
                }
                subscriber.setMessageListener(this);

                //2. 启动主题消息队列
                this.connection.start();

                LogUtils.info(LOGGER, "初始化主题消息队列完成，topicName={0}", topicName);
            }
        } catch (JMSException ex) {
            LogUtils.error(LOGGER, "创建主题消息队列时发生异常", ex);
        }
    }

    /** 
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    @Override
    public void onMessage(Message message) {
        MessageEvent event = null;
        try {
            //查找消息事件
            if (message instanceof ObjectMessage) {
                ObjectMessage msg = (ObjectMessage) message;
                if (msg.getObject() instanceof MessageEvent) {
                    event = (MessageEvent) msg.getObject();
                }
            }

            //检查消息事件对象是否为空
            if (event == null) {
                LogUtils.warn(LOGGER, "不是系统关注的消息类型，放弃当前消息, message={0}", message);
                return;
            }

            if (this.executor != null) {
                //调用业务逻辑执行器
                this.executor.execute(event);
            }
        } catch (JMSException ex) {
            LogUtils.error(LOGGER, "处理接收消息的时候发生异常", ex);
        } catch (Exception ex) {
            LogUtils.error(LOGGER, "调用业务逻辑执行器时候发生异常", ex);
        }
    }

    /**
     * Setter method for property <tt>executor</tt>.
     * 
     * @param executor value to be assigned to property executor
     */
    public void setExecutor(TaskExecutor executor) {
        this.executor = executor;
    }

    /**
     * Setter method for property <tt>connectionFactory</tt>.
     * 
     * @param connectionFactory value to be assigned to property connectionFactory
     */
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * Setter method for property <tt>topicName</tt>.
     * 
     * @param topicName value to be assigned to property topicName
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    /**
     * Setter method for property <tt>selector</tt>.
     * 
     * @param selector value to be assigned to property selector
     */
    public void setSelector(String selector) {
        this.selector = selector;
    }
}
