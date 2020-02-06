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
import com.metamug.parser.schema.Arg;
import com.metamug.parser.schema.Execute;
import com.metamug.parser.schema.Text;
import com.metamug.parser.schema.Param;
import com.metamug.parser.schema.Request;
import com.metamug.parser.schema.Resource;
import com.metamug.parser.schema.Script;
import com.metamug.parser.schema.Sql;
import com.metamug.parser.schema.Transaction;
import com.metamug.parser.schema.Xheader;
import com.metamug.parser.schema.Xparam;
import com.metamug.parser.schema.Xrequest;
import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;
import org.xml.sax.SAXException;

/**
 *
 * @author Kainix
 */
public class ParserService {

    public static final String MASON_DATASOURCE = "datasource";
    public static final String MASON_OUTPUT = "output";
    
    public static final String UPLOAD_OBJECT = "_upload";

    public String appName;
    public String resourceName;
    public double resourceVersion;
    protected JSONObject queryMap;

    protected String OUTPUT_FOLDER;
    public OutputStream output;
    XMLOutputFactory factory = XMLOutputFactory.newInstance();

    public HashMap<String,String> elementIds = new HashMap<String,String>() {
        {
            put(UPLOAD_OBJECT, UPLOAD_OBJECT);
        }
    }; // <id,elementType>  
    public String domain;
                                             
    public static final String REQUEST_PARAM_PATTERN = "\\$(\\w+((\\[\\d\\]){0,}\\.\\w+(\\[\\d\\]){0,}){0,})";
    public static final String MPATH_EXPRESSION_PATTERN = "\\$\\[(\\w+?)\\](\\[\\d+\\]){0,1}(\\.\\w+(\\[\\d+\\]){0,1}){0,}";

