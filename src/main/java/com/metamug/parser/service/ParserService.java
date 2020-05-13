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

import com.metamug.parser.ResourceParser;
import com.metamug.parser.exception.ResourceTestException;
import com.metamug.parser.schema.Request;
import com.metamug.parser.schema.InvocableElement;
import com.metamug.parser.schema.Resource;
import com.metamug.parser.schema.Upload;
import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import org.apache.commons.io.FilenameUtils;
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

    public HashMap<String,InvocableElement> elementIds = new HashMap<String,InvocableElement>() {
        {
            put(UPLOAD_OBJECT, new Upload());
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
        ResourceParser parser = new ResourceParser(OUTPUT_FOLDER, appName, uploadedFile);
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

                    List elements = req.getInvocableElements();

                    printRequestElements(elements, writer);

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

    protected void printRequestElements(List elements, XMLStreamWriter writer) throws XMLStreamException, IOException, XPathExpressionException, SAXException, ResourceTestException {
        for (Object child : elements) {
            ((InvocableElement)child).print(writer, this);
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
        writer.writeAttribute("item", String.valueOf(req.getItem()) );
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

}