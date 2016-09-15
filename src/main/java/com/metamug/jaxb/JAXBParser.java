/**
 * ***********************************************************************
 * Freeware Licence Agreement
 *
 * This licence agreement only applies to the free version of this software.
 *
 * Terms and Conditions
 *
 * BY DOWNLOADING, INSTALLING, USING, TRANSMITTING, DISTRIBUTING OR COPYING THIS SOFTWARE ("THE SOFTWARE"), YOU AGREE TO THE TERMS OF THIS AGREEMENT (INCLUDING THE SOFTWARE LICENCE AND DISCLAIMER OF
 * WARRANTY) WITH METAMUG THE OWNER OF ALL RIGHTS IN RESPECT OF THE SOFTWARE.
 *
 * PLEASE READ THIS DOCUMENT CAREFULLY BEFORE USING THE SOFTWARE.
 *
 * IF YOU DO NOT AGREE TO ANY OF THE TERMS OF THIS LICENCE THEN DO NOT DOWNLOAD, INSTALL, USE, TRANSMIT, DISTRIBUTE OR COPY THE SOFTWARE.
 *
 * THIS DOCUMENT CONSTITUTES A LICENCE TO USE THE SOFTWARE ON THE TERMS AND CONDITIONS APPEARING BELOW.
 *
 * The Software is licensed to you without charge for use only upon the terms of this licence, and METAMUG TECHNOLOGIES LLP (hereafter METAMUG) reserves all rights not expressly granted to you.
 * METAMUG retains ownership of all copies of the Software.
 *
 * 1. Licence
 *
 * You may use the Software without charge.
 *
 * You may freely distribute exact copies of the Software to anyone.
 *
 * The inclusion of the Software in any shareware, freeware or similar media compilation or distribution method whereby it is made available at cost (ie. sold) is strictly prohibited.
 *
 * The selling of the Software is strictly prohibited. 2. Restrictions
 *
 * METAMUG reserves the right to revoke the above distribution right at any time, for any or no reason.
 *
 * YOU MAY NOT MODIFY, ADAPT, TRANSLATE, RENT, LEASE, LOAN, SELL, ONSELL, REQUEST DONATIONS OR CREATE DERIVATIVE WORKS BASED UPON THE SOFTWARE OR ANY PART THEREOF.
 *
 * The Software contains intellectual property and to protect them you may not decompile, reverse engineer, disassemble or otherwise reduce the Software to a humanly perceivable form. You agree not to
 * divulge, directly or indirectly, until such intellectual property cease to be confidential, for any reason not your own fault.
 *
 * 3. Termination
 *
 * This licence is effective until terminated. The Licence will terminate automatically without notice from METAMUG if you fail to comply with any provision of this Licence. Upon termination you must
 * destroy the Software and all copies thereof. You may terminate this Licence at any time by destroying the Software and all copies thereof. Upon termination of this licence for any reason you shall
 * continue to be bound by the provisions of Section 2 above. Termination will be without prejudice to any rights METAMUG may have as a result of this agreement.
 *
 * 4. Disclaimer of Warranty, Limitation of Remedies
 *
 * TO THE FULL EXTENT PERMITTED BY LAW, METAMUG HEREBY EXCLUDES ALL CONDITIONS AND WARRANTIES, WHETHER IMPOSED BY STATUTE OR BY OPERATION OF LAW OR OTHERWISE, NOT EXPRESSLY SET OUT HEREIN. THE
 * SOFTWARE, AND ALL ACCOMPANYING FILES, DATA AND MATERIALS ARE DISTRIBUTED "AS IS" AND WITH NO WARRANTIES OF ANY KIND, WHETHER EXPRESS OR IMPLIED. METAMUG DOES NOT WARRANT, GUARANTEE OR MAKE ANY
 * REPRESENTATIONS REGARDING THE USE, OR THE RESULTS OF THE USE, OF THE SOFTWARE WITH RESPECT TO ITS CORRECTNESS, ACCURACY, RELIABILITY, CURRENTNESS OR OTHERWISE. THE ENTIRE RISK OF USING THE SOFTWARE
 * IS ASSUMED BY YOU. METAMUG MAKES NO EXPRESS OR IMPLIED WARRANTIES OR CONDITIONS INCLUDING, WITHOUT LIMITATION, THE WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE WITH RESPECT TO
 * THE SOFTWARE. NO ORAL OR WRITTEN INFORMATION OR ADVICE GIVEN BY METAMUG, IT'S DISTRIBUTORS, AGENTS OR EMPLOYEES SHALL CREATE A WARRANTY, AND YOU MAY NOT RELY ON ANY SUCH INFORMATION OR ADVICE.
 *
 * IMPORTANT NOTE: Nothing in this Agreement is intended or shall be construed as excluding or modifying any statutory rights, warranties or conditions which by virtue of any national or state Fair
 * Trading, Trade Practices or other such consumer legislation may not be modified or excluded. If permitted by such legislation, however, METAMUG's liability for any breach of any such warranty or
 * condition shall be and is hereby limited to the supply of the Software licensed hereunder again as METAMUG at its sole discretion may determine to be necessary to correct the said breach.
 *
 * IN NO EVENT SHALL METAMUG BE LIABLE FOR ANY SPECIAL, INCIDENTAL, INDIRECT OR CONSEQUENTIAL DAMAGES (INCLUDING, WITHOUT LIMITATION, DAMAGES FOR LOSS OF BUSINESS PROFITS, BUSINESS INTERRUPTION, AND
 * THE LOSS OF BUSINESS INFORMATION OR COMPUTER PROGRAMS), EVEN IF METAMUG OR ANY METAMUG REPRESENTATIVE HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. IN ADDITION, IN NO EVENT DOES METAMUG
 * AUTHORISE YOU TO USE THE SOFTWARE IN SITUATIONS WHERE FAILURE OF THE SOFTWARE TO PERFORM CAN REASONABLY BE EXPECTED TO RESULT IN A PHYSICAL INJURY, OR IN LOSS OF LIFE. ANY SUCH USE BY YOU IS
 * ENTIRELY AT YOUR OWN RISK, AND YOU AGREE TO HOLD METAMUG HARMLESS FROM ANY CLAIMS OR LOSSES RELATING TO SUCH UNAUTHORISED USE.
 *
 * 5. General
 *
 * All rights of any kind in the Software which are not expressly granted in this Agreement are entirely and exclusively reserved to and by METAMUG.
 *
 * This Agreement shall be governed by the laws of the State of Maharastra, India. Exclusive jurisdiction and venue for all matters relating to this Agreement shall be in courts and fora located in
 * the State of Maharastra, India, and you consent to such jurisdiction and venue. This agreement contains the entire Agreement between the parties hereto with respect to the subject matter hereof,
 * and supersedes all prior agreements and/or understandings (oral or written). Failure or delay by METAMUG in enforcing any right or provision hereof shall not be deemed a waiver of such provision or
 * right with respect to the instant or any subsequent breach. If any provision of this Agreement shall be held by a court of competent jurisdiction to be contrary to law, that provision will be
 * enforced to the maximum extent permissible, and the remaining provisions of this Agreement will remain in force and effect.
 */
