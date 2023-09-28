package de.muenchen.oss.digiwf.address.service.integration.gen.api;

import de.muenchen.oss.digiwf.address.service.integration.gen.ApiClient;

import de.muenchen.oss.digiwf.address.service.integration.gen.model.Adresse;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.AdresseDistanz;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.AdresseResponse;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.AenderungResponse;
import java.util.Date;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.MuenchenAdresse;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.MuenchenAdresseResponse;

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


@Component("de.muenchen.oss.digiwf.address.service.integration.gen.api.AdressenMnchenApi")
public class AdressenMnchenApi {
    private ApiClient apiClient;

    public AdressenMnchenApi() {
        this(new ApiClient());
    }

    @Autowired
    public AdressenMnchenApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Prüft, ob die angegebene Adresse existiert. Die Pflichtwerte können entweder: 1. komplett mittels adresse-Parameter, Hausnummer und Buchstabe ohne Leerzeichen dazwischen, angegeben werden oder 2. als Einzelwerte über die Parameter strassenname/strassenschluessel, hausnummer, zusatz angegeben werden 
     * 
     * <p><b>400</b> - Validation Fehler
     * <p><b>200</b> - Adresse ist vorhanden
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>404</b> - Adresse konnte nicht gefunden werden
     * @param adresse Amtliche Schreibweise des Straßennamens mit Hausnummer und ggf. Zusatz (Pflichtfeld, wenn Straßenname/Straßenschluessel + Hausnummer nicht gesetzt sind). Optional Postleitzahl und Ort  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: entweder &#x27;Marsstraße 4a&#x27; oder &#x27;Marsstraße 4a, 80638 München&#x27; (optional)
     * @param strassenname Amtliche Schreibweise des Straßennamens &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße&#x27; (optional)
     * @param strasseId StrasseId &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (optional)
     * @param hausnummer Hausnummer der Straße &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;4&#x27; (optional)
     * @param zusatz Hausnummerzusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;a&#x27; (optional)
     * @param plz Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;80638&#x27; (optional)
     * @param ortsname Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;München&#x27; (optional)
     * @param gemeindeschluessel Optional: Gemeindeschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;09162000&#x27; (optional)
     * @return MuenchenAdresse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public MuenchenAdresse checkAdresse(String adresse, String strassenname, Integer strasseId, String hausnummer, String zusatz, String plz, String ortsname, String gemeindeschluessel) throws RestClientException {
        return checkAdresseWithHttpInfo(adresse, strassenname, strasseId, hausnummer, zusatz, plz, ortsname, gemeindeschluessel).getBody();
    }

