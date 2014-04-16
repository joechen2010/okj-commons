
package org.okj.commons.soap.client.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for inputAttributes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="inputAttributes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="documentRefNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billingRequestNumber" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="requestType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="csId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="csIdPub" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="coId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="coIdPub" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="documentRefOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inputAttributes", propOrder = {

})
public class InputAttributes {

    protected String documentRefNum;
    protected Long billingRequestNumber;
    protected String requestType;
    protected Long csId;
    protected String csIdPub;
    protected Long coId;
    protected String coIdPub;
    protected Boolean documentRefOnly;

    /**
     * Gets the value of the documentRefNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentRefNum() {
        return documentRefNum;
    }

    /**
     * Sets the value of the documentRefNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentRefNum(String value) {
        this.documentRefNum = value;
    }

    /**
     * Gets the value of the billingRequestNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBillingRequestNumber() {
        return billingRequestNumber;
    }

    /**
     * Sets the value of the billingRequestNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBillingRequestNumber(Long value) {
        this.billingRequestNumber = value;
    }

    /**
     * Gets the value of the requestType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * Sets the value of the requestType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestType(String value) {
        this.requestType = value;
    }

    /**
     * Gets the value of the csId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCsId() {
        return csId;
    }

    /**
     * Sets the value of the csId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCsId(Long value) {
        this.csId = value;
    }

    /**
     * Gets the value of the csIdPub property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCsIdPub() {
        return csIdPub;
    }

    /**
     * Sets the value of the csIdPub property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCsIdPub(String value) {
        this.csIdPub = value;
    }

    /**
     * Gets the value of the coId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCoId() {
        return coId;
    }

    /**
     * Sets the value of the coId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCoId(Long value) {
        this.coId = value;
    }

    /**
     * Gets the value of the coIdPub property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoIdPub() {
        return coIdPub;
    }

    /**
     * Sets the value of the coIdPub property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoIdPub(String value) {
        this.coIdPub = value;
    }

    /**
     * Gets the value of the documentRefOnly property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDocumentRefOnly() {
        return documentRefOnly;
    }

    /**
     * Sets the value of the documentRefOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDocumentRefOnly(Boolean value) {
        this.documentRefOnly = value;
    }

}
