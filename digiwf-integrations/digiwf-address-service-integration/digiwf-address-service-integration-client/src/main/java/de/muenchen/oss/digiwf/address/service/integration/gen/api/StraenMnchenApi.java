package de.muenchen.oss.digiwf.address.service.integration.gen.api;

import de.muenchen.oss.digiwf.address.service.integration.gen.ApiClient;

import de.muenchen.oss.digiwf.address.service.integration.gen.model.Strasse;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.StrasseResponse;

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

@Component("de.muenchen.oss.digiwf.address.service.integration.gen.api.StraenMnchenApi")
public class StraenMnchenApi {
    private ApiClient apiClient;

    public StraenMnchenApi() {
        this(new ApiClient());
    }

    @Autowired
    public StraenMnchenApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Liefert die Straße zu der Straßen-Id.
     * 
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Keine Straße zu der Straßennummer gefunden
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Abfrage erfolgreich durchgeführt
     * @param strasseId Straßennummer/Straßenschlüssel  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (required)
     * @return Strasse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Strasse findStrasseByNummer(Long strasseId) throws RestClientException {
        return findStrasseByNummerWithHttpInfo(strasseId).getBody();
    }

    /**
     * Liefert die Straße zu der Straßen-Id.
     * 
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Keine Straße zu der Straßennummer gefunden
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Abfrage erfolgreich durchgeführt
     * @param strasseId Straßennummer/Straßenschlüssel  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (required)
     * @return ResponseEntity&lt;Strasse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Strasse> findStrasseByNummerWithHttpInfo(Long strasseId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'strasseId' is set
        if (strasseId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'strasseId' when calling findStrasseByNummer");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("strasseId", strasseId);
        String path = UriComponentsBuilder.fromPath("/v2/strasse/{strasseId}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Strasse> returnType = new ParameterizedTypeReference<Strasse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Liefert die Straße zu der Straßennummer.
     * 
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Keine Straße zu der Straßennummer gefunden
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Abfrage erfolgreich durchgeführt
     * @param strassennummer Straßennummer  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (required)
     * @return Strasse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Strasse findStrasseByNummer1(Long strassennummer) throws RestClientException {
        return findStrasseByNummer1WithHttpInfo(strassennummer).getBody();
    }

    /**
     * Liefert die Straße zu der Straßennummer.
     * 
     * <p><b>400</b> - Bad Request
     * <p><b>404</b> - Keine Straße zu der Straßennummer gefunden
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Abfrage erfolgreich durchgeführt
     * @param strassennummer Straßennummer  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (required)
     * @return ResponseEntity&lt;Strasse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Strasse> findStrasseByNummer1WithHttpInfo(Long strassennummer) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'strassennummer' is set
        if (strassennummer == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'strassennummer' when calling findStrasseByNummer1");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("strassennummer", strassennummer);
        String path = UriComponentsBuilder.fromPath("/strasse/{strassennummer}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Strasse> returnType = new ParameterizedTypeReference<Strasse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Liefert alle Straßen, die mit den Suchparametern übereinstimmen.
     * 
     * <p><b>400</b> - Validation Fehler
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Abfrage erfolgreich durchgeführt
     * @param stadtbezirksnamen Stadtbezirksnamen &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Maxvorstadt&#x27; (optional)
     * @param stadtbezirksnummern Stadtbezirksnummern  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3 (optional)
     * @param strassenname Straßenname  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return StrasseResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public StrasseResponse listStrassen(List<String> stadtbezirksnamen, List<Long> stadtbezirksnummern, String strassenname, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        return listStrassenWithHttpInfo(stadtbezirksnamen, stadtbezirksnummern, strassenname, sortdir, page, pagesize).getBody();
    }

    /**
     * Liefert alle Straßen, die mit den Suchparametern übereinstimmen.
     * 
     * <p><b>400</b> - Validation Fehler
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Abfrage erfolgreich durchgeführt
     * @param stadtbezirksnamen Stadtbezirksnamen &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Maxvorstadt&#x27; (optional)
     * @param stadtbezirksnummern Stadtbezirksnummern  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3 (optional)
     * @param strassenname Straßenname  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return ResponseEntity&lt;StrasseResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StrasseResponse> listStrassenWithHttpInfo(List<String> stadtbezirksnamen, List<Long> stadtbezirksnummern, String strassenname, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/v2/strasse/search").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "stadtbezirksnamen", stadtbezirksnamen));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "stadtbezirksnummern", stadtbezirksnummern));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "strassenname", strassenname));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortdir", sortdir));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pagesize", pagesize));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<StrasseResponse> returnType = new ParameterizedTypeReference<StrasseResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Liefert alle Straßen, die mit den Suchparametern übereinstimmen.
     * 
     * <p><b>400</b> - Validation Fehler
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Abfrage erfolgreich durchgeführt
     * @param stadtbezirksnamen Stadtbezirksnamen &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Maxvorstadt&#x27; (optional)
     * @param stadtbezirksnummern Stadtbezirksnummern  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3 (optional)
     * @param strassenname Straßenname  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return StrasseResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public StrasseResponse listStrassen1(List<String> stadtbezirksnamen, List<Long> stadtbezirksnummern, String strassenname, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        return listStrassen1WithHttpInfo(stadtbezirksnamen, stadtbezirksnummern, strassenname, sortdir, page, pagesize).getBody();
    }

    /**
     * Liefert alle Straßen, die mit den Suchparametern übereinstimmen.
     * 
     * <p><b>400</b> - Validation Fehler
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Abfrage erfolgreich durchgeführt
     * @param stadtbezirksnamen Stadtbezirksnamen &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Maxvorstadt&#x27; (optional)
     * @param stadtbezirksnummern Stadtbezirksnummern  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3 (optional)
     * @param strassenname Straßenname  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return ResponseEntity&lt;StrasseResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StrasseResponse> listStrassen1WithHttpInfo(List<String> stadtbezirksnamen, List<Long> stadtbezirksnummern, String strassenname, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/strasse/search").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "stadtbezirksnamen", stadtbezirksnamen));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "stadtbezirksnummern", stadtbezirksnummern));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "strassenname", strassenname));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "sortdir", sortdir));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "page", page));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pagesize", pagesize));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<StrasseResponse> returnType = new ParameterizedTypeReference<StrasseResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
