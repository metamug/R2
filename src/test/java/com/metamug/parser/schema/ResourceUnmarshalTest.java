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


import org.junit.Assert;
import org.junit.Test;

public class ResourceUnmarshalTest {
    @Test
    public void resourceUnmarshal() throws FileNotFoundException, JAXBException {

        File file = new File(this.getClass().getResource("/sample.xml").getFile());

        System.out.println(file.getName());

        Resource resource = (Resource)new Resource().unmarshal(file);

        Desc desc = resource.getDesc();
        List<Tag> tags = desc.getTags();
        tags.forEach( tag -> {
            System.out.println(tag.getName());
        }); 
        List<String> descString = desc.getDesc();
        descString.forEach( d -> {
            System.out.println(d);
        });
    }
}

