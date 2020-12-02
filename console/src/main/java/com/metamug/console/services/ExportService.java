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
package com.metamug.console.services;

import com.metamug.parser.ExportParserService;
import com.metamug.console.util.CopyDirectory;
import com.metamug.console.util.FileUtil;
import com.metamug.console.util.Util;
import static com.metamug.console.util.Util.EXPORT_FOLDER;
import static com.metamug.console.util.Util.LIB_FOLDER;
import static com.metamug.console.util.Util.OUTPUT_FOLDER;
import com.metamug.parser.exception.ResourceTestException;
import static com.metamug.parser.schema.InvocableElement.BACKEND_PROP_PATTERN;
import static com.metamug.parser.schema.InvocableElement.MPATH_EXPRESSION_PATTERN;
import static com.metamug.parser.schema.InvocableElement.REQUEST_PARAM_PATTERN;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

/**
 *
 * @author Anish
 */
public class ExportService {

    private final String ANALYTIC_FILTER = "AnalyticFilter";

    private final String[] deleteLibs = {
        "query-api-1.0.jar"
    };

    private JSONObject queryMap;

    /*
    {
        "queries": [
            {
                
            },
            .
            .
        ],
        "tags": [
            {
                
            },
            .
            .
        ]
    }   
     */
    /**
     * Export a deployed webapp and build its .war
     *
     * @param appName Name of the deployed webapp
     * @param domain Domain name needed for querying the backend API
     * @return Generated war file path
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws net.lingala.zip4j.exception.ZipException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.transform.TransformerException
     * @throws com.metamug.parser.exception.ResourceTestException
     */
    public String exportAppWar(String appName, String domain) throws IOException, InterruptedException,
            ZipException, ParserConfigurationException, SAXException, TransformerException, ResourceTestException {
        //copy webapp to exported apps dir
        /*String exportedAppDir = copyWebapp(appName);
        //update web.xml file of app - remove analytics filter
        updateWebXml(exportedAppDir);
        //get query catalog mapping for app from db
        queryMap = getQueryMap(appName, domain);
        //write query mapping to props file
        createQueryMap(exportedAppDir);
        //create jsp files with decoupled queries
        transformResources(appName);
        removeHookLibs(exportedAppDir);
        addRuntimeLibs(exportedAppDir);

        return generateWar(appName);*/ 
        return null;
    }

    public String exportAppSource(String appName) throws IOException, ZipException {
        String appSrcPath = createSrcDir(appName);
        copyConfig(appName);
        copyResources(appName, appSrcPath + File.separator + "res");
        //copyPlugins(appName, appSrcPath + File.separator + "plugins");

        return createZip(appName);
    }

    private void copyConfig(String appName) throws IOException {
        File configSrc = new File(Util.BACKEND_FOLDER + File.separator + appName + File.separator + "config.json");
        File configDest = new File(Util.EXPORT_FOLDER + File.separator + appName + File.separator + "config.json");
        FileUtil.copyFile(configSrc, configDest);
    }

    /**
     * Create zip from webapp directory and named as .war
     *
     * @param appName Name of the application
     * @return Generated war file path
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws net.lingala.zip4j.exception.ZipException
     */
    private String generateWar(String appName) throws IOException, InterruptedException, ZipException {
        String warFilePath = EXPORT_FOLDER + File.separator + appName + ".war";
        //delete existing war file
        Files.deleteIfExists(Paths.get(warFilePath));

        String webappDir = EXPORT_FOLDER + File.separator + appName;
        ZipFile zipFile = new ZipFile(warFilePath);
        ZipParameters parameters = new ZipParameters();
        parameters.setIncludeRootFolder(false);
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        zipFile.addFolder(webappDir, parameters);

        return warFilePath;
    }

    private String createZip(String appName) throws IOException, ZipException {
        String zipPath = EXPORT_FOLDER + File.separator + appName + ".zip";
        Files.deleteIfExists(Paths.get(zipPath));

        String appSource = EXPORT_FOLDER + File.separator + appName;
        ZipFile zipFile = new ZipFile(zipPath);
        ZipParameters parameters = new ZipParameters();
        parameters.setIncludeRootFolder(true);
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        zipFile.addFolder(appSource, parameters);

        return zipPath;
    }

    private String createSrcDir(String appName) {
        Path exportDir = Paths.get(EXPORT_FOLDER);
        String appFolder = EXPORT_FOLDER + File.separator + appName;
        String dirPath = appFolder + File.separator + "src";
        try {
            if (!Files.exists(exportDir)) {
                //create APP_BASE/exported folder if not exists
                Files.createDirectories(exportDir);
            } else {
                File appFolderFile = new File(appFolder);
                if (appFolderFile.exists()) {
                    FileUtil.recursiveDelete(appFolderFile);
                }
            }
            //wait for 1 second for all files to get removed
            TimeUnit.SECONDS.sleep(1);
            Files.createDirectories(Paths.get(dirPath));
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ExportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dirPath;
    }

    private void copyResources(String appName, String destDir) {
        String resourcesDir = Util.XML_RESOURCE_FOLDER + File.separator + appName;
        try {
            File source = new File(resourcesDir);
            if(source.exists()){
                FileUtil.copyFolder(new File(resourcesDir), new File(destDir));
            }
        } catch (IOException ex) {
            Logger.getLogger(ExportService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/*
    private void copyPlugins(String appName, String destDir) {
        File pluginsDir = new File(Util.PLUGINS_FOLDER + File.separator + appName);
        if(!pluginsDir.exists()){
            return;
        }
        try {
            Util.copyFolder(pluginsDir, new File(destDir));
        } catch (IOException ex) {
            Logger.getLogger(ExportService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    /**
     * Copy webapp folder to export directory
     *
     * @param appName Name of the webapp
     * @return Exported webapp path
     */
    private String copyWebapp(String appName) {
        String destAppPath = EXPORT_FOLDER + File.separator + appName;
        Path exportDir = Paths.get(EXPORT_FOLDER);
        Path srcApp = Paths.get(OUTPUT_FOLDER + File.separator + appName);
        try {
            if (!Files.exists(exportDir)) {
                //create APP_BASE/exported folder if not exists
                Files.createDirectories(exportDir);
            } else {
                File destAppFolder = new File(destAppPath);
                if (destAppFolder.exists()) {
                    FileUtil.recursiveDelete(destAppFolder);
                }
            }
            //wait for 1 second for all files to get removed
            TimeUnit.SECONDS.sleep(1);
            CopyDirectory.copy(srcApp, Paths.get(destAppPath));
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ExportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return destAppPath;
    }
    
    

    
}
