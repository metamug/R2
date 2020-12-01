/** ***********************************************************************
 *Freeware License Agreement
 *
 *This license agreement only applies to the free version of this software.
 *
 *Terms and Conditions
 *
 *BY DOWNLOADING, INSTALLING, USING, TRANSMITTING, DISTRIBUTING OR COPYING THIS SOFTWARE ("THE SOFTWARE"), YOU AGREE TO THE TERMS OF THIS AGREEMENT (INCLUDING THE SOFTWARE LICENSE AND DISCLAIMER OF WARRANTY) WITH METAMUG THE OWNER OF ALL RIGHTS IN RESPECT OF THE SOFTWARE.
 *
 *PLEASE READ THIS DOCUMENT CAREFULLY BEFORE USING THE SOFTWARE.
 *
 *IF YOU DO NOT AGREE TO ANY OF THE TERMS OF THIS LICENSE THEN DO NOT DOWNLOAD, INSTALL, USE, TRANSMIT, DISTRIBUTE OR COPY THE SOFTWARE.
 *
 *THIS DOCUMENT CONSTITUTES A LICENSE TO USE THE SOFTWARE ON THE TERMS AND CONDITIONS APPEARING BELOW.
 *
 *The Software is licensed to you without charge for use only upon the terms of this license, and METAMUG TECHNOLOGIES LLP (hereafter METAMUG) reserves all rights not expressly granted to you. METAMUG retains ownership of all copies of the Software.
 *
 *1. License
 *
 *You may use the Software without charge.
 *
 *You may freely distribute exact copies of the Software to anyone.
 *
 *The inclusion of the Software in any Shareware, Freeware or similar media compilation or distribution method whereby it is made available at cost (ie. sold) is strictly prohibited.
 *
 *The selling of the Software is strictly prohibited.
 *2. Restrictions
 *
 *METAMUG reserves the right to revoke the above distribution right at any time, for any or no reason.
 *
 *YOU MAY NOT MODIFY, ADAPT, TRANSLATE, RENT, LEASE, LOAN, SELL, ONSELL, REQUEST DONATIONS OR CREATE DERIVATIVE WORKS BASED UPON THE SOFTWARE OR ANY PART THEREOF.
 *
 *The Software contains intellectual property and to protect them you may not decompile, reverse engineer, disassemble or otherwise reduce the Software to a humanly perceivable form. You agree not to divulge, directly or indirectly, until such intellectual property cease to be confidential, for any reason not your own fault.
 *
 *3. Termination
 *
 *This license is effective until terminated. The License will terminate automatically without notice from METAMUG if you fail to comply with any provision of this License. Upon termination you must destroy the Software and all copies thereof. You may terminate this License at any time by destroying the Software and all copies thereof. Upon termination of this license for any reason you shall continue to be bound by the provisions of Section 2 above. Termination will be without prejudice to any rights METAMUG may have as a result of this agreement.
 *
 *4. Disclaimer of Warranty, Limitation of Remedies
 *
 *TO THE FULL EXTENT PERMITTED BY LAW, METAMUG HEREBY EXCLUDES ALL CONDITIONS AND WARRANTIES, WHETHER IMPOSED BY STATUTE OR BY OPERATION OF LAW OR OTHERWISE, NOT EXPRESSLY SET OUT HEREIN. THE SOFTWARE, AND ALL ACCOMPANYING FILES, DATA AND MATERIALS ARE DISTRIBUTED "AS IS" AND WITH NO WARRANTIES OF ANY KIND, WHETHER EXPRESS OR IMPLIED. METAMUG DOES NOT WARRANT, GUARANTEE OR MAKE ANY REPRESENTATIONS REGARDING THE USE, OR THE RESULTS OF THE USE, OF THE SOFTWARE WITH RESPECT TO ITS CORRECTNESS, ACCURACY, RELIABILITY, CURRENTNESS OR OTHERWISE. THE ENTIRE RISK OF USING THE SOFTWARE IS ASSUMED BY YOU. METAMUG MAKES NO EXPRESS OR IMPLIED WARRANTIES OR CONDITIONS INCLUDING, WITHOUT LIMITATION, THE WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE WITH RESPECT TO THE SOFTWARE. NO ORAL OR WRITTEN INFORMATION OR ADVICE GIVEN BY METAMUG, IT'S DISTRIBUTORS, AGENTS OR EMPLOYEES SHALL CREATE A WARRANTY, AND YOU MAY NOT RELY ON ANY SUCH INFORMATION OR ADVICE.
 *
 *IMPORTANT NOTE: Nothing in this Agreement is intended or shall be construed as excluding or modifying any statutory rights, warranties or conditions which by virtue of any national or state Fair Trading, Trade Practices or other such consumer legislation may not be modified or excluded. If permitted by such legislation, however, METAMUG's liability for any breach of any such warranty or condition shall be and is hereby limited to the supply of the Software licensed hereunder again as METAMUG at its sole discretion may determine to be necessary to correct the said breach.
 *
 *IN NO EVENT SHALL METAMUG BE LIABLE FOR ANY SPECIAL, INCIDENTAL, INDIRECT OR CONSEQUENTIAL DAMAGES (INCLUDING, WITHOUT LIMITATION, DAMAGES FOR LOSS OF BUSINESS PROFITS, BUSINESS INTERRUPTION, AND THE LOSS OF BUSINESS INFORMATION OR COMPUTER PROGRAMS), EVEN IF METAMUG OR ANY METAMUG REPRESENTATIVE HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. IN ADDITION, IN NO EVENT DOES METAMUG AUTHORIZE YOU TO USE THE SOFTWARE IN SITUATIONS WHERE FAILURE OF THE SOFTWARE TO PERFORM CAN REASONABLY BE EXPECTED TO RESULT IN A PHYSICAL INJURY, OR IN LOSS OF LIFE. ANY SUCH USE BY YOU IS ENTIRELY AT YOUR OWN RISK, AND YOU AGREE TO HOLD METAMUG HARMLESS FROM ANY CLAIMS OR LOSSES RELATING TO SUCH UNAUTHORIZED USE.
 *
 *5. General
 *
 *All rights of any kind in the Software which are not expressly granted in this Agreement are entirely and exclusively reserved to and by METAMUG.
 *
 *This Agreement shall be governed by the laws of the State of Maharashtra, India. Exclusive jurisdiction and venue for all matters relating to this Agreement shall be in courts and fora located in the State of Maharashtra, India, and you consent to such jurisdiction and venue. This agreement contains the entire Agreement between the parties hereto with respect to the subject matter hereof, and supersedes all prior agreements and/or understandings (oral or written). Failure or delay by METAMUG in enforcing any right or provision hereof shall not be deemed a waiver of such provision or right with respect to the instant or any subsequent breach. If any provision of this Agreement shall be held by a court of competent jurisdiction to be contrary to law, that provision will be enforced to the maximum extent permissible, and the remaining provisions of this Agreement will remain in force and effect.
 */
