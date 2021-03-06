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
 * Java class for Resource complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Resource">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Resource" type="{}Resource" maxOccurs="unbounded"/>
 *         &lt;element name="ResourceConfig" type="{}ResourceConfig" maxOccurs="unbounded"/>
 *         &lt;element name="ResourceProperty" type="{}ResourceProperty" maxOccurs="unbounded"/>
 *         &lt;element name="ResourcePrototype" type="{}ResourcePrototype"/>
 *         &lt;element name="Agent" type="{}Agent"/>
 *         &lt;element name="Ip" type="{}Ip" maxOccurs="unbounded"/>
 *         &lt;element name="ResourceInfo" type="{}ResourceInfo" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="typeId" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="instanceId" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="location" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Resource", propOrder = { "resource", "resourceConfig", "resourceProperty", "resourcePrototype",
        "agent", "ip", "resourceInfo" })
public class Resource {

    @XmlElement(name = "Resource", required = true)
    protected List<Resource> resource;
    @XmlElement(name = "ResourceConfig", required = true)
    protected List<ResourceConfig> resourceConfig;
    @XmlElement(name = "ResourceProperty", required = true)
    protected List<ResourceProperty> resourceProperty;
    @XmlElement(name = "ResourcePrototype", required = true)
    protected ResourcePrototype resourcePrototype;
    @XmlElement(name = "Agent", required = true)
    protected Agent agent;
    @XmlElement(name = "Ip", required = true)
    protected List<Ip> ip;
    @XmlElement(name = "ResourceInfo", required = true)
    protected List<ResourceInfo> resourceInfo;
    @XmlAttribute
    protected Integer typeId;
    @XmlAttribute
    protected Integer instanceId;
    @XmlAttribute
    protected String location;
    @XmlAttribute
    protected String description;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute
    protected Integer id;

    /**
     * Gets the value of the resource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the resource property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getResource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Resource }
     * 
     * 
     */
    public List<Resource> getResource() {
        if (resource == null) {
            resource = new ArrayList<Resource>();
        }
        return this.resource;
    }

    /**
     * Gets the value of the resourceConfig property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the resourceConfig property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getResourceConfig().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ResourceConfig }
     * 
     * 
     */
    public List<ResourceConfig> getResourceConfig() {
        if (resourceConfig == null) {
            resourceConfig = new ArrayList<ResourceConfig>();
        }
        return this.resourceConfig;
    }

    /**
     * Gets the value of the resourceProperty property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the resourceProperty property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getResourceProperty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ResourceProperty }
     * 
     * 
     */
    public List<ResourceProperty> getResourceProperty() {
        if (resourceProperty == null) {
            resourceProperty = new ArrayList<ResourceProperty>();
        }
        return this.resourceProperty;
    }

    /**
     * Gets the value of the resourcePrototype property.
     * 
     * @return possible object is {@link ResourcePrototype }
     * 
     */
    public ResourcePrototype getResourcePrototype() {
        return resourcePrototype;
    }

    /**
     * Sets the value of the resourcePrototype property.
     * 
     * @param value
     *            allowed object is {@link ResourcePrototype }
     * 
     */
    public void setResourcePrototype(ResourcePrototype value) {
        this.resourcePrototype = value;
    }

    /**
     * Gets the value of the agent property.
     * 
     * @return possible object is {@link Agent }
     * 
     */
    public Agent getAgent() {
        return agent;
    }

    /**
     * Sets the value of the agent property.
     * 
     * @param value
     *            allowed object is {@link Agent }
     * 
     */
    public void setAgent(Agent value) {
        this.agent = value;
    }

    /**
     * Gets the value of the ip property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the ip property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getIp().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Ip }
     * 
     * 
     */
    public List<Ip> getIp() {
        if (ip == null) {
            ip = new ArrayList<Ip>();
        }
        return this.ip;
    }

    /**
     * Gets the value of the resourceInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the resourceInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getResourceInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ResourceInfo }
     * 
     * 
     */
    public List<ResourceInfo> getResourceInfo() {
        if (resourceInfo == null) {
            resourceInfo = new ArrayList<ResourceInfo>();
        }
        return this.resourceInfo;
    }

    /**
     * Gets the value of the typeId property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * Sets the value of the typeId property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setTypeId(Integer value) {
        this.typeId = value;
    }

    /**
     * Gets the value of the instanceId property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getInstanceId() {
        return instanceId;
    }

    /**
     * Sets the value of the instanceId property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setInstanceId(Integer value) {
        this.instanceId = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDescription(String value) {
        this.description = value;
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
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setId(Integer value) {
        this.id = value;
    }

}
