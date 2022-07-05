package io.muenchendigital.digiwf.s3.integration.gen.api;

import io.muenchendigital.digiwf.s3.integration.gen.ApiClient;

import java.util.Date;
import io.muenchendigital.digiwf.s3.integration.gen.model.FileDataDto;
import io.muenchendigital.digiwf.s3.integration.gen.model.PresignedUrlDto;

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

@Component("io.muenchendigital.digiwf.s3.integration.gen.api.FileApiApi")
public class FileApiApi {
    private ApiClient apiClient;

    public FileApiApi() {
        this(new ApiClient());
    }

    @Autowired
    public FileApiApi(ApiClient apiClient) {
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
     * Creates a presigned URL to delete the file specified in the parameter from the S3 storage
     * <p><b>200</b> - OK
     * @param pathToFile  (required)
     * @param expiresInMinutes  (required)
     * @return PresignedUrlDto
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PresignedUrlDto delete1(String pathToFile, Integer expiresInMinutes) throws RestClientException {
        return delete1WithHttpInfo(pathToFile, expiresInMinutes).getBody();
    }

    /**
     * 
     * Creates a presigned URL to delete the file specified in the parameter from the S3 storage
     * <p><b>200</b> - OK
     * @param pathToFile  (required)
     * @param expiresInMinutes  (required)
     * @return ResponseEntity&lt;PresignedUrlDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PresignedUrlDto> delete1WithHttpInfo(String pathToFile, Integer expiresInMinutes) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'pathToFile' is set
        if (pathToFile == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'pathToFile' when calling delete1");
        }
        // verify the required parameter 'expiresInMinutes' is set
        if (expiresInMinutes == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'expiresInMinutes' when calling delete1");
        }
        String path = UriComponentsBuilder.fromPath("/file").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pathToFile", pathToFile));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expiresInMinutes", expiresInMinutes));

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PresignedUrlDto> returnType = new ParameterizedTypeReference<PresignedUrlDto>() {};
        return apiClient.invokeAPI(path, HttpMethod.DELETE, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * Creates a presigned URL to fetch the file specified in the parameter from the S3 storage
     * <p><b>200</b> - OK
     * @param pathToFile  (required)
     * @param expiresInMinutes  (required)
     * @return PresignedUrlDto
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PresignedUrlDto get(String pathToFile, Integer expiresInMinutes) throws RestClientException {
        return getWithHttpInfo(pathToFile, expiresInMinutes).getBody();
    }

    /**
     * 
     * Creates a presigned URL to fetch the file specified in the parameter from the S3 storage
     * <p><b>200</b> - OK
     * @param pathToFile  (required)
     * @param expiresInMinutes  (required)
     * @return ResponseEntity&lt;PresignedUrlDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PresignedUrlDto> getWithHttpInfo(String pathToFile, Integer expiresInMinutes) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'pathToFile' is set
        if (pathToFile == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'pathToFile' when calling get");
        }
        // verify the required parameter 'expiresInMinutes' is set
        if (expiresInMinutes == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'expiresInMinutes' when calling get");
        }
        String path = UriComponentsBuilder.fromPath("/file").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pathToFile", pathToFile));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expiresInMinutes", expiresInMinutes));

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PresignedUrlDto> returnType = new ParameterizedTypeReference<PresignedUrlDto>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * Creates a presigned URL to store the file specified in the parameter within the S3 storage
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return PresignedUrlDto
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PresignedUrlDto save(FileDataDto body) throws RestClientException {
        return saveWithHttpInfo(body).getBody();
    }

    /**
     * 
     * Creates a presigned URL to store the file specified in the parameter within the S3 storage
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;PresignedUrlDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PresignedUrlDto> saveWithHttpInfo(FileDataDto body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling save");
        }
        String path = UriComponentsBuilder.fromPath("/file").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PresignedUrlDto> returnType = new ParameterizedTypeReference<PresignedUrlDto>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * Creates a presigned URL to overwrite the file specified in the parameter within the S3 storage
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return PresignedUrlDto
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PresignedUrlDto update(FileDataDto body) throws RestClientException {
        return updateWithHttpInfo(body).getBody();
    }

    /**
     * 
     * Creates a presigned URL to overwrite the file specified in the parameter within the S3 storage
     * <p><b>200</b> - OK
     * @param body  (required)
     * @return ResponseEntity&lt;PresignedUrlDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PresignedUrlDto> updateWithHttpInfo(FileDataDto body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling update");
        }
        String path = UriComponentsBuilder.fromPath("/file").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<PresignedUrlDto> returnType = new ParameterizedTypeReference<PresignedUrlDto>() {};
        return apiClient.invokeAPI(path, HttpMethod.PUT, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * 
     * Updates the end of life attribute in the corresponding database entry for the file specified in the parameter
     * <p><b>200</b> - OK
     * @param pathToFile  (required)
     * @param endOfLife  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void updateEndOfLife(String pathToFile, java.time.LocalDate endOfLife) throws RestClientException {
        updateEndOfLifeWithHttpInfo(pathToFile, endOfLife);
    }

    /**
     * 
     * Updates the end of life attribute in the corresponding database entry for the file specified in the parameter
     * <p><b>200</b> - OK
     * @param pathToFile  (required)
     * @param endOfLife  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> updateEndOfLifeWithHttpInfo(String pathToFile, java.time.LocalDate endOfLife) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'pathToFile' is set
        if (pathToFile == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'pathToFile' when calling updateEndOfLife");
        }
        // verify the required parameter 'endOfLife' is set
        if (endOfLife == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'endOfLife' when calling updateEndOfLife");
        }
        String path = UriComponentsBuilder.fromPath("/file").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pathToFile", pathToFile));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "endOfLife", endOfLife));

        final String[] accepts = {  };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI(path, HttpMethod.PATCH, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
