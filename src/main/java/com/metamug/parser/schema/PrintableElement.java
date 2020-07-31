package com.metamug.parser.schema;

import com.metamug.parser.exception.ResourceTestException;
import com.metamug.parser.service.ParserService;
import org.xml.sax.SAXException;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public abstract class PrintableElement extends XMLElement {
    @XmlTransient
    public ParserService parent;

    /**
     * @param writer
     * @param parent
     * @throws javax.xml.stream.XMLStreamException
     * @throws java.io.IOException
     * @throws javax.xml.xpath.XPathExpressionException
     * @throws com.metamug.parser.exception.ResourceTestException
     * @throws org.xml.sax.SAXException
     */
    abstract public void print(XMLStreamWriter writer, ParserService parent) throws XMLStreamException, IOException, XPathExpressionException, ResourceTestException, SAXException;
}
