package io.muenchendigital.digiwf.schema.registry.gen.api;

import io.muenchendigital.digiwf.schema.registry.gen.ApiClient;

import io.muenchendigital.digiwf.schema.registry.gen.model.JsonSchemaDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Component("io.muenchendigital.digiwf.schema.registry.gen.api.JsonSchemaApi")
public class JsonSchemaApi {
    private ApiClient apiClient;

    public JsonSchemaApi() {
        this(new ApiClient());
    }

    @Autowired
    public JsonSchemaApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * 
     * create a new json schema
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return JsonSchemaDto
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public JsonSchemaDto createJsonSchema(JsonSchemaDto body) throws RestClientException {
        return createJsonSchemaWithHttpInfo(body).getBody();
    }

    /**
     * 
     * create a new json schema
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;JsonSchemaDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<JsonSchemaDto> createJsonSchemaWithHttpInfo(JsonSchemaDto body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createJsonSchema");
        }
        String path = UriComponentsBuilder.fromPath("/jsonschema").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<JsonSchemaDto> returnType = new ParameterizedTypeReference<JsonSchemaDto>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * get json schema by key
     * <p><b>200</b> - OK
     * @param key  (required)
     * @return JsonSchemaDto
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public JsonSchemaDto getJsonSchema(String key) throws RestClientException {
        return getJsonSchemaWithHttpInfo(key).getBody();
    }

    /**
     * 
     * get json schema by key
     * <p><b>200</b> - OK
     * @param key  (required)
     * @return ResponseEntity&lt;JsonSchemaDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<JsonSchemaDto> getJsonSchemaWithHttpInfo(String key) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'key' when calling getJsonSchema");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("key", key);
        String path = UriComponentsBuilder.fromPath("/jsonschema/{key}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<JsonSchemaDto> returnType = new ParameterizedTypeReference<JsonSchemaDto>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
