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
package com.metamug.parser.parser.service;

import com.metamug.parser.parser.exception.ResourceTestException;
import com.metamug.parser.parser.util.Utils;
import com.metamug.parser.schema.Param;
import com.metamug.parser.schema.Resource;
import com.metamug.parser.schema.Sql;
import com.metamug.parser.schema.SqlType;
import com.metamug.parser.schema.Transaction;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author anishhirlekar
 */
public class ResourceTestService {

    public static Map<String, String> escapeCharacters = new HashMap<String, String>() {
        {
            put("\\sle(\\s|\\b)", " <= ");
            put("\\sge(\\s|\\b)", " >= ");
            put("\\seq(\\s|\\b)", " = ");
            put("\\sne(\\s|\\b)", " != ");
            put("\\slt(\\s|\\b)", " < ");
            put("\\sgt(\\s|\\b)", " > ");
        }
    };
    
    public static String preprocessSql(String sql){
        sql = sql.replace("\n", " ").replace("\r", " ").trim();
        sql = sql.replaceAll("\\s{2,}", " ");
        return replaceEscapeCharacters(sql);         
    }

    private static String replaceEscapeCharacters(String sql) {
        for (Map.Entry<String, String> e : escapeCharacters.entrySet()) {
            if (sql.toLowerCase().matches(".*" + e.getKey() + ".*")) {
                sql = sql.replaceAll(e.getKey(), e.getValue());
            }
        }
        return sql;
    }

