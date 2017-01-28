/**
 * ***********************************************************************
 * Freeware Licence Agreement
 *
 * This licence agreement only applies to the free version of this software.
 *
 * Terms and Conditions
 *
 * BY DOWNLOADING, INSTALLING, USING, TRANSMITTING, DISTRIBUTING OR COPYING THIS SOFTWARE ("THE SOFTWARE"), YOU AGREE TO THE TERMS OF THIS AGREEMENT (INCLUDING THE SOFTWARE LICENCE AND DISCLAIMER OF WARRANTY) WITH METAMUG THE OWNER OF ALL RIGHTS IN RESPECT OF THE SOFTWARE.
 *
 * PLEASE READ THIS DOCUMENT CAREFULLY BEFORE USING THE SOFTWARE.
 *
 * IF YOU DO NOT AGREE TO ANY OF THE TERMS OF THIS LICENCE THEN DO NOT DOWNLOAD, INSTALL, USE, TRANSMIT, DISTRIBUTE OR COPY THE SOFTWARE.
 *
 * THIS DOCUMENT CONSTITUTES A LICENCE TO USE THE SOFTWARE ON THE TERMS AND CONDITIONS APPEARING BELOW.
 *
 * The Software is licensed to you without charge for use only upon the terms of this licence, and METAMUG TECHNOLOGIES LLP (hereafter METAMUG) reserves all rights not expressly granted to you. METAMUG retains ownership of all copies of the Software.
 *
 * 1. Licence
 *
 * You may use the Software without charge.
 *
 * You may freely distribute exact copies of the Software to anyone.
 *
 * The inclusion of the Software in any shareware, freeware or similar media compilation or distribution method whereby it is made available at cost (ie. sold) is strictly prohibited.
 *
 * The selling of the Software is strictly prohibited.
 * 2. Restrictions
 *
 * METAMUG reserves the right to revoke the above distribution right at any time, for any or no reason.
 *
 * YOU MAY NOT MODIFY, ADAPT, TRANSLATE, RENT, LEASE, LOAN, SELL, ONSELL, REQUEST DONATIONS OR CREATE DERIVATIVE WORKS BASED UPON THE SOFTWARE OR ANY PART THEREOF.
 *
 * The Software contains intellectual property and to protect them you may not decompile, reverse engineer, disassemble or otherwise reduce the Software to a humanly perceivable form. You agree not to divulge, directly or indirectly, until such intellectual property cease to be confidential, for any reason not your own fault.
 *
 * 3. Termination
 *
 * This licence is effective until terminated. The Licence will terminate automatically without notice from METAMUG if you fail to comply with any provision of this Licence. Upon termination you must destroy the Software and all copies thereof. You may terminate this Licence at any time by destroying the Software and all copies thereof. Upon termination of this licence for any reason you shall continue to be bound by the provisions of Section 2 above. Termination will be without prejudice to any rights METAMUG may have as a result of this agreement.
 *
 * 4. Disclaimer of Warranty, Limitation of Remedies
 *
 * TO THE FULL EXTENT PERMITTED BY LAW, METAMUG HEREBY EXCLUDES ALL CONDITIONS AND WARRANTIES, WHETHER IMPOSED BY STATUTE OR BY OPERATION OF LAW OR OTHERWISE, NOT EXPRESSLY SET OUT HEREIN. THE SOFTWARE, AND ALL ACCOMPANYING FILES, DATA AND MATERIALS ARE DISTRIBUTED "AS IS" AND WITH NO WARRANTIES OF ANY KIND, WHETHER EXPRESS OR IMPLIED. METAMUG DOES NOT WARRANT, GUARANTEE OR MAKE ANY REPRESENTATIONS REGARDING THE USE, OR THE RESULTS OF THE USE, OF THE SOFTWARE WITH RESPECT TO ITS CORRECTNESS, ACCURACY, RELIABILITY, CURRENTNESS OR OTHERWISE. THE ENTIRE RISK OF USING THE SOFTWARE IS ASSUMED BY YOU. METAMUG MAKES NO EXPRESS OR IMPLIED WARRANTIES OR CONDITIONS INCLUDING, WITHOUT LIMITATION, THE WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE WITH RESPECT TO THE SOFTWARE. NO ORAL OR WRITTEN INFORMATION OR ADVICE GIVEN BY METAMUG, IT'S DISTRIBUTORS, AGENTS OR EMPLOYEES SHALL CREATE A WARRANTY, AND YOU MAY NOT RELY ON ANY SUCH INFORMATION OR ADVICE.
 *
 * IMPORTANT NOTE: Nothing in this Agreement is intended or shall be construed as excluding or modifying any statutory rights, warranties or conditions which by virtue of any national or state Fair Trading, Trade Practices or other such consumer legislation may not be modified or excluded. If permitted by such legislation, however, METAMUG's liability for any breach of any such warranty or condition shall be and is hereby limited to the supply of the Software licensed hereunder again as METAMUG at its sole discretion may determine to be necessary to correct the said breach.
 *
 * IN NO EVENT SHALL METAMUG BE LIABLE FOR ANY SPECIAL, INCIDENTAL, INDIRECT OR CONSEQUENTIAL DAMAGES (INCLUDING, WITHOUT LIMITATION, DAMAGES FOR LOSS OF BUSINESS PROFITS, BUSINESS INTERRUPTION, AND THE LOSS OF BUSINESS INFORMATION OR COMPUTER PROGRAMS), EVEN IF METAMUG OR ANY METAMUG REPRESENTATIVE HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. IN ADDITION, IN NO EVENT DOES METAMUG AUTHORISE YOU TO USE THE SOFTWARE IN SITUATIONS WHERE FAILURE OF THE SOFTWARE TO PERFORM CAN REASONABLY BE EXPECTED TO RESULT IN A PHYSICAL INJURY, OR IN LOSS OF LIFE. ANY SUCH USE BY YOU IS ENTIRELY AT YOUR OWN RISK, AND YOU AGREE TO HOLD METAMUG HARMLESS FROM ANY CLAIMS OR LOSSES RELATING TO SUCH UNAUTHORISED USE.
 *
 * 5. General
 *
 * All rights of any kind in the Software which are not expressly granted in this Agreement are entirely and exclusively reserved to and by METAMUG.
 *
 * This Agreement shall be governed by the laws of the State of Maharastra, India. Exclusive jurisdiction and venue for all matters relating to this Agreement shall be in courts and fora located in the State of Maharastra, India, and you consent to such jurisdiction and venue. This agreement contains the entire Agreement between the parties hereto with respect to the subject matter hereof, and supersedes all prior agreements and/or understandings (oral or written). Failure or delay by METAMUG in enforcing any right or provision hereof shall not be deemed a waiver of such provision or right with respect to the instant or any subsequent breach. If any provision of this Agreement shall be held by a court of competent jurisdiction to be contrary to law, that provision will be enforced to the maximum extent permissible, and the remaining provisions of this Agreement will remain in force and effect.
 */
