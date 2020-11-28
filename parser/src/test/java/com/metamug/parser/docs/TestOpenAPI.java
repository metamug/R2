package com.metamug.parser.docs;

import com.metamug.parser.ResourceParser;
import com.metamug.parser.apidocs.Backend;
import com.metamug.parser.apidocs.OpenAPIGenerator;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;


public class TestOpenAPI {

    Backend backend;

    @Before
    public void setup() throws JAXBException, SAXException, IOException {
        backend = new Backend("bookmark", "A bookmarking backend", "hi@metamug.com");
//        List<Resource> resources = new ArrayList<>();
        File file = new File(TestOpenAPI.class.getClassLoader().getResource("./xrequest.xml").getFile());
        backend.addResource("/backend/v1.0/xrequest", ResourceParser.generateResource(file));
        file = new File(TestOpenAPI.class.getClassLoader().getResource("./movie.xml").getFile());
        backend.addResource("/backend/v1.0/movie", ResourceParser.generateResource(file));
    }


    @Test
    public void tesOpenAPIGeneration() {
        OpenAPIGenerator generator = new OpenAPIGenerator();
        OpenAPI api = generator.build(backend);
        String str = generator.serializeJSON(api);
        System.out.println(str);
    }

//    @Test
//    public void testImport() throws JsonProcessingException, JAXBException {
//        OpenAPIGenerator generator = new OpenAPIGenerator();
//        OpenAPI api = generator.build(backend);
//        String str = generator.serializeJSON(api);
//
//
//        OpenAPIParser parser = new OpenAPIParser(str);
//        Backend backend  = parser.getBackend();
//        Resource resource = backend.getResourceList().get("/backend/v1.0/movie");
//        System.out.println(resource.marshal());
//    }
}
