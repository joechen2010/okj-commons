package org.okj.commons.hyperic.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ResourcePrototype complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResourcePrototype">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="instanceId" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="resourceTypeId" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResourcePrototype")
public class ResourcePrototype {

    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute(required = true)
    protected int id;
    @XmlAttribute
    protected Integer instanceId;
    @XmlAttribute
    protected Integer resourceTypeId;

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
     * Gets the value of the resourceTypeId property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getResourceTypeId() {
        return resourceTypeId;
    }

    /**
     * Sets the value of the resourceTypeId property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setResourceTypeId(Integer value) {
        this.resourceTypeId = value;
    }

}
