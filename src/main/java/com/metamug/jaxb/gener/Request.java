package com.metamug.jaxb.gener;


import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "request", propOrder = {
    "sql"
})
@XmlSeeAlso({
    Create.class,
    Read.class,
    Update.class,
    Delete.class
})
public class Request {

    @XmlElement(name = "Sql", required = true)
    protected List<Sql> sql;
    @XmlAttribute(name = "method")
    protected Method method;
    @XmlAttribute(name = "id")
    protected Long id;
    @XmlAttribute(name = "out")
    protected Out out;
    
    public Request(){
    }
    
    public Request(Method m){
        method = m;
        
    }
    
    /**
     * Gets the value of the sql property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sql property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSql().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Sql }
     * 
     * 
     */
    public List<Sql> getSql() {
        if (sql == null) {
            sql = new ArrayList<Sql>();
        }
        
        //System.out.println("Not Overeided!+_+_+_+_+_+++_+_+_+_+_+_+_+_");
        
        return this.sql;
    }

    /**
     * Gets the value of the method property.
     * 
     * @return
     *     possible object is
     *     {@link Method }
     *     
     */
    public Method getMethod() {
        if (method == null) {
            return Method.GET;
        } else {
            return method;
        }
    }

    /**
     * Sets the value of the method property.
     * 
     * @param value
     *     allowed object is
     *     {@link Method }
     *     
     */
    public void setMethod(Method value) {
        this.method = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the out property.
     * 
     * @return
     *     possible object is
     *     {@link Out }
     *     
     */
    public Out getOut() {
        if (out == null) {
            return Out.JSON;
        } else {
            return out;
        }
    }

    /**
     * Sets the value of the out property.
     * 
     * @param value
     *     allowed object is
     *     {@link Out }
     *     
     */
    public void setOut(Out value) {
        this.out = value;
    }

}
