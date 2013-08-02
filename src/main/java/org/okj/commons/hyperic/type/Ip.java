package org.okj.commons.hyperic.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Ip complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Ip">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="netmask" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mac" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="address" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ip")
public class Ip {

    @XmlAttribute
    protected String netmask;
    @XmlAttribute
    protected String mac;
    @XmlAttribute(required = true)
    protected String address;

    /**
     * Gets the value of the netmask property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNetmask() {
        return netmask;
    }

    /**
     * Sets the value of the netmask property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setNetmask(String value) {
        this.netmask = value;
    }

    /**
     * Gets the value of the mac property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMac() {
        return mac;
    }

    /**
     * Sets the value of the mac property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setMac(String value) {
        this.mac = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setAddress(String value) {
        this.address = value;
    }

}
