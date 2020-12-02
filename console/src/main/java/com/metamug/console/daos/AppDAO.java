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
package com.metamug.console.daos;

import com.ibatis.common.jdbc.ScriptRunner;
import com.metamug.console.backend.ConfigManager;
import com.metamug.console.services.ConnectionProvider;
import com.metamug.console.services.PropertiesService;
import com.metamug.console.util.FileUtil;
import com.metamug.console.util.Util;
import static com.metamug.console.util.Util.APP_BASE;
import static com.metamug.console.util.Util.OUTPUT_FOLDER;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

/**
 *
 * @author Kainix
 */
public class AppDAO {

    public AppDAO() {
    }

    /**
     * Creates a database with given name
     *
     * @param dbName Name by which database will be generated
     * @param user User details
     */
    public void createDatabase(String dbName, JSONObject user) {
        try {
            if (Util.getDbProperties().getProperty("driver").toLowerCase().trim().contains("hsqldb")) {

            } else {
                try (Connection con = ConnectionProvider.getInstance().getConnection()) {
                    try (PreparedStatement stmt = con.prepareStatement("CREATE DATABASE " + dbName + "_db")) {
                        stmt.execute();
                        //@todo change the hardcoded remote ip to localhost
                        PreparedStatement grantStatement = con.prepareStatement("GRANT ALL ON " + dbName + "_db.* TO '" + user.getString("user_id") + "'@'%' IDENTIFIED BY '" + user.getString("password") + "'");
//                PreparedStatement grantStatement = con.prepareStatement("GRANT SELECT, INSERT, UPDATE, DELETE, DROP, CREATE, CREATE ROUTINE, INDEX, ALTER ON " + dbName + "_db.* TO '" + user.getString("user_id") + "'@'%' IDENTIFIED BY '" + user.getString("password") + "'");
                        grantStatement.execute();
                        PreparedStatement privilegeStatement = con.prepareStatement("flush privileges");
                        privilegeStatement.execute();
                    }
                } catch (SQLException | ClassNotFoundException | IOException | PropertyVetoException ex) {
                    Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates a database with given name
     *
     * @param dbName Name by which database will be generated
     * @param user User details
     */
    /*public void createDatabaseForExternal(String dbName, JSONObject user,
            String dbType, String dbUrl, String dbUser, String dbPass) {

        try (Connection con = ConnectionProvider.getInstance().getConnection(dbType, dbUrl, dbUser, dbPass)) {
            try (PreparedStatement stmt = con.prepareStatement("CREATE DATABASE " + dbName + "_db")) {
                stmt.execute();
                //@todo change the hardcoded remote ip to localhost
                PreparedStatement grantStatement = con.prepareStatement("GRANT ALL ON " + dbName + "_db.* TO '" + user.getString("user_id") + "'@'%' IDENTIFIED BY '" + user.getString("password") + "'");
//              PreparedStatement grantStatement = con.prepareStatement("GRANT SELECT, INSERT, UPDATE, DELETE, DROP, CREATE, CREATE ROUTINE, INDEX, ALTER ON " + dbName + "_db.* TO '" + user.getString("user_id") + "'@'%' IDENTIFIED BY '" + user.getString("password") + "'");
                grantStatement.execute();
                PreparedStatement privilegeStatement = con.prepareStatement("flush privileges");
                privilegeStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException | IOException | PropertyVetoException ex) {
            Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }

    }*/
    /**
     * Creates default tables that are given to every user who registers
     *
     * @param dbName database name in which the tables are generated
     */
    public void createDefaultTables(String dbName) {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            CallableStatement callStatement = con.prepareCall("{call defaultTables(?)}");
            callStatement.setString("db", dbName + "_db");
            callStatement.executeUpdate();
        } catch (SQLException | IOException | PropertyVetoException | ClassNotFoundException ex) {
            Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
    }

    /**
     * Creates default tables that are given to every user who registers
     *
     * @param dbDetails
     */
    public void createDefaultTablesForExternal(Map<String,String> dbDetails) {
        String dbType = dbDetails.get("dbType");
        String dbUrl = dbDetails.get("dbUrl");
        String dbUser = dbDetails.get("dbUser");
        String dbPass = dbDetails.get("dbPass");
        
        String scriptFile = "";
        switch (dbType) {
            case Util.MYSQL:
                scriptFile = "mysql_defaultTables.sql";
                break;
            case Util.POSTGRESQL:
                scriptFile = "postgres_defaultTables.sql";
                break;
            case Util.MSSQL:
                scriptFile = "mssql_defaultTables.sql";
                break;
            default:
                break;
        }
        try (Connection con = ConnectionProvider.getInstance().getConnection(dbType, dbUrl, dbUser, dbPass)) {
            InputStream scriptFileInputStream = AppDAO.class.getClassLoader().getResourceAsStream(scriptFile);
            ScriptRunner sr = new ScriptRunner(con, false, false);
            sr.runScript(new BufferedReader(new InputStreamReader(scriptFileInputStream)));
        } catch (SQLException | IOException | PropertyVetoException | ClassNotFoundException ex) {
            //Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
    }

    /**
     * Test external database connection
     *
     * @param dbDetails
     * @return true if connection test successful
     */
    public static boolean testConnection(Map<String,String> dbDetails) {
        String dbType = dbDetails.get("dbType");
        String dbUrl = dbDetails.get("dbUrl");
        String dbUser = dbDetails.get("dbUser");
        String dbPass = dbDetails.get("dbPass");
        
        String currentDatabase = null;
        switch (dbType) {
            case Util.MYSQL:
                currentDatabase = "database()";
                break;
            case Util.POSTGRESQL:
                currentDatabase = "current_database()";
                break;        
            case Util.MSSQL:
                currentDatabase = "db_name()";
                break;
            default:
                break;
        }
        
        try (Connection con = ConnectionProvider.getInstance().getConnection(dbType, dbUrl, dbUser, dbPass)) {
            Statement stmt = con.createStatement();
            String sql = "SELECT " + currentDatabase + " AS selecteddb";
            String dbname;
            try (ResultSet rs = stmt.executeQuery(sql)) {
                dbname = null;
                while (rs.next()) {
                    dbname = rs.getString("selecteddb");
                }
            }
            con.close();

            if(dbname == null){
                Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, "Database name not found in DB url.");
            }
            return null != dbname;
        } catch (SQLException | IOException | PropertyVetoException | ClassNotFoundException ex) {
            Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            return false;
        }
    }

    /**
     * Makes an entry in database regarding App's creation
     *
     * @param userId Unique number to identify every user
     * @param appName Name of the App
     * @param dbName Name of the database
     * @param appDescription Description about the App
     * @param version App version
     * @param domain Domain name where the console is hosted
     */
    public void createApp(int userId, String appName, String dbName, String appDescription, String version, String domain) {
        SecureRandom random = new SecureRandom();
        String secretKey = null, accessKey = null;
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            try (PreparedStatement statement = con.prepareStatement("INSERT INTO console_app "
                    + "(app_name,"
                    + "app_description,"
                    + "app_version,"
                    + "app_db,"
                    + "end_point,"
                    + "user_id,"
                    + "access_key,"
                    + "secret_key,"
                    + "deleted_on)"
                    
                    + "values (?,?,?,?,?,?,?,?,null)")) {
                accessKey = new BigInteger(130, random).toString(16);
                secretKey = new BigInteger(256, random).toString(16);
                
                statement.setString(1, appName);
                statement.setString(2, appDescription);
                statement.setString(3, version);
                statement.setString(4, dbName);
                statement.setString(5, domain + "/" + appName + "/");
                statement.setInt(6, userId);
                statement.setString(7, accessKey);
                statement.setString(8, secretKey);
                statement.execute();
            }
            try (PreparedStatement statement = con.prepareStatement("UPDATE console_user SET last_visited_app=? WHERE user_id=?")) {
                statement.setString(1, appName);
                statement.setInt(2, userId);
                statement.executeUpdate();
            }
        } catch (PropertyVetoException | IOException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        Util.storeKeys(accessKey, secretKey, appName);
    }

    public JSONObject getApp(String appName, Integer userId, boolean makeDefaultApp) {
        JSONObject obj = new JSONObject();
        JSONArray jsonArray;
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT app_name,app_description,app_version,app_db,end_point,deployed,app_resources,created_on FROM console_app WHERE user_id=? AND app_name=? AND deleted_on IS NULL");
            statement.setInt(1, userId);
            statement.setString(2, appName);
            try (ResultSet result = statement.executeQuery()) {
                jsonArray = new JSONArray();
                while (result.next()) {
                    JSONObject app = new JSONObject();
                    app.put("app_description", result.getString("app_description"));
                    app.put("app_version", result.getString("app_version"));
                    app.put("app_db", result.getString("app_db"));
                    app.put("end_point", result.getString("end_point"));
                    app.put("app_resources", result.getString("app_resources"));
                    app.put("deployed", result.getInt("deployed"));
                    
                    PropertiesService pservice = new PropertiesService();
                    try {
                        JSONObject appProps = pservice.getProperties(appName);
                        app.put("properties", appProps);
                    } catch (ParserConfigurationException | SAXException ex) {
                        Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //Make this app as last_visited_app
                    if(makeDefaultApp){
                        statement = con.prepareStatement("UPDATE console_user SET last_visited_app=? WHERE user_id=?");
                        statement.setString(1, result.getString("app_name"));
                        statement.setInt(2, userId);
                        statement.execute();
                    }
                    jsonArray.put(app);
                }
            }
            statement.close();
            obj.put("data", jsonArray);
        } catch (IOException | SQLException | PropertyVetoException | ClassNotFoundException ex) {
            obj.put("message", "Query Error");
            obj.put("status", 409);
            Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return obj;
    }

    public JSONObject getApps(Integer userId, boolean name) {
        JSONObject obj = new JSONObject();
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            JSONArray jsonArray;
            String query = "SELECT app_name,app_description,app_version,app_db,end_point,app_resources,created_on FROM console_app WHERE user_id=? AND deleted_on IS NULL";
            if(name){
                query = "SELECT app_name FROM console_app WHERE user_id=? AND deleted_on IS NULL";
            }
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setInt(1, userId);
                try (ResultSet result = statement.executeQuery()) {
                    jsonArray = new JSONArray();
                    while (result.next()) {
                        JSONObject app = new JSONObject();
                        String appName = result.getString("app_name");
                        app.put("app_name", appName);
                        if(!name){
                            app.put("app_description", result.getString("app_description"));
                            app.put("app_version", result.getString("app_version"));
                            app.put("app_db", result.getString("app_db"));
                            try {
                                ConfigManager conf = new ConfigManager(appName);
                                JSONObject dbDetails = conf.getDbDetails();
                                app.put("app_db_details", dbDetails);
                            } catch (ParserConfigurationException | SAXException ex) {
                                //Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            app.put("end_point", result.getString("end_point"));
                            app.put("app_resources", result.getString("app_resources"));
                            app.put("created_on", result.getTimestamp("created_on"));
                        }
                        jsonArray.put(app);
                    }
                }
            }
            obj.put("data", jsonArray);
        } catch (IOException | SQLException | PropertyVetoException | ClassNotFoundException ex) {
            obj.put("message", "Query Error");
            obj.put("status", 409);
            Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        return obj;
    }
    
    public void deployApp(String appName) throws IOException {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            try (PreparedStatement statement = con.prepareStatement("UPDATE console_app SET deployed=1"
                    + " WHERE app_name=?")) {
                statement.setString(1, appName);
                statement.execute();
            }
        } catch (SQLException | PropertyVetoException | ClassNotFoundException ex) {
            Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void undeployApp(String appName) throws IOException {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            try (PreparedStatement statement = con.prepareStatement("UPDATE console_app SET deployed=0"
                    + " WHERE app_name=?")) {
                statement.setString(1, appName);
                statement.execute();
            }
        } catch (SQLException | PropertyVetoException | ClassNotFoundException ex) {
            Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public JSONObject deleteApp(String appName, int userId) {
        JSONObject obj = new JSONObject();
        //JSONObject config = null;
        ConfigManager backendConfig = new ConfigManager(appName);
        //config = backendConfig.getConfig();
        //remove /backend/{backend} folder
        backendConfig.deleteBackend();
                
        //String type = backendConfig.getConfig().getJSONObject("dbDetails").getString("type");
        
        if (FileUtil.deleteFile(new File(OUTPUT_FOLDER + File.separator + appName))) {
            try {
                //5 sec delay is added so that the database is closed from HikariCP pool.
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            //delete internal database
            //System.out.println("run(): "+APP_BASE + File.separator + "databases" + File.separator + appName);
            FileUtil.deleteFile(new File(APP_BASE + File.separator + "databases" + File.separator + appName));
             
            //delete resource files
            FileUtil.deleteFile(new File(Util.XML_RESOURCE_FOLDER + File.separator + appName));

            try (Connection con = ConnectionProvider.getInstance().getConnection()) {
                try (PreparedStatement statement = con.prepareStatement("DELETE FROM console_app WHERE app_name=? AND user_id=?")) {
                    statement.setString(1, appName);
                    statement.setInt(2, userId);
                    statement.execute();
                }
                try (PreparedStatement statement = con.prepareStatement("DELETE FROM console_file WHERE app_name=? AND user_id=?")) {
                    statement.setString(1, appName);
                    statement.setInt(2, userId);
                    statement.execute();
                }
                try (PreparedStatement statement = con.prepareStatement("DELETE FROM console_script_file WHERE app_name=? AND user_id=?")) {
                    statement.setString(1, appName);
                    statement.setInt(2, userId);
                    statement.execute();
                }
                try (PreparedStatement statement = con.prepareStatement("UPDATE console_user SET last_visited_app=null WHERE user_id=?")) {
                    statement.setInt(1, userId);
                    statement.execute();
                }
                try (PreparedStatement statement = con.prepareStatement("DELETE FROM console_execute_code WHERE app_name=? AND user_id=?")) {
                    statement.setString(1, appName);
                    statement.setInt(2, userId);
                    statement.execute();
                }
                try (PreparedStatement statement = con.prepareStatement("DELETE FROM console_execute_class WHERE app_name=? AND user_id=?")) {
                    statement.setString(1, appName);
                    statement.setInt(2, userId);
                    statement.execute();
                }/*
                if (!driver.contains("hsqldb")) {
                    try (PreparedStatement statement = con.prepareStatement("REVOKE ALL PRIVILEGES ON " + appName + "_db.*  FROM '" + userId + "'@'%'")) {
                        statement.execute();
                    }
                    try (PreparedStatement statement = con.prepareStatement("flush privileges")) {
                        statement.execute();
                    }
                }*/
                obj.put("message", "App has been deleted");
            } catch (SQLException | PropertyVetoException | IOException | ClassNotFoundException ex) {
                obj.put("message", "Query Error");
                obj.put("status", 409);
                Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        }
        return obj;
    }

    public boolean appExists(String appName) {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            PreparedStatement query = con.prepareStatement("SELECT app_name FROM console_app WHERE app_name=? AND deleted_on IS NULL");
            query.setString(1, appName);
            ResultSet result = query.executeQuery();
            return result.next();
        } catch (IOException | SQLException | PropertyVetoException | ClassNotFoundException ex) {
            Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }
    
    public boolean appDeployed(String appName){
        boolean deployed = false;
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            PreparedStatement query = con.prepareStatement("SELECT deployed FROM console_app WHERE app_name=? AND deleted_on IS NULL");
            query.setString(1, appName);
            ResultSet result = query.executeQuery();
            while (result.next()) {
                int d = result.getInt("deployed");
                if(d == 1) {
                    deployed = true;
                }
            }
        } catch (IOException | SQLException | PropertyVetoException | ClassNotFoundException ex) {
            Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return deployed;
    }

//    @Deprecated
//    public void deleteAppLogs(String appName) throws IOException, SQLException, PropertyVetoException, ClassNotFoundException {
//        try (Connection con = ConnectionProvider.getInstance().getConnection(); PreparedStatement statement = con.prepareStatement("DELETE FROM console_log WHERE app_name=?")) {
//            statement.setString(1, appName);
//            statement.execute();
//        }
//    }
    public boolean hasReachedThreshold(int userId) {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            String query;
            if (Util.getDbProperties().getProperty("driver").toLowerCase().trim().contains("hsqldb")) {
                query = "SELECT COUNT(cp.app_name) AS `app_count`,cu.premium FROM console_app cp INNER JOIN console_user cu ON cp.user_id=cu.user_id WHERE cp.user_id=? AND cp.deleted_on IS NULL GROUP BY cu.premium";
            } else {
                query = "SELECT COUNT(cp.app_name) AS `app_count`,cu.premium FROM console_app cp INNER JOIN console_user cu ON cp.user_id=cu.user_id WHERE cp.user_id=? AND cp.deleted_on IS NULL";
            }
            PreparedStatement appCountStmnt = con.prepareStatement(query);
            appCountStmnt.setInt(1, userId);
            ResultSet result = appCountStmnt.executeQuery();
            while (result.next()) {
                return result.getInt("app_count") <= 3 || result.getBoolean("premium");
            }
            //If while-loop isn't executed that means the user has not generated a single app thus its safe to return true.
            return true;
        } catch (IOException | SQLException | PropertyVetoException | ClassNotFoundException ex) {
            Logger.getLogger(AppDAO.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

//    @Deprecated
//    public JSONObject getErrorLogs(String appName) throws IOException, ClassNotFoundException, PropertyVetoException, SQLException {
//        JSONObject logQueryRecords = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        JSONArray columnHeader = new JSONArray();
//        columnHeader.put("error_id").put("method").put("message").put("trace").put("uri").put("created_on");
//        try (Connection con = ConnectionProvider.getBackendInstance(appName).getConnection()) {
//            try (PreparedStatement statement = con.prepareStatement("SELECT error_id,method,message,trace,resource as uri,created_on FROM error_log ORDER BY created_on DESC LIMIT 100")) {
//                try (ResultSet result = statement.executeQuery()) {
//                    while (result.next()) {
//                        JSONObject errorLogRecord = new JSONObject();
//                        errorLogRecord.put("error_id", result.getString("error_id"));
//                        errorLogRecord.put("method", result.getString("method"));
//                        errorLogRecord.put("message", result.getString("message"));
//                        errorLogRecord.put("trace", result.getString("trace"));
//                        errorLogRecord.put("uri", result.getString("uri"));
//                        errorLogRecord.put("created_on", result.getTimestamp("created_on"));
//                        jsonArray.put(errorLogRecord);
//                    }
//                }
//            }
//        }
//        logQueryRecords.put("columnHeaders", columnHeader);
//        logQueryRecords.put("data", jsonArray);
//        return logQueryRecords;
//    }
}
