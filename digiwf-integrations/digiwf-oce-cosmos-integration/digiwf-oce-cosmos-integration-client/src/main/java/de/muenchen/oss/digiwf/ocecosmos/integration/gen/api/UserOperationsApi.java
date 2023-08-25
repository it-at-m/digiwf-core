package de.muenchen.oss.digiwf.ocecosmos.integration.gen.api;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.ApiClient;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.model.User;

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

@Component("de.muenchen.oss.digiwf.ocecosmos.integration.gen.api.UserOperationsApi")
public class UserOperationsApi {
    private ApiClient apiClient;

    public UserOperationsApi() {
        this(new ApiClient());
    }

    @Autowired
    public UserOperationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve the current user
     * Get the current user representation out of the bearer token. Requires permissions &#x27;PERM_IADMINSERVICE_GETUSERDATABYNAME&#x27;.
     * <p><b>200</b> - The current user has been fetched and returned in the response body.
     * @return User
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public User getUsingGET() throws RestClientException {
        return getUsingGETWithHttpInfo().getBody();
    }

    /**
     * Retrieve the current user
     * Get the current user representation out of the bearer token. Requires permissions &#x27;PERM_IADMINSERVICE_GETUSERDATABYNAME&#x27;.
     * <p><b>200</b> - The current user has been fetched and returned in the response body.
     * @return ResponseEntity&lt;User&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<User> getUsingGETWithHttpInfo() throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/webapi/v3/currentUser").build().toUriString();
        
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

        ParameterizedTypeReference<User> returnType = new ParameterizedTypeReference<User>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
