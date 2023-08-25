package de.muenchen.oss.digiwf.ocecosmos.integration.gen.api;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.ApiClient;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.StreamingResponseBody;
import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.WorkflowExecution;

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

@Component("de.muenchen.oss.digiwf.ocecosmos.integration.gen.api.WorkflowOperationsApi")
public class WorkflowOperationsApi {
    private ApiClient apiClient;

    public WorkflowOperationsApi() {
        this(new ApiClient());
    }

    @Autowired
    public WorkflowOperationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete one given workflow execution
     * Deletes specified workflow execution resource identified by its UUID. Pending and running workflow executions wont&#x27;t be deleted nor cancelled. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_DELETEOBJECT&#x27;.
     * <p><b>204</b> - The specified workflow execution has been deleted.
     * <p><b>409</b> - The specified workflow execution could not be deleted because it is pending or running. This request should succeed when the workflow execution has been processed.
     * @param uuid uuid of the workflow execution object to delete (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteUsingDELETE5(String uuid) throws RestClientException {
        deleteUsingDELETE5WithHttpInfo(uuid);
    }

    /**
     * Delete one given workflow execution
     * Deletes specified workflow execution resource identified by its UUID. Pending and running workflow executions wont&#x27;t be deleted nor cancelled. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_DELETEOBJECT&#x27;.
     * <p><b>204</b> - The specified workflow execution has been deleted.
     * <p><b>409</b> - The specified workflow execution could not be deleted because it is pending or running. This request should succeed when the workflow execution has been processed.
     * @param uuid uuid of the workflow execution object to delete (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteUsingDELETE5WithHttpInfo(String uuid) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'uuid' is set
        if (uuid == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'uuid' when calling deleteUsingDELETE5");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("uuid", uuid);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/workflows/execute/{uuid}").buildAndExpand(uriVariables).toUriString();
        
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
     * Retrieve all workflow executions
     * Get all workflow execution resources for execution monitoring and result retrieval. Use dictionaryKeys and nestingLimit query parameters to add output dictionary content to the response. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_GETLIST&#x27;.
     * <p><b>200</b> - All workflow execution resources have been fetched and returned in the response body.
     * @param dictionaryKeys Semicolon separated enumerations of keys to output in the workflow execution resources dictionary. SQL like statement wildcards supported. (optional)
     * @param nestingLimit Nesting limit for output workflow execution resource dictionary (optional)
     * @return List&lt;WorkflowExecution&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<WorkflowExecution> getUsingGET14(String dictionaryKeys, Integer nestingLimit) throws RestClientException {
        return getUsingGET14WithHttpInfo(dictionaryKeys, nestingLimit).getBody();
    }

    /**
     * Retrieve all workflow executions
     * Get all workflow execution resources for execution monitoring and result retrieval. Use dictionaryKeys and nestingLimit query parameters to add output dictionary content to the response. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_GETLIST&#x27;.
     * <p><b>200</b> - All workflow execution resources have been fetched and returned in the response body.
     * @param dictionaryKeys Semicolon separated enumerations of keys to output in the workflow execution resources dictionary. SQL like statement wildcards supported. (optional)
     * @param nestingLimit Nesting limit for output workflow execution resource dictionary (optional)
     * @return ResponseEntity&lt;List&lt;WorkflowExecution&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<WorkflowExecution>> getUsingGET14WithHttpInfo(String dictionaryKeys, Integer nestingLimit) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/webapi/v3/workflows/execute").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dictionaryKeys", dictionaryKeys));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "nestingLimit", nestingLimit));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<List<WorkflowExecution>> returnType = new ParameterizedTypeReference<List<WorkflowExecution>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Get one given workflow execution
     * Get one workflow execution resource identified by its UUID for execution monitoring and result retrieval. Use dictionaryKeys and nestingLimit query parameters to add output dictionary content to the response. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_GETOBJECT&#x27;.
     * <p><b>200</b> - The specified workflow execution resource has been fetched and returned in the response body.
     * @param uuid uuid of the workflow execution object to retrieve (required)
     * @param dictionaryKeys Semicolon separated enumerations of keys to output in the workflow execution resource dictionary. SQL like statement wildcards supported. (optional)
     * @param nestingLimit Nesting limit for output workflow execution resource dictionary (optional)
     * @return WorkflowExecution
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public WorkflowExecution getUsingGET15(String uuid, String dictionaryKeys, Integer nestingLimit) throws RestClientException {
        return getUsingGET15WithHttpInfo(uuid, dictionaryKeys, nestingLimit).getBody();
    }

    /**
     * Get one given workflow execution
     * Get one workflow execution resource identified by its UUID for execution monitoring and result retrieval. Use dictionaryKeys and nestingLimit query parameters to add output dictionary content to the response. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_GETOBJECT&#x27;.
     * <p><b>200</b> - The specified workflow execution resource has been fetched and returned in the response body.
     * @param uuid uuid of the workflow execution object to retrieve (required)
     * @param dictionaryKeys Semicolon separated enumerations of keys to output in the workflow execution resource dictionary. SQL like statement wildcards supported. (optional)
     * @param nestingLimit Nesting limit for output workflow execution resource dictionary (optional)
     * @return ResponseEntity&lt;WorkflowExecution&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<WorkflowExecution> getUsingGET15WithHttpInfo(String uuid, String dictionaryKeys, Integer nestingLimit) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'uuid' is set
        if (uuid == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'uuid' when calling getUsingGET15");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("uuid", uuid);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/workflows/execute/{uuid}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dictionaryKeys", dictionaryKeys));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "nestingLimit", nestingLimit));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<WorkflowExecution> returnType = new ParameterizedTypeReference<WorkflowExecution>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Run a workflow by its alias or uuid. Note: see documentation, how to use it!
     * Run a workflow by its alias or uuid with prefix UUID. Requires sysHUB permission &#x27;PERM_IADMINSERVICE_GETWORKFLOWITEM&#x27;
     * <p><b>200</b> - The workflow has been executed successfully.
     * @param workflowAlias Alias name of the workflow to be called or UUID as prefix calls wf with uuid. (required)
     * @return StreamingResponseBody
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public StreamingResponseBody postByAliasUsingDELETE(String workflowAlias) throws RestClientException {
        return postByAliasUsingDELETEWithHttpInfo(workflowAlias).getBody();
    }

    /**
     * Run a workflow by its alias or uuid. Note: see documentation, how to use it!
     * Run a workflow by its alias or uuid with prefix UUID. Requires sysHUB permission &#x27;PERM_IADMINSERVICE_GETWORKFLOWITEM&#x27;
     * <p><b>200</b> - The workflow has been executed successfully.
     * @param workflowAlias Alias name of the workflow to be called or UUID as prefix calls wf with uuid. (required)
     * @return ResponseEntity&lt;StreamingResponseBody&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StreamingResponseBody> postByAliasUsingDELETEWithHttpInfo(String workflowAlias) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'workflowAlias' is set
        if (workflowAlias == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'workflowAlias' when calling postByAliasUsingDELETE");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("workflowAlias", workflowAlias);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/workflows/execute/alias/{workflowAlias}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json", "text/plain"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<StreamingResponseBody> returnType = new ParameterizedTypeReference<StreamingResponseBody>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Run a workflow by its alias or uuid. Note: see documentation, how to use it!
     * Run a workflow by its alias or uuid with prefix UUID. Requires sysHUB permission &#x27;PERM_IADMINSERVICE_GETWORKFLOWITEM&#x27;
     * <p><b>200</b> - The workflow has been executed successfully.
     * @param workflowAlias Alias name of the workflow to be called or UUID as prefix calls wf with uuid. (required)
     * @return StreamingResponseBody
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public StreamingResponseBody postByAliasUsingGET(String workflowAlias) throws RestClientException {
        return postByAliasUsingGETWithHttpInfo(workflowAlias).getBody();
    }

    /**
     * Run a workflow by its alias or uuid. Note: see documentation, how to use it!
     * Run a workflow by its alias or uuid with prefix UUID. Requires sysHUB permission &#x27;PERM_IADMINSERVICE_GETWORKFLOWITEM&#x27;
     * <p><b>200</b> - The workflow has been executed successfully.
     * @param workflowAlias Alias name of the workflow to be called or UUID as prefix calls wf with uuid. (required)
     * @return ResponseEntity&lt;StreamingResponseBody&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StreamingResponseBody> postByAliasUsingGETWithHttpInfo(String workflowAlias) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'workflowAlias' is set
        if (workflowAlias == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'workflowAlias' when calling postByAliasUsingGET");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("workflowAlias", workflowAlias);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/workflows/execute/alias/{workflowAlias}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json", "text/plain"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<StreamingResponseBody> returnType = new ParameterizedTypeReference<StreamingResponseBody>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Run a workflow by its alias or uuid. Note: see documentation, how to use it!
     * Run a workflow by its alias or uuid with prefix UUID. Requires sysHUB permission &#x27;PERM_IADMINSERVICE_GETWORKFLOWITEM&#x27;
     * <p><b>200</b> - The workflow has been executed successfully.
     * @param workflowAlias Alias name of the workflow to be called or UUID as prefix calls wf with uuid. (required)
     * @return StreamingResponseBody
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public StreamingResponseBody postByAliasUsingPOST(String workflowAlias) throws RestClientException {
        return postByAliasUsingPOSTWithHttpInfo(workflowAlias).getBody();
    }

    /**
     * Run a workflow by its alias or uuid. Note: see documentation, how to use it!
     * Run a workflow by its alias or uuid with prefix UUID. Requires sysHUB permission &#x27;PERM_IADMINSERVICE_GETWORKFLOWITEM&#x27;
     * <p><b>200</b> - The workflow has been executed successfully.
     * @param workflowAlias Alias name of the workflow to be called or UUID as prefix calls wf with uuid. (required)
     * @return ResponseEntity&lt;StreamingResponseBody&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StreamingResponseBody> postByAliasUsingPOSTWithHttpInfo(String workflowAlias) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'workflowAlias' is set
        if (workflowAlias == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'workflowAlias' when calling postByAliasUsingPOST");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("workflowAlias", workflowAlias);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/workflows/execute/alias/{workflowAlias}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json", "text/plain"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<StreamingResponseBody> returnType = new ParameterizedTypeReference<StreamingResponseBody>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Run a workflow by its alias or uuid. Note: see documentation, how to use it!
     * Run a workflow by its alias or uuid with prefix UUID. Requires sysHUB permission &#x27;PERM_IADMINSERVICE_GETWORKFLOWITEM&#x27;
     * <p><b>200</b> - The workflow has been executed successfully.
     * @param workflowAlias Alias name of the workflow to be called or UUID as prefix calls wf with uuid. (required)
     * @return StreamingResponseBody
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public StreamingResponseBody postByAliasUsingPUT(String workflowAlias) throws RestClientException {
        return postByAliasUsingPUTWithHttpInfo(workflowAlias).getBody();
    }

    /**
     * Run a workflow by its alias or uuid. Note: see documentation, how to use it!
     * Run a workflow by its alias or uuid with prefix UUID. Requires sysHUB permission &#x27;PERM_IADMINSERVICE_GETWORKFLOWITEM&#x27;
     * <p><b>200</b> - The workflow has been executed successfully.
     * @param workflowAlias Alias name of the workflow to be called or UUID as prefix calls wf with uuid. (required)
     * @return ResponseEntity&lt;StreamingResponseBody&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StreamingResponseBody> postByAliasUsingPUTWithHttpInfo(String workflowAlias) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'workflowAlias' is set
        if (workflowAlias == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'workflowAlias' when calling postByAliasUsingPUT");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("workflowAlias", workflowAlias);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/workflows/execute/alias/{workflowAlias}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json", "text/plain"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<StreamingResponseBody> returnType = new ParameterizedTypeReference<StreamingResponseBody>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Run a workflow by its wfuuid. Use &#x27;currentJobId&#x27; in the body to push it to currentJob
     * Run a workflow by its wfuuid Requires sysHUB permission &#x27;PERM_IADMINSERVICE_GETWORKFLOWITEM&#x27;
     * <p><b>200</b> - The workflow has been executed successfully.
     * @param workflowUUID wfuuid of the workflow to run. (required)
     * @param body Simple json object to push it to the dictionary (optional)
     * @param jmsUiQueue The jmsUIQueue to send back results (optional)
     * @param jobId The jobids (comma seperated) to push it to the loopobject (optional)
     * @return StreamingResponseBody
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public StreamingResponseBody postByWFUUIDUsingPOST(String workflowUUID, Object body, String jmsUiQueue, String jobId) throws RestClientException {
        return postByWFUUIDUsingPOSTWithHttpInfo(workflowUUID, body, jmsUiQueue, jobId).getBody();
    }

    /**
     * Run a workflow by its wfuuid. Use &#x27;currentJobId&#x27; in the body to push it to currentJob
     * Run a workflow by its wfuuid Requires sysHUB permission &#x27;PERM_IADMINSERVICE_GETWORKFLOWITEM&#x27;
     * <p><b>200</b> - The workflow has been executed successfully.
     * @param workflowUUID wfuuid of the workflow to run. (required)
     * @param body Simple json object to push it to the dictionary (optional)
     * @param jmsUiQueue The jmsUIQueue to send back results (optional)
     * @param jobId The jobids (comma seperated) to push it to the loopobject (optional)
     * @return ResponseEntity&lt;StreamingResponseBody&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StreamingResponseBody> postByWFUUIDUsingPOSTWithHttpInfo(String workflowUUID, Object body, String jmsUiQueue, String jobId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'workflowUUID' is set
        if (workflowUUID == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'workflowUUID' when calling postByWFUUIDUsingPOST");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("workflowUUID", workflowUUID);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/workflows/execute/wfuuid/{workflowUUID}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "jmsUiQueue", jmsUiQueue));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "jobId", jobId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<StreamingResponseBody> returnType = new ParameterizedTypeReference<StreamingResponseBody>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Create and run one given workflow execution
     * Executes specified workflow and creates a workflow execution resource keeping track of the execution. Requires a workflow execution representation in the request body specifying at least a valid workflowUUID. Refer to WorflowExecution model documentation for other available input parameters. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_GETWORKFLOWITEM&#x27;.
     * <p><b>201</b> - The specified workflow has been executed successfully. Ad-hoc workflow execution resource has been created and stored.
     * <p><b>202</b> - The specified workflow execution request has been accepted and will be processed. Ad-hoc workflow execution resource has been created and stored.
     * <p><b>503</b> - The workflow execution request has been rejected because the WorkflowExecutor is overloaded. The request can be resubmitted later. WorkflowExecutor thread count and queue size can also be increased in order to minimize this issue.
     * @param body Execution options and parameters wrapped in a workflow execution object representation (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void postUsingPOST9(WorkflowExecution body) throws RestClientException {
        postUsingPOST9WithHttpInfo(body);
    }

    /**
     * Create and run one given workflow execution
     * Executes specified workflow and creates a workflow execution resource keeping track of the execution. Requires a workflow execution representation in the request body specifying at least a valid workflowUUID. Refer to WorflowExecution model documentation for other available input parameters. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_GETWORKFLOWITEM&#x27;.
     * <p><b>201</b> - The specified workflow has been executed successfully. Ad-hoc workflow execution resource has been created and stored.
     * <p><b>202</b> - The specified workflow execution request has been accepted and will be processed. Ad-hoc workflow execution resource has been created and stored.
     * <p><b>503</b> - The workflow execution request has been rejected because the WorkflowExecutor is overloaded. The request can be resubmitted later. WorkflowExecutor thread count and queue size can also be increased in order to minimize this issue.
     * @param body Execution options and parameters wrapped in a workflow execution object representation (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postUsingPOST9WithHttpInfo(WorkflowExecution body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling postUsingPOST9");
        }
        String path = UriComponentsBuilder.fromPath("/webapi/v3/workflows/execute").build().toUriString();
        
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
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
