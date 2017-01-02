/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.objectreturn.tests;

import com.metamug.objectreturn.Converter;
import com.metamug.objectreturn.tests.testclasses.Customer;
import com.metamug.objectreturn.tests.testclasses.PhoneNumber;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.junit.Before;
import org.junit.Test;

public class ObjectReturnTest {
    private final String TYPE_JSON = "application/json";
    private final String TYPE_XML = "application/xml";
    private Customer customer;
    
    @Before
    public void init(){
        customer = new Customer(1,"Kaustubh","Gosling");
        PhoneNumber pn = new PhoneNumber();
        pn.setNum("9128992849");
        pn.setType("mobile");
        customer.addPhoneNumber(pn);
        customer.getClass();
    }
    
    @Test
    public void ObjectToJsonTest() throws ClassNotFoundException, JAXBException{
        String resultJson = Converter.convert(customer, TYPE_JSON);
        System.out.println(resultJson);
    } 
    
    @Test
    public void ObjectToXmlTest() throws JAXBException{
        String resultXml = Converter.convert(customer, TYPE_XML);
        System.out.println(resultXml);
    }
}
