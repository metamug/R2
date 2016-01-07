/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.jaxb;


import com.metamug.jaxb.gener.Request;
import com.metamug.jaxb.gener.Resource;
import com.metamug.jaxb.gener.Sql;
import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

/**
 *
 * @author anish
 */
public class JAXBParser {
    public static void main(String[] args) throws SAXException{
        File xml = new File(JAXBParser.class.getResource("/apple.xml").getFile());
        File xsd = new File(JAXBParser.class.getResource("/apple.xsd").getFile());
        Source xmlFile = new StreamSource(xml);
	SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	Schema schema = schemaFactory.newSchema(xsd);
	Validator validator = schema.newValidator();
	try {
            validator.validate(xmlFile);
            parse();
        } catch (SAXException|IOException e) {
	    System.out.println(xmlFile.getSystemId() + " is NOT valid.");
	    System.out.println("Reason: " + e.getMessage());
	}		
       
    }
    
    public static void parse(){
        try {            
            JAXBContext jaxbContext = JAXBContext.newInstance(Resource.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Resource resource = (Resource) jaxbUnmarshaller.unmarshal(
                    JAXBParser.class.getResourceAsStream("/apple.xml"));
            
            System.out.println("table: " + resource.getTable());
            System.out.println("    version: " + Float.toString(resource.getVersion()));
            System.out.println("    desc: " + resource.getDesc());
            System.out.print("\n--------------------------------------------------------------");
            
            for (Request req : resource.getRequestOrCreateOrRead()) {
                System.out.println("\n--------------------------------------------------------------");
                System.out.print("    method: " + req.getMethod().value());
                System.out.print("    out: " + req.getOut().value());
                System.out.println("    id: " + req.getId());               
                System.out.println("    desc: " + req.getDesc());
                
                for(Sql sql : req.getSql()){
                    System.out.println("    sqltype: " + sql.getType());
                    System.out.println("on: " + sql.getOn());
                    System.out.println(sql.getValue().trim()); 
                }
            }
        } catch (JAXBException e) {
            e.printStackTrace();
	}
    }
    
}
