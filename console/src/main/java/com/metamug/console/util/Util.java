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
package com.metamug.console.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

/**
 *
 * @author Kainix
 */
public class Util {

    public static final String REQUEST_SCHEME = "http";
    public static final String UTF8 = "UTF-8";
    public static final String LOCALHOST_IP = "127.0.0.1";
    public static final String SERVER_PORT = "7000";
    public static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    public static final int MAX_REQUEST_SIZE = 1024 * 1024;

    public static final String APP_BASE = System.getProperty("catalina.base");
    public static final String REMOVE_SH = APP_BASE + File.separator + "bin" + File.separator + "remove.sh";
    public static final String REMOVE_BAT = APP_BASE + File.separator + "bin" + File.separator + "remove.bat";

    public static final String BACKEND_FOLDER = APP_BASE + File.separator + "backend";
    public static final String OUTPUT_FOLDER = APP_BASE + File.separator + "webapps";
    public static final String TEMP_FOLDER = APP_BASE + File.separator + "tempapps";
    public static final String UNDEPLOY_FOLDER = APP_BASE + File.separator + "undeployedapps";
    public static final String EXPORT_FOLDER = APP_BASE + File.separator + "export";
    public static final String IMPORT_FOLDER = APP_BASE + File.separator + "imported";
    public static final String LIB_FOLDER = APP_BASE + File.separator + "lib";
    public static final String XML_RESOURCE_FOLDER = APP_BASE + File.separator + "downloads" + File.separator + "resources";
    public static final String PLUGINS_FOLDER = APP_BASE + File.separator + "downloads" + File.separator + "plugins";
    public static final String MD5_ALGORITHM = "MD5";

    public static final String HSQLDB = "hsqldb";
    public static final String MYSQL = "mysql";
    public static final String POSTGRESQL = "postgresql";
    public static final String MSSQL = "mssql";
    public static final String INTERNAL_DB = "internal";

    public static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String POSTGRESQL_JDBC_DRIVER = "org.postgresql.Driver";
    public static final String MSSQL_JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String HSQLDB_JDBC_DRIVER = "org.hsqldb.jdbc.JDBCDriver";

    static final String RANDOM_STRING = "01234567891337875371ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$*&";
    public static final SecureRandom SECURE_RANDOM = new SecureRandom();
    static final File CONFIG_DIRECTORY = new File(Util.APP_BASE, "conf");
    static final Properties DB_PROPERTIES = new Properties();
    static final Properties CONFIG_PROPERTIES = new Properties();
    static final Properties BACKEND_DB_PROPERTIES = new Properties();
    
    //declared in server.xml
    private static final String SHUTDOWN_COMMAND = "SERVERISSHUTTINGDOWN";
    private static final int SHUTDOWN_PORT = 7005;

