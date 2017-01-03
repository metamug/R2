/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mpath;

import com.github.wnameless.json.flattener.JsonFlattener;
import java.io.IOException;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.json.JSONObject;
import org.json.XML;
import org.xml.sax.SAXException;

/**
 *
 * @author anishhirlekar
 */
public class MPathUtil {
    
    public static Object getValueFromJson(String inputJson, String mPath){
        Map<String,Object> flatMap = JsonFlattener.flattenAsMap(inputJson);
        //System.out.println(flatMap);
        return flatMap.get(mPath);
    }
    
    public static Object getValueFromXml(String xmlInput, String mPath) throws IOException,
                    SAXException, XPathExpressionException, ParserConfigurationException{
        
        JSONObject jobj = XML.toJSONObject(xmlInput);
        String jobjStr = jobj.toString();
        Map<String,Object> flatMap = JsonFlattener.flattenAsMap(jobjStr);
        //System.out.println("jobj:\n"+flatMap);
        
        return flatMap.get(mPath);
    }
}
