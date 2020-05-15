package com.metamug.parser.apidocs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.metamug.parser.schema.*;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generate open api 3.0 specification using backend information
 */
public class OpenAPIGenerator {


    public static String generateSpec(OpenAPI openAPI) throws JsonProcessingException {
        return Json.mapper().writeValueAsString(openAPI);
    }


    /**
     * Generate Open API documentation from metamug Resource
     *
     * @return OpenAPI object representation of open api spec 3.0
     */
    public OpenAPI build(Backend backend) {

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



    /**
     * Each path represents a single resource file
     * @param resources Map of URI,resource
     * @return Open API Path
     */


    private Paths buildPath(Map<String, Resource> resources) {
        Paths paths = new Paths();
        for (Map.Entry<String, Resource> resource : resources.entrySet()) {
            Set<Request> req =  resource.getValue().getRequest();
            String itemRequest = "";

            for(Request r : req) {
                itemRequest = r.getItem();
                break;
            }
            paths.addPathItem(resource.getKey() + "/" + itemRequest, buildPathItem(resource.getValue()));
        }
        return paths;
    }

    /**
     * One path corresponds to one resource.
     *
     * @param resource Metamug API Console REST Resource
     * @return Open API Path Item equivalent
     */
    private PathItem buildPathItem(Resource resource) {
        PathItem item = new PathItem();
        item.setDescription(resource.getDescString());
        
        //loop over all request tags
        for (Request request : resource.getRequest()) {

            //operation corresponds to HTTP Verb Method
            Operation operation = new Operation();
            operation.setDescription(request.getDescString());


                Set<Param> requestParameters = request.getParamSet();
                List<Parameter> openAPIParams = new ArrayList<>();

                List<InvocableElement> list = request.getInvocableElements();

                //@TODO use precedence of Param tag over $variable since param tags define type

                //loop over request elements to get params
                for (InvocableElement requestElement : list) {

                    for (String strParam : requestElement.getRequestParameters()) {
                        Param param = new Param(strParam);
                        requestParameters.add(param);
                    }

                }

                for (Param param : requestParameters) {
                    Parameter parameter = createParameter(param);
                    openAPIParams.add(parameter);
                }

                if (!openAPIParams.isEmpty())
                    operation.setParameters(openAPIParams);

                switch (request.getMethod().value().toLowerCase()) {
                    case "post":
                        item.setPost(operation);
                        break;
                    case "get":
                        item.setGet(operation);
                        break;
                    case "put":
                        item.setPut(operation);
                        break;
                    case "delete":
                        item.setDelete(operation);
                        break;
                }
                setStandardResponses(operation);
            }

//        }

        return item;
    }

    /**
     * Convert Metamug Param object to Open API Parameter
     *
     * @param param Metamug Param Object
     * @return Open API Parameter
     */
    private Parameter createParameter(Param param) {
        Parameter parameter = new Parameter();
        parameter.setName(param.getName());
        parameter.setRequired(param.isRequired());
        parameter.setIn("query");
        Schema schema = new Schema();
        if (param.getType().value() == null || param.getType().value().equalsIgnoreCase("text"))
            schema.setType("string");

        parameter.setSchema(schema);
        return parameter;
    }

    /**
     * Set standard set of responses by Mason
     *
     * @param operation
     */
    private void setStandardResponses(Operation operation) {
        ApiResponses responses = new ApiResponses();
        ApiResponse response = new ApiResponse();
        response.description("API Response");
        responses.addApiResponse("200", response);
        operation.setResponses(responses);
    }

    public String serializeJSON(OpenAPI api) {

        return Json.pretty(api);
    }

    public String serializeYML(OpenAPI api) {
        return Yaml.pretty(api);
    }
}