package com.metamug.console.backend;

import com.metamug.console.exception.MetamugException;
import com.metamug.console.util.FileUtil;
import com.metamug.console.util.Util;
import static com.metamug.console.util.Util.EXPORT_FOLDER;
import static com.metamug.console.util.Util.LIB_FOLDER;
import static com.metamug.console.util.Util.TEMP_FOLDER;
import com.metamug.parser.ExportParserService;
import com.metamug.parser.exception.ResourceTestException;
import static com.metamug.parser.schema.InvocableElement.BACKEND_PROP_PATTERN;
import static com.metamug.parser.schema.InvocableElement.MPATH_EXPRESSION_PATTERN;
import static com.metamug.parser.schema.InvocableElement.REQUEST_PARAM_PATTERN;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author anishhirlekar
 */
public class Exporter extends Loader {
    
    public static final String ANALYTIC_FILTER = "AnalyticFilter";
    
    private final String[] removeLibs = {
        "query-api-1.0.jar"
    };
    
    public Exporter(String appName) throws FileNotFoundException {
        super(appName);
    }
    
    public String export(String domain) throws MetamugException, ParserConfigurationException, SAXException, IOException, TransformerException, ResourceTestException{
        createWebapp();
        //set only datasources in context.xml
        ContextManager cx = new ContextManager(appName,appConfig);
        cx.setExternalDatasources();
        //update web xml of app - remove console backend hooks
        updateWebXml();
        //create jsp files with decoupled queries
        generateResources(domain);
        //remove console hook libs and add runtime libs
        updateLibs();
        
        return generateWar();
    }
    
