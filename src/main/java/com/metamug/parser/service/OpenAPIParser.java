package com.metamug.parser.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.metamug.parser.apidocs.Backend;
import com.metamug.parser.schema.*;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Convert Open API to Backend Object
 */
public class OpenAPIParser {
    private Backend backend;

    public  OpenAPIParser(String json) throws JsonProcessingException {
        this(Json.mapper().readValue(json, OpenAPI.class));
    }

    public OpenAPIParser(OpenAPI swagger) throws JsonProcessingException {
        Info info = swagger.getInfo();
        backend = new Backend(info.getTitle(), info.getDescription(), "");

        for(Map.Entry<String, PathItem> entry : swagger.getPaths().entrySet()){
            String resourceUrl = entry.getKey();
            Resource resource = buildResource(entry.getValue(), buildResource(entry.getKey()));
            backend.addResource(resourceUrl, resource);
        }

    }

    public Backend getBackend(){
        return this.backend;
    }

    /**
     * Build Resource object from Open API Path Item
     * @param item
     * @param resource
     * @return
     */
    private Resource buildResource(PathItem item, Resource resource){
        resource.setDesc(item.getDescription());

        //loop over all request tags
        for (Map.Entry<PathItem.HttpMethod, Operation> openAPIOperations : item.readOperationsMap().entrySet()) {
            Request request = new Request();
            //operation corresponds to HTTP Verb Method
            Operation operation = openAPIOperations.getValue();
            request.setDesc(operation.getDescription());
            request.setMethod(Method.fromValue(openAPIOperations.getKey().name()));
            resource.addRequest(request);
        }
        return resource;
    }

    /**
     * Build initial resource object using URI
     * @param uri
     * @return
     */
    private Resource buildResource(String uri){
        String resourceName = FilenameUtils.getBaseName(uri);
        return new Resource(resourceName, 1.0);
    }
}
