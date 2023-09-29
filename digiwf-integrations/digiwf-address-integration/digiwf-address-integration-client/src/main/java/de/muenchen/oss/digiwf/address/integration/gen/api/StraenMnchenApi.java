package de.muenchen.oss.digiwf.address.integration.gen.api;

import de.muenchen.oss.digiwf.address.integration.gen.ApiClient;
import de.muenchen.oss.digiwf.address.integration.gen.EncodingUtils;
import de.muenchen.oss.digiwf.address.integration.gen.model.ApiResponse;

import de.muenchen.oss.digiwf.address.integration.gen.model.Strasse;
import de.muenchen.oss.digiwf.address.integration.gen.model.StrasseResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import feign.*;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-09-29T12:03:57.927376700+02:00[Europe/Berlin]")
public interface StraenMnchenApi extends ApiClient.Api {


  /**
   * Liefert die Straße zu der Straßen-Id.
   * 
   * @param strasseId Straßennummer/Straßenschlüssel  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (required)
   * @return Strasse
   */
  @RequestLine("GET /v2/strasse/{strasseId}")
  @Headers({
    "Accept: application/json",
  })
  Strasse findStrasseByNummer(@Param("strasseId") Long strasseId);

  /**
   * Liefert die Straße zu der Straßen-Id.
   * Similar to <code>findStrasseByNummer</code> but it also returns the http response headers .
   * 
   * @param strasseId Straßennummer/Straßenschlüssel  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (required)
   * @return A ApiResponse that wraps the response boyd and the http headers.
   */
  @RequestLine("GET /v2/strasse/{strasseId}")
  @Headers({
    "Accept: application/json",
  })
  ApiResponse<Strasse> findStrasseByNummerWithHttpInfo(@Param("strasseId") Long strasseId);



  /**
   * Liefert die Straße zu der Straßennummer.
   * 
   * @param strassennummer Straßennummer  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (required)
   * @return Strasse
   */
  @RequestLine("GET /strasse/{strassennummer}")
  @Headers({
    "Accept: application/json",
  })
  Strasse findStrasseByNummer1(@Param("strassennummer") Long strassennummer);

  /**
   * Liefert die Straße zu der Straßennummer.
   * Similar to <code>findStrasseByNummer1</code> but it also returns the http response headers .
   * 
   * @param strassennummer Straßennummer  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (required)
   * @return A ApiResponse that wraps the response boyd and the http headers.
   */
  @RequestLine("GET /strasse/{strassennummer}")
  @Headers({
    "Accept: application/json",
  })
  ApiResponse<Strasse> findStrasseByNummer1WithHttpInfo(@Param("strassennummer") Long strassennummer);



