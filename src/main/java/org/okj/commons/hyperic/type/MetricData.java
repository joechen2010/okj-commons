//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.08 at 03:01:54 PM CST 
//

package org.okj.commons.hyperic.type;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for MetricData complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MetricData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DataPoint" type="{}DataPoint" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="resourceId" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="resourceName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="metricId" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="metricName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MetricData", propOrder = { "dataPoint" })
public class MetricData {

    @XmlElement(name = "DataPoint")
    protected List<DataPoint> dataPoint;
    @XmlAttribute(required = true)
    protected int resourceId;
    @XmlAttribute(required = true)
    protected String resourceName;
    @XmlAttribute(required = true)
    protected int metricId;
    @XmlAttribute(required = true)
    protected String metricName;

    /**
     * Gets the value of the dataPoint property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the dataPoint property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getDataPoint().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link DataPoint }
     * 
     * 
     */
    public List<DataPoint> getDataPoint() {
        if (dataPoint == null) {
            dataPoint = new ArrayList<DataPoint>();
        }
        return this.dataPoint;
    }

    /**
     * Gets the value of the resourceId property.
     * 
     */
    public int getResourceId() {
        return resourceId;
    }

    /**
     * Sets the value of the resourceId property.
     * 
     */
    public void setResourceId(int value) {
        this.resourceId = value;
    }

    /**
     * Gets the value of the resourceName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Sets the value of the resourceName property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setResourceName(String value) {
        this.resourceName = value;
    }

    /**
     * Gets the value of the metricId property.
     * 
     */
    public int getMetricId() {
        return metricId;
    }

    /**
     * Sets the value of the metricId property.
     * 
     */
    public void setMetricId(int value) {
        this.metricId = value;
    }

    /**
     * Gets the value of the metricName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMetricName() {
        return metricName;
    }

    /**
     * Sets the value of the metricName property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setMetricName(String value) {
        this.metricName = value;
    }

}
