package de.muenchen.oss.digiwf.ocecosmos.integration.gen.api;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.ApiClient;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.Syslog;
import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.WebException;

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

@Component("de.muenchen.oss.digiwf.ocecosmos.integration.gen.api.SystemLogOperationsApi")
public class SystemLogOperationsApi {
    private ApiClient apiClient;

    public SystemLogOperationsApi() {
        this(new ApiClient());
    }

    @Autowired
    public SystemLogOperationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Get used hostnames of system log
     * Returns all the hostnames of the system log. Requires permission &#x27;PERM_IADMINSERVICE_GETLIST, PERM_PLUGIN_SYSLOG&#x27;.
     * <p><b>200</b> - Ok
     * @return String
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public String getHostNamesUsingGET() throws RestClientException {
        return getHostNamesUsingGETWithHttpInfo().getBody();
    }

    /**
     * Get used hostnames of system log
     * Returns all the hostnames of the system log. Requires permission &#x27;PERM_IADMINSERVICE_GETLIST, PERM_PLUGIN_SYSLOG&#x27;.
     * <p><b>200</b> - Ok
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> getHostNamesUsingGETWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/webapi/v3/syslogs/hostNames").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<String> returnType = new ParameterizedTypeReference<String>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Query system logs
     * Returns all system log resources. Filtering, sorting and paging supported. Requires permission &#x27;PERM_IADMINSERVICE_GETLIST, PERM_PLUGIN_SYSLOG&#x27;.
     * <p><b>200</b> - All system log resources have been fetched and returned in the response body.
     * <p><b>400</b> - Orderby for attribute message is not supported for databases HSQL and Oracle.
     * @param limit Count of elements to retrieve from the given offset. MUST be used in conjunction with offset. (optional)
     * @param offset Index of the first element to retrieve. MUST be used in conjunction with limit. (optional)
     * @param orderby Sorting order of the queried result set having the following SQL-like syntax : attribute1[.asc|.desc][;attributeN[.asc|.desc]]. Attribute message is not supported for databases HSQL and Oracle. (optional)
     * @param search RSQL filtering search criteria. (optional)
     * @return List&lt;Syslog&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<Syslog> getUsingGET10(Integer limit, Integer offset, String orderby, String search) throws RestClientException {
        return getUsingGET10WithHttpInfo(limit, offset, orderby, search).getBody();
    }

    /**
     * Query system logs
     * Returns all system log resources. Filtering, sorting and paging supported. Requires permission &#x27;PERM_IADMINSERVICE_GETLIST, PERM_PLUGIN_SYSLOG&#x27;.
     * <p><b>200</b> - All system log resources have been fetched and returned in the response body.
     * <p><b>400</b> - Orderby for attribute message is not supported for databases HSQL and Oracle.
     * @param limit Count of elements to retrieve from the given offset. MUST be used in conjunction with offset. (optional)
     * @param offset Index of the first element to retrieve. MUST be used in conjunction with limit. (optional)
     * @param orderby Sorting order of the queried result set having the following SQL-like syntax : attribute1[.asc|.desc][;attributeN[.asc|.desc]]. Attribute message is not supported for databases HSQL and Oracle. (optional)
     * @param search RSQL filtering search criteria. (optional)
     * @return ResponseEntity&lt;List&lt;Syslog&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<Syslog>> getUsingGET10WithHttpInfo(Integer limit, Integer offset, String orderby, String search) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/webapi/v3/syslogs").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "offset", offset));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "orderby", orderby));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "search", search));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<List<Syslog>> returnType = new ParameterizedTypeReference<List<Syslog>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Retrieve one given system log
     * Returns the system log resource matching provided id. Requires permission &#x27;PERM_IADMINSERVICE_GETOBJECT, PERM_PLUGIN_SYSLOG&#x27;.
     * <p><b>200</b> - The requested system log resource has been found and returned in the response body.
     * @param id id of the system log resource to retrieve (required)
     * @return Syslog
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Syslog getUsingGET11(Long id) throws RestClientException {
        return getUsingGET11WithHttpInfo(id).getBody();
    }

    /**
     * Retrieve one given system log
     * Returns the system log resource matching provided id. Requires permission &#x27;PERM_IADMINSERVICE_GETOBJECT, PERM_PLUGIN_SYSLOG&#x27;.
     * <p><b>200</b> - The requested system log resource has been found and returned in the response body.
     * @param id id of the system log resource to retrieve (required)
     * @return ResponseEntity&lt;Syslog&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Syslog> getUsingGET11WithHttpInfo(Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getUsingGET11");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/syslogs/{id}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<Syslog> returnType = new ParameterizedTypeReference<Syslog>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Create one system log
     * Create a new system log resource. Requires permission &#x27;PERM_IADMINSERVICE_ADDOBJECTID, PERM_PLUGIN_SYSLOG&#x27;.
     * <p><b>201</b> - A new system log resource has been created.
     * @param body the representation of the system log entry to create (required)
     * @return Syslog
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Syslog postUsingPOST7(Syslog body) throws RestClientException {
        return postUsingPOST7WithHttpInfo(body).getBody();
    }

    /**
     * Create one system log
     * Create a new system log resource. Requires permission &#x27;PERM_IADMINSERVICE_ADDOBJECTID, PERM_PLUGIN_SYSLOG&#x27;.
     * <p><b>201</b> - A new system log resource has been created.
     * @param body the representation of the system log entry to create (required)
     * @return ResponseEntity&lt;Syslog&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Syslog> postUsingPOST7WithHttpInfo(Syslog body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling postUsingPOST7");
        }
        String path = UriComponentsBuilder.fromPath("/webapi/v3/syslogs").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<Syslog> returnType = new ParameterizedTypeReference<Syslog>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
