
package org.okj.commons.soap.client.test;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "BilldocumentReadService", targetNamespace = "http://ericsson.com/services/ws_CIL_5", wsdlLocation = "file:/D:/CFSI/service/ws_CIL_5_BilldocumentReadService.wsdl")
public class BilldocumentReadService_Service
    extends Service
{

    private final static URL BILLDOCUMENTREADSERVICE_WSDL_LOCATION;
    private final static WebServiceException BILLDOCUMENTREADSERVICE_EXCEPTION;
    private final static QName BILLDOCUMENTREADSERVICE_QNAME = new QName("http://ericsson.com/services/ws_CIL_5", "BilldocumentReadService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/D:/CFSI/service/ws_CIL_5_BilldocumentReadService.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        BILLDOCUMENTREADSERVICE_WSDL_LOCATION = url;
        BILLDOCUMENTREADSERVICE_EXCEPTION = e;
    }

    public BilldocumentReadService_Service() {
        super(__getWsdlLocation(), BILLDOCUMENTREADSERVICE_QNAME);
    }

    public BilldocumentReadService_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), BILLDOCUMENTREADSERVICE_QNAME, features);
    }

    public BilldocumentReadService_Service(URL wsdlLocation) {
        super(wsdlLocation, BILLDOCUMENTREADSERVICE_QNAME);
    }

    public BilldocumentReadService_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, BILLDOCUMENTREADSERVICE_QNAME, features);
    }

    public BilldocumentReadService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BilldocumentReadService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns BilldocumentReadService
     */
    @WebEndpoint(name = "BilldocumentReadServiceSoap11")
    public BilldocumentReadService getBilldocumentReadServiceSoap11() {
        return super.getPort(new QName("http://ericsson.com/services/ws_CIL_5", "BilldocumentReadServiceSoap11"), BilldocumentReadService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns BilldocumentReadService
     */
    @WebEndpoint(name = "BilldocumentReadServiceSoap11")
    public BilldocumentReadService getBilldocumentReadServiceSoap11(WebServiceFeature... features) {
        return super.getPort(new QName("http://ericsson.com/services/ws_CIL_5", "BilldocumentReadServiceSoap11"), BilldocumentReadService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (BILLDOCUMENTREADSERVICE_EXCEPTION!= null) {
            throw BILLDOCUMENTREADSERVICE_EXCEPTION;
        }
        return BILLDOCUMENTREADSERVICE_WSDL_LOCATION;
    }

}
