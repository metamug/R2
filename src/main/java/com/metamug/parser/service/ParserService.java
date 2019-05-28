/**
 * ***********************************************************************
 * Freeware License Agreement
 *
 * This license agreement only applies to the free version of this software.
 *
 * Terms and Conditions
 *
 * BY DOWNLOADING, INSTALLING, USING, TRANSMITTING, DISTRIBUTING OR COPYING THIS SOFTWARE ("THE SOFTWARE"), YOU AGREE TO THE TERMS OF THIS AGREEMENT (INCLUDING THE SOFTWARE LICENSE AND DISCLAIMER OF WARRANTY) WITH METAMUG THE OWNER OF ALL RIGHTS IN RESPECT OF THE SOFTWARE.
 *
 * PLEASE READ THIS DOCUMENT CAREFULLY BEFORE USING THE SOFTWARE.
 *
 * IF YOU DO NOT AGREE TO ANY OF THE TERMS OF THIS LICENSE THEN DO NOT DOWNLOAD, INSTALL, USE, TRANSMIT, DISTRIBUTE OR COPY THE SOFTWARE.
 *
 * THIS DOCUMENT CONSTITUTES A LICENSE TO USE THE SOFTWARE ON THE TERMS AND CONDITIONS APPEARING BELOW.
 *
 * The Software is licensed to you without charge for use only upon the terms of this license, and METAMUG TECHNOLOGIES LLP (hereafter METAMUG) reserves all rights not expressly granted to you. METAMUG retains ownership of all copies of the Software.
 *
 * 1. License
 *
 * You may use the Software without charge.
 *
 * You may freely distribute exact copies of the Software to anyone.
 *
 * The inclusion of the Software in any Shareware, Freeware or similar media compilation or distribution method whereby it is made available at cost (ie. sold) is strictly prohibited.
 *
 * The selling of the Software is strictly prohibited.
 * 2. Restrictions
 *
 * METAMUG reserves the right to revoke the above distribution right at any time, for any or no reason.
 *
 * YOU MAY NOT MODIFY, ADAPT, TRANSLATE, RENT, LEASE, LOAN, SELL, ONSELL, REQUEST DONATIONS OR CREATE DERIVATIVE WORKS BASED UPON THE SOFTWARE OR ANY PART THEREOF.
 *
 * The Software contains intellectual property and to protect them you may not decompile, reverse engineer, disassemble or otherwise reduce the Software to a humanly perceivable form. You agree not to divulge, directly or indirectly, until such intellectual property ceases to be confidential, for any reason not your own fault.
 *
 * 3. Termination
 *
 * This license is effective until terminated. The License will terminate automatically without notice from METAMUG if you fail to comply with any provision of this License. Upon termination, you must destroy the Software and all copies thereof. You may terminate this License at any time by destroying the Software and all copies thereof. Upon termination of this license for any reason, you shall continue to be bound by the provisions of Section 2 above. Termination will be without prejudice to any rights METAMUG may have as a result of this agreement.
 *
 * 4. Disclaimer of Warranty, Limitation of Remedies
 *
 * TO THE FULL EXTENT PERMITTED BY LAW, METAMUG HEREBY EXCLUDES ALL CONDITIONS AND WARRANTIES, WHETHER IMPOSED BY STATUTE OR BY OPERATION OF LAW OR OTHERWISE, NOT EXPRESSLY SET OUT HEREIN. THE SOFTWARE, AND ALL ACCOMPANYING FILES, DATA AND MATERIALS ARE DISTRIBUTED "AS IS" AND WITH NO WARRANTIES OF ANY KIND, WHETHER EXPRESS OR IMPLIED. METAMUG DOES NOT WARRANT, GUARANTEE OR MAKE ANY REPRESENTATIONS REGARDING THE USE, OR THE RESULTS OF THE USE, OF THE SOFTWARE WITH RESPECT TO ITS CORRECTNESS, ACCURACY, RELIABILITY, CURRENTNESS OR OTHERWISE. THE ENTIRE RISK OF USING THE SOFTWARE IS ASSUMED BY YOU. METAMUG MAKES NO EXPRESS OR IMPLIED WARRANTIES OR CONDITIONS INCLUDING, WITHOUT LIMITATION, THE WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE WITH RESPECT TO THE SOFTWARE. NO ORAL OR WRITTEN INFORMATION OR ADVICE GIVEN BY METAMUG, IT'S DISTRIBUTORS, AGENTS OR EMPLOYEES SHALL CREATE A WARRANTY, AND YOU MAY NOT RELY ON ANY SUCH INFORMATION OR ADVICE.
 *
 * IMPORTANT NOTE: Nothing in this Agreement is intended or shall be construed as excluding or modifying any statutory rights, warranties or conditions which by virtue of any national or state Fair Trading, Trade Practices or other such consumer legislation may not be modified or excluded. If permitted by such legislation, however, METAMUG's liability for any breach of any such warranty or condition shall be and is hereby limited to the supply of the Software licensed hereunder again as METAMUG at its sole discretion may determine to be necessary to correct the said breach.
 *
 * IN NO EVENT SHALL METAMUG BE LIABLE FOR ANY SPECIAL, INCIDENTAL, INDIRECT OR CONSEQUENTIAL DAMAGES (INCLUDING, WITHOUT LIMITATION, DAMAGES FOR LOSS OF BUSINESS PROFITS, BUSINESS INTERRUPTION, AND THE LOSS OF BUSINESS INFORMATION OR COMPUTER PROGRAMS), EVEN IF METAMUG OR ANY METAMUG REPRESENTATIVE HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. IN ADDITION, IN NO EVENT DOES METAMUG AUTHORIZE YOU TO USE THE SOFTWARE IN SITUATIONS WHERE FAILURE OF THE SOFTWARE TO PERFORM CAN REASONABLY BE EXPECTED TO RESULT IN A PHYSICAL INJURY, OR IN LOSS OF LIFE. ANY SUCH USE BY YOU IS ENTIRELY AT YOUR OWN RISK, AND YOU AGREE TO HOLD METAMUG HARMLESS FROM ANY CLAIMS OR LOSSES RELATING TO SUCH UNAUTHORIZED USE.
 *
 * 5. General
 *
 * All rights of any kind in the Software which are not expressly granted in this Agreement are entirely and exclusively reserved to and by METAMUG.
 *
 * This Agreement shall be governed by the laws of the State of Maharashtra, India. Exclusive jurisdiction and venue for all matters relating to this Agreement shall be in courts and fora located in the State of Maharashtra, India, and you consent to such jurisdiction and venue. This agreement contains the entire Agreement between the parties hereto with respect to the subject matter hereof, and supersedes all prior agreements and/or understandings (oral or written). Failure or delay by METAMUG in enforcing any right or provision hereof shall not be deemed a waiver of such provision or right with respect to the instant or any subsequent breach. If any provision of this Agreement shall be held by a court of competent jurisdiction to be contrary to law, that provision will be enforced to the maximum extent permissible, and the remaining provisions of this Agreement will remain in force and effect.
 */
