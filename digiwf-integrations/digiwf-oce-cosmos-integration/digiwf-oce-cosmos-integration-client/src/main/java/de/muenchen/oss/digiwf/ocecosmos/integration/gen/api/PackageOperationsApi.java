package de.muenchen.oss.digiwf.ocecosmos.integration.gen.api;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.ApiClient;

import java.io.File;
import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.PackageImportFile;

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

@Component("de.muenchen.oss.digiwf.ocecosmos.integration.gen.api.PackageOperationsApi")
public class PackageOperationsApi {
    private ApiClient apiClient;

    public PackageOperationsApi() {
        this(new ApiClient());
    }

    @Autowired
    public PackageOperationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve representations of all package files ready for import
     * Returns the list of package files (*.cel and *.ppk) ready for import on the sysHUB filesystem in the folder &#x27;impexp&#x27;. Requires permission &#x27;PERM_IADMINSERVICE_ADDOBJECTID&#x27;.
     * <p><b>200</b> - The list of packages ready for import has been returned in the response body.
     * @return List&lt;PackageImportFile&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<PackageImportFile> getUsingGET9() throws RestClientException {
        return getUsingGET9WithHttpInfo().getBody();
    }

    /**
     * Retrieve representations of all package files ready for import
     * Returns the list of package files (*.cel and *.ppk) ready for import on the sysHUB filesystem in the folder &#x27;impexp&#x27;. Requires permission &#x27;PERM_IADMINSERVICE_ADDOBJECTID&#x27;.
     * <p><b>200</b> - The list of packages ready for import has been returned in the response body.
     * @return ResponseEntity&lt;List&lt;PackageImportFile&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<PackageImportFile>> getUsingGET9WithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/webapi/v3/packages/files").build().toUriString();
        
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

        ParameterizedTypeReference<List<PackageImportFile>> returnType = new ParameterizedTypeReference<List<PackageImportFile>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Upload one package file to make it ready for import
     * Upload a package file (*.ppk or *.cel) with a multipart/form-data request to the sysHUB filesystem to make it available for import. Any existing package file of the same name present on sysHUB server impexp folder will be overwritten. Requires permission &#x27;PERM_IADMINSERVICE_ADDOBJECTID&#x27;.
     * <p><b>201</b> - The package has been uploaded successfully. The result of the operation has been sent in the response body as a package import file representation.
     * @param packageFile  (required)
     * @return PackageImportFile
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public PackageImportFile postUsingPOST6(java.nio.file.Path packageFile) throws RestClientException {
        return postUsingPOST6WithHttpInfo(packageFile).getBody();
    }

    /**
     * Upload one package file to make it ready for import
     * Upload a package file (*.ppk or *.cel) with a multipart/form-data request to the sysHUB filesystem to make it available for import. Any existing package file of the same name present on sysHUB server impexp folder will be overwritten. Requires permission &#x27;PERM_IADMINSERVICE_ADDOBJECTID&#x27;.
     * <p><b>201</b> - The package has been uploaded successfully. The result of the operation has been sent in the response body as a package import file representation.
     * @param packageFile  (required)
     * @return ResponseEntity&lt;PackageImportFile&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<PackageImportFile> postUsingPOST6WithHttpInfo(java.nio.file.Path packageFile) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'packageFile' is set
        if (packageFile == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'packageFile' when calling postUsingPOST6");
        }
        String path = UriComponentsBuilder.fromPath("/webapi/v3/packages/files").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (packageFile != null)
            formParams.add("packageFile", new FileSystemResource(packageFile));

        final String[] accepts = { 
            "*/*"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "multipart/form-data"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "oauth2schema" };

        ParameterizedTypeReference<PackageImportFile> returnType = new ParameterizedTypeReference<PackageImportFile>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
