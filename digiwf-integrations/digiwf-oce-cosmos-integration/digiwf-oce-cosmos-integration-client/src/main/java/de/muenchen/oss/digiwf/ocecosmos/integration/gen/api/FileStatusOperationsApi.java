package de.muenchen.oss.digiwf.ocecosmos.integration.gen.api;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.ApiClient;

import java.io.File;
import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.Filestatus;

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

@Component("de.muenchen.oss.digiwf.ocecosmos.integration.gen.api.FileStatusOperationsApi")
public class FileStatusOperationsApi {
    private ApiClient apiClient;

    public FileStatusOperationsApi() {
        this(new ApiClient());
    }

    @Autowired
    public FileStatusOperationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Delete all file status of one given job
     * Delete all file status of a given job. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_DELETEOBJECT&#x27;.
     * <p><b>200</b> - All file status entries for specified job have been successfully deleted.
     * @param id Job id of which file status will be deleted (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteUsingDELETE(Long id) throws RestClientException {
        deleteUsingDELETEWithHttpInfo(id);
    }

    /**
     * Delete all file status of one given job
     * Delete all file status of a given job. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_DELETEOBJECT&#x27;.
     * <p><b>200</b> - All file status entries for specified job have been successfully deleted.
     * @param id Job id of which file status will be deleted (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteUsingDELETEWithHttpInfo(Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling deleteUsingDELETE");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}/filestatus").buildAndExpand(uriVariables).toUriString();
        
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
     * Delete one given file status
     * Deletes the file status identified by file status id. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_DELETEOBJECT&#x27;.
     * <p><b>204</b> - The file status has been successfully deleted.
     * @param id id of the file status to delete (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void deleteUsingDELETE1(Long id) throws RestClientException {
        deleteUsingDELETE1WithHttpInfo(id);
    }

    /**
     * Delete one given file status
     * Deletes the file status identified by file status id. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_DELETEOBJECT&#x27;.
     * <p><b>204</b> - The file status has been successfully deleted.
     * @param id id of the file status to delete (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteUsingDELETE1WithHttpInfo(Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling deleteUsingDELETE1");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/filestatus/{id}").buildAndExpand(uriVariables).toUriString();
        
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
     * Query multiple file status of one given job
     * Get list of a file status for given job supporting filtering (using RSQL), ordering and paging. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_GETLIST&#x27;.
     * <p><b>200</b> - The requested file status have been fetched and returned in the response body.
     * @param id Job id for which file status will be retrieved (required)
     * @param limit Count of elements to retrieve from the given offset. MUST be used in conjunction with offset. (optional)
     * @param offset Index of the first element to retrieve. MUST be used in conjunction with limit. (optional)
     * @param orderby Sorting order of the queried result set having the following SQL-like syntax : attribute1[.asc|.desc][;attributeN[.asc|.desc]]. (optional)
     * @param search RSQL filtering search criteria. (optional)
     * @return List&lt;Filestatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<Filestatus> getUsingGET1(Long id, Integer limit, Integer offset, String orderby, String search) throws RestClientException {
        return getUsingGET1WithHttpInfo(id, limit, offset, orderby, search).getBody();
    }

    /**
     * Query multiple file status of one given job
     * Get list of a file status for given job supporting filtering (using RSQL), ordering and paging. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_GETLIST&#x27;.
     * <p><b>200</b> - The requested file status have been fetched and returned in the response body.
     * @param id Job id for which file status will be retrieved (required)
     * @param limit Count of elements to retrieve from the given offset. MUST be used in conjunction with offset. (optional)
     * @param offset Index of the first element to retrieve. MUST be used in conjunction with limit. (optional)
     * @param orderby Sorting order of the queried result set having the following SQL-like syntax : attribute1[.asc|.desc][;attributeN[.asc|.desc]]. (optional)
     * @param search RSQL filtering search criteria. (optional)
     * @return ResponseEntity&lt;List&lt;Filestatus&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<Filestatus>> getUsingGET1WithHttpInfo(Long id, Integer limit, Integer offset, String orderby, String search) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getUsingGET1");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}/filestatus").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<List<Filestatus>> returnType = new ParameterizedTypeReference<List<Filestatus>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Retrieve one given file status
     * Receive the data of the requested file status in the response body. Requires permission &#x27;PERM_IADMINSERVICE_GETOBJECT&#x27;.
     * <p><b>200</b> - The file status resource has been retrieved successfully and returned in the response body.
     * @param id id of the file status to retrieve (required)
     * @return Filestatus
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Filestatus getUsingGET2(Long id) throws RestClientException {
        return getUsingGET2WithHttpInfo(id).getBody();
    }

    /**
     * Retrieve one given file status
     * Receive the data of the requested file status in the response body. Requires permission &#x27;PERM_IADMINSERVICE_GETOBJECT&#x27;.
     * <p><b>200</b> - The file status resource has been retrieved successfully and returned in the response body.
     * @param id id of the file status to retrieve (required)
     * @return ResponseEntity&lt;Filestatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Filestatus> getUsingGET2WithHttpInfo(Long id) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling getUsingGET2");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/filestatus/{id}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<Filestatus> returnType = new ParameterizedTypeReference<Filestatus>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Patch one given file status
     * Partially update a single file status fields with data specified in the request body. Unspecified fields are left unaltered. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_UPDATEOBJECT&#x27;.
     * <p><b>200</b> - The file status has been successfully patched. The updated resource has been sent in the response body.
     * @param id id of the file status to update (required)
     * @param body Partial or complete file status representation. Only specified fields will be updated (optional)
     * @return Filestatus
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Filestatus patchUsingPATCH(Long id, Filestatus body) throws RestClientException {
        return patchUsingPATCHWithHttpInfo(id, body).getBody();
    }

    /**
     * Patch one given file status
     * Partially update a single file status fields with data specified in the request body. Unspecified fields are left unaltered. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_UPDATEOBJECT&#x27;.
     * <p><b>200</b> - The file status has been successfully patched. The updated resource has been sent in the response body.
     * @param id id of the file status to update (required)
     * @param body Partial or complete file status representation. Only specified fields will be updated (optional)
     * @return ResponseEntity&lt;Filestatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Filestatus> patchUsingPATCHWithHttpInfo(Long id, Filestatus body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling patchUsingPATCH");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/filestatus/{id}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<Filestatus> returnType = new ParameterizedTypeReference<Filestatus>() {};
        return apiClient.invokeAPI(path, HttpMethod.PATCH, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Create one file status for one given job
     * Creates a new file status and receive the new file status data in the response body. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_ADDOBJECTID&#x27;.
     * <p><b>201</b> - A new file status has been created with values from request body.
     * @param id id of the job to which the created file status will be added (required)
     * @param body New file status representation. (optional)
     * @return Filestatus
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Filestatus postUsingPOST1(Long id, Filestatus body) throws RestClientException {
        return postUsingPOST1WithHttpInfo(id, body).getBody();
    }

    /**
     * Create one file status for one given job
     * Creates a new file status and receive the new file status data in the response body. Requires COSMOS permission &#x27;PERM_IADMINSERVICE_ADDOBJECTID&#x27;.
     * <p><b>201</b> - A new file status has been created with values from request body.
     * @param id id of the job to which the created file status will be added (required)
     * @param body New file status representation. (optional)
     * @return ResponseEntity&lt;Filestatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Filestatus> postUsingPOST1WithHttpInfo(Long id, Filestatus body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling postUsingPOST1");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}/filestatus").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<Filestatus> returnType = new ParameterizedTypeReference<Filestatus>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Create one file status for one given job and upload attached file
     * Create a file status out of a file status definition and a file. Requires permission PERM_IADMINSERVICE_ADDOBJECTID
     * <p><b>201</b> - A file status has been created out of the optional representation and optional file.
     * @param id id of the job to which the created file status will be added (required)
     * @param file  (optional)
     * @param fileStatus  (optional)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void postUsingPOST2(Long id, java.nio.file.Path file, Object fileStatus) throws RestClientException {
        postUsingPOST2WithHttpInfo(id, file, fileStatus);
    }

    /**
     * Create one file status for one given job and upload attached file
     * Create a file status out of a file status definition and a file. Requires permission PERM_IADMINSERVICE_ADDOBJECTID
     * <p><b>201</b> - A file status has been created out of the optional representation and optional file.
     * @param id id of the job to which the created file status will be added (required)
     * @param file  (optional)
     * @param fileStatus  (optional)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> postUsingPOST2WithHttpInfo(Long id, java.nio.file.Path file, Object fileStatus) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling postUsingPOST2");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/jobs/{id}/filestatus/submit").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (file != null)
            formParams.add("file", new FileSystemResource(file));
        if (fileStatus != null)
            formParams.add("fileStatus", fileStatus);

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
     * Replace one given file status
     * Replaces the whole file status identified by the given file status id with the representation provided in the request body. Any unspecified attribute will bet set to its default value.  Requires COSMOS permission &#x27;PERM_IADMINSERVICE_UPDATEOBJECT&#x27;.
     * <p><b>200</b> - The file status has been successfully replaced. The updated resource has been sent in the response body.
     * @param id Filestatus ID (required)
     * @param body Partial or complete file status representation. Unspecified fields will be set to null or server-managed value. (optional)
     * @return Filestatus
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Filestatus putUsingPUT(Long id, Filestatus body) throws RestClientException {
        return putUsingPUTWithHttpInfo(id, body).getBody();
    }

    /**
     * Replace one given file status
     * Replaces the whole file status identified by the given file status id with the representation provided in the request body. Any unspecified attribute will bet set to its default value.  Requires COSMOS permission &#x27;PERM_IADMINSERVICE_UPDATEOBJECT&#x27;.
     * <p><b>200</b> - The file status has been successfully replaced. The updated resource has been sent in the response body.
     * @param id Filestatus ID (required)
     * @param body Partial or complete file status representation. Unspecified fields will be set to null or server-managed value. (optional)
     * @return ResponseEntity&lt;Filestatus&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Filestatus> putUsingPUTWithHttpInfo(Long id, Filestatus body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'id' when calling putUsingPUT");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/filestatus/{id}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<Filestatus> returnType = new ParameterizedTypeReference<Filestatus>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
