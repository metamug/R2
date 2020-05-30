/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.parser.schema;


import java.io.File;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.Ignore;


import org.junit.Test;

public class ResourceUnmarshalTest {
   
   // @Ignore
    @Test
    public void resourceUnmarshal() throws FileNotFoundException, JAXBException {

        File file = new File(this.getClass().getResource("/sample.xml").getFile());

        System.out.println(file.getName());

        Resource resource = (Resource)new Resource().unmarshal(file);

        Desc desc = resource.getDesc();
        if(desc != null){
            List<Tag> tags = desc.getTags();
            tags.forEach( tag -> {
                System.out.println(tag.getName());
            }); 
            String descString = resource.getDescString();
            System.out.println(descString);
        }
    }
    //@Ignore
    @Test
    public void resourceUnmarshal2() throws FileNotFoundException, JAXBException {

        File file = new File(this.getClass().getResource("/movie.xml").getFile());

        System.out.println(file.getName());

        Resource resource = (Resource)new Resource().unmarshal(file);

        Desc desc = resource.getDesc();

        if(desc != null){
            List<Tag> tags = desc.getTags();
            tags.forEach( tag -> {
                System.out.println(tag.getName());
            }); 
            String descString = resource.getDescString();
            System.out.println(descString);
        }
    }
    @Test
    public void resourceUnmarshal3() throws FileNotFoundException, JAXBException {

        File file = new File(this.getClass().getResource("/xrequest.xml").getFile());

        System.out.println(file.getName());

        Resource resource = (Resource)new Resource().unmarshal(file);

        Desc desc = resource.getDesc();
        if(desc != null){
            List<Tag> tags = desc.getTags();
            tags.forEach( tag -> {
                System.out.println(tag.getName());
            }); 
            String descString = resource.getDescString();
            System.out.println(descString);
        }
    }
}

