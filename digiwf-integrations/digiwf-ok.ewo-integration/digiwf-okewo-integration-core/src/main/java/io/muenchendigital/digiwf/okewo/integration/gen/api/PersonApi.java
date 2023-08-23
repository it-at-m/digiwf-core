package io.muenchendigital.digiwf.okewo.integration.gen.api;

import io.muenchendigital.digiwf.okewo.integration.gen.ApiClient;

import io.muenchendigital.digiwf.okewo.integration.gen.model.Person;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonAnfrage;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonAntwort;

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

@Component("io.muenchendigital.digiwf.okewo.integration.gen.api.PersonApi")
public class PersonApi {
    private ApiClient apiClient;

    public PersonApi() {
        this(new ApiClient());
    }

    @Autowired
    public PersonApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Lesen von Daten auf Basis des Ordnungsmerkmales
     * 
     * <p><b>200</b> - Output type
     * @param om Ordnungsmerkmal der gesuchten Person (required)
     * @param benutzerId BenutzerId des Anfragenden Service (required)
     * @return Person
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Person deMuenchenEaiEwoRouteROUTEPROCESSGETPERSON(String om, String benutzerId) throws RestClientException {
        return deMuenchenEaiEwoRouteROUTEPROCESSGETPERSONWithHttpInfo(om, benutzerId).getBody();
    }

    /**
     * Lesen von Daten auf Basis des Ordnungsmerkmales
     * 
     * <p><b>200</b> - Output type
     * @param om Ordnungsmerkmal der gesuchten Person (required)
     * @param benutzerId BenutzerId des Anfragenden Service (required)
     * @return ResponseEntity&lt;Person&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Person> deMuenchenEaiEwoRouteROUTEPROCESSGETPERSONWithHttpInfo(String om, String benutzerId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'om' is set
        if (om == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'om' when calling deMuenchenEaiEwoRouteROUTEPROCESSGETPERSON");
        }
        // verify the required parameter 'benutzerId' is set
        if (benutzerId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'benutzerId' when calling deMuenchenEaiEwoRouteROUTEPROCESSGETPERSON");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("om", om);
        String path = UriComponentsBuilder.fromPath("person/{om}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "benutzerId", benutzerId));

        final String[] accepts = { 
            "application/json;charset&#x3D;UTF-8"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Person> returnType = new ParameterizedTypeReference<Person>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Suchparameter für zu den gesuchten Personen
     * 
     * <p><b>200</b> - Output type
     * @param body  (required)
     * @return SuchePersonAntwort
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SuchePersonAntwort deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSON(SuchePersonAnfrage body) throws RestClientException {
        return deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSONWithHttpInfo(body).getBody();
    }

    /**
     * Suchparameter für zu den gesuchten Personen
     * 
     * <p><b>200</b> - Output type
     * @param body  (required)
     * @return ResponseEntity&lt;SuchePersonAntwort&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SuchePersonAntwort> deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSONWithHttpInfo(SuchePersonAnfrage body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSON");
        }
        String path = UriComponentsBuilder.fromPath("person/search").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json;charset&#x3D;UTF-8"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<SuchePersonAntwort> returnType = new ParameterizedTypeReference<SuchePersonAntwort>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
