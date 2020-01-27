package com.metamug.parser.apidocs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.metamug.parser.schema.Request;
import com.metamug.parser.schema.Resource;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

import java.util.Map;

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
        info.version("1.0.0");
        info.setTitle(backend.getName());
        info.setDescription(backend.getDescription());
        api.setInfo(info);

        api.setPaths(buildPath(backend.getResourceList()));


        return api;
    }

    private Paths buildPath(Map<String, Resource> resources){
        Paths paths = new Paths();
        for (Map.Entry<String, Resource> resource : resources.entrySet()) {
            paths.addPathItem(resource.getKey(), buildPathItem(resource.getValue()));
        }
        return paths;
    }

    private PathItem buildPathItem(Resource resource){
        PathItem item = new PathItem();
        item.setDescription(resource.getDesc());
        for (Request request : resource.getRequest()) {
            if (request.getMethod().value().equalsIgnoreCase("post")) {
                Operation operation = new Operation();
                operation.setDescription(request.getDesc());
                item.setPost(operation);
                setStandardResponses(operation);
            }
        }


        return item;
    }

    /**
     * Set standard set of responses by Mason
     * @param operation
     */
    private void setStandardResponses(Operation operation){
        ApiResponses responses = new ApiResponses();
        ApiResponse response = new ApiResponse();
        response.description("API Response");
        responses.addApiResponse("200", response);
        operation.setResponses(responses);
    }

    public String serializeJSON(OpenAPI api){
        return Json.pretty(api);
    }

    public String serializeYML(OpenAPI api){
        return Yaml.pretty(api);
    }
}
