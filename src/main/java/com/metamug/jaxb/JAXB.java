/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.jaxb;


import com.metamug.jaxb.gener.Request;
import com.metamug.jaxb.gener.Resource;
import java.io.File;
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
            
            for (Request req : resource.getRequestOrCreateOrRead()) {
                //System.out.println("\nrequest_name: " + req.getClass().getSimpleName());
                System.out.print("    method: " + req.getMethod().value());
                System.out.print("    out: " + req.getOut().value());
                System.out.print("    id: " + req.getId());               
                
                System.out.println("    sqltype: " + req.getSql().getType());
                System.out.println("on: " + req.getSql().getOn());
                System.out.println(req.getSql().getValue().trim());
            }

        } catch (JAXBException e) {
            e.printStackTrace();
	}
    }
    
}
