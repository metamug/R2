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
import com.metamug.console.services.AppService;
import com.metamug.console.services.UserService;
import com.metamug.parser.exception.ResourceTestException;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

/**
 *
 * @author Kainix
 */
@WebServlet(name = "AppController", urlPatterns = {"/app/*"})
public class AppController extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String pathInfo = request.getPathInfo();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject obj = new JSONObject();
        AppService appService = new AppService();
        UserService userService = new UserService();
        
        boolean name = request.getParameter("name") == null ? false : Boolean.parseBoolean(request.getParameter("name"));
        try {
            if (pathInfo != null && pathInfo.trim().length() > 0) {
                String[] path = pathInfo.split("/");
                if (path.length > 0) {
                    request.setAttribute("appName", path[1]);
                    switch (path.length) {
                        case 5: {
                            //url: /{appName}/rpx/{version}/{resourceName}
                            RequestDispatcher rd = request.getRequestDispatcher("/" + path[2] + "/" + path[3] + "/" + path[4]);
                            rd.forward(request, response);
                            break;
                        }
                        case 4: {
                            //url: /{appName}/script/{scriptFileName}
                            RequestDispatcher rd = request.getRequestDispatcher("/" + path[2] + "/" + path[3]);
                            rd.forward(request, response);
                            break;
                        }
                        case 3: {
                            //url: /{appName}/rpx OR url: /{appName}/execute
                            //OR url: /{appName}/export OR url: /{appName}/script
                            RequestDispatcher rd = request.getRequestDispatcher("/" + path[2]);
                            rd.forward(request, response);
                            break;
                        }
                        case 2:
                            Integer userId = (Integer) request.getAttribute("userId");
                            userService.validateUserApp(userId, path[1]);
                            if (userService.isVerifiedUser(userId)) {
                                //url: /{appName}
                                obj = appService.getApp(path[1], userId, true);
                            } else {
                                throw new MetamugException(MetamugError.UNVERIFIED_USER);
                            }
                            break;
                        default:
                            obj.put("message", "Bad Request");
                            obj.put("status", 400);
                            response.setStatus(400);
                    }
                }
            } else {
                obj = appService.getApps((Integer) request.getAttribute("userId"), name);
            }
        } catch (ServletException | IOException ex) {
            obj.put("message", "Bad Request");
            obj.put("status", 400);
            response.setStatus(400);
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (SQLException | PropertyVetoException | ClassNotFoundException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (MetamugException ex) {
            obj.put("message", ex.getMessage());
            switch (ex.getError()) {
                case UNAUTHORIZED_USER:
                    obj.put("status", 401);
                    response.setStatus(401);
                    break;
                case UNVERIFIED_USER:
                    obj.put("status", 403);
                    response.setStatus(403);
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
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        AppService appService = new AppService();
        UserService userService = new UserService();
        JSONObject obj = new JSONObject();
        String pathInfo = request.getPathInfo();
        try {
            if (userService.isVerifiedUser((Integer) request.getAttribute("userId"))) {
                if (pathInfo != null) {
                    String[] path = pathInfo.split("/");
                    request.setAttribute("appName", path[1].toLowerCase());
                    userService.validateUserApp((Integer) request.getAttribute("userId"), path[1].toLowerCase());
                    //url: /app/{appName}/rpx/ OR url: /app/{appName}/script/
                    RequestDispatcher rd = request.getRequestDispatcher("/" + path[2]);
                    rd.forward(request, response);
                } else {
                    int userId = (Integer) request.getAttribute("userId");

                    String appName = request.getParameter("id").toLowerCase().replaceAll("\\s+", "");
                    String version = "v1.0";
                    String description = request.getParameter("desc");
                    String dbType = request.getParameter("dbtype") != null ? request.getParameter("dbtype") : "internal";
                    String dbUrl = request.getParameter("dburl");
                    String dbUser = request.getParameter("dbuser");
                    String dbPass = request.getParameter("dbpass");
                    
                    Map<String,String> dbDetails = new HashMap<String,String>(){{
                        put("dbType",dbType);
                        put("dbUrl",dbUrl);
                        put("dbUser",dbUser);
                        put("dbPass",dbPass);
                    }};

                    if ((appName != null && !appName.trim().isEmpty()) && (description != null && !description.trim().isEmpty())) {
                    
                        String domain = (String)request.getAttribute("domain");

                        boolean connectionSuccessful = true;
                        //test db connection if db type is NOT internal
                        if (!dbType.equals("internal")) {
                            connectionSuccessful = appService.testConnection(dbDetails);
                        }
                        if (connectionSuccessful) {
                            Map<String,String> appDetails = new HashMap<String,String>(){{
                                put("appName",appName.toLowerCase());
                                put("version",version);
                                put("description",description);
                            }};
                            
                            //Make a db generate dataSource and create app with keys and store user's app details and generate a key-pair file
                            boolean keys = appService.create(userId, appDetails, domain,dbDetails);

                            if (keys) {
                                response.setStatus(409);
                                obj.put("message", "Backend with that name already exist.");
                                obj.put("status", 409);
                            } else {
                                response.setStatus(201);
                            }
                        } else {
                            obj.put("message", "Could not connect to database! Make sure the db server is running and the db url/username/password are correct.");
                            response.setStatus(503);
                        }
                    } else {
                        obj.put("message", "Preconditions Failed. Check Request parameter list");
                        obj.put("status", 412);
                        response.setStatus(412);
                    }
                }
            } else {
                throw new MetamugException(MetamugError.UNVERIFIED_USER);
            }
        } catch (ClassNotFoundException | URISyntaxException ex) {
            obj.put("message", "Can't connect to database");
            obj.put("status", "503");
            response.setStatus(503);
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (MetamugException ex) {
            obj.put("message", ex.getMessage());
            switch (ex.getError()) {
                case APP_CREATE_LIMIT_EXCEEDED:
                    obj.put("status", 403);
                    response.setStatus(403);
                    break;
                case APP_EXISTS:
                    obj.put("status", 409);
                    response.setStatus(409);
                    break;
                case INVALID_APP_NAME:
                    obj.put("status", 409);
                    response.setStatus(409);
                    break;
                case UNAUTHORIZED_USER:
                    obj.put("status", 401);
                    response.setStatus(401);
                    break;
                case UNVERIFIED_USER:
                    obj.put("status", 403);
                    response.setStatus(403);
                    break;
            }
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (TransformerException | SAXException | ParserConfigurationException | SQLException | PropertyVetoException ex) {
            obj.put("message", "Resource file can't be uploaded.");
            obj.put("status", 422);
            response.setStatus(422);
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (ResourceTestException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (ServletOutputStream out = response.getOutputStream()) {
            out.print(obj.toString());
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int userId = (Integer) request.getAttribute("userId");
        JSONObject obj = new JSONObject();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo();
        try {
            UserService userService = new UserService();
            if (pathInfo != null) {
                String[] path = pathInfo.split("/");
                String appName = path[1];
                userService.validateUserApp(userId, appName);
                if (userService.isVerifiedUser(userId)) {
                    request.setAttribute("appName", appName);
                    switch (path.length) {
                        case 5: {
                            //url: /app/{appName}/rpx/{version}/{resourceName}
                            RequestDispatcher rd = request.getRequestDispatcher("/" + path[2] + "/" + path[3] + "/" + path[4]);
                            rd.forward(request, response);
                            break;
                        }
                        case 4: {
                            //url: /app/{appName}/script/{scriptFileName}
                            RequestDispatcher rd = request.getRequestDispatcher("/" + path[2] + "/" + path[3]);
                            rd.forward(request, response);
                            break;
                        }
                        case 3: {
                            //url: /app/{appName}/properties
                            RequestDispatcher rd = request.getRequestDispatcher("/" + path[2]);
                            rd.forward(request, response);
                            break;
                        }
                        default:
                            obj.put("message", "Bad Request");
                            obj.put("status", 400);
                            response.setStatus(400);
                    }
                } else {
                    throw new MetamugException(MetamugError.UNVERIFIED_USER);
                }
            }
        } catch (IOException | SQLException | PropertyVetoException | ClassNotFoundException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MetamugException ex) {
            obj.put("message", ex.getMessage());
            switch (ex.getError()) {
                case UNAUTHORIZED_USER:
                    obj.put("status", 401);
                    response.setStatus(401);
                    break;
                case UNVERIFIED_USER:
                    obj.put("status", 403);
                    response.setStatus(403);
                case APP_EXISTS:
                    obj.put("status", 500);
                    response.setStatus(500);
            }
        }
        try (ServletOutputStream out = response.getOutputStream()) {
            out.print(obj.toString());
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String pathInfo = request.getPathInfo();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject obj = new JSONObject();
        AppService appService = new AppService();
        UserService userService = new UserService();
        try {
            if (pathInfo != null) {
                String[] path = pathInfo.split("/");
                String appName = path[1].toLowerCase().replaceAll("\\s+", "");
                Integer userId = (Integer) request.getAttribute("userId");
                userService.validateUserApp(userId, appName);
                request.setAttribute("appName", appName);
                if (userService.isVerifiedUser(userId)) {
                    switch (path.length) {
                        case 5: //Redirecting to delete resource /app/{appName}/rpx/{version}/{resourceName}
                        {
                            RequestDispatcher rd = request.getRequestDispatcher("/" + path[2] + "/" + path[3] + "/" + path[4]);
                            rd.forward(request, response);
                            break;
                        }
                        case 4:
                            //Redirecting to delete uploaded code 
                            // url: /app/{appName}/execute/{codeName} OR /app/{appName}/script/{scriptFileName}
                            RequestDispatcher rd = request.getRequestDispatcher("/" + path[2] + "/" + path[3]);
                            rd.forward(request, response);
                            break;
                        case 2:
                            //delete an app
                            obj = appService.deleteApp(path[1], (int) request.getAttribute("userId"));
                            obj.put("message", "App deleted");
                            obj.put("status", 410);
                            response.setStatus(410);
                            break;
                        default:
                            break;
                    }
                } else {
                    throw new MetamugException(MetamugError.UNVERIFIED_USER);
                }
            } else {
                obj = new JSONObject();
                response.setStatus(412);
                obj.put("message", "Preconditions Failed.Check Requests parameter list");
                obj.put("status", 412);
            }
        } catch (IOException | SQLException | PropertyVetoException | ClassNotFoundException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (MetamugException ex) {
            obj.put("message", ex.getMessage());
            switch (ex.getError()) {
                case UNAUTHORIZED_USER:
                    obj.put("status", 401);
                    response.setStatus(401);
                    break;
                case UNVERIFIED_USER:
                    obj.put("status", 403);
                    response.setStatus(403);
            }
        }
        try (ServletOutputStream out = response.getOutputStream()) {
            out.print(obj.toString());
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
    }

    @Override
    protected void doHead(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        AppService appService = new AppService();
        if (pathInfo != null) {
            String[] path = pathInfo.split("/");
            if (appService.appExists(path[1])) {
                response.setStatus(200);
            } else {
                response.setStatus(404);
            }
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
