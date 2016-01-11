/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author anish
 */
public class SqlUtil {
    //try replacing keys in html with params using java
    public static List<String> getParamsFromSql(String sql){
        StringBuilder sb = null;
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile("\\@(\\w+)");
        Matcher matcher = pattern.matcher(sql);
        while(matcher.find()){
            list.add(sql.substring(matcher.start(1), matcher.end(1)));
        }
        
        return list;
    }
}
