package io.muenchendigital.digiwf.okverkehr.integration.gen.api;

import io.muenchendigital.digiwf.okverkehr.integration.gen.ApiClient;

import io.muenchendigital.digiwf.okverkehr.integration.gen.model.DefaultErrorProjection;
import io.muenchendigital.digiwf.okverkehr.integration.gen.model.HalterPersonAnfrage;
import io.muenchendigital.digiwf.okverkehr.integration.gen.model.HalterPersonAntwort;

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

@Component("io.muenchendigital.digiwf.okverkehr.integration.gen.api.BusinessActionskfzhalterpersonenApi")
public class BusinessActionskfzhalterpersonenApi {
    private ApiClient apiClient;

    public BusinessActionskfzhalterpersonenApi() {
        this(new ApiClient());
    }

    @Autowired
    public BusinessActionskfzhalterpersonenApi(ApiClient apiClient) {
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
     * 
     * <p><b>200</b> - Output type
     * <p><b>404</b> - Keine Halter-/Fahrzeugdaten gefunden
     * <p><b>401</b> - Es liegt keine Authentifizierung vor
     * <p><b>403</b> - Die notwendigen Rechte fehlen
     * <p><b>500</b> - Interner Verarbeitungsfehler
     * <p><b>502</b> - Fehlerhafte Antwort vom Fachverfahren
     * <p><b>504</b> - Timeout bei Bearbeitung durch Fachverfahren oder keine Verbindung
     * @param body  (required)
     * @return HalterPersonAntwort
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public HalterPersonAntwort route1(HalterPersonAnfrage body) throws RestClientException {
        return route1WithHttpInfo(body).getBody();
    }

    /**
     * 
     * 
     * <p><b>200</b> - Output type
     * <p><b>404</b> - Keine Halter-/Fahrzeugdaten gefunden
     * <p><b>401</b> - Es liegt keine Authentifizierung vor
     * <p><b>403</b> - Die notwendigen Rechte fehlen
     * <p><b>500</b> - Interner Verarbeitungsfehler
     * <p><b>502</b> - Fehlerhafte Antwort vom Fachverfahren
     * <p><b>504</b> - Timeout bei Bearbeitung durch Fachverfahren oder keine Verbindung
     * @param body  (required)
     * @return ResponseEntity&lt;HalterPersonAntwort&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<HalterPersonAntwort> route1WithHttpInfo(HalterPersonAnfrage body) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling route1");
        }
        String path = UriComponentsBuilder.fromPath("/businessActions/kfzhalterpersonen").build().toUriString();
        
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

        ParameterizedTypeReference<HalterPersonAntwort> returnType = new ParameterizedTypeReference<HalterPersonAntwort>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
