/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.jaxb.tests;

import com.metamug.jaxb.JAXBParser;
import com.metamug.jaxb.gener.Param;
import com.metamug.jaxb.gener.Request;
import com.metamug.jaxb.gener.Resource;
import com.metamug.jaxb.gener.Sql;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
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
    
    private static String RES_VER;
    private static String RES_DESC;
    
    private static String REQ_METHOD;
    
    private static String PARAM_NAME;
    private static String PARAM_REQUIRED;
    
    private static String SQL_TYPE;
    private static String SQL_WHEN;
    
    private static String[] testArray;
    
    @Before
    public void init(){
        //initialize test values
        
        XML_FILE_PATH = "/test.xml";
        
        RES_VER = "1.0";
        RES_DESC = "This works";
    
        REQ_METHOD = "POST";
        
        PARAM_NAME = "param1";
        PARAM_REQUIRED = "true";
        
        SQL_TYPE = "update";
        SQL_WHEN = "$a gt 1";
        
        testArray = new String[]{RES_VER,RES_DESC,REQ_METHOD,PARAM_NAME,
                                        PARAM_REQUIRED,SQL_TYPE,SQL_WHEN};
    }
    
    //validate xml against xsd
    //yet to add validation code for param validation
    @Test
    public void testValidation() throws IOException {
        File xsd = new File(JAXBParser.class.getResource("/resource.xsd").getFile());
        StreamSource xmlFile = new StreamSource(this.getClass().getResourceAsStream(XML_FILE_PATH));
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.1");
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
        Resource rs = null;
        try {
            rs = JAXBParser.parseFromXml(XML_FILE_PATH);
        } catch (JAXBException ex) {
            Assert.fail(ex.getMessage());
        }
        
        String method = null;
        String paramName = null;
        String paramRequired = null;
        String sqlType = null;
        String sqlWhen = null;
        
        List<Request> requests = rs.getRequest();
        if(!requests.isEmpty()){
            for(Request request : requests){
                method = request.getMethod().value();
                for(Param p : request.getParam()){
                    paramName = p.getName();
                    paramRequired = Boolean.toString((Boolean)p.isRequired());
                }
                for(Sql sql : request.getSql()){
                    sqlType = sql.getType().value();
                    sqlWhen = sql.getWhen();
                }
            }
        }else{
            Assert.fail("No <Request> element found!");
        }
        
        String[] resultArray = new String[]{rs.getVersion(),rs.getDesc(),
                        method,paramName,paramRequired,sqlType,sqlWhen};
        
        Assert.assertArrayEquals(testArray, resultArray);
    }
}
