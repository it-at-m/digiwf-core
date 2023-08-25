package de.muenchen.oss.digiwf.ocecosmos.integration.gen.api;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.ApiClient;

import java.io.File;
import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.Jobs;

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

@Component("de.muenchen.oss.digiwf.ocecosmos.integration.gen.api.JobOperationsApi")
public class JobOperationsApi {
    private ApiClient apiClient;

    public JobOperationsApi() {
        this(new ApiClient());
    }

    @Autowired
    public JobOperationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete one given job
     * Deletes the job identified by the job id. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_DELETEJOBENTRY&#x27;.
     * <p><b>204</b> - The job has been successfully deleted.
     * @param id id of the job to delete (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteUsingDELETE4(Long id) throws RestClientException {
        deleteUsingDELETE4WithHttpInfo(id);
    }

    /**
     * Delete one given job
     * Deletes the job identified by the job id. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_DELETEJOBENTRY&#x27;.
     * <p><b>204</b> - The job has been successfully deleted.
     * @param id id of the job to delete (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteUsingDELETE4WithHttpInfo(Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling deleteUsingDELETE4");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}").buildAndExpand(uriVariables).toUriString();
        
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
     * Query jobs
     * Get list of jobs featuring filtering (using RSQL), ordering and paging. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_GETJOBS&#x27;.
     * <p><b>200</b> - The requested jobs have been fetched and returned in the response body.
     * @param limit Count of elements to retrieve from the given offset. MUST be used in conjunction with offset. (optional)
     * @param offset Index of the first element to retrieve. MUST be used in conjunction with limit. (optional)
     * @param orderby Sorting order of the queried result set having the following SQL-like syntax : attribute1[.asc|.desc][;attributeN[.asc|.desc]]. (optional)
     * @param search RSQL filtering search criteria. (optional)
     * @return List&lt;Jobs&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<Jobs> getUsingGET7(Integer limit, Integer offset, String orderby, String search) throws RestClientException {
        return getUsingGET7WithHttpInfo(limit, offset, orderby, search).getBody();
    }

    /**
     * Query jobs
     * Get list of jobs featuring filtering (using RSQL), ordering and paging. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_GETJOBS&#x27;.
     * <p><b>200</b> - The requested jobs have been fetched and returned in the response body.
     * @param limit Count of elements to retrieve from the given offset. MUST be used in conjunction with offset. (optional)
     * @param offset Index of the first element to retrieve. MUST be used in conjunction with limit. (optional)
     * @param orderby Sorting order of the queried result set having the following SQL-like syntax : attribute1[.asc|.desc][;attributeN[.asc|.desc]]. (optional)
     * @param search RSQL filtering search criteria. (optional)
     * @return ResponseEntity&lt;List&lt;Jobs&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<Jobs>> getUsingGET7WithHttpInfo(Integer limit, Integer offset, String orderby, String search) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs").build().toUriString();
        
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

        ParameterizedTypeReference<List<Jobs>> returnType = new ParameterizedTypeReference<List<Jobs>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Retrieve one given job
     * Receive the data of the requested job in the response body. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_GETJOB&#x27;.
     * <p><b>200</b> - The requested job has been fetched and returned in the response body.
     * @param id id of the job to retrieve (required)
     * @return Jobs
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Jobs getUsingGET8(Long id) throws RestClientException {
        return getUsingGET8WithHttpInfo(id).getBody();
    }

    /**
     * Retrieve one given job
     * Receive the data of the requested job in the response body. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_GETJOB&#x27;.
     * <p><b>200</b> - The requested job has been fetched and returned in the response body.
     * @param id id of the job to retrieve (required)
     * @return ResponseEntity&lt;Jobs&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Jobs> getUsingGET8WithHttpInfo(Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getUsingGET8");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<Jobs> returnType = new ParameterizedTypeReference<Jobs>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Patch one given job
     * Patches or replaces single job fields with data specified in the request body. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_UPDATEJOB&#x27;.
     * <p><b>200</b> - The job has been successfully patched. The updated resource has been sent in the response body.
     * @param body Partial or complete representation of the job to update. Any unspecified attribute will not be altered. The job id must not be included. (required)
     * @param id id of the job to update (required)
     * @return Jobs
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Jobs patchUsingPATCH1(Jobs body, Long id) throws RestClientException {
        return patchUsingPATCH1WithHttpInfo(body, id).getBody();
    }

    /**
     * Patch one given job
     * Patches or replaces single job fields with data specified in the request body. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_UPDATEJOB&#x27;.
     * <p><b>200</b> - The job has been successfully patched. The updated resource has been sent in the response body.
     * @param body Partial or complete representation of the job to update. Any unspecified attribute will not be altered. The job id must not be included. (required)
     * @param id id of the job to update (required)
     * @return ResponseEntity&lt;Jobs&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Jobs> patchUsingPATCH1WithHttpInfo(Jobs body, Long id) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling patchUsingPATCH1");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling patchUsingPATCH1");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<Jobs> returnType = new ParameterizedTypeReference<Jobs>() {};
        return apiClient.invokeAPI(path, HttpMethod.PATCH, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Submit one job along with its source and/or ticket file
     * Create a job out of a job definition, a source file and/or a ticket file. The default job status is \&quot;received\&quot;. This operation requires the following permissions: PERM_IJOBSERVICE_ADDJOB, PERM_IJOBSERVICE_UPDATEJOB
     * <p><b>201</b> - A job has been created out of the input definition, source file and/or ticket file.
     * @param job  (optional)
     * @param sourceFile  (optional)
     * @param ticketFile  (optional)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void postUsingPOST3(Object job, java.nio.file.Path sourceFile, java.nio.file.Path ticketFile) throws RestClientException {
        postUsingPOST3WithHttpInfo(job, sourceFile, ticketFile);
    }

    /**
     * Submit one job along with its source and/or ticket file
     * Create a job out of a job definition, a source file and/or a ticket file. The default job status is \&quot;received\&quot;. This operation requires the following permissions: PERM_IJOBSERVICE_ADDJOB, PERM_IJOBSERVICE_UPDATEJOB
     * <p><b>201</b> - A job has been created out of the input definition, source file and/or ticket file.
     * @param job  (optional)
     * @param sourceFile  (optional)
     * @param ticketFile  (optional)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postUsingPOST3WithHttpInfo(Object job, java.nio.file.Path sourceFile, java.nio.file.Path ticketFile) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/submit").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (job != null)
            formParams.add("job", job);
        if (sourceFile != null)
            formParams.add("sourceFile", new FileSystemResource(sourceFile));
        if (ticketFile != null)
            formParams.add("ticketFile", new FileSystemResource(ticketFile));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Create one job
     * Creates a new job and receive the new jobs data in the response body. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_ADDJOB&#x27;.
     * <p><b>201</b> - A new job has been created with values from the request body. The created job has been sent in the response body.
     * @param body Representation of the job to create (required)
     * @return Jobs
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Jobs postUsingPOST5(Jobs body) throws RestClientException {
        return postUsingPOST5WithHttpInfo(body).getBody();
    }

    /**
     * Create one job
     * Creates a new job and receive the new jobs data in the response body. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_ADDJOB&#x27;.
     * <p><b>201</b> - A new job has been created with values from the request body. The created job has been sent in the response body.
     * @param body Representation of the job to create (required)
     * @return ResponseEntity&lt;Jobs&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Jobs> postUsingPOST5WithHttpInfo(Jobs body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling postUsingPOST5");
        }
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs").build().toUriString();
        
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

        ParameterizedTypeReference<Jobs> returnType = new ParameterizedTypeReference<Jobs>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Replace one given job
     * Replaces the job identified by job id with data specified in the request body. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_UPDATEJOB&#x27;.
     * <p><b>200</b> - The job has been successfully replaced. The updated resource has been sent in the response body.
     * @param body Partial or complete representation of the job to update. Any unspecified attribute will bet set to its default value. The job id must not be included. (required)
     * @param id id of the job to update (required)
     * @return Jobs
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Jobs putUsingPUT3(Jobs body, Long id) throws RestClientException {
        return putUsingPUT3WithHttpInfo(body, id).getBody();
    }

    /**
     * Replace one given job
     * Replaces the job identified by job id with data specified in the request body. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_UPDATEJOB&#x27;.
     * <p><b>200</b> - The job has been successfully replaced. The updated resource has been sent in the response body.
     * @param body Partial or complete representation of the job to update. Any unspecified attribute will bet set to its default value. The job id must not be included. (required)
     * @param id id of the job to update (required)
     * @return ResponseEntity&lt;Jobs&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Jobs> putUsingPUT3WithHttpInfo(Jobs body, Long id) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling putUsingPUT3");
        }
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling putUsingPUT3");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<Jobs> returnType = new ParameterizedTypeReference<Jobs>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Upload the source file of one given job.
     * Upload the source file for a given job and update corresponding job field. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_UPDATEJOB&#x27;.
     * <p><b>201</b> - The source file has been uploaded successfully and the related job was updated accordingly.
     * @param id id of the job owning the file (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void uploadSourceFileUsingPOST15(Long id) throws RestClientException {
        uploadSourceFileUsingPOST15WithHttpInfo(id);
    }

    /**
     * Upload the source file of one given job.
     * Upload the source file for a given job and update corresponding job field. Requires COSMOS permission &#x27;PERM_IJOBSERVICE_UPDATEJOB&#x27;.
     * <p><b>201</b> - The source file has been uploaded successfully and the related job was updated accordingly.
     * @param id id of the job owning the file (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> uploadSourceFileUsingPOST15WithHttpInfo(Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling uploadSourceFileUsingPOST15");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}/uploadSourceFile").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Upload the ticket file of one given job.
     * Upload the ticket file for a given job and update corresponding job field.
     * <p><b>201</b> - The ticket file has been uploaded successfully and the related job was updated accordingly.
     * @param id id of the job owning the file (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void uploadTicketFileUsingPOST15(Long id) throws RestClientException {
        uploadTicketFileUsingPOST15WithHttpInfo(id);
    }

    /**
     * Upload the ticket file of one given job.
     * Upload the ticket file for a given job and update corresponding job field.
     * <p><b>201</b> - The ticket file has been uploaded successfully and the related job was updated accordingly.
     * @param id id of the job owning the file (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> uploadTicketFileUsingPOST15WithHttpInfo(Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling uploadTicketFileUsingPOST15");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}/uploadTicketFile").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
