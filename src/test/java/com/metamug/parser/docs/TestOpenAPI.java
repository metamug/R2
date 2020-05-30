package com.metamug.parser.docs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.metamug.parser.ResourceParser;
import com.metamug.parser.apidocs.Backend;
import com.metamug.parser.apidocs.OpenAPIGenerator;
import com.metamug.parser.schema.Resource;

import com.metamug.parser.service.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.Map;


public class TestOpenAPI {

    Backend backend;

    @Before
    public void setup() throws JAXBException, SAXException, IOException {
        backend = new Backend("bookmark", "A bookmarking backend", "hi@metamug.com");
//        List<Resource> resources = new ArrayList<>();
        File file = new File(TestOpenAPI.class.getClassLoader().getResource("./xrequest.xml").getFile());
        backend.addResource("/backend/v1.0/xrequest", ResourceParser.generateResource(file));
        file = new File(TestOpenAPI.class.getClassLoader().getResource("./moviewithtag.xml").getFile());
        backend.addResource("/backend/v1.0/moviewithtag", ResourceParser.generateResource(file));
    }


    @Test
    public void tesOpenAPIGeneration() {
        OpenAPIGenerator generator = new OpenAPIGenerator();
        OpenAPI api = generator.build(backend);
        String str = generator.serializeJSON(api);
        System.out.println(str);
    }

    @Test
    public void testImport() throws JsonProcessingException, JAXBException {
        OpenAPIGenerator generator = new OpenAPIGenerator();
        OpenAPI api = generator.build(backend);
        String str = generator.serializeJSON(api);

        OpenAPIParser parser = new OpenAPIParser(str);
        Backend backend  = parser.getBackend();
        System.out.println(backend.getResourceList());
        Map<String, Resource> resourceMap = backend.getResourceList();
        for(String x: resourceMap.keySet())
            System.out.println(x);
        Resource resource = backend.getResourceList().get("/backend/v1.0/moviewithtag/action");
        System.out.println(resource);
        System.out.println(resource.marshal());
    }
}