package com.metamug.parser.service;

import com.metamug.parser.RPXParser;
import com.metamug.parser.exception.ResourceTestException;
import com.metamug.parser.util.Utils;
import com.metamug.schema.Execute;
import com.metamug.schema.Param;
import com.metamug.schema.Request;
import com.metamug.schema.Resource;
import com.metamug.schema.Sql;
import com.metamug.schema.Xheader;
import com.metamug.schema.Xparam;
import com.metamug.schema.Xrequest;
import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;
import org.xml.sax.SAXException;

/**
 *
 * @author Kainix
 */
public class ParserService {

    protected static final String MASON_DATASOURCE = "datasource";
    protected static final String MASON_OUTPUT = "masonOutput";
    public static final String MTG_PERSIST_MAP = "mtgPersist";

    protected String appName;
    protected String resourceName;
    protected double resourceVersion;
    protected JSONObject queryMap;

    protected String OUTPUT_FOLDER;
    OutputStream output;
    XMLOutputFactory factory = XMLOutputFactory.newInstance();

    protected HashSet<String> elementIds;

    // Number added as prefix to 'data' so as to generate unique keys to store in map against the resultset of sql:query
    //int count = 0;
    public JSONObject transform(File uploadedFile, String appName, boolean isOldFile, String outputFolder,
            String domain, JSONObject queryMap) throws SAXException, FileAlreadyExistsException, FileNotFoundException, XMLStreamException,
            XPathExpressionException, ParserConfigurationException, TransformerException, JAXBException,
            URISyntaxException, IOException, SQLException, ClassNotFoundException, PropertyVetoException, ResourceTestException {
        this.appName = appName;
        this.resourceName = Utils.removeExtension(uploadedFile.getName());
        this.queryMap = queryMap;

        OUTPUT_FOLDER = outputFolder;

        RPXParser parser = new RPXParser(OUTPUT_FOLDER, appName, uploadedFile);
        Resource parsedResource = parser.parseFromXml();

        this.resourceVersion = parsedResource.getVersion();

        //make test queries requests
        if (null != domain) {
            ResourceTestService testService = new ResourceTestService();
            testService.testResource(parsedResource, domain, appName);
        }

        Resource resource = createJsp(parsedResource, uploadedFile, isOldFile, domain);

        JSONObject obj = new JSONObject();
        obj.put("version", resource.getVersion());
        if (resource.getAuth() != null && !resource.getAuth().isEmpty()) {
            obj.put("secure", true);
            obj.put("auth",resource.getAuth());
        } else {
            obj.put("secure", false);      
        }
        return obj;
    }

