/*
 * $HeadURL: $
 * $Id: $
 * Copyright (c) 2014 by Ericsson, all rights reserved.
 */

package org.okj.commons.soap.server;

import java.util.Locale;

import javax.xml.ws.soap.SOAPFaultException;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.AbstractEndpointExceptionResolver;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.soap11.Soap11Body;

public class FaultCodeSoapExceptionResolver extends AbstractEndpointExceptionResolver {

    /** Revision of the class */
    public static final String _REV_ID_ = "$Revision: $";

    @Override
    protected final boolean resolveExceptionInternal(MessageContext messageContext, Object endpoint, Exception ex) {
        Assert.isInstanceOf(SoapMessage.class, messageContext.getResponse(),
                "CfsiCBiOFaultCodeSoapExceptionResolver requires a SoapMessage");
        SoapMessage response = (SoapMessage) messageContext.getResponse();
        Soap11Body body = (Soap11Body) response.getSoapBody();
        SOAPFaultException origException = (SOAPFaultException) ex;
        String faultString = StringUtils.hasLength(ex.getMessage()) ? ex.getMessage() : ex.toString();
        body.addFault(origException.getFault().getFaultCodeAsQName(), faultString, Locale.ENGLISH);
        return true;
    }
}
