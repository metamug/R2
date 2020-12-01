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
package com.metamug.console.services;

import com.metamug.console.daos.ExecuteDAO;
import com.metamug.console.exception.MetamugError;
import com.metamug.console.exception.MetamugException;
import com.metamug.console.services.file.ResourceFileService;
import com.metamug.console.util.FileUtil;
import com.metamug.console.util.Util;
import static com.metamug.console.util.Util.OUTPUT_FOLDER;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author Kaisteel
 */
public class ExecuteService {

    public static final String UPLOAD_LISTENER = "UploadListener";
    public static final String REQUEST_PROCESSABLE = "RequestProcessable";
    public static final String RESULT_PROCESSABLE = "ResultProcessable";
    public static final String RESPONSE_PROCESSABLE = "ResponseProcessable";
    
    private final ExecuteDAO dao;

    public ExecuteService() {
        this.dao = new ExecuteDAO();
    }

    public boolean addCode(int userId, String appName, String mavenZipFileName, String jarFileName, String fileSize) throws SQLException, IOException, PropertyVetoException, ClassNotFoundException, MalformedURLException, MetamugException {
        if (isValid(jarFileName, appName, mavenZipFileName, userId)) {
            dao.addCode(userId, appName, mavenZipFileName, jarFileName, fileSize);
            return true;
        }
        return false;
    }