package com.metamug.jaxb;

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
import java.util.regex.PatternSyntaxException;
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
                    + "    if (header != null && java.util.Arrays.asList(header.split(\"/\")).contains(\"xml\")) {\n"
                    + "        response.setContentType(\"application/xml;charset=UTF-8\");\n"
                    + "    } else {\n"
                    + "        response.setContentType(\"application/json;charset=UTF-8\");\n"
                    + "    }\n"
                    + "%>");
            writer.writeCharacters(System.lineSeparator());
            writer.writeStartElement("c:choose");
            for (Request req : resource.getRequest()) {
                writer.writeStartElement("c:when");
                if (req.isItem()) {
                    writer.writeAttribute("test", enclose("not empty mtgReq.id and mtgReq.method eq '" + req.getMethod() + "'"));
                } else {
                    writer.writeAttribute("test", enclose("empty mtgReq.id and mtgReq.method eq '" + req.getMethod() + "'"));
                }
                for (Param param : req.getParam()) {

                    isValid(param, "hell");
                }
                if (!req.getSql().isEmpty()) {
                    for (Sql sql : req.getSql()) {
                        if (sql.getType().value().equalsIgnoreCase("update")) {
                            writer.writeStartElement("sql:update");
                        } else {
                            writer.writeStartElement("sql:query");
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

                        if (sql.getClassName() == null && sql.getType().value().equalsIgnoreCase("query")) {
                            writer.writeStartElement("mtg:out");
                            writer.writeAttribute("value", enclose("result"));
                            writer.writeAttribute("type", enclose("header.accept"));
                            writer.writeAttribute("tableName", FilenameUtils.removeExtension(xmlFile.getName()));
                            writer.writeEndElement();
                        } else if (sql.getClassName() != null && sql.getType().value().equalsIgnoreCase("query")) {
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
                        writer.writeEndElement();
                    }
                }
                writer.writeEndElement();//end c:when for resource
                writer.writeCharacters(System.lineSeparator());
            }
            writer.writeStartElement("c:otherwise");
            writeEscapedCharacters("<%\n"
                    + "            response.setStatus(405);\n"
                    + "        %>");
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
                    modifiedStr = modifiedStr.replace(" ge ", " >= ");
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
        } catch (PatternSyntaxException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(JAXBParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InputValidationException ex) {
            System.out.println(ex.getMessage());
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

    public static void isValid(Param param, String str) throws PatternSyntaxException, InputValidationException {
        if ("".equals(str)) {
            if (!param.isBlank()) {
                throw new InputValidationException(param.getName() + " doesn't take blank input");
            }
        }
        if (param.isNum()) {
            String regex = "[0-9]+";
            if (!str.matches(regex)) {
                throw new InputValidationException(param.getName() + " accepts only numeric input value");
            }
            if (null != param.getMax()) {
                long val = Long.parseLong(str);
                long maxVal = Long.parseLong(param.getMax());
                if (val > maxVal) {
                    throw new InputValidationException("Max value allowed for " + param.getName() + " is " + maxVal);
                }
            }
            if (null != param.getMin()) {
                long val = Long.parseLong(str);
                long minVal = Long.parseLong(param.getMin());
                if (val < minVal) {
                    throw new InputValidationException("Min value allowed for " + param.getName() + " is " + minVal);
                }
            }
        } else {
            if (null != param.getPattern()) {
                if (!str.matches(param.getPattern())) {
                    throw new InputValidationException("Input value doesn't match with specified pattern of " + param.getName() + " parameter");
                }
            }
            if (null != param.getMaxLen()) {
                int maxLength = Integer.parseInt(param.getMaxLen());
                if (str.length() > maxLength) {
                    throw new InputValidationException("Input value can be " + maxLength + " character long for " + param.getName() + " parameter");
                }
            }
            if (null != param.getMinLen()) {
                int minLength = Integer.parseInt(param.getMinLen());
                if (str.length() < minLength) {
                    throw new InputValidationException("Input value must be " + minLength + " character long for " + param.getName() + " parameter");
                }
            }
        }
    }

//     private String processParam(List<Param> paramVar) {
//        StringBuilder builder = new StringBuilder();
//        for (Param param : paramVar) {
//            if (param.getParamName().equals("id")) {
//                builder.append("<code:param value=\"${mtgReq.id}\"/>");
//            } else {
//                builder.append(MessageFormat.format("<code:param value=\"$'{'mtgReq.params.{0}'}\" />", param.getParamName()));
//            }
//            builder.append("\n");
//        }
//        return builder.toString();
//    }
}
