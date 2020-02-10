//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.28 at 08:04:33 PM IST 
//


package com.metamug.parser.schema;

import com.metamug.parser.exception.ResourceTestException;
import com.metamug.parser.service.ParserService;
import static com.metamug.parser.service.ParserService.MASON_OUTPUT;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPathExpressionException;
import org.apache.commons.text.StringEscapeUtils;
import org.xml.sax.SAXException;


/**
 * <p>Java class for script complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="script">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="id" use="required" type="{http://xml.metamug.net/resource/1.0}elementId" />
 *       &lt;attribute name="file" use="required" type="{http://xml.metamug.net/resource/1.0}scriptFile" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "script")
public class Script extends RequestChild{

    @XmlAttribute(name = "id", required = true)
    protected String id;
    @XmlAttribute(name = "file", required = true)
    protected String file;
    @XmlAttribute(name = "output")
    private Boolean output;
    @XmlAttribute(name = "when")
    protected String when;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the file property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFile() {
        return file;
    }

    /**
     * Sets the value of the file property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFile(String value) {
        this.file = value;
    }
    
    /**
     * Gets the value of the when property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getWhen() {
        return when;
    }

    /**
     * Sets the value of the when property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setWhen(String value) {
        this.when = value;
    }
    /**
     * Gets the value of the output property.
     *
     * @return possible object is {@link Boolean}.
     *
     */
    public Boolean getOutput() {
        if (output == null) {
            return true;
        } else {
            return output;
        }
    }

    /**
     * Sets the value of the verbose property.
     *
     * @param output allowed object is {@link Boolean}
     *
     */
    public void setOutput(Boolean output) {
        this.output = output;
    }

    @Override
    public void print(XMLStreamWriter writer, ParserService parent) throws XMLStreamException, IOException, XPathExpressionException, ResourceTestException, SAXException {
        this.parent = parent;
        //Script script = (Script)this;
        
        if (getWhen() != null) {
            writer.writeStartElement("c:if");
            //String testString = getQuotedString(script.getWhen());
            //writer.writeAttribute("test", enclose(testString.replace("$", "mtgReq.params")));
            String test = transformVariables(getWhen(),parent.elementIds,false);
            writeUnescapedData(" test=\""+enclose(StringEscapeUtils.unescapeXml(test))+"\"",parent.output);
        }
        writer.writeCharacters(System.lineSeparator());
        writer.writeStartElement("m:script");
        String var = getId();
        writer.writeAttribute("var", var);
        writer.writeAttribute("file", getFile());
        
        writer.writeEndElement(); //End of <m:script>    
        writer.writeCharacters(System.lineSeparator());
        
        if (getOutput()) {
            printTargetCSet(writer,enclose(MASON_OUTPUT),var,enclose(var)); 
        }
        
        if (getWhen() != null) {
            writer.writeEndElement(); //End of <c:if>
        }
    }

    @Override
    public List<String> getRequestParameters() {
        List<String> p = new ArrayList<>();
        getRequestParametersFromString(p,getWhen());
        return p;
    }

    @Override
    public String getJspVariableForMPath(String mpathVariable, String elementId, boolean enclose) {
        StringBuilder sb = new StringBuilder();
                
        // bus[id].name
        String locator = getMPathLocator(mpathVariable);
        String transformedVariable = elementId+locator;
        
        sb.append(transformedVariable);
                      
        return enclose ? enclose(sb.toString()) : sb.toString();
    }
}
