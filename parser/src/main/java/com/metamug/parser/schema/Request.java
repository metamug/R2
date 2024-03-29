//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2016.01.07 at 10:53:17 AM IST
//
package com.metamug.parser.schema;

import com.metamug.parser.exception.ResourceTestException;
import com.metamug.parser.service.ParserService;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPathExpressionException;

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
@XmlRootElement(name = "Request")
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

    public Request(Request r) {
        this.desc = r.getDesc();
        this.paramSet = r.getParamSet();
        this.invocableElements = r.getInvocableElements();
        this.status = r.getStatus();
        this.method = r.getMethod();
        this.item = r.getItem();
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
     * Appends given set of params to param set of this request
     * 
     * @param ps given param set
     */
    public void appendParams(Set<Param> ps){
        if(paramSet != null){
            ps.forEach((param)->{
                paramSet.add(param);
            });
        }
    }

    /**
     * Appends all elements from given request to this request
     *   
     * @param request incoming request
     *
     */
    public void appendElements(Request request) {
        //append params from incoming to this request
        appendParams(request.getParamSet());
        //append invocable elements
        appendInvocableElements(request.getInvocableElements());
    }
    
    /**
     * Get the list of action items in the request block
     * 
     * @return List of operations
     */
    public List getInvocableElements() {
        if (invocableElements == null) {
            invocableElements = new ArrayList<>();
        }
        return invocableElements;
    }
    
    /**
     * Appends given list of invocable elements to list of invocable elements of this request
     * 
     * @param invElements
     */
    public void appendInvocableElements(List<InvocableElement> invElements){
        /*
        invElements.forEach((e) -> {
            System.out.println(e);
        });
        */
        if(invElements != null){
            getInvocableElements().addAll(invElements);
        }
    }
    
    /**
     * Gets the value of the desc property.
     *
     * @return possible object is {@link String }
     *
     */
    public Desc getDesc() {
        if(desc == null){
            desc = new Desc();
        } 
        return desc;
    }

    public String getDescString() {
        if(desc != null && desc.getDesc().size() > 0){
            return desc.getDesc().get(0);
        }
        return null;
    }

    public void setDesc(String value) {
        this.getDesc().setDesc(value);
    }

    public void setDescString(String value) {
        this.getDesc().setDesc(value);
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

    public String getValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public InvocableElement get(String id){
       //@TODO get invocable elements from children
       return null;
    }

    public void print(XMLStreamWriter w, ParserService parent) throws XMLStreamException, ResourceTestException, SAXException, XPathExpressionException, IOException {
        printStart(w);
        printChildren(w, parent);
        printEnd(w);
    }

    protected void printChildren(XMLStreamWriter w, ParserService parent) throws SAXException, IOException, ResourceTestException, XPathExpressionException, XMLStreamException {
        for (Object child : getInvocableElements()) {
            ((InvocableElement)child).print(w, parent);
        }
    }

    private void printStart(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement("m:request");
        writer.writeAttribute("method", getMethod().value());
        if(getItem()!=null) {
            writer.writeAttribute("item", String.valueOf(getItem()));
        }
    }

    private void printEnd(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeEndElement();
        writer.writeCharacters(System.lineSeparator());
    }
}