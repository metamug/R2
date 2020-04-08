package com.metamug.parser.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.metamug.parser.apidocs.Backend;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import org.apache.commons.io.FilenameUtils;

import java.util.Map;

/**
 * Convert Open API to Backend Object
 */
public class OpenAPIParser {

    public Backend build(String json) throws JsonProcessingException {
        OpenAPI swagger = Json.mapper().readValue(json, OpenAPI.class);
        return build(swagger);
    }

    public Backend build(OpenAPI swagger) throws JsonProcessingException {
        Info info = swagger.getInfo();
        Backend backend = new Backend(info.getTitle(), info.getDescription(), "");

        for(Map.Entry<String, PathItem> entry : swagger.getPaths().entrySet()){
            String resourceUrl = entry.getKey();
            String resourceName = FilenameUtils.getBaseName(resourceUrl);
        }
        return backend;
    }
}
