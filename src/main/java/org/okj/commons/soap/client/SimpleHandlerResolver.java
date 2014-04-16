/*
 * $HeadURL: $
 * $Id: $
 * Copyright (c) 2011 by Ericsson, all rights reserved.
 */

package org.okj.commons.soap.client;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

/**
 * A simple HandlerResolver implementation.
 * 
 * @author
 * @version $Revision: $
 */
@SuppressWarnings("rawtypes")
public class SimpleHandlerResolver implements HandlerResolver {

    /** Revision of the class */
    public static final String _REV_ID_ = "$Revision: $";

    private List<Handler> handlerChain;

    public void setHandlerChain(List<Handler> handlerChain) {
        this.handlerChain = handlerChain;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Handler> getHandlerChain(PortInfo portInfo) {
        if (handlerChain == null) {
            handlerChain = new ArrayList<Handler>();
        }
        return handlerChain;
    }

}
