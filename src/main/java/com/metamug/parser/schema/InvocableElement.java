/**
 * **********************************************************************
 * Freeware License Agreement
 * <p>
 * This license agreement only applies to the free version of this software.
 * <p>
 * Terms and Conditions
 * <p>
 * BY DOWNLOADING, INSTALLING, USING, TRANSMITTING, DISTRIBUTING OR COPYING THIS SOFTWARE ("THE SOFTWARE"), YOU AGREE TO THE TERMS OF THIS AGREEMENT (INCLUDING THE SOFTWARE LICENSE AND DISCLAIMER OF WARRANTY) WITH METAMUG THE OWNER OF ALL RIGHTS IN RESPECT OF THE SOFTWARE.
 * <p>
 * PLEASE READ THIS DOCUMENT CAREFULLY BEFORE USING THE SOFTWARE.
 * <p>
 * IF YOU DO NOT AGREE TO ANY OF THE TERMS OF THIS LICENSE THEN DO NOT DOWNLOAD, INSTALL, USE, TRANSMIT, DISTRIBUTE OR COPY THE SOFTWARE.
 * <p>
 * THIS DOCUMENT CONSTITUTES A LICENSE TO USE THE SOFTWARE ON THE TERMS AND CONDITIONS APPEARING BELOW.
 * <p>
 * The Software is licensed to you without charge for use only upon the terms of this license, and METAMUG TECHNOLOGIES LLP (hereafter METAMUG) reserves all rights not expressly granted to you. METAMUG retains ownership of all copies of the Software.
 * <p>
 * 1. License
 * <p>
 * You may use the Software without charge.
 * <p>
 * You may freely distribute exact copies of the Software to anyone.
 * <p>
 * The inclusion of the Software in any Shareware, Freeware or similar media compilation or distribution method whereby it is made available at cost (ie. sold) is strictly prohibited.
 * <p>
 * The selling of the Software is strictly prohibited.
 * 2. Restrictions
 * <p>
 * METAMUG reserves the right to revoke the above distribution right at any time, for any or no reason.
 * <p>
 * YOU MAY NOT MODIFY, ADAPT, TRANSLATE, RENT, LEASE, LOAN, SELL, ONSELL, REQUEST DONATIONS OR CREATE DERIVATIVE WORKS BASED UPON THE SOFTWARE OR ANY PART THEREOF.
 * <p>
 * The Software contains intellectual property and to protect them you may not decompile, reverse engineer, disassemble or otherwise reduce the Software to a humanly perceivable form. You agree not to divulge, directly or indirectly, until such intellectual property cease to be confidential, for any reason not your own fault.
 * <p>
 * 3. Termination
 * <p>
 * This license is effective until terminated. The License will terminate automatically without notice from METAMUG if you fail to comply with any provision of this License. Upon termination you must destroy the Software and all copies thereof. You may terminate this License at any time by destroying the Software and all copies thereof. Upon termination of this license for any reason you shall continue to be bound by the provisions of Section 2 above. Termination will be without prejudice to any rights METAMUG may have as a result of this agreement.
 * <p>
 * 4. Disclaimer of Warranty, Limitation of Remedies
 * <p>
 * TO THE FULL EXTENT PERMITTED BY LAW, METAMUG HEREBY EXCLUDES ALL CONDITIONS AND WARRANTIES, WHETHER IMPOSED BY STATUTE OR BY OPERATION OF LAW OR OTHERWISE, NOT EXPRESSLY SET OUT HEREIN. THE SOFTWARE, AND ALL ACCOMPANYING FILES, DATA AND MATERIALS ARE DISTRIBUTED "AS IS" AND WITH NO WARRANTIES OF ANY KIND, WHETHER EXPRESS OR IMPLIED. METAMUG DOES NOT WARRANT, GUARANTEE OR MAKE ANY REPRESENTATIONS REGARDING THE USE, OR THE RESULTS OF THE USE, OF THE SOFTWARE WITH RESPECT TO ITS CORRECTNESS, ACCURACY, RELIABILITY, CURRENTNESS OR OTHERWISE. THE ENTIRE RISK OF USING THE SOFTWARE IS ASSUMED BY YOU. METAMUG MAKES NO EXPRESS OR IMPLIED WARRANTIES OR CONDITIONS INCLUDING, WITHOUT LIMITATION, THE WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE WITH RESPECT TO THE SOFTWARE. NO ORAL OR WRITTEN INFORMATION OR ADVICE GIVEN BY METAMUG, IT'S DISTRIBUTORS, AGENTS OR EMPLOYEES SHALL CREATE A WARRANTY, AND YOU MAY NOT RELY ON ANY SUCH INFORMATION OR ADVICE.
 * <p>
 * IMPORTANT NOTE: Nothing in this Agreement is intended or shall be construed as excluding or modifying any statutory rights, warranties or conditions which by virtue of any national or state Fair Trading, Trade Practices or other such consumer legislation may not be modified or excluded. If permitted by such legislation, however, METAMUG's liability for any breach of any such warranty or condition shall be and is hereby limited to the supply of the Software licensed hereunder again as METAMUG at its sole discretion may determine to be necessary to correct the said breach.
 * <p>
 * IN NO EVENT SHALL METAMUG BE LIABLE FOR ANY SPECIAL, INCIDENTAL, INDIRECT OR CONSEQUENTIAL DAMAGES (INCLUDING, WITHOUT LIMITATION, DAMAGES FOR LOSS OF BUSINESS PROFITS, BUSINESS INTERRUPTION, AND THE LOSS OF BUSINESS INFORMATION OR COMPUTER PROGRAMS), EVEN IF METAMUG OR ANY METAMUG REPRESENTATIVE HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. IN ADDITION, IN NO EVENT DOES METAMUG AUTHORIZE YOU TO USE THE SOFTWARE IN SITUATIONS WHERE FAILURE OF THE SOFTWARE TO PERFORM CAN REASONABLY BE EXPECTED TO RESULT IN A PHYSICAL INJURY, OR IN LOSS OF LIFE. ANY SUCH USE BY YOU IS ENTIRELY AT YOUR OWN RISK, AND YOU AGREE TO HOLD METAMUG HARMLESS FROM ANY CLAIMS OR LOSSES RELATING TO SUCH UNAUTHORIZED USE.
 * <p>
 * 5. General
 * <p>
 * All rights of any kind in the Software which are not expressly granted in this Agreement are entirely and exclusively reserved to and by METAMUG.
 * <p>
 * This Agreement shall be governed by the laws of the State of Maharashtra, India. Exclusive jurisdiction and venue for all matters relating to this Agreement shall be in courts and fora located in the State of Maharashtra, India, and you consent to such jurisdiction and venue. This agreement contains the entire Agreement between the parties hereto with respect to the subject matter hereof, and supersedes all prior agreements and/or understandings (oral or written). Failure or delay by METAMUG in enforcing any right or provision hereof shall not be deemed a waiver of such provision or right with respect to the instant or any subsequent breach. If any provision of this Agreement shall be held by a court of competent jurisdiction to be contrary to law, that provision will be enforced to the maximum extent permissible, and the remaining provisions of this Agreement will remain in force and effect.
 */
