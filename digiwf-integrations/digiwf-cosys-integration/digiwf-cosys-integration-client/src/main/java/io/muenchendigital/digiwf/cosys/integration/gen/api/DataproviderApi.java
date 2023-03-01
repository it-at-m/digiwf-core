package io.muenchendigital.digiwf.cosys.integration.gen.api;

import io.muenchendigital.digiwf.cosys.integration.gen.ApiClient;

import io.muenchendigital.digiwf.cosys.integration.gen.model.DataProviderInput;

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
public class DataproviderApi {
    private ApiClient apiClient;

    public DataproviderApi() {
        this(new ApiClient());
    }

    @Autowired
    public DataproviderApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets the document provider data for given the template id
     * 
     * <p><b>200</b> - JSON or XML that contains the data
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template does not available
     * <p><b>500</b> - An unexpected system error occured.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param body The body parameter
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @param userId User id (lhmObjectId) of the selected sender. By default the user id from login is used.
     * @param outputFormat Output format of the document provider data.
     * @return Object
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getProviderDataRequestCreation(String guid, String client, String role, DataProviderInput body, String stateFilter, String validity, String userId, String outputFormat) throws WebClientResponseException {
        Object postBody = body;
        // verify the required parameter 'guid' is set
        if (guid == null) {
            throw new WebClientResponseException("Missing the required parameter 'guid' when calling getProviderData", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'client' is set
        if (client == null) {
            throw new WebClientResponseException("Missing the required parameter 'client' when calling getProviderData", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'role' is set
        if (role == null) {
            throw new WebClientResponseException("Missing the required parameter 'role' when calling getProviderData", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new WebClientResponseException("Missing the required parameter 'body' when calling getProviderData", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "userId", userId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "outputFormat", outputFormat));

        final String[] localVarAccepts = { 
            "application/json", "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuthSecurityDefintion", "accessTokenSecurityDefintion" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI("/dataprovider/{guid}", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Gets the document provider data for given the template id
     * 
     * <p><b>200</b> - JSON or XML that contains the data
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template does not available
     * <p><b>500</b> - An unexpected system error occured.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param body The body parameter
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @param userId User id (lhmObjectId) of the selected sender. By default the user id from login is used.
     * @param outputFormat Output format of the document provider data.
     * @return Object
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Object> getProviderData(String guid, String client, String role, DataProviderInput body, String stateFilter, String validity, String userId, String outputFormat) throws WebClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<Object>() {};
        return getProviderDataRequestCreation(guid, client, role, body, stateFilter, validity, userId, outputFormat).bodyToMono(localVarReturnType);
    }

    /**
     * Gets the document provider data for given the template id
     * 
     * <p><b>200</b> - JSON or XML that contains the data
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template does not available
     * <p><b>500</b> - An unexpected system error occured.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param body The body parameter
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @param userId User id (lhmObjectId) of the selected sender. By default the user id from login is used.
     * @param outputFormat Output format of the document provider data.
     * @return ResponseEntity&lt;Object&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Object>> getProviderDataWithHttpInfo(String guid, String client, String role, DataProviderInput body, String stateFilter, String validity, String userId, String outputFormat) throws WebClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<Object>() {};
        return getProviderDataRequestCreation(guid, client, role, body, stateFilter, validity, userId, outputFormat).toEntity(localVarReturnType);
    }

    /**
     * Gets the document provider data for given the template id
     * 
     * <p><b>200</b> - JSON or XML that contains the data
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template does not available
     * <p><b>500</b> - An unexpected system error occured.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param body The body parameter
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @param userId User id (lhmObjectId) of the selected sender. By default the user id from login is used.
     * @param outputFormat Output format of the document provider data.
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getProviderDataWithResponseSpec(String guid, String client, String role, DataProviderInput body, String stateFilter, String validity, String userId, String outputFormat) throws WebClientResponseException {
        return getProviderDataRequestCreation(guid, client, role, body, stateFilter, validity, userId, outputFormat);
    }
}
