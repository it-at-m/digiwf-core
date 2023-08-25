package de.muenchen.oss.digiwf.ocecosmos.integration.gen.api;

import de.muenchen.oss.digiwf.ocecosmos.integration.gen.ApiClient;


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

@Component("de.muenchen.oss.digiwf.ocecosmos.integration.gen.api.ConsoleOperationsApi")
public class ConsoleOperationsApi {
    private ApiClient apiClient;

    public ConsoleOperationsApi() {
        this(new ApiClient());
    }

    @Autowired
    public ConsoleOperationsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Execute one given COSMOS console command
     * Execute the specified console command with optional parameter(s). The command standard output is returned in the response body as an array of strings. \&quot;SHUT\&quot;, \&quot;EXIT\&quot; and \&quot;RESTART\&quot; commands are forbidden. Requires COSMOS permission &#x27;PERM_IEPOSSERVER_RUNCONSOLECOMMAND&#x27;.
     * <p><b>202</b> - The command has been executed. The command output is returned in the response body as a string array.
     * <p><b>404</b> - Specified command does not exist or is not permitted.
     * @param consoleCommandName COSMOS command name (case insensitive) (required)
     * @param body command parameters [&quot;HotFolder&quot;,&quot;Q1&quot;], as an ordered array of strings (optional)
     * @return List&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<String> postUsingPOST(String consoleCommandName, List<String> body) throws RestClientException {
        return postUsingPOSTWithHttpInfo(consoleCommandName, body).getBody();
    }

    /**
     * Execute one given COSMOS console command
     * Execute the specified console command with optional parameter(s). The command standard output is returned in the response body as an array of strings. \&quot;SHUT\&quot;, \&quot;EXIT\&quot; and \&quot;RESTART\&quot; commands are forbidden. Requires COSMOS permission &#x27;PERM_IEPOSSERVER_RUNCONSOLECOMMAND&#x27;.
     * <p><b>202</b> - The command has been executed. The command output is returned in the response body as a string array.
     * <p><b>404</b> - Specified command does not exist or is not permitted.
     * @param consoleCommandName COSMOS command name (case insensitive) (required)
     * @param body command parameters [&quot;HotFolder&quot;,&quot;Q1&quot;], as an ordered array of strings (optional)
     * @return ResponseEntity&lt;List&lt;String&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<String>> postUsingPOSTWithHttpInfo(String consoleCommandName, List<String> body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'consoleCommandName' is set
        if (consoleCommandName == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'consoleCommandName' when calling postUsingPOST");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("consoleCommandName", consoleCommandName);
        String path = UriComponentsBuilder.fromPath("/webapi/v3/consolecommands/execute/{consoleCommandName}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<List<String>> returnType = new ParameterizedTypeReference<List<String>>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
