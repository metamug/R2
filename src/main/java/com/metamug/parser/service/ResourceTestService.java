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
package com.metamug.parser.service;

import com.metamug.parser.exception.ResourceTestException;
import com.metamug.schema.Param;
import com.metamug.schema.Request;
import com.metamug.schema.Resource;
import com.metamug.schema.Sql;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author anishhirlekar
 */
public class ResourceTestService {
    /*
    private JSONArray executeQuery(String query, String appName, String domain, String type)
            throws SQLException, ClassNotFoundException, PropertyVetoException, IOException, ResourceTestException {

        JSONArray tablesArray = new JSONArray();
        JSONObject tableData = new JSONObject();
        String result = Utils.executeQueryInApp(domain + "/" + appName, type, query);

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
    }*/
    
    public static String makeRequest(String appUrl, String action, JSONObject inputJson) throws IOException, ResourceTestException {

        URL obj = new URL(appUrl + "/query");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "action=" + action + "&querydata=" + URLEncoder.encode(inputJson.toString(), "UTF-8");

        // Send post request
        con.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }
        int statusCode = con.getResponseCode();
        if (statusCode != 200) {
            throw new ResourceTestException("Server error. Something went wrong!");
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            StringBuilder responseBuffer = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                responseBuffer.append(inputLine);
            }
            return responseBuffer.toString();
        }
    }

    public JSONObject testResource(Resource resource, String domain, String appName)
            throws SQLException, ClassNotFoundException, PropertyVetoException, IOException, ResourceTestException {
        JSONObject result = new JSONObject();

        JSONObject inputJson = new JSONObject();
        JSONArray queries = new JSONArray();
        
        for (Request req : resource.getRequest()) {
            List<Param> paramsWithValue = new ArrayList<>();    
            
            List elements = req.getParamOrSqlOrExecuteOrXrequest();
            
            for (Object obj : elements) {
                if (obj instanceof Param) {
                    Param param = (Param)obj;
                    if(param.getValue() != null){
                        paramsWithValue.add(param);
                    }
                }
            }
            
            for (Object object : elements) {
                
                if (object instanceof Sql) {
                    Sql sql = (Sql) object;
                    
                    if(null == sql.getRef()){
                        String query = sql.getValue().trim();
                        
                        List<String> sqlParamNames = getSqlParams(query);
                        JSONArray testdata = new JSONArray();
                        
                        for(String sqlParamName: sqlParamNames){
                            for(Param p: paramsWithValue){
                                if(p.getName().equals(sqlParamName)){
                                    JSONObject testdataObject = new JSONObject();
                                    testdataObject.put("varname", p.getName());
                                    testdataObject.put("varvalue",p.getValue());
                                    testdata.put(testdataObject);
                                }
                            }
                        }
                        
                        if(testdata.length() != sqlParamNames.size()){
                            //if not all param values are given, do not send test data
                            testdata = new JSONArray();
                        }
                        
                        JSONObject queryObj = new JSONObject();
                        queryObj.put("value", query);
                        queryObj.put("testdata", testdata);
                        queryObj.put("tag_id", sql.getId());
                        
                        queries.put(queryObj);
                        
                    }                    
                }
            }
        }
        if(!queries.isEmpty()){
            inputJson.put("queries", queries);
            String testresults = makeRequest(domain+"/"+appName,"testqueries",inputJson);
            //verifyResult(result);
            System.out.println("PARSER-RESULT");
            JSONArray results = new JSONArray(testresults);
            System.out.println(results.toString(3));
        }
       
        //System.out.println(inputJson.toString(4));

        return result;
    }
    
    protected List<String> getSqlParams(String query) {
        List<String> params = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\$(\\w+((\\[\\d\\]){0,}\\.\\w+(\\[\\d\\]){0,}){0,})");
        Matcher match = pattern.matcher(query);
        while (match.find()) {
            params.add(query.substring(match.start(1), match.end(1)).trim());
        }
        return params;
    }

    private void verifyResult(JSONObject res) throws ResourceTestException {
        StringBuilder sb = new StringBuilder("Errors occurred while testing queries:");
        sb.append(System.getProperty("line.separator"));

        boolean error = false;
        Iterator<String> keys = res.keys();
        while (keys.hasNext()) {
            String queryId = keys.next();
            JSONArray array = res.getJSONArray(queryId);
            JSONObject queryResult = array.getJSONObject(0);

            int status = queryResult.getInt("status");
            if (status == 500 || status == 403 || status == 404) {
                error = true;
                JSONArray data = queryResult.getJSONArray("data");
                String message = data.getString(0);

                sb.append("<b>Ref:</b> ").append("<span class='text-success'>").append(queryId).append("</span>");
                sb.append("<br/>");
                sb.append("<b>Error:</b> ").append(message);
                sb.append("<br/>");
            }
        }

        if (error) {
            throw new ResourceTestException(sb.toString());
        }
    }
}
