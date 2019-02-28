/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.parser.util;

import com.metamug.parser.exception.ResourceTestException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;

/**
 *
 * @author anishhirlekar
 */
public class Utils {
    public static String executeQueryInApp(String appUrl, String action, String query) throws IOException, ResourceTestException {
        
        URL obj = new URL(appUrl + "/query");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "action=" + action + "&query=" + query;

        // Send post request
        con.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }
        int statusCode = con.getResponseCode();
        if(statusCode != 200) {
            throw new ResourceTestException("Server error. Could not test query!");
        }
        
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            StringBuilder responseBuffer = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                responseBuffer.append(inputLine);
            }
            return responseBuffer.toString();
        }
    }
    
    public static String mapToUrlParams(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();

        map.keySet().forEach( key -> {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            String value = map.get(key);
            try {
                stringBuilder.append((key != null ? URLEncoder.encode(key, "UTF-8") : ""));
                stringBuilder.append("=");
                stringBuilder.append(value != null ? URLEncoder.encode(value, "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        });

        return stringBuilder.toString();
    }

}