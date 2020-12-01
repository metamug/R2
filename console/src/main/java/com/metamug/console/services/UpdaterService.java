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

import com.metamug.console.daos.UpdaterDAO;
import com.metamug.console.sockets.UpdaterSocket;
import com.metamug.console.util.Util;
import static com.metamug.console.util.Util.APP_BASE;
import java.beans.PropertyVetoException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.hsqldb.cmdline.SqlFile;
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
public class UpdaterService {

    private final UpdaterDAO dao;

    public UpdaterService() {
        this.dao = new UpdaterDAO();
    }

    public void upgradeConsoleDatabase(String scriptFilepath) throws ClassNotFoundException, IOException {
        File scriptFile = new File(scriptFilepath);
        if (scriptFile.exists()) {
            //Connection con = null;
            String driver = Util.getDbProperties().getProperty("driver").toLowerCase().trim();
            if (driver.contains("hsqldb")) {
                try (Connection con = ConnectionProvider.getInstance().getConnection()) {
                    try (FileInputStream inputStream = new FileInputStream(scriptFile); InputStreamReader streamReader = new InputStreamReader(inputStream)) {
                        SqlFile sqlFile = new SqlFile(streamReader, "init", null, "UTF-8", false, new File("."));
                        sqlFile.setConnection(con);
                        sqlFile.execute();
                    }
                    scriptFile.delete();
                } catch (Exception e) {
                    Logger.getLogger(UpdaterService.class.getName()).log(Level.SEVERE, e.toString());
                }
            } else if (driver.contains("mysql")) {
            }
        }
    }

