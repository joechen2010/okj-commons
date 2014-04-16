/*
 * $HeadURL: $
 * 
 * Copyright (c) 2014 by Ericsson, all rights reserved.
 */
package org.okj.commons.soap.client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.handler.HandlerResolver;

import org.okj.commons.reflection.ReflectionUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

/**
 * @author ejijyan
 * @version $Revision: $
 */
public abstract class AbstractWebServicePolicy<WebServiceInterface, ServiceClass extends Service> implements
        InitializingBean {

    /** Class revision */
    public static final String _REV_ID_ = "$Revision: $";

    private final Class<WebServiceInterface> webServiceInterface;

    private final Class<ServiceClass> serviceClass;

    private URL wsdlEndpoint;

    private String endpointAddress;

    private HandlerResolver handlerResolver;

    private WebServiceInterface port;

    @SuppressWarnings("unchecked")
    public AbstractWebServicePolicy() {
        webServiceInterface = (Class<WebServiceInterface>) ReflectionUtil.getTypeArguments(
                AbstractWebServicePolicy.class, this.getClass()).get(0);
        serviceClass = (Class<ServiceClass>) ReflectionUtil.getTypeArguments(AbstractWebServicePolicy.class,
                this.getClass()).get(1);
    }

    @Override
    public void afterPropertiesSet() {
        WebServiceClient ws = serviceClass.getAnnotation(WebServiceClient.class);
        QName qName = new QName(ws.targetNamespace(), ws.name());
        ServiceClass service = createInstance(serviceClass, wsdlEndpoint, qName);
        if (handlerResolver != null) {
            service.setHandlerResolver(handlerResolver);
        }
        port = service.getPort(webServiceInterface);
        if (endpointAddress != null) {
            BindingProvider bindingProvider = (BindingProvider) port;
            bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
        }
    }

    private ServiceClass createInstance(Class<ServiceClass> serviceClass, URL wsdlEndpoint, QName qName) {
        return ReflectionUtil.createInstance(serviceClass, new Class<?>[] { URL.class, QName.class }, new Object[] {
                wsdlEndpoint, qName });
    }

    public WebServiceInterface getPort() {
        return port;
    }

    @Required
    public void setWsdlEndpoint(URL wsdlEndpoint) {
        this.wsdlEndpoint = wsdlEndpoint;
    }

    public void setEndpointAddress(String endpointAddress) {
        this.endpointAddress = endpointAddress;
    }

    public void setHandlerResolver(HandlerResolver handlerResolver) {
        this.handlerResolver = handlerResolver;
    }

}
