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
 * @author anishhirlekar
 */
public class ScriptFileDAO {

    public ScriptFileDAO() {
    }

    public JSONObject listing(int userId, String appName) throws IOException, SQLException, PropertyVetoException, ClassNotFoundException {
        JSONObject obj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT script_file_name,script_file_size,"
                    + " uploaded_on FROM console_script_file WHERE app_name=? AND user_id=? AND deleted_on IS NULL"
                    + " ORDER BY uploaded_on DESC");
            statement.setString(1, appName);
            statement.setInt(2, userId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    JSONObject file = new JSONObject();
                    file.put("name", result.getString("script_file_name"));
                    file.put("size", result.getString("script_file_size"));
                    file.put("created", result.getTimestamp("uploaded_on"));
                    jsonArray.put(file);
                }
            }
        }
        return obj.put("data", jsonArray);
    }

    public boolean addFile(int userId, String appName, String fileName, String fileSize) throws IOException, SQLException, PropertyVetoException, ClassNotFoundException {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            //Check whether the file exists for the same app and created by same user
            try (PreparedStatement stmt = con.prepareStatement("SELECT script_file_name FROM console_script_file WHERE script_file_name=?"
                    + " AND app_name=? AND user_id=? AND deleted_on IS NULL")) {
                stmt.setString(1, fileName);
                stmt.setString(2, appName);
                stmt.setInt(3, userId);
                ResultSet result = stmt.executeQuery();
                if (result.next()) {
                    try (PreparedStatement stmnt = con.prepareStatement("UPDATE console_script_file SET uploaded_on=now()"
                            + " WHERE script_file_name=? AND app_name=? AND user_id=? AND deleted_on IS NULL")) {

                        stmnt.setString(1, fileName);
                        stmnt.setString(2, appName);
                        stmnt.setInt(3, userId);
                        stmnt.executeUpdate();
                    }
                    return true;
                } else {
                    PreparedStatement statement = con.prepareStatement("INSERT INTO console_script_file (script_file_hash,script_file_name,"
                            + " script_file_size,app_name,user_id,deleted_on) values(?,?,?,?,?,null)");
                    String hash = new BigInteger(256, new Random()).toString(16);
                    statement.setString(1, hash);
                    statement.setString(2, fileName);
                    statement.setString(3, fileSize);
                    statement.setString(4, appName);
                    statement.setInt(5, userId);
                    statement.execute();

                    /*statement = con.prepareStatement("UPDATE console_app SET app_resources=app_resources+1 WHERE"
                            + " app_name=? AND user_id=? AND deleted_on IS NULL");
                    statement.setString(1, appName);
                    statement.setInt(2, userId);
                    statement.execute();*/
                    statement.close();
                    return false;
                }
            }
        }
    }

    public JSONObject downloadFile(int userId, String appName, String fileName) throws IOException, SQLException, PropertyVetoException, ClassNotFoundException {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            JSONObject obj;
            try (PreparedStatement statement = con.prepareStatement("SELECT script_file_hash FROM console_script_file WHERE"
                    + " script_file_name=? AND app_name=? AND user_id=? AND deleted_on IS NULL")) {
                statement.setString(1, fileName);
                statement.setString(2, appName);
                statement.setInt(3, userId);
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

    public JSONObject deleteFile(String fileName, String appName, int userId) throws IOException, SQLException, PropertyVetoException, ClassNotFoundException {
        JSONObject obj = null;
        File file = new File(OUTPUT_FOLDER + File.separator + appName + File.separator + "WEB-INF" + File.separator + "scripts" + File.separator + fileName + ".groovy");
        if (FileUtil.deleteFile(file)) {
            try (Connection con = ConnectionProvider.getInstance().getConnection()) {
                /*try (PreparedStatement statement = con.prepareStatement("UPDATE console_app SET app_resources=app_resources-1 WHERE app_name=? AND user_id=? AND deleted_on IS NULL")) {
                    statement.setString(1, appName);
                    statement.setInt(2, userId);
                    statement.execute();
                }*/
                try (PreparedStatement statement = con.prepareStatement("UPDATE console_script_file SET deleted_on=now()"
                        + " WHERE script_file_name=? AND app_name=? AND user_id=?")) {
                    statement.setString(1, fileName);
                    statement.setString(2, appName);
                    statement.setInt(3, userId);
                    statement.executeUpdate();
                }
            }
            return obj;
        } else {
            obj = new JSONObject();
            obj.put("message", "File couldn't be deleted");
        }
        return obj;
    }

    public void updateFile(String appName, String fileName, int userId) throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            try (PreparedStatement statement = con.prepareStatement("UPDATE console_script_file SET uploaded_on=now() WHERE"
                    + " script_file_name=? AND app_name=? AND user_id=? AND deleted_on IS NULL")) {
                statement.setString(1, fileName);
                statement.setString(2, appName);
                statement.setInt(3, userId);
                statement.executeUpdate();
            }
        }
    }
}