    public Resource createJsp(Resource resource, File resourceFile, boolean isOldFile, String domain)
            throws JAXBException, SAXException, IOException, FileNotFoundException, XPathExpressionException,
            TransformerException, URISyntaxException, XMLStreamException, ResourceTestException {

        String resourceDir = OUTPUT_FOLDER + File.separator + appName + File.separator
                + "WEB-INF" + File.separator + "resources" + File.separator;

        if (!new File(resourceDir + "v" + resource.getVersion()).exists()) {
            Files.createDirectories(Paths.get(resourceDir + "v" + resource.getVersion()));
        }

        if (!new File(resourceDir + "v" + resource.getVersion() + File.separator + FilenameUtils.removeExtension(resourceFile.getName()) + ".jsp").exists() || isOldFile) {
            output = new FileOutputStream(resourceDir + "v" + resource.getVersion() + File.separator + FilenameUtils.removeExtension(resourceFile.getName()) + ".jsp");
            XMLStreamWriter writer = new IndentingXMLStreamWriter(factory.createXMLStreamWriter(output));

            printHeaderAndGroup(writer, resource);

            elementIds = new HashSet<>();

            for (Request req : resource.getRequest()) {
                writer.writeStartElement("m:request");
                initializeRequest(writer, req);

                //Add UploadListener tag
                if (req.getMethod().value().equalsIgnoreCase("POST")) {
                    writer.writeEmptyElement("m:upload");
                }
                List elements = req.getParamOrSqlOrExecuteOrXrequestOrScript();

                printRequestElements(elements, writer, domain);

                //end m:request
                closeRequest(writer);

                writer.writeCharacters(System.lineSeparator());
            }

            writer.writeEndElement();//end m:resource
            writer.flush();
            writer.close();

            output.close();
            escapeSpecialCharacters(resource, appName, resourceFile);

            return resource;
        } else {
            //user is trying to create new resource with already created resource 
            throw new FileAlreadyExistsException(FilenameUtils.removeExtension(resourceFile.getName())
                    + " file with version no. " + resource.getVersion() + " already exists.");
        }
    }

    protected void printRequestElements(List elements, XMLStreamWriter writer, String domain) throws XMLStreamException, IOException, XPathExpressionException, SAXException, ResourceTestException {
        for (Object object : elements) {
            if (object instanceof Param) {
                Param param = (Param) object;
                printParamTag(writer, param);
            } else if (object instanceof Sql) {
                Sql sql = (Sql) object;
                String tag = sql.getId();
                elementIds.add(tag);

                if (sql.getOnerror() != null && sql.getOnerror().length() > 0) {
                    startValidateTag(writer, sql.getOnerror());
                }

                String ref = sql.getRef();
                QueryManagerService service = new QueryManagerService();
                String url = domain + "/" + appName;
                String version = Double.toString(resourceVersion);
                String sqlValue = ResourceTestService.replaceEscapeCharacters(sql.getValue().trim());

                if (ref == null) {
                    service.saveQueryWithTag(url, sqlValue, this.resourceName, version, tag, sql.getType().value(), appName);
                } else {
                    service.saveRefWithTag(url, ref, this.resourceName, version, tag, appName);
                }

                printSqlTag(sql, writer);

                if (sql.getOnerror() != null && sql.getOnerror().length() > 0) {
                    closeValidateTag(writer);
                }
            } else if (object instanceof Execute) {
                Execute execute = (Execute) object;
                elementIds.add(execute.getId());
                printExecuteTag(execute, writer);
            } else if (object instanceof Xrequest) {
                Xrequest xr = (Xrequest) object;
                elementIds.add(xr.getId());
                printXrequestTag(xr, writer);
            }
        }
    }

    /**
     * Initializes the JSP page and add necessary tags for Auth group and nested resource handling.
     *
     * @param writer XMLStreamWriter to write to JSP file.
     * @param resource Marshalled Resource object of resource file.
     * @throws XMLStreamException
     */
    private void printHeaderAndGroup(XMLStreamWriter writer, Resource resource) throws XMLStreamException {
        writer.writeEmptyElement("jsp:directive.include");
        writer.writeAttribute("file", "../fragments/mason-init.jspf");
        writer.writeCharacters(System.lineSeparator());

        writer.writeEmptyElement("jsp:useBean");
        writer.writeAttribute("id", MTG_PERSIST_MAP);
        writer.writeAttribute("class", "java.util.LinkedHashMap");
        writer.writeAttribute("scope", "request");
        writer.writeCharacters(System.lineSeparator());

        writer.writeStartElement("m:resource");

        //Add a Auth group resource tag
        if (resource.getAuth() != null) {
            writer.writeAttribute("auth", resource.getAuth());
        }

        writer.writeCharacters(System.lineSeparator());

        //Add a Parent tag
        if (resource.getParent() != null) {
            writer.writeEmptyElement("m:parent");
            writer.writeAttribute("value", resource.getParent());
            writer.writeCharacters(System.lineSeparator());
        }
    }

