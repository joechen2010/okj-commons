/**
 * @(#)SimpleMessagePublisher.java 2013-1-30
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
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 简单的消息发布器实现
 * @author Administrator
 * @version $Id: SimpleMessagePublisher.java, v 0.1 2013-1-30 上午11:10:37 Administrator Exp $
 */
public class SimpleMessagePublisher implements MessagePublisher, InitializingBean, DisposableBean {
    /* logger */
    private static final Logger LOGGER = Logger.getLogger(MessagePublisher.class);

    /* JMS连接工厂 */
    private ConnectionFactory   connectionFactory;

    /* JMS目的地名称 */
    private String              topicName;

    /* 主题连接 */
    private TopicConnection     connection;

    /* 主题会话 */
    private TopicSession        session;

    /* 消息主题 */
    private Topic               topic;

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

                //2. 启动主题消息队列
                this.connection.start();

                LogUtils.info(LOGGER, "初始化主题消息队列完成，topicName={0}", topicName);
            }
        } catch (JMSException ex) {
            LogUtils.error(LOGGER, "创建主题消息队列时发生异常", ex);
        }
    }

    /** 
     * @see org.storevm.commons.broker.MessagePublisher#publishMessage(org.storevm.commons.broker.MessageEvent)
     */
    @Override
    public void publishMessage(MessageEvent event) {
        try {
            //1. 创建消息发布者
            TopicPublisher publisher = this.session.createPublisher(this.topic);

            //2. 创建消息并发布消息
            ObjectMessage message = session.createObjectMessage(event);

            //3. 添加消息过滤条件
            message.setStringProperty(SERVER_ID, event.getAttribute(DEST_HOST));
            message.setStringProperty(EVENT_CODE, event.getEventCode());
            message.setStringProperty(TOPIC_NAME, topicName);

            //4. 发送消息
            publisher.publish(message);

            LogUtils.info(LOGGER, "消息事件发布完成，event={0}", event);
        } catch (JMSException ex) {
            LogUtils.error(LOGGER, "发布消息事件时发生异常", ex);
        }
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
}
