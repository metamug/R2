/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mpath;

import com.github.wnameless.json.flattener.JsonFlattener;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author anishhirlekar
 */
public class MPathUtil {
    
    public static String getValueFromJson(String inputJson, String mPathString){
        Map<String,Object> flatMap = JsonFlattener.flattenAsMap(inputJson);
        //System.out.println(flatMap);
        return (String)flatMap.get(mPathString);
    }
    
    public static String getValueFromXml(String xmlInput, String mPath) throws IOException,
                    SAXException, XPathExpressionException, ParserConfigurationException{
        String xPathExpression = getXPathFromMPath(mPath);
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .parse(new InputSource(new StringReader(xmlInput)));
        XPath xPath =  XPathFactory.newInstance().newXPath();
        NodeList nodeList = (NodeList) xPath.compile(xPathExpression).evaluate(
                        doc, XPathConstants.NODESET);
        System.out.println(xmlInput);
        
        return ((Element)nodeList.item(0)).getTextContent();
    }
    
    public static String getXPathFromMPath(String mPath){
        String str = mPath.replace(".", "/");
        return "/"+str;
    }
    
    public static Integer getIndexFromArrayNotation(String mPath){
        Pattern pattern = Pattern.compile("[(.*?)]");
        Matcher matcher = pattern.matcher(mPath);
        if(matcher.find()){
            return Integer.parseInt(matcher.group(1));
        }else{
            return null;
        }
    }
}
/*
    import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XPathParserDemo {
   public static void main(String[] args) {
      try {
         File inputFile = new File("input.txt");
         DocumentBuilderFactory dbFactory 
            = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder;

         dBuilder = dbFactory.newDocumentBuilder();

         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();

         XPath xPath =  XPathFactory.newInstance().newXPath();

         String expression = "/class/student";	        
         NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
         for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);
            System.out.println("\nCurrent Element :" 
               + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               System.out.println("Student roll no : " 
                  + eElement.getAttribute("rollno"));
               System.out.println("First Name : " 
                  + eElement
                     .getElementsByTagName("firstname")
                     .item(0)
                     .getTextContent());
               System.out.println("Last Name : " 
                  + eElement
                     .getElementsByTagName("lastname")
                     .item(0)
                     .getTextContent());
               System.out.println("Nick Name : " 
                  + eElement
                     .getElementsByTagName("nickname")
                     .item(0)
                     .getTextContent());
               System.out.println("Marks : " 
                  + eElement
                     .getElementsByTagName("marks")
                     .item(0)
                     .getTextContent());
            }
         }
      } catch (ParserConfigurationException e) {
         e.printStackTrace();
      } catch (SAXException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (XPathExpressionException e) {
         e.printStackTrace();
      }
   }
}
This would produce the following result:

Current Element :student
Student roll no : 393
*/
