package de.muenchen.oss.digiwf.ocecosmos.integration.gen.api;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.ApiClient;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.JobAttribute;

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

@Component("de.muenchen.oss.digiwf.ocecosmos.integration.gen.api.JobAttributeOperationsApi")
public class JobAttributeOperationsApi {
    private ApiClient apiClient;

    public JobAttributeOperationsApi() {
        this(new ApiClient());
    }

    @Autowired
    public JobAttributeOperationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete one given job attribute
     * Delete specified job attribute identified by job id and attribute name. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_DELETEOBJECT&#x27;.
     * <p><b>204</b> - The specified job attribute has been deleted.
     * @param attributeName name of the job attribute to delete (required)
     * @param id id of the job owning the attribute to delete (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteUsingDELETE2(String attributeName, Long id) throws RestClientException {
        deleteUsingDELETE2WithHttpInfo(attributeName, id);
    }

    /**
     * Delete one given job attribute
     * Delete specified job attribute identified by job id and attribute name. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_DELETEOBJECT&#x27;.
     * <p><b>204</b> - The specified job attribute has been deleted.
     * @param attributeName name of the job attribute to delete (required)
     * @param id id of the job owning the attribute to delete (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteUsingDELETE2WithHttpInfo(String attributeName, Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'attributeName' is set
        if (attributeName == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'attributeName' when calling deleteUsingDELETE2");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling deleteUsingDELETE2");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("attributeName", attributeName);
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}/attributes/{attributeName}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Delete all job attributes of one given job
     * Delete job attributes identified by job id. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_DELETEOBJECT&#x27;.
     * <p><b>204</b> - The specified job attributes collection has been cleared.
     * @param id id of the job owning the attributes to delete (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteUsingDELETE3(Long id) throws RestClientException {
        deleteUsingDELETE3WithHttpInfo(id);
    }

    /**
     * Delete all job attributes of one given job
     * Delete job attributes identified by job id. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_DELETEOBJECT&#x27;.
     * <p><b>204</b> - The specified job attributes collection has been cleared.
     * @param id id of the job owning the attributes to delete (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteUsingDELETE3WithHttpInfo(Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling deleteUsingDELETE3");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}/attributes").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Retrieve one given job attribute
     * Returns specified job attribute identified by job id and attribute name. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_GETJOB&#x27;.
     * <p><b>200</b> - The specified job attribute has been fetched and returned in the response body.
     * @param attributeName name of the job attribute to retrieve (required)
     * @param id id of the job owning the attribute to retrieve (required)
     * @return JobAttribute
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public JobAttribute getUsingGET3(String attributeName, Long id) throws RestClientException {
        return getUsingGET3WithHttpInfo(attributeName, id).getBody();
    }

    /**
     * Retrieve one given job attribute
     * Returns specified job attribute identified by job id and attribute name. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_GETJOB&#x27;.
     * <p><b>200</b> - The specified job attribute has been fetched and returned in the response body.
     * @param attributeName name of the job attribute to retrieve (required)
     * @param id id of the job owning the attribute to retrieve (required)
     * @return ResponseEntity&lt;JobAttribute&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<JobAttribute> getUsingGET3WithHttpInfo(String attributeName, Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'attributeName' is set
        if (attributeName == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'attributeName' when calling getUsingGET3");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getUsingGET3");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("attributeName", attributeName);
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}/attributes/{attributeName}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<JobAttribute> returnType = new ParameterizedTypeReference<JobAttribute>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Retrieve all job attributes of one given job
     * Returns job attributes identified by job id. Returns an empty list if no job with matching id is found. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_GETJOB&#x27;.
     * <p><b>200</b> - The specified job attribute collection has been fetched and returned in the response body.
     * @param id id of the job owning the attributes to retrieve (required)
     * @return List&lt;JobAttribute&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<JobAttribute> getUsingGET4(Long id) throws RestClientException {
        return getUsingGET4WithHttpInfo(id).getBody();
    }

    /**
     * Retrieve all job attributes of one given job
     * Returns job attributes identified by job id. Returns an empty list if no job with matching id is found. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_GETJOB&#x27;.
     * <p><b>200</b> - The specified job attribute collection has been fetched and returned in the response body.
     * @param id id of the job owning the attributes to retrieve (required)
     * @return ResponseEntity&lt;List&lt;JobAttribute&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<JobAttribute>> getUsingGET4WithHttpInfo(Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getUsingGET4");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}/attributes").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<List<JobAttribute>> returnType = new ParameterizedTypeReference<List<JobAttribute>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Query multiple file status of one given job
     * Get list of a job attributes for given job supporting filtering (using RSQL), ordering and paging. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_GETLIST&#x27;.
     * <p><b>200</b> - The requested job attributes have been fetched and returned in the response body.
     * @param id Job id for which job attributes will be retrieved (required)
     * @param limit Count of elements to retrieve from the given offset. MUST be used in conjunction with offset. (optional)
     * @param offset Index of the first element to retrieve. MUST be used in conjunction with limit. (optional)
     * @param orderby Sorting order of the queried result set having the following SQL-like syntax : attribute1[.asc|.desc][;attributeN[.asc|.desc]]. (optional)
     * @param search RSQL filtering search criteria. (optional)
     * @return List&lt;JobAttribute&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<JobAttribute> getUsingGET5(Long id, Integer limit, Integer offset, String orderby, String search) throws RestClientException {
        return getUsingGET5WithHttpInfo(id, limit, offset, orderby, search).getBody();
    }

    /**
     * Query multiple file status of one given job
     * Get list of a job attributes for given job supporting filtering (using RSQL), ordering and paging. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_GETLIST&#x27;.
     * <p><b>200</b> - The requested job attributes have been fetched and returned in the response body.
     * @param id Job id for which job attributes will be retrieved (required)
     * @param limit Count of elements to retrieve from the given offset. MUST be used in conjunction with offset. (optional)
     * @param offset Index of the first element to retrieve. MUST be used in conjunction with limit. (optional)
     * @param orderby Sorting order of the queried result set having the following SQL-like syntax : attribute1[.asc|.desc][;attributeN[.asc|.desc]]. (optional)
     * @param search RSQL filtering search criteria. (optional)
     * @return ResponseEntity&lt;List&lt;JobAttribute&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<JobAttribute>> getUsingGET5WithHttpInfo(Long id, Integer limit, Integer offset, String orderby, String search) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getUsingGET5");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}/jobAttributes").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<List<JobAttribute>> returnType = new ParameterizedTypeReference<List<JobAttribute>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Create or replace one job attribute
     * Creates or replaces specified job attribute identified by job id and attribute name. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_UPDATEJOB&#x27;.
     * <p><b>201</b> - The specified job attribute has been created
     * <p><b>204</b> - The specified job attribute has been updated
     * @param body job attribute representation as it will be created or updated (required)
     * @param attributeName name of the attribute to create or update (required)
     * @param id id of the job owning the attribute to create or update (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void putUsingPUT1(JobAttribute body, String attributeName, Long id) throws RestClientException {
        putUsingPUT1WithHttpInfo(body, attributeName, id);
    }

    /**
     * Create or replace one job attribute
     * Creates or replaces specified job attribute identified by job id and attribute name. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_UPDATEJOB&#x27;.
     * <p><b>201</b> - The specified job attribute has been created
     * <p><b>204</b> - The specified job attribute has been updated
     * @param body job attribute representation as it will be created or updated (required)
     * @param attributeName name of the attribute to create or update (required)
     * @param id id of the job owning the attribute to create or update (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putUsingPUT1WithHttpInfo(JobAttribute body, String attributeName, Long id) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling putUsingPUT1");
        }
        // verify the required parameter 'attributeName' is set
        if (attributeName == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'attributeName' when calling putUsingPUT1");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling putUsingPUT1");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("attributeName", attributeName);
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}/attributes/{attributeName}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Replace all job attributes of one given job
     * Replace all job attributes for the specified job. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_UPDATEJOB&#x27;.
     * <p><b>201</b> - The specified job attributes collection has been replaced with input data.
     * @param body array of job attribute representations that will replace the given job attributes collection (required)
     * @param id id of the job owning the attributes to replace (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void putUsingPUT2(List<JobAttribute> body, Long id) throws RestClientException {
        putUsingPUT2WithHttpInfo(body, id);
    }

    /**
     * Replace all job attributes of one given job
     * Replace all job attributes for the specified job. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_UPDATEJOB&#x27;.
     * <p><b>201</b> - The specified job attributes collection has been replaced with input data.
     * @param body array of job attribute representations that will replace the given job attributes collection (required)
     * @param id id of the job owning the attributes to replace (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> putUsingPUT2WithHttpInfo(List<JobAttribute> body, Long id) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling putUsingPUT2");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling putUsingPUT2");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}/attributes").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
