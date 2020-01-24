package com.metamug.parser;

import com.metamug.parser.apidocs.OpenAPIGenerator;
import com.metamug.parser.parser.RPXParser;
import com.metamug.parser.parser.service.Backend;
import com.metamug.parser.schema.Resource;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TestOpenAPI {

    @Test
    public void tesOpenAPIGeneration() throws JAXBException, SAXException, IOException {
        OpenAPIGenerator generator = new OpenAPIGenerator();
        Backend backend = new Backend("bookmark", "A bookmarking backend", "hi@metamug.com");
        List<Resource> resources = new ArrayList<>();
        File file = new File(TestOpenAPI.class.getClassLoader().getResource("./customer.xml").getFile());
        resources.add(RPXParser.generateResource(file));
        backend.setResourceList(resources);
        OpenAPI api = generator.buildOpenAPI(backend);
        String str = generator.serializeJSON(api);
        System.out.println(str);
    }
}