    public static boolean isValidEmailId(String emailId) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailId);
        return matcher.matches();
    }

    public static String getMasonApiRequestSignature(String backendName) {
        try {
            String salt = "MASON_STRING_API_SALT_METAMUG";
            String input = backendName.trim() + salt;
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            // return the HashText
            return hashtext;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(RANDOM_STRING.charAt(SECURE_RANDOM.nextInt(RANDOM_STRING.length())));
        }
        return sb.toString();
    }
    
    public static String getDeviceType(String userAgent) {
        String deviceType = "Desktop";
        if (userAgent.toLowerCase().contains("tablet")) {
            deviceType = "Tablet";
        } else if (userAgent.toLowerCase().contains("tv")) {
            deviceType = "TV";
        } else if (userAgent.toLowerCase().contains("mobi")) {
            deviceType = "Mobile";
        }
        return deviceType;
    }

    public static Properties getDbProperties() throws FileNotFoundException, IOException {
        DB_PROPERTIES.load(new FileReader(CONFIG_DIRECTORY + File.separator + "db.properties"));
        return DB_PROPERTIES;
    }
    
    public static Properties getConfigProperties() throws FileNotFoundException, IOException {
        CONFIG_PROPERTIES.load(new FileReader(CONFIG_DIRECTORY + File.separator + "config.properties"));
        return CONFIG_PROPERTIES;
    }

    public static String executeQueryInApp(String appUrl, String action, String query, String appName, String datasource) throws IOException {
        switch (action.toLowerCase()) {
            case "defaulttables":
                runQueryInApp(appUrl, action, query, appName, datasource);
                break;
            case "plsql":
                return runQueryInApp(appUrl, action, query, appName, datasource);
            case "query":
                return runQueryInApp(appUrl, action, query, appName, datasource);
            case "plsqlbyuser":
                return runQueryInApp(appUrl, action, query, appName, datasource);
            case "querybyuser":
                return runQueryInApp(appUrl, action, query, appName, datasource);
            case "errorlog":
                return getDataFromApp(appUrl, action, appName);
            case "querylog":
                return getDataFromApp(appUrl, action, appName);
            case "querycatalog":
                return getDataFromApp(appUrl, action, appName);
            case "stats":
                return getDataFromApp(appUrl, action, appName);
            default:
                return null;
        }
        return null;
    }

    private static String runQueryInApp(String appUrl, String action, String query, String appName, String datasource) {
        try {
            URL obj = new URL(appUrl + "/query");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Authorization", getMasonApiRequestSignature(appName));

            StringBuilder params = new StringBuilder();
            params.append("action=").append(action);
            params.append("&query=").append(URLEncoder.encode(query, UTF8));
            //System.out.println(datasource);
            if(datasource != null){
                params.append("&datasource=").append(URLEncoder.encode(datasource, UTF8));
            }
            // Send post request
            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(params.toString());
                wr.flush();
            }
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder responseBuffer = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    responseBuffer.append(inputLine);
                }
                return responseBuffer.toString();
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    private static String getDataFromApp(String appUrl, String action, String appName) throws IOException {
        try {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("query", action);
            String url = getParamsString(parameters).length() > 0 ? appUrl + "/query?" + getParamsString(parameters) : appUrl + "/query";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Authorization", getMasonApiRequestSignature(appName) );
            con.setDoOutput(true);
            StringBuffer response;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
            return response.toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }
        String resultString = result.toString();
        return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
    }

    public static String getMd5Hash(String str) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance(MD5_ALGORITHM);
            byte[] hashInBytes = md.digest(str.getBytes(StandardCharsets.UTF_8));

            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }

    /**
     * Creates a properties file to store API keys
     *
     * @param accessKey
     * @param secretKey
     * @param appName Name of the application
     */
    public static void storeKeys(String accessKey, String secretKey, String appName) {
        try {
            File file = new File(OUTPUT_FOLDER + "/" + appName + "/WEB-INF/classes/config.properties");
            Properties props;
            try (FileInputStream in = new FileInputStream(file)) {
                props = new Properties();
                props.load(in);
            }
            try (FileOutputStream out = new FileOutputStream(file)) {
                props.setProperty("access_key", accessKey);
                props.setProperty("secret_key", secretKey);
                props.store(out, null);
            }
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    public static void shutdownServer(){
        try { 
            Socket socket = new Socket("localhost", SHUTDOWN_PORT); 
            if (socket.isConnected()) { 
                try (PrintWriter pw = new PrintWriter(socket.getOutputStream(), true)) {
                    pw.println(SHUTDOWN_COMMAND);//send shut down command 
                } //send shut down command
                socket.close(); 
            } 
        } catch (IOException ex) { 
            //Logger.getLogger(Util.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    private static String getHwInfo() {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();
        ComputerSystem computerSystem = hardwareAbstractionLayer.getComputerSystem();

        String vendor = operatingSystem.getManufacturer();
        String processorSerialNumber = computerSystem.getSerialNumber();
        String processorIdentifier = centralProcessor.getProcessorIdentifier().getIdentifier();
        int processors = centralProcessor.getLogicalProcessorCount();

        String delimiter = "#";

        return vendor +
                delimiter +
                processorSerialNumber +
                delimiter +
                processorIdentifier +
                delimiter +
                processors;
    }
    
    public static String getMacAddr(){
        try {
            String os = System.getProperty("os.name").toLowerCase();
            //System.out.println(os);
            String command = null;
            if(os.contains("win")){
                command = "getmac";
            }else if(os.contains("mac")){
                command = "networksetup -listallhardwareports | grep \"Ethernet Address\"";
            }else if(os.contains("nix") || os.contains("nux") || os.contains("aix") ){
                //command = "/sbin/ifconfig | grep HWaddr";
                return getHwInfo();
            }
            
            Process process = Runtime.getRuntime().exec(command);
            StringBuilder responseBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    //System.out.println(line);
                    responseBuilder.append(line);
                    responseBuilder.append(" ");
                }
            }
            
            String responseString = responseBuilder.toString();
            Pattern p = Pattern.compile("([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})");// mac address xx:xx:xx:xx:xx:xx
            //System.out.println(responseString);
            Matcher matcher = p.matcher(responseString);
            if (matcher.find())
            {
                return matcher.group(0);
            }
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
