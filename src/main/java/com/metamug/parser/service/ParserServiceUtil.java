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
import static com.metamug.parser.service.ParserService.MPATH_EXPRESSION_PATTERN;
import static com.metamug.parser.service.ParserService.REQUEST_PARAM_PATTERN;
import static com.metamug.parser.service.ParserService.UPLOAD_OBJECT;
import com.metamug.schema.Execute;
import com.metamug.schema.Sql;
import com.metamug.schema.Text;
import com.metamug.schema.Xrequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author anishhirlekar
 */
public class ParserServiceUtil {
    //transforms request variables in given string
    protected static String transformRequestVariables(String input, boolean enclose) {
        String output = input;
        Pattern pattern = Pattern.compile(REQUEST_PARAM_PATTERN);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String v = input.substring(matcher.start(1), matcher.end(1)).trim();
            String tv;
            StringBuilder sb = new StringBuilder();
            if(enclose){
                sb.append("${");
            }
            switch (v) {
                case "id":
                    tv = "mtgReq.id";
                    break;
                case "pid":
                    tv = "mtgReq.pid";
                    break;
                case "uid":
                    tv = "mtgReq.uid";
                    break;
                default:
                    tv = "mtgReq.params['" + v + "']";
            }
            sb.append(tv);
            
            if(enclose){
                sb.append("}");
            }
            
            output = output.replace("$"+v, sb.toString());
        }
       
        return output;
    }
    
    protected static String getJspVariableForMPath(String mpathVariable, String type, String elementId, boolean enclose){
        String transformedVariable = mpathVariable;
        
        StringBuilder sb = new StringBuilder();
        if(enclose) {
            sb.append("${");
        }
        
        if(type.equals(Sql.class.getName())) {
            // id.rows[0].name
            String rowIndex = "0";
            String colName = null;

            Pattern p = Pattern.compile("^\\$\\[(\\w+?)\\]\\[(\\d+?)\\]\\.(\\S+?)$");
            Matcher m = p.matcher(mpathVariable);

            if(m.find()) {
                rowIndex = m.group(2);
                colName = m.group(3);
            }
            //System.out.println("Sql");
            //System.out.println("elementId: "+elementId);
            transformedVariable = elementId+".rows"+"["+rowIndex+"]."+colName;
              
        }else if(type.equals(Xrequest.class.getName())){
            // m:jsonPath('$.body.args.foo1',bus['id'])
            String locator = getMPathLocator(mpathVariable);
                
            transformedVariable = "m:jsonPath('$"+locator+"',"+elementId+")";
            
        } else if(type.equals(Execute.class.getName())){
            // bus[id].name
            String locator = getMPathLocator(mpathVariable);
            transformedVariable = elementId+locator;
            
        } else if(type.equals(Text.class.getName())){
            //System.out.println("Text");
            //System.out.println("elementId: "+elementId);
            transformedVariable = elementId;
            
        } else if(type.equals(UPLOAD_OBJECT)){
            transformedVariable = elementId;
        }
        
        sb.append(transformedVariable);
        
        if(enclose){
            sb.append("}");
        }
        
        return sb.toString();
    }
    
    //collects MPath variables for sql:param tags
    protected static void collectMPathParams(LinkedList<String> params,String sql, Map<String,String> elementIds) throws ResourceTestException {
        Pattern pattern = Pattern.compile(MPATH_EXPRESSION_PATTERN);
        Matcher matcher = pattern.matcher(sql);
        
        while (matcher.find()) {
            params.add(sql.substring(matcher.start(), matcher.end()).trim());
        }
    }
    
    //transforms MPath variables in given string
    protected static String transformMPathVariables(String input, Map<String,String> elementIds, boolean enclose) throws ResourceTestException {
        String transformed = input;
        Pattern pattern = Pattern.compile(MPATH_EXPRESSION_PATTERN);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String mpathVariable = input.substring(matcher.start(), matcher.end()).trim();
            //get element id from mpath variable
            String elementId = getMPathId(mpathVariable);
            
            if(!elementIds.containsKey(elementId)){
                throw new ResourceTestException("Could not find element with ID: "+elementId);
            }
            //get type of element
            String type = elementIds.get(elementId);
            String tv = getJspVariableForMPath(mpathVariable, type, elementId, enclose);
            
            transformed = transformed.replace(mpathVariable, tv);
        }
       
        return transformed;
    }
    
    

    // '%$variable%' => CONCAT('%',$variable,'%')
    public static String processVariablesInLikeClause(String q) {
        Pattern quotePattern = Pattern.compile("'(.*?)'");
        Matcher quotedSubstringMatcher = quotePattern.matcher(q);
        while (quotedSubstringMatcher.find()) {
            String stringWithinQuotes = quotedSubstringMatcher.group(1);

            Pattern varPattern = Pattern.compile("\\$(\\w+((\\[\\d\\]){0,}\\.\\w+(\\[\\d\\]){0,}){0,})");
            Matcher matcher = varPattern.matcher(stringWithinQuotes);

            StringBuilder builder = new StringBuilder();
            String succedent = stringWithinQuotes;

            builder.append("CONCAT(");
            List<String> args = new ArrayList();
            boolean variableFound = false;
            while (matcher.find()) {
                variableFound = true;
                String variable = matcher.group(1);

                if (!args.isEmpty()) {
                    args.remove(args.size() - 1);
                }
                String precedent = succedent.substring(0, succedent.length() - stringWithinQuotes.length() + matcher.start());
                if (!"".equals(precedent)) {
                    args.add("'" + precedent + "'");
                }
                args.add("$" + variable);
                succedent = succedent.substring(succedent.length() - stringWithinQuotes.length() + matcher.end(), succedent.length());
                if (!"".equals(succedent)) {
                    args.add("'" + succedent + "'");
                }
            }

            builder.append(String.join(",", args));
            builder.append(")");
            if (variableFound) {
                q = q.replace("'" + stringWithinQuotes + "'", builder.toString());
            }
        }
        return q;
    }

    public static String getMPathId(String path){
        Pattern p = Pattern.compile("^\\$\\[(.*?)\\]");// $[varname]

        Matcher m = p.matcher(path);
        
        while(m.find()) {
            return m.group(1);
        }
        
        return null;
    }
    
    protected static String getMPathLocator(String path){
        return path.replaceFirst("\\$\\[(.*?)\\]","");
    }
    
}
