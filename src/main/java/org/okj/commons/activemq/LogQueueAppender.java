package org.okj.commons.activemq;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

/**
 * JMSQueue appender is a log4j appender that writes LoggingEvent to a queue.
 * 
 */
public class LogQueueAppender extends AppenderSkeleton implements Appender {


	private String brokerUri;
	
	private String queueName;
	
	@Autowired
	private final JmsTemplate jmsTemplate = null;
	
	@Override
	public void close() {

	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	protected synchronized void append(LoggingEvent event) {

		try {
			
			jmsTemplate.convertAndSend(queueName, new LoggingEventWrapper(event));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setBrokerUri(String brokerUri) {
		this.brokerUri = brokerUri;
	}

	public String getBrokerUri() {
		return brokerUri;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getQueueName() {
		return queueName;
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	
	
}