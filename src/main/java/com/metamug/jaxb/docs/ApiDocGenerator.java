/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.jaxb.docs;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.xpath.XPathExpressionException;

/**
 *
 * @author anish
 */
public class ApiDocGenerator {

    OutputStream output;
    XMLOutputFactory factory = XMLOutputFactory.newInstance();
    XMLStreamWriter writer;

    public void generate(String appDirectory) throws IOException {

        try {
            List<File> htmlFilenameList = new ArrayList<>();
            File docsDirectory = new File(appDirectory + "/docs");
            File[] versionDirecotries = docsDirectory.listFiles();
            for (File versions : versionDirecotries) {
                File[] versionFile = versions.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().endsWith(".html");
                    }
                });
                if (versionFile != null) {
                    htmlFilenameList.addAll(Arrays.asList(versionFile));
                }
            }
            output = new FileOutputStream(appDirectory + "/docs/list" + ".html");
            writer = new IndentingXMLStreamWriter(factory.createXMLStreamWriter(output, "UTF-8"));
            writeEscapedCharacters("<!DOCTYPE html>");
            writer.writeStartElement("head");
            writer.writeStartElement("link");
            writer.writeAttribute("rel", "stylesheet");
            writer.writeAttribute("href", "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css");
            writer.writeAttribute("integrity", "sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u");
            writer.writeAttribute("crossorigin", "anonymous");
            writer.writeEndElement(); //End </link>
            writer.writeEndElement(); //End of </head>
            writer.writeStartElement("body");
            writer.writeStartElement("div");
            writer.writeAttribute("class", "container");
            writer.writeAttribute("style", "padding-top:5px");
            writer.writeStartElement("div");
            writer.writeAttribute("class", "page-header");
            writer.writeStartElement("h4");
            writeEscapedCharacters("Resources");
            writer.writeEndElement();//End of </h4>
            writer.writeEndElement();//End of </div class="page-header">
            if (!htmlFilenameList.isEmpty()) {
                writer.writeStartElement("div");
                writer.writeAttribute("height", "100%");
                writer.writeAttribute("class", "list-group col-md-4");
                for (File resourceDoc : htmlFilenameList) {
                    writer.writeStartElement("a");
                    writer.writeAttribute("class", "list-group-item");
                    writer.writeAttribute("href", resourceDoc.getParentFile().getName() + File.separator + resourceDoc.getName());
                    writer.writeAttribute("target", "description");
                    writeEscapedCharacters(resourceDoc.getName());
                    writer.writeEndElement();   //Endo of </a>
                }
                writer.writeEndElement();   //Endo of </div class="list-group">
            }
            writer.writeEndElement();   //End of </div class="container">
            writer.writeEndElement();   //End of </body>
        } catch (FileNotFoundException | XMLStreamException | XPathExpressionException ex) {
            Logger.getLogger(ApiDocGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeEscapedCharacters(String data) throws XMLStreamException, IOException, XPathExpressionException {
        writer.writeCharacters("");
        writer.flush();
        OutputStreamWriter osw = new OutputStreamWriter(output);
        osw.write(data);
        osw.flush();
    }
}
