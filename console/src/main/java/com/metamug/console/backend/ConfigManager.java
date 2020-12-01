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

import com.metamug.console.services.ConnectionProvider;
import static com.metamug.console.services.PropertiesService.ALLOWED_ORIGINS;
import com.metamug.console.util.FileUtil;
import com.metamug.console.util.Util;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xml.sax.SAXException;

/**
 *
 * @author anishhirlekar
 */
public class ConfigManager {
    
    public static final String KEY_EXT_DS = "externalDatasources"; 
    public static final String KEY_DB_DETAILS = "dbDetails";
    public static final String KEY_PROPERTIES = "properties";
    
    private final String appName;
    
    public ConfigManager(String appName) {
        this.appName = appName;
    }
    
    /**
     * @return details of database used by the backend
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws java.io.IOException
     * @throws org.xml.sax.SAXException
     */
    public JSONObject getDbDetails() throws ParserConfigurationException, IOException, SAXException {
        JSONObject config = getConfig();
       
        JSONObject dbDetails = config.getJSONObject(KEY_DB_DETAILS);
        
        JSONObject details = new JSONObject();
        details.put("app_db_user", dbDetails.getString("user"));
        details.put("app_db_pw", dbDetails.getString("password"));
        details.put("app_db_type", dbDetails.getString("type"));
        details.put("app_db_url", dbDetails.getString("url"));

        return details;
    }
    
    public void addProperty(String name, String value) {
        try {
            JSONObject config = getConfig();
            if(!config.has(KEY_PROPERTIES)){
                config.put(KEY_PROPERTIES, new JSONObject());
            }
            
            JSONObject properties = config.getJSONObject(KEY_PROPERTIES);
            properties.put(name, value);
            saveConfig(config);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void removeProperty(String name){
        try {
            JSONObject config = getConfig();
            if(config.has(KEY_PROPERTIES)){
                JSONObject properties = config.getJSONObject(KEY_PROPERTIES);
                if(properties.has(name)){
                    properties.remove(name);
                    saveConfig(config);
                }
            }           
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    public void removeExternalDatasource(String dsName) throws FileNotFoundException, IOException{
        JSONObject config = getConfig();
        if(config.has(KEY_EXT_DS)){
            JSONArray externalDatasources = config.getJSONArray(KEY_EXT_DS);
            for(int i=0;i<externalDatasources.length();i++){
                JSONObject ds = externalDatasources.getJSONObject(i);
                if(ds.getString("name").equals(dsName)){
                    //if external datasource with given name exists, remove it
                    externalDatasources.remove(i);
                    saveConfig(config);
                }
            }
        }
    }
    
    public void removeAllExternalDatasources() throws FileNotFoundException{
        JSONObject config = getConfig();
        if(config.has(KEY_EXT_DS)){
            config.remove(KEY_EXT_DS);
        }
    }
    
    /**
     * Add external datasource to config
     * 
     * @param user database user
     * @param password database password
     * @param type database type
     * @param url database connection url
     * @param dsName datasource name
     * @return boolean true if datasource added successfully, false if not
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public boolean addExternalDatasource(String user, String password, String type, String url, String dsName) throws FileNotFoundException, IOException{
        JSONObject config = getConfig();
        
        if(!config.has(KEY_EXT_DS)){
            config.put(KEY_EXT_DS, new JSONArray());
        }
         
        JSONArray externalDatasources = config.getJSONArray(KEY_EXT_DS);
        
        for(int i=0;i<externalDatasources.length();i++){
            JSONObject ds = externalDatasources.getJSONObject(i);
            if(ds.getString("name").equals(dsName)){
                //if external datasource with given name already exists, return false
                return false;
            }
        }
        
        JSONObject datasource = createDatasourceObject(user, password, type, url, dsName);
        if(testConnection(datasource)){
            externalDatasources.put(datasource);
            saveConfig(config);
            return true;
        }
        return false;
    }
    
    public JSONObject getDatasources() throws FileNotFoundException{
        JSONObject data = new JSONObject();
        data.put("backend", appName);
        
        JSONArray datasources = new JSONArray();
        
        JSONObject config = getConfig();
             
        if(config.has(KEY_EXT_DS)){
            datasources = config.getJSONArray(KEY_EXT_DS);         
        }
         
        JSONObject dbDetails = config.getJSONObject(KEY_DB_DETAILS);
        datasources.put(dbDetails);
              
        data.put("datasources",datasources);
        
        return data;
    }
    
    public void create(Map<String,String> appDetails, Map<String,String> dbDetails, int userId) {
        File configFile = new File(Util.BACKEND_FOLDER + File.separator + appName + File.separator + "config.json");
        
        try {
            if(!configFile.exists()){
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            }
            JSONObject config = new JSONObject();
            JSONObject db = new JSONObject();

            config.put("name", appName);
            config.put("version", appDetails.get("version"));
            config.put("description", appDetails.get("description"));
            config.put("userId",userId);

            //database details
            db.put("url",dbDetails.get("dbUrl"));
            db.put("type",dbDetails.get("dbType"));
            db.put("user",dbDetails.get("dbUser"));
            db.put("password",dbDetails.get("dbPass"));
            config.put(KEY_DB_DETAILS, db);
            
            //properties
            JSONObject props = new JSONObject();
            props.put(ALLOWED_ORIGINS, "*");
            config.put(KEY_PROPERTIES, props);
            
            configFile.createNewFile();
            saveConfig(config);
        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void saveConfig(JSONObject config) throws IOException {
        File configFile = new File(Util.BACKEND_FOLDER + File.separator + appName + File.separator + "config.json");
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(config.toString(4));
        }
    }
    
    private JSONObject createDatasourceObject(String user, String password, String type, String url, String dsName){
        JSONObject obj = new JSONObject();
        obj.put("user", user);
        obj.put("password", password);
        obj.put("type", type);
        obj.put("url", url);
        obj.put("name",dsName);
        return obj;
    }
   
    public JSONObject getConfig() throws FileNotFoundException{
        File configFile = new File(Util.BACKEND_FOLDER + File.separator + appName + File.separator + "config.json");
        InputStream is = new FileInputStream(configFile);
        JSONTokener tokener = new JSONTokener(is);
        return new JSONObject(tokener);
    } 
    
    public void deleteBackend(){
        FileUtil.deleteFile(new File(Util.BACKEND_FOLDER + File.separator + appName));
    }
    
    /**
     * Test database connection
     *
     * @param dbType database type - Mysql, postgresql, sql server etc
     * @param dbUrl db connection url
     * @param dbUser db username
     * @param dbPass db password
     * @return true if connection test successful
     */
    public static boolean testConnection(String dbType,String dbUrl,String dbUser,String dbPass){
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
                Logger.getLogger(ConfigManager.class.getName()).log(Level.INFO, "Database name not found in DB url.");
            }
            return null != dbname;
        } catch (SQLException | IOException | PropertyVetoException | ClassNotFoundException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.INFO, ex.getLocalizedMessage(), ex);
            return false;
        }
    }
    
    /**
     * Test external database connection
     *
     * @param datasource
     * @return true if connection test successful
     */
    public static boolean testConnection(JSONObject datasource) {
        String dbType = datasource.getString("type");
        String dbUrl = datasource.getString("url");
        String dbUser = datasource.getString("user");
        String dbPass = datasource.getString("password");
        
        return testConnection(dbType, dbUrl, dbUser, dbPass);
    }
}