    /**
     * Write method and item attributes according to whether the Request is an item or collection Request.
     *
     * @param writer XMLStreamWriter to write to JSP file.
     * @param req Request object defined by user in Resource file.
     * @throws XMLStreamException
     */
    private void initializeRequest(XMLStreamWriter writer, Request req) throws XMLStreamException {
        writer.writeAttribute("method", req.getMethod().value());
        if (req.isItem()) {
            writer.writeAttribute("item", "true");
        }
    }

    /**
     * I
     *
     * @param writer XMLStreamWriter to write to JSP file.
     * @param param Parameter set that has to be initialized.
     * @throws XMLStreamException
     * @throws IOException
     * @throws XPathExpressionException
     */
    protected void printParamTag(XMLStreamWriter writer, Param param) throws XMLStreamException, IOException, XPathExpressionException {
        writer.writeCharacters(System.lineSeparator());
        writeEscapedCharacters(writer, processParam(param));
    }

    /**
     * Adds validation tag to catch exception generated by incorrect query.
     *
     * @param writer XMLStreamWriter to write to JSP file.
     * @param errorMessage Error message to be printed.
     * @throws XMLStreamException
     */
    protected void startValidateTag(XMLStreamWriter writer, String errorMessage) throws XMLStreamException {
        writer.writeStartElement("m:validate");
        writer.writeAttribute("onError", errorMessage);
    }

    /**
     * Prints Query tag along with necessary test conditions as mentioned in Query tag.
     *
     * @param sql Sql object which is to be converted
     * @param writer XMLStreamWriter to write to JSP file.
     * @throws XMLStreamException
     * @throws IOException
     * @throws SAXException
     * @throws XPathExpressionException
     */
    protected void printSqlTag(Sql sql, XMLStreamWriter writer)
            throws XMLStreamException, IOException, SAXException, XPathExpressionException {
        //Check for empty the Sql tag
        if (!sql.getValue().trim().isEmpty()) {
            if (sql.getWhen() != null) {
                writer.writeStartElement("c:if");
                String testString = getQuotedString(sql.getWhen());
                writer.writeAttribute("test", enclose(testString.replace("$", "mtgReq.params")));
            }
            //Print params those are marked as 'requires' in <Sql>
            String requiredParams = sql.getRequires();
            if (requiredParams != null) {
                for (String param : requiredParams.split(",")) {
                    writer.writeEmptyElement("m:param");
                    writer.writeAttribute("name", param);
                    writer.writeAttribute("type", "");
                    writer.writeAttribute("value", enclose("mtgReq.params['" + param + "']"));
                    writer.writeAttribute("isRequired", "true");
                }
            }

            if (sql.getLimit() != null || sql.getOffset() != null) {
                if (sql.getType() != null && sql.getType().value().equalsIgnoreCase("query")) {
                    if (sql.getLimit() != null) {
                        writer.writeEmptyElement("m:param");
                        writer.writeAttribute("name", String.valueOf(sql.getLimit()));
                        writer.writeAttribute("value", enclose("mtgReq.params['" + sql.getLimit() + "']"));
                        writer.writeAttribute("type", "number");
                        writer.writeAttribute("defaultValue", "-1");
                    }
                } else {
                    throw new SAXException("Offset or limit attribute can't be used for Update query");
                }
            }

            writer.writeCharacters(System.lineSeparator());

            if (sql.getType() != null && sql.getType().value().equalsIgnoreCase("update")) {
                writer.writeStartElement("sql:update");
            } else {
                writer.writeStartElement("sql:query");
            }

            writer.writeAttribute("var", "result");
            writer.writeAttribute("dataSource", enclose(MASON_DATASOURCE));

            if (sql.getLimit() != null || sql.getOffset() != null) {
                if (sql.getType() != null && sql.getType().value().equalsIgnoreCase("query")) {
                    if (sql.getLimit() != null) {
                        writer.writeAttribute("maxRows", enclose("mtgReq.params['" + sql.getLimit() + "']"));
                    }

                    if (sql.getOffset() != null) {
                        writer.writeAttribute("startRow", enclose("mtgReq.params['" + sql.getOffset() + "']"));
                    }

                } else {
                    throw new SAXException("Offset or limit attribute can't be used for Update query");
                }
            }
            //writer.writeCharacters(System.lineSeparator());
            String sqlParams = getSqlParams(sql);

            writeEscapedCharacters(writer, sqlParams);

            writer.writeEndElement();//End of <sql:query/update>
            //Store the sql data in map for <sql:query> or <sql:update>  

            writer.writeCharacters(System.lineSeparator());

            printSqlEnd(sql, writer);
        }
    }

