/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.jaxb.xslt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author anish
 */
public class XslTransformer {
    
    public static void transform(File xmlFile, File xslFile, File outputHTML)
            throws TransformerConfigurationException, TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        StreamSource xslStream = new StreamSource(xslFile);
        Transformer transformer = factory.newTransformer(xslStream);
        StreamSource in = new StreamSource(xmlFile);
        StreamResult out = new StreamResult(outputHTML);
        transformer.transform(in, out);
        System.out.println("---------------------------------------------");
        System.out.println("---------------------------------------------");
        System.out.println("The generated HTML file is:" + outputHTML);
    }
    
}
