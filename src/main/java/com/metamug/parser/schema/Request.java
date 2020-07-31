//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2016.01.07 at 10:53:17 AM IST
//
package com.metamug.parser.schema;

import com.metamug.parser.schema.InvocableElement.Element;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "request", propOrder = {
    "desc",
    "invocableElements",
    "header",
    "paramSet",
    "execute",
    "sql",
    "transaction",
    "xrequest",
    "script",
    "text"
})
public class Request extends XMLElement {

    @XmlElement(name = "Desc")
    protected Desc desc;
    @XmlElement(name = "Param")
    protected Set<Param> paramSet;
    protected Set<Header> header;
    protected Set<Execute> execute;
    protected Set<Xrequest> xrequest;
    protected Set<Script> script;
    protected Set<Text> text;
    protected Set<Transaction> transaction;
    protected Set<Sql> sql;
    
    @XmlElements({
        @XmlElement(name = "Header", type = Header.class),
        @XmlElement(name = "Param", type = Param.class),
        @XmlElement(name = "Sql", type = Sql.class),
        @XmlElement(name = "Transaction", type = Transaction.class),
        @XmlElement(name = "Execute", type = Execute.class),
        @XmlElement(name = "XRequest", type = Xrequest.class),
        @XmlElement(name = "Script", type = Script.class),
        @XmlElement(name = "Text", type = Text.class)
    })
    protected List<InvocableElement> invocableElements;

    @XmlAttribute(name = "status")
    protected Integer status;
    @XmlAttribute(name = "method")
    protected Method method;
    @XmlAttribute(name = "item")
    protected String item;

    public Request() {
    }
    
    @Override
    public boolean equals(Object o) {
        Request request = (Request)o;
        if(getMethod().value().equals(request.getMethod().value())) {
            //if method value matches
            if(getItem()==null && request.getItem()==null) {
                //if both are collection requests
                return true;
            } else if(getItem()!=null && request.getItem()!=null) {
                //if both are item requests
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.method);
        int itemHashCode = this.item != null ? Objects.hashCode(this.item) : 0;
        hash = 17 * hash + itemHashCode;
        return hash;
    }

    public Request(Method method) {
        this.method = method;
    }

    /**
     *
     * @return
     */
    public Set<Param> getParamSet() {
        if (paramSet == null) {
            Set<Param> paramSet = new HashSet<>();
            return paramSet;
        } else {
            Map<String, Param> paramMap = new HashMap<>();
            paramSet.forEach((param1) -> {
                paramMap.put(param1.name, param1);
            });
            Set<Param> paramSet = new HashSet<>();
            paramSet.addAll(paramMap.values());
            return paramSet;
        }
    }

    public void addParam(Param param){
        paramSet = new HashSet<>();
        paramSet.add(param);
    }

    /**
     * Get the list of action items in the request block
     * @return List of operations
     */
    public List getInvocableElements() {
        if (invocableElements == null) {
            invocableElements = new ArrayList<>();
        }
        return invocableElements;
    }

    public void addElement(InvocableElement element){
        getInvocableElements().add(element);
    }
    
    public void addElement(String elementXml, String elementType) throws JAXBException {
        Element type = Element.fromValue(elementType);
        
        InvocableElement element = null;
        if(type.equals(Element.EXECUTE)){
            element = (Execute)new Execute().unmarshal(elementXml);
        }else if(type.equals(Element.XREQUEST)){
            element = (Xrequest)new Xrequest().unmarshal(elementXml);
        } if(type.equals(Element.SQL)){        
            element = (Sql)new Sql().unmarshal(elementXml);
        } if(type.equals(Element.SCRIPT)){
            element = (Script)new Script().unmarshal(elementXml);
        }
        
        addElement(element);
    }
    
    public Desc getDesc() {
        return desc;
    }

    public String getDescString() {
        if(desc != null && desc.getDesc().size() > 0){
            return desc.getDesc().get(0);
        }
        return null;
    }

    public void setDesc(String value) {
        this.desc.setDesc(value);
    }

    public void setDescString(String value) {
        this.desc.setDesc(value);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer value) {
        this.status = value;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method value) {
        this.method = value;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String value) {
        this.item = value;
    }

//    @Override
    public String getValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public InvocableElement get(String id){
       //@TODO get invocable elements from children
       return null;
    }

    public void printStart(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement("m:request");
        writer.writeAttribute("method", getMethod().value());
        if(getItem()!=null && !getItem().equals("false")) {
            writer.writeAttribute("item", String.valueOf(getItem()));
        }
    }

    public void printEnd(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeEndElement();
        writer.writeCharacters(System.lineSeparator());
    }
}