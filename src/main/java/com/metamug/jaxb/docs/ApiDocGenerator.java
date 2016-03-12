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

    public void generate(String appDirectory) {

        try {
            List<String> htmlFilenameList = new ArrayList<>();

            File directory = new File(appDirectory + "/docs");
            File[] listOfFiles = directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".html");
                }
            });
            directory.listFiles((File dir, String name) -> name.toLowerCase().endsWith(".html"));
            for (File file : listOfFiles) {
                htmlFilenameList.add(file.getName());
            }
            output = new FileOutputStream(appDirectory + "/index" + ".html");
            writer = new IndentingXMLStreamWriter(factory.createXMLStreamWriter(output, "UTF-8"));
            writeEscapedCharacters("<!DOCTYPE html>");
            writer.writeStartElement("html");
            writer.writeStartElement("head");
            writer.writeStartElement("meta");
            writer.writeAttribute("charset", "utf-8");
            writer.writeEndElement();
            writer.writeStartElement("meta");
            writer.writeAttribute("http-equiv", "X-UA-Compatible");
            writer.writeAttribute("content", "IE=edge");
            writer.writeEndElement();
            writer.writeStartElement("meta");
            writer.writeAttribute("name", "viewport");
            writer.writeAttribute("content", "width=device-width, initial-scale=1, shrink-to-fit=no");
            writer.writeEndElement();
            writer.writeStartElement("link");
            writer.writeAttribute("href", "https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/css/bootstrap.min.css");
            writer.writeAttribute("rel", "stylesheet");
            writer.writeEndElement();
            writer.writeStartElement("title");
            writeEscapedCharacters("Welcome");
            writer.writeEndElement();   //End of </title>
            writer.writeEndElement();   //End of </head>
            writer.writeStartElement("body");
            writer.writeStartElement("div");
            writer.writeAttribute("class", "container");
            writer.writeAttribute("style", "padding-top:25px");
            writer.writeStartElement("h2");
            writeEscapedCharacters("Resource List:");
            writer.writeEndElement();
            if (!htmlFilenameList.isEmpty()) {
                writer.writeStartElement("div");
                writer.writeAttribute("class", "list-group col-md-4");
                for (String name : htmlFilenameList) {
                    writer.writeStartElement("a");
                    writer.writeAttribute("class", "list-group-item");
                    writer.writeAttribute("href", "docs\\" + name);
                    writeEscapedCharacters(name);
                    writer.writeEndElement();   //Endo of </a>
                }

                writer.writeEndElement();   //Endo of </div class="list-group">
            } else {
                writer.writeStartElement("h4");
                writeEscapedCharacters("No resources created!");
                writer.writeEndElement();   //Endo of </h4>
            }
            writer.writeEndElement();   //End of </div class="container">
            writer.writeEndElement();   //End of </body>
            writer.writeEndElement();   //End of </html>
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ApiDocGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException | XPathExpressionException | IOException ex) {
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
