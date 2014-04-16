/*
 * $HeadURL: $
 * $Id: $
 * Copyright (c) 2011 by Ericsson, all rights reserved.
 */

package org.okj.commons.soap.client;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The UsernamePasswordCredentialHandler for Web Service.
 * 
 * @author
 * @version $Revision: $
 */
public class UsernamePasswordCredentialHandler implements SOAPHandler<SOAPMessageContext> {

    /** Revision of the class */
    public static final String _REV_ID_ = "$Revision: $";

    private static final Logger LOGGER = LoggerFactory.getLogger(UsernamePasswordCredentialHandler.class);

    private static final String SECURITY_URL = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";

    private static final String PASSWORD_TYPE_URL = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText";

    private static final String PREFIX = "wsse";

    private static final String ELEMENT_SECURITY = "Security";

    private static final String ELEMENT_USERNAME_TOKEN = "UsernameToken";

    private static final String ELEMENT_USERNAME = "Username";

    private static final String ELEMENT_PASSWORD = "Password";

    private static final String ATTRIBUTE_TYPE = "Type";

    private String username;

    private String password;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleMessage(SOAPMessageContext smc) {
        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (outboundProperty.booleanValue() && username != null && !"".equals(username.trim())) {
            try {
                SOAPEnvelope envelope = smc.getMessage().getSOAPPart().getEnvelope();
                SOAPHeader header = envelope.getHeader();
                if (header == null) {
                    header = envelope.addHeader();
                }
                SOAPElement securityElement = header.addChildElement(ELEMENT_SECURITY, PREFIX, SECURITY_URL);
                SOAPElement usernameTokenElement = securityElement.addChildElement(ELEMENT_USERNAME_TOKEN, PREFIX);
                SOAPElement usernameElement = usernameTokenElement.addChildElement(ELEMENT_USERNAME, PREFIX);
                usernameElement.addTextNode(username);
                SOAPElement passwordElement = usernameTokenElement.addChildElement(ELEMENT_PASSWORD, PREFIX);
                passwordElement.setAttribute(ATTRIBUTE_TYPE, PASSWORD_TYPE_URL);
                passwordElement.addTextNode(password);
            } catch (SOAPException e) {
                LOGGER.error("", e);
            }
        }
        return outboundProperty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close(MessageContext context) {
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
