/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.jaxb;


import com.metamug.jaxb.gener.Request;
import com.metamug.jaxb.gener.Resource;
import com.metamug.jaxb.gener.Sql;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author anish
 */
public class JAXB {
    public static void main(String[] args){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Resource.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Resource resource = (Resource) jaxbUnmarshaller.unmarshal(JAXB.class.getResourceAsStream("/apple.xml"));
            
            System.out.println("table: " + resource.getTable());
            System.out.print("    version: " + Float.toString(resource.getVersion()));
            //
            for (Request req : resource.getRequestOrCreateOrRead()) {
                System.out.println("\n--------------------------------------------------------------");
                System.out.print("    method: " + req.getMethod().value());
                System.out.print("    out: " + req.getOut().value());
                System.out.print("    id: " + req.getId());               
                
                for(Sql sql : req.getSql()){
                    System.out.println("    sqltype: " + sql.getType());
                    System.out.println("on: " + sql.getOn());
                    System.out.println(sql.getValue().trim()); 
                }
            }
            //it is because  of the spcae in the print statements
        } catch (JAXBException e) {
            e.printStackTrace();
	}
    }
    
}
