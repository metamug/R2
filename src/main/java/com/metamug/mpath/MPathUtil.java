/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.mpath;

import com.github.wnameless.json.flattener.JsonFlattener;
import java.util.Map;

/**
 *
 * @author anishhirlekar
 */
public class MPathUtil {
    private final String TYPE_JSON = "application/json";
    private final String TYPE_XML = "application/xml";
    
    public static String getValueFromJson(String inputJson, String mPathString){
        Map<String,Object> flatMap = JsonFlattener.flattenAsMap(inputJson);
        //System.out.println(flatMap);
        return (String)flatMap.get(mPathString);
    }
}