    public static String makeRequest(String appUrl, String action, JSONObject inputJson, String appName) throws IOException, ResourceTestException {

        URL obj = new URL(appUrl + "/query");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Authorization", Utils.getMasonApiRequestSignature(appName));
        String urlParameters = "action=" + action + "&querydata=" + URLEncoder.encode(inputJson.toString(), "UTF-8");
        // Send post request
        con.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }
        int statusCode = con.getResponseCode();
        if (statusCode != 200) {
            throw new ResourceTestException("Something went wrong!");
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
    
    private void addSqlData(JSONArray queries, Sql sql, List<Param> paramsWithValue, boolean tx){
        JSONObject queryObj = new JSONObject();
        queryObj.put("tag_id", sql.getId());
        
        if (null == sql.getRef()) {
            queryObj.put("ref", false);
            String query = preprocessSql(sql.getValue());
            List<String> sqlParamNames = getRequestParams(query);
            JSONArray testdata = new JSONArray();
            for (String sqlParamName : sqlParamNames) {
                for (Param p : paramsWithValue) {
                    if (p.getName().equals(sqlParamName)) {
                        JSONObject testdataObject = new JSONObject();
                        testdataObject.put("varname", p.getName());
                        testdataObject.put("varvalue", p.getValue());
                        testdata.put(testdataObject);
                    }
                }
            }
            if (testdata.length() != sqlParamNames.size()) {
                //if not all param values are given, do not send test data
                testdata = new JSONArray();
            }
            queryObj.put("value", query);
            queryObj.put("testdata", testdata);
            if (sql.getType() != null) {
                queryObj.put("type", sql.getType().value());
            }
        } else {
            queryObj.put("ref", true);
            queryObj.put("query_id", sql.getRef());
        }

        queries.put(queryObj);
    }

    public void testResource(Resource resource, String domain, String appName)
            throws SQLException, ClassNotFoundException, PropertyVetoException, IOException, ResourceTestException {
        JSONObject inputJson = new JSONObject();
        JSONArray queries = new JSONArray();

        resource.getRequest().forEach( req -> {
            List<Param> paramsWithValue = new ArrayList<>();

            List elements = req.getParamOrSqlOrExecuteOrXrequestOrScript();

            elements.forEach( obj -> {
                if (obj instanceof Param) {
                    Param param = (Param) obj;
                    if (param.getValue() != null) {
                        paramsWithValue.add(param);
                    }
                }
            });

            elements.forEach( object -> {
                if(object instanceof Transaction){
                    List<Sql> sqlList = ((Transaction)object).getSql();
                    sqlList.forEach( sql -> {
                        addSqlData(queries,sql,paramsWithValue,true);
                    });
                } else if (object instanceof Sql) {
                    addSqlData(queries,(Sql)object,paramsWithValue,false);
                }
            });
        });

        if (!queries.isEmpty()) {
            inputJson.put("queries", queries);
            String testresults = makeRequest(domain + "/" + appName, "testqueries", inputJson, appName);
            verifyResult(testresults);

            //System.out.println("PARSER-RESULT");
            JSONArray results = new JSONArray(testresults);
            //System.out.println(results.toString(3));

            //set type for sql tags without given type
            resource.getRequest().forEach( req -> {
                List elements = req.getParamOrSqlOrExecuteOrXrequestOrScript();
                JSONArray test_results = results.getJSONObject(0).getJSONArray("test_results");
                
                elements.forEach( object -> {
                    if(object instanceof Transaction){
                        List<Sql> sqlList = ((Transaction)object).getSql();
                        sqlList.forEach( sql -> {
                            setSqlType(sql,test_results);     
                        });
                    }else if (object instanceof Sql) {
                        Sql sql = (Sql) object;
                        setSqlType(sql,test_results);
                    }
                });
            });
        }
    }
    
    private void setSqlType(Sql sql,JSONArray test_results){
        String tagId = sql.getId();

        for (int i=0; i < test_results.length(); i++) {
            JSONObject resultObj = test_results.getJSONObject(i);
            String tag_id = resultObj.getString("tag_id");

            if (tagId.equals(tag_id)) {
                SqlType type = SqlType.fromValue(resultObj.getString("querytype"));
                sql.setType(type);

                if (sql.getRef() != null) {
                    sql.setValue(resultObj.getString("query"));
                }
            }
        }
    }

    protected List<String> getRequestParams(String query) {
        List<String> params = new ArrayList<>();
        Pattern pattern = Pattern.compile(ParserService.REQUEST_PARAM_PATTERN);
        Matcher match = pattern.matcher(query);
        while (match.find()) {
            params.add(query.substring(match.start(1), match.end(1)).trim());
        }
        return params;
    }

    private void verifyResult(String result) throws ResourceTestException {
        JSONArray resultArray;
        try {
            resultArray = new JSONArray(result);
        } catch (JSONException jex) {
            throw new ResourceTestException("Something went wrong!");
        }
        JSONArray testResults = resultArray.getJSONObject(0).getJSONArray("test_results");
        //System.out.println(testResults.toString(3));
        StringBuilder sb = new StringBuilder("Errors found in Sql tags:");
        sb.append("<br/>");

        boolean error = false;

        for (int i = 0; i < testResults.length(); i++) {
            JSONObject testResult = testResults.getJSONObject(i);
            String tag_id = testResult.getString("tag_id");
            boolean isRef = testResult.getBoolean("ref");
            
            if (isRef) {
                boolean exists = testResult.getBoolean("exists");
                if (!exists) {
                    error = true;
                    sb.append("<b>Tag ID:</b> ").append("<b class='text-info'>").append(tag_id).append("</b>");
                    sb.append("<br/>");
                    String queryId = testResult.getString("query_id");
                    sb.append("<b>Error:</b> ").append("Invalid reference ID <b class='text-success'>").append(queryId);
                    sb.append("</b><br/>");
                }
            } else {
                int status = testResult.getInt("status");
                if (status == 500 || status == 403 || status == 404) {
                    error = true;
                    JSONArray data = testResult.getJSONArray("data");
                    String message = data.getString(0);

                    sb.append("<b>Tag ID:</b> ").append("<b class='text-info'>").append(tag_id).append("</b>");
                    sb.append("<br/>");
                    sb.append("<b>Error:</b> ").append(message);
                    sb.append("<br/>");
                }
            }
        }

        if (error) {
            throw new ResourceTestException(sb.toString());
        }
    }
}
