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
package com.metamug.console.services;

import com.metamug.console.exception.MetamugError;
import com.metamug.console.exception.MetamugException;
import com.metamug.console.util.Util;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Kainix
 */
public class QueryService {

    public JSONArray executeQuery(String queryType, String queries, String appName, String domain, int userId, String datasource)
            throws SQLException, ClassNotFoundException, PropertyVetoException, IOException, MetamugException {
        JSONArray tablesArray = new JSONArray();
        UserService userService = new UserService();
        JSONObject tableData = new JSONObject();
        String result = "";
        userService.validateUserApp(userId, appName);
        if (userService.isVerifiedUser(userId)) {
            if (queryType.equalsIgnoreCase("query")) {
                result = Util.executeQueryInApp(domain + "/" + appName, "query", queries, appName, datasource);
            } else if (queryType.equalsIgnoreCase("plsql")) {
                result = Util.executeQueryInApp(domain + "/" + appName, "plsql", queries, appName, datasource);
            } else if (queryType.equalsIgnoreCase("querybyuser")) {
                result = Util.executeQueryInApp(domain + "/" + appName, "querybyuser", queries, appName, datasource);
            } else if (queryType.equalsIgnoreCase("plsqlbyuser")) {
                result = Util.executeQueryInApp(domain + "/" + appName, "plsqlbyuser", queries, appName, datasource);
            }
        } else {
            throw new MetamugException(MetamugError.UNVERIFIED_USER);
        }
        if (result == null || result.isEmpty()) {
            tableData.put("status", 204);
            tablesArray.put(tableData);
            return tablesArray;
        } else {
            try {
                JSONArray resultArray = new JSONArray(result);
                return resultArray;
            } catch (JSONException ex) {
                tableData.put("status", 204);
                tablesArray.put(tableData);
                return tablesArray;
            }
        }
    }

    public JSONObject saveQuery(String domain, String appName, String action, String queryName, String query,
            String[] varnames, String[] varvalues, String datasource) throws IOException {
        StringBuilder params = new StringBuilder();
        params.append("action=").append(action).append("&queryname=").append(URLEncoder.encode(queryName, "UTF-8"))
                .append("&query=").append(URLEncoder.encode(query, "UTF-8"));
        
        if(datasource != null){
            params.append("&datasource=").append(URLEncoder.encode(datasource, "UTF-8"));
        }
        if (varnames != null && varvalues != null) {
            params.append("&");
            params.append(getEncodedVarParams(varnames, varvalues));
        }

        String result = makeRequest(domain + "/" + appName, params.toString(), appName);

        JSONArray json = new JSONArray(result);
        return json.getJSONObject(0);
    }

    public void removeReferences(String domain, String appName, String resName, String resV) throws IOException {
        StringBuilder params = new StringBuilder();
        params.append("action=").append("removeref");
        params.append("&resname=").append(resName);
        params.append("&resversion=").append(resV);

        makeRequest(domain + "/" + appName, params.toString(), appName);
    }

    private String getEncodedVarParams(String[] varnames, String[] varvalues) throws UnsupportedEncodingException {
        StringBuilder varnameSb = new StringBuilder();
        StringBuilder varvalueSb = new StringBuilder();

        for (int i = 0; i < varnames.length; i++) {
            String varname = varnames[i];
            String varvalue = varvalues[i];

            varnameSb.append("varnames[]=").append(URLEncoder.encode(varname, "UTF-8"));
            varvalueSb.append("varvalues[]=").append(URLEncoder.encode(varvalue, "UTF-8"));

            if (i != (varnames.length - 1)) {
                varnameSb.append("&");
                varvalueSb.append("&");
            }
        }

        return varnameSb.toString() + "&" + varvalueSb.toString();
    }

    private String makeRequest(String appUrl, String params, String appName) throws IOException {
        URL obj = new URL(appUrl + "/query");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Authorization", Util.getMasonApiRequestSignature(appName));
        // Send post request
        con.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(params);
            wr.flush();
        }
        if (con.getResponseCode() != 200) {
            throw new IOException();
        }
        StringBuilder responseBuffer = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBuffer.append(inputLine);
            }
        }
        return responseBuffer.toString();
    }

    public JSONObject getQueryLogs(String appName, String domain) throws IOException, SQLException, PropertyVetoException, ClassNotFoundException {
        String result = Util.executeQueryInApp(domain + "/" + appName, "querylog", "", appName, null);

        if (result.length() > 0) {
            try {
                JSONObject json = new JSONObject(result);
                return json;
            } catch (JSONException ex) {
                return getEmptyJson();
            }
        } else {
            return getEmptyJson();
        }
    }

    public JSONObject getQueryCatalog(String appName, String domain) throws IOException, SQLException, PropertyVetoException, ClassNotFoundException {
        String result = Util.executeQueryInApp(domain + "/" + appName, "querycatalog", "", appName, null);

        if (result.length() > 0) {
            try {
                JSONObject json = new JSONObject(result);
                return json;
            } catch (JSONException ex) {
                return getEmptyJson();
            }
        } else {
            return getEmptyJson();
        }
    }

    private JSONObject getEmptyJson() {
        JSONObject emptyJson = new JSONObject();
        JSONArray columnHeader = new JSONArray();
        columnHeader.put("query").put("status").put("executed_on");
        emptyJson.put("columnHeaders", columnHeader);
        emptyJson.put("data", new JSONArray());
        return emptyJson;
    }
}
