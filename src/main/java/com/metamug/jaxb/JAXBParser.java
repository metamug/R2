package com.metamug.jaxb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.metamug.jaxb.docs.ApiDocGenerator;
import com.metamug.jaxb.gener.Execute;
import com.metamug.jaxb.gener.Request;
import com.metamug.jaxb.gener.Resource;
import com.metamug.jaxb.gener.Sql;
import com.metamug.jaxb.docs.XslTransformer;
import com.metamug.jaxb.gener.Param;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.apache.commons.io.FilenameUtils;
import org.xml.sax.SAXException;

/**
 *
 * @author anish
 */
public class JAXBParser {

    public static void main(String[] args) throws SAXException {
        File xml = new File(JAXBParser.class.getResource("/apple.xml").getFile());
        File xsd = new File(JAXBParser.class.getResource("/apple.xsd").getFile());
        Source xmlFile = new StreamSource(xml);
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(xsd);
        Validator validator = schema.newValidator();
        try {
            validator.validate(xmlFile);
            Resource resource = parse();
            if (resource != null) {
                createHtml(resource);
            }
        } catch (SAXException | IOException ex) {
            System.out.println(xmlFile.getSystemId() + " is NOT valid.");
            System.out.println("Reason: " + ex.getMessage());
        }
        
        ApiDocGenerator.generate("C:\\c4\\metamug\\RPXParser\\doctest");
    }
    
    public static void createHtml(Resource resource) {
        File xml = new File(JAXBParser.class.getResource("/apple.xml").getFile());
        File xsl = new File(JAXBParser.class.getResource("/resource.xsl").getFile());
        File outHtml = new File("../rpx-parser/src/main/resources/"
                + FilenameUtils.removeExtension(xml.getName()) + ".html");
        try {
            XslTransformer.transform(xml, xsl, outHtml);
        } catch (TransformerException ex) {
            Logger.getLogger(JAXBParser.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public static Resource parse() {
        Resource resource = new Resource();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Resource.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            resource = (Resource) jaxbUnmarshaller.unmarshal(
                    JAXBParser.class.getResourceAsStream("/apple.xml"));

            System.out.println("    version: " + resource.getVersion());
            System.out.println("    desc: " + resource.getDesc());

            for (Request req : resource.getRequestOrCreateOrRead()) {
                System.out.println("\n--------------------------------------------------------------");
                System.out.println("\n-----   ----   ------R E Q U E S T------   ----   -----");
                System.out.println("\n--------------------------------------------------------------");
                    
                System.out.print("    method: " + req.getMethod().value());
                System.out.println("    isItem: " + Boolean.toString(req.isItem()));
                System.out.println("    desc: " + req.getDesc());

                if(!req.getParam().isEmpty()){
                    System.out.println("----------------------PARAMS-----------------------");
                    for(Param param : req.getParam()){
                        System.out.println("   paramName: " + param.getParamName());
                        System.out.println("   isBlank? : " + Boolean.toString(param.isBlank()));
                        System.out.println("   isNum? : " + Boolean.toString(param.isNum()));
                        System.out.println("   min: " + param.getMin());
                        System.out.println("   max: " + param.getMax());
                        System.out.println("   pattern: " + param.getPattern());
                        System.out.println("   exists: " + param.getExists());
                        System.out.println("--------------------------------------------");
                    }
                }else{
                    System.out.println("------------------Param List empty..------------------");
                }
                
                if(!req.getExecute().isEmpty()){
                    System.out.println("----------------------EXECUTE-----------------------");
                    for(Execute execute : req.getExecute()){
                        System.out.println("   className: " + execute.getClassName());
                        System.out.println("--------------------------------------------");
                    }
                }else{
                    System.out.println("------------------Execute List empty..------------------");
                }
                
                if(!req.getSql().isEmpty()){
                    System.out.println("----------------------SQL-----------------------");
                    for (Sql sql : req.getSql()) {
                        System.out.println("    sqltype: " + sql.getType());
                        System.out.println("on: " + sql.getWhen());
                        System.out.println("className: " + sql.getClassName());
                        System.out.println(sql.getValue().trim());
                        System.out.println("--------------------------------------------");
                    }
                }else{
                    System.out.println("------------------Sql List empty..-------------------");
                }
            }
        } catch (JAXBException ex) {
            Logger.getLogger(JAXBParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resource;
    }
}
