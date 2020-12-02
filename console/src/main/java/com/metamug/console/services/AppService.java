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

import com.metamug.console.backend.ConfigManager;
import com.metamug.console.backend.Loader;
import com.metamug.console.daos.AppDAO;
import com.metamug.console.exception.MetamugError;
import com.metamug.console.exception.MetamugException;
import com.metamug.console.util.Util;
import com.metamug.parser.exception.ResourceTestException;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

/**
 *
 * @author Kainix
 */
public class AppService {

    private final AppDAO dao;

    private static final List<String> RESERVED_APP_NAMES = new ArrayList<>();

    static {
        RESERVED_APP_NAMES.add("console");
        RESERVED_APP_NAMES.add("ROOT");
    }

    public AppService() {
        this.dao = new AppDAO();
    }   
    
    public JSONObject getApp(String appName, Integer userId, boolean makeDefault) {
        return dao.getApp(appName, userId, makeDefault);
    }

    public JSONObject getApps(Integer userId, boolean name) {
        return dao.getApps(userId, name);
    }

    public void createApp(int userId, String appName, String dbName, String description, String version, String domain) {
        dao.createApp(userId, appName, dbName, description, version, domain);
    }

    public JSONObject deleteApp(String appName, int userId) {
        return dao.deleteApp(appName, userId);
    }

    public boolean appExists(String appName) {
        return dao.appExists(appName);
    }

    private void createDefaultTables(String appName, JSONObject user)
            throws SQLException, IOException, PropertyVetoException, ClassNotFoundException, URISyntaxException {
        Properties dbProperties = Util.getDbProperties();
        String driver;

        driver = dbProperties.getProperty("driver").toLowerCase().trim();

        if (driver.contains("hsqldb")) {
            String host = dbProperties.getProperty("host") == null ? Util.APP_BASE + "/databases/" + appName : dbProperties.getProperty("host").trim();
            String jdbcUrl = dbProperties.getProperty("protocol") + "://" + host + File.separator + appName + "_db" + (dbProperties.getProperty("options") != null && !dbProperties.getProperty("options").trim().isEmpty() ? ";" + dbProperties.getProperty("options").trim() : "");
            String defaultTables = new String(Files.readAllBytes(Paths.get(AppService.class.getClassLoader().getResource("hsqldb_defaultTables.sql").toURI())), StandardCharsets.UTF_8);
            separateBySemiColon(defaultTables).forEach( query -> {
                executeQuery(query.trim(), jdbcUrl, user);
            });
        } else {
            dao.createDefaultTables(appName);
        }
    }

    public void createDefaultTablesForExternal(Map<String,String> dbDetails)
            throws SQLException, IOException, PropertyVetoException, ClassNotFoundException, URISyntaxException {
        dao.createDefaultTablesForExternal(dbDetails);
    }

    public boolean testConnection(Map<String,String> dbDetails) {
        return AppDAO.testConnection(dbDetails);
    }    