    protected void printSqlEnd(Sql sql, XMLStreamWriter writer) throws XMLStreamException {
        //Don't insert result into the map when
        /**
         * 1. Its an 'Update' query with verbose attribute set to 'FALSE' or NOT SET at all
         *
         * 2. Its a 'Query' query with verbose attribute set to 'FALSE' explicitly.
         */
        boolean isVerbose = isVerbose(sql);

        String sqlVar = "result";
        if (sql.getType().value().equalsIgnoreCase("update") && isVerbose && sql.getClassname() == null) {
            printCSet(writer, enclose(MASON_OUTPUT), sql.getId(), enclose(sqlVar));
        } else if (sql.getType().value().equalsIgnoreCase("query")) {
            if (sql.getPersist()) {
                printConvert(writer, enclose(MTG_PERSIST_MAP), sql.getId(), enclose(sqlVar));
            }
            if (sql.getClassname() == null) {
                /*
                    if (sql.getCollect() && isVerbose) {
                        writer.writeEmptyElement("c:set");
                        writer.writeAttribute("target", enclose(MASON_OUTPUT));
                        writer.writeAttribute("property", "dc" + (count++));
                        writer.writeAttribute("value", enclose("result"));
                }*/
                if (isVerbose) {
                    printCSet(writer, enclose(MASON_OUTPUT), sql.getId(), enclose(sqlVar));
                }
            } else {
                writer.writeEmptyElement("m:execute");
                writer.writeAttribute("className", sql.getClassname());
                String execVar = "execResult";
                writer.writeAttribute("var", execVar);
                writer.writeAttribute("param", enclose(sqlVar));

                if (isVerbose) {
                    printCSet(writer, enclose(MASON_OUTPUT), sql.getId(), enclose(execVar));
                }
            }
        }
        if (sql.getWhen() != null) {
            writer.writeEndElement(); //End of <c:if>
        }
    }

    /**
     * Prints mtg:execute tag to call Java code for code execution.
     *
     * @param execute Execute object which is to be converted
     * @param writer XMLStreamWriter to write to JSP file.
     * @throws XMLStreamException
     * @throws SAXException
     */
    protected void printExecuteTag(Execute execute, XMLStreamWriter writer) throws XMLStreamException, SAXException {
        if (execute.getWhen() != null) {
            writer.writeStartElement("c:if");
            String testString = getQuotedString(execute.getWhen());
            writer.writeAttribute("test", enclose(testString.replace("$", "mtgReq.params")));
        }
        //Print params those are marked as 'requires' in <Execute>
        String requiredParams = execute.getRequires();
        if (requiredParams != null) {
            for (String param : requiredParams.split(",")) {
                writer.writeEmptyElement("m:param");
                writer.writeAttribute("name", param);
                writer.writeAttribute("type", "");
                writer.writeAttribute("value", enclose("mtgReq.params['" + param + "']"));
                writer.writeAttribute("isRequired", "true");
            }
        }
        writer.writeCharacters(System.lineSeparator());
        writer.writeEmptyElement("m:execute");
        String execVar = "execResult";
        writer.writeAttribute("var", execVar);
        writer.writeAttribute("className", execute.getClassName());
        writer.writeAttribute("param", enclose("mtgReq"));
        if (execute.getPersist()) {
            writer.writeAttribute("persistParam", enclose(MTG_PERSIST_MAP));
        }

        //Sets Verbose,Persist and Collect attributes
        if (execute.getVerbose() != null && execute.getVerbose()) {
            printCSet(writer, enclose(MASON_OUTPUT), execute.getId(), enclose(execVar));
        }
        if (execute.getPersist()) {
            //printCSet(writer, enclose(MTG_PERSIST_MAP), execute.getId(), enclose(execVar));
            printConvert(writer, enclose(MTG_PERSIST_MAP), execute.getId(), enclose(execVar));
        }

        /*
        if (execute.getCollect()) {
            writer.writeAttribute("isCollect", "true");
        }*/
        if (execute.getOnerror() != null && execute.getOnerror().length() > 0) {
            writer.writeAttribute("onError", execute.getOnerror());
        }

        // Sets status code
        /*if (execute.getStatus() != null) {
            writer.writeEmptyElement("mtg:status");
            writer.writeAttribute("value", String.valueOf(execute.getStatus()));
        }*/
        writer.writeCharacters(System.lineSeparator());

        if (execute.getWhen() != null) {
            writer.writeEndElement(); //End of <c:if>
        }
    }

