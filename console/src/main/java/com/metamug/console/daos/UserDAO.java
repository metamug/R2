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

import com.metamug.console.exception.MetamugError;
import com.metamug.console.exception.MetamugException;
import com.metamug.console.services.ConnectionProvider;
import com.metamug.console.util.Util;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Kainix
 */
public class UserDAO {

    public UserDAO() {
    }
/*
    public void register(String password, String emailId, String orgName, String activationCode) throws IOException, SQLException, PropertyVetoException, ClassNotFoundException {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            PreparedStatement statement = con.prepareStatement("INSERT INTO console_user (password,db_password,email_id,organization_name,activate_code,deleted_on) values(?,?,?,?,?,null)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, password);
            statement.setString(2, password);
            statement.setString(3, emailId);
            statement.setString(4, orgName);
            statement.setString(5, activationCode);
            statement.execute();
            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            int key = keys.getInt(1);
            PreparedStatement createUserStatement = con.prepareStatement("CREATE USER '" + key + "'@'%' IDENTIFIED BY '" + password + "';");
            createUserStatement.execute();
        }
    }*/

    public JSONObject getUserDetails(int userId, String activeToken) throws IOException, SQLException, PropertyVetoException, ClassNotFoundException {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            JSONObject obj = new JSONObject();
            if (userId != 0) {
                try (PreparedStatement statement = con.prepareStatement("SELECT email_id,organization_name,verified,created_on FROM console_user WHERE user_id=? AND deleted_on IS NULL")) {
                    statement.setInt(1, userId);
                    try (ResultSet result = statement.executeQuery()) {
                        while (result.next()) {
                            obj.put("email_id", result.getString("email_id"));
                            obj.put("organization_name", result.getString("organization_name"));
                            obj.put("created", result.getTimestamp("created_on"));
                            obj.put("verified", result.getBoolean("verified") == Boolean.TRUE ? "true" : "false");
                        }
                    }
                    String query;
                    if (Util.getDbProperties().getProperty("driver").toLowerCase().contains("hsqldb")) {
                        query = "SELECT ip,device_type,created_on FROM console_auth_user WHERE user_id=? AND auth_token<>? AND deleted_on IS NULL";
                    } else {
                        query = "SELECT inet6_ntoa(ip) as ip,device_type,created_on FROM console_auth_user WHERE user_id=? AND auth_token<>? AND deleted_on IS NULL";
                    }
                    try (PreparedStatement stmnt = con.prepareStatement(query)) {
                        stmnt.setInt(1, userId);
                        stmnt.setString(2, activeToken);
                        JSONArray array = new JSONArray();
                        try (ResultSet result = stmnt.executeQuery()) {
                            while (result.next()) {
                                JSONObject deviceData = new JSONObject();
                                deviceData.put("ip", result.getString("ip"));
                                deviceData.put("device_type", result.getString("device_type"));
                                deviceData.put("created_on", result.getTimestamp("created_on"));
                                array.put(deviceData);
                            }
                        }
                        obj.put("active_sessions", array);
                    }
                }
            }
            return obj;
        }
    }

    public JSONObject getUser(int userId) throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            JSONObject obj = new JSONObject();
            if (userId != 0) {
                try (PreparedStatement statement = con.prepareStatement("SELECT db_password,verified,premium from console_user WHERE user_id=?")) {
                    statement.setInt(1, userId);
                    try (ResultSet result = statement.executeQuery()) {
                        while (result.next()) {
                            obj.put("user_id", String.valueOf(userId));
                            obj.put("password", result.getString("db_password").trim());
                            obj.put("verified", result.getBoolean("verified"));
                            obj.put("is_premium", result.getBoolean("premium"));
                        }
                    }
                }
            }
            return obj;
        }
    }
    
    public String getLastVisitedApp(int userId) {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            if (userId != 0) {
                try (PreparedStatement statement = con.prepareStatement("SELECT last_visited_app from console_user WHERE user_id=?"
                        + " AND last_visited_app IS NOT NULL")) {
                    statement.setInt(1, userId);
                    try (ResultSet result = statement.executeQuery()) {
                        while (result.next()) {
                            //obj.put("last_visited_app", result.getString("last_visited_app"));
                            return result.getString("last_visited_app");
                        }
                    }
                }
            }
            return null;
        } catch (IOException | SQLException | PropertyVetoException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void deleteUser(int userId) throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            if (userId != 0) {
                try (PreparedStatement statement = con.prepareStatement("DELETE FROM console_user WHERE user_id=?")) {
                    statement.setInt(1, userId);
                    statement.execute();
                }
                try (PreparedStatement dropUserStatement = con.prepareStatement("DROP USER '" + userId + "'@'%';")) {
                    dropUserStatement.execute();
                }
                try (PreparedStatement statement = con.prepareStatement("flush privileges")) {
                    statement.execute();
                }
                try (PreparedStatement stmnt = con.prepareStatement("DELETE FROM console_auth_user WHERE user_id=?")) {
                    stmnt.setInt(1, userId);
                    stmnt.execute();
                }
            }
        }
    }

    public boolean userExists(String emailId) {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            PreparedStatement query = con.prepareStatement("SELECT email_id FROM console_user WHERE lower(email_id)=? AND deleted_on IS NULL");
            query.setString(1, emailId);
            ResultSet result = query.executeQuery();
            return result.next();
        } catch (IOException | SQLException | PropertyVetoException | ClassNotFoundException ex) {
            return false;
        }
    }

    public boolean verifyAccount(String emailId, String activationCode) {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            PreparedStatement query = con.prepareStatement("UPDATE console_user SET verified=true WHERE activate_code=? AND email_id=? AND verified=false AND deleted_on IS NULL");
            query.setString(1, activationCode);
            query.setString(2, emailId);
            return query.executeUpdate() > 0;
        } catch (IOException | SQLException | PropertyVetoException | ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, ex.getMessage());
            return false;
        }
    }

    public String getActivationCode(String emailId) throws IOException, SQLException, PropertyVetoException, ClassNotFoundException {
        String activationCode = "";
        try (Connection con = ConnectionProvider.getInstance().getConnection(); PreparedStatement query = con.prepareStatement("SELECT activate_code FROM console_user WHERE email_id=? AND deleted_on IS NULL")) {
            query.setString(1, emailId);
            try (ResultSet result = query.executeQuery()) {
                if (result.next()) {
                    activationCode = result.getString("activate_code");
                }
            }
        }
        return activationCode;
    }

    public long validateUserApp(int userId, String appName) throws IOException, SQLException, PropertyVetoException, ClassNotFoundException, MetamugException {
        long appId = 0;
        try (Connection con = ConnectionProvider.getInstance().getConnection(); PreparedStatement statement = con.prepareStatement("SELECT app_id FROM console_app WHERE app_name=? AND user_id=? AND deleted_on IS NULL")) {
            statement.setString(1, appName);
            statement.setInt(2, userId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    appId = result.getInt("app_id");
                }
            }
        }
        if (appId == 0) {
            throw new MetamugException(MetamugError.UNAUTHORIZED_USER);
        }
        return appId;
    }

}