    public JSONObject transform(File uploadedFile, String appName, boolean updateResource, String outputFolder,
            String domain, JSONObject queryMap) throws SAXException, XMLStreamException,
            XPathExpressionException, ParserConfigurationException, TransformerException, JAXBException,
            URISyntaxException, IOException, SQLException, ClassNotFoundException, PropertyVetoException, ResourceTestException {
        this.domain = domain;
        this.appName = appName;
        this.resourceName = FilenameUtils.removeExtension(uploadedFile.getName());
        this.queryMap = queryMap;

        OUTPUT_FOLDER = outputFolder;

        RPXParser parser = new RPXParser(OUTPUT_FOLDER, appName, uploadedFile);
        Resource parsedResource = parser.parse();

        this.resourceVersion = parsedResource.getVersion();

        //make test queries requests
        if (null != domain) {
            ResourceTestService testService = new ResourceTestService();
            testService.testResource(parsedResource, domain, appName);
        }

        Resource resource = createJsp(parsedResource, uploadedFile, updateResource, domain);

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

    public Resource createJsp(Resource resource, File resourceFile, boolean updateResource, String domain)
            throws JAXBException, SAXException, IOException, XPathExpressionException,
            TransformerException, URISyntaxException, XMLStreamException, ResourceTestException {

        String resourceDir = OUTPUT_FOLDER + File.separator + appName + File.separator
                + "WEB-INF" + File.separator + "resources" + File.separator;

        if (!new File(resourceDir + "v" + resource.getVersion()).exists()) {
            Files.createDirectories(Paths.get(resourceDir + "v" + resource.getVersion()));
        }

        String jsp = resourceDir + "v" + resource.getVersion() + File.separator 
                + FilenameUtils.removeExtension(resourceFile.getName()) + ".jsp";
        if (!new File(jsp).exists() || updateResource) {
            try{
                output = new FileOutputStream(jsp);
                XMLStreamWriter writer = new IndentingXMLStreamWriter(factory.createXMLStreamWriter(output));

                printHeaderAndGroup(writer, resource);

                for (Request req : resource.getRequest()) {
                    writer.writeStartElement("m:request");
                    initializeRequest(writer, req);

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
                //writeUnescapedCharacters(resource, appName, resourceFile);

                return resource;
            }catch(ResourceTestException | IOException | XMLStreamException | XPathExpressionException | SAXException
                    | NullPointerException e){
                if( (!updateResource) && (new File(jsp).exists()) ) {
                    new File(jsp).delete();
                }
                
                throw e;
            }
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
            } else if (object instanceof Xheader) {
                Xheader header = (Xheader)object;
                printHeaderTag(header, writer);
            } else if (object instanceof Sql) {
                Sql sql = (Sql) object;
                elementIds.put(sql.getId(), Sql.class.getName());

                if (sql.getOnerror() != null && sql.getOnerror().length() > 0) {
                    startValidateTag(writer, sql.getOnerror());
                }

                preProcessSqlElement(sql, domain);               

                printSqlTag(sql, writer, true);

                if (sql.getOnerror() != null && sql.getOnerror().length() > 0) {
                    closeValidateTag(writer);
                }
            } else if (object instanceof Execute) {
                Execute execute = (Execute) object;
                elementIds.put(execute.getId(), Execute.class.getName());
                printExecuteTag(execute, writer);
            } else if (object instanceof Xrequest) {
                Xrequest xr = (Xrequest) object;
                elementIds.put(xr.getId(), Xrequest.class.getName());
                printXrequestTag(xr, writer);
            } else if(object instanceof Script){
                Script sc = (Script)object;
                elementIds.put(sc.getId(), Script.class.getName());
                printScriptTag(sc, writer);
            } else if(object instanceof Transaction){
                printTransaction((Transaction)object,writer,domain);
            } else if(object instanceof Text){
                Text txt = (Text)object;
                elementIds.put(txt.getId(), Text.class.getName());
                printTextTag(txt,writer);
            }
        }
    }
    
    
    protected void printTextTag(Text txt, XMLStreamWriter writer) throws XMLStreamException, ResourceTestException, IOException{
        if (txt.getWhen() != null) {
            writer.writeStartElement("c:if");
            String test = transformVariables(txt.getWhen(),elementIds,false);
            writeUnescapedData(" test=\""+enclose(StringEscapeUtils.unescapeXml(test))+"\"");
        }
        writer.writeCharacters(System.lineSeparator());
        
        String value = transformVariables(txt.getValue(),elementIds,true).trim();
        
        printPageScopeCSet(writer, txt.getId(), value);
        
        if(txt.isOutput()){
            printTargetCSet(writer, enclose(MASON_OUTPUT), txt.getId(), enclose(txt.getId()) );
        }
        
        if (txt.getWhen() != null) {
            writer.writeEndElement();
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
        writer.writeAttribute("item", String.valueOf(req.isItem()) );
    }
    
    protected void printTargetCSet(XMLStreamWriter writer, String target, String property, String value) throws XMLStreamException{
        writer.writeEmptyElement("c:set");
        writer.writeAttribute("target", target);
        writer.writeAttribute("property", property);
        writer.writeAttribute("value", value);
    }
    
    protected void printPageScopeCSet(XMLStreamWriter writer, String var, String value) throws XMLStreamException{
        printScopeCSet(writer,"page",var,value);
    }
    
    protected void printScopeCSet(XMLStreamWriter writer, String scope, String var, String value) throws XMLStreamException{
        writer.writeEmptyElement("c:set");
        writer.writeAttribute("var", var);
        writer.writeAttribute("scope", scope);    
        writer.writeAttribute("value", value);
    }

    /**
     * Prints mtg:execute tag to call Java code for code execution.
     *
     * @param execute Execute object which is to be converted
     * @param writer XMLStreamWriter to write to JSP file.
     * @throws XMLStreamException
     * @throws SAXException
     */
    protected void printExecuteTag(Execute execute, XMLStreamWriter writer) throws XMLStreamException, SAXException, ResourceTestException, IOException {
        if (execute.getWhen() != null) {
            writer.writeStartElement("c:if");
            //String testString = getQuotedString(execute.getWhen());
            //writer.writeAttribute("test", enclose(testString.replace("$", "mtgReq.params")));
            String test = transformVariables(execute.getWhen(),elementIds,false);
            writeUnescapedData(" test=\""+enclose(StringEscapeUtils.unescapeXml(test))+"\"");
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
        writer.writeStartElement("m:execute");
        writer.writeAttribute("var", execute.getId());
        writer.writeAttribute("className", execute.getClassName());
        writer.writeAttribute("param", enclose("mtgReq"));
        if ( (execute.getVerbose() != null && execute.getVerbose()) 
                || execute.getOutput() != null && execute.getOutput() ) {
            writer.writeAttribute("output", "true");
        }
        if (execute.getOnerror() != null && execute.getOnerror().length() > 0) {
            writer.writeAttribute("onError", execute.getOnerror());
        }
        
        for(Arg arg: execute.getArg()){
            writer.writeEmptyElement("m:arg");
            writer.writeAttribute("name", arg.getName());
            if(arg.getValue()!=null){
                writer.writeAttribute("value", transformVariables(arg.getValue(),elementIds,true) );
            }else{
                //value is null, check path
                if(arg.getPath()!=null){
                    writer.writeAttribute("value", transformVariables(arg.getPath(),elementIds,true) );
                } else{
                    writer.writeAttribute("value","null");
                }
            }
        }
        
        writer.writeCharacters(System.lineSeparator());
        writer.writeEndElement(); // </m:execute>
       
        writer.writeCharacters(System.lineSeparator());

        if (execute.getWhen() != null) {
            writer.writeEndElement(); //End of <c:if>
        }
    }
    
    
    protected void printTransaction(Transaction tx, XMLStreamWriter writer, String domain) throws XMLStreamException, 
            SAXException, IOException, XPathExpressionException, ResourceTestException{
        if (tx.getWhen() != null) {
            writer.writeStartElement("c:if");
            //String testString = getQuotedString(tx.getWhen());
            //writer.writeAttribute("test", enclose(testString.replace("$", "mtgReq.params")));
            String test = transformVariables(tx.getWhen(),elementIds,false);
            writeUnescapedData(" test=\""+enclose(StringEscapeUtils.unescapeXml(test))+"\"");
        }
        writer.writeCharacters(System.lineSeparator());
        writer.writeStartElement("sql:transaction");
        writer.writeAttribute("dataSource", enclose(MASON_DATASOURCE));
        
        List<Sql> sqlList = tx.getSql();
        for(Sql sql: sqlList){
            elementIds.put(sql.getId(), Sql.class.getName());

            if (sql.getOnerror() != null && sql.getOnerror().length() > 0) {
                startValidateTag(writer, sql.getOnerror());
            }

            preProcessSqlElement(sql, domain);               

            printSqlTag(sql, writer, false);

            if (sql.getOnerror() != null && sql.getOnerror().length() > 0) {
                closeValidateTag(writer);
            }
        }
        
        writer.writeEndElement(); //End of <sql:transaction> 
        if (tx.getWhen() != null) {
            writer.writeEndElement(); //End of <c:if>
        }
    }
    
    protected void printScriptTag(Script script,XMLStreamWriter writer) throws XMLStreamException, SAXException, ResourceTestException, IOException{
        if (script.getWhen() != null) {
            writer.writeStartElement("c:if");
            //String testString = getQuotedString(script.getWhen());
            //writer.writeAttribute("test", enclose(testString.replace("$", "mtgReq.params")));
            String test = transformVariables(script.getWhen(),elementIds,false);
            writeUnescapedData(" test=\""+enclose(StringEscapeUtils.unescapeXml(test))+"\"");
        }
        writer.writeCharacters(System.lineSeparator());
        writer.writeStartElement("m:script");
        String var = script.getId();
        writer.writeAttribute("var", var);
        writer.writeAttribute("file", script.getFile());
        
        writer.writeEndElement(); //End of <m:script>    
        writer.writeCharacters(System.lineSeparator());
        
        if (script.getOutput()) {
            printTargetCSet(writer,enclose(MASON_OUTPUT),var,enclose(var)); 
        }
        
        if (script.getWhen() != null) {
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
     * @throws java.io.IOException
     * @throws javax.xml.xpath.XPathExpressionException
     * @throws ResourceTestException
     */
    protected void printXrequestTag(Xrequest xrequest, XMLStreamWriter writer)
            throws XMLStreamException, SAXException, IOException, XPathExpressionException, ResourceTestException {
        if (xrequest.getWhen() != null) {
            writer.writeStartElement("c:if");
            //String testString = getQuotedString(xrequest.getWhen());
            //writer.writeAttribute("test", enclose(testString.replace("$", "mtgReq.params")));
            String test = transformVariables(xrequest.getWhen(),elementIds,false);
            writeUnescapedData(" test=\""+enclose(StringEscapeUtils.unescapeXml(test))+"\"");
        }
   
        //print xrequest mason tags
        writer.writeCharacters(System.lineSeparator());
        writer.writeStartElement("m:xrequest");
        writer.writeAttribute("var", xrequest.getId());
        writer.writeAttribute("method", xrequest.getMethod().name());
        if ( xrequest.getOutput() != null ) { 
            String outputVal = xrequest.getOutput().value();
            if(outputVal.equals("headers")){
                writer.writeAttribute("outputHeaders", "true");
                writer.writeAttribute("output", "true");
            } else if(outputVal.equals("true")) {
                writer.writeAttribute("output", "true");
            }
        }
        
        if ( xrequest.getClassName() != null ) {
            writer.writeAttribute("className", xrequest.getClassName());
        }
        
        writeUnescapedData(" url=\""+StringEscapeUtils.unescapeXml(xrequest.getUrl())+"\"");
                
        for (Object paramOrHeaderOrBody : xrequest.getParamOrHeaderOrBody()) {
            if (paramOrHeaderOrBody instanceof Xheader) {
                writer.writeEmptyElement("m:header");
                writer.writeAttribute("name", ((Xheader) paramOrHeaderOrBody).getName());
                
                String value = ((Xheader) paramOrHeaderOrBody).getValue();
                writeUnescapedData(" value=\""+StringEscapeUtils.unescapeXml(value)+"\"");
            } else if (paramOrHeaderOrBody instanceof Xparam) {
                writer.writeEmptyElement("m:xparam");
                writer.writeAttribute("name", ((Xparam) paramOrHeaderOrBody).getName());
                //transform request parameters and mpath variables in xrequest param value
                String v = transformVariables(((Xparam) paramOrHeaderOrBody).getValue(),elementIds,true);
                writeUnescapedData(" value=\""+StringEscapeUtils.unescapeXml(v)+"\"");
            } else if (paramOrHeaderOrBody instanceof String) {
                writer.writeStartElement("m:xbody");
                //transform request parameters and mpath variables in xrequest body
                String body = transformVariables((String) paramOrHeaderOrBody,elementIds,true);
              
                writeUnescapedCharacters(writer, body);
                writer.writeEndElement();
            }
        }
        writer.writeEndElement(); //End of <m:xrequest>    
        writer.writeCharacters(System.lineSeparator());
        
        if (xrequest.getWhen() != null) {
            writer.writeEndElement(); //End of <c:if>
        }
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
     * @param writer
     * @param data
     * @throws javax.xml.stream.XMLStreamException
     * @throws IOException
     * @throws javax.xml.xpath.XPathExpressionException
     */
    protected void writeUnescapedCharacters(XMLStreamWriter writer, String data) throws XMLStreamException, IOException, XPathExpressionException {
        writer.writeCharacters("");
        writer.flush();
        OutputStreamWriter osw = new OutputStreamWriter(output);
        osw.write(data);
        osw.flush();
    }
    
    protected void writeUnescapedData(String data) throws IOException{
        OutputStreamWriter osw = new OutputStreamWriter(output);
        osw.write(data);
        osw.flush();
    }

    protected String enclose(String expression) {
        return "${" + expression + "}";
    }
    
    protected void collectVariables(LinkedList<String> requestParams, LinkedList<String> mPathParams, String query) throws ResourceTestException {
        Pattern pattern = Pattern.compile(REQUEST_PARAM_PATTERN);
        Matcher match = pattern.matcher(query);
        while (match.find()) {
            requestParams.add(query.substring(match.start(1), match.end(1)).trim());
        }
        //collect Mpath variables
        ParserServiceUtil.collectMPathParams(mPathParams,query, elementIds);
    }
    
    
    protected String transformVariables(String input, Map<String,String> elementIds, boolean enclose) throws ResourceTestException{
        input = ParserServiceUtil.transformRequestVariables(input,enclose);
        input = ParserServiceUtil.transformMPathVariables(input, elementIds, enclose);
        return input;
    }
   
    
    protected String getJspVariableForRequestParam(String param){
        switch (param) {
            case "id":
                return "${mtgReq.id}";
            case "pid":
                return "${mtgReq.pid}";
            case "uid":
                return "${mtgReq.uid}";
            default:
                return "${mtgReq.params['" +param+ "']}";
        }
    }

    
/*
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
        //System.out.println("q1: "+quotedString);
        
        
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
        //System.out.println("q2: "+finalString.toString());
        //return finalString.toString();
        
        return null;
    }

    private StringBuilder enclose(StringBuilder finalString) {
        Pattern pattern = Pattern.compile(REQUEST_PARAM_PATTERN);
        Matcher match = pattern.matcher(finalString.toString());
        if (match.find()) {
            int start = match.start(1);
            int end = match.end(1);
            String old = finalString.substring(start, end);
            String modified = "['" + old + "']";
            finalString.replace(start, end, modified);
        }
        
        System.out.println("q3: "+finalString.toString());
        return finalString;
    }*/
/*
    protected boolean isVerbose(Sql sql) {
        if (sql.getType().value().equalsIgnoreCase("update") &&
               ( (sql.getVerbose() != null && sql.getVerbose()) || (sql.getOutput() != null && sql.getOutput()) ) ) {
            // type = update and verbose = true
            return true;
        } else {
            // type = query and verbose != false
            return sql.getType().value().equalsIgnoreCase("query") && 
               ( ( sql.getVerbose() == null || sql.getVerbose() || sql.getOutput() == null || sql.getOutput() ) );
        }
    }*/
    
    
   


}