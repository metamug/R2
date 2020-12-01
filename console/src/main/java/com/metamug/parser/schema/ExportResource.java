package com.metamug.parser.schema;

import com.metamug.parser.exception.ResourceTestException;
import com.metamug.parser.service.ParserService;
import org.xml.sax.SAXException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class ExportResource extends Resource {
    public ExportResource(Resource r){
        super(r);
    }
    public ExportResource(){
        super();
    }
    @Override
    protected void printRequest(XMLStreamWriter writer, ParserService parent) throws ResourceTestException, IOException, SAXException, XPathExpressionException, XMLStreamException {
        for (Request req : getRequest()) {
            ExportRequest exReq = new ExportRequest(req);
            exReq.print(writer, parent);
            writer.writeCharacters(System.lineSeparator());
        }
    }
}