package com.metamug.jaxb.tests;

import com.metamug.parser.RPXParser;
import com.metamug.schema.Execute;
import com.metamug.schema.Param;
import com.metamug.schema.Query;
import com.metamug.schema.Request;
import com.metamug.schema.Resource;
import com.metamug.schema.Sql;
import com.metamug.schema.Update;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author deepak+anish
 */
public class XSDValidationTest {

    private static String XML_FILE_PATH;

    private static String RES_VER, RES_DESC, RES_ID, RES_PARENT, RES_IS_AUTH;

    private static String EXEC_CLASS;

    private static String REQ_METHOD, REQ_DESC;

    private static String PARAM_NAME, PARAM_REQUIRED;

    private static String SQL_TYPE, SQL_WHEN, SQL_CLASS, SQL_VAL;

    private static String QUERY_TYPE, QUERY_CLASS, QUERY_VAL;

    private static String UPDATE_TYPE, UPDATE_WHEN, UPDATE_VAL;

    private static String[] testArray;
    private static RPXParser parser;
    private File resourceFile;

    @Before
    public void init() {
        XML_FILE_PATH = "/test.xml";
        resourceFile = new File(this.getClass().getResource(XML_FILE_PATH).getFile());
        parser = new RPXParser("/opt/tomcat8/api", "testApp", resourceFile);

        RES_VER = "1.0";
        RES_DESC = "This works";
        RES_ID = "testIdString";
        RES_PARENT = "exam";
        RES_IS_AUTH = "true";

        EXEC_CLASS = "execute_classname";

        REQ_METHOD = "POST";
        REQ_DESC = "POST Desc 1";

        PARAM_NAME = "param1";
        PARAM_REQUIRED = "true";

        QUERY_TYPE = "query";
        QUERY_CLASS = "com.mtg.query";
        QUERY_VAL = "SELECT * from table";

        UPDATE_TYPE = "update";
        UPDATE_WHEN = "$q eq 1";
        UPDATE_VAL = "UPDATE table SET column=$q WHERE id=$id";

        SQL_TYPE = "update";
        SQL_WHEN = "$a gt 1";
        SQL_CLASS = "sql_classname";
        SQL_VAL = "INSERT INTO table VALUE ($a)";

        testArray = new String[]{RES_VER, RES_DESC, RES_ID, RES_PARENT, RES_IS_AUTH, REQ_METHOD, REQ_DESC, PARAM_NAME, PARAM_REQUIRED,
            EXEC_CLASS, SQL_TYPE, SQL_WHEN, SQL_CLASS, SQL_VAL, QUERY_TYPE, QUERY_CLASS, QUERY_VAL,
            UPDATE_TYPE, UPDATE_WHEN, UPDATE_VAL};
    }

