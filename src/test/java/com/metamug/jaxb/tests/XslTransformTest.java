/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.jaxb.tests;

import com.metamug.jaxb.JAXBParser;
import com.metamug.jaxb.docs.XslTransformer;
import java.io.File;
import javax.xml.transform.TransformerException;
import org.junit.Before;

public class XslTransformTest {
    private File xml;
    private File xsl;
    
    @Before
    public void init(){
        xml = new File(JAXBParser.class.getResource("/resource.xsd").getFile());
        xsl = new File(JAXBParser.class.getResource("/xs3p.xsl").getFile());
    }
    
    public void testTransformation() throws TransformerException{
        XslTransformer.transform(xml, xsl, new File("/XsdDoc.html"));
    }
    
}
