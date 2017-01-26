//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2016.04.19 at 06:43:03 PM IST
//
package com.metamug.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "param")
public class Param {

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "blank")
    protected Boolean blank;
    @XmlAttribute(name = "number")
    protected Boolean isNum;
    @XmlAttribute(name = "required")
    protected Boolean required;
    @XmlAttribute(name = "max")
    protected String max;
    @XmlAttribute(name = "min")
    protected String min;
    @XmlAttribute(name = "maxlength")
    protected String maxLen;
    @XmlAttribute(name = "minlength")
    protected String minLen;
    @XmlAttribute(name = "pattern")
    protected String pattern;
    @XmlAttribute(name = "exists")
    protected String exists;
    @XmlAttribute(name = "value")
    protected String defaultValue;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public Object isBlank() {
        return blank;
    }

    public void setBlank(Boolean value) {
        this.blank = value;
    }

    public Object isNum() {
        return isNum;
    }

    public void setNum(Boolean value) {
        this.isNum = value;
    }

    public Object isRequired() {
        return required;
    }

    public void setRequired(Boolean value) {
        this.required = value;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String value) {
        this.max = value;
    }

    /**
     * Gets the value of the min property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMin() {
        return min;
    }

    /**
     * Sets the value of the min property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMin(String value) {
        this.min = value;
    }

    public String getMaxLen() {
        return maxLen;
    }

    /**
     * Sets the value of the maxLen property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMaxLen(String value) {
        this.maxLen = value;
    }

    /**
     * Gets the value of the minLen property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMinLen() {
        return minLen;
    }

    /**
     * Sets the value of the minLen property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMinLen(String value) {
        this.minLen = value;
    }

    /**
     * Gets the value of the pattern property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Sets the value of the pattern property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPattern(String value) {
        this.pattern = value;
    }

    /**
     * Gets the value of the exists property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getExists() {
        return exists;
    }

    public Boolean getIsNum() {
        return isNum;
    }

    public void setIsNum(Boolean isNum) {
        this.isNum = isNum;
    }

    /**
     * Sets the value of the exists property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setExists(String value) {
        this.exists = value;
    }

    /**
     * Gets the value of the default property
     *
     * @return
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the value of the default property
     *
     * @param value
     */
    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }
}