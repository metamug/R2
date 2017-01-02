/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mpath;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Before;
import org.junit.Test;
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
      "               },\n" +
      "               {\n" +
      "                   \"name\": \"connectionTimeout_Again!\",\n" +
      "                   \"D\": \"60000\"\n" +
      "               }\n" +      
      "           ]\n" +
      "       }\n" +
      "   }\n" +
      "}";
    
    @Before
    public void init(){
        JSONObject jobj = new JSONObject(TEST_JSON);
        //System.out.println(jobj);
        testXml = XML.toString(jobj);
    }
    
    @Test
    public void MPathTest() throws XPathExpressionException, IOException,
                                    SAXException, ParserConfigurationException{
        //String xPath3 = "/Port/ThreadPool/Max";
        
        String mKey1 = "Port.ExtendedProperties.Property[0].D";
        String mKey2 = "Port.ExtendedProperties.Property[1].D";
        String mKey3 = "Port.ThreadPool.Max";
        String jsonVal1 = MPathUtil.getValueFromJson(TEST_JSON, mKey1);
        String jsonVal2 = MPathUtil.getValueFromJson(TEST_JSON, mKey2);
        String jsonVal3 = MPathUtil.getValueFromJson(TEST_JSON, mKey3);
        System.out.println(jsonVal1);
        System.out.println(jsonVal2);
        System.out.println("JSON Val 3: "+jsonVal3);
        
        String xmlVal3 = MPathUtil.getValueFromXml(testXml, mKey3);
        System.out.println("XML Val 3: "+xmlVal3);
        
        System.out.println(testXml);
    }
}
