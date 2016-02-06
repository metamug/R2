/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.jaxb.docs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anish
 */
public class ApiDocGenerator {
    
    public static void generate(String directoryAddress){
        
        List<String> htmlFilenameList = new ArrayList<>();
        
        File directory = new File(directoryAddress);
        File[] listOfFiles = directory.listFiles();
                
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if(listOfFiles[i].getName().endsWith(".html")){
                    htmlFilenameList.add(listOfFiles[i].getName());
                }
            }               
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html>");
        sb.append("<head><title>Welcome</title></head>");
        sb.append("<body>");
            
        
        if(!htmlFilenameList.isEmpty()){
            sb.append("<ul>");
            for(String name : htmlFilenameList){    
                sb.append("<li><a href=\"#\">");
                sb.append(name);
                sb.append("</a></li>");
            }
            sb.append("</ul>");
        } else{
            sb.append("<h4>No resources created!</h4>");
        }
            
        sb.append("</body></html>");
               
        try {
            File outFile = new File(directoryAddress + "\\doc\\welcome_page" + ".html");
            outFile.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
            bw.write(sb.toString());
            bw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ApiDocGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
