/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.objectreturn.tests;

import com.metamug.objectreturn.tests.testclasses.Customer;
import com.metamug.objectreturn.tests.testclasses.PhoneNumber;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.junit.Before;
import org.junit.Test;

public class ObjectReturnTest {
   
    //private final String className = "com.metamug.objectreturn.tests.testclasses.Customer";
    private Customer customer;
    
    @Before
    public void init(){
        customer = new Customer(1,"Kaustubh","Gosling");
        //customer = new Customer();
        
        PhoneNumber pn = new PhoneNumber();
        pn.setNum("9128992849");
        pn.setType("mobile");
        
        customer.addPhoneNumber(pn);
        customer.getClass();
    }
    
    //method(object, accept-type)
    
    @Test
    public void ObjectToJsonTest() throws ClassNotFoundException, JAXBException{
        Object returnedObject = customer;
        //Class jaxbClass = Class.forName(className);
        JAXBContext jc = JAXBContext.newInstance(returnedObject.getClass());
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
        marshaller.marshal(customer, System.out);
    } 
    
}
