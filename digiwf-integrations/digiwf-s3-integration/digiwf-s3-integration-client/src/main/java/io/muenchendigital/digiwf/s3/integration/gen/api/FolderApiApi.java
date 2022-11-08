package io.muenchendigital.digiwf.s3.integration.gen.api;

import io.muenchendigital.digiwf.s3.integration.gen.ApiClient;

import io.muenchendigital.digiwf.s3.integration.gen.model.FilesInFolderDto;

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

@Component("io.muenchendigital.digiwf.s3.integration.gen.api.FolderApiApi")
public class FolderApiApi {
    private ApiClient apiClient;

    public FolderApiApi() {
        this(new ApiClient());
    }

    @Autowired
    public FolderApiApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * 
     * Deletes the folder specified in the parameter together with the corresponding database entry
     * <p><b>200</b> - OK
     * @param pathToFolder  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void delete(String pathToFolder) throws RestClientException {
        deleteWithHttpInfo(pathToFolder);
    }

    /**
     * 
     * Deletes the folder specified in the parameter together with the corresponding database entry
     * <p><b>200</b> - OK
     * @param pathToFolder  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> deleteWithHttpInfo(String pathToFolder) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'pathToFolder' is set
        if (pathToFolder == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'pathToFolder' when calling delete");
        }
        String path = UriComponentsBuilder.fromPath("/folder").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pathToFolder", pathToFolder));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * Returns all file paths for the folder specified in the parameter
     * <p><b>200</b> - OK
     * @param pathToFolder  (required)
     * @return FilesInFolderDto
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public FilesInFolderDto getAllFilesInFolderRecursively(String pathToFolder) throws RestClientException {
        return getAllFilesInFolderRecursivelyWithHttpInfo(pathToFolder).getBody();
    }

    /**
     * 
     * Returns all file paths for the folder specified in the parameter
     * <p><b>200</b> - OK
     * @param pathToFolder  (required)
     * @return ResponseEntity&lt;FilesInFolderDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<FilesInFolderDto> getAllFilesInFolderRecursivelyWithHttpInfo(String pathToFolder) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'pathToFolder' is set
        if (pathToFolder == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'pathToFolder' when calling getAllFilesInFolderRecursively");
        }
        String path = UriComponentsBuilder.fromPath("/folder").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pathToFolder", pathToFolder));

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<FilesInFolderDto> returnType = new ParameterizedTypeReference<FilesInFolderDto>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
