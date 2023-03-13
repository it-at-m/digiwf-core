package io.muenchendigital.digiwf.cosys.integration.gen.api;

import io.muenchendigital.digiwf.cosys.integration.gen.ApiClient;

import java.io.File;

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
public class DatapoolApi {
    private ApiClient apiClient;

    public DatapoolApi() {
        this(new ApiClient());
    }

    @Autowired
    public DatapoolApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Creates a new data pool and returns the id.
     * 
     * <p><b>200</b> - ID of the created data pool or the cosys url if the guid is set. With this ID you can call coSys: https://cosys-dev.muenchen.de/webdesk?j_user_company&#x3D;0002&amp;j_role&#x3D;TESTER&amp;j_template_guid&#x3D;{GUID}&amp;j_datapoolid&#x3D;{ID}
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - The data pool does not exist
     * <p><b>500</b> - An unexpected system error occured.
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param data The data pool. Allowed mime types are: application/xml and application/json
     * @param guid The guid of the template
     * @param parameter The configuration parameter for the data pool as application/json.  *Default value:* &#x60;&#x60;&#x60; {   \\\&quot;userInformation\\\&quot; : null,   \\\&quot;resultCallbackUrl\\\&quot; : null,   \\\&quot;outputFormat\\\&quot; : \\\&quot;PDF\\\&quot;,   \\\&quot;processingHint\\\&quot; : \\\&quot;mapViaDataSupplySchemaWithPassThrough\\\&quot;,   \\\&quot;deleteAfterCommit\\\&quot; : false } &#x60;&#x60;&#x60;  *Example:* &#x60;&#x60;&#x60; {   \\\&quot;userInformation\\\&quot; : \\\&quot;Wohnen in München\\\&quot;,   \\\&quot;resultCallbackUrl\\\&quot; : \\\&quot;https://example.com/uploadDocument\\\&quot;,   \\\&quot;outputFormat\\\&quot; : \\\&quot;PDF, RTF\\\&quot;,   \\\&quot;processingHint\\\&quot; : \\\&quot;passThroughToDocGen\\\&quot;,   \\\&quot;deleteAfterCommit\\\&quot; : true } &#x60;&#x60;&#x60;
     * @return String
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createDataPoolRequestCreation(String client, String role, File data, String guid, File parameter) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'client' is set
        if (client == null) {
            throw new WebClientResponseException("Missing the required parameter 'client' when calling createDataPool", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'role' is set
        if (role == null) {
            throw new WebClientResponseException("Missing the required parameter 'role' when calling createDataPool", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'data' is set
        if (data == null) {
            throw new WebClientResponseException("Missing the required parameter 'data' when calling createDataPool", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "client", client));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "role", role));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "guid", guid));

        if (data != null)
            formParams.add("data", new FileSystemResource(data));
        if (parameter != null)
            formParams.add("parameter", new FileSystemResource(parameter));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuthSecurityDefintion", "accessTokenSecurityDefintion" };

        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<String>() {};
        return apiClient.invokeAPI("/datapool", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Creates a new data pool and returns the id.
     * 
     * <p><b>200</b> - ID of the created data pool or the cosys url if the guid is set. With this ID you can call coSys: https://cosys-dev.muenchen.de/webdesk?j_user_company&#x3D;0002&amp;j_role&#x3D;TESTER&amp;j_template_guid&#x3D;{GUID}&amp;j_datapoolid&#x3D;{ID}
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - The data pool does not exist
     * <p><b>500</b> - An unexpected system error occured.
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param data The data pool. Allowed mime types are: application/xml and application/json
     * @param guid The guid of the template
     * @param parameter The configuration parameter for the data pool as application/json.  *Default value:* &#x60;&#x60;&#x60; {   \\\&quot;userInformation\\\&quot; : null,   \\\&quot;resultCallbackUrl\\\&quot; : null,   \\\&quot;outputFormat\\\&quot; : \\\&quot;PDF\\\&quot;,   \\\&quot;processingHint\\\&quot; : \\\&quot;mapViaDataSupplySchemaWithPassThrough\\\&quot;,   \\\&quot;deleteAfterCommit\\\&quot; : false } &#x60;&#x60;&#x60;  *Example:* &#x60;&#x60;&#x60; {   \\\&quot;userInformation\\\&quot; : \\\&quot;Wohnen in München\\\&quot;,   \\\&quot;resultCallbackUrl\\\&quot; : \\\&quot;https://example.com/uploadDocument\\\&quot;,   \\\&quot;outputFormat\\\&quot; : \\\&quot;PDF, RTF\\\&quot;,   \\\&quot;processingHint\\\&quot; : \\\&quot;passThroughToDocGen\\\&quot;,   \\\&quot;deleteAfterCommit\\\&quot; : true } &#x60;&#x60;&#x60;
     * @return String
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<String> createDataPool(String client, String role, File data, String guid, File parameter) throws WebClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<String>() {};
        return createDataPoolRequestCreation(client, role, data, guid, parameter).bodyToMono(localVarReturnType);
    }

