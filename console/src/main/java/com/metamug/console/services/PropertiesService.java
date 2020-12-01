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
package com.metamug.console.services;

import com.metamug.console.backend.ConfigManager;
import static com.metamug.console.util.Util.OUTPUT_FOLDER;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author anishhirlekar
 */
public class PropertiesService {
    
    public static final String CORS_FILTER = "CORSFilter";
    public static final String ROUTER = "Router";
    public static final String ALLOWED_ORIGINS = "allowedOrigins";

    private Document getDocument(String webXmlPath) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        return docBuilder.parse(webXmlPath);
    }

    private void writeXml(Document doc, String webXmlPath) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(webXmlPath));
        transformer.transform(source, result);
    }

    public Node getFilterNode(Document doc, String filterName) {
        NodeList filters = doc.getElementsByTagName("filter");

        for (int i = 0; i < filters.getLength(); i++) {
            Node filter = filters.item(i);
            Node filterNameNode = ((Element) filter).getElementsByTagName("filter-name").item(0);
            String fname = filterNameNode.getTextContent().trim();
            if (filterName.equals(fname)) {
                return filter;
            }
        }
        return null;
    }
    
    public void addProperty(String appName, String key, String value, String appFolder) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        //add in backend config
        ConfigManager c = new ConfigManager(appName);
        c.addProperty(key, value);
        
        //add in web xml
        if(key.equals(ALLOWED_ORIGINS)){
            updateCors(appName,value,appFolder);
            return;
        }
        
        String webXmlPath = appFolder + File.separator + appName + File.separator + "WEB-INF" + File.separator + "web.xml";
        Document doc = getDocument(webXmlPath);

        Node router = getFilterNode(doc, ROUTER);
        
        Element initParam = doc.createElement("init-param");
        
        Element paramName = doc.createElement("param-name");
        paramName.setTextContent(key);
        initParam.appendChild(paramName);
        
        Element paramValue = doc.createElement("param-value");
        paramValue.setTextContent(value);
        initParam.appendChild(paramValue);
        
        router.appendChild(initParam);
        
        writeXml(doc, webXmlPath);
    }
    
    public Map<String,String> getCors(String appName) throws ParserConfigurationException, SAXException, IOException {
        Map<String,String> map = new HashMap<>();
        map.put("allowedOrigins", "*");
        String webXmlPath = OUTPUT_FOLDER + File.separator + appName + File.separator + "WEB-INF" + File.separator + "web.xml";
        Document doc = getDocument(webXmlPath);
        Node corsFilter = getFilterNode(doc,CORS_FILTER);
        
        NodeList initParams = ((Element) corsFilter).getElementsByTagName("init-param");
        for (int i = 0; i < initParams.getLength(); i++) {
            Node initParam = initParams.item(i);
            Node paramNameNode = ((Element) initParam).getElementsByTagName("param-name").item(0);
            String paramname = paramNameNode.getTextContent().trim();
            
            if(paramname.equals("cors.allowed.origins")){
                Node paramValNode = ((Element) initParam).getElementsByTagName("param-value").item(0);
                map.put(ALLOWED_ORIGINS, paramValNode.getTextContent().trim());
            }
        }
        
        return map;
    }
    
    public void updateCors(String appName, String origins, String appFolder) throws ParserConfigurationException, SAXException, IOException, TransformerException{
        String webXmlPath = appFolder + File.separator + appName + File.separator + "WEB-INF" + File.separator + "web.xml";
        Document doc = getDocument(webXmlPath);
        Node corsFilter = getFilterNode(doc,CORS_FILTER);
        
        if(origins == null || origins.equals("")){
            origins = "*";
        }
        
        boolean supportCreds = false;
        
        NodeList initParams = ((Element) corsFilter).getElementsByTagName("init-param");
        for (int i = 0; i < initParams.getLength(); i++) {
            Node initParam = initParams.item(i);
            Node paramNameNode = ((Element) initParam).getElementsByTagName("param-name").item(0);
            String paramname = paramNameNode.getTextContent().trim();
            
            if(paramname.equals("cors.allowed.origins")){
                Node paramValueNode = ((Element) initParam).getElementsByTagName("param-value").item(0);
                paramValueNode.setTextContent(origins);
                
            } else if(paramname.equals("cors.support.credentials")){
                //support credentials param exists
                supportCreds = true;
                // if all * allowed urls, remove init param cors.support.credentials
                if(origins.equals("*")){
                    corsFilter.removeChild(initParam);
                }
            }
        }
        //if origin is valid and not * and cors.support.credentials param does not exist, add the param
        if(!origins.equals("*") && !supportCreds){
            Element supportCredInitParam = doc.createElement("init-param");
        
            Element paramName = doc.createElement("param-name");
            paramName.setTextContent("cors.support.credentials");
            supportCredInitParam.appendChild(paramName);

            Element paramValue = doc.createElement("param-value");
            paramValue.setTextContent("true");
            supportCredInitParam.appendChild(paramValue);

            corsFilter.appendChild(supportCredInitParam);
        }
        
        writeXml(doc,webXmlPath);
    }
    
    public void removeProperty(String appName, String key) throws ParserConfigurationException, IOException, SAXException, TransformerConfigurationException, TransformerException {
        if(key.equals(ALLOWED_ORIGINS)){
            return;
        }

        //remove from backend config
        ConfigManager c = new ConfigManager(appName);
        c.removeProperty(key);
        
        // remove from web.xml
        String webXmlPath = OUTPUT_FOLDER + File.separator + appName + File.separator + "WEB-INF" + File.separator + "web.xml";
        Document doc = getDocument(webXmlPath);

        Node router = getFilterNode(doc, ROUTER);

        NodeList initParams = ((Element) router).getElementsByTagName("init-param");
        for (int i = 0; i < initParams.getLength(); i++) {
            Node initParam = initParams.item(i);
            Node paramNameNode = ((Element) initParam).getElementsByTagName("param-name").item(0);
            String paramname = paramNameNode.getTextContent().trim();
            if(key.equals(paramname)){
                router.removeChild(initParam);
            }
        }

        writeXml(doc, webXmlPath);
    }

    public JSONObject getProperties(String appName) throws ParserConfigurationException, SAXException, IOException {
        ConfigManager c = new ConfigManager(appName);
        JSONObject config = c.getConfig();
        JSONObject properties = config.getJSONObject(ConfigManager.KEY_PROPERTIES);
        
        return properties;
    }
}
