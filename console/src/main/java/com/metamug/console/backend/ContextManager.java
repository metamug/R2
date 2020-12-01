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

import static com.metamug.console.backend.ConfigManager.KEY_EXT_DS;
import com.metamug.console.util.Util;
import static com.metamug.console.util.Util.TEMP_FOLDER;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author anishhirlekar
 */
public class ContextManager {
    
    private final String appName;
    private final String contextFilepath;
    private final JSONObject appConfig;
    
    public ContextManager(String appName, JSONObject config){
        this.appName = appName;
        appConfig = config;
        contextFilepath = TEMP_FOLDER + File.separator + appName + File.separator + "META-INF" + File.separator + "context.xml";
    }
    
    private void setAppDatasource() throws IOException {
        JSONObject dbDetails = appConfig.getJSONObject("dbDetails");
        String type = dbDetails.getString("type");
            
        if(!type.equals(Util.INTERNAL_DB)){
            setExternalAppDataSource();
        } else {
            setInternalAppDatasource();
        }
    }
    
    public void setDatasources() throws IOException {
        setAppDatasource();
        setExternalDatasources();
    }
    
    public void setExternalDatasources() throws IOException{
        if(appConfig.has(KEY_EXT_DS) && appConfig.getJSONArray(KEY_EXT_DS).length()>0) {
            JSONArray externalDatasources = appConfig.getJSONArray(KEY_EXT_DS);
            Document doc = getDocument();
            Node context = doc.getElementsByTagName("Context").item(0);
            
            for(int i=0; i<externalDatasources.length(); i++){
                JSONObject ds = externalDatasources.getJSONObject(i);
                          
                boolean connectionSuccessful = ConfigManager.testConnection(ds);
                if(connectionSuccessful){
                    //add datasource Resource to context xml of webapp
                    
                    //create copy of already existing resource tag
                    Node resource = doc.getElementsByTagName("Resource").item(0).cloneNode(true);

                    String name = ds.getString("name");
                    String dbUrl = ds.getString("url");
                    String type = ds.getString("type");
                    String user = ds.getString("user");
                    String password = ds.getString("password");

                    String url = "";

                    switch (type) {
                        case Util.MYSQL:
                            String options = "?";
                            if(dbUrl.contains("?")){
                                options = "&";
                            }   
                            options += "useOldAliasMetadataBehavior=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&characterSetResults=UTF-8&allowMultiQueries=true&connectTimeout=120000";
                            url = "jdbc:mysql://" + dbUrl + options;
                            break;
                        case Util.POSTGRESQL:
                            url = "jdbc:postgresql://" + dbUrl;
                            break;
                        case Util.MSSQL:
                            url = "jdbc:sqlserver://" +dbUrl;
                            break;
                        default:
                            break;
                    }

                    NamedNodeMap attr = resource.getAttributes();

                    // update Resource attribute-driverClassName
                    Node driverClassNameAttrb = attr.getNamedItem("driverClassName");

                    switch (type) {
                        case Util.MYSQL:
                            driverClassNameAttrb.setTextContent(Util.MYSQL_JDBC_DRIVER);
                            break;
                        case Util.POSTGRESQL:
                            driverClassNameAttrb.setTextContent(Util.POSTGRESQL_JDBC_DRIVER);
                            break;
                        case Util.MSSQL:
                            driverClassNameAttrb.setTextContent(Util.MSSQL_JDBC_DRIVER);
                            break;
                        default:
                            break;
                    }
                    // update Resource attribute-name
                    Node nameAttrb = attr.getNamedItem("name");
                    nameAttrb.setTextContent(name);
                    // update Resource attribute-jdbcUrl
                    Node urlAttrb = attr.getNamedItem("jdbcUrl");
                    urlAttrb.setTextContent(url);
                    // update Resource attribute-username
                    Node usernameAttrb = attr.getNamedItem("username");
                    usernameAttrb.setTextContent(user);
                    // update Resource attribute-password
                    Node passwordAttrb = attr.getNamedItem("password");
                    passwordAttrb.setTextContent(password);
                    // update Resource attribute-poolName
                    Node poolNameAttrb = attr.getNamedItem("poolName");
                    poolNameAttrb.setTextContent(appName + name.substring(0, 1).toUpperCase()+name.substring(1) + "Pool");

                    context.appendChild(resource);   
                }else{
                    Logger.getLogger(ContextManager.class.getName()).log(Level.SEVERE, "Datasource connection test failed for backend: "+ 
                           appName +", datasource: "+ds.toString(4));
                }
            }
            saveDocument(doc);
        }
    }
    
