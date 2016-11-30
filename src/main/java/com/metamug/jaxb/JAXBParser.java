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
import com.metamug.jaxb.util.InputValidationException;
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
        File xsd = new File(JAXBParser.class.getResource("/resource.xsd").getFile());
        Source xmlFile = new StreamSource(xml);
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance("http://www.w3.org/XML/XMLSchema/v1.1");
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

            System.out.println("    Version: " + resource.getVersion());
            System.out.println("    Desc: " + resource.getDesc());
            
            for (Request req : resource.getRequest()) {
                System.out.println("\n-----   ----   ------R E Q U E S T------   ----   -----");
                    
                System.out.print("    Method: " + req.getMethod().value());
                System.out.println("    Item: " + Boolean.toString(req.isItem()));
                System.out.println("    Desc: " + req.getDesc());
                if(null!=req.getStatus()){    
                    System.out.println("    Status: " + Integer.toString(req.getStatus()));
                }else{
                    System.out.println("    Status: not specified");
                }
                    
                if(!req.getParam().isEmpty()){
                    System.out.println("---PARAMS:---");
                    for(Param param : req.getParam()){
                        System.out.println("   paramName: " + param.getName());
                        System.out.println("   isRequired? : " + Boolean.toString((Boolean)param.isRequired()));
                        System.out.println("   isBlank? : " + Boolean.toString((Boolean)param.isBlank()));
                        System.out.println("   isNum? : " + Boolean.toString((Boolean)param.isNum()));
                        System.out.println("   min: " + param.getMin());
                        System.out.println("   max: " + param.getMax());
                        System.out.println("   minLength: " + param.getMinLen());
                        System.out.println("   maxLength: " + param.getMaxLen());
                        System.out.println("   pattern: " + param.getPattern());
                        System.out.println("   exists: " + param.getExists());
                        System.out.println(" ++++++ ");
                    }
                    System.out.println("---^^^^^^^---");
                }else{
                    System.out.println("///Param List Empty///");
                }
                if(!req.getExecute().isEmpty()){
                    System.out.println("---EXECUTE---");
                    for(Execute ex : req.getExecute()){
                        System.out.println("   ClassName: " + ex.getClassName());
                    }
                    System.out.println("---^^^^^^^---");
                }else{
                    System.out.println("///Execute List Empty///");
                }
                if(!req.getSql().isEmpty()){
                    System.out.println("---SQL---");
                    for(Sql sql : req.getSql()){
                        System.out.println("    sqltype: " + sql.getType());
                        System.out.println("When: " + sql.getWhen());
                        System.out.println("ClassName: " + sql.getClassName());
                        System.out.println(sql.getValue().trim());
                    }
                    System.out.println("---^^^---");
                }
                /*if(!req.getDescOrParamOrExecute().isEmpty()){
                    for(Object child : req.getDescOrParamOrExecute()){
                        if(child instanceof String){
                            System.out.println("    Desc: " + (String)child);
                        }
                        else if(child instanceof Param){
                            Param param = (Param)child;
                            System.out.println("---PARAM:---");
                            System.out.println("   paramName: " + param.getName());
                            System.out.println("   isRequired? : " + Boolean.toString(param.isRequired()));
                            System.out.println("   isBlank? : " + Boolean.toString(param.isBlank()));
                            System.out.println("   isNum? : " + Boolean.toString(param.isNum()));
                            System.out.println("   min: " + param.getMin());
                            System.out.println("   max: " + param.getMax());
                            System.out.println("   minLength: " + param.getMinLen());
                            System.out.println("   maxLength: " + param.getMaxLen());
                            System.out.println("   pattern: " + param.getPattern());
                            System.out.println("   exists: " + param.getExists());
                            System.out.println("---^^^^^^---");
                        }
                        else if(child instanceof Execute){
                            System.out.println("---EXECUTE---");
                            System.out.println("   ClassName: " + ((Execute) child).getClassName());
                            System.out.println("---^^^^^^^---");
                        }
                        else if(child instanceof Sql){
                            Sql sql = (Sql)child;
                            System.out.println("---SQL---");
                            System.out.println("    sqltype: " + sql.getType());
                            System.out.println("When: " + sql.getWhen());
                            System.out.println("ClassName: " + sql.getClassName());
                            System.out.println(sql.getValue().trim());
                            System.out.println("---^^^---");
                        }
                    }
                }else{
                    System.out.println("No Child Elements!");
                }*/
                System.out.println("\n-----   ----   -- ///////////////////// --   ----   -----");             
            }
        } catch (JAXBException ex) {
            Logger.getLogger(JAXBParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resource;
    }
    
    public static void validateParam(Param param, String value) throws InputValidationException{
        if("".equals(value)){
            if(!(Boolean)param.isBlank()){
                throw new InputValidationException("Parameter is empty!");
            }
        }
        if((Boolean)param.isNum()){
            String regex = "[0-9]+";
            if(!value.matches(regex)){
                throw new InputValidationException("Parameter is not a number!");
            }
            if(null != param.getMax()){
                long val = Long.parseLong(value);
                long maxVal = Long.parseLong(param.getMax());
                if(val > maxVal){
                    throw new InputValidationException("Parameter value exceeds maximum value!");
                }
            }
            if(null != param.getMin()){
                long val = Long.parseLong(value);
                long minVal = Long.parseLong(param.getMin());
                if(val < minVal){
                    throw new InputValidationException("Parameter value is less than minimum value!");
                }
            }
        }else{
            if(null != param.getPattern()){
                if(!value.matches(param.getPattern())){
                    throw new InputValidationException("Parameter value does not match given regex pattern!");
                }
            }
            if(null != param.getMaxLen()){
                int maxLength = Integer.parseInt(param.getMaxLen());
                if(value.length() > maxLength){
                    throw new InputValidationException("Parameter length is greater than maximum length!");
                }
            }
            if(null != param.getMinLen()){
                int minLength = Integer.parseInt(param.getMinLen());
                if(value.length() < minLength){
                    throw new InputValidationException("Parameter length is smaller than minimum length!");
                }
            }
        }        
    }
}
