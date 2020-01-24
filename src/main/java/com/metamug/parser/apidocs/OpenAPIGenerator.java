package com.metamug.parser.apidocs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.metamug.parser.parser.service.Backend;
import com.metamug.parser.schema.Resource;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;

import java.util.List;

public class OpenAPIGenerator {
    public static String generateSpec(OpenAPI openAPI) throws JsonProcessingException {
        return Json.mapper().writeValueAsString(openAPI);
    }


    /**
     * Generate Open API documentation from metamug Resource
     * @return OpenAPI object representation of open api spec 3.0
     */
    public OpenAPI buildOpenAPI(Backend backend) {

        OpenAPI api = new OpenAPI();


        //Set Info
        Info info = new Info();
        info.setTitle(backend.getName());
        info.setDescription(backend.getDescription());
        api.setInfo(info);


        return api;
    }

    private Paths setPath(List<Resource> resources){
        Paths paths = new Paths();
        for (Resource resource : resources) {
            PathItem item = new PathItem();
            item.setDescription(resource.getDesc());
            paths.addPathItem("resource", item);
        }
        return paths;
    }

    public String serializeJSON(OpenAPI api){
        return Json.pretty(api);
    }

    public String serializeYML(OpenAPI api){
        return Yaml.pretty(api);
    }
}
