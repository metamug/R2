/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.jaxb.tests;

import com.metamug.jaxb.JAXBParser;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author deepak
 */
public class XSDValidationTest {

    @Test
    public void testValidation() throws IOException {

        File xsd = new File(JAXBParser.class.getResource("/resource.xsd").getFile());
        StreamSource xmlFile = new StreamSource(this.getClass().getResourceAsStream("/test.xml"));
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance("http://www.w3.org/XML/XMLSchema/v1.1");
        Schema schema = null;
        try {
            schema = schemaFactory.newSchema(xsd);
        } catch (SAXException ex) {
            Assert.fail("Invalid XSD File");
        }
        Validator validator = schema.newValidator();
        try {
            validator.validate(xmlFile);
        } catch (SAXException ex) {
            Assert.fail(ex.getMessage());
        }

    }
}
