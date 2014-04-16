
package org.okj.commons.soap.client.test;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ericsson.services.ws_cil_5.billdocumentread package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BilldocumentReadRequest_QNAME = new QName("http://ericsson.com/services/ws_CIL_5/billdocumentread", "billdocumentReadRequest");
    private final static QName _BilldocumentReadResponse_QNAME = new QName("http://ericsson.com/services/ws_CIL_5/billdocumentread", "billdocumentReadResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ericsson.services.ws_cil_5.billdocumentread
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Svlany }
     * 
     */
    public Svlany createSvlany() {
        return new Svlany();
    }

    /**
     * Create an instance of {@link Money }
     * 
     */
    public Money createMoney() {
        return new Money();
    }

    /**
     * Create an instance of {@link BilldocumentReadResponse }
     * 
     */
    public BilldocumentReadResponse createBilldocumentReadResponse() {
        return new BilldocumentReadResponse();
    }

    /**
     * Create an instance of {@link BilldocumentReadRequest }
     * 
     */
    public BilldocumentReadRequest createBilldocumentReadRequest() {
        return new BilldocumentReadRequest();
    }

    /**
     * Create an instance of {@link ResultListResponse }
     * 
     */
    public ResultListResponse createResultListResponse() {
        return new ResultListResponse();
    }

    /**
     * Create an instance of {@link ResultListListpartResponse }
     * 
     */
    public ResultListListpartResponse createResultListListpartResponse() {
        return new ResultListListpartResponse();
    }

    /**
     * Create an instance of {@link InputAttributes }
     * 
     */
    public InputAttributes createInputAttributes() {
        return new InputAttributes();
    }

    /**
     * Create an instance of {@link SessionChangeRequest }
     * 
     */
    public SessionChangeRequest createSessionChangeRequest() {
        return new SessionChangeRequest();
    }

    /**
     * Create an instance of {@link ValuesRequest }
     * 
     */
    public ValuesRequest createValuesRequest() {
        return new ValuesRequest();
    }

    /**
     * Create an instance of {@link ValuesListpartRequest }
     * 
     */
    public ValuesListpartRequest createValuesListpartRequest() {
        return new ValuesListpartRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BilldocumentReadRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ericsson.com/services/ws_CIL_5/billdocumentread", name = "billdocumentReadRequest")
    public JAXBElement<BilldocumentReadRequest> createBilldocumentReadRequest(BilldocumentReadRequest value) {
        return new JAXBElement<BilldocumentReadRequest>(_BilldocumentReadRequest_QNAME, BilldocumentReadRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BilldocumentReadResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ericsson.com/services/ws_CIL_5/billdocumentread", name = "billdocumentReadResponse")
    public JAXBElement<BilldocumentReadResponse> createBilldocumentReadResponse(BilldocumentReadResponse value) {
        return new JAXBElement<BilldocumentReadResponse>(_BilldocumentReadResponse_QNAME, BilldocumentReadResponse.class, null, value);
    }

}
