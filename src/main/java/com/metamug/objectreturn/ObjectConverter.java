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
public class ObjectConverter {
    //resultType can be "application/json" or "application/xml as specified by the accept header"
    //if object is of type String, the object will be returned as it is and accept header will be ignored
    public static String convert(Object returnObject, String acceptHeader) throws JAXBException{
        if(returnObject instanceof String){
            return (String)returnObject;
        }
        StringWriter marshalledResult = new StringWriter();
        JAXBContext jc = JAXBContext.newInstance(returnObject.getClass());
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, acceptHeader);
        //marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
        marshaller.marshal(returnObject, marshalledResult);
        return marshalledResult.toString();
    }
    
}
