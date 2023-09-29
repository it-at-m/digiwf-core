package de.muenchen.oss.digiwf.address.integration.gen.api;

import de.muenchen.oss.digiwf.address.integration.gen.ApiClient;

import de.muenchen.oss.digiwf.address.integration.gen.model.BundesweiteAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.gen.model.ExterneAdresseResponse;

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


@Component("de.muenchen.oss.digiwf.address.integration.gen.api.AdressenBundesweitApi")
public class AdressenBundesweitApi {
    private ApiClient apiClient;

    public AdressenBundesweitApi() {
        this(new ApiClient());
    }

    @Autowired
    public AdressenBundesweitApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Sucht bundesweit Adressen für angegebene Query
     * 
     * <p><b>400</b> - Request-Objekt enthält fehlerhafte Werte
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Adresssuche wurde erfolgreich durchgeführt
     * @param query Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer, ggf. Buchstabe und optional Plz/Ort  &lt;br /&gt; &lt;i&gt;Beispiele&lt;/i&gt;: &#x27;Wippenhauser Straße 54a&#x27;, &#x27;Wippenhauser Straße 54a, 85354 Freising&#x27; (optional)
     * @param plz Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;85354&#x27; (optional)
     * @param ortsname Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Freising&#x27; (optional)
     * @param gemeindeschluessel Optional: Verwaltungszugehörigkeit - Land + Regierung + Kreis + Gemeinde  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;09178124&#x27; (optional)
     * @param hausnummerfilter Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#x27;90&#x27;. Nur Adressen mit Hausnummer &#x27;90&#x27; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#x27;90&#x27; und &#x27;100&#x27;. Nur Adressen mit einer Hausnummer von &#x27;90&#x27; - &#x27;100&#x27; werden ausgegeben (optional)
     * @param buchstabefilter Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#x27;a&#x27;. Nur Adressen mit Hausnummerzusatz &#x27;a&#x27; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#x27;a&#x27; und &#x27;c&#x27;. Nur Adressen mit einem Hausnummerzusatz von &#x27;a&#x27;, &#x27;b&#x27; oder &#x27;c&#x27; werden ausgegeben (optional)
     * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;HAUSNUMMER&#x27;, &#x27;BUCHSTABE&#x27;, &#x27;STRASSE&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return BundesweiteAdresseResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public BundesweiteAdresseResponse searchAdressen(String query, String plz, String ortsname, String gemeindeschluessel, List<Long> hausnummerfilter, List<String> buchstabefilter, String sort, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        return searchAdressenWithHttpInfo(query, plz, ortsname, gemeindeschluessel, hausnummerfilter, buchstabefilter, sort, sortdir, page, pagesize).getBody();
    }

    /**
     * Sucht bundesweit Adressen für angegebene Query
     * 
     * <p><b>400</b> - Request-Objekt enthält fehlerhafte Werte
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Adresssuche wurde erfolgreich durchgeführt
     * @param query Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer, ggf. Buchstabe und optional Plz/Ort  &lt;br /&gt; &lt;i&gt;Beispiele&lt;/i&gt;: &#x27;Wippenhauser Straße 54a&#x27;, &#x27;Wippenhauser Straße 54a, 85354 Freising&#x27; (optional)
     * @param plz Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;85354&#x27; (optional)
     * @param ortsname Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Freising&#x27; (optional)
     * @param gemeindeschluessel Optional: Verwaltungszugehörigkeit - Land + Regierung + Kreis + Gemeinde  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;09178124&#x27; (optional)
     * @param hausnummerfilter Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#x27;90&#x27;. Nur Adressen mit Hausnummer &#x27;90&#x27; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#x27;90&#x27; und &#x27;100&#x27;. Nur Adressen mit einer Hausnummer von &#x27;90&#x27; - &#x27;100&#x27; werden ausgegeben (optional)
     * @param buchstabefilter Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#x27;a&#x27;. Nur Adressen mit Hausnummerzusatz &#x27;a&#x27; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#x27;a&#x27; und &#x27;c&#x27;. Nur Adressen mit einem Hausnummerzusatz von &#x27;a&#x27;, &#x27;b&#x27; oder &#x27;c&#x27; werden ausgegeben (optional)
     * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;HAUSNUMMER&#x27;, &#x27;BUCHSTABE&#x27;, &#x27;STRASSE&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return ResponseEntity&lt;BundesweiteAdresseResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<BundesweiteAdresseResponse> searchAdressenWithHttpInfo(String query, String plz, String ortsname, String gemeindeschluessel, List<Long> hausnummerfilter, List<String> buchstabefilter, String sort, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/v2/adresse_bundesweit/search").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "plz", plz));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "ortsname", ortsname));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "gemeindeschluessel", gemeindeschluessel));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "hausnummerfilter", hausnummerfilter));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "buchstabefilter", buchstabefilter));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
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

        ParameterizedTypeReference<BundesweiteAdresseResponse> returnType = new ParameterizedTypeReference<BundesweiteAdresseResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Sucht bundesweit Adressen für angegebene Query
     * 
     * <p><b>400</b> - Request-Objekt enthält fehlerhafte Werte
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Adresssuche wurde erfolgreich durchgeführt
     * @param query Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer und ggf. Buchstabe  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße 4a&#x27; (optional)
     * @param plz Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;85354&#x27; (optional)
     * @param ortsname Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Freising&#x27; (optional)
     * @param gemeindeschluessel Optional: Verwaltungszugehörigkeit - Land + Regierung + Kreis + Gemeinde  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;09178124&#x27; (optional)
     * @param hausnummerfilter Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer (optional)
     * @param buchstabefilter Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer (optional)
     * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;HAUSNUMMER&#x27;, &#x27;BUCHSTABE&#x27;, &#x27;STRASSE&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return ExterneAdresseResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ExterneAdresseResponse searchAdressen2(String query, String plz, String ortsname, String gemeindeschluessel, List<Long> hausnummerfilter, List<String> buchstabefilter, String sort, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        return searchAdressen2WithHttpInfo(query, plz, ortsname, gemeindeschluessel, hausnummerfilter, buchstabefilter, sort, sortdir, page, pagesize).getBody();
    }

    /**
     * Sucht bundesweit Adressen für angegebene Query
     * 
     * <p><b>400</b> - Request-Objekt enthält fehlerhafte Werte
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Adresssuche wurde erfolgreich durchgeführt
     * @param query Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer und ggf. Buchstabe  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße 4a&#x27; (optional)
     * @param plz Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;85354&#x27; (optional)
     * @param ortsname Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Freising&#x27; (optional)
     * @param gemeindeschluessel Optional: Verwaltungszugehörigkeit - Land + Regierung + Kreis + Gemeinde  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;09178124&#x27; (optional)
     * @param hausnummerfilter Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer (optional)
     * @param buchstabefilter Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer (optional)
     * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;HAUSNUMMER&#x27;, &#x27;BUCHSTABE&#x27;, &#x27;STRASSE&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return ResponseEntity&lt;ExterneAdresseResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ExterneAdresseResponse> searchAdressen2WithHttpInfo(String query, String plz, String ortsname, String gemeindeschluessel, List<Long> hausnummerfilter, List<String> buchstabefilter, String sort, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/externe_adresse/search").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "plz", plz));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "ortsname", ortsname));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "gemeindeschluessel", gemeindeschluessel));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "hausnummerfilter", hausnummerfilter));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "buchstabefilter", buchstabefilter));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "sort", sort));
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

        ParameterizedTypeReference<ExterneAdresseResponse> returnType = new ParameterizedTypeReference<ExterneAdresseResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
