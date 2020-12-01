package com.metamug.parser.schema;

import com.metamug.parser.ExportParserService;
import com.metamug.parser.exception.ResourceTestException;
import com.metamug.parser.service.ParserService;
import org.xml.sax.SAXException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class ExportRequest extends Request{
    public ExportRequest(Request r){
        super(r);
    }
    
    public ExportRequest(){
        super();
    }

    @Override
    protected void printChildren(XMLStreamWriter w, ParserService parent) throws SAXException, IOException, ResourceTestException, XPathExpressionException, XMLStreamException {
        for (Object child : getInvocableElements()) {
            if(child instanceof Sql){
                ExportSql exportSql = new ExportSql((Sql)child,((ExportParserService)parent).getQueryMap());
                exportSql.print(w, parent);
            }else{
                ((InvocableElement)child).print(w, parent);
            }
        }
    }
}