    //validate xml against xsd
    //yet to add validation code for param validation
    @Test
    public void testValidation1() throws IOException, URISyntaxException {
        try {
            Resource rs = parser.parseFromXml();
            //System.out.println(rs);
            String resourceIsAuth = Boolean.toString(rs.isAuth());
            String resourceVersion = Double.toString(rs.getVersion());
            String method = null;
            String reqDesc = null;
            String paramName = null, paramRequired = null;
            String execClass = null;
            String sqlType = null, sqlWhen = null, sqlClass = null, sqlValue = null;
            String queryType = null, queryClass = null, queryValue = null;
            String updateType = null, updateWhen = null, updateValue = null;

            if (rs != null) {
                List<Request> requests = rs.getRequest();
                if (!requests.isEmpty()) {
                    for (Request request : requests) {
                        reqDesc = request.getDesc();
                        method = request.getMethod().value();
                        for (Param p : request.getParam()) {
                            paramName = p.getName();
                            paramRequired = Boolean.toString((Boolean) p.isRequired());
                        }
                        for (Execute e : request.getExecute()) {
                            execClass = e.getClassName();
                        }
                        for (Sql sql : request.getSql()) {
                            sqlType = sql.getType().value();
                            sqlWhen = sql.getWhen();
                            sqlClass = sql.getClassname();
                            sqlValue = sql.getValue().trim();
                        }
                        for (Query q : request.getQuery()) {
                            queryType = q.getType().value();
                            queryClass = q.getClassname();
                            queryValue = q.getValue().trim();
                        }
                        for (Update u : request.getUpdate()) {
                            updateType = u.getType().value();
                            updateWhen = u.getWhen();
                            updateValue = u.getValue().trim();
                        }
                    }
                } else {
                    Assert.fail("No <Request> element found!");
                }
                String[] resultArray = new String[]{resourceVersion, rs.getDesc(), rs.getId(), rs.getParent(), resourceIsAuth, method, reqDesc, paramName, paramRequired,
                    execClass, sqlType, sqlWhen, sqlClass, sqlValue, queryType, queryClass, queryValue,
                    updateType, updateWhen, updateValue};
                Assert.assertArrayEquals(testArray, resultArray);
            }
        } catch (JAXBException ex) {
            Assert.fail(ex.toString());
        } catch (SAXException ex) {
            Assert.fail(ex.getMessage());
        } catch (FileNotFoundException | XMLStreamException | XPathExpressionException | TransformerException ex) {
            Logger.getLogger(XSDValidationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void TestValidation2() {
        try {
            Resource rs = parser.parseFromXml();
            String[] expectedResultArray = new String[]{"false", "1.1"};
            String[] resultArray = new String[]{Boolean.toString(rs.isAuth()), Double.toString(rs.getVersion())};
            Assert.assertArrayEquals(expectedResultArray, resultArray);
        } catch (JAXBException ex) {
            Assert.fail(ex.toString());
        } catch (SAXException ex) {
            Assert.fail(ex.getMessage());
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
        } catch (XMLStreamException | XPathExpressionException | TransformerException | URISyntaxException ex) {
            Logger.getLogger(XSDValidationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void TestValidation3() {
        try {
            Resource rs = parser.parseFromXml();
            Assert.assertEquals(rs.isAuth(), false);
        } catch (JAXBException ex) {
            Assert.fail(ex.toString());
        } catch (SAXException ex) {
            Assert.fail(ex.getMessage());
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
        } catch (XMLStreamException | XPathExpressionException | TransformerException | URISyntaxException ex) {
            Logger.getLogger(XSDValidationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
