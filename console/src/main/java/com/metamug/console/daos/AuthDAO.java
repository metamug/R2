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

import com.metamug.console.backend.ConfigManager;
import com.metamug.console.services.ConnectionProvider;
import com.metamug.console.util.Util;
import static com.metamug.console.util.Util.SECURE_RANDOM;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

/**
 *
 * @author Kainix
 */
public class AuthDAO {

    public AuthDAO() {
    }
    
    public JSONObject getLoginData(int userId) {
        JSONObject obj = new JSONObject();
        
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT user_id,last_visited_app FROM CONSOLE_USER WHERE user_id=? AND deleted_on IS NULL")) {
                ps.setInt(1, userId);
                try (ResultSet result = ps.executeQuery()) {
                    while (result.next()) {
                        String lastVisitedApp = result.getString("last_visited_app");
                        obj.put("active_id", lastVisitedApp);
                        if (null != lastVisitedApp) {
                            ConfigManager conf = new ConfigManager(lastVisitedApp);
                            JSONObject dbDetails = conf.getDbDetails();
                            obj.put("active_id_db_type", dbDetails.get("app_db_type"));
                            
                            try(PreparedStatement ps1 = con.prepareStatement("SELECT deployed from CONSOLE_APP where app_name=? and user_id=?")){
                                ps1.setString(1, lastVisitedApp);
                                ps1.setInt(2, userId);
                                try(ResultSet rs1 = ps1.executeQuery()){
                                    while(rs1.next()){
                                        obj.put("deployed", rs1.getInt("deployed"));
                                    }
                                }
                            }
                        }
                    }
                } catch (ParserConfigurationException | SAXException ex) {
                    Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            }        
        } catch (IOException | SQLException | PropertyVetoException | ClassNotFoundException ex) {
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return obj;
    }

    public JSONObject generateToken(String emailId, String password, String ip, String deviceType) {
        JSONObject obj = new JSONObject();
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            String token = new BigInteger(128, SECURE_RANDOM).toString(16);
            try (PreparedStatement statement = con.prepareStatement("SELECT user_id,last_visited_app,password FROM CONSOLE_USER WHERE email_id=? AND deleted_on IS NULL")) {
                statement.setString(1, emailId);
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        String pswd = Util.getConfigProperties().getProperty("password").trim();
                        String lastVisitedApp = result.getString("last_visited_app");
                        int userId = result.getInt("user_id");
                        //boolean checkpw = BCrypt.checkpw(password, pswd);
                        //if (checkpw) {
                        if(password.equals(pswd)) {  
                            obj.put("token", token);
                            obj.put("active_id", lastVisitedApp);
                            if (null != lastVisitedApp) {
                                ConfigManager conf = new ConfigManager(lastVisitedApp);
                                JSONObject dbDetails = conf.getDbDetails();
                                obj.put("active_id_db_type", dbDetails.get("app_db_type"));
                            }

                            String query;
                            if (Util.getDbProperties().getProperty("driver").toLowerCase().contains("hsqldb")) {
                                query = "INSERT INTO console_auth_user (user_id,auth_token,ip,device_type,created_on,deleted_on) VALUES(?,?,?,?,now(),null)";
                            } else {
                                query = "INSERT INTO console_auth_user (user_id,auth_token,ip,device_type,created_on,deleted_on) VALUES(?,?,inet6_aton(?),?,now(),null)";
                            }
                            try (PreparedStatement stmt = con.prepareStatement(query)) {
                                stmt.setInt(1, userId);
                                stmt.setString(2, token);
                                stmt.setString(3, ip);
                                stmt.setString(4, deviceType);
                                stmt.execute();
                            }
                        }
                    }
                } catch (ParserConfigurationException | SAXException ex) {
                    Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException | SQLException | PropertyVetoException | ClassNotFoundException ex) {
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        return obj;
    }

    public JSONObject generateTokenByActivationCode(String emailId, String activateCode, String ip, String deviceType) {
        JSONObject obj = new JSONObject();
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            String token = new BigInteger(128, SECURE_RANDOM).toString(16);
            try (PreparedStatement statement = con.prepareStatement("SELECT user_id,last_visited_app FROM console_user WHERE email_id=? AND activate_code=? AND verified=true AND deleted_on IS NULL")) {
                statement.setString(1, emailId);
                statement.setString(2, activateCode);
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        String lastVisitedApp = result.getString("last_visited_app");
                        int userId = result.getInt("user_id");
                        obj.put("token", token);
                        obj.put("active_id", lastVisitedApp);
                        String query;
                        if (Util.getDbProperties().getProperty("driver").toLowerCase().contains("hsqldb")) {
                            query = "INSERT INTO console_auth_user (user_id,auth_token,ip,device_type,created_on,deleted_on) VALUES(?,?,?,?,now(),null)";
                        } else {
                            query = "INSERT INTO console_auth_user (user_id,auth_token,ip,device_type,created_on,deleted_on) VALUES(?,?,inet6_aton(?),?,now(),null)";
                        }
                        try (PreparedStatement stmt = con.prepareStatement(query)) {
                            stmt.setInt(1, userId);
                            stmt.setString(2, token);
                            stmt.setString(3, ip);
                            stmt.setString(4, deviceType);
                            stmt.execute();
                        }
                    }
                }
            }
        } catch (IOException | SQLException | PropertyVetoException | ClassNotFoundException ex) {
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        return obj;
    }

    public void deleteToken(String token) throws IOException, SQLException, PropertyVetoException {
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            PreparedStatement statement = con.prepareStatement("DELETE FROM console_auth_user WHERE auth_token=?");
            statement.setString(1, token);
            statement.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
    }

    public int authorizeToken(String token) {
        int userId = 0;
        try (Connection con = ConnectionProvider.getInstance().getConnection()) {
            PreparedStatement statement = con.prepareStatement("SELECT user_id FROM console_auth_user WHERE auth_token=? AND deleted_on IS NULL");
            statement.setString(1, token);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    userId = result.getInt("user_id");
                }
            }
        } catch (IOException | SQLException | PropertyVetoException | ClassNotFoundException ex) {
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        return userId;
    }
}