    /**
     * Creates a new data pool and returns the id.
     * 
     * <p><b>200</b> - ID of the created data pool or the cosys url if the guid is set. With this ID you can call coSys: https://cosys-dev.muenchen.de/webdesk?j_user_company&#x3D;0002&amp;j_role&#x3D;TESTER&amp;j_template_guid&#x3D;{GUID}&amp;j_datapoolid&#x3D;{ID}
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - The data pool does not exist
     * <p><b>500</b> - An unexpected system error occured.
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param data The data pool. Allowed mime types are: application/xml and application/json
     * @param guid The guid of the template
     * @param parameter The configuration parameter for the data pool as application/json.  *Default value:* &#x60;&#x60;&#x60; {   \\\&quot;userInformation\\\&quot; : null,   \\\&quot;resultCallbackUrl\\\&quot; : null,   \\\&quot;outputFormat\\\&quot; : \\\&quot;PDF\\\&quot;,   \\\&quot;processingHint\\\&quot; : \\\&quot;mapViaDataSupplySchemaWithPassThrough\\\&quot;,   \\\&quot;deleteAfterCommit\\\&quot; : false } &#x60;&#x60;&#x60;  *Example:* &#x60;&#x60;&#x60; {   \\\&quot;userInformation\\\&quot; : \\\&quot;Wohnen in München\\\&quot;,   \\\&quot;resultCallbackUrl\\\&quot; : \\\&quot;https://example.com/uploadDocument\\\&quot;,   \\\&quot;outputFormat\\\&quot; : \\\&quot;PDF, RTF\\\&quot;,   \\\&quot;processingHint\\\&quot; : \\\&quot;passThroughToDocGen\\\&quot;,   \\\&quot;deleteAfterCommit\\\&quot; : true } &#x60;&#x60;&#x60;
     * @return ResponseEntity&lt;String&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<String>> createDataPoolWithHttpInfo(String client, String role, File data, String guid, File parameter) throws WebClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<String>() {};
        return createDataPoolRequestCreation(client, role, data, guid, parameter).toEntity(localVarReturnType);
    }

    /**
     * Creates a new data pool and returns the id.
     * 
     * <p><b>200</b> - ID of the created data pool or the cosys url if the guid is set. With this ID you can call coSys: https://cosys-dev.muenchen.de/webdesk?j_user_company&#x3D;0002&amp;j_role&#x3D;TESTER&amp;j_template_guid&#x3D;{GUID}&amp;j_datapoolid&#x3D;{ID}
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - The data pool does not exist
     * <p><b>500</b> - An unexpected system error occured.
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param data The data pool. Allowed mime types are: application/xml and application/json
     * @param guid The guid of the template
     * @param parameter The configuration parameter for the data pool as application/json.  *Default value:* &#x60;&#x60;&#x60; {   \\\&quot;userInformation\\\&quot; : null,   \\\&quot;resultCallbackUrl\\\&quot; : null,   \\\&quot;outputFormat\\\&quot; : \\\&quot;PDF\\\&quot;,   \\\&quot;processingHint\\\&quot; : \\\&quot;mapViaDataSupplySchemaWithPassThrough\\\&quot;,   \\\&quot;deleteAfterCommit\\\&quot; : false } &#x60;&#x60;&#x60;  *Example:* &#x60;&#x60;&#x60; {   \\\&quot;userInformation\\\&quot; : \\\&quot;Wohnen in München\\\&quot;,   \\\&quot;resultCallbackUrl\\\&quot; : \\\&quot;https://example.com/uploadDocument\\\&quot;,   \\\&quot;outputFormat\\\&quot; : \\\&quot;PDF, RTF\\\&quot;,   \\\&quot;processingHint\\\&quot; : \\\&quot;passThroughToDocGen\\\&quot;,   \\\&quot;deleteAfterCommit\\\&quot; : true } &#x60;&#x60;&#x60;
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createDataPoolWithResponseSpec(String client, String role, File data, String guid, File parameter) throws WebClientResponseException {
        return createDataPoolRequestCreation(client, role, data, guid, parameter);
    }
    /**
     * Deletes the data pool.
     * 
     * <p><b>200</b> - The data pool was deleted
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - The data pool does not exist
     * <p><b>500</b> - An unexpected system error occured.
     * @param dataPoolId The id of the data pool
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec deleteDataPoolRequestCreation(String dataPoolId, String client, String role) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'dataPoolId' is set
        if (dataPoolId == null) {
            throw new WebClientResponseException("Missing the required parameter 'dataPoolId' when calling deleteDataPool", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'client' is set
        if (client == null) {
            throw new WebClientResponseException("Missing the required parameter 'client' when calling deleteDataPool", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'role' is set
        if (role == null) {
            throw new WebClientResponseException("Missing the required parameter 'role' when calling deleteDataPool", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("dataPoolId", dataPoolId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "client", client));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "role", role));

        final String[] localVarAccepts = { };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuthSecurityDefintion", "accessTokenSecurityDefintion" };

        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/datapool/{dataPoolId}", HttpMethod.DELETE, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Deletes the data pool.
     * 
     * <p><b>200</b> - The data pool was deleted
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - The data pool does not exist
     * <p><b>500</b> - An unexpected system error occured.
     * @param dataPoolId The id of the data pool
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Void> deleteDataPool(String dataPoolId, String client, String role) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteDataPoolRequestCreation(dataPoolId, client, role).bodyToMono(localVarReturnType);
    }

    /**
     * Deletes the data pool.
     * 
     * <p><b>200</b> - The data pool was deleted
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - The data pool does not exist
     * <p><b>500</b> - An unexpected system error occured.
     * @param dataPoolId The id of the data pool
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Void>> deleteDataPoolWithHttpInfo(String dataPoolId, String client, String role) throws WebClientResponseException {
        ParameterizedTypeReference<Void> localVarReturnType = new ParameterizedTypeReference<Void>() {};
        return deleteDataPoolRequestCreation(dataPoolId, client, role).toEntity(localVarReturnType);
    }

    /**
     * Deletes the data pool.
     * 
     * <p><b>200</b> - The data pool was deleted
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - The data pool does not exist
     * <p><b>500</b> - An unexpected system error occured.
     * @param dataPoolId The id of the data pool
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec deleteDataPoolWithResponseSpec(String dataPoolId, String client, String role) throws WebClientResponseException {
        return deleteDataPoolRequestCreation(dataPoolId, client, role);
    }
}
