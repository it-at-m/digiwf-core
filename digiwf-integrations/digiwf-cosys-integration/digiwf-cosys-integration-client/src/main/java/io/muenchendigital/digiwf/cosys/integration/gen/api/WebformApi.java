package io.muenchendigital.digiwf.cosys.integration.gen.api;

import io.muenchendigital.digiwf.cosys.integration.gen.ApiClient;

import io.muenchendigital.digiwf.cosys.integration.gen.model.Generator;
import io.muenchendigital.digiwf.cosys.integration.gen.model.WebformResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class WebformApi {
    private ApiClient apiClient;

    public WebformApi() {
        this(new ApiClient());
    }

    @Autowired
    public WebformApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Prepares a web form for a template and returns the id
     * 
     * <p><b>200</b> - JSON that contains the formId
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template (version) does not exist
     * <p><b>500</b> - An unexpected system error occured.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param body The body parameter
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @return WebformResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec prepareWebformRequestCreation(String guid, String client, String role, Generator body, String stateFilter, String validity) throws WebClientResponseException {
        Object postBody = body;
        // verify the required parameter 'guid' is set
        if (guid == null) {
            throw new WebClientResponseException("Missing the required parameter 'guid' when calling prepareWebform", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'client' is set
        if (client == null) {
            throw new WebClientResponseException("Missing the required parameter 'client' when calling prepareWebform", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'role' is set
        if (role == null) {
            throw new WebClientResponseException("Missing the required parameter 'role' when calling prepareWebform", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new WebClientResponseException("Missing the required parameter 'body' when calling prepareWebform", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("guid", guid);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "client", client));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "role", role));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "stateFilter", stateFilter));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "validity", validity));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuthSecurityDefintion", "accessTokenSecurityDefintion" };

        ParameterizedTypeReference<WebformResponse> localVarReturnType = new ParameterizedTypeReference<WebformResponse>() {};
        return apiClient.invokeAPI("/webform/{guid}", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Prepares a web form for a template and returns the id
     * 
     * <p><b>200</b> - JSON that contains the formId
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template (version) does not exist
     * <p><b>500</b> - An unexpected system error occured.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param body The body parameter
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @return WebformResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<WebformResponse> prepareWebform(String guid, String client, String role, Generator body, String stateFilter, String validity) throws WebClientResponseException {
        ParameterizedTypeReference<WebformResponse> localVarReturnType = new ParameterizedTypeReference<WebformResponse>() {};
        return prepareWebformRequestCreation(guid, client, role, body, stateFilter, validity).bodyToMono(localVarReturnType);
    }

    /**
     * Prepares a web form for a template and returns the id
     * 
     * <p><b>200</b> - JSON that contains the formId
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template (version) does not exist
     * <p><b>500</b> - An unexpected system error occured.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param body The body parameter
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @return ResponseEntity&lt;WebformResponse&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<WebformResponse>> prepareWebformWithHttpInfo(String guid, String client, String role, Generator body, String stateFilter, String validity) throws WebClientResponseException {
        ParameterizedTypeReference<WebformResponse> localVarReturnType = new ParameterizedTypeReference<WebformResponse>() {};
        return prepareWebformRequestCreation(guid, client, role, body, stateFilter, validity).toEntity(localVarReturnType);
    }

    /**
     * Prepares a web form for a template and returns the id
     * 
     * <p><b>200</b> - JSON that contains the formId
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template (version) does not exist
     * <p><b>500</b> - An unexpected system error occured.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param body The body parameter
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec prepareWebformWithResponseSpec(String guid, String client, String role, Generator body, String stateFilter, String validity) throws WebClientResponseException {
        return prepareWebformRequestCreation(guid, client, role, body, stateFilter, validity);
    }
}
