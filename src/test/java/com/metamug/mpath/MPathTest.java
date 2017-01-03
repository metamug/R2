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
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author anishhirlekar
 */
public class MPathTest {
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
      "                   \"D\": 20000\n" +
      "               },\n" +
      "               {\n" +
      "                   \"name\": \"connectionTimeout_Again!\",\n" +
      "                   \"D\": \"60000\"\n" +
      "               }\n" +      
      "           ]\n" +
      "       }\n" +
      "   }\n" +
      "}";
    
    public static final String TEST_XML = "<Resource version=\"1.1\" >\n" +
            "\n" +
            "	<Desc>Contains records for data type apple</Desc>\n" +
            "\n" +
            "	<Request method=\"GET\" item=\"true\" status=\"200\">\n" +
            "		<Param name=\"t\" blank=\"true\" min=\"1\" exists=\"table.column\" />\n" +
            "                <Param name=\"u\" min=\"1\" max=\"100\" pattern=\"pat341./1@[]^\" />\n" +
            "                \n" +
            "                <Sql when=\"@q eq 1\" type=\"query\">\n" +
            "                    select * from employee where employee_name = @v\n" +
            "                </Sql>\n" +
            "                <Sql when=\"@q eq 3\" type=\"update\">\n" +
            "                    update employee set employee_name = @param2 where employee_id=@id\n" +
            "                </Sql>\n" +
            "	</Request>\n" +
            "	       \n" +
            "	<Request method=\"POST\" status=\"201\">\n" +
            "		<Desc>Request for creating an employee record.</Desc>\n" +
            "		<Sql type=\"update\">\n" +
            "		insert into employee \n" +
            "		(employee_name, employee_id) \n" +
            "		values(@name, @id)\n" +
            "		</Sql>\n" +
            "\n" +
            "	</Request>\n" +
            "\n" +
            "	<!--derived from request -->\n" +
            "	<Request method=\"PUT\" item=\"true\">\n" +
            "		<Execute className=\"com.mtg.Apple\" />\n" +
            "	</Request>\n" +
            "	\n" +
            "</Resource>\n" +
            "";
    
    @Ignore
    @Test
    public void TestCase1() throws XPathExpressionException, IOException,
                                    SAXException, ParserConfigurationException{
        
        String testXml = XML.toString(new JSONObject(TEST_JSON));
        String mKey1 = "Port.ExtendedProperties.Property[0].D";
        String mKey2 = "Port.ExtendedProperties.Property[1].D";
        String mKey3 = "Port.ThreadPool.Max";
        Object jsonVal1 = MPathUtil.getValueFromJson(TEST_JSON, mKey1);
        Object jsonVal2 = MPathUtil.getValueFromJson(TEST_JSON, mKey2);
        Object jsonVal3 = MPathUtil.getValueFromJson(TEST_JSON, mKey3);
        /*System.out.println("json 1: "+jsonVal1);
        System.out.println("json 2: "+jsonVal2);
        System.out.println("JSON Val 3: "+jsonVal3);
        */
        Object xmlVal1 = MPathUtil.getValueFromXml(testXml, mKey1);
        Object xmlVal2 = MPathUtil.getValueFromXml(testXml, mKey2);
        Object xmlVal3 = MPathUtil.getValueFromXml(testXml, mKey3);
        /*
        System.out.println("x1: "+xmlVal1);
        System.out.println("x2: "+xmlVal2);
        System.out.println("x Val 3: "+xmlVal3);
        */
        Assert.assertArrayEquals(new String[]{jsonVal1.toString(),jsonVal2.toString(),jsonVal3.toString()},
                        new Object[]{xmlVal1.toString(),xmlVal2.toString(),xmlVal3.toString()});
        
    }
    
    @Test
    public void TestCase2() throws IOException, SAXException, XPathExpressionException, ParserConfigurationException{
        String testJson = (XML.toJSONObject(TEST_XML)).toString();
        String mKey1 = "Resource.Request[0].method";
        //System.out.println(TEST_XML);
        String xmlVal1 = (MPathUtil.getValueFromXml(TEST_XML, mKey1)).toString();
        String jsonVal1 = (MPathUtil.getValueFromJson(testJson, mKey1)).toString();
        
        Assert.assertArrayEquals(new String[]{xmlVal1}, new String[]{jsonVal1});
    }
}
