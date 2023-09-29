package de.muenchen.oss.digiwf.address.integration.gen.api;

import de.muenchen.oss.digiwf.address.integration.gen.ApiClient;
import de.muenchen.oss.digiwf.address.integration.gen.EncodingUtils;
import de.muenchen.oss.digiwf.address.integration.gen.model.ApiResponse;

import de.muenchen.oss.digiwf.address.integration.gen.model.BundesweiteAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.gen.model.ExterneAdresseResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import feign.*;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-09-29T12:03:57.927376700+02:00[Europe/Berlin]")
public interface AdressenBundesweitApi extends ApiClient.Api {


  /**
   * Sucht bundesweit Adressen für angegebene Query
   * 
   * @param query Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer, ggf. Buchstabe und optional Plz/Ort  &lt;br /&gt; &lt;i&gt;Beispiele&lt;/i&gt;: &#39;Wippenhauser Straße 54a&#39;, &#39;Wippenhauser Straße 54a, 85354 Freising&#39; (optional)
   * @param plz Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;85354&#39; (optional)
   * @param ortsname Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Freising&#39; (optional)
   * @param gemeindeschluessel Optional: Verwaltungszugehörigkeit - Land + Regierung + Kreis + Gemeinde  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;09178124&#39; (optional)
   * @param hausnummerfilter Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;90&#39;. Nur Adressen mit Hausnummer &#39;90&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;90&#39; und &#39;100&#39;. Nur Adressen mit einer Hausnummer von &#39;90&#39; - &#39;100&#39; werden ausgegeben (optional)
   * @param buchstabefilter Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;a&#39;. Nur Adressen mit Hausnummerzusatz &#39;a&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;a&#39; und &#39;c&#39;. Nur Adressen mit einem Hausnummerzusatz von &#39;a&#39;, &#39;b&#39; oder &#39;c&#39; werden ausgegeben (optional)
   * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return BundesweiteAdresseResponse
   */
  @RequestLine("GET /v2/adresse_bundesweit/search?query={query}&plz={plz}&ortsname={ortsname}&gemeindeschluessel={gemeindeschluessel}&hausnummerfilter={hausnummerfilter}&buchstabefilter={buchstabefilter}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  BundesweiteAdresseResponse searchAdressen(@Param("query") String query, @Param("plz") String plz, @Param("ortsname") String ortsname, @Param("gemeindeschluessel") String gemeindeschluessel, @Param("hausnummerfilter") List<Long> hausnummerfilter, @Param("buchstabefilter") List<String> buchstabefilter, @Param("sort") String sort, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);

  /**
   * Sucht bundesweit Adressen für angegebene Query
   * Similar to <code>searchAdressen</code> but it also returns the http response headers .
   * 
   * @param query Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer, ggf. Buchstabe und optional Plz/Ort  &lt;br /&gt; &lt;i&gt;Beispiele&lt;/i&gt;: &#39;Wippenhauser Straße 54a&#39;, &#39;Wippenhauser Straße 54a, 85354 Freising&#39; (optional)
   * @param plz Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;85354&#39; (optional)
   * @param ortsname Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Freising&#39; (optional)
   * @param gemeindeschluessel Optional: Verwaltungszugehörigkeit - Land + Regierung + Kreis + Gemeinde  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;09178124&#39; (optional)
   * @param hausnummerfilter Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;90&#39;. Nur Adressen mit Hausnummer &#39;90&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;90&#39; und &#39;100&#39;. Nur Adressen mit einer Hausnummer von &#39;90&#39; - &#39;100&#39; werden ausgegeben (optional)
   * @param buchstabefilter Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;a&#39;. Nur Adressen mit Hausnummerzusatz &#39;a&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;a&#39; und &#39;c&#39;. Nur Adressen mit einem Hausnummerzusatz von &#39;a&#39;, &#39;b&#39; oder &#39;c&#39; werden ausgegeben (optional)
   * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return A ApiResponse that wraps the response boyd and the http headers.
   */
  @RequestLine("GET /v2/adresse_bundesweit/search?query={query}&plz={plz}&ortsname={ortsname}&gemeindeschluessel={gemeindeschluessel}&hausnummerfilter={hausnummerfilter}&buchstabefilter={buchstabefilter}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  ApiResponse<BundesweiteAdresseResponse> searchAdressenWithHttpInfo(@Param("query") String query, @Param("plz") String plz, @Param("ortsname") String ortsname, @Param("gemeindeschluessel") String gemeindeschluessel, @Param("hausnummerfilter") List<Long> hausnummerfilter, @Param("buchstabefilter") List<String> buchstabefilter, @Param("sort") String sort, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);


  /**
   * Sucht bundesweit Adressen für angegebene Query
   * 
   * Note, this is equivalent to the other <code>searchAdressen</code> method,
   * but with the query parameters collected into a single Map parameter. This
   * is convenient for services with optional query parameters, especially when
   * used with the {@link SearchAdressenQueryParams} class that allows for
   * building up this map in a fluent style.
   * @param queryParams Map of query parameters as name-value pairs
   *   <p>The following elements may be specified in the query map:</p>
   *   <ul>
   *   <li>query - Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer, ggf. Buchstabe und optional Plz/Ort  &lt;br /&gt; &lt;i&gt;Beispiele&lt;/i&gt;: &#39;Wippenhauser Straße 54a&#39;, &#39;Wippenhauser Straße 54a, 85354 Freising&#39; (optional)</li>
   *   <li>plz - Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;85354&#39; (optional)</li>
   *   <li>ortsname - Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Freising&#39; (optional)</li>
   *   <li>gemeindeschluessel - Optional: Verwaltungszugehörigkeit - Land + Regierung + Kreis + Gemeinde  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;09178124&#39; (optional)</li>
   *   <li>hausnummerfilter - Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;90&#39;. Nur Adressen mit Hausnummer &#39;90&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;90&#39; und &#39;100&#39;. Nur Adressen mit einer Hausnummer von &#39;90&#39; - &#39;100&#39; werden ausgegeben (optional)</li>
   *   <li>buchstabefilter - Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;a&#39;. Nur Adressen mit Hausnummerzusatz &#39;a&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;a&#39; und &#39;c&#39;. Nur Adressen mit einem Hausnummerzusatz von &#39;a&#39;, &#39;b&#39; oder &#39;c&#39; werden ausgegeben (optional)</li>
   *   <li>sort - Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)</li>
   *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
   *   <li>page - Seitennummer (optional, default to 0)</li>
   *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
   *   </ul>
   * @return BundesweiteAdresseResponse
   */
  @RequestLine("GET /v2/adresse_bundesweit/search?query={query}&plz={plz}&ortsname={ortsname}&gemeindeschluessel={gemeindeschluessel}&hausnummerfilter={hausnummerfilter}&buchstabefilter={buchstabefilter}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
  "Accept: application/json",
  })
  BundesweiteAdresseResponse searchAdressen(@QueryMap(encoded=true) SearchAdressenQueryParams queryParams);

  /**
  * Sucht bundesweit Adressen für angegebene Query
  * 
  * Note, this is equivalent to the other <code>searchAdressen</code> that receives the query parameters as a map,
  * but this one also exposes the Http response headers
      * @param queryParams Map of query parameters as name-value pairs
      *   <p>The following elements may be specified in the query map:</p>
      *   <ul>
          *   <li>query - Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer, ggf. Buchstabe und optional Plz/Ort  &lt;br /&gt; &lt;i&gt;Beispiele&lt;/i&gt;: &#39;Wippenhauser Straße 54a&#39;, &#39;Wippenhauser Straße 54a, 85354 Freising&#39; (optional)</li>
          *   <li>plz - Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;85354&#39; (optional)</li>
          *   <li>ortsname - Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Freising&#39; (optional)</li>
          *   <li>gemeindeschluessel - Optional: Verwaltungszugehörigkeit - Land + Regierung + Kreis + Gemeinde  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;09178124&#39; (optional)</li>
          *   <li>hausnummerfilter - Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;90&#39;. Nur Adressen mit Hausnummer &#39;90&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;90&#39; und &#39;100&#39;. Nur Adressen mit einer Hausnummer von &#39;90&#39; - &#39;100&#39; werden ausgegeben (optional)</li>
          *   <li>buchstabefilter - Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;a&#39;. Nur Adressen mit Hausnummerzusatz &#39;a&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;a&#39; und &#39;c&#39;. Nur Adressen mit einem Hausnummerzusatz von &#39;a&#39;, &#39;b&#39; oder &#39;c&#39; werden ausgegeben (optional)</li>
          *   <li>sort - Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)</li>
          *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
          *   <li>page - Seitennummer (optional, default to 0)</li>
          *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
      *   </ul>
          * @return BundesweiteAdresseResponse
      */
      @RequestLine("GET /v2/adresse_bundesweit/search?query={query}&plz={plz}&ortsname={ortsname}&gemeindeschluessel={gemeindeschluessel}&hausnummerfilter={hausnummerfilter}&buchstabefilter={buchstabefilter}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
      @Headers({
    "Accept: application/json",
      })
   ApiResponse<BundesweiteAdresseResponse> searchAdressenWithHttpInfo(@QueryMap(encoded=true) SearchAdressenQueryParams queryParams);


   /**
   * A convenience class for generating query parameters for the
   * <code>searchAdressen</code> method in a fluent style.
   */
  public static class SearchAdressenQueryParams extends HashMap<String, Object> {
    public SearchAdressenQueryParams query(final String value) {
      put("query", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenQueryParams plz(final String value) {
      put("plz", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenQueryParams ortsname(final String value) {
      put("ortsname", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenQueryParams gemeindeschluessel(final String value) {
      put("gemeindeschluessel", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenQueryParams hausnummerfilter(final List<Long> value) {
      put("hausnummerfilter", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public SearchAdressenQueryParams buchstabefilter(final List<String> value) {
      put("buchstabefilter", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public SearchAdressenQueryParams sort(final String value) {
      put("sort", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenQueryParams sortdir(final String value) {
      put("sortdir", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenQueryParams page(final Integer value) {
      put("page", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenQueryParams pagesize(final Integer value) {
      put("pagesize", EncodingUtils.encode(value));
      return this;
    }
  }

  /**
   * Sucht bundesweit Adressen für angegebene Query
   * 
   * @param query Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer und ggf. Buchstabe  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße 4a&#39; (optional)
   * @param plz Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;85354&#39; (optional)
   * @param ortsname Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Freising&#39; (optional)
   * @param gemeindeschluessel Optional: Verwaltungszugehörigkeit - Land + Regierung + Kreis + Gemeinde  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;09178124&#39; (optional)
   * @param hausnummerfilter Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer (optional)
   * @param buchstabefilter Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer (optional)
   * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return ExterneAdresseResponse
   */
  @RequestLine("GET /externe_adresse/search?query={query}&plz={plz}&ortsname={ortsname}&gemeindeschluessel={gemeindeschluessel}&hausnummerfilter={hausnummerfilter}&buchstabefilter={buchstabefilter}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  ExterneAdresseResponse searchAdressen2(@Param("query") String query, @Param("plz") String plz, @Param("ortsname") String ortsname, @Param("gemeindeschluessel") String gemeindeschluessel, @Param("hausnummerfilter") List<Long> hausnummerfilter, @Param("buchstabefilter") List<String> buchstabefilter, @Param("sort") String sort, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);

  /**
   * Sucht bundesweit Adressen für angegebene Query
   * Similar to <code>searchAdressen2</code> but it also returns the http response headers .
   * 
   * @param query Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer und ggf. Buchstabe  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße 4a&#39; (optional)
   * @param plz Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;85354&#39; (optional)
   * @param ortsname Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Freising&#39; (optional)
   * @param gemeindeschluessel Optional: Verwaltungszugehörigkeit - Land + Regierung + Kreis + Gemeinde  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;09178124&#39; (optional)
   * @param hausnummerfilter Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer (optional)
   * @param buchstabefilter Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer (optional)
   * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return A ApiResponse that wraps the response boyd and the http headers.
   */
  @RequestLine("GET /externe_adresse/search?query={query}&plz={plz}&ortsname={ortsname}&gemeindeschluessel={gemeindeschluessel}&hausnummerfilter={hausnummerfilter}&buchstabefilter={buchstabefilter}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  ApiResponse<ExterneAdresseResponse> searchAdressen2WithHttpInfo(@Param("query") String query, @Param("plz") String plz, @Param("ortsname") String ortsname, @Param("gemeindeschluessel") String gemeindeschluessel, @Param("hausnummerfilter") List<Long> hausnummerfilter, @Param("buchstabefilter") List<String> buchstabefilter, @Param("sort") String sort, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);


  /**
   * Sucht bundesweit Adressen für angegebene Query
   * 
   * Note, this is equivalent to the other <code>searchAdressen2</code> method,
   * but with the query parameters collected into a single Map parameter. This
   * is convenient for services with optional query parameters, especially when
   * used with the {@link SearchAdressen2QueryParams} class that allows for
   * building up this map in a fluent style.
   * @param queryParams Map of query parameters as name-value pairs
   *   <p>The following elements may be specified in the query map:</p>
   *   <ul>
   *   <li>query - Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer und ggf. Buchstabe  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße 4a&#39; (optional)</li>
   *   <li>plz - Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;85354&#39; (optional)</li>
   *   <li>ortsname - Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Freising&#39; (optional)</li>
   *   <li>gemeindeschluessel - Optional: Verwaltungszugehörigkeit - Land + Regierung + Kreis + Gemeinde  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;09178124&#39; (optional)</li>
   *   <li>hausnummerfilter - Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer (optional)</li>
   *   <li>buchstabefilter - Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer (optional)</li>
   *   <li>sort - Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)</li>
   *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
   *   <li>page - Seitennummer (optional, default to 0)</li>
   *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
   *   </ul>
   * @return ExterneAdresseResponse
   */
  @RequestLine("GET /externe_adresse/search?query={query}&plz={plz}&ortsname={ortsname}&gemeindeschluessel={gemeindeschluessel}&hausnummerfilter={hausnummerfilter}&buchstabefilter={buchstabefilter}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
  "Accept: application/json",
  })
  ExterneAdresseResponse searchAdressen2(@QueryMap(encoded=true) SearchAdressen2QueryParams queryParams);

  /**
  * Sucht bundesweit Adressen für angegebene Query
  * 
  * Note, this is equivalent to the other <code>searchAdressen2</code> that receives the query parameters as a map,
  * but this one also exposes the Http response headers
      * @param queryParams Map of query parameters as name-value pairs
      *   <p>The following elements may be specified in the query map:</p>
      *   <ul>
          *   <li>query - Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer und ggf. Buchstabe  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße 4a&#39; (optional)</li>
          *   <li>plz - Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;85354&#39; (optional)</li>
          *   <li>ortsname - Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Freising&#39; (optional)</li>
          *   <li>gemeindeschluessel - Optional: Verwaltungszugehörigkeit - Land + Regierung + Kreis + Gemeinde  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;09178124&#39; (optional)</li>
          *   <li>hausnummerfilter - Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer (optional)</li>
          *   <li>buchstabefilter - Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer (optional)</li>
          *   <li>sort - Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)</li>
          *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
          *   <li>page - Seitennummer (optional, default to 0)</li>
          *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
      *   </ul>
          * @return ExterneAdresseResponse
      */
      @RequestLine("GET /externe_adresse/search?query={query}&plz={plz}&ortsname={ortsname}&gemeindeschluessel={gemeindeschluessel}&hausnummerfilter={hausnummerfilter}&buchstabefilter={buchstabefilter}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
      @Headers({
    "Accept: application/json",
      })
   ApiResponse<ExterneAdresseResponse> searchAdressen2WithHttpInfo(@QueryMap(encoded=true) SearchAdressen2QueryParams queryParams);


   /**
   * A convenience class for generating query parameters for the
   * <code>searchAdressen2</code> method in a fluent style.
   */
  public static class SearchAdressen2QueryParams extends HashMap<String, Object> {
    public SearchAdressen2QueryParams query(final String value) {
      put("query", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressen2QueryParams plz(final String value) {
      put("plz", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressen2QueryParams ortsname(final String value) {
      put("ortsname", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressen2QueryParams gemeindeschluessel(final String value) {
      put("gemeindeschluessel", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressen2QueryParams hausnummerfilter(final List<Long> value) {
      put("hausnummerfilter", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public SearchAdressen2QueryParams buchstabefilter(final List<String> value) {
      put("buchstabefilter", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public SearchAdressen2QueryParams sort(final String value) {
      put("sort", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressen2QueryParams sortdir(final String value) {
      put("sortdir", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressen2QueryParams page(final Integer value) {
      put("page", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressen2QueryParams pagesize(final Integer value) {
      put("pagesize", EncodingUtils.encode(value));
      return this;
    }
  }
}
