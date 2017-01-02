/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.objectreturn;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.eclipse.persistence.jaxb.MarshallerProperties;

/**
 *
 * @author anishhirlekar
 */
public class Converter {
    //resultType can be "application/json" or "application/xml as specified by the accept header"
    public static String convert(Object returnObject, String resultType) throws JAXBException{
        StringWriter marshalledResult = new StringWriter();
        JAXBContext jc = JAXBContext.newInstance(returnObject.getClass());
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, resultType);
        //marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
        marshaller.marshal(returnObject, marshalledResult);
        return marshalledResult.toString();
    }
    
}