    /**
     * Prints mtg:xrequest tag to call Java code for making HTTP requests to 3rd party APIs.
     *
     * @param xrequest Xrequest object which is to be converted
     * @param writer XMLStreamWriter to write to JSP file.
     * @throws XMLStreamException
     * @throws SAXException
     */
    protected void printXrequestTag(Xrequest xrequest, XMLStreamWriter writer)
            throws XMLStreamException, SAXException {
        if (xrequest.getWhen() != null) {
            writer.writeStartElement("c:if");
            String testString = getQuotedString(xrequest.getWhen());
            writer.writeAttribute("test", enclose(testString.replace("$", "mtgReq.params")));
        }
        writer.writeCharacters(System.lineSeparator());
        writer.writeStartElement("m:xrequest");
        String xreqVar = "xreqResult";
        writer.writeAttribute("var", xreqVar);
        writer.writeAttribute("method", xrequest.getMethod().name());
        writer.writeAttribute("url", StringEscapeUtils.unescapeXml(xrequest.getUrl()));
        for (Object paramOrHeaderOrBody : xrequest.getParamOrHeaderOrBody()) {
            if (paramOrHeaderOrBody instanceof Xheader) {
                writer.writeEmptyElement("m:xheader");
                writer.writeAttribute("name", ((Xheader) paramOrHeaderOrBody).getName());
                writer.writeAttribute("value", ((Xheader) paramOrHeaderOrBody).getValue());
            } else if (paramOrHeaderOrBody instanceof Xparam) {
                writer.writeEmptyElement("m:xparam");
                writer.writeAttribute("name", ((Xparam) paramOrHeaderOrBody).getName());
                String v = transformXReqParams(((Xparam) paramOrHeaderOrBody).getValue());
                writer.writeAttribute("value", v);
            } else if (paramOrHeaderOrBody instanceof String) {
                writer.writeStartElement("m:xbody");
                String body = transformXReqParams((String) paramOrHeaderOrBody);
                writer.writeCharacters(body);
                writer.writeEndElement();
            }
        }
        writer.writeEndElement(); //End of <m:xrequest>    
        writer.writeCharacters(System.lineSeparator());
        if (xrequest.isVerbose()) {
            printCSet(writer, enclose(MASON_OUTPUT), xrequest.getId(), enclose(xreqVar));
        }
        if (xrequest.isPersist()) {
            printConvert(writer, enclose(MTG_PERSIST_MAP), xrequest.getId(), enclose(xreqVar));
        }
        if (xrequest.getWhen() != null) {
            writer.writeEndElement(); //End of <c:if>
        }
    }

    protected void printConvert(XMLStreamWriter writer, String target, String prop, String result) throws XMLStreamException {
        writer.writeEmptyElement("m:convert");
        writer.writeAttribute("target", target);
        writer.writeAttribute("property", prop);
        writer.writeAttribute("value", result);
    }

    protected void printCSet(XMLStreamWriter writer, String target, String prop, String value) throws XMLStreamException {
        writer.writeEmptyElement("c:set");
        writer.writeAttribute("target", target);
        writer.writeAttribute("property", prop);
        writer.writeAttribute("value", value);
    }

    /**
     * Closes the m:validate tag
     *
     * @param writer XMLStreamWriter to write to JSP file.
     * @throws XMLStreamException
     */
    protected void closeValidateTag(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeEndElement();
    }

    /**
     * Close the m:request for Request tag.
     *
     * @param writer XMLStreamWriter to write to JSP file.
     * @throws XMLStreamException
     */
    private void closeRequest(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeEndElement();
        writer.writeCharacters(System.lineSeparator());
    }

    /**
     *
     * @param resource
     * @throws IOException
     */
    private void escapeSpecialCharacters(Resource resource, String appName, File resourceFile) throws IOException {
        List<String> newLines = new ArrayList<>();
        for (String line : Files.readAllLines(Paths.get(OUTPUT_FOLDER + File.separator + appName + "/WEB-INF/resources/v" + resource.getVersion() + File.separator + FilenameUtils.removeExtension(resourceFile.getName()) + ".jsp"), StandardCharsets.UTF_8)) {
            String modifiedStr = line;
            if (line.toLowerCase().matches(".*\\sle(\\s|\\b).*")) {
                modifiedStr = line.replaceAll("\\sle(\\s|\\b)", " <= ");
            }
            if (line.toLowerCase().matches(".*\\sge(\\s|\\b).*")) {
                modifiedStr = modifiedStr.replaceAll("\\sge(\\s|\\b)", " >= ");
            }
            if (line.toLowerCase().matches(".*\\seq(\\s|\\b).*")) {
                modifiedStr = modifiedStr.replaceAll("\\seq(\\s|\\b)", " = ");
            }
            if (line.toLowerCase().matches(".*\\sne(\\s|\\b).*")) {
                modifiedStr = modifiedStr.replaceAll("\\sne(\\s|\\b)", " != ");
            }
            if (line.toLowerCase().matches(".*\\slt(\\s|\\b).*")) {
                modifiedStr = modifiedStr.replaceAll("\\slt(\\s|\\b)", " < ");
            }
            if (line.toLowerCase().matches(".*\\sgt(\\s|\\b).*")) {
                modifiedStr = modifiedStr.replaceAll("\\sgt(\\s|\\b)", " > ");
            }
            newLines.add(modifiedStr.replaceAll("\\s+", " "));
        }
        Files.write(Paths.get(OUTPUT_FOLDER + File.separator + appName + "/WEB-INF/resources/v" + resource.getVersion() + File.separator + FilenameUtils.removeExtension(resourceFile.getName()) + ".jsp"), newLines, StandardCharsets.UTF_8);
    }

    protected void writeEscapedCharacters(XMLStreamWriter writer, String data) throws XMLStreamException, IOException, XPathExpressionException {
        writer.writeCharacters("");
        writer.flush();
        OutputStreamWriter osw = new OutputStreamWriter(output);
        osw.write(data);
        osw.flush();
    }

