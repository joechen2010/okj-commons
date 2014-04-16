package org.okj.commons.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

public class QueueListener implements MessageListener{
	public static Logger logger = Logger.getLogger(QueueListener.class);

    public void onMessage( final Message message )
    {
        if ( message instanceof ObjectMessage ){
        	handleMessage(message);
	    }
    }
    
    protected void handleMessage(Message message){
    	try {
			final IMessage loggingEventWrapper = (LoggingEventWrapper)((ObjectMessage) message).getObject();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}