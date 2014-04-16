
package org.okj.commons.soap.client.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for billdocumentReadResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="billdocumentReadResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="resultList" type="{http://ericsson.com/services/ws_CIL_5/billdocumentread}resultListResponse" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "billdocumentReadResponse", propOrder = {

})
public class BilldocumentReadResponse {

    protected ResultListResponse resultList;

    /**
     * Gets the value of the resultList property.
     * 
     * @return
     *     possible object is
     *     {@link ResultListResponse }
     *     
     */
    public ResultListResponse getResultList() {
        return resultList;
    }

    /**
     * Sets the value of the resultList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResultListResponse }
     *     
     */
    public void setResultList(ResultListResponse value) {
        this.resultList = value;
    }

}
