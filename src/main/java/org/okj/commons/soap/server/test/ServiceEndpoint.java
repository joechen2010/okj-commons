package org.okj.commons.soap.server.test;

import java.io.IOException;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

import org.okj.commons.soap.client.test.BilldocumentReadRequest;
import org.okj.commons.soap.client.test.BilldocumentReadResponse;
import org.okj.commons.soap.client.test.ResultListListpartResponse;
import org.okj.commons.soap.client.test.ResultListResponse;
import org.okj.commons.soap.server.AbstractServiceEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;

@Endpoint
public class ServiceEndpoint extends AbstractServiceEndpoint{

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEndpoint.class);
    private Resource billdocumentReadResponse;
    private Boolean isBillDocumentReadResponseBase64;
    
    @PayloadRoot(localPart = "billdocumentReadRequest", namespace = "http://ericsson.com/services/ws_CIL_5/billdocumentread")
    public JAXBElement<BilldocumentReadResponse> processBillDocumentRead(
            JAXBElement<BilldocumentReadRequest> req) {
        try{
            BilldocumentReadResponse billdocumentReadResponseObject = new BilldocumentReadResponse();
            ResultListResponse resultListResponse = new ResultListResponse();
            ResultListListpartResponse resultListListpartResponse = new ResultListListpartResponse();
            resultListListpartResponse.setDocumentFormat("pdf");
            resultListListpartResponse.setDocumentFileName(billdocumentReadResponse.getFilename());
            resultListListpartResponse.setDocument(obtainByteData(billdocumentReadResponse.getInputStream(), isBillDocumentReadResponseBase64));
            resultListResponse.getItem().add(resultListListpartResponse);
            billdocumentReadResponseObject.setResultList(resultListResponse);
            return new JAXBElement<BilldocumentReadResponse>(getQName(BilldocumentReadResponse.class), BilldocumentReadResponse.class, null,billdocumentReadResponseObject);
        }catch(IOException e){
            try{
                SOAPFault fault = null;
                fault = SOAPFactory.newInstance().createFault();
                QName faultCodeQName = new QName("http://ericsson.com/services/fault", "BILLSRV.billproc_invalidbillimagefound");
                fault.setFaultCode(faultCodeQName);
                fault.setFaultString("Fault String returned by CBiO Simulator");
                throw new SOAPFaultException(fault);
            }catch (SOAPException se) {
                LOGGER.error("Error during building SOAPFault.", se);
                return null;
            }
        }
    }
    
    

    
}
