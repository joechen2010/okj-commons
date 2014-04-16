package org.okj.commons.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class QueueSender {
	
	private String brokerUri;
	
	private String queueName;
	
	@Autowired
	private final JmsTemplate jmsTemplate = null;

	public void send(final Object message) {
		jmsTemplate.convertAndSend(queueName, message);
	}

	public String getBrokerUri() {
		return brokerUri;
	}

	public void setBrokerUri(String brokerUri) {
		this.brokerUri = brokerUri;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	
	
}