    /**
     * Prüft, ob die angegebene Adresse existiert. Die Pflichtwerte können entweder: 1. komplett mittels adresse-Parameter, Hausnummer und Buchstabe ohne Leerzeichen dazwischen, angegeben werden oder 2. als Einzelwerte über die Parameter strassenname/strassenschluessel, hausnummer, zusatz angegeben werden 
     * 
     * <p><b>400</b> - Validation Fehler
     * <p><b>200</b> - Adresse ist vorhanden
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>404</b> - Adresse konnte nicht gefunden werden
     * @param adresse Amtliche Schreibweise des Straßennamens mit Hausnummer und ggf. Zusatz (Pflichtfeld, wenn Straßenname/Straßenschluessel + Hausnummer nicht gesetzt sind). Optional Postleitzahl und Ort  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: entweder &#x27;Marsstraße 4a&#x27; oder &#x27;Marsstraße 4a, 80638 München&#x27; (optional)
     * @param strassenname Amtliche Schreibweise des Straßennamens &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße&#x27; (optional)
     * @param strasseId StrasseId &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (optional)
     * @param hausnummer Hausnummer der Straße &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;4&#x27; (optional)
     * @param zusatz Hausnummerzusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;a&#x27; (optional)
     * @param plz Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;80638&#x27; (optional)
     * @param ortsname Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;München&#x27; (optional)
     * @param gemeindeschluessel Optional: Gemeindeschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;09162000&#x27; (optional)
     * @return ResponseEntity&lt;MuenchenAdresse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MuenchenAdresse> checkAdresseWithHttpInfo(String adresse, String strassenname, Integer strasseId, String hausnummer, String zusatz, String plz, String ortsname, String gemeindeschluessel) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/v2/adresse/check").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "adresse", adresse));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "strassenname", strassenname));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "strasseId", strasseId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "hausnummer", hausnummer));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "zusatz", zusatz));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "plz", plz));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "ortsname", ortsname));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "gemeindeschluessel", gemeindeschluessel));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<MuenchenAdresse> returnType = new ParameterizedTypeReference<MuenchenAdresse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Prüft, ob die angegebene Adresse existiert. Die Pflichtwerte können entweder: 1. komplett mittels adresse-Parameter, Hausnummer und Buchstabe ohne Leerzeichen dazwischen angegeben werden oder 2. als Einzelwerte über die Parameter strassenname/strassenschluessel, hausnummer, zusatz angegeben werden 
     * 
     * <p><b>400</b> - Validation Fehler
     * <p><b>200</b> - Adresse ist vorhanden
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>404</b> - Adresse konnte nicht gefunden werden
     * @param adresse Amtliche Schreibweise des Straßennamens mit Hausnummer und ggf. Zusatz (Pflichtfeld, wenn Straßenname/Straßenschluessel + Hausnummer nicht gesetzt sind). &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße 4a&#x27; (optional)
     * @param strassenname Amtliche Schreibweise des Straßennamens &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße&#x27; (optional)
     * @param strassenschluessel Straßenschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (optional)
     * @param hausnummer Hausnummer der Straße &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;4&#x27; (optional)
     * @param zusatz Hausnummerzusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;a&#x27; (optional)
     * @param plz Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;80638&#x27; (optional)
     * @param ortsname Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;München&#x27; (optional)
     * @param gemeindeschluessel Optional: Gemeindeschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;09162000&#x27; (optional)
     * @return Adresse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Adresse checkAdresse1(String adresse, String strassenname, Integer strassenschluessel, String hausnummer, String zusatz, String plz, String ortsname, String gemeindeschluessel) throws RestClientException {
        return checkAdresse1WithHttpInfo(adresse, strassenname, strassenschluessel, hausnummer, zusatz, plz, ortsname, gemeindeschluessel).getBody();
    }

    /**
     * Prüft, ob die angegebene Adresse existiert. Die Pflichtwerte können entweder: 1. komplett mittels adresse-Parameter, Hausnummer und Buchstabe ohne Leerzeichen dazwischen angegeben werden oder 2. als Einzelwerte über die Parameter strassenname/strassenschluessel, hausnummer, zusatz angegeben werden 
     * 
     * <p><b>400</b> - Validation Fehler
     * <p><b>200</b> - Adresse ist vorhanden
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>404</b> - Adresse konnte nicht gefunden werden
     * @param adresse Amtliche Schreibweise des Straßennamens mit Hausnummer und ggf. Zusatz (Pflichtfeld, wenn Straßenname/Straßenschluessel + Hausnummer nicht gesetzt sind). &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße 4a&#x27; (optional)
     * @param strassenname Amtliche Schreibweise des Straßennamens &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße&#x27; (optional)
     * @param strassenschluessel Straßenschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (optional)
     * @param hausnummer Hausnummer der Straße &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;4&#x27; (optional)
     * @param zusatz Hausnummerzusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;a&#x27; (optional)
     * @param plz Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;80638&#x27; (optional)
     * @param ortsname Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;München&#x27; (optional)
     * @param gemeindeschluessel Optional: Gemeindeschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;09162000&#x27; (optional)
     * @return ResponseEntity&lt;Adresse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Adresse> checkAdresse1WithHttpInfo(String adresse, String strassenname, Integer strassenschluessel, String hausnummer, String zusatz, String plz, String ortsname, String gemeindeschluessel) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/adresse/check").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "adresse", adresse));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "strassenname", strassenname));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "strassenschluessel", strassenschluessel));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "hausnummer", hausnummer));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "zusatz", zusatz));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "plz", plz));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "ortsname", ortsname));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "gemeindeschluessel", gemeindeschluessel));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Adresse> returnType = new ParameterizedTypeReference<Adresse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Liefert alle Adressen, die mit den angegebenen Gebietszuordnungen übereinstimmen.
     * 
     * <p><b>400</b> - Validation Fehler
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Abfrage erfolgreich durchgeführt
     * @param baublock Baublock &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;11.3.3.22&#x27; (optional)
     * @param erhaltungssatzung Erhaltungssatzung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;nein&#x27; (optional)
     * @param gemarkung Gemarkung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;8687&#x27; (optional)
     * @param kaminkehrerbezirk Kaminkehrerbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;26&#x27; (optional)
     * @param plz Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;80807&#x27; (optional)
     * @param mittelschule Mittelschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;2274&#x27; (optional)
     * @param grundschule Grundschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;2273&#x27; (optional)
     * @param polizeiinspektion Polizeiinspektion &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;47&#x27; (optional)
     * @param stimmbezirk Stimmbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1 (optional)
     * @param stimmkreis Stimmkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 104 (optional)
     * @param wahlbezirk Wahlbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1171 (optional)
     * @param wahlkreis Wahlkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 217 (optional)
     * @param stadtbezirk Stadtbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;11&#x27; (optional)
     * @param stadtbezirksteil Stadtbezirksteil &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;11.3&#x27; (optional)
     * @param stadtbezirksviertel Stadtbezirksviertel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;11.3.3&#x27; (optional)
     * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;HAUSNUMMER&#x27;, &#x27;BUCHSTABE&#x27;, &#x27;STRASSE&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return MuenchenAdresseResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public MuenchenAdresseResponse listAdressen(List<String> baublock, List<String> erhaltungssatzung, List<String> gemarkung, List<String> kaminkehrerbezirk, List<String> plz, List<String> mittelschule, List<String> grundschule, List<String> polizeiinspektion, List<Long> stimmbezirk, List<Long> stimmkreis, List<Long> wahlbezirk, List<Long> wahlkreis, List<String> stadtbezirk, List<String> stadtbezirksteil, List<String> stadtbezirksviertel, String sort, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        return listAdressenWithHttpInfo(baublock, erhaltungssatzung, gemarkung, kaminkehrerbezirk, plz, mittelschule, grundschule, polizeiinspektion, stimmbezirk, stimmkreis, wahlbezirk, wahlkreis, stadtbezirk, stadtbezirksteil, stadtbezirksviertel, sort, sortdir, page, pagesize).getBody();
    }

    /**
     * Liefert alle Adressen, die mit den angegebenen Gebietszuordnungen übereinstimmen.
     * 
     * <p><b>400</b> - Validation Fehler
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Abfrage erfolgreich durchgeführt
     * @param baublock Baublock &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;11.3.3.22&#x27; (optional)
     * @param erhaltungssatzung Erhaltungssatzung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;nein&#x27; (optional)
     * @param gemarkung Gemarkung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;8687&#x27; (optional)
     * @param kaminkehrerbezirk Kaminkehrerbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;26&#x27; (optional)
     * @param plz Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;80807&#x27; (optional)
     * @param mittelschule Mittelschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;2274&#x27; (optional)
     * @param grundschule Grundschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;2273&#x27; (optional)
     * @param polizeiinspektion Polizeiinspektion &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;47&#x27; (optional)
     * @param stimmbezirk Stimmbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1 (optional)
     * @param stimmkreis Stimmkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 104 (optional)
     * @param wahlbezirk Wahlbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1171 (optional)
     * @param wahlkreis Wahlkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 217 (optional)
     * @param stadtbezirk Stadtbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;11&#x27; (optional)
     * @param stadtbezirksteil Stadtbezirksteil &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;11.3&#x27; (optional)
     * @param stadtbezirksviertel Stadtbezirksviertel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;11.3.3&#x27; (optional)
     * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;HAUSNUMMER&#x27;, &#x27;BUCHSTABE&#x27;, &#x27;STRASSE&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return ResponseEntity&lt;MuenchenAdresseResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MuenchenAdresseResponse> listAdressenWithHttpInfo(List<String> baublock, List<String> erhaltungssatzung, List<String> gemarkung, List<String> kaminkehrerbezirk, List<String> plz, List<String> mittelschule, List<String> grundschule, List<String> polizeiinspektion, List<Long> stimmbezirk, List<Long> stimmkreis, List<Long> wahlbezirk, List<Long> wahlkreis, List<String> stadtbezirk, List<String> stadtbezirksteil, List<String> stadtbezirksviertel, String sort, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/v2/adresse/list").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "baublock", baublock));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "erhaltungssatzung", erhaltungssatzung));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "gemarkung", gemarkung));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "kaminkehrerbezirk", kaminkehrerbezirk));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "plz", plz));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "mittelschule", mittelschule));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "grundschule", grundschule));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "polizeiinspektion", polizeiinspektion));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "stimmbezirk", stimmbezirk));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "stimmkreis", stimmkreis));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "wahlbezirk", wahlbezirk));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "wahlkreis", wahlkreis));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "stadtbezirk", stadtbezirk));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "stadtbezirksteil", stadtbezirksteil));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "stadtbezirksviertel", stadtbezirksviertel));
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

        ParameterizedTypeReference<MuenchenAdresseResponse> returnType = new ParameterizedTypeReference<MuenchenAdresseResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Liefert alle Adressen, die mit den angegebenen Gebietszuordnungen übereinstimmen.
     * 
     * <p><b>400</b> - Validation Fehler
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Abfrage erfolgreich durchgeführt
     * @param baublock Baublock &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;11.3.3.22&#x27; (optional)
     * @param erhaltungssatzung Erhaltungssatzung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;nein&#x27; (optional)
     * @param gemarkung Gemarkung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;8687&#x27; (optional)
     * @param kaminkehrerbezirk Kaminkehrerbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;26&#x27; (optional)
     * @param plz Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;80807&#x27; (optional)
     * @param mittelschule Mittelschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;2274&#x27; (optional)
     * @param grundschule Grundschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;2273&#x27; (optional)
     * @param polizeiinspektion Polizeiinspektion &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;47&#x27; (optional)
     * @param stimmbezirk Stimmbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1 (optional)
     * @param stimmkreis Stimmkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 104 (optional)
     * @param wahlbezirk Wahlbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1171 (optional)
     * @param wahlkreis Wahlkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 217 (optional)
     * @param stadtbezirk Stadtbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;11&#x27; (optional)
     * @param stadtbezirksteil Stadtbezirksteil &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;11.3&#x27; (optional)
     * @param stadtbezirksviertel Stadtbezirksviertel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;11.3.3&#x27; (optional)
     * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;HAUSNUMMER&#x27;, &#x27;BUCHSTABE&#x27;, &#x27;STRASSE&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return AdresseResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public AdresseResponse listAdressen1(List<String> baublock, List<String> erhaltungssatzung, List<String> gemarkung, List<String> kaminkehrerbezirk, List<String> plz, List<String> mittelschule, List<String> grundschule, List<String> polizeiinspektion, List<Long> stimmbezirk, List<Long> stimmkreis, List<Long> wahlbezirk, List<Long> wahlkreis, List<String> stadtbezirk, List<String> stadtbezirksteil, List<String> stadtbezirksviertel, String sort, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        return listAdressen1WithHttpInfo(baublock, erhaltungssatzung, gemarkung, kaminkehrerbezirk, plz, mittelschule, grundschule, polizeiinspektion, stimmbezirk, stimmkreis, wahlbezirk, wahlkreis, stadtbezirk, stadtbezirksteil, stadtbezirksviertel, sort, sortdir, page, pagesize).getBody();
    }

    /**
     * Liefert alle Adressen, die mit den angegebenen Gebietszuordnungen übereinstimmen.
     * 
     * <p><b>400</b> - Validation Fehler
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Abfrage erfolgreich durchgeführt
     * @param baublock Baublock &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;11.3.3.22&#x27; (optional)
     * @param erhaltungssatzung Erhaltungssatzung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;nein&#x27; (optional)
     * @param gemarkung Gemarkung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;8687&#x27; (optional)
     * @param kaminkehrerbezirk Kaminkehrerbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;26&#x27; (optional)
     * @param plz Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;80807&#x27; (optional)
     * @param mittelschule Mittelschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;2274&#x27; (optional)
     * @param grundschule Grundschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;2273&#x27; (optional)
     * @param polizeiinspektion Polizeiinspektion &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;47&#x27; (optional)
     * @param stimmbezirk Stimmbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1 (optional)
     * @param stimmkreis Stimmkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 104 (optional)
     * @param wahlbezirk Wahlbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1171 (optional)
     * @param wahlkreis Wahlkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 217 (optional)
     * @param stadtbezirk Stadtbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;11&#x27; (optional)
     * @param stadtbezirksteil Stadtbezirksteil &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;11.3&#x27; (optional)
     * @param stadtbezirksviertel Stadtbezirksviertel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;11.3.3&#x27; (optional)
     * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;HAUSNUMMER&#x27;, &#x27;BUCHSTABE&#x27;, &#x27;STRASSE&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return ResponseEntity&lt;AdresseResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AdresseResponse> listAdressen1WithHttpInfo(List<String> baublock, List<String> erhaltungssatzung, List<String> gemarkung, List<String> kaminkehrerbezirk, List<String> plz, List<String> mittelschule, List<String> grundschule, List<String> polizeiinspektion, List<Long> stimmbezirk, List<Long> stimmkreis, List<Long> wahlbezirk, List<Long> wahlkreis, List<String> stadtbezirk, List<String> stadtbezirksteil, List<String> stadtbezirksviertel, String sort, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/adresse/list").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "baublock", baublock));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "erhaltungssatzung", erhaltungssatzung));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "gemarkung", gemarkung));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "kaminkehrerbezirk", kaminkehrerbezirk));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "plz", plz));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "mittelschule", mittelschule));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "grundschule", grundschule));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "polizeiinspektion", polizeiinspektion));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "stimmbezirk", stimmbezirk));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "stimmkreis", stimmkreis));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "wahlbezirk", wahlbezirk));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "wahlkreis", wahlkreis));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "stadtbezirk", stadtbezirk));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "stadtbezirksteil", stadtbezirksteil));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "stadtbezirksviertel", stadtbezirksviertel));
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

        ParameterizedTypeReference<AdresseResponse> returnType = new ParameterizedTypeReference<AdresseResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Liefert alle Änderungen, die mit den angegebenen Datum übereinstimmen.
     * 
     * <p><b>400</b> - Validation Fehler
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Abfrage erfolgreich durchgeführt
     * @param wirkungsdatumvon Von-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;2022-01-01&#x27; (optional)
     * @param wirkungsdatumbis Bis-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;2022-01-14&#x27; (optional)
     * @param strassenname Straßenname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße&#x27; (optional)
     * @param hausnummer Hausnummer  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;4&#x27; (optional)
     * @param plz Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;80638&#x27; (optional)
     * @param zusatz Zusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;a&#x27; (optional)
     * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;HAUSNUMMER&#x27;, &#x27;BUCHSTABE&#x27;, &#x27;STRASSE&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return AenderungResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public AenderungResponse listAenderungen(String wirkungsdatumvon, String wirkungsdatumbis, String strassenname, Long hausnummer, String plz, String zusatz, String sort, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        return listAenderungenWithHttpInfo(wirkungsdatumvon, wirkungsdatumbis, strassenname, hausnummer, plz, zusatz, sort, sortdir, page, pagesize).getBody();
    }

    /**
     * Liefert alle Änderungen, die mit den angegebenen Datum übereinstimmen.
     * 
     * <p><b>400</b> - Validation Fehler
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Abfrage erfolgreich durchgeführt
     * @param wirkungsdatumvon Von-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;2022-01-01&#x27; (optional)
     * @param wirkungsdatumbis Bis-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;2022-01-14&#x27; (optional)
     * @param strassenname Straßenname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße&#x27; (optional)
     * @param hausnummer Hausnummer  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;4&#x27; (optional)
     * @param plz Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;80638&#x27; (optional)
     * @param zusatz Zusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;a&#x27; (optional)
     * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;HAUSNUMMER&#x27;, &#x27;BUCHSTABE&#x27;, &#x27;STRASSE&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return ResponseEntity&lt;AenderungResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AenderungResponse> listAenderungenWithHttpInfo(String wirkungsdatumvon, String wirkungsdatumbis, String strassenname, Long hausnummer, String plz, String zusatz, String sort, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/v2/adresse/aenderung").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "wirkungsdatumvon", wirkungsdatumvon));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "wirkungsdatumbis", wirkungsdatumbis));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "strassenname", strassenname));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "hausnummer", hausnummer));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "plz", plz));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "zusatz", zusatz));
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

        ParameterizedTypeReference<AenderungResponse> returnType = new ParameterizedTypeReference<AenderungResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Liefert alle Änderungen, die mit den angegebenen Datum übereinstimmen.
     * 
     * <p><b>400</b> - Validation Fehler
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Abfrage erfolgreich durchgeführt
     * @param wirkungsdatumvon Von-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;2022-01-01&#x27; (optional)
     * @param wirkungsdatumbis Bis-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;2022-01-14&#x27; (optional)
     * @param strassenname Straßenname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße&#x27; (optional)
     * @param hausnummer Hausnummer  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;4&#x27; (optional)
     * @param plz Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;80638&#x27; (optional)
     * @param zusatz Zusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;a&#x27; (optional)
     * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;HAUSNUMMER&#x27;, &#x27;BUCHSTABE&#x27;, &#x27;STRASSE&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return AenderungResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public AenderungResponse listAenderungen1(String wirkungsdatumvon, String wirkungsdatumbis, String strassenname, Long hausnummer, String plz, String zusatz, String sort, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        return listAenderungen1WithHttpInfo(wirkungsdatumvon, wirkungsdatumbis, strassenname, hausnummer, plz, zusatz, sort, sortdir, page, pagesize).getBody();
    }

    /**
     * Liefert alle Änderungen, die mit den angegebenen Datum übereinstimmen.
     * 
     * <p><b>400</b> - Validation Fehler
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Abfrage erfolgreich durchgeführt
     * @param wirkungsdatumvon Von-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;2022-01-01&#x27; (optional)
     * @param wirkungsdatumbis Bis-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;2022-01-14&#x27; (optional)
     * @param strassenname Straßenname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße&#x27; (optional)
     * @param hausnummer Hausnummer  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;4&#x27; (optional)
     * @param plz Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;80638&#x27; (optional)
     * @param zusatz Zusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;a&#x27; (optional)
     * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;HAUSNUMMER&#x27;, &#x27;BUCHSTABE&#x27;, &#x27;STRASSE&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return ResponseEntity&lt;AenderungResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AenderungResponse> listAenderungen1WithHttpInfo(String wirkungsdatumvon, String wirkungsdatumbis, String strassenname, Long hausnummer, String plz, String zusatz, String sort, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        Object postBody = null;
        String path = UriComponentsBuilder.fromPath("/adresse/aenderung").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "wirkungsdatumvon", wirkungsdatumvon));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "wirkungsdatumbis", wirkungsdatumbis));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "strassenname", strassenname));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "hausnummer", hausnummer));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "plz", plz));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "zusatz", zusatz));
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

        ParameterizedTypeReference<AenderungResponse> returnType = new ParameterizedTypeReference<AenderungResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Sucht Münchner Adressen für angegebene Query
     * 
     * <p><b>400</b> - Request-Objekt enthält fehlerhafte Werte
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Adresssuche wurde erfolgreich durchgeführt
     * @param query Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer, ggf. Buchstabe und optional Plz/Ort  &lt;br /&gt; &lt;i&gt;Beispiele&lt;/i&gt;: &#x27;Marsstraße 4a&#x27;, &#x27;Marsstraße 4a, 80638 München&#x27; (required)
     * @param plzfilter PLZ Filterobjekt. Rangefilter (von...bis) für PLZ.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#x27;80000&#x27;. Nur Adressen mit Postleitzahl &#x27;80000&#x27; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#x27;80000&#x27; und &#x27;80002&#x27;. Nur Adressen mit einer Postleitzahl von &#x27;80000&#x27;,&#x27;80001&#x27; oder &#x27;80002&#x27; werden ausgegeben (optional)
     * @param hausnummerfilter Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#x27;90&#x27;. Nur Adressen mit Hausnummer &#x27;90&#x27; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#x27;90&#x27; und &#x27;100&#x27;. Nur Adressen mit einer Hausnummer von &#x27;90&#x27; - &#x27;100&#x27; werden ausgegeben (optional)
     * @param buchstabefilter Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#x27;a&#x27;. Nur Adressen mit Hausnummerzusatz a&#x27; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#x27;a&#x27; und &#x27;c&#x27;. Nur Adressen mit einem Hausnummerzusatz von &#x27;a&#x27;, &#x27;b&#x27; oder &#x27;c&#x27; werden ausgegeben (optional)
     * @param searchtype SearchType &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;:&lt;br /&gt;&#x27;AKTIV&#x27; – Default. Nur aktive Adressen werden durchsucht. &lt;br /&gt;&#x27;HISTORISCH&#x27; – Historische Adressen werden zusätzlich durchsucht. Falls ein Treffer mit aktiver Nachfolgeradresse gefunden wird, wird die aktive Adresse in die Ergebnisliste mit aufgenommen (mit dem zusätzlichen Attribut &#x27;ehemaligeAdresse&#x27;). Achtung: Es können Duplikate auftreten, z.B. wenn eine aktive Nachfolgeradresse viele historische Vorgänger hat. (optional, default to AKTIV)
     * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;HAUSNUMMER&#x27;, &#x27;BUCHSTABE&#x27;, &#x27;STRASSE&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return MuenchenAdresseResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public MuenchenAdresseResponse searchAdressen1(String query, List<String> plzfilter, List<Long> hausnummerfilter, List<String> buchstabefilter, String searchtype, String sort, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        return searchAdressen1WithHttpInfo(query, plzfilter, hausnummerfilter, buchstabefilter, searchtype, sort, sortdir, page, pagesize).getBody();
    }

    /**
     * Sucht Münchner Adressen für angegebene Query
     * 
     * <p><b>400</b> - Request-Objekt enthält fehlerhafte Werte
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Adresssuche wurde erfolgreich durchgeführt
     * @param query Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer, ggf. Buchstabe und optional Plz/Ort  &lt;br /&gt; &lt;i&gt;Beispiele&lt;/i&gt;: &#x27;Marsstraße 4a&#x27;, &#x27;Marsstraße 4a, 80638 München&#x27; (required)
     * @param plzfilter PLZ Filterobjekt. Rangefilter (von...bis) für PLZ.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#x27;80000&#x27;. Nur Adressen mit Postleitzahl &#x27;80000&#x27; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#x27;80000&#x27; und &#x27;80002&#x27;. Nur Adressen mit einer Postleitzahl von &#x27;80000&#x27;,&#x27;80001&#x27; oder &#x27;80002&#x27; werden ausgegeben (optional)
     * @param hausnummerfilter Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#x27;90&#x27;. Nur Adressen mit Hausnummer &#x27;90&#x27; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#x27;90&#x27; und &#x27;100&#x27;. Nur Adressen mit einer Hausnummer von &#x27;90&#x27; - &#x27;100&#x27; werden ausgegeben (optional)
     * @param buchstabefilter Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#x27;a&#x27;. Nur Adressen mit Hausnummerzusatz a&#x27; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#x27;a&#x27; und &#x27;c&#x27;. Nur Adressen mit einem Hausnummerzusatz von &#x27;a&#x27;, &#x27;b&#x27; oder &#x27;c&#x27; werden ausgegeben (optional)
     * @param searchtype SearchType &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;:&lt;br /&gt;&#x27;AKTIV&#x27; – Default. Nur aktive Adressen werden durchsucht. &lt;br /&gt;&#x27;HISTORISCH&#x27; – Historische Adressen werden zusätzlich durchsucht. Falls ein Treffer mit aktiver Nachfolgeradresse gefunden wird, wird die aktive Adresse in die Ergebnisliste mit aufgenommen (mit dem zusätzlichen Attribut &#x27;ehemaligeAdresse&#x27;). Achtung: Es können Duplikate auftreten, z.B. wenn eine aktive Nachfolgeradresse viele historische Vorgänger hat. (optional, default to AKTIV)
     * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;HAUSNUMMER&#x27;, &#x27;BUCHSTABE&#x27;, &#x27;STRASSE&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return ResponseEntity&lt;MuenchenAdresseResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<MuenchenAdresseResponse> searchAdressen1WithHttpInfo(String query, List<String> plzfilter, List<Long> hausnummerfilter, List<String> buchstabefilter, String searchtype, String sort, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'query' is set
        if (query == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'query' when calling searchAdressen1");
        }
        String path = UriComponentsBuilder.fromPath("/v2/adresse/search").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "plzfilter", plzfilter));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "hausnummerfilter", hausnummerfilter));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "buchstabefilter", buchstabefilter));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "searchtype", searchtype));
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

        ParameterizedTypeReference<MuenchenAdresseResponse> returnType = new ParameterizedTypeReference<MuenchenAdresseResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Sucht Münchner Adressen für angegebene Query
     * 
     * <p><b>400</b> - Request-Objekt enthält fehlerhafte Werte
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Adresssuche wurde erfolgreich durchgeführt
     * @param query Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer und ggf. Buchstabe  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße 4a&#x27; (required)
     * @param plzfilter PLZ Filterobjekt. Rangefilter (von...bis) für PLZ (optional)
     * @param hausnummerfilter Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer (optional)
     * @param buchstabefilter Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer (optional)
     * @param searchtype SearchType &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;:&lt;br /&gt;&#x27;AKTIV&#x27; – Default. Nur aktive Adressen werden durchsucht. &lt;br /&gt;&#x27;HISTORISCH&#x27; – Historische Adressen werden zusätzlich durchsucht. Falls ein Treffer mit aktiver Nachfolgeradresse gefunden wird, wird die aktive Adresse in die Ergebnisliste mit aufgenommen (mit dem zusätzlichen Attribut &#x27;ehemaligeAdresse&#x27;). Achtung: Es können Duplikate auftreten, z.B. wenn eine aktive Nachfolgeradresse viele historische Vorgänger hat. (optional, default to AKTIV)
     * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;HAUSNUMMER&#x27;, &#x27;BUCHSTABE&#x27;, &#x27;STRASSE&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return AdresseResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public AdresseResponse searchAdressen3(String query, List<String> plzfilter, List<Long> hausnummerfilter, List<String> buchstabefilter, String searchtype, String sort, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        return searchAdressen3WithHttpInfo(query, plzfilter, hausnummerfilter, buchstabefilter, searchtype, sort, sortdir, page, pagesize).getBody();
    }

    /**
     * Sucht Münchner Adressen für angegebene Query
     * 
     * <p><b>400</b> - Request-Objekt enthält fehlerhafte Werte
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Adresssuche wurde erfolgreich durchgeführt
     * @param query Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer und ggf. Buchstabe  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#x27;Marsstraße 4a&#x27; (required)
     * @param plzfilter PLZ Filterobjekt. Rangefilter (von...bis) für PLZ (optional)
     * @param hausnummerfilter Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer (optional)
     * @param buchstabefilter Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer (optional)
     * @param searchtype SearchType &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;:&lt;br /&gt;&#x27;AKTIV&#x27; – Default. Nur aktive Adressen werden durchsucht. &lt;br /&gt;&#x27;HISTORISCH&#x27; – Historische Adressen werden zusätzlich durchsucht. Falls ein Treffer mit aktiver Nachfolgeradresse gefunden wird, wird die aktive Adresse in die Ergebnisliste mit aufgenommen (mit dem zusätzlichen Attribut &#x27;ehemaligeAdresse&#x27;). Achtung: Es können Duplikate auftreten, z.B. wenn eine aktive Nachfolgeradresse viele historische Vorgänger hat. (optional, default to AKTIV)
     * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;HAUSNUMMER&#x27;, &#x27;BUCHSTABE&#x27;, &#x27;STRASSE&#x27; (optional)
     * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;ASC&#x27;, &#x27;DESC&#x27; (optional)
     * @param page Seitennummer (optional, default to 0)
     * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
     * @return ResponseEntity&lt;AdresseResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AdresseResponse> searchAdressen3WithHttpInfo(String query, List<String> plzfilter, List<Long> hausnummerfilter, List<String> buchstabefilter, String searchtype, String sort, String sortdir, Integer page, Integer pagesize) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'query' is set
        if (query == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'query' when calling searchAdressen3");
        }
        String path = UriComponentsBuilder.fromPath("/adresse/search").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "query", query));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "plzfilter", plzfilter));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "hausnummerfilter", hausnummerfilter));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase()), "buchstabefilter", buchstabefilter));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "searchtype", searchtype));
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

        ParameterizedTypeReference<AdresseResponse> returnType = new ParameterizedTypeReference<AdresseResponse>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Sucht Münchner Adressen in angegebener Fläche
     * 
     * <p><b>400</b> - Request-Objekt enthält fehlerhafte Werte
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Geo-Adresssuche wurde erfolgreich durchgeführt
     * @param geometrie Geometrie, in der gesucht werden soll &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;Punkt&#x27; mit Entfernung für die Suche innerhalb eines Kreises oder &#x27;BoundingBox&#x27; (required)
     * @param lat Latitude. Geographische Breite des Bezugspunktes. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
     * @param lng Longitude. Geographische Länge des Bezugspunktes. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
     * @param distanz Kreisradius. Suchdistanz zum Bezugspunkt in Metern. Wenn kein Wert gesetzt ist, wird ein Defaultwert von 50 Metern bei der Kreissuche verwendet. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 125.50 (optional)
     * @param topleftlat Latitude des linken oberen Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
     * @param topleftlng Longitude des linken oberen Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
     * @param bottomrightlat Latitude des rechten unteren Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
     * @param bottomrightlng Longitude des rechten unteren Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
     * @param format Koordinatenformat des Bezugspunktes. Wenn der Parameter fehlt, wird von UTM ausgegangen. &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;UTM&#x27;, &#x27;WGS&#x27; (optional)
     * @return List&lt;AdresseDistanz&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<AdresseDistanz> searchAdressenGeo(String geometrie, Double lat, Double lng, Double distanz, Double topleftlat, Double topleftlng, Double bottomrightlat, Double bottomrightlng, String format) throws RestClientException {
        return searchAdressenGeoWithHttpInfo(geometrie, lat, lng, distanz, topleftlat, topleftlng, bottomrightlat, bottomrightlng, format).getBody();
    }

    /**
     * Sucht Münchner Adressen in angegebener Fläche
     * 
     * <p><b>400</b> - Request-Objekt enthält fehlerhafte Werte
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Geo-Adresssuche wurde erfolgreich durchgeführt
     * @param geometrie Geometrie, in der gesucht werden soll &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;Punkt&#x27; mit Entfernung für die Suche innerhalb eines Kreises oder &#x27;BoundingBox&#x27; (required)
     * @param lat Latitude. Geographische Breite des Bezugspunktes. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
     * @param lng Longitude. Geographische Länge des Bezugspunktes. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
     * @param distanz Kreisradius. Suchdistanz zum Bezugspunkt in Metern. Wenn kein Wert gesetzt ist, wird ein Defaultwert von 50 Metern bei der Kreissuche verwendet. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 125.50 (optional)
     * @param topleftlat Latitude des linken oberen Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
     * @param topleftlng Longitude des linken oberen Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
     * @param bottomrightlat Latitude des rechten unteren Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
     * @param bottomrightlng Longitude des rechten unteren Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
     * @param format Koordinatenformat des Bezugspunktes. Wenn der Parameter fehlt, wird von UTM ausgegangen. &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;UTM&#x27;, &#x27;WGS&#x27; (optional)
     * @return ResponseEntity&lt;List&lt;AdresseDistanz&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<AdresseDistanz>> searchAdressenGeoWithHttpInfo(String geometrie, Double lat, Double lng, Double distanz, Double topleftlat, Double topleftlng, Double bottomrightlat, Double bottomrightlng, String format) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'geometrie' is set
        if (geometrie == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'geometrie' when calling searchAdressenGeo");
        }
        String path = UriComponentsBuilder.fromPath("/v2/adresse/search/geo").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "geometrie", geometrie));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "lat", lat));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "lng", lng));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "distanz", distanz));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "topleftlat", topleftlat));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "topleftlng", topleftlng));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "bottomrightlat", bottomrightlat));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "bottomrightlng", bottomrightlng));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "format", format));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<AdresseDistanz>> returnType = new ParameterizedTypeReference<List<AdresseDistanz>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Sucht Münchner Adressen in angegebener Fläche
     * 
     * <p><b>400</b> - Request-Objekt enthält fehlerhafte Werte
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Geo-Adresssuche wurde erfolgreich durchgeführt
     * @param geometrie Geometrie, in der gesucht werden soll &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;Punkt&#x27; mit Entfernung für die Suche innerhalb eines Kreises oder &#x27;BoundingBox&#x27; (required)
     * @param lat Latitude. Geographische Breite des Bezugspunktes. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
     * @param lng Longitude. Geographische Länge des Bezugspunktes. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
     * @param distanz Kreisradius. Suchdistanz zum Bezugspunkt in Metern. Wenn kein Wert gesetzt ist, wird ein Defaultwert von 50 Metern bei der Kreissuche verwendet. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 125.50 (optional)
     * @param topleftlat Latitude des linken oberen Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
     * @param topleftlng Longitude des linken oberen Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
     * @param bottomrightlat Latitude des rechten unteren Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
     * @param bottomrightlng Longitude des rechten unteren Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
     * @param format Koordinatenformat des Bezugspunktes. Wenn der Parameter fehlt, wird von UTM ausgegangen. &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;UTM&#x27;, &#x27;WGS&#x27; (optional)
     * @return List&lt;AdresseDistanz&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<AdresseDistanz> searchAdressenGeo1(String geometrie, Double lat, Double lng, Double distanz, Double topleftlat, Double topleftlng, Double bottomrightlat, Double bottomrightlng, String format) throws RestClientException {
        return searchAdressenGeo1WithHttpInfo(geometrie, lat, lng, distanz, topleftlat, topleftlng, bottomrightlat, bottomrightlng, format).getBody();
    }

    /**
     * Sucht Münchner Adressen in angegebener Fläche
     * 
     * <p><b>400</b> - Request-Objekt enthält fehlerhafte Werte
     * <p><b>500</b> - Unerwartetes Fehlverhalten
     * <p><b>200</b> - Geo-Adresssuche wurde erfolgreich durchgeführt
     * @param geometrie Geometrie, in der gesucht werden soll &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;Punkt&#x27; mit Entfernung für die Suche innerhalb eines Kreises oder &#x27;BoundingBox&#x27; (required)
     * @param lat Latitude. Geographische Breite des Bezugspunktes. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
     * @param lng Longitude. Geographische Länge des Bezugspunktes. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
     * @param distanz Kreisradius. Suchdistanz zum Bezugspunkt in Metern. Wenn kein Wert gesetzt ist, wird ein Defaultwert von 50 Metern bei der Kreissuche verwendet. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 125.50 (optional)
     * @param topleftlat Latitude des linken oberen Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
     * @param topleftlng Longitude des linken oberen Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
     * @param bottomrightlat Latitude des rechten unteren Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
     * @param bottomrightlng Longitude des rechten unteren Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
     * @param format Koordinatenformat des Bezugspunktes. Wenn der Parameter fehlt, wird von UTM ausgegangen. &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#x27;UTM&#x27;, &#x27;WGS&#x27; (optional)
     * @return ResponseEntity&lt;List&lt;AdresseDistanz&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<AdresseDistanz>> searchAdressenGeo1WithHttpInfo(String geometrie, Double lat, Double lng, Double distanz, Double topleftlat, Double topleftlng, Double bottomrightlat, Double bottomrightlng, String format) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'geometrie' is set
        if (geometrie == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'geometrie' when calling searchAdressenGeo1");
        }
        String path = UriComponentsBuilder.fromPath("/adresse/search/geo").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "geometrie", geometrie));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "lat", lat));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "lng", lng));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "distanz", distanz));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "topleftlat", topleftlat));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "topleftlng", topleftlng));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "bottomrightlat", bottomrightlat));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "bottomrightlng", bottomrightlng));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "format", format));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<AdresseDistanz>> returnType = new ParameterizedTypeReference<List<AdresseDistanz>>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
