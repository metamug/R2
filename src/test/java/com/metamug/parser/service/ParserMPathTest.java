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
import com.metamug.schema.Execute;
import com.metamug.schema.Sql;
import com.metamug.schema.Xrequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author anishhirlekar
 */
public class ParserMPathTest {
    
    HashMap<String,String> elementIds = new HashMap<>();
    
    @Before
    public void init(){
        elementIds.put("sqlresult", Sql.class.getName());
        elementIds.put("xreq", Xrequest.class.getName());
        elementIds.put("exec", Execute.class.getName());
        
    }
    
    @Test
    public void getMPathIdRegex(){
        String[] paths = {"$[xreq].body.args[2].foo","$[xreq].body.args.foo1",
            "$[res].id.name","$[res][0].rating"};
        String[] expected = {"xreq","xreq","res","res"};
        
        Assert.assertEquals(ParserService.getMPathId(paths[0]),expected[0]);
        Assert.assertEquals(ParserService.getMPathId(paths[1]),expected[1]);
        Assert.assertEquals(ParserService.getMPathId(paths[2]),expected[2]);
        Assert.assertEquals(ParserService.getMPathId(paths[3]),expected[3]);        
    }
    
    @Test
    public void detectMPathInQueryRegex(){
        String testString = "SELECT $[xreq].body.args.foo1,$[xreq].body.args[2].foo AS 'foo1'";
        
        Pattern pattern = Pattern.compile(ParserService.MPATH_EXPRESSION_PATTERN);
        Matcher matcher = pattern.matcher(testString);
        
        List<String> extracted = new ArrayList<>();
        while (matcher.find()) {
            String v = testString.substring(matcher.start(), matcher.end()).trim();
            extracted.add(v);
        }
        
        List<String> expected = new ArrayList<>();
        expected.add("$[xreq].body.args.foo1");
        expected.add("$[xreq].body.args[2].foo");
        
        Assert.assertEquals(expected, extracted);
    }
    
    
    @Test
    public void detectMPathInJsonRegex(){
        String testString = "{\n" +
"                    \"foo1\": $[res].id.name,\n" +
"                    \"foo2\": $[res][0].rating\"\n" +
"                }";
        
        Pattern pattern = Pattern.compile(ParserService.MPATH_EXPRESSION_PATTERN);
        Matcher matcher = pattern.matcher(testString);
        
        List<String> extracted = new ArrayList<>();
        while (matcher.find()) {
            String v = testString.substring(matcher.start(), matcher.end()).trim();
            extracted.add(v);
        }
        
        List<String> expected = new ArrayList<>();
        expected.add("$[res].id.name");
        expected.add("$[res][0].rating");
        
        Assert.assertEquals(expected, extracted);
    }  
    
    @Test
    public void transformMPathXReq(){
        String input = "{\n" +
"                    \"foo1\": $[sqlresult][1].id.name,\n" +
"                    \"foo2\": $[sqlresult][0].rating\"\n" +
"                }";
        String expected = "{\n" +
"                    \"foo1\": ${sqlresult.rows[1].id.name},\n" +
"                    \"foo2\": ${sqlresult.rows[0].rating}\"\n" +
"                }";
        try {
            String output = ParserService.transformMPathVariables(input, elementIds);
            //System.out.println(output);
            Assert.assertEquals(output,expected);
        } catch (ResourceTestException ex) {
            Logger.getLogger(ParserMPathTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Test
    public void transformMPathExec(){
        String input = "{\n" +
"                   \"foo1\": \"Hello\",\n" +
"                   \"foo2\": ${exec.name}\"\n" +
"                   \"foo3\": ${exec.contact.phone}\"\n" +
"               }";
        String expected = "{\n" +
"                   \"foo1\": \"Hello\",\n" +
"                   \"foo2\": ${exec.name}\"\n" +
"                   \"foo3\": ${exec.contact.phone}\"\n" +
"               }";
        try {
            String output = ParserService.transformMPathVariables(input, elementIds);
            //System.out.println(output);
            Assert.assertEquals(output,expected);
        } catch (ResourceTestException ex) {
            Logger.getLogger(ParserMPathTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Test
    public void transformMPathSql(){
        String input = "SELECT $[xreq].body.args.foo1,$[xreq].body.args[2].foo[0].bar AS 'foo1'";
        String expected = "SELECT ${m:jsonPath('$.body.args.foo1',xreq )},${m:jsonPath('$.body.args[2].foo[0].bar',xreq )} AS 'foo1'";
        
        try {
            String output = ParserService.transformMPathVariables(input, elementIds);
            Assert.assertEquals(output,expected);
        } catch (ResourceTestException ex) {
            Logger.getLogger(ParserMPathTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Test
    public void transformMPathInvalidId(){
        String input = "SELECT $[invalidId].body.args.foo1,$[xreq].body.args[2].foo[0].bar AS 'foo1'";
        
        try {
            String output = ParserService.transformMPathVariables(input, elementIds);
            //Assert.assertEquals(output,null);
        } catch (ResourceTestException ex) {
            //Logger.getLogger(ParserMPathTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.assertTrue(ex.getMessage().contains("Could not find element with ID"));
        }
    }
    
    @Test
    public void sqlParamTest() throws ResourceTestException{
        String input = "SELECT $[exec].body.args.foo1,$reqvar1,$[sqlresult][1].id.name,$reqvar2,$[xreq].body.args[2].foo[0].bar AS 'foo1'"
                + " WHERE id=$id";
        
        String expected = "SELECT ? ,? ,? ,? ,?  AS 'foo1' WHERE id=?\n" +
"<sql:param value=\"${exec.body.args.foo1}\"/>\n" +
"<sql:param value=\"${mtgReq.params['reqvar1']}\"/>\n" +
"<sql:param value=\"${sqlresult.rows[1].id.name}\"/>\n" +
"<sql:param value=\"${mtgReq.params['reqvar2']}\"/>\n" +
"<sql:param value=\"${m:jsonPath('$.body.args[2].foo[0].bar',xreq )}\"/>\n" +
"<sql:param value=\"${mtgReq.id}\"/>";
        
        Sql sql = new Sql();
        sql.setValue(input);
        
        ParserService p = new ParserService();
        String op = p.getSqlParams(sql,elementIds);
        Assert.assertEquals(expected.trim(), op.trim());
        System.out.println(op);
        //System.out.println(expected);
    }
}