    protected String enclose(String expression) {
        return "${" + expression + "}";
    }

    private String transformXReqParams(String inputStr) {
        Pattern pattern = Pattern.compile("\\$(\\w+((\\[\\d\\]){0,}\\.\\w+(\\[\\d\\]){0,}){0,})");
        Matcher matcher = pattern.matcher(inputStr);
        while (matcher.find()) {
            String variable = matcher.group(1);
            String newVariable = "${mtgReq.params['" + variable + "']}";
            if (paramIsPersisted(variable)) {
                newVariable = "${" + MTG_PERSIST_MAP + "['" + variable + "']}";
                //newVariable = "${" + MTG_PERSIST_MAP + "." + variable + "}";
            }
            inputStr = inputStr.replace("$" + variable, newVariable);
        }
        return inputStr;
    }

    // '%$variable%' => CONCAT('%',$variable,'%')
    public static String processVariablesInLikeClause(String q) {
        Pattern quotePattern = Pattern.compile("'(.*?)'");
        Matcher quotedSubstringMatcher = quotePattern.matcher(q);
        while (quotedSubstringMatcher.find()) {
            String stringWithinQuotes = quotedSubstringMatcher.group(1);

            Pattern varPattern = Pattern.compile("\\$(\\w+((\\[\\d\\]){0,}\\.\\w+(\\[\\d\\]){0,}){0,})");
            Matcher matcher = varPattern.matcher(stringWithinQuotes);

            StringBuilder builder = new StringBuilder();
            String succedent = stringWithinQuotes;

            builder.append("CONCAT(");
            List<String> args = new ArrayList();
            boolean variableFound = false;
            while (matcher.find()) {
                variableFound = true;
                String variable = matcher.group(1);

                if (!args.isEmpty()) {
                    args.remove(args.size() - 1);
                }
                String precedent = succedent.substring(0, succedent.length() - stringWithinQuotes.length() + matcher.start());
                if (!"".equals(precedent)) {
                    args.add("'" + precedent + "'");
                }
                args.add("$" + variable);
                succedent = succedent.substring(succedent.length() - stringWithinQuotes.length() + matcher.end(), succedent.length());
                if (!"".equals(succedent)) {
                    args.add("'" + succedent + "'");
                }
            }

            builder.append(String.join(",", args));
            builder.append(")");
            if (variableFound) {
                q = q.replace("'" + stringWithinQuotes + "'", builder.toString());
            }
        }
        return q;
    }

    protected String getSqlParams(Sql sql) {
        String query = sql.getValue();
        List<String> params = new ArrayList<>();

        collectSqlParams(params, query);
        String processedQuery = query;
        if (processedQuery.toLowerCase().contains(" like ")) {
            processedQuery = processVariablesInLikeClause(processedQuery);
        }
        String queryWithWildcard = processedQuery.replaceAll("\\$(\\w+((\\[\\d\\]){0,}\\.\\w+(\\[\\d\\]){0,}){0,})", "? ");
        StringBuilder builder = new StringBuilder(queryWithWildcard);
        builder.append("\n");

        appendSqlParams(builder, params);

        return builder.toString();
    }

    protected void collectSqlParams(List<String> params, String query) {
        Pattern pattern = Pattern.compile("\\$(\\w+((\\[\\d\\]){0,}\\.\\w+(\\[\\d\\]){0,}){0,})");
        Matcher match = pattern.matcher(query);
        while (match.find()) {
            params.add(query.substring(match.start(1), match.end(1)).trim());
        }
    }

    protected void appendSqlParams(StringBuilder builder, List<String> params) {
        for (String param : params) {
            switch (param) {
                case "id":
                    builder.append("<sql:param value=\"${mtgReq.id}\"/>");
                    break;
                case "pid":
                    builder.append("<sql:param value=\"${mtgReq.pid}\"/>");
                    break;
                case "uid":
                    builder.append("<sql:param value=\"${mtgReq.uid}\"/>");
                    break;
                default:
                    if (paramIsPersisted(param)) {
                        builder.append(MessageFormat.format("<sql:param value=\"$'{'" + MTG_PERSIST_MAP + "[\''{0}'\']}\" />", param));
                        //builder.append(MessageFormat.format("<sql:param value=\"$'{'" + MTG_PERSIST_MAP + ".{0}}\" />", param));
                    } else {
                        builder.append(MessageFormat.format("<sql:param value=\"$'{'mtgReq.params[\''{0}'\']}\" />", param));
                    }
                    break;
            }
            builder.append("\n");
        }
    }

    protected boolean paramIsPersisted(String paramName) {
        //first segment of mpath param is an element id
        return elementIds.contains(paramName.split("\\.")[0]);
    }

