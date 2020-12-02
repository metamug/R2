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

import com.metamug.console.services.ConnectionProvider;
import com.metamug.console.util.FileUtil;
import com.metamug.console.util.Util;
import static com.metamug.console.util.Util.REMOVE_BAT;
import static com.metamug.console.util.Util.REMOVE_SH;
import java.beans.PropertyVetoException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Kaisteel
 */
public class ExecuteDAO {

    public JSONObject getCodeListing(int userId, String appName) throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
        JSONObject obj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT code_name,code_file_size,uploaded_on FROM console_execute_code WHERE app_name=? AND user_id=? AND deleted_on IS null");
            statement.setString(1, appName);
            statement.setInt(2, userId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    JSONObject file = new JSONObject();
                    file.put("name", result.getString("code_name"));
                    file.put("size", result.getString("code_file_size"));
                    file.put("created", result.getTimestamp("uploaded_on"));
                    try (PreparedStatement stmnt = con.prepareStatement("SELECT class_name,class_type FROM console_execute_class WHERE code_name=? AND user_id=? AND app_name=? AND deleted_on IS NULL")) {
                        stmnt.setString(1, result.getString("code_name"));
                        stmnt.setInt(2, userId);
                        stmnt.setString(3, appName);
                        if (stmnt.execute()) {
                            try (ResultSet resultSet = stmnt.getResultSet()) {
                                while (resultSet.next()) {
                                    file.accumulate("class", resultSet.getString("class_name") + "-" + resultSet.getString("class_type"));
                                }
                            }
                        }
                    }
                    jsonArray.put(file);
                }
            }
        }
        return obj.put("data", jsonArray);
    }

    public void addCode(int userId, String appName, String mvnProjectName, String jarFileName, String fileSize) throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
        try (Connection con = ConnectionProvider.getInstance().getConnection(); PreparedStatement stmnt = con.prepareStatement("SELECT code_name FROM console_execute_code WHERE code_name=? AND app_name=? AND code_file_name=? AND user_id=? AND deleted_on IS NULL")) {
            stmnt.setString(1, mvnProjectName);
            stmnt.setString(2, appName);
            stmnt.setString(3, jarFileName);
            stmnt.setInt(4, userId);
            try (ResultSet result = stmnt.executeQuery()) {
                if (result.next()) {
                    try (PreparedStatement stmt = con.prepareStatement("UPDATE console_execute_code SET uploaded_on=now(),code_file_size=? WHERE code_name=? AND app_name=? AND code_file_name=? AND user_id=? AND deleted_on IS NULL")) {
                        stmt.setString(1, fileSize);
                        stmt.setString(2, mvnProjectName);
                        stmt.setString(3, appName);
                        stmt.setString(4, jarFileName);
                        stmt.setInt(5, userId);
                        stmt.executeUpdate();
                    }
                } else {
                    try (PreparedStatement statement = con.prepareStatement("INSERT INTO console_execute_code (code_name,code_file_name,code_file_size,app_name,user_id,uploaded_on) VALUES(?,?,?,?,?,now())")) {
                        statement.setString(1, mvnProjectName);
                        statement.setString(2, jarFileName);
                        statement.setString(3, fileSize);
                        statement.setString(4, appName);
                        statement.setInt(5, userId);
                        statement.execute();
                    }
                }
            }
        }
    }

    public void addClassNames(String className, String classType, String appName, String codeName, int userId) throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            try (PreparedStatement stmnt = con.prepareStatement("SELECT class_name FROM console_execute_class WHERE class_name=? AND app_name=? AND code_name=? AND user_id=? AND deleted_on IS NULL")) {
                stmnt.setString(1, className);
                stmnt.setString(2, appName);
                stmnt.setString(3, codeName);
                stmnt.setInt(4, userId);
                try (ResultSet result = stmnt.executeQuery()) {
                    if (result.next()) {
                        try (PreparedStatement stmt = con.prepareStatement("UPDATE console_execute_class SET added_on=now() WHERE class_name=? AND app_name=? AND code_name=? AND user_id=? AND deleted_on IS NULL")) {
                            stmt.setString(1, className);
                            stmt.setString(2, appName);
                            stmt.setString(3, codeName);
                            stmt.setInt(4, userId);
                            stmt.executeUpdate();
                        }
                    } else {
                        try (PreparedStatement statement = con.prepareStatement("INSERT INTO console_execute_class (code_name,user_id,class_name,class_type,app_name) VALUES(?,?,?,?,?)")) {
                            statement.setString(1, codeName);
                            statement.setInt(2, userId);
                            statement.setString(3, className);
                            statement.setString(4, classType);
                            statement.setString(5, appName);
                            statement.execute();
                        }
                    }
                }
            }
        }
    }

    public boolean classExists(String className, String projectName, String appName, int userId) throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
        try (Connection con = ConnectionProvider.getInstance().getConnection(); PreparedStatement statement = con.prepareStatement("SELECT class_name FROM console_execute_class WHERE app_name=? AND class_name=? AND code_name!=? AND user_id=? AND deleted_on IS NULL")) {
            statement.setString(1, appName);
            statement.setString(2, className);
            statement.setString(3, projectName);
            statement.setInt(4, userId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    if (result.getString("class_name").equalsIgnoreCase(className)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean classExists(String appName, int userId, String classType) throws IOException, SQLException, ClassNotFoundException, PropertyVetoException {
        try (Connection con = ConnectionProvider.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement("SELECT count(class_name) as cnt FROM console_execute_class WHERE class_type=? AND app_name=? AND user_id=? AND deleted_on IS NULL")) {
            ps.setString(1, classType);
            ps.setString(2, appName);
            ps.setInt(3, userId);
            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    if (result.getInt("cnt") > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public JSONObject deleteCode(String codeName, String appName, int userId) throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
        JSONObject obj = null;
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            try (PreparedStatement statement = con.prepareStatement("SELECT code_file_name FROM console_execute_code WHERE code_name=? AND app_name=? AND user_id=? AND deleted_on is null")) {
                statement.setString(1, codeName);
                statement.setString(2, appName);
                statement.setInt(3, userId);
                try (ResultSet result = statement.executeQuery()) {
                    if (!result.next()) {
                        obj = new JSONObject();
                        obj.put("message", "Code already deleted");
                    } else {
                        /*String codeFile = OUTPUT_FOLDER + File.separator + appName + File.separator + "WEB-INF" + 
                                File.separator+"lib"+File.separator+result.getString("code_file_name");
                        removeFile(codeFile);*/

                        //if (ResourceFileService.deleteFile(codeFile)) {
                        String pluginFolder = Util.APP_BASE + File.separator + "downloads" + File.separator
                                + "plugins" + File.separator + appName + File.separator + codeName;
                        FileUtil.deleteFile(new File(pluginFolder));

                        try (PreparedStatement stmt = con.prepareStatement("UPDATE console_execute_code SET deleted_on=now() WHERE code_file_name=? AND app_name=? AND user_id=?")) {
                            stmt.setString(1, result.getString("code_file_name"));
                            stmt.setString(2, appName);
                            stmt.setInt(3, userId);
                            stmt.executeUpdate();
                        }
                        try (PreparedStatement stmt = con.prepareStatement("UPDATE console_execute_class SET deleted_on=now() WHERE code_name=? AND app_name=? AND user_id=?")) {
                            stmt.setString(1, codeName);
                            stmt.setString(2, appName);
                            stmt.setInt(3, userId);
                            stmt.executeUpdate();
                        }
                        /*} else {
                            obj = new JSONObject();
                            obj.put("message", "Code can't be deleted");
                        }*/
                    }
                }
            }
        }
        return obj;
    }

    public static void removeFile(String filepath) throws IOException {
        File removeShFile = new File(REMOVE_SH);
        if (!removeShFile.exists()) {
            removeShFile.createNewFile();
        }
        File removeBatFile = new File(REMOVE_BAT);
        if (!removeBatFile.exists()) {
            removeBatFile.createNewFile();
        }

        String delScript = "\ndel /Q /F " + filepath.replace(File.separator, "\\");
        try (Writer w = new BufferedWriter(new FileWriter(REMOVE_BAT, true))) {
            w.write(delScript);
        }

        String rmScript = "\nrm -rf " + filepath.replace(File.separator, "/");
        try (Writer w = new BufferedWriter(new FileWriter(REMOVE_SH, true))) {
            w.write(rmScript);
        }
    }
}
