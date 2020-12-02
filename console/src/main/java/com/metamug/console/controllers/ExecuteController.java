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
package com.metamug.console.controllers;

import com.metamug.console.exception.MetamugError;
import com.metamug.console.exception.MetamugException;
import com.metamug.console.services.ExecuteService;
import com.metamug.console.services.UserService;
import com.metamug.console.util.RequestUtil;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

/**
 *
 * @author Kainix
 */
@WebServlet(name = "ExecuteController", urlPatterns = {"/execute/*"})
public class ExecuteController extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        JSONObject obj = new JSONObject();
        ExecuteService executeService = new ExecuteService();
        UserService userService = new UserService();
        int userId = (int) request.getAttribute("userId");
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            boolean isVerified = userService.isVerifiedUser(userId);
            boolean isPremium = userService.isPremiumUser(userId);
            String appName = (String) request.getAttribute("appName");
            userService.validateUserApp(userId, appName);
            if (isVerified && isPremium) {
                obj = executeService.getCodeListing(userId, appName);
            } else {
                if (!isVerified) {
                    throw new MetamugException(MetamugError.UNVERIFIED_USER);
                }
                if (!isPremium) {
                    throw new MetamugException(MetamugError.NON_PREMIUM_USER);
                }
            }
        } catch (SQLException | PropertyVetoException | ClassNotFoundException ex) {
            obj.put("message", "Bad Request");
            obj.put("status", 400);
            response.setStatus(400);
            Logger.getLogger(ExecuteController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IOException ex) {
            Logger.getLogger(ExecuteController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (MetamugException ex) {
            obj.put("message", ex.getMessage());
            switch (ex.getError()) {
                case NON_PREMIUM_USER:
                case UNVERIFIED_USER:
                    obj.put("status", 403);
                    response.setStatus(403);
                    break;
                case UNAUTHORIZED_USER:
                    obj.put("status", 401);
                    response.setStatus(401);
                    break;
            }
        }
        try (ServletOutputStream out = response.getOutputStream()) {
            if (obj.toString().length() > 0) {
                out.print(obj.toString());
                out.flush();
            } else {
                response.setStatus(204);
            }
        } catch (IOException ex) {
            Logger.getLogger(ExecuteController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        JSONObject obj = new JSONObject();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ExecuteService executeService = new ExecuteService();
        UserService userService = new UserService();
        String pathInfo = request.getPathInfo();
        int userId = (Integer) request.getAttribute("userId");
        try {
            if (pathInfo != null) {
                String[] path = pathInfo.split("/");
                String appName = path[1].toLowerCase();
                boolean isVerified = userService.isVerifiedUser(userId);
                boolean isPremium = userService.isPremiumUser(userId);
                userService.validateUserApp(userId, appName);
                if (isVerified && isPremium) {
                    if (isMultipart) {
                        File uploadedFile = RequestUtil.getUploadedFile(request);
                        if (uploadedFile != null) {
                            //Check whether user is premium user
                            //Build and deploy maven project
                            boolean isBuilt = executeService.buildAndDeployMavenProject(userId, uploadedFile, appName);
                            if (isBuilt) {
                                // Give a message that upload is done
                                obj.put("message", "Your Project has been uploaded");
                                response.setStatus(201);
                            } else {
                                obj.put("message", "Check whether the interface are implemented correctly");
                                response.setStatus(422);
                            }

                            //Delete the temporary file
                            uploadedFile.delete();
                        }
                    } else {
                        obj.put("message", "Preconditions Failed.Check Requests parameter list");
                        obj.put("status", 412);
                        response.setStatus(412);
                    }
                } else {
                    if (!isVerified) {
                        throw new MetamugException(MetamugError.UNVERIFIED_USER);
                    }
                    if (!isPremium) {
                        throw new MetamugException(MetamugError.NON_PREMIUM_USER);
                    }
                }
            } else {
                obj.put("message", "Preconditions Failed.Check Requests parameter list");
                obj.put("status", 412);
                response.setStatus(412);
            }
        } catch (MetamugException ex) {
            obj.put("message", ex.getMessage());
            switch (ex.getError()) {
                case CLASS_NAME_EXISTS:
                    obj.put("status", 409);
                    response.setStatus(409);
                    break;
                case MAVEN_BUILD_FAILED:
                    obj.put("status", 500);
                    response.setStatus(500);
                    break;
                case MAVEN_DEPRECATED_DEPENDENCY_VERSION:
                    obj.put("status", 426);
                    response.setStatus(426);
                    break;
                case MAVEN_MISSING_DEPENDENCY:
                    obj.put("status", 424);
                    response.setStatus(424);
                    break;
                case UPLOAD_LISTENER_EXISTS:
                    obj.put("status", 409);
                    response.setStatus(409);
                    break;
                case NON_PREMIUM_USER:
                case UNVERIFIED_USER:
                    obj.put("status", 403);
                    response.setStatus(403);
                    break;
                case UNAUTHORIZED_USER:
                    obj.put("status", 401);
                    response.setStatus(401);
                    break;
            }
            Logger.getLogger(ExecuteController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (FileUploadException ex) {
            obj.put("message", "Zip File can't be uploaded.Try again");
            obj.put("status", 502);
            response.setStatus(502);
        } catch (FileNotFoundException ex) {
            obj.put("message", ex.getMessage());
            obj.put("status", 422);
            response.setStatus(422);
        } catch (NullPointerException ex) {
            obj.put("message", "Error in building project.");
            obj.put("status", 422);
            response.setStatus(422);
            Logger.getLogger(ExecuteController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (ZipException ex) {
            obj.put("message", "Zip File corrupted.Try again");
            obj.put("status", 422);
            response.setStatus(422);
        } catch (SQLException | PropertyVetoException | ClassNotFoundException ex) {
            obj.put("message", "Service currently unavailable");
            obj.put("status", 503);
            response.setStatus(503);
            Logger.getLogger(ExecuteController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IOException ex) {
            obj.put("message", "File can't be uploaded.Try again");
            obj.put("status", 422);
            response.setStatus(422);
        } catch (Exception ex) {
            obj.put("message", "File can't be uploaded.Try again");
            obj.put("status", 422);
            response.setStatus(422);
            Logger.getLogger(ExecuteController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        try (ServletOutputStream out = response.getOutputStream()) {
            out.print(obj.toString());
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(ExecuteController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ExecuteService executeService = new ExecuteService();
        UserService userService = new UserService();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject obj = new JSONObject();
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo != null) {
                String[] path = pathInfo.split("/");
                String appName = (String) request.getAttribute("appName");
                int userId = (Integer) request.getAttribute("userId");
                boolean isVerified = userService.isVerifiedUser(userId);
                boolean isPremium = userService.isPremiumUser(userId);
                userService.validateUserApp(userId, appName);
                if (isVerified && isPremium) {
                    String codeName = path[1];
                    if ((appName != null && !appName.isEmpty()) && (codeName != null && !codeName.isEmpty())) {
                        JSONObject result = executeService.deleteCode(codeName, appName, userId);
                        if (result == null) {
                            obj.put("message", "Code has been deleted");
                        } else {
                            obj.put("message", result.get("message"));
                        }
                        obj.put("status", 410);
                        response.setStatus(410);
                    } else {
                        obj.put("message", "Preconditions Failed.Check Requests parameter list");
                        obj.put("status", 412);
                        response.setStatus(412);
                    }
                } else {
                    if (!isVerified) {
                        throw new MetamugException(MetamugError.UNVERIFIED_USER);
                    }
                    if (!isPremium) {
                        throw new MetamugException(MetamugError.NON_PREMIUM_USER);
                    }
                }
            }
        } catch (SQLException | PropertyVetoException | ClassNotFoundException | IOException ex) {
            obj.put("message", "Preconditions Failed.Check Requests parameter list");
            obj.put("status", 412);
            response.setStatus(412);
            Logger.getLogger(ExecuteController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (MetamugException ex) {
            obj.put("message", ex.getMessage());
            switch (ex.getError()) {
                case NON_PREMIUM_USER:
                case UNVERIFIED_USER:
                    obj.put("status", 403);
                    response.setStatus(403);
                    break;
                case UNAUTHORIZED_USER:
                    obj.put("status", 401);
                    response.setStatus(401);
                    break;
            }
        }
        try (ServletOutputStream out = response.getOutputStream()) {
            out.print(obj.toString());
            out.flush();

        } catch (IOException ex) {
            Logger.getLogger(ExecuteController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
