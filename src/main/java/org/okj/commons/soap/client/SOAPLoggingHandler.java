/*
 * $HeadURL: $
 * $Id: $
 * Copyright (c) 2014 by Ericsson, all rights reserved.
 */

package org.okj.commons.soap.client;

import java.io.ByteArrayOutputStream;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 
 * @author enrrssw
 * @version $Revision: $ This simple SOAPHandler will output the contents of
 *          incoming and outgoing messages.
 */
public class SOAPLoggingHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SOAPLoggingHandler.class);

    /** Revision of the class */
    public static final String _REV_ID_ = "$Revision: $";
    
    private static final String RECEIVED = "Received";
    private static final String SEND = "Send";


    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext smc) {
        logMessage(smc);
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext smc) {
        logMessage(smc);
        return true;
    }

    @Override
    public void close(MessageContext messageContext) {

    }

    private void logMessage(SOAPMessageContext context) {
        if (context == null) {
            return;
        }
        if (LOGGER.isDebugEnabled()) {
            try {
                Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
                String type = outboundProperty.booleanValue() ? SEND : RECEIVED;
                final ByteArrayOutputStream bout = new ByteArrayOutputStream();
                Transformer tf = TransformerFactory.newInstance().newTransformer();
                tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tf.setOutputProperty(OutputKeys.INDENT, "yes");
                tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                tf.transform(new DOMSource(context.getMessage().getSOAPPart()), new StreamResult(bout));
                String message = bout.toString();
                String hs = java.text.MessageFormat.format("{0} Message: Length={1}",
                        new Object[] { type, Integer.valueOf(message.length()) });
                LOGGER.debug("LogSOAPMessage: " + hs);
                LOGGER.debug(message);
            } catch (Exception e) {
                LOGGER.error("Failed to log SOAP Message: ", e);
            }
        }

    }

}