package com.metamug.parser.schema;

import com.metamug.parser.exception.ResourceTestException;

import com.metamug.parser.service.ParserService;

import static com.metamug.parser.service.ParserService.MPATH_EXPRESSION_PATTERN;
import static com.metamug.parser.service.ParserService.REQUEST_PARAM_PATTERN;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.SAXException;

/**
 * @author anishhirlekar
 */
@XmlTransient
public abstract class InvocableElement {

    @XmlTransient
    public ParserService parent;

    /**
     * @param writer
     * @param parent
     * @throws javax.xml.stream.XMLStreamException
     * @throws java.io.IOException
     * @throws javax.xml.xpath.XPathExpressionException
     * @throws com.metamug.parser.exception.ResourceTestException
     * @throws org.xml.sax.SAXException
     */
    abstract public void print(XMLStreamWriter writer, ParserService parent) throws XMLStreamException, IOException, XPathExpressionException, ResourceTestException, SAXException;

    /*
     * Returns parameters according to type of child element
     *
     */
    abstract public Set<String> getRequestParameters();


    /**
     * @param writer
     * @param data
     * @param output
     * @throws javax.xml.stream.XMLStreamException
     * @throws IOException
     * @throws javax.xml.xpath.XPathExpressionException
     */
    protected void writeUnescapedCharacters(XMLStreamWriter writer, String data, OutputStream output) throws XMLStreamException, IOException, XPathExpressionException {
        writer.writeCharacters("");
        writer.flush();
        OutputStreamWriter osw = new OutputStreamWriter(output);
        osw.write(data);
        osw.flush();
    }

