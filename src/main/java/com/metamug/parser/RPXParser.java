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
package com.metamug.parser;

import com.metamug.docgenerator.DocGenerator;
import com.metamug.schema.Resource;
import com.metamug.xslttransformer.XslTransformer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPathExpressionException;
import org.apache.commons.io.FilenameUtils;
import org.xml.sax.SAXException;

/**
 *
 * @author anish
 */
public class RPXParser {

    private final String appDirectory;
    private final String appName;
    private final File xmlResourceFile;

    /**
     *
     * @param appDirectory Application path where docs has to be generated.
     * @param appName App name
     * @param resourceFile XML file to parsed.
     */
    public RPXParser(String appDirectory, String appName, File resourceFile) {
        this.appDirectory = appDirectory;
        this.appName = appName;
        this.xmlResourceFile = resourceFile;
    }

    private void createHtml(Resource resource) throws IOException, FileNotFoundException, XMLStreamException, XPathExpressionException, TransformerException, URISyntaxException {
        InputStream xsl = getClass().getClassLoader().getResourceAsStream("resource.xsl");
        if (!new File(appDirectory + File.separator + appName + File.separator + "docs/v" + resource.getVersion()).exists()) {
            Files.createDirectories(Paths.get(appDirectory + File.separator + appName + File.separator + "docs/v" + resource.getVersion()));
        }
        File outHtml = new File(appDirectory + File.separator + appName + File.separator + "docs/v" + resource.getVersion() + "/" + FilenameUtils.removeExtension(xmlResourceFile.getName()) + ".html");
        XslTransformer.transform(xmlResourceFile, xsl, outHtml);
    }

    public Resource parseFromXml() throws JAXBException, SAXException, IOException, FileNotFoundException,
            XMLStreamException, XPathExpressionException, TransformerException, URISyntaxException {
        StreamSource xmlFile = new StreamSource(xmlResourceFile);
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.1");
        Schema schema = schemaFactory.newSchema(getClass().getClassLoader().getResource("resource.xsd"));
        Validator validator = schema.newValidator();
        validator.validate(xmlFile);
        JAXBContext jaxbContext = JAXBContext.newInstance(Resource.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Resource resource = (Resource) jaxbUnmarshaller.unmarshal(xmlResourceFile);
        if (resource != null) {
            createHtml(resource);
        }
        new DocGenerator().generate(appDirectory + File.separator + appName);

        return resource;
    }
    /*
    private void unescapeXmlFile(File xmlFile) throws FileNotFoundException, IOException {        
        List<String> lines;
        
        try (FileReader fr = new FileReader(xmlFile);BufferedReader br = new BufferedReader(fr);) {            
            String line;
            lines = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                line = StringEscapeUtils.unescapeXml(line);
                System.out.println(line);
                lines.add(StringEscapeUtils.unescapeXml(line));
            }
        }

        FileWriter fw = new FileWriter(xmlFile);
        try (BufferedWriter out = new BufferedWriter(fw)) {
            for(String s : lines){
                out.write(s);
            }
            out.flush();
        }
    }*/
}