    public boolean create(int userId, Map<String,String> appDetails, String domain,Map<String,String> dbDetails)
            throws ClassNotFoundException, TransformerException, TransformerConfigurationException, SAXException, IOException, ParserConfigurationException, SQLException, PropertyVetoException, MetamugException, URISyntaxException, ResourceTestException {
        String appName = appDetails.get("appName");
        String version = appDetails.get("version");
        String description = appDetails.get("description");
        
        String dbType = dbDetails.get("dbType");
        String dbUrl = dbDetails.get("dbUrl");
        
        if (appName.matches("[a-zA-Z]+")) {
            if(!dao.appExists(appName)){
                if (hasThresholdReached(userId)) {
                    if (!appExists(appName) && !RESERVED_APP_NAMES.contains(appName)) {
                        UserService userService = new UserService();
                        JSONObject user = userService.getUser(userId);

                        if (dbType.equals(Util.INTERNAL_DB)) {
                            //if dbtype is internal, use details of console db
                            String driver = Util.getDbProperties().getProperty("driver").toLowerCase().trim();

                            if (driver.contains(Util.HSQLDB)) {
                                //create default tables (cors,request_log,query_log,error_log,usr,usr_role,mtg_config)
                                createDefaultTables(appName.toLowerCase(), user);

                                dbDetails.put("dbUser", user.getString("user_id"));
                                dbDetails.put("dbPass", user.getString("password"));

                                Properties dbProperties = Util.getDbProperties();
                                String host = dbProperties.getProperty("host") == null ? Util.APP_BASE + File.separator+ "databases"
                                            + File.separator + appName : dbProperties.getProperty("host").trim();
                                String url = dbProperties.getProperty("protocol") + "://" + host + File.separator + appName + "_db" + (dbProperties.getProperty("options") != null && !dbProperties.getProperty("options").trim().isEmpty() ? ";" + dbProperties.getProperty("options").trim() : "");
                                dbDetails.put("dbUrl", url);

                                ConfigManager conf = new ConfigManager(appName);
                                conf.create(appDetails,dbDetails,userId);

                                Loader deployer = new Loader(appName);
                                deployer.load(null);

                                //Make a db entry storing user's app details
                                createApp(userId, appName, appName + "_db", description, version, domain);

                            }
                            return false;
                        } else {
                            //create default tables (cors,request_log,query_log,error_log,usr,usr_role,mtg_config)
                            createDefaultTablesForExternal(dbDetails);

                            ConfigManager conf = new ConfigManager(appName);
                            conf.create(appDetails,dbDetails,userId);

                            Loader deployer = new Loader(appName);
                            deployer.load(null);

                            //Make a db entry storing user's app details
                            createApp(userId, appName, dbUrl, description, version, domain);

                            return false;                       
                        }
                    } else {
                        return true;
                    }
                } else {
                    throw new MetamugException(MetamugError.APP_CREATE_LIMIT_EXCEEDED);
                }
            } else {
                throw new MetamugException(MetamugError.APP_EXISTS);
            }  
        } else {
            throw new MetamugException(MetamugError.INVALID_APP_NAME);
        }
    }

//    public void deleteAppLogs(String appName) throws SQLException, PropertyVetoException, ClassNotFoundException, IOException {
//        dao.deleteAppLogs(appName);
//    }
    private boolean hasThresholdReached(int userId) {
        return dao.hasReachedThreshold(userId);
    }

    public JSONObject getErrorLogs(String appName, String domain) throws IOException, ClassNotFoundException, PropertyVetoException, SQLException {
        String result = Util.executeQueryInApp(domain + "/" + appName, "errorlog", "", appName, null);

        if (result.length() > 0) {
            try {
                JSONObject json = new JSONObject(result);
                return json;
            } catch (JSONException ex) {
                JSONObject emptyJson = new JSONObject();
                JSONArray columnHeader = new JSONArray();
                columnHeader.put("error_id").put("method").put("message").put("trace").put("uri").put("created_on");
                emptyJson.put("columnHeaders", columnHeader);
                emptyJson.put("data", new JSONArray());
                return emptyJson;
            }
        } else {
            JSONObject emptyJson = new JSONObject();
            JSONArray columnHeader = new JSONArray();
            columnHeader.put("error_id").put("method").put("message").put("trace").put("uri").put("created_on");
            emptyJson.put("columnHeaders", columnHeader);
            emptyJson.put("data", new JSONArray());
            return emptyJson;
        }
//        return dao.getErrorLogs(appName);
    }

    /**
     * Creates default tables for HSQL based database.
     *
     * @param query Table query.
     * @param jdbcUrl Database connection string.
     * @param user User details JSON Object.
     */
    private void executeQuery(String query, String jdbcUrl, JSONObject user) {
        try {
            String JDBC_DRIVER = "org.hsqldb.jdbc.JDBCDriver";
            Class.forName(JDBC_DRIVER);
            try (Connection con = DriverManager.getConnection(jdbcUrl, user.getString("user_id"), user.getString("password")); Statement stmnt = con.createStatement()) {
                stmnt.execute(query);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private List<String> separateBySemiColon(String str) {
        str = str.replace("\"", "'");
        char last = str.charAt(str.length() - 1);
        if (!(last == ';')) {
            str += ';';
        }
        Pattern p = Pattern.compile("((?:(?:'[^']*')|[^;])*);");
        Matcher m = p.matcher(str);
        //int cnt = 0;
        List<String> sqlList = new ArrayList<>();
        while (m.find()) {
            //  System.out.println(++cnt + ": " + m.group(1));
            sqlList.add(m.group(1));
        }
        return sqlList;
    }
}
