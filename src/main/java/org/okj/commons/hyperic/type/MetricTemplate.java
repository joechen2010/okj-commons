//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.08 at 03:01:54 PM CST 
//

package org.okj.commons.hyperic.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for MetricTemplate complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MetricTemplate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="defaultInterval" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="category" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="collectionType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="defaultOn" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="indicator" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="plugin" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="units" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="alias" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MetricTemplate")
public class MetricTemplate {

    @XmlAttribute(required = true)
    protected long defaultInterval;
    @XmlAttribute(required = true)
    protected String category;
    @XmlAttribute(required = true)
    protected String collectionType;
    @XmlAttribute(required = true)
    protected boolean defaultOn;
    @XmlAttribute(required = true)
    protected boolean indicator;
    @XmlAttribute(required = true)
    protected String plugin;
    @XmlAttribute(required = true)
    protected String units;
    @XmlAttribute(required = true)
    protected String alias;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute(required = true)
    protected int id;

    /**
     * Gets the value of the defaultInterval property.
     * 
     */
    public long getDefaultInterval() {
        return defaultInterval;
    }

    /**
     * Sets the value of the defaultInterval property.
     * 
     */
    public void setDefaultInterval(long value) {
        this.defaultInterval = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCategory(String value) {
        this.category = value;
    }

    /**
     * Gets the value of the collectionType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCollectionType() {
        return collectionType;
    }

    /**
     * Sets the value of the collectionType property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCollectionType(String value) {
        this.collectionType = value;
    }

    /**
     * Gets the value of the defaultOn property.
     * 
     */
    public boolean isDefaultOn() {
        return defaultOn;
    }

    /**
     * Sets the value of the defaultOn property.
     * 
     */
    public void setDefaultOn(boolean value) {
        this.defaultOn = value;
    }

    /**
     * Gets the value of the indicator property.
     * 
     */
    public boolean isIndicator() {
        return indicator;
    }

    /**
     * Sets the value of the indicator property.
     * 
     */
    public void setIndicator(boolean value) {
        this.indicator = value;
    }

    /**
     * Gets the value of the plugin property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPlugin() {
        return plugin;
    }

    /**
     * Sets the value of the plugin property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setPlugin(String value) {
        this.plugin = value;
    }

    /**
     * Gets the value of the units property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getUnits() {
        return units;
    }

    /**
     * Sets the value of the units property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setUnits(String value) {
        this.units = value;
    }

    /**
     * Gets the value of the alias property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the value of the alias property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setAlias(String value) {
        this.alias = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

}