    public void upgradeAppDatabases(String scriptFileDir) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException, PropertyVetoException, ClassNotFoundException {
        File dir = new File(Util.OUTPUT_FOLDER);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.isDirectory() && !child.getName().equals("ROOT") && !child.getName().equals("console")) {
                    
                    try {
                        String contextFilePath = Util.OUTPUT_FOLDER + File.separator + child.getName()
                                + File.separator + "META-INF" + File.separator + "context.xml";
                        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                        Document doc = docBuilder.parse(contextFilePath);

                        Node resourceElement = doc.getElementsByTagName("Resource").item(0);
                        NamedNodeMap attr = resourceElement.getAttributes();

                        Node driverClassNameAttrb = attr.getNamedItem("driverClassName");
                        String driverClass = driverClassNameAttrb.getTextContent();

                        Node urlAttrb = attr.getNamedItem("jdbcUrl");
                        String url = urlAttrb.getTextContent();

                        Node usernameAttrb = attr.getNamedItem("username");
                        String user = usernameAttrb.getTextContent();

                        Node passwordAttrb = attr.getNamedItem("password");
                        String pass = passwordAttrb.getTextContent();

                        StringBuilder scriptFilePath = new StringBuilder(scriptFileDir);
                        scriptFilePath.append(File.separator);
                        String type = null;

                        switch (driverClass) {
                            case Util.HSQLDB_JDBC_DRIVER:
                                scriptFilePath.append("hsqldb_app_upgrade.sql");
                                type = Util.HSQLDB;
                                break;
                            case Util.MYSQL_JDBC_DRIVER:
                                scriptFilePath.append("mysql_app_upgrade.sql");
                                type = Util.MYSQL;
                                break;
                            case Util.POSTGRESQL_JDBC_DRIVER:
                                scriptFilePath.append("postgresql_app_upgrade.sql");
                                type = Util.POSTGRESQL;
                                break; 
                            case Util.MSSQL_JDBC_DRIVER:
                                scriptFilePath.append("mssql_app_upgrade.sql");
                                type = Util.MSSQL;
                                break;
                            default:
                                throw new SQLException("Database driver not supported: "+driverClass);
                        }

                        File scriptFile = new File(scriptFilePath.toString());
                        if (scriptFile.exists()) {
                            dao.runUpgradeScript(type, url, user, pass, scriptFilePath.toString());
                            scriptFile.delete(); 
                        }             
                    } catch (SQLException ex) {
                        Logger.getLogger(UpdaterService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public void downloadUpdate(String jsonConfig, String localVersion, List<Session> sessions, String token) throws IOException {
        DefaultArtifactVersion localV = new DefaultArtifactVersion(localVersion);
        JSONObject json = new JSONObject(jsonConfig);
        String policyVersion = json.getString("version");
        if (localV.compareTo(new DefaultArtifactVersion(policyVersion)) == 0) {
            return;
        }
        JSONArray files = json.getJSONArray("files");
        UpdaterSocket.totalParts = files.length();
        UpdaterSocket.downloadedParts = 0;
        //change status to IN_PROGRESS before starting update downloads
        UpdaterSocket.updaterStatus = UpdaterSocket.UpdateStatus.IN_PROGRESS;

        dao.addVersion(policyVersion, json.getString("description"), files.toString());

        for (int iter = 0; iter < files.length(); iter++) {
            JSONObject file = files.getJSONObject(iter);

            String destFilePath = file.getString("dest");
            destFilePath = destFilePath.replace("/", File.separator);

            String action = file.getString("action");

            if (action.equals("add"))// && !md5.equals(generateMD5(destFile)) )
            {
                String updateDir = APP_BASE + File.separator + "update";

                String src = file.getString("src");

                File updateDirFolder = new File(updateDir);
                if (!updateDirFolder.exists()) {
                    updateDirFolder.mkdir();
                }

                //check whether file exists and match hash
                String originalDest = file.getString("dest");
                originalDest = originalDest.replace("/", File.separator);
                String originalDestFilePath = APP_BASE + originalDest;
                File originalFile = new File(originalDestFilePath);

                if (originalFile.exists()) {

                    String generatedHash = generateMD5(originalFile);

                    if (!(generatedHash.equalsIgnoreCase(file.getString("md5")))) {
                        destFilePath = updateDir + destFilePath;
                        File destFile = new File(destFilePath);

                        File destFileParent = destFile.getParentFile();
                        if (!destFileParent.exists()) {
                            destFileParent.mkdirs();
                        }

                        FileUtils.copyURLToFile(new URL(src), destFile);
                    }
                } else {
                    destFilePath = updateDir + destFilePath;
                    File destFile = new File(destFilePath);

                    File destFileParent = destFile.getParentFile();
                    if (!destFileParent.exists()) {
                        destFileParent.mkdirs();
                    }

                    FileUtils.copyURLToFile(new URL(src), destFile);
                }

            } else if (action.equals("remove")) {
                String removeShFilePath = APP_BASE + File.separator + "bin" + File.separator + "remove.sh";
                String removeBatFilePath = APP_BASE + File.separator + "bin" + File.separator + "remove.bat";

                File removeShFile = new File(removeShFilePath);
                if (!removeShFile.exists()) {
                    removeShFile.createNewFile();
                }

                File removeBatFile = new File(removeBatFilePath);
                if (!removeBatFile.exists()) {
                    removeBatFile.createNewFile();
                }

                destFilePath = APP_BASE + destFilePath;
                File destFile = new File(destFilePath);

                if (destFile.exists()) {
                    String generatedHash = generateMD5(destFile);

                    if (generatedHash.equalsIgnoreCase(file.getString("md5"))) {
                        writeRemoveSh(removeShFilePath, destFilePath);
                        writeRemoveBat(removeBatFilePath, destFilePath);
                    }
                }
            }
            UpdaterSocket.downloadedParts = iter + 1;

            for (Session s : sessions) {
                JSONObject obj = new JSONObject();
                obj.put("status", UpdaterSocket.updaterStatus);
                obj.put("total", UpdaterSocket.totalParts);
                obj.put("done", iter + 1);

                s.getBasicRemote().sendText(obj.toString());
            }
        }
        //updates download completed - status DONE
        UpdaterSocket.updaterStatus = UpdaterSocket.UpdateStatus.DONE;

        for (Session s : sessions) {
            JSONObject obj = new JSONObject();
            obj.put("status", UpdaterSocket.UpdateStatus.DONE);
            obj.put("total", UpdaterSocket.totalParts);
            obj.put("done", UpdaterSocket.downloadedParts);
            s.getBasicRemote().sendText(obj.toString());
        }
        //logout after completing download
        AuthService authService = new AuthService();
        try {
            authService.deleteToken(token);
        } catch (SQLException | PropertyVetoException ex) {
            Logger.getLogger(UpdaterService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //shut down the server
        Util.shutdownServer();
    }

    private void writeRemoveSh(String removeShFilePath, String rmFilePath) throws IOException {
        rmFilePath = rmFilePath.replace(File.separator, "/");
        String rmScript = "rm -rf " + rmFilePath + " \n";
        try (Writer w = new BufferedWriter(new FileWriter(removeShFilePath, true))) {
            w.write(rmScript);
        }
    }

    private void writeRemoveBat(String removeBatFilePath, String rmFilePath) throws IOException {
        rmFilePath = rmFilePath.replace(File.separator, "\\");
        String rmScript = "del /Q /F " + rmFilePath + " \n";
        try (Writer w = new BufferedWriter(new FileWriter(removeBatFilePath, true))) {
            w.write(rmScript);
        }
    }

    private String generateMD5(File file) throws FileNotFoundException, IOException {
        if (!file.exists()) {
            return "";
        }
        String md5;
        try (FileInputStream fis = new FileInputStream(file)) {
            md5 = DigestUtils.md5Hex(fis);
        }
        return md5;
    }

    public String getCurrentVersion() {
        return dao.getCurrentVersion();
    }
}
