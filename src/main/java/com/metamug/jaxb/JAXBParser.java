package com.metamug.jaxb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.metamug.jaxb.gener.Execute;
import com.metamug.jaxb.gener.ParamVar;
import com.metamug.jaxb.gener.Request;
import com.metamug.jaxb.gener.Resource;
import com.metamug.jaxb.gener.Sql;
import com.metamug.jaxb.xslt.XslTransformer;
import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

/**
 *
 * @author anish
 */
public class JAXBParser {

    OutputStream output;
    XMLOutputFactory factory = XMLOutputFactory.newInstance();
    XMLStreamWriter writer;

    public static void main(String[] args) throws SAXException {
        File xml = new File(JAXBParser.class.getResource("/apple.xml").getFile());
        File xsd = new File(JAXBParser.class.getResource("/apple.xsd").getFile());
        Source xmlFile = new StreamSource(xml);
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(xsd);
        Validator validator = schema.newValidator();
        try {
            validator.validate(xmlFile);
            Resource resource = new JAXBParser().parse();
            if (resource != null) {
                createHtml(resource);
            }
        } catch (SAXException | IOException ex) {
            System.out.println(xmlFile.getSystemId() + " is NOT valid.");
            System.out.println("Reason: " + ex.getMessage());
        }
    }

    public static void createHtml(Resource resource) {
        File xml = new File(JAXBParser.class.getResource("/apple.xml").getFile());
        File xsl = new File(JAXBParser.class.getResource("/resource.xsl").getFile());
        File outHtml = new File("../rpx-parser/src/main/resources/" + resource.getTable() + ".html");
        try {
            XslTransformer.transform(xml, xsl, outHtml);
        } catch (TransformerException ex) {
            Logger.getLogger(JAXBParser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Resource parse() {
        Resource resource = new Resource();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Resource.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            resource = (Resource) jaxbUnmarshaller.unmarshal(JAXBParser.class.getResourceAsStream("/apple.xml"));

            output = new FileOutputStream("../rpx-parser/src/main/resources/" + resource.getTable() + ".jsp");
            writer = new IndentingXMLStreamWriter(factory.createXMLStreamWriter(output));
            writeEscapedCharacters("<%@include  file=\"../fragments/taglibs.jspf\"%>");
            writer.writeCharacters(System.lineSeparator());
            writer.writeStartElement("c:choose");
            for (Request req : resource.getRequestOrCreateOrRead()) {
                writer.writeStartElement("c:when");
                if (req.getId() != null) {
                    writer.writeAttribute("test", enclose("not empty mtgReq.id and mtgReq.method eq '" + req.getMethod() + "'"));
                } else {
                    writer.writeAttribute("test", enclose("empty mtgReq.id and mtgReq.method eq '" + req.getMethod() + "'"));
                }
                if (!req.getSql().isEmpty()) {
                    for (Sql sql : req.getSql()) {
                        if (sql.getType().equalsIgnoreCase("query")) {
                            writer.writeStartElement("sql:query");
                            writer.writeAttribute("var", "result");
                            writer.writeAttribute("dataSource", "jdbc/mtgMySQL");
                            String processSQL = processSQL(sql.getValue());
                            writeEscapedCharacters(processSQL);
                            writer.writeEndElement();
                        } else {
                            writer.writeStartElement("sql:update");
                            writer.writeAttribute("var", "result");
                            writer.writeAttribute("dataSource", "jdbc/mtgMySQL");
                            String processSQL = processSQL(sql.getValue());
                            writeEscapedCharacters(processSQL);
                            writer.writeEndElement();
                        }
                        if (sql.getClazz() == null && sql.getType().equalsIgnoreCase("query")) {
                            writer.writeStartElement("mtg:out");
                            writer.writeAttribute("value", enclose("result"));
                            writer.writeEndElement();
                        } else if (sql.getClazz() != null && sql.getType().equalsIgnoreCase("query")) {
                            writer.writeStartElement("p:postProcess");
                            writer.writeAttribute("className", sql.getClazz());
                            writer.writeAttribute("value", enclose("result"));
                            writer.writeEndElement();
                        }
                    }
                }
                if (!req.getExecute().isEmpty()) {
                    for (Execute execute : req.getExecute()) {
                        writer.writeStartElement("code:execute");
                        writer.writeAttribute("className", execute.getClassName());
                        writer.writeAttribute("methodName", execute.getFunctionName());
                        if (!execute.getParamVal().isEmpty()) {
                            for (String pvl : execute.getParamVal()) {
                                writeEscapedCharacters(MessageFormat.format("<code:param value=\"{0}\" />", pvl));
                            }
                        }
                        if (!execute.getParamVar().isEmpty()) {
                            String processParam = processParam(execute.getParamVar());
                            writeEscapedCharacters(processParam);
                        }
                        writer.writeEndElement();
                    }
                }
                writer.writeEndElement();//end c:when for resource
                writer.writeCharacters(System.lineSeparator());
            }
            writer.writeStartElement("c:otherwise");

            writer.writeStartElement("json:object");
            writer.writeAttribute("name", "data");

            writer.writeEmptyElement("json:property");
            writer.writeAttribute("name", "Code");
            writer.writeAttribute("value", "405");
            writer.writeEmptyElement("json:property");
            writer.writeAttribute("name", "Message");
            writer.writeAttribute("value", "Method not allowed");

            writer.writeEndElement();//end json:object

            writer.writeEndElement();//end c:otherwise

            writer.writeEndElement();//end c:choose for resource
            writer.flush();
            writer.close();
        } catch (JAXBException | FileNotFoundException | XMLStreamException ex) {
            Logger.getLogger(JAXBParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | XPathExpressionException ex) {
            Logger.getLogger(JAXBParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resource;
    }

    private void writeEscapedCharacters(String data) throws XMLStreamException, IOException, XPathExpressionException {
        writer.writeCharacters("");
        writer.flush();
        OutputStreamWriter osw = new OutputStreamWriter(output);
        osw.write(data);
        osw.flush();
    }

    private String enclose(String expression) {
        return "${" + expression + "}";
    }

    private String processSQL(String query) {

        StringBuilder builder = null;
        List<String> params = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\@(\\w+)");
        Matcher match = pattern.matcher(query);
        while (match.find()) {
            params.add(query.substring(match.start(1), match.end(1)));
        }
//        System.out.println(params);
        builder = new StringBuilder(query.replaceAll("\\@\\w+", "?"));
        for (String param : params) {
            if (param.equals("id")) {
                builder.append("<sql:param value=\"${mtgReq.id}\"/>");
            } else {
                builder.append(MessageFormat.format("<sql:param value=\"$'{'mtgReq.params.{0}'}\" />", param));
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private String processParam(List<ParamVar> paramVar) {
        StringBuilder builder = new StringBuilder();
        for (ParamVar param : paramVar) {
            if (param.getName().equals("id")) {
                builder.append("<code:param value=\"${mtgReq.id}\"/>");
            } else {
                builder.append(MessageFormat.format("<code:param value=\"$'{'mtgReq.params.{0}'}\" />", param.getName()));
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