  /**
   * Liefert alle Straßen, die mit den Suchparametern übereinstimmen.
   * 
   * @param stadtbezirksnamen Stadtbezirksnamen &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Maxvorstadt&#39; (optional)
   * @param stadtbezirksnummern Stadtbezirksnummern  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3 (optional)
   * @param strassenname Straßenname  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return StrasseResponse
   */
  @RequestLine("GET /v2/strasse/search?stadtbezirksnamen={stadtbezirksnamen}&stadtbezirksnummern={stadtbezirksnummern}&strassenname={strassenname}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  StrasseResponse listStrassen(@Param("stadtbezirksnamen") List<String> stadtbezirksnamen, @Param("stadtbezirksnummern") List<Long> stadtbezirksnummern, @Param("strassenname") String strassenname, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);

  /**
   * Liefert alle Straßen, die mit den Suchparametern übereinstimmen.
   * Similar to <code>listStrassen</code> but it also returns the http response headers .
   * 
   * @param stadtbezirksnamen Stadtbezirksnamen &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Maxvorstadt&#39; (optional)
   * @param stadtbezirksnummern Stadtbezirksnummern  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3 (optional)
   * @param strassenname Straßenname  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return A ApiResponse that wraps the response boyd and the http headers.
   */
  @RequestLine("GET /v2/strasse/search?stadtbezirksnamen={stadtbezirksnamen}&stadtbezirksnummern={stadtbezirksnummern}&strassenname={strassenname}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  ApiResponse<StrasseResponse> listStrassenWithHttpInfo(@Param("stadtbezirksnamen") List<String> stadtbezirksnamen, @Param("stadtbezirksnummern") List<Long> stadtbezirksnummern, @Param("strassenname") String strassenname, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);


  /**
   * Liefert alle Straßen, die mit den Suchparametern übereinstimmen.
   * 
   * Note, this is equivalent to the other <code>listStrassen</code> method,
   * but with the query parameters collected into a single Map parameter. This
   * is convenient for services with optional query parameters, especially when
   * used with the {@link ListStrassenQueryParams} class that allows for
   * building up this map in a fluent style.
   * @param queryParams Map of query parameters as name-value pairs
   *   <p>The following elements may be specified in the query map:</p>
   *   <ul>
   *   <li>stadtbezirksnamen - Stadtbezirksnamen &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Maxvorstadt&#39; (optional)</li>
   *   <li>stadtbezirksnummern - Stadtbezirksnummern  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3 (optional)</li>
   *   <li>strassenname - Straßenname  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)</li>
   *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
   *   <li>page - Seitennummer (optional, default to 0)</li>
   *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
   *   </ul>
   * @return StrasseResponse
   */
  @RequestLine("GET /v2/strasse/search?stadtbezirksnamen={stadtbezirksnamen}&stadtbezirksnummern={stadtbezirksnummern}&strassenname={strassenname}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
  "Accept: application/json",
  })
  StrasseResponse listStrassen(@QueryMap(encoded=true) ListStrassenQueryParams queryParams);

  /**
  * Liefert alle Straßen, die mit den Suchparametern übereinstimmen.
  * 
  * Note, this is equivalent to the other <code>listStrassen</code> that receives the query parameters as a map,
  * but this one also exposes the Http response headers
      * @param queryParams Map of query parameters as name-value pairs
      *   <p>The following elements may be specified in the query map:</p>
      *   <ul>
          *   <li>stadtbezirksnamen - Stadtbezirksnamen &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Maxvorstadt&#39; (optional)</li>
          *   <li>stadtbezirksnummern - Stadtbezirksnummern  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3 (optional)</li>
          *   <li>strassenname - Straßenname  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)</li>
          *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
          *   <li>page - Seitennummer (optional, default to 0)</li>
          *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
      *   </ul>
          * @return StrasseResponse
      */
      @RequestLine("GET /v2/strasse/search?stadtbezirksnamen={stadtbezirksnamen}&stadtbezirksnummern={stadtbezirksnummern}&strassenname={strassenname}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
      @Headers({
    "Accept: application/json",
      })
   ApiResponse<StrasseResponse> listStrassenWithHttpInfo(@QueryMap(encoded=true) ListStrassenQueryParams queryParams);


   /**
   * A convenience class for generating query parameters for the
   * <code>listStrassen</code> method in a fluent style.
   */
  public static class ListStrassenQueryParams extends HashMap<String, Object> {
    public ListStrassenQueryParams stadtbezirksnamen(final List<String> value) {
      put("stadtbezirksnamen", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListStrassenQueryParams stadtbezirksnummern(final List<Long> value) {
      put("stadtbezirksnummern", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListStrassenQueryParams strassenname(final String value) {
      put("strassenname", EncodingUtils.encode(value));
      return this;
    }
    public ListStrassenQueryParams sortdir(final String value) {
      put("sortdir", EncodingUtils.encode(value));
      return this;
    }
    public ListStrassenQueryParams page(final Integer value) {
      put("page", EncodingUtils.encode(value));
      return this;
    }
    public ListStrassenQueryParams pagesize(final Integer value) {
      put("pagesize", EncodingUtils.encode(value));
      return this;
    }
  }

  /**
   * Liefert alle Straßen, die mit den Suchparametern übereinstimmen.
   * 
   * @param stadtbezirksnamen Stadtbezirksnamen &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Maxvorstadt&#39; (optional)
   * @param stadtbezirksnummern Stadtbezirksnummern  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3 (optional)
   * @param strassenname Straßenname  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return StrasseResponse
   */
  @RequestLine("GET /strasse/search?stadtbezirksnamen={stadtbezirksnamen}&stadtbezirksnummern={stadtbezirksnummern}&strassenname={strassenname}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  StrasseResponse listStrassen1(@Param("stadtbezirksnamen") List<String> stadtbezirksnamen, @Param("stadtbezirksnummern") List<Long> stadtbezirksnummern, @Param("strassenname") String strassenname, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);

  /**
   * Liefert alle Straßen, die mit den Suchparametern übereinstimmen.
   * Similar to <code>listStrassen1</code> but it also returns the http response headers .
   * 
   * @param stadtbezirksnamen Stadtbezirksnamen &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Maxvorstadt&#39; (optional)
   * @param stadtbezirksnummern Stadtbezirksnummern  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3 (optional)
   * @param strassenname Straßenname  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return A ApiResponse that wraps the response boyd and the http headers.
   */
  @RequestLine("GET /strasse/search?stadtbezirksnamen={stadtbezirksnamen}&stadtbezirksnummern={stadtbezirksnummern}&strassenname={strassenname}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  ApiResponse<StrasseResponse> listStrassen1WithHttpInfo(@Param("stadtbezirksnamen") List<String> stadtbezirksnamen, @Param("stadtbezirksnummern") List<Long> stadtbezirksnummern, @Param("strassenname") String strassenname, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);


  /**
   * Liefert alle Straßen, die mit den Suchparametern übereinstimmen.
   * 
   * Note, this is equivalent to the other <code>listStrassen1</code> method,
   * but with the query parameters collected into a single Map parameter. This
   * is convenient for services with optional query parameters, especially when
   * used with the {@link ListStrassen1QueryParams} class that allows for
   * building up this map in a fluent style.
   * @param queryParams Map of query parameters as name-value pairs
   *   <p>The following elements may be specified in the query map:</p>
   *   <ul>
   *   <li>stadtbezirksnamen - Stadtbezirksnamen &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Maxvorstadt&#39; (optional)</li>
   *   <li>stadtbezirksnummern - Stadtbezirksnummern  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3 (optional)</li>
   *   <li>strassenname - Straßenname  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)</li>
   *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
   *   <li>page - Seitennummer (optional, default to 0)</li>
   *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
   *   </ul>
   * @return StrasseResponse
   */
  @RequestLine("GET /strasse/search?stadtbezirksnamen={stadtbezirksnamen}&stadtbezirksnummern={stadtbezirksnummern}&strassenname={strassenname}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
  "Accept: application/json",
  })
  StrasseResponse listStrassen1(@QueryMap(encoded=true) ListStrassen1QueryParams queryParams);

  /**
  * Liefert alle Straßen, die mit den Suchparametern übereinstimmen.
  * 
  * Note, this is equivalent to the other <code>listStrassen1</code> that receives the query parameters as a map,
  * but this one also exposes the Http response headers
      * @param queryParams Map of query parameters as name-value pairs
      *   <p>The following elements may be specified in the query map:</p>
      *   <ul>
          *   <li>stadtbezirksnamen - Stadtbezirksnamen &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Maxvorstadt&#39; (optional)</li>
          *   <li>stadtbezirksnummern - Stadtbezirksnummern  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3 (optional)</li>
          *   <li>strassenname - Straßenname  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)</li>
          *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
          *   <li>page - Seitennummer (optional, default to 0)</li>
          *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
      *   </ul>
          * @return StrasseResponse
      */
      @RequestLine("GET /strasse/search?stadtbezirksnamen={stadtbezirksnamen}&stadtbezirksnummern={stadtbezirksnummern}&strassenname={strassenname}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
      @Headers({
    "Accept: application/json",
      })
   ApiResponse<StrasseResponse> listStrassen1WithHttpInfo(@QueryMap(encoded=true) ListStrassen1QueryParams queryParams);


   /**
   * A convenience class for generating query parameters for the
   * <code>listStrassen1</code> method in a fluent style.
   */
  public static class ListStrassen1QueryParams extends HashMap<String, Object> {
    public ListStrassen1QueryParams stadtbezirksnamen(final List<String> value) {
      put("stadtbezirksnamen", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListStrassen1QueryParams stadtbezirksnummern(final List<Long> value) {
      put("stadtbezirksnummern", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListStrassen1QueryParams strassenname(final String value) {
      put("strassenname", EncodingUtils.encode(value));
      return this;
    }
    public ListStrassen1QueryParams sortdir(final String value) {
      put("sortdir", EncodingUtils.encode(value));
      return this;
    }
    public ListStrassen1QueryParams page(final Integer value) {
      put("page", EncodingUtils.encode(value));
      return this;
    }
    public ListStrassen1QueryParams pagesize(final Integer value) {
      put("pagesize", EncodingUtils.encode(value));
      return this;
    }
  }
}