    public JSONObject getCodeListing(int userId, String appName) throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
        return dao.getCodeListing(userId, appName);
    }

    public JSONObject deleteCode(String codeName, String appName, int userId) throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
        return dao.deleteCode(codeName, appName, userId);
    }

    private boolean isValid(String jarFileName, String appName, String projectName, int userId) throws ClassNotFoundException, MalformedURLException, MetamugException, IOException, SQLException, PropertyVetoException {
        Map<String, String> classMap = new HashMap<>();
        File jar = new File(OUTPUT_FOLDER + "/" + appName + "/WEB-INF/lib/" + jarFileName);
        JarFile jarFile = new JarFile(jar);
        Enumeration allEntries = jarFile.entries();
        URL[] urls = {new URL("jar:file:" + jar.getPath() + "!/")};
        URLClassLoader classLoader = URLClassLoader.newInstance(urls, ExecuteService.class.getClassLoader());
        boolean containsMtgDependency = false;
        while (allEntries.hasMoreElements()) {
            JarEntry je = (JarEntry) allEntries.nextElement();
            if (!je.isDirectory() && je.getName().endsWith(".class")) {
                String className = je.getName().substring(0, je.getName().length() - ".class".length());
                className = className.replace('/', '.');
                Class clazz = classLoader.loadClass(className);
                Class[] interfaces = clazz.getInterfaces();
                for (Class aInterface : interfaces) {
                    if (aInterface.getName().contains(REQUEST_PROCESSABLE)) {
                        containsMtgDependency = true;
                        if (dao.classExists(className, projectName, appName, userId)) {
                            throw new MetamugException(MetamugError.CLASS_NAME_EXISTS);
                        } else {
                            classMap.put(className, REQUEST_PROCESSABLE);
                        }
                    } else if (aInterface.getName().contains(RESULT_PROCESSABLE)) {
                        containsMtgDependency = true;
                        if (dao.classExists(className, projectName, appName, userId)) {
                            throw new MetamugException(MetamugError.CLASS_NAME_EXISTS);
                        } else {
                            classMap.put(className, RESULT_PROCESSABLE);
                        }
                    } else if (aInterface.getName().contains(RESPONSE_PROCESSABLE)) {
                        containsMtgDependency = true;
                        if (dao.classExists(className, projectName, appName, userId)) {
                            throw new MetamugException(MetamugError.CLASS_NAME_EXISTS);
                        } else {
                            classMap.put(className, RESPONSE_PROCESSABLE);
                        }
                    } else if (aInterface.getName().contains(UPLOAD_LISTENER)) {
                        containsMtgDependency = true;
                        if (dao.classExists(appName, userId, UPLOAD_LISTENER)) {
                            //if listener already exists, throw error
                            throw new MetamugException(MetamugError.UPLOAD_LISTENER_EXISTS);
                        } else {
                            //save listener classname in config file
                            try {
                                saveToConfig(appName, className,UPLOAD_LISTENER);
                            } catch (IOException ex) {
                                Logger.getLogger(ExecuteService.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                            }
                            classMap.put(className, UPLOAD_LISTENER);
                        }
                    }
                }
            }
        }
        if (!classMap.isEmpty()) {
            for (Map.Entry<String, String> entry : classMap.entrySet()) {
                String className = entry.getKey();
                String classType = entry.getValue();
                dao.addClassNames(className, classType, appName, projectName, userId);
            }
        } else {
            FileUtil.deleteFile(jar);
        }
        if (!containsMtgDependency) {
            throw new MetamugException(MetamugError.MAVEN_MISSING_DEPENDENCY);
        }
        return (!classMap.isEmpty());
    }

    public boolean buildAndDeployMavenProject(int userId, File projectFile, String appName) throws NullPointerException, FileNotFoundException, MetamugException, MavenInvocationException, ZipException, IOException, ClassNotFoundException, MalformedURLException, SQLException, PropertyVetoException {
        File projectDirectory = new File(OUTPUT_FOLDER + File.separator + appName + File.separator + "WEB-INF" +
                File.separator + "projects");
        FileUtils.cleanDirectory(projectDirectory);

        ZipFile zipFile = new ZipFile(projectFile);
        zipFile.extractAll(OUTPUT_FOLDER + File.separator + appName + File.separator + "WEB-INF" + 
                File.separator + "projects" + File.separator);
        InvocationRequest invocRequest = new DefaultInvocationRequest();
        File[] projectFiles = projectDirectory.listFiles((File dir, String name) -> !name.matches("^[^A-Za-z0-9].*$"));
        for (File mvnProject : projectFiles) {
            if (mvnProject.isDirectory()) {
                File pomFile = new File(mvnProject.getPath() + File.separator + "pom.xml");
                if (pomFile.exists()) {
                    createMtgjar(pomFile);
                    invocRequest.setPomFile(pomFile);
                    invocRequest.setGoals(Collections.singletonList("clean install -DskipTests=true -ff -Dfile.encoding=UTF-8 org.apache.maven.plugins:maven-dependency-plugin:2.6:copy-dependencies -DexcludeScope=provided -DoutputDirectory=" + OUTPUT_FOLDER + File.separator + appName + File.separator + "WEB-INF/lib -l log.txt"));
                    Invoker invoker = new DefaultInvoker();
                    invoker.setMavenHome(new File(Util.APP_BASE + File.separator + "maven"));
                    InvocationResult result = invoker.execute(invocRequest);
                    if (result.getExitCode() != 0) {
                        throw new MetamugException(MetamugError.MAVEN_BUILD_FAILED);
                    }
                    File file = new File(mvnProject.getPath() + File.separator + "target");
                    File[] jarFiles = file.listFiles((File dir, String name) -> name.toLowerCase().endsWith(".jar"));
                    if (jarFiles != null) {
                        FileUtils.copyFileToDirectory(jarFiles[0], new File(OUTPUT_FOLDER + File.separator + appName + File.separator + "WEB-INF/lib"), true);
                        try {
                            addCode(userId, appName, mvnProject.getName(), jarFiles[0].getName(), ResourceFileService.getFileSize((float) jarFiles[0].length()));

                            //copy project folder to /downloads/plugins/{appName}/
                            File pluginFolder = new File(Util.PLUGINS_FOLDER + File.separator + appName + File.separator + mvnProject.getName());
                            if (!pluginFolder.exists()) {
                                Files.createDirectories(Paths.get(pluginFolder.getPath()));
                            } else {
                                FileUtil.deleteFile(pluginFolder);
                            }
                            FileUtil.copyFolder(mvnProject, pluginFolder);

                            FileUtil.deleteFile(mvnProject);
                        } catch (MetamugException ex) {
                            Logger.getLogger(ExecuteService.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                            Files.deleteIfExists(Paths.get(mvnProject.getPath()));
                            Files.deleteIfExists(Paths.get(new File(OUTPUT_FOLDER + File.separator + appName + File.separator + "WEB-INF/lib/" + jarFiles[0]).toURI()));
                            switch (ex.getError()) {
                                case CLASS_NAME_EXISTS:
                                    throw new MetamugException(MetamugError.CLASS_NAME_EXISTS);
                                case UPLOAD_LISTENER_EXISTS:
                                    throw new MetamugException(MetamugError.UPLOAD_LISTENER_EXISTS);
                                default:
                                    throw new MetamugException(MetamugError.MAVEN_MISSING_DEPENDENCY);
                            }
                        }
                        return true;
                    }
                } else {
                    FileUtil.deleteFile(mvnProject);
                    throw new FileNotFoundException(mvnProject.getName() + " project doesn't contain 'pom.xml' file. Upload a valid maven project");
                }
            }
        }
        return false;
    }
    
    private void saveToConfig(String appName, String className, String classType) throws FileNotFoundException, IOException{
        File file = new File(OUTPUT_FOLDER + "/" + appName + "/WEB-INF/classes/config.properties");
        Properties prop = new Properties();
        
        try (FileInputStream in = new FileInputStream(file)) {
            prop.load(in);
        }
        try (FileOutputStream out = new FileOutputStream(file)) {
            if(classType.equals(UPLOAD_LISTENER)){
                prop.setProperty("uploadlistener", className);
            }
            prop.store(out, null);
        }
    }

    private void createMtgjar(File pomFile) throws MetamugException {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(pomFile);
            //Check whether <finalName> element exists
            NodeList finalNameList = doc.getElementsByTagName("finalName");
            if (finalNameList.getLength() > 0) {
                Node finalNameNode = finalNameList.item(0);
                finalNameNode.setTextContent(finalNameNode.getTextContent() + "-MTG");
            } else {
                Node versionNode = doc.getElementsByTagName("version").item(0);
                versionNode.setTextContent(versionNode.getTextContent() + "-MTG");
            }

            //Add <scope>provided</scope> for known dependencies
            NodeList dependencyList = doc.getElementsByTagName("dependency");
            for (int i = 0; i < dependencyList.getLength(); i++) {
                Element item = (Element) dependencyList.item(i);
                if (item.getElementsByTagName("groupId").item(0).getTextContent().equalsIgnoreCase("com.metamug") && (item.getElementsByTagName("scope").getLength() < 1)) {
                    String givenVersion = item.getElementsByTagName("version").item(0).getTextContent();
                    if (!givenVersion.equalsIgnoreCase("1.5")) {
                        throw new MetamugException(MetamugError.MAVEN_DEPRECATED_DEPENDENCY_VERSION);
                    }
                    Element scopeElement = doc.createElement("scope");
                    Text value = doc.createTextNode("provided");
                    scopeElement.appendChild(value);
                    item.appendChild(scopeElement);
                }
                if (item.getElementsByTagName("groupId").item(0).getTextContent().equalsIgnoreCase("org.json") && (item.getElementsByTagName("scope").getLength() < 1)) {
                    Element scopeElement = doc.createElement("scope");
                    Text value = doc.createTextNode("provided");
                    scopeElement.appendChild(value);
                    item.appendChild(scopeElement);
                }
                if (item.getElementsByTagName("groupId").item(0).getTextContent().equalsIgnoreCase("org.mindrot") && (item.getElementsByTagName("scope").getLength() < 1)) {
                    Element scopeElement = doc.createElement("scope");
                    Text value = doc.createTextNode("provided");
                    scopeElement.appendChild(value);
                    item.appendChild(scopeElement);
                }
                if (item.getElementsByTagName("artifactId").item(0).getTextContent().equalsIgnoreCase("mysql-connector-java") && (item.getElementsByTagName("scope").getLength() < 1)) {
                    Element scopeElement = doc.createElement("scope");
                    Text value = doc.createTextNode("provided");
                    scopeElement.appendChild(value);
                    item.appendChild(scopeElement);
                }
                if (item.getElementsByTagName("artifactId").item(0).getTextContent().equalsIgnoreCase("postgresql") && (item.getElementsByTagName("scope").getLength() < 1)) {
                    Element scopeElement = doc.createElement("scope");
                    Text value = doc.createTextNode("provided");
                    scopeElement.appendChild(value);
                    item.appendChild(scopeElement);
                }
            }
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(pomFile.getPath()));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | SAXException | TransformerException | IOException ex) {
            Logger.getLogger(ExecuteService.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
