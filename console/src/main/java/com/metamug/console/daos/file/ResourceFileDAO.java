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
package com.metamug.console.daos.file;

import com.metamug.console.services.ConnectionProvider;
import com.metamug.console.util.FileUtil;
import com.metamug.console.util.Util;
import static com.metamug.console.util.Util.OUTPUT_FOLDER;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Kainix
 */
public class ResourceFileDAO {

    public ResourceFileDAO() {
    }

    public JSONObject listing(int userId, String appName) throws IOException, SQLException, PropertyVetoException, ClassNotFoundException {
        JSONObject obj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT file_name,file_size,file_version,"
                    + "deployed,secure,file_auth,tag,"
                    + " uploaded_on FROM console_file WHERE app_name=? AND user_id=? AND deleted_on IS NULL"
                    + " ORDER BY uploaded_on DESC");
            statement.setString(1, appName);
            statement.setInt(2, userId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    JSONObject file = new JSONObject();
                    file.put("name", result.getString("file_name"));
                    file.put("size", result.getString("file_size"));
                    file.put("version", result.getString("file_version"));
                    file.put("deployed", result.getBoolean("deployed"));
                    file.put("secure", result.getBoolean("secure"));
                    file.put("auth", result.getString("file_auth"));
                    file.put("tag", result.getString("tag"));
                    file.put("created", result.getTimestamp("uploaded_on"));
                    jsonArray.put(file);
                }
            }
        }
        return obj.put("data", jsonArray);
    }

    public boolean addFile(int userId, String appName, String fileName, 
            String fileSize, JSONObject props, Boolean deploy) throws IOException, SQLException, PropertyVetoException, ClassNotFoundException {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            String version = "v"+props.getString("version");
            boolean isSecure = props.getBoolean("secure");
            String auth = props.has("auth") ? props.getString("auth") : null;
            JSONObject tag = props.has("tag") ? props.getJSONObject("tag") : null;
            String tagJson = null;
            
            //Check whether the file exists for the same app, same version and created by same user
            try (PreparedStatement stmt = con.prepareStatement("SELECT file_name FROM console_file WHERE file_name=?"
                    + " AND file_version=? AND app_name=? AND user_id=? AND deleted_on IS NULL")) {
                stmt.setString(1, fileName);
                stmt.setString(2, version);
                stmt.setString(3, appName);
                stmt.setInt(4, userId);
                ResultSet result = stmt.executeQuery();
                if (result.next()) {
                    //file exists, so update file data
                    if(deploy != null && deploy) {
                        //if deploy=true, set deploy flag
                        try (PreparedStatement statement = con.prepareStatement("UPDATE console_file SET deployed=1 "
                                    + "WHERE file_name=? AND file_version=? AND app_name=? AND user_id=?")) {
                            statement.setString(1, fileName);
                            statement.setString(2, version);
                            statement.setString(3, appName);
                            statement.setInt(4, userId);
                            statement.execute();
                        }
                    } else {
                        // update file data
                        try (PreparedStatement stmnt = con.prepareStatement("UPDATE console_file SET uploaded_on=now(),"
                                + " secure=?, file_auth=?, tag=? WHERE file_name=? AND file_version=? AND app_name=? AND user_id=? AND"
                                + " deleted_on IS NULL")) {
                            stmnt.setBoolean(1, isSecure);
                            if (auth != null) {
                                stmnt.setString(2, auth);
                            } else {
                                stmnt.setNull(2, java.sql.Types.VARCHAR);
                            }
                            if(tag != null){
                                stmnt.setString(3, tagJson);
                            } else {
                                stmnt.setNull(3, java.sql.Types.VARCHAR);
                            }
                            stmnt.setString(4, fileName);
                            stmnt.setString(5, version);
                            stmnt.setString(6, appName);
                            stmnt.setInt(7, userId);
                            stmnt.executeUpdate();
                        }
                    }
                    return true;
                } else {
                    //file does not exist, insert file data
                    PreparedStatement statement = con.prepareStatement("INSERT INTO console_file (file_hash,file_name,"
                            + " file_size,file_version,secure,file_auth,tag,app_name,user_id,deleted_on) values(?,?,?,?,?,?,?,?,?,null)");
                    String hash = new BigInteger(256, new Random()).toString(16);
                    statement.setString(1, hash);
                    statement.setString(2, fileName);
                    statement.setString(3, fileSize);
                    statement.setString(4, version);
                    statement.setBoolean(5, isSecure);
                    if (auth != null) {
                        statement.setString(6, auth);
                    } else {
                        statement.setNull(6, java.sql.Types.VARCHAR);
                    }
                    if(tag != null){
                        statement.setString(7, tagJson);
                    } else {
                        statement.setNull(7, java.sql.Types.VARCHAR);
                    }
                    statement.setString(8, appName);
                    statement.setInt(9, userId);
                    statement.execute();

                    //update backend resources count
                    statement = con.prepareStatement("UPDATE console_app SET app_resources=app_resources+1 WHERE"
                            + " app_name=? AND user_id=? AND deleted_on IS NULL");
                    statement.setString(1, appName);
                    statement.setInt(2, userId);
                    statement.execute();
                    statement.close();
                    return false;
                }
            }
        }
    }

    public JSONObject downloadFile(int userId, String appName, String fileName, String version) throws IOException, SQLException, PropertyVetoException, ClassNotFoundException {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            JSONObject obj;
            try (PreparedStatement statement = con.prepareStatement("SELECT file_hash FROM console_file WHERE"
                    + " file_name=? AND file_version=? AND app_name=? AND user_id=? AND deleted_on IS NULL")) {
                statement.setString(1, fileName);
                statement.setString(2, version);
                statement.setString(3, appName);
                statement.setInt(4, userId);
                try (ResultSet result = statement.executeQuery()) {
                    String fileHash = null;
                    if (result.next()) {
                        fileHash = result.getString("file_hash");
                    }
                    obj = new JSONObject();
                    if (fileHash != null) {
                        obj.put("hash", fileHash);
                        obj.put("name", fileName);
                    } else {
                        obj.put("message", "File doesn't not exists");
                    }
                }
            }
            return obj;
        }
    }
    
    public JSONObject deleteFile(String version, String fileName, String appName, int userId, String domain, String type) throws IOException, SQLException, PropertyVetoException, ClassNotFoundException {
        JSONObject obj = null;
        File resourceFile = new File(OUTPUT_FOLDER + File.separator + appName + 
            File.separator + "WEB-INF"+File.separator +"resources"+File.separator + version + File.separator
                + fileName + ".jsp");
        
        //delete jsp
        if (FileUtil.deleteFile(resourceFile)) {
            if(type == null || type.equals("all")){
                //delete file doc and xml
                resourceFile = new File(OUTPUT_FOLDER + File.separator + appName + File.separator + "docs" +
                        File.separator + version + File.separator + fileName + ".html");
                if (FileUtil.deleteFile(resourceFile)) {
                    File xmlFile = new File(Util.XML_RESOURCE_FOLDER + File.separator + appName + File.separator + version + File.separator + fileName + ".xml");
                    xmlFile.delete();
                    try (Connection con = ConnectionProvider.getInstance().getConnection()) {
                        try (PreparedStatement statement = con.prepareStatement("UPDATE console_app SET app_resources=app_resources-1 WHERE app_name=? AND user_id=? AND deleted_on IS NULL")) {
                            statement.setString(1, appName);
                            statement.setInt(2, userId);
                            statement.execute();
                        }
                        try (PreparedStatement statement = con.prepareStatement("UPDATE console_file SET deleted_on=now()"
                                + " WHERE file_name=? AND file_version=? AND app_name=? AND user_id=?")) {
                            statement.setString(1, fileName);
                            statement.setString(2, version);
                            statement.setString(3, appName);
                            statement.setInt(4, userId);
                            statement.executeUpdate();
                        }
                    }
                    return obj;
                } else {
                    obj = new JSONObject();
                    obj.put("message", "File couldn't be deleted");
                }
            } else if(type.equals("jsp")){
               
                try (Connection con = ConnectionProvider.getInstance().getConnection()) {
                    try (PreparedStatement statement = con.prepareStatement("UPDATE console_file SET deployed=0 "
                            + "WHERE file_name=? AND file_version=? AND app_name=? AND user_id=?")) {
                        statement.setString(1, fileName);
                        statement.setString(2, version);
                        statement.setString(3, appName);
                        statement.setInt(4, userId);
                        statement.execute();
                    }
                }
            }
        } else {
            obj = new JSONObject();
            obj.put("message", "File couldn't be deleted");
        }
        return obj;
    }

    /*public void updateFile(String appName, String fileName, String version, int userId) throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            try (PreparedStatement statement = con.prepareStatement("UPDATE console_file SET uploaded_on=now() WHERE"
                    + " file_name=? AND file_version=? AND app_name=? AND user_id=? AND deleted_on IS NULL")) {
                statement.setString(1, fileName);
                statement.setString(2, version);
                statement.setString(3, appName);
                statement.setInt(4, userId);
                statement.executeUpdate();
            }
        }
    }*/
}
