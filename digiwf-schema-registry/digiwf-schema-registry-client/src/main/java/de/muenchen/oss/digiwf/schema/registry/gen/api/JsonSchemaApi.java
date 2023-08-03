package de.muenchen.oss.digiwf.schema.registry.gen.api;

import de.muenchen.oss.digiwf.schema.registry.gen.ApiClient;

import de.muenchen.oss.digiwf.schema.registry.gen.model.JsonSchemaDto;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class JsonSchemaApi {
    private ApiClient apiClient;

    public JsonSchemaApi() {
        this(new ApiClient());
    }

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
     * @param jsonSchemaDto  (required)
     * @return JsonSchemaDto
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public JsonSchemaDto createJsonSchema(JsonSchemaDto jsonSchemaDto) throws RestClientException {
        return createJsonSchemaWithHttpInfo(jsonSchemaDto).getBody();
    }

    /**
     * 
     * create a new json schema
     * <p><b>200</b> - OK
     * @param jsonSchemaDto  (required)
     * @return ResponseEntity&lt;JsonSchemaDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<JsonSchemaDto> createJsonSchemaWithHttpInfo(JsonSchemaDto jsonSchemaDto) throws RestClientException {
        Object localVarPostBody = jsonSchemaDto;
        
        // verify the required parameter 'jsonSchemaDto' is set
        if (jsonSchemaDto == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'jsonSchemaDto' when calling createJsonSchema");
        }
        

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<JsonSchemaDto> localReturnType = new ParameterizedTypeReference<JsonSchemaDto>() {};
        return apiClient.invokeAPI("/jsonschema", HttpMethod.POST, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
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
        Object localVarPostBody = null;
        
        // verify the required parameter 'key' is set
        if (key == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'key' when calling getJsonSchema");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("key", key);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "*/*"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<JsonSchemaDto> localReturnType = new ParameterizedTypeReference<JsonSchemaDto>() {};
        return apiClient.invokeAPI("/jsonschema/{key}", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
}