    /**
     * Sets database connection strings
     *
     * @param dataSource Connection URL of database
     * @param appName Name of the application which will connect to the given dataSource
     * @throws javax.xml.transform.TransformerConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    private void setInternalAppDatasource() throws IOException {
        Properties dbProperties = Util.getDbProperties();
        String driver = dbProperties.getProperty("driver").toLowerCase().trim();

        Document doc = getDocument();

        // Set the context path
        Node context = doc.getElementsByTagName("Context").item(0);
        NamedNodeMap contextAttributes = context.getAttributes();
        Node pathAttrb = contextAttributes.getNamedItem("path");
        pathAttrb.setTextContent("/" + appName);
        context.getAttributes().getNamedItem("path").setNodeValue("/" + appName);
        // Initialize Data Source variable
        Node resourceElement = doc.getElementsByTagName("Resource").item(0);
        NamedNodeMap attr = resourceElement.getAttributes();

        // update Resource attribute-driverClassName
        Node driverClassNameAttrb = attr.getNamedItem("driverClassName");
        if (driver.contains("hsqldb")) {
            driverClassNameAttrb.setTextContent("org.hsqldb.jdbc.JDBCDriver");
        }
        if (driver.contains("mysql")) {
            driverClassNameAttrb.setTextContent("com.mysql.cj.jdbc.Driver");
        }
        if (driver.contains("oracle")) {
            driverClassNameAttrb.setTextContent("oracle.jdbc.driver.OracleDriver");
        }
        if (driver.contains("postgresql")) {
            driverClassNameAttrb.setTextContent("org.postgresql.Driver");
        }
        
        JSONObject dbDetails = appConfig.getJSONObject("dbDetails");
        
        // update Resource attribute-jdbcUrl
        Node urlAttrb = attr.getNamedItem("jdbcUrl");
        urlAttrb.setTextContent(dbDetails.getString("url"));

        // update Resource attribute-username
        Node usernameAttrb = attr.getNamedItem("username");
        usernameAttrb.setTextContent(dbDetails.getString("user"));

        // update Resource attribute-password
        Node passwordAttrb = attr.getNamedItem("password");
        passwordAttrb.setTextContent(dbDetails.getString("password"));

        // update Resource attribute-poolName
        Node poolNameAttrb = attr.getNamedItem("poolName");
        poolNameAttrb.setTextContent(appName + "Pool");

        // save the content into xml file
        saveDocument(doc);
    }


    /**
     * Sets database connection properties
     *
     * @param appName Name of the application which will connect to the given dataSource
     * @param dbDetails
     * 
     * @throws javax.xml.transform.TransformerConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    private void setExternalAppDataSource() throws IOException {
        String url = "";

        JSONObject dbDetails = appConfig.getJSONObject("dbDetails");
        String dbType = dbDetails.getString("type");
        String dbUrl = dbDetails.getString("url");
        
        switch (dbType) {
            case Util.MYSQL:
                String options = "?";
                if(dbUrl.contains("?")){
                    options = "&";
                }   
                options += "useOldAliasMetadataBehavior=true&characterEncoding=UTF-8&zeroDateTimeBehavior=CONVERT_TO_NULL&characterSetResults=UTF-8&allowMultiQueries=true&connectTimeout=120000";
                url = "jdbc:mysql://" + dbUrl + options;
                break;
            case Util.POSTGRESQL:
                url = "jdbc:postgresql://" + dbUrl;
                break;
            case Util.MSSQL:
                url = "jdbc:sqlserver://" +dbUrl;
                break;
            default:
                break;
        }
          
        Document doc = getDocument();

        // Set the context path
        Node context = doc.getElementsByTagName("Context").item(0);
        NamedNodeMap contextAttributes = context.getAttributes();
        Node pathAttrb = contextAttributes.getNamedItem("path");
        pathAttrb.setTextContent("/" + appName);
        context.getAttributes().getNamedItem("path").setNodeValue("/" + appName);
        // Initialize Data Source variable
        Node resourceElement = doc.getElementsByTagName("Resource").item(0);
        NamedNodeMap attr = resourceElement.getAttributes();

        // update Resource attribute-driverClassName
        Node driverClassNameAttrb = attr.getNamedItem("driverClassName");

        switch (dbType) {
            case Util.MYSQL:
                driverClassNameAttrb.setTextContent(Util.MYSQL_JDBC_DRIVER);
                break;
            case Util.POSTGRESQL:
                driverClassNameAttrb.setTextContent(Util.POSTGRESQL_JDBC_DRIVER);
                break;
            case Util.MSSQL:
                driverClassNameAttrb.setTextContent(Util.MSSQL_JDBC_DRIVER);
                break;
            default:
                break;
        }
        // update Resource attribute-jdbcUrl
        Node urlAttrb = attr.getNamedItem("jdbcUrl");
        urlAttrb.setTextContent(url);
        // update Resource attribute-username
        Node usernameAttrb = attr.getNamedItem("username");
        usernameAttrb.setTextContent(dbDetails.getString("user"));
        // update Resource attribute-password
        Node passwordAttrb = attr.getNamedItem("password");
        passwordAttrb.setTextContent(dbDetails.getString("password"));
        // update Resource attribute-poolName
        Node poolNameAttrb = attr.getNamedItem("poolName");
        poolNameAttrb.setTextContent(appName + "Pool");
        // save the content into xml file
        saveDocument(doc);
    } 
    
    private Document getDocument() throws IOException {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            return docBuilder.parse(contextFilepath);
        } catch (ParserConfigurationException | SAXException ex) {
            Logger.getLogger(ContextManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void saveDocument(Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(contextFilepath));
            transformer.transform(source, result);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(ContextManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(ContextManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}