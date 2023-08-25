package de.muenchen.oss.digiwf.ocecosmos.integration.gen.api;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.ApiClient;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.BackupRestoreDTO;
import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.SimpleResponse;

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

@Component("de.muenchen.oss.digiwf.ocecosmos.integration.gen.api.BackupRestoreOperationsApi")
public class BackupRestoreOperationsApi {
    private ApiClient apiClient;

    public BackupRestoreOperationsApi() {
        this(new ApiClient());
    }

    @Autowired
    public BackupRestoreOperationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Executes a Backup or Restore
     * Executes a Backup or Restore. Requires permissions &#x27;PERM_IEPOSSERVER_SAVEDATABASE&#x27;, &#x27;PERM_IEPOSSERVER_RESTOREDATABASE&#x27;.
     * <p><b>200</b> - OK
     * <p><b>201</b> - OK
     * @param body Representation of the backup properties and which backuptypes should be created/restored (required)
     * @param folder The folder for the operation (required)
     * @param command backup or restore (required)
     * @return SimpleResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SimpleResponse doBackupRestoreUsingPOST(BackupRestoreDTO body, String folder, String command) throws RestClientException {
        return doBackupRestoreUsingPOSTWithHttpInfo(body, folder, command).getBody();
    }

    /**
     * Executes a Backup or Restore
     * Executes a Backup or Restore. Requires permissions &#x27;PERM_IEPOSSERVER_SAVEDATABASE&#x27;, &#x27;PERM_IEPOSSERVER_RESTOREDATABASE&#x27;.
     * <p><b>200</b> - OK
     * <p><b>201</b> - OK
     * @param body Representation of the backup properties and which backuptypes should be created/restored (required)
     * @param folder The folder for the operation (required)
     * @param command backup or restore (required)
     * @return ResponseEntity&lt;SimpleResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SimpleResponse> doBackupRestoreUsingPOSTWithHttpInfo(BackupRestoreDTO body, String folder, String command) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling doBackupRestoreUsingPOST");
        }
        // verify the required parameter 'folder' is set
        if (folder == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'folder' when calling doBackupRestoreUsingPOST");
        }
        // verify the required parameter 'command' is set
        if (command == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'command' when calling doBackupRestoreUsingPOST");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("command", command);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/backuprestore/{command}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "folder", folder));

        final String[] accepts = { 
            "application/json", "application/xml"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<SimpleResponse> returnType = new ParameterizedTypeReference<SimpleResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Gets the Backup properties
     * Gets the Backup properties and all backup types of a given folder. Requires permissions &#x27;PERM_IEPOSSERVER_RESTOREDATABASE&#x27;, &#x27;PERM_IEPOSSERVER_EXISTSSERVERFILE&#x27;.
     * <p><b>200</b> - OK
     * @param folder The folder to the backup files, file-separator &#x3D; &#x27;/&#x27; (required)
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object getBackupMetadataUsingGET(String folder) throws RestClientException {
        return getBackupMetadataUsingGETWithHttpInfo(folder).getBody();
    }

    /**
     * Gets the Backup properties
     * Gets the Backup properties and all backup types of a given folder. Requires permissions &#x27;PERM_IEPOSSERVER_RESTOREDATABASE&#x27;, &#x27;PERM_IEPOSSERVER_EXISTSSERVERFILE&#x27;.
     * <p><b>200</b> - OK
     * @param folder The folder to the backup files, file-separator &#x3D; &#x27;/&#x27; (required)
     * @return ResponseEntity&lt;Object&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Object> getBackupMetadataUsingGETWithHttpInfo(String folder) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'folder' is set
        if (folder == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'folder' when calling getBackupMetadataUsingGET");
        }
        String path = UriComponentsBuilder.fromPath("/webapi/v3/backuprestore/metadata").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "folder", folder));

        final String[] accepts = { 
            "application/json", "application/xml"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