    /**
     * Remove analytics filter from web.xml of webapp
     *
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
    private void updateWebXml() throws ParserConfigurationException, IOException, SAXException, TransformerConfigurationException, TransformerException {
        String webXml = TEMP_FOLDER + File.separator + appName + File.separator + "WEB-INF" + File.separator + "web.xml";
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(webXml);
        //get list of all <filter>
        NodeList filters = doc.getElementsByTagName("filter");
        for (int i = 0; i < filters.getLength(); i++) {
            Node filter = filters.item(i);
            for (int j = 0; j < filter.getChildNodes().getLength(); j++) {
                Node filterChildNode = filter.getChildNodes().item(j);

                if (filterChildNode.getNodeName().equals("filter-name")) {
                    if (filterChildNode.getTextContent().trim().equals(ANALYTIC_FILTER)) {
                        filter.getParentNode().removeChild(filter);
                        break;
                    }
                }
            }
        }
        //get list of all <filter-mapping>
        NodeList filterMappings = doc.getElementsByTagName("filter-mapping");
        for (int i = 0; i < filterMappings.getLength(); i++) {
            Node filterMapping = filterMappings.item(i);
            for (int j = 0; j < filterMapping.getChildNodes().getLength(); j++) {
                Node filterChildNode = filterMapping.getChildNodes().item(j);

                if (filterChildNode.getNodeName().equals("filter-name")) {
                    if (filterChildNode.getTextContent().trim().equals(ANALYTIC_FILTER)) {
                        filterMapping.getParentNode().removeChild(filterMapping);
                        break;
                    }
                }
            }
        }
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(webXml));
        transformer.transform(source, result);
    }
    
    @Override
    protected void unzipAppTemplate() throws MetamugException {
        try {
            FileUtil.unzip(Exporter.class.getClassLoader().getResource("app-template-v0.1.zip").toURI().getPath(),
                        TEMP_FOLDER + File.separator + appName);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Exporter.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    /**
     * Create zip from webapp directory and named as .war
     *
     * @param appName Name of the application
     * @return Generated war file path
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws net.lingala.zip4j.exception.ZipException
     */
    private String generateWar() throws IOException {
        try {
            String warFilePath = EXPORT_FOLDER + File.separator + appName + ".war";
            //delete existing war file
            Files.deleteIfExists(Paths.get(warFilePath));
            
            String webappDir = TEMP_FOLDER + File.separator + appName;
            ZipFile zipFile = new ZipFile(warFilePath);
            ZipParameters parameters = new ZipParameters();
            parameters.setIncludeRootFolder(false);
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            zipFile.addFolder(webappDir, parameters);
            
            return warFilePath;
        } catch (ZipException ex) {
            Logger.getLogger(Exporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private JSONObject getQueryMap(String domain) throws IOException, ResourceTestException {
        String url = domain + "/" + appName;
        String params = "query=querymap";

        URL obj = new URL(url + "/query?" + params);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Authorization", Util.getMasonApiRequestSignature(appName));
        if (con.getResponseCode() != 200) {
            throw new ResourceTestException("Server error. Could not export queries!");
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            StringBuilder responseBuffer = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                responseBuffer.append(inputLine);
            }
            return new JSONObject(responseBuffer.toString());
        }
    }
    
    /**
     * Write the query_name -> query mappings inside query.properties file in the webapp
     *
     * @param appDir Webapp path
     */
    private void saveQueryMap(JSONObject queryMap) throws IOException {
        File propsFile = new File(TEMP_FOLDER + File.separator + appName + File.separator + "WEB-INF" + File.separator
                + "classes" + File.separator + "query.properties");
        if (!propsFile.exists()) {
            propsFile.createNewFile();
        }
        Properties p = new Properties();
        JSONArray queries = queryMap.getJSONArray(ExportParserService.KEY_QUERIES);
        for (int i = 0; i < queries.length(); i++) {
            JSONObject queryObject = queries.getJSONObject(i);
            String query = queryObject.getString("query");
            /*if (query.toLowerCase().contains(" like ")) {
                query = ParserServiceUtil.processVariablesInLikeClause(query);
            }*/
            query = query.replaceAll(REQUEST_PARAM_PATTERN, "? ");
            query = query.replaceAll(MPATH_EXPRESSION_PATTERN, "? ");
            query = query.replaceAll(BACKEND_PROP_PATTERN, "? ");
            
            p.setProperty(queryObject.getString("query_name"), query);
        }
        p.store(new FileOutputStream(propsFile), null);
    }
    
    /**
     * Transform XML resources to jsp files for exported app
     *
     * @param appDir Webapp path
     */
    private void generateResources(String domain) throws ResourceTestException, IOException {
        //get query catalog mapping for app from db
        JSONObject queryMap = getQueryMap(domain);
        //write query mapping to props file
        saveQueryMap(queryMap);
        
        File appResourceFolder = new File(Util.XML_RESOURCE_FOLDER + File.separator + appName);
        File[] versionFolders = appResourceFolder.listFiles();
        for (File vFolder : versionFolders) {
            if (vFolder.isDirectory()) {
                File[] resourceFiles = vFolder.listFiles();
                for (File resourceFile : resourceFiles) {
                    String resourceFilePath = resourceFile.getPath();
                    String ext = FilenameUtils.getExtension(resourceFilePath);
                    if (ext.equals("xml")) {
                        ExportParserService parseService = new ExportParserService();
                        try {
                            //System.out.println(queryMap);
                            parseService.transform(resourceFile, appName, true, Util.TEMP_FOLDER, null, queryMap);
                        } catch (SAXException | XMLStreamException | XPathExpressionException | ParserConfigurationException | TransformerException | JAXBException | URISyntaxException | IOException | SQLException | ClassNotFoundException | PropertyVetoException ex) {
                            Logger.getLogger(Exporter.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Copy libraries from tomcat libs folder to webapp lib folder
     *
     * @param appDir Webapp path
     */
    private void addRuntimeLibs() {
        getExportLibs().forEach( lib -> {
            Path srcFile = Paths.get(LIB_FOLDER + File.separator + lib);
            Path destFile = Paths.get(TEMP_FOLDER + File.separator + appName + File.separator + "WEB-INF"
                    + File.separator + "lib" + File.separator + lib);
            try {
                Files.copy(srcFile, destFile, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                //Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        });
    }  
    
    private void updateLibs(){
        removeHookLibs();
        addRuntimeLibs();
    }
    
    /**
     * Remove Metamug Console hooks from webapp libs folder
     *
     * @param appDir Webapp path
     */
    private void removeHookLibs() {
        for (String lib : removeLibs) {
            Path libPath = Paths.get(TEMP_FOLDER + File.separator + appName + File.separator + "WEB-INF"
                    + File.separator + "lib" + File.separator + lib);
            try {
                Files.delete(libPath);
            } catch (IOException ex) {
                //Logger.getLogger(ExportService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private List<String> getExportLibs(){
        try {
            File file = new File(getClass().getResource("/exportLib.txt").getFile());
            List<String> exportLibs = new ArrayList<>();
            try (Scanner myReader = new Scanner(file)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    //System.out.println(data);
                    exportLibs.add(data);
                }
            }
            return exportLibs;
        } catch (FileNotFoundException e) {
            //Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
