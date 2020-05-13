/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.parser.schema;


import java.io.File;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;


import org.junit.Assert;
import org.junit.Test;

public class ResourceUnmarshalTest {
    @Test
    public void resourceUnmarhal() throws FileNotFoundException, JAXBException {

        File file = new File(this.getClass().getResource("/sample.xml").getFile());

        System.out.println(file.getName());

        Resource resource = (Resource)new  Resource().unmarshal(file);

        Desc desc = resource.getDesc();
        Tag tag = desc.getTag();
        System.out.println(tag);

        Assert.assertNotNull(tag);
    }
}

