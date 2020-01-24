package com.metamug.parser.apidocs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.metamug.parser.parser.service.Backend;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

public class OpenAPIGenerator {
    public static String generateSpec(OpenAPI openAPI) throws JsonProcessingException {
        return Json.mapper().writeValueAsString(openAPI);
    }


    /**
     * Generate Open API documentation from metamug Resource
     * @return OpenAPI object representation of open api spec 3.0
     */
    public OpenAPI buildOpenAPI(Backend resource) {

        OpenAPI api = new OpenAPI();
        Info info = new Info();
        api.setInfo(info);
        return api;
    }

    public String serializeJSON(OpenAPI api){
        return Json.pretty(api);
    }

    public String serializeYML(OpenAPI api){
        return Yaml.pretty(api);
    }
}