    protected String transformVariables(String input, Map<String, InvocableElement> elementIds, boolean enclose) throws ResourceTestException {
        input = transformRequestVariables(input, enclose);
        input = transformMPathVariables(input, elementIds, enclose);
        return input;
    }

    protected String enclose(String expression) {
        return "${" + expression + "}";
    }

    protected String enclosePageScope(String exp) {
        return "${pageScope['" + exp + "']}";
    }


    protected void printTargetCSet(XMLStreamWriter writer, String target, String property, String value) throws XMLStreamException {
        writer.writeEmptyElement("c:set");
        writer.writeAttribute("target", target);
        writer.writeAttribute("property", property);
        writer.writeAttribute("value", value);
    }

    protected void writeUnescapedData(String data, OutputStream output) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(output);
        osw.write(data);
        osw.flush();
    }

    protected void collectVariables(LinkedList<String> requestParams, LinkedList<String> mPathParams, String query,
                                    Map<String, InvocableElement> elementIds) throws ResourceTestException {
        Pattern pattern = Pattern.compile(REQUEST_PARAM_PATTERN);
        Matcher match = pattern.matcher(query);
        while (match.find()) {
            requestParams.add(query.substring(match.start(1), match.end(1)).trim());
        }
        //collect Mpath variables
        collectMPathParams(mPathParams, query, elementIds);
    }

    protected String escapeSpecialCharacters(String input) {
        String[] lines = input.split("\\r?\\n");

        StringBuilder outputString = new StringBuilder();

        for (String line : lines) {
            String escapedString = line;

            if (line.toLowerCase().matches(".*\\sle(\\s|\\b).*")) {
                escapedString = line.replaceAll("\\sle(\\s|\\b)", " <= ");
            }
            if (line.toLowerCase().matches(".*\\sge(\\s|\\b).*")) {
                escapedString = escapedString.replaceAll("\\sge(\\s|\\b)", " >= ");
            }
            if (line.toLowerCase().matches(".*\\seq(\\s|\\b).*")) {
                escapedString = escapedString.replaceAll("\\seq(\\s|\\b)", " = ");
            }
            if (line.toLowerCase().matches(".*\\sne(\\s|\\b).*")) {
                escapedString = escapedString.replaceAll("\\sne(\\s|\\b)", " != ");
            }
            if (line.toLowerCase().matches(".*\\slt(\\s|\\b).*")) {
                escapedString = escapedString.replaceAll("\\slt(\\s|\\b)", " < ");
            }
            if (line.toLowerCase().matches(".*\\sgt(\\s|\\b).*")) {
                escapedString = escapedString.replaceAll("\\sgt(\\s|\\b)", " > ");
            }

            outputString.append(escapedString).append("\n");
        }

        return outputString.toString().trim();
    }

    protected String getJspVariableForRequestParam(String param) {
        switch (param) {
            case "id":
                return "${mtgReq.id}";
            case "pid":
                return "${mtgReq.pid}";
            case "uid":
                return "${mtgReq.uid}";
            default:
                return "${mtgReq.params['" + param + "']}";
        }
    }

    protected void printPageScopeCSet(XMLStreamWriter writer, String var, String value) throws XMLStreamException {
        printScopeCSet(writer, "page", var, value);
    }

    protected void printScopeCSet(XMLStreamWriter writer, String scope, String var, String value) throws XMLStreamException {
        writer.writeEmptyElement("c:set");
        writer.writeAttribute("var", var);
        writer.writeAttribute("scope", scope);
        writer.writeAttribute("value", value);
    }

    public void getRequestParametersFromString(Set<String> paramList, String input) {

        if (StringUtils.isBlank(input)) return;

        Pattern pattern = Pattern.compile(REQUEST_PARAM_PATTERN);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String v = input.substring(matcher.start(1), matcher.end(1)).trim();
            paramList.add(v);
        }
    }

    //transforms request variables in given string
    public String transformRequestVariables(String input, boolean enclose) {
        String output = input;
        Pattern pattern = Pattern.compile(REQUEST_PARAM_PATTERN);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String v = input.substring(matcher.start(1), matcher.end(1)).trim();
            String tv;
            StringBuilder sb = new StringBuilder();
            if (enclose) {
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

            if (enclose) {
                sb.append("}");
            }

            output = output.replace("$" + v, sb.toString());
        }

        return output;
    }

    public abstract String extractFromMPath(String mpathVariable, String elementId, boolean enclose);
    /*
    public String getJspVariableForMPath(String mpathVariable, String type, String elementId, boolean enclose){
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
            
        } else if(type.equals(Script.class.getName())){
            // bus[id].name
            String locator = getMPathLocator(mpathVariable);
            transformedVariable = elementId+locator;
            
        } else if(type.equals(Text.class.getName())){
            transformedVariable = elementId;
            
        } else if(type.equals(UPLOAD_OBJECT)){
            transformedVariable = elementId;
        }
        
        sb.append(transformedVariable);
        
        if(enclose){
            sb.append("}");
        }
        
        return sb.toString();
    }*/

    //collects MPath variables for sql:param tags
    public void collectMPathParams(LinkedList<String> params, String sql, Map<String, InvocableElement> elementIds) throws ResourceTestException {
        Pattern pattern = Pattern.compile(MPATH_EXPRESSION_PATTERN);
        Matcher matcher = pattern.matcher(sql);

        while (matcher.find()) {
            params.add(sql.substring(matcher.start(), matcher.end()).trim());
        }
    }

    //collects request variables for sql:param tags
    public void collectRequestParams(LinkedList<String> params, String sql) throws ResourceTestException {
        Pattern pattern = Pattern.compile(REQUEST_PARAM_PATTERN);
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            String variable = sql.substring(matcher.start(1), matcher.end(1)).trim();
            params.add(variable);
        }
    }

    //transforms MPath variables in given string
    public String transformMPathVariables(String input, Map<String, InvocableElement> elementIds, boolean enclose) throws ResourceTestException {

        String transformed = input;
        Pattern pattern = Pattern.compile(MPATH_EXPRESSION_PATTERN);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String mpathVariable = input.substring(matcher.start(), matcher.end()).trim();
            //get element id from mpath variable
            String elementId = getMPathId(mpathVariable);

            if (!elementIds.containsKey(elementId)) {
                throw new ResourceTestException("Could not find element with ID: " + elementId);
            }
            //get type of element
            InvocableElement type = (InvocableElement) elementIds.get(elementId);
            //String tv = getJspVariableForMPath(mpathVariable, elementId, enclose);
            String tv = type.extractFromMPath(mpathVariable, elementId, enclose);

            transformed = transformed.replace(mpathVariable, tv);
        }

        return transformed;
    }

    // '%$variable%' => CONCAT('%',$variable,'%')
    public String processVariablesInLikeClause(String q) {
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
                succedent = succedent.substring(succedent.length() - stringWithinQuotes.length() + matcher.end());
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

    public String getMPathId(String path) {
        Pattern p = Pattern.compile("^\\$\\[(.*?)\\]");// $[varname]

        Matcher m = p.matcher(path);

        while (m.find()) {
            return m.group(1);
        }

        return null;
    }

    protected String getMPathLocator(String path) {
        return path.replaceFirst("\\$\\[(.*?)\\]", "");
    }
}
