package de.muenchen.oss.digiwf.ocecosmos.integration.gen.api;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.ApiClient;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.JobLog;

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

@Component("de.muenchen.oss.digiwf.ocecosmos.integration.gen.api.JobLogOperationsApi")
public class JobLogOperationsApi {
    private ApiClient apiClient;

    public JobLogOperationsApi() {
        this(new ApiClient());
    }

    @Autowired
    public JobLogOperationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Gets joblog children by given jobid and optional parentId for displaying joblog tree.
     * Gets joblog children by given jobid and optinal parentId for displaying joblog tree.
     * <p><b>200</b> - OK
     * @param id id of the job (required)
     * @param parentId parentId of joblog (required)
     * @return List&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<String> getChildrenUsingGET(Long id, Long parentId) throws RestClientException {
        return getChildrenUsingGETWithHttpInfo(id, parentId).getBody();
    }

    /**
     * Gets joblog children by given jobid and optional parentId for displaying joblog tree.
     * Gets joblog children by given jobid and optinal parentId for displaying joblog tree.
     * <p><b>200</b> - OK
     * @param id id of the job (required)
     * @param parentId parentId of joblog (required)
     * @return ResponseEntity&lt;List&lt;String&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<String>> getChildrenUsingGETWithHttpInfo(Long id, Long parentId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getChildrenUsingGET");
        }
        // verify the required parameter 'parentId' is set
        if (parentId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'parentId' when calling getChildrenUsingGET");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        uriVariables.put("parentId", parentId);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}/joblog/children").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<List<String>> returnType = new ParameterizedTypeReference<List<String>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Gets joblog children by given jobid and optional parentId for displaying joblog tree.
     * Gets joblog children by given jobid and optinal parentId for displaying joblog tree.
     * <p><b>200</b> - OK
     * @param id id of the job (required)
     * @param parentId parentId of joblog (required)
     * @return List&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<String> getChildrenUsingGET1(Long id, Long parentId) throws RestClientException {
        return getChildrenUsingGET1WithHttpInfo(id, parentId).getBody();
    }

    /**
     * Gets joblog children by given jobid and optional parentId for displaying joblog tree.
     * Gets joblog children by given jobid and optinal parentId for displaying joblog tree.
     * <p><b>200</b> - OK
     * @param id id of the job (required)
     * @param parentId parentId of joblog (required)
     * @return ResponseEntity&lt;List&lt;String&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<String>> getChildrenUsingGET1WithHttpInfo(Long id, Long parentId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getChildrenUsingGET1");
        }
        // verify the required parameter 'parentId' is set
        if (parentId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'parentId' when calling getChildrenUsingGET1");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        uriVariables.put("parentId", parentId);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}/joblog/children/{parentId}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<List<String>> returnType = new ParameterizedTypeReference<List<String>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Query job logs of one given job.
     * Get list of job logs featuring filtering (using RSQL), ordering and paging. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_GETLIST&#x27;.
     * <p><b>200</b> - The requested job log entries have been fetched and returned in the response body.
     * @param id id of the job owning retrieved job log entries (required)
     * @param limit Count of elements to retrieve from the given offset. MUST be used in conjunction with offset. (optional)
     * @param offset Index of the first element to retrieve. MUST be used in conjunction with limit. (optional)
     * @param orderby Sorting order of the queried result set having the following SQL-like syntax : attribute1[.asc|.desc][;attributeN[.asc|.desc]]. (optional)
     * @param search RSQL filtering search criteria. (optional)
     * @return List&lt;JobLog&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<JobLog> getUsingGET6(Long id, Integer limit, Integer offset, String orderby, String search) throws RestClientException {
        return getUsingGET6WithHttpInfo(id, limit, offset, orderby, search).getBody();
    }

    /**
     * Query job logs of one given job.
     * Get list of job logs featuring filtering (using RSQL), ordering and paging. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_GETLIST&#x27;.
     * <p><b>200</b> - The requested job log entries have been fetched and returned in the response body.
     * @param id id of the job owning retrieved job log entries (required)
     * @param limit Count of elements to retrieve from the given offset. MUST be used in conjunction with offset. (optional)
     * @param offset Index of the first element to retrieve. MUST be used in conjunction with limit. (optional)
     * @param orderby Sorting order of the queried result set having the following SQL-like syntax : attribute1[.asc|.desc][;attributeN[.asc|.desc]]. (optional)
     * @param search RSQL filtering search criteria. (optional)
     * @return ResponseEntity&lt;List&lt;JobLog&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<JobLog>> getUsingGET6WithHttpInfo(Long id, Integer limit, Integer offset, String orderby, String search) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getUsingGET6");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}/joblog").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<List<JobLog>> returnType = new ParameterizedTypeReference<List<JobLog>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Create one job log for one given job.
     * Creates a new job log and receive the new job log data in the response body. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_ADDOBJECTID&#x27;.
     * <p><b>201</b> - A new job log has been created out of the request body. The created job log entry has been sent in the response body.
     * @param body representation of the job log to create (required)
     * @param id id of the job owning the job log to create (required)
     * @return JobLog
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public JobLog postUsingPOST4(JobLog body, Long id) throws RestClientException {
        return postUsingPOST4WithHttpInfo(body, id).getBody();
    }

    /**
     * Create one job log for one given job.
     * Creates a new job log and receive the new job log data in the response body. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_ADDOBJECTID&#x27;.
     * <p><b>201</b> - A new job log has been created out of the request body. The created job log entry has been sent in the response body.
     * @param body representation of the job log to create (required)
     * @param id id of the job owning the job log to create (required)
     * @return ResponseEntity&lt;JobLog&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<JobLog> postUsingPOST4WithHttpInfo(JobLog body, Long id) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling postUsingPOST4");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling postUsingPOST4");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}/joblog").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<JobLog> returnType = new ParameterizedTypeReference<JobLog>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
