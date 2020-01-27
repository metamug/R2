package com.metamug.parser.docs;

import com.metamug.parser.RPXParser;
import com.metamug.parser.apidocs.Backend;
import com.metamug.parser.apidocs.OpenAPIGenerator;
import com.metamug.parser.schema.Resource;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TestOpenAPI {

    Backend backend;

    @Before
    public void setup() throws JAXBException, SAXException, IOException {
        backend = new Backend("bookmark", "A bookmarking backend", "hi@metamug.com");
        List<Resource> resources = new ArrayList<>();
        File file = new File(TestOpenAPI.class.getClassLoader().getResource("./movie.xml").getFile());
        backend.addResource("/backend/v1.0/movie", RPXParser.generateResource(file));
    }


    @Test
    public void tesOpenAPIGeneration() {
        OpenAPIGenerator generator = new OpenAPIGenerator();
        OpenAPI api = generator.buildOpenAPI(backend);
        String str = generator.serializeJSON(api);
        System.out.println(str);
    }
}