    private String processParam(Param param) {
        StringBuilder builder = new StringBuilder();
        builder.append("<m:param name=\"").append(param.getName()).append("\" ");
        if (param.getType() != null) {
            builder.append("type=\"").append(param.getType()).append("\" ");
        }
        builder.append("value=\"").append("${mtgReq.params['").append(param.getName()).append("']}\" ");
        if (param.getMax() != null) {
            builder.append("max=\"").append(param.getMax()).append("\" ");
        }
        if (param.getMaxlength() != null) {
            builder.append("maxLen=\"").append(param.getMaxlength()).append("\" ");
        }
        if (param.getMin() != null) {
            builder.append("min=\"").append(param.getMin()).append("\" ");
        }
        if (param.getMinlength() != null) {
            builder.append("minLen=\"").append(param.getMinlength()).append("\" ");
        }
        if (param.getPattern() != null) {
            builder.append("pattern=\"").append(param.getPattern()).append("\" ");
        }
        if (param.getExists() != null) {
            builder.append("exists=\"").append(param.getExists()).append("\" ");
        }
        if (param.getValue() != null) {
            builder.append("defaultValue=\"").append(param.getValue()).append("\" ");
        }
        if (param.isRequired()) {
            builder.append("isRequired=\"true\"");
        }
        builder.append("/>");
        return builder.toString();
    }

    public String getQuotedString(String plainString) throws SAXException {
        StringBuilder finalString;
        String quotedString = plainString.replaceAll("\\s+", " ");
        quotedString = quotedString.replaceAll("\\s+\\(", "\\(");
        quotedString = quotedString.replaceAll("\\s+\\)", "\\)");
        quotedString = quotedString.replaceAll("\\s+\\{", "\\{");
        quotedString = quotedString.replaceAll("\\s+\\}", "\\}");
        quotedString = quotedString.replaceAll("\\(\\s+", "\\(");
        quotedString = quotedString.replaceAll("\\)\\s+", "\\)");
        quotedString = quotedString.replaceAll("\\{\\s+", "\\{");
        quotedString = quotedString.replaceAll("\\}\\s+", "\\}");
        String temp = quotedString.trim();
        quotedString = quotedString.replaceAll("\\s+", " ");
        Pattern pattern = Pattern.compile("((\\$|\\'|\\\"){0,1}\\w+((\\[\\d\\]){0,}\\.\\w+(\\[\\d\\]){0,}){0,}(\\'|\\\"){0,1})");
        Matcher matcher = pattern.matcher(temp);
        String[] tokens = temp.split("\\s+");
        String[] operators = {"eq", "le", "ge", "ne", "lt", "gt", "true", "false", "not"};
        String[] logicalOperators = {"and", "or", "empty", "null"};
        List<String> testStringToken = Arrays.asList(tokens);
        List<String> operatorList = Arrays.asList(operators);
        List<String> logicalOperatorList = Arrays.asList(logicalOperators);
        if (!Collections.disjoint(testStringToken, operatorList)) {
            while (matcher.find()) {
                String token = temp.substring(matcher.start(1), matcher.end(1)).trim();
                String tempToken = token;
                if (!token.startsWith("$") && !operatorList.contains(tempToken.toLowerCase()) && !logicalOperatorList.contains(tempToken.toLowerCase())) {
                    if (token.startsWith("\"") || token.startsWith("'")) {
                        if (token.endsWith("\"") || token.endsWith("'")) {
                            if (token.charAt(0) != token.charAt(token.length() - 1)) {
                                token = token.replace(token.charAt(token.length() - 1), token.charAt(0));
                            }
                        } else {
                            token = token.concat(String.valueOf(token.charAt(0)));
                        }
                    } else {
                        if (!token.matches("[0-9]")) {
                            token = "'" + token + "'";
                        }
                    }
                    quotedString = quotedString.replaceFirst(tempToken, token);
                }
            }
            finalString = new StringBuilder(quotedString);
            String before, after = "";
            do {
                before = after;
                finalString = new StringBuilder(enclose(finalString));
                after = finalString.toString();
            } while (!before.equalsIgnoreCase(after));
        } else {
            throw new SAXException("Incorrect conditional operator used in 'when' attribute");
        }
        return finalString.toString();
    }

    private StringBuilder enclose(StringBuilder finalString) {
        Pattern pattern = Pattern.compile("\\$(\\w+((\\[\\d\\]){0,}\\.\\w+(\\[\\d\\]){0,}){0,})");
        Matcher match = pattern.matcher(finalString.toString());
        if (match.find()) {
            int start = match.start(1);
            int end = match.end(1);
            String old = finalString.substring(start, end);
            String modified = "['" + old + "']";
            finalString.replace(start, end, modified);
        }
        return finalString;
    }

    protected boolean isVerbose(Sql sql) {
        if (sql.getType().value().equalsIgnoreCase("update") && (sql.getVerbose() != null && sql.getVerbose())) {
            return true;
        } else {
            return sql.getType().value().equalsIgnoreCase("query") && (sql.getVerbose() == null || sql.getVerbose());
        }
    }
}
