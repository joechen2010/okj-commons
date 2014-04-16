
package org.okj.commons.soap.client.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sessionChangeRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sessionChangeRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="values" type="{http://ericsson.com/services/ws_CIL_5/sessionchange}valuesRequest" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sessionChangeRequest", propOrder = {

})
public class SessionChangeRequest {

    protected ValuesRequest values;

    /**
     * Gets the value of the values property.
     * 
     * @return
     *     possible object is
     *     {@link ValuesRequest }
     *     
     */
    public ValuesRequest getValues() {
        return values;
    }

    /**
     * Sets the value of the values property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValuesRequest }
     *     
     */
    public void setValues(ValuesRequest value) {
        this.values = value;
    }

}
