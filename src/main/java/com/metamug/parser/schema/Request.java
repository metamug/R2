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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "request", propOrder = {
    "desc",
    "invocableElements",
    "header",
    "param",
    "execute",
    "sql",
    "transaction",
    "xrequest",
    "script",
    "text"
})
public class Request extends XMLElement {

    @XmlElement(name = "Desc")
    protected String desc;
    @XmlElement(name = "Param")
    protected List<Param> param;
    protected List<Header> header;
    protected List<Execute> execute;
    protected List<Xrequest> xrequest;
    protected List<Script> script;
    protected List<Text> text;
    protected List<Transaction> transaction;
    protected List<Sql> sql;
    
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
    protected Boolean item;

    public Request() {
    }
    
    @Override
    public boolean equals(Object o) {
        Request request = (Request)o;
        if(getMethod().value().equals(request.getMethod().value())) {
            if(isItem() == request.isItem()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.method);
        hash = 17 * hash + Objects.hashCode(this.item);
        return hash;
    }


    public Request(Method method) {
        this.method = method;
    }

    /**
     *
     * @return
     */
    public Set<Param> getParam() {
        if (param == null) {
            Set<Param> paramSet = new HashSet<>();
            return paramSet;
        } else {
            Map<String, Param> paramMap = new HashMap<>();
            param.forEach((param1) -> {
                paramMap.put(param1.name, param1);
            });
            Set<Param> paramSet = new HashSet<>();
            paramSet.addAll(paramMap.values());
            return paramSet;
        }
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
            //System.out.println(element);
        } if(type.equals(Element.SQL)){        
            element = (Sql)new Sql().unmarshal(elementXml);
        } if(type.equals(Element.SCRIPT)){
            element = (Script)new Script().unmarshal(elementXml);
        }
        
        addElement(element);
    }
    
    public String getDesc() {
        return desc;
    }

    public void setDesc(String value) {
        this.desc = value;
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

    public boolean isItem() {
        if (item == null) {
            return false;
        } else {
            return item;
        }
    }

    public void setItem(Boolean value) {
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
}