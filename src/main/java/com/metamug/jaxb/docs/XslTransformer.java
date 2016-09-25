/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.jaxb.docs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public static void transform(File xmlFile, File xslFile, File htmlFile)
            throws TransformerConfigurationException, TransformerException {
        System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
        TransformerFactory factory = TransformerFactory.newInstance();
        StreamSource xslStream = new StreamSource(xslFile);
        Transformer transformer = factory.newTransformer(xslStream);
        StreamSource in = new StreamSource(xmlFile);
        StreamResult out = new StreamResult(htmlFile);
        transformer.transform(in, out);
        System.out.println("The generated HTML file is:" + htmlFile);

    }

    public static void replaceStringInFile(File file, String oldString, String newString) {
        //file reading
        try {
            FileReader fr = new FileReader(file);
            String s;

            BufferedReader br = new BufferedReader(fr);
            while ((s = br.readLine()) != null) {
                s.replaceAll(oldString, newString);
                // do something with the resulting line
            }
        } catch (IOException ix) {
            Logger.getLogger(XslTransformer.class.getName()).log(Level.SEVERE, null, ix);
        }
    }
}
