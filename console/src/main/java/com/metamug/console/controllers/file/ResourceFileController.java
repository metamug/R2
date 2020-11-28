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
package com.metamug.console.controllers.file;

import com.metamug.console.exception.MetamugError;
import com.metamug.console.exception.MetamugException;
import com.metamug.console.services.file.ResourceFileService;
import com.metamug.console.util.Util;
import com.metamug.parser.exception.ResourceTestException;
import com.metamug.parser.service.ParserService;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.beans.PropertyVetoException;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kainix
 */
@WebServlet(name = "ResourceFileController", urlPatterns = {"/rpx/*"})
public class ResourceFileController extends HttpServlet {

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
        ResourceFileService fileService = new ResourceFileService();
        int userId = (Integer) request.getAttribute("userId");
        //UserService userService = new UserService();
        try (ServletOutputStream out = response.getOutputStream()) {
            try {
                String pathInfo = request.getPathInfo();
                String appName = (String) request.getAttribute("appName") == null ? "" : (String) request.getAttribute("appName");
                    if (pathInfo != null) {
                        String[] path = pathInfo.split("/");
                        String version = path[1] == null ? "" : path[1].toLowerCase();
                        String fileName = path[2] == null ? "" : path[2];
                        if (path.length == 3) {
                            if (!appName.trim().isEmpty() && !fileName.trim().isEmpty() && !version.trim().isEmpty()) {
                                File file = new File(Util.XML_RESOURCE_FOLDER + File.separator + appName + File.separator + version + File.separator + fileName + ".xml");
                                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\".xml");
                                response.setContentType("application/octet-stream");
                                response.setContentLength((int) file.length());
                                try (FileInputStream fileIn = new FileInputStream(file)) {
                                    byte[] outputByte = new byte[1024];
                                    //copy binary content to output stream
                                    int data = 0;
                                    while ((data = fileIn.read(outputByte, 0, 1024)) != -1) {
                                        out.write(outputByte, 0, data);
                                    }
                                }
                            }
                        }
                    } else if (!appName.trim().isEmpty()) {
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        obj = fileService.listing(userId, appName.trim());
                        out.print(obj.toString());
                    }
            } catch (IOException | SQLException | PropertyVetoException | ClassNotFoundException ex) {
                obj.put("message", "File can't be uploaded.");
                obj.put("status", 422);
                response.setStatus(422);
                Logger.getLogger(ResourceFileController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
                out.print(obj.toString());
            }
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(ResourceFileController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
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
        JSONObject obj = new JSONObject();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ResourceFileService fileService = new ResourceFileService();
        ParserService parseService = new ParserService();
        String fileName = request.getParameter("filename") == null ? "" : request.getParameter("filename");
        String fileContent = request.getParameter("data") == null ? "" : request.getParameter("data");
        
        Boolean deploy = request.getParameter("deploy") == null ? false : Boolean.parseBoolean(request.getParameter("deploy"));
                
        String appName = (String) request.getAttribute("appName");
        
        if ( !fileName.isEmpty() && (!fileContent.isEmpty() || deploy) ) {
            File uploadedFile = null;
            try {
                if(deploy){   
                    String version = request.getParameter("version");
                    uploadedFile = ResourceFileService.getResourceXmlFile(appName, fileName, version);
                } else {
                    uploadedFile = new File(fileName);
                    try (FileOutputStream fos = new FileOutputStream(uploadedFile)) {
                        fos.write(fileContent.getBytes());
                    }
                }
                
                int userId = (Integer) request.getAttribute("userId");
                
                fileName = FilenameUtils.removeExtension(uploadedFile.getName());
                    if (fileName.matches("[a-zA-Z]+")) {
                        //Parse the file
                        boolean isOldFile = false;
                       
                        String domain = (String)request.getAttribute("domain");
                        
                        JSONObject jsonObj = parseService.transform(uploadedFile, appName, isOldFile, Util.OUTPUT_FOLDER, domain);
                        //String auth = jsonObj.has("auth") ? jsonObj.getString("auth") : null;

                        //Insert its entry to database and Update the resources number
                        boolean fileExists = fileService.addFile(userId, appName, fileName, 
                                ResourceFileService.getFileSize(uploadedFile.length()), jsonObj, deploy);
                        
                        if (!fileExists) {
                            //Save it on Server
                            if (!new File(Util.XML_RESOURCE_FOLDER + File.separator + appName).exists()) {
                                Files.createDirectories(Paths.get(Util.XML_RESOURCE_FOLDER + File.separator + appName));
                            }
                            if (!new File(Util.XML_RESOURCE_FOLDER + File.separator + appName + File.separator + "v" + jsonObj.get("version")).exists()) {
                                Files.createDirectories(Paths.get(Util.XML_RESOURCE_FOLDER + File.separator + appName + File.separator + "v" + jsonObj.get("version")));
                            }
                            Files.copy(uploadedFile.toPath(), new File(Util.XML_RESOURCE_FOLDER + File.separator + appName + File.separator + "v" + jsonObj.get("version") + File.separator + uploadedFile.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                        }
                        // Give a message that upload is done
                        obj.put("message", "Your file has been uploaded");
                        response.setStatus(201);
                    } else {
                        throw new MetamugException(MetamugError.RESOURCE_FILE_NAME_INVALID);
                    }
            } catch (ResourceTestException ex) {
                response.setStatus(422);
                Logger.getLogger(ResourceFileController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
                obj.put("message", ex.getMessage());
                obj.put("status", 422);
            } catch (SAXException | FileNotFoundException | XMLStreamException | XPathExpressionException | TransformerException ex) {
                String message = ex.getMessage();
                if (message.contains("facet-valid with respect to minInclusive") && ex.getMessage().contains("type 'status'")) {
                    obj.put("message", "'status' attribute must be in range [100-599]");
                } else if (message.contains("facet-valid with respect to minInclusive") && ex.getMessage().contains("type 'strlength'")) {
                    obj.put("message", "'minlength/maxlength' attributes takes only positive numbers");
                } else if (message.contains("cvc-identity-constraint")) {
                    String err = "Can't have multiple Item/Collection request.";
                    if (message.contains("identity constraint \"uniqueId\"")) {
                        err = "Duplicate values found for 'id' attributes of multiple tags.";
                    }
                    obj.put("message", err);
                } else if (message.contains("facet-valid with respect to pattern '[a-zA-Z].*'")) {
                    if (message.contains("'#AnonType_offsetsql'")) {
                        obj.put("message", "'offset' attribute value must start with a letter");
                    } else if (message.contains("'#AnonType_limitsql'")) {
                        obj.put("message", "'limit' attribute value must start with a letter");
                    } else if (message.contains("'#AnonType_authResource'")) {
                        obj.put("message", "'auth' attribute value must start with a letter");
                    }
                } else if (ex.getMessage().contains(": ")) {
                    obj.put("message", ex.getMessage().split(": ")[1]);
                } else {
                    obj.put("message", ex.getMessage());
                }
                obj.put("status", 422);
                response.setStatus(422);
                Logger.getLogger(ResourceFileController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            } catch (SQLException | PropertyVetoException | ClassNotFoundException ex) {
                response.setStatus(503);
                Logger.getLogger(ResourceFileController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            } catch (MetamugException ex) {
                obj.put("message", ex.getMessage());
                switch (ex.getError()) {
                    case RESOURCE_FILE_NAME_INVALID:
                        obj.put("status", 422);
                        response.setStatus(422);
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
//                Logger.getLogger(ResourceFileController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            } catch (FileAlreadyExistsException ex) {
                obj.put("message", ex.getMessage());
                obj.put("status", 409);
                response.setStatus(409);
            } catch (IllegalStateException ex) {
                if (ex.getMessage().contains("exceed") && ex.getMessage().contains("max") && ex.getMessage().contains("filesize")) {
                    obj.put("message", "File size too large.");
                    obj.put("status", 413);
                    response.setStatus(413);
                }
            } catch (IOException | URISyntaxException | JAXBException | JSONException | NullPointerException ex) {
                obj.put("message", "Resource could not be saved. Please try again.");
                obj.put("status", 502);
                response.setStatus(502);
                Logger.getLogger(ResourceFileController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
            if (!deploy && (uploadedFile != null) ) {
                //Delete the temporary file
                uploadedFile.delete();
            }
        } else {
            if (fileContent == null || fileContent.isEmpty()) {
                obj.put("message", "File is empty can't be uploaded.");
                obj.put("status", 406);
                response.setStatus(406);
            } else {
                obj.put("message", "Preconditions Failed. Check Requests parameter list");
                obj.put("status", 412);
                response.setStatus(412);
            }
        }
        try (ServletOutputStream out = response.getOutputStream()) {
            out.print(obj.toString());
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(ResourceFileController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        JSONObject obj = new JSONObject();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo();
        try (ServletOutputStream out = response.getOutputStream()) {
            if (pathInfo != null) {
                String[] path = pathInfo.split("/");
                String appName = (String) request.getAttribute("appName");
                int userId = (Integer) request.getAttribute("userId");
                
                String fileName = path[2];
                        
                try {
                        if (fileName.matches("[a-zA-Z]+")) {
                            File resourceFile = new File(fileName + ".xml");
                            
                            try (FileOutputStream fos = new FileOutputStream(resourceFile)) {
                                BufferedReader reader = request.getReader();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    if (!line.isEmpty()) {
                                        fos.write(line.getBytes());
                                        fos.write("\n".getBytes());
                                    }
                                }
                            }
                           
                            ParserService parseService = new ParserService();

                            //Parse the file
                            boolean isOldFile = true;

                            String domain = (String)request.getAttribute("domain");

                            JSONObject jsonObj = parseService.transform(resourceFile, appName, isOldFile, Util.OUTPUT_FOLDER, domain);
                            
                            new ResourceFileService().addFile(userId, appName, FilenameUtils.removeExtension(resourceFile.getName()),
                                    ResourceFileService.getFileSize(resourceFile.length()), jsonObj, false);
                            //Save it on Server
                            if (!new File(Util.XML_RESOURCE_FOLDER + File.separator + appName + File.separator + "v" + jsonObj.get("version")).exists()) {
                                Files.createDirectories(Paths.get(Util.XML_RESOURCE_FOLDER + File.separator + appName + File.separator + "v" + jsonObj.get("version")));
                            }
                            Files.copy(resourceFile.toPath(), new File(Util.XML_RESOURCE_FOLDER + File.separator 
                                    + appName + File.separator + "v" + jsonObj.get("version") + File.separator +
                                            resourceFile.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                            resourceFile.delete();
                        } else {
                            throw new MetamugException(MetamugError.RESOURCE_FILE_NAME_INVALID);
                        }
                    
                } catch (ResourceTestException ex) {
                    response.setStatus(422);
                    obj.put("message", ex.getMessage());
                    obj.put("status", 422);
                } catch (SAXException ex) {
                    String message = ex.getMessage();
                    if (message.contains("facet-valid with respect to minInclusive") && ex.getMessage().contains("type 'status'")) {
                        obj.put("message", "'status' attribute must be in range [100-599]");
                    } else if (message.contains("facet-valid with respect to minInclusive") && ex.getMessage().contains("type 'strlength'")) {
                        obj.put("message", "'minlength/maxlength' attributes takes only positive numbers");
                    } else if (message.contains("facet-valid with respect to pattern '[a-zA-Z].*'")) {
                        if (message.contains("'#AnonType_offsetsql'")) {
                            obj.put("message", "'offset' attribute value must start with a letter");
                        } else if (message.contains("'#AnonType_limitsql'")) {
                            obj.put("message", "'limit' attribute value must start with a letter");
                        } else if (message.contains("'#AnonType_authResource'")) {
                            obj.put("message", "'auth' attribute value must start with a letter");
                        }
                    } else if (message.contains("cvc-identity-constraint")) {
                        String err = "Can't have multiple Item/Collection request.";
                        if (message.contains("identity constraint \"uniqueId\"")) {
                            err = "Duplicate values found for 'id' attributes of multiple tags.";
                        }
                        obj.put("message", err);
                    } else if (message.contains(": ")) {
                        obj.put("message", message.split(": ")[1]);
                    } else {
                        obj.put("message", message);
                    }
                    obj.put("status", 422);
                    response.setStatus(422);
//                    Logger.getLogger(ResourceFileController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
                } catch (SQLException | PropertyVetoException | ClassNotFoundException ex) {
                    response.setStatus(503);
                    Logger.getLogger(ResourceFileController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
                } catch (FileNotFoundException | XMLStreamException | XPathExpressionException | TransformerException ex) {
                    obj.put("message", ex.getLocalizedMessage());
                    obj.put("status", 422);
                    response.setStatus(422);
                    Logger.getLogger(ResourceFileController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
                } catch (MetamugException ex) {
                    obj.put("message", ex.getMessage());
                    switch (ex.getError()) {
                        case RESOURCE_FILE_NAME_INVALID:
                            obj.put("status", 422);
                            response.setStatus(422);
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
                } catch (IOException | JAXBException | NullPointerException ex) {
                    obj.put("message", "Resource could not be saved. Please try again.");
                    obj.put("status", 502);
                    response.setStatus(502);
                    Logger.getLogger(ResourceFileController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(ResourceFileController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            out.print(obj.toString());
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(ResourceFileController.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ResourceFileService fileService = new ResourceFileService();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject obj = new JSONObject();
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo != null) {
                String[] path = pathInfo.split("/");
                String appName = (String) request.getAttribute("appName");
                String type = (String)request.getParameter("type");
                //System.out.println("TYPE: "+type);
                int userId = (Integer) request.getAttribute("userId");
                String version = path[1].toLowerCase();
                String fileName = path[2];
                String domain = (String)request.getAttribute("domain");
        
                    JSONObject result = fileService.deleteFile(version, fileName, appName, userId, domain.toString(), type);
                    if (result == null) {
                        obj.put("message", "File has been deleted");
                        obj.put("status", 410);
                        response.setStatus(410);
                    } else {
                        response.setStatus(422);
                        obj.put("message", result.get("message"));
                        obj.put("status", 422);
                    }
            } else {
                obj.put("message", "Preconditions Failed.Check Requests parameter list");
                obj.put("status", 412);
                response.setStatus(412);
            }
        } catch (SQLException | PropertyVetoException | ClassNotFoundException | IOException ex) {
            obj.put("message", "Preconditions Failed.Check Requests parameter list");
            obj.put("status", 412);
            response.setStatus(412);
            Logger.getLogger(ResourceFileController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        try (ServletOutputStream out = response.getOutputStream()) {
            out.print(obj.toString());
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(ResourceFileController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
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