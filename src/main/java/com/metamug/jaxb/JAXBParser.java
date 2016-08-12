package com.metamug.jaxb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.metamug.jaxb.docs.ApiDocGenerator;
import com.metamug.jaxb.docs.XslTransformer;
import com.metamug.jaxb.gener.Execute;
import com.metamug.jaxb.gener.Param;
import com.metamug.jaxb.gener.Request;
import com.metamug.jaxb.gener.Resource;
import com.metamug.jaxb.gener.Sql;
import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPathExpressionException;
import org.apache.commons.io.FilenameUtils;
import org.xml.sax.SAXException;

/**
 *
 * @author anish
 */
public class JAXBParser {

    OutputStream output;
    XMLOutputFactory factory = XMLOutputFactory.newInstance();
    XMLStreamWriter writer;

    public static void main(String[] args) throws TransformerConfigurationException, SAXException, IOException, FileNotFoundException, XMLStreamException, XPathExpressionException {
        File xml = new File(JAXBParser.class.getResource("/movies.xml").getFile());
        File xsd = new File(JAXBParser.class.getResource("/resource.xsd").getFile());
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(xsd);
        Validator validator = schema.newValidator();
        try {
            Source xmlFile = new StreamSource(xml);
            validator.validate(xmlFile);

            Resource resource = new JAXBParser().parse(xml);
            if (resource != null) {
                createHtml(resource, xml);
            }
        } catch (SAXException | IOException ex) {
            Logger.getLogger(JAXBParser.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (TransformerException ex) {
            Logger.getLogger(JAXBParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        new ApiDocGenerator().generate("/opt/tomcat8/api/semanticweb");
    }

    public static void createHtml(Resource resource, File xmlFile) throws IOException {
        try {
            File xsl = new File(JAXBParser.class.getResource("/resource.xsl").getFile());
            if (!new File("/opt/tomcat8/api/semanticweb/docs/v" + resource.getVersion()).exists()) {
                Files.createDirectory(Paths.get("/opt/tomcat8/api/semanticweb/docs/v" + resource.getVersion()));
            }
            File outHtml = new File("/opt/tomcat8/api/semanticweb/docs/v" + resource.getVersion() + "/" + FilenameUtils.removeExtension(xmlFile.getName()) + ".html");
            XslTransformer.transform(xmlFile, xsl, outHtml);
        } catch (TransformerException ex) {
            Logger.getLogger(JAXBParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Resource parse(File xmlFile) throws TransformerConfigurationException {
        Resource resource = new Resource();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Resource.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            resource = (Resource) jaxbUnmarshaller.unmarshal(xmlFile);
            if (!new File("/opt/tomcat8/api/semanticweb/WEB-INF/resources/v" + resource.getVersion()).exists()) {
                Files.createDirectory(Paths.get("/opt/tomcat8/api/semanticweb/WEB-INF/resources/v" + resource.getVersion()));
            }
            output = new FileOutputStream("/opt/tomcat8/api/semanticweb/WEB-INF/resources/v" + resource.getVersion() + File.separator + FilenameUtils.removeExtension(xmlFile.getName()) + ".jsp");
            writer = new IndentingXMLStreamWriter(factory.createXMLStreamWriter(output, "UTF-8"));
            writeEscapedCharacters("<%@include  file=\"../../fragments/taglibs.jspf\"%>");
            writer.writeCharacters(System.lineSeparator());
            writeEscapedCharacters("<%\n"
                    + "    String header = request.getHeader(\"Accept\");\n"
                    + "    if (header != null && Arrays.asList(header.split(\"/\")).contains(\"xml\")) {\n"
                    + "        response.setContentType(\"application/xml;charset=UTF-8\");\n"
                    + "    } else {\n"
                    + "        response.setContentType(\"application/json;charset=UTF-8\");\n"
                    + "    }\n"
                    + "%>");
            writer.writeCharacters(System.lineSeparator());
            writer.writeStartElement("c:choose");
            for (Request req : resource.getRequestOrCreateOrRead()) {

                writer.writeStartElement("c:when");
                if (req.isItem()) {
                    writer.writeAttribute("test", enclose("not empty mtgReq.id and mtgReq.method eq '" + req.getMethod() + "'"));
                } else {
                    writer.writeAttribute("test", enclose("empty mtgReq.id and mtgReq.method eq '" + req.getMethod() + "'"));
                }
                if (!req.getSql().isEmpty()) {
                    for (Sql sql : req.getSql()) {
                        if (sql.getType().equalsIgnoreCase("query")) {
                            writer.writeStartElement("sql:query");
                        } else {
                            writer.writeStartElement("sql:update");
                        }
                        writer.writeAttribute("var", "result");
                        writer.writeAttribute("dataSource", "jdbc/mtgMySQL");
                        if (sql.getWhen() != null) {
                            writer.writeStartElement("c:if");
                            writer.writeAttribute("test", enclose(sql.getWhen().replace("@", "mtgReq.params.")));
                            String processSQL = processSQL(sql.getValue());
                            writeEscapedCharacters(processSQL);
                            writer.writeEndElement(); //End of <c:if>
                        } else {
                            String processSQL = processSQL(sql.getValue());
                            writeEscapedCharacters(processSQL);
                        }
                        writer.writeEndElement();

                        if (sql.getClassName() == null && sql.getType().equalsIgnoreCase("query")) {
                            writer.writeStartElement("mtg:out");
                            writer.writeAttribute("value", enclose("result"));
                            writer.writeAttribute("type", enclose("header.accept"));
                            writer.writeAttribute("tableName", FilenameUtils.removeExtension(xmlFile.getName()));
                            writer.writeEndElement();
                        } else if (sql.getClassName() != null && sql.getType().equalsIgnoreCase("query")) {
                            writer.writeStartElement("code:execute");
                            writer.writeAttribute("className", sql.getClassName());
                            writer.writeAttribute("param", enclose("result"));
                            writer.writeEndElement();
                        }
                    }
                }
                if (!req.getExecute().isEmpty()) {
                    for (Execute execute : req.getExecute()) {
                        writer.writeStartElement("code:execute");
                        writer.writeAttribute("className", execute.getClassName());
                        writer.writeAttribute("param", enclose("mtgReq"));
//                        if (!execute.getParamVal().isEmpty()) {
//                            for (String pvl : execute.getParamVal()) {
//                                writeEscapedCharacters(MessageFormat.format("<code:param value=\"{0}\" />", pvl));
//                            }
//                        }
//                        if (!execute.getParamVar().isEmpty()) {
//                            String processParam = processParam(execute.getParamVar());
//                            writeEscapedCharacters(processParam);
//                        }
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
            List<String> newLines = new ArrayList<>();
            for (String line : Files.readAllLines(Paths.get("/opt/tomcat8/api/semanticweb/WEB-INF/resources/v" + resource.getVersion() + File.separator + FilenameUtils.removeExtension(xmlFile.getName()) + ".jsp"), StandardCharsets.UTF_8)) {
                String modifiedStr = line;
                if (line.toLowerCase().contains(" le ") || line.toLowerCase().contains(" le")) {
                    modifiedStr = line.replace(" le ", " <= ");
                }
                if (line.toLowerCase().contains(" ge ") || line.toLowerCase().contains(" ge")) {
                    modifiedStr = modifiedStr.replace(" gte ", " >= ");
                }
                if (line.toLowerCase().contains(" ne ")) {
                    modifiedStr = modifiedStr.replace(" ne ", " != ");
                }
                if (line.toLowerCase().contains(" lt ") || line.toLowerCase().contains(" lt")) {
                    modifiedStr = modifiedStr.replace(" lt ", " < ");
                }
                if (line.toLowerCase().contains(" gt ") || line.toLowerCase().contains(" gt")) {
                    modifiedStr = modifiedStr.replace(" gt ", " > ");
                }
                newLines.add(modifiedStr);
            }
            Files.write(Paths.get("/opt/tomcat8/api/semanticweb/WEB-INF/resources/v" + resource.getVersion() + File.separator + FilenameUtils.removeExtension(xmlFile.getName()) + ".jsp"), newLines, StandardCharsets.UTF_8);
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

    private String processParam(List<Param> paramVar) {
        StringBuilder builder = new StringBuilder();
        for (Param param : paramVar) {
            if (param.getParamName().equals("id")) {
                builder.append("<code:param value=\"${mtgReq.id}\"/>");
            } else {
                builder.append(MessageFormat.format("<code:param value=\"$'{'mtgReq.params.{0}'}\" />", param.getParamName()));
            }
            builder.append("\n");
        }
        return builder.toString();
    }
//    public boolean isValid(Param param, String value) {
//
//        if ("".equals(value)) {
//            if (!param.isBlank()) {
//                return false;
//            }
//        }
//        if (param.isNum()) {
//            String regex = "[0-9]+";
//            if (!value.matches(regex)) {
//                return false;
//            }
//            if (null != param.getMax()) {
//                long val = Long.parseLong(value);
//                long maxVal = Long.parseLong(param.getMax());
//                if (val > maxVal) {
//                    return false;
//                }
//            }
//            if (null != param.getMin()) {
//                long val = Long.parseLong(value);
//                long minVal = Long.parseLong(param.getMin());
//                if (val < minVal) {
//                    return false;
//                }
//            }
//        } else {
//            if (null != param.getPattern()) {
//                if (!value.matches(param.getPattern())) {
//                    return false;
//                }
//            }
//            if (null != param.getMaxLen()) {
//                int maxLength = Integer.parseInt(param.getMaxLen());
//                if (value.length() > maxLength) {
//                    return false;
//                }
//            }
//            if (null != param.getMinLen()) {
//                int minLength = Integer.parseInt(param.getMinLen());
//                if (value.length() < minLength) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
}
