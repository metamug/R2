/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mpath;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author anishhirlekar
 */
public class MPathTest {
    public static String testXml;
    public static final String TEST_JSON = "{\n" +
      "   \"Port\":\n" +
      "   {\n" +
      "       \"alias\": \"defaultHttp\",\n" +
      "       \"Enabled\": \"true\",\n" +
      "       \"Number\": \"10092\",\n" +
      "       \"Protocol\": \"http\",\n" +
      "       \"KeepAliveTimeout\": \"20000\",\n" +
      "       \"ThreadPool\":\n" +
      "       {\n" +
      "           \"enabled\": \"false\",\n" +
      "           \"Max\": \"150\",\n" +
      "           \"ThreadPriority\": \"5\"\n" +
      "       },\n" +
      "       \"ExtendedProperties\":\n" +
      "       {\n" +
      "           \"Property\":\n" +
      "           [                         \n" +
      "               {\n" +
      "                   \"name\": \"connectionTimeout\",\n" +
      "                   \"D\": \"20000\"\n" +
      "               }\n" +
      "           ]\n" +
      "       }\n" +
      "   }\n" +
      "}";
    
    @Before
    public void init(){
        JSONObject jobj = new JSONObject(TEST_JSON);
        testXml = XML.toString(jobj);
        System.out.println(testXml);
    }
    
    @Test
    public void MPathTest() throws XPathExpressionException, IOException, SAXException, ParserConfigurationException{
        String xPathExpression = "/Port/Number";
        String mPathKey = "Port.ExtendedProperties.Property[0].D";
        String value = MPathUtil.getValueFromJson(TEST_JSON, mPathKey);
        //System.out.println(testXml);
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            .parse(new InputSource(new StringReader(testXml)));
        XPath xPath =  XPathFactory.newInstance().newXPath();
        NodeList nodeList = (NodeList) xPath.compile(xPathExpression).evaluate(
                        doc, XPathConstants.NODESET);
    }
}
