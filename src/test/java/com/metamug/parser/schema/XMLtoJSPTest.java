package com.metamug.parser.schema;

import com.metamug.parser.exception.ResourceTestException;
import com.metamug.parser.service.ParserService;
import org.json.JSONObject;
import org.junit.Test;
import org.xml.sax.SAXException;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 *
 * @author richard937
 */

public class XMLtoJSPTest {

    @Test
    public void executeTranspile() throws IOException, JAXBException, SAXException, ResourceTestException, XPathExpressionException, XMLStreamException, TransformerException, URISyntaxException, ClassNotFoundException, ParserConfigurationException, PropertyVetoException, SQLException {
        File file = new File(this.getClass().getResource("/execute.xml").getFile());

        System.out.println(file.getName());

        ParserService parserService = new ParserService();
        JSONObject resource = parserService.transform(file, "webapp", true, "E:\\internship\\MetaMug\\openapi-rest-model\\src\\test\\java\\com\\metamug\\parser\\schema", null);
        System.out.println(resource);

    }
}
