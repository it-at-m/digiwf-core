package de.muenchen.oss.digiwf.address.integration.gen.api;

import de.muenchen.oss.digiwf.address.integration.gen.ApiClient;
import de.muenchen.oss.digiwf.address.integration.gen.EncodingUtils;
import de.muenchen.oss.digiwf.address.integration.gen.model.ApiResponse;

import de.muenchen.oss.digiwf.address.integration.gen.model.Adresse;
import de.muenchen.oss.digiwf.address.integration.gen.model.AdresseDistanz;
import de.muenchen.oss.digiwf.address.integration.gen.model.AdresseResponse;
import de.muenchen.oss.digiwf.address.integration.gen.model.AenderungResponse;
import java.util.Date;
import de.muenchen.oss.digiwf.address.integration.gen.model.MuenchenAdresse;
import de.muenchen.oss.digiwf.address.integration.gen.model.MuenchenAdresseResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import feign.*;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-09-29T12:03:57.927376700+02:00[Europe/Berlin]")
public interface AdressenMnchenApi extends ApiClient.Api {


  /**
   * Prüft, ob die angegebene Adresse existiert. Die Pflichtwerte können entweder: 1. komplett mittels adresse-Parameter, Hausnummer und Buchstabe ohne Leerzeichen dazwischen, angegeben werden oder 2. als Einzelwerte über die Parameter strassenname/strassenschluessel, hausnummer, zusatz angegeben werden 
   * 
   * @param adresse Amtliche Schreibweise des Straßennamens mit Hausnummer und ggf. Zusatz (Pflichtfeld, wenn Straßenname/Straßenschluessel + Hausnummer nicht gesetzt sind). Optional Postleitzahl und Ort  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: entweder &#39;Marsstraße 4a&#39; oder &#39;Marsstraße 4a, 80638 München&#39; (optional)
   * @param strassenname Amtliche Schreibweise des Straßennamens &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)
   * @param strasseId StrasseId &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (optional)
   * @param hausnummer Hausnummer der Straße &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;4&#39; (optional)
   * @param zusatz Hausnummerzusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;a&#39; (optional)
   * @param plz Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80638&#39; (optional)
   * @param ortsname Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;München&#39; (optional)
   * @param gemeindeschluessel Optional: Gemeindeschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;09162000&#39; (optional)
   * @return MuenchenAdresse
   */
  @RequestLine("GET /v2/adresse/check?adresse={adresse}&strassenname={strassenname}&strasseId={strasseId}&hausnummer={hausnummer}&zusatz={zusatz}&plz={plz}&ortsname={ortsname}&gemeindeschluessel={gemeindeschluessel}")
  @Headers({
    "Accept: application/json",
  })
  MuenchenAdresse checkAdresse(@Param("adresse") String adresse, @Param("strassenname") String strassenname, @Param("strasseId") Integer strasseId, @Param("hausnummer") String hausnummer, @Param("zusatz") String zusatz, @Param("plz") String plz, @Param("ortsname") String ortsname, @Param("gemeindeschluessel") String gemeindeschluessel);

  /**
   * Prüft, ob die angegebene Adresse existiert. Die Pflichtwerte können entweder: 1. komplett mittels adresse-Parameter, Hausnummer und Buchstabe ohne Leerzeichen dazwischen, angegeben werden oder 2. als Einzelwerte über die Parameter strassenname/strassenschluessel, hausnummer, zusatz angegeben werden 
   * Similar to <code>checkAdresse</code> but it also returns the http response headers .
   * 
   * @param adresse Amtliche Schreibweise des Straßennamens mit Hausnummer und ggf. Zusatz (Pflichtfeld, wenn Straßenname/Straßenschluessel + Hausnummer nicht gesetzt sind). Optional Postleitzahl und Ort  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: entweder &#39;Marsstraße 4a&#39; oder &#39;Marsstraße 4a, 80638 München&#39; (optional)
   * @param strassenname Amtliche Schreibweise des Straßennamens &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)
   * @param strasseId StrasseId &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (optional)
   * @param hausnummer Hausnummer der Straße &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;4&#39; (optional)
   * @param zusatz Hausnummerzusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;a&#39; (optional)
   * @param plz Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80638&#39; (optional)
   * @param ortsname Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;München&#39; (optional)
   * @param gemeindeschluessel Optional: Gemeindeschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;09162000&#39; (optional)
   * @return A ApiResponse that wraps the response boyd and the http headers.
   */
  @RequestLine("GET /v2/adresse/check?adresse={adresse}&strassenname={strassenname}&strasseId={strasseId}&hausnummer={hausnummer}&zusatz={zusatz}&plz={plz}&ortsname={ortsname}&gemeindeschluessel={gemeindeschluessel}")
  @Headers({
    "Accept: application/json",
  })
  ApiResponse<MuenchenAdresse> checkAdresseWithHttpInfo(@Param("adresse") String adresse, @Param("strassenname") String strassenname, @Param("strasseId") Integer strasseId, @Param("hausnummer") String hausnummer, @Param("zusatz") String zusatz, @Param("plz") String plz, @Param("ortsname") String ortsname, @Param("gemeindeschluessel") String gemeindeschluessel);


  /**
   * Prüft, ob die angegebene Adresse existiert. Die Pflichtwerte können entweder: 1. komplett mittels adresse-Parameter, Hausnummer und Buchstabe ohne Leerzeichen dazwischen, angegeben werden oder 2. als Einzelwerte über die Parameter strassenname/strassenschluessel, hausnummer, zusatz angegeben werden 
   * 
   * Note, this is equivalent to the other <code>checkAdresse</code> method,
   * but with the query parameters collected into a single Map parameter. This
   * is convenient for services with optional query parameters, especially when
   * used with the {@link CheckAdresseQueryParams} class that allows for
   * building up this map in a fluent style.
   * @param queryParams Map of query parameters as name-value pairs
   *   <p>The following elements may be specified in the query map:</p>
   *   <ul>
   *   <li>adresse - Amtliche Schreibweise des Straßennamens mit Hausnummer und ggf. Zusatz (Pflichtfeld, wenn Straßenname/Straßenschluessel + Hausnummer nicht gesetzt sind). Optional Postleitzahl und Ort  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: entweder &#39;Marsstraße 4a&#39; oder &#39;Marsstraße 4a, 80638 München&#39; (optional)</li>
   *   <li>strassenname - Amtliche Schreibweise des Straßennamens &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)</li>
   *   <li>strasseId - StrasseId &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (optional)</li>
   *   <li>hausnummer - Hausnummer der Straße &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;4&#39; (optional)</li>
   *   <li>zusatz - Hausnummerzusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;a&#39; (optional)</li>
   *   <li>plz - Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80638&#39; (optional)</li>
   *   <li>ortsname - Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;München&#39; (optional)</li>
   *   <li>gemeindeschluessel - Optional: Gemeindeschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;09162000&#39; (optional)</li>
   *   </ul>
   * @return MuenchenAdresse
   */
  @RequestLine("GET /v2/adresse/check?adresse={adresse}&strassenname={strassenname}&strasseId={strasseId}&hausnummer={hausnummer}&zusatz={zusatz}&plz={plz}&ortsname={ortsname}&gemeindeschluessel={gemeindeschluessel}")
  @Headers({
  "Accept: application/json",
  })
  MuenchenAdresse checkAdresse(@QueryMap(encoded=true) CheckAdresseQueryParams queryParams);

  /**
  * Prüft, ob die angegebene Adresse existiert. Die Pflichtwerte können entweder: 1. komplett mittels adresse-Parameter, Hausnummer und Buchstabe ohne Leerzeichen dazwischen, angegeben werden oder 2. als Einzelwerte über die Parameter strassenname/strassenschluessel, hausnummer, zusatz angegeben werden 
  * 
  * Note, this is equivalent to the other <code>checkAdresse</code> that receives the query parameters as a map,
  * but this one also exposes the Http response headers
      * @param queryParams Map of query parameters as name-value pairs
      *   <p>The following elements may be specified in the query map:</p>
      *   <ul>
          *   <li>adresse - Amtliche Schreibweise des Straßennamens mit Hausnummer und ggf. Zusatz (Pflichtfeld, wenn Straßenname/Straßenschluessel + Hausnummer nicht gesetzt sind). Optional Postleitzahl und Ort  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: entweder &#39;Marsstraße 4a&#39; oder &#39;Marsstraße 4a, 80638 München&#39; (optional)</li>
          *   <li>strassenname - Amtliche Schreibweise des Straßennamens &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)</li>
          *   <li>strasseId - StrasseId &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (optional)</li>
          *   <li>hausnummer - Hausnummer der Straße &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;4&#39; (optional)</li>
          *   <li>zusatz - Hausnummerzusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;a&#39; (optional)</li>
          *   <li>plz - Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80638&#39; (optional)</li>
          *   <li>ortsname - Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;München&#39; (optional)</li>
          *   <li>gemeindeschluessel - Optional: Gemeindeschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;09162000&#39; (optional)</li>
      *   </ul>
          * @return MuenchenAdresse
      */
      @RequestLine("GET /v2/adresse/check?adresse={adresse}&strassenname={strassenname}&strasseId={strasseId}&hausnummer={hausnummer}&zusatz={zusatz}&plz={plz}&ortsname={ortsname}&gemeindeschluessel={gemeindeschluessel}")
      @Headers({
    "Accept: application/json",
      })
   ApiResponse<MuenchenAdresse> checkAdresseWithHttpInfo(@QueryMap(encoded=true) CheckAdresseQueryParams queryParams);


   /**
   * A convenience class for generating query parameters for the
   * <code>checkAdresse</code> method in a fluent style.
   */
  public static class CheckAdresseQueryParams extends HashMap<String, Object> {
    public CheckAdresseQueryParams adresse(final String value) {
      put("adresse", EncodingUtils.encode(value));
      return this;
    }
    public CheckAdresseQueryParams strassenname(final String value) {
      put("strassenname", EncodingUtils.encode(value));
      return this;
    }
    public CheckAdresseQueryParams strasseId(final Integer value) {
      put("strasseId", EncodingUtils.encode(value));
      return this;
    }
    public CheckAdresseQueryParams hausnummer(final String value) {
      put("hausnummer", EncodingUtils.encode(value));
      return this;
    }
    public CheckAdresseQueryParams zusatz(final String value) {
      put("zusatz", EncodingUtils.encode(value));
      return this;
    }
    public CheckAdresseQueryParams plz(final String value) {
      put("plz", EncodingUtils.encode(value));
      return this;
    }
    public CheckAdresseQueryParams ortsname(final String value) {
      put("ortsname", EncodingUtils.encode(value));
      return this;
    }
    public CheckAdresseQueryParams gemeindeschluessel(final String value) {
      put("gemeindeschluessel", EncodingUtils.encode(value));
      return this;
    }
  }

  /**
   * Prüft, ob die angegebene Adresse existiert. Die Pflichtwerte können entweder: 1. komplett mittels adresse-Parameter, Hausnummer und Buchstabe ohne Leerzeichen dazwischen angegeben werden oder 2. als Einzelwerte über die Parameter strassenname/strassenschluessel, hausnummer, zusatz angegeben werden 
   * 
   * @param adresse Amtliche Schreibweise des Straßennamens mit Hausnummer und ggf. Zusatz (Pflichtfeld, wenn Straßenname/Straßenschluessel + Hausnummer nicht gesetzt sind). &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße 4a&#39; (optional)
   * @param strassenname Amtliche Schreibweise des Straßennamens &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)
   * @param strassenschluessel Straßenschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (optional)
   * @param hausnummer Hausnummer der Straße &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;4&#39; (optional)
   * @param zusatz Hausnummerzusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;a&#39; (optional)
   * @param plz Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80638&#39; (optional)
   * @param ortsname Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;München&#39; (optional)
   * @param gemeindeschluessel Optional: Gemeindeschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;09162000&#39; (optional)
   * @return Adresse
   */
  @RequestLine("GET /adresse/check?adresse={adresse}&strassenname={strassenname}&strassenschluessel={strassenschluessel}&hausnummer={hausnummer}&zusatz={zusatz}&plz={plz}&ortsname={ortsname}&gemeindeschluessel={gemeindeschluessel}")
  @Headers({
    "Accept: application/json",
  })
  Adresse checkAdresse1(@Param("adresse") String adresse, @Param("strassenname") String strassenname, @Param("strassenschluessel") Integer strassenschluessel, @Param("hausnummer") String hausnummer, @Param("zusatz") String zusatz, @Param("plz") String plz, @Param("ortsname") String ortsname, @Param("gemeindeschluessel") String gemeindeschluessel);

  /**
   * Prüft, ob die angegebene Adresse existiert. Die Pflichtwerte können entweder: 1. komplett mittels adresse-Parameter, Hausnummer und Buchstabe ohne Leerzeichen dazwischen angegeben werden oder 2. als Einzelwerte über die Parameter strassenname/strassenschluessel, hausnummer, zusatz angegeben werden 
   * Similar to <code>checkAdresse1</code> but it also returns the http response headers .
   * 
   * @param adresse Amtliche Schreibweise des Straßennamens mit Hausnummer und ggf. Zusatz (Pflichtfeld, wenn Straßenname/Straßenschluessel + Hausnummer nicht gesetzt sind). &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße 4a&#39; (optional)
   * @param strassenname Amtliche Schreibweise des Straßennamens &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)
   * @param strassenschluessel Straßenschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (optional)
   * @param hausnummer Hausnummer der Straße &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;4&#39; (optional)
   * @param zusatz Hausnummerzusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;a&#39; (optional)
   * @param plz Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80638&#39; (optional)
   * @param ortsname Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;München&#39; (optional)
   * @param gemeindeschluessel Optional: Gemeindeschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;09162000&#39; (optional)
   * @return A ApiResponse that wraps the response boyd and the http headers.
   */
  @RequestLine("GET /adresse/check?adresse={adresse}&strassenname={strassenname}&strassenschluessel={strassenschluessel}&hausnummer={hausnummer}&zusatz={zusatz}&plz={plz}&ortsname={ortsname}&gemeindeschluessel={gemeindeschluessel}")
  @Headers({
    "Accept: application/json",
  })
  ApiResponse<Adresse> checkAdresse1WithHttpInfo(@Param("adresse") String adresse, @Param("strassenname") String strassenname, @Param("strassenschluessel") Integer strassenschluessel, @Param("hausnummer") String hausnummer, @Param("zusatz") String zusatz, @Param("plz") String plz, @Param("ortsname") String ortsname, @Param("gemeindeschluessel") String gemeindeschluessel);


  /**
   * Prüft, ob die angegebene Adresse existiert. Die Pflichtwerte können entweder: 1. komplett mittels adresse-Parameter, Hausnummer und Buchstabe ohne Leerzeichen dazwischen angegeben werden oder 2. als Einzelwerte über die Parameter strassenname/strassenschluessel, hausnummer, zusatz angegeben werden 
   * 
   * Note, this is equivalent to the other <code>checkAdresse1</code> method,
   * but with the query parameters collected into a single Map parameter. This
   * is convenient for services with optional query parameters, especially when
   * used with the {@link CheckAdresse1QueryParams} class that allows for
   * building up this map in a fluent style.
   * @param queryParams Map of query parameters as name-value pairs
   *   <p>The following elements may be specified in the query map:</p>
   *   <ul>
   *   <li>adresse - Amtliche Schreibweise des Straßennamens mit Hausnummer und ggf. Zusatz (Pflichtfeld, wenn Straßenname/Straßenschluessel + Hausnummer nicht gesetzt sind). &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße 4a&#39; (optional)</li>
   *   <li>strassenname - Amtliche Schreibweise des Straßennamens &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)</li>
   *   <li>strassenschluessel - Straßenschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (optional)</li>
   *   <li>hausnummer - Hausnummer der Straße &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;4&#39; (optional)</li>
   *   <li>zusatz - Hausnummerzusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;a&#39; (optional)</li>
   *   <li>plz - Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80638&#39; (optional)</li>
   *   <li>ortsname - Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;München&#39; (optional)</li>
   *   <li>gemeindeschluessel - Optional: Gemeindeschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;09162000&#39; (optional)</li>
   *   </ul>
   * @return Adresse
   */
  @RequestLine("GET /adresse/check?adresse={adresse}&strassenname={strassenname}&strassenschluessel={strassenschluessel}&hausnummer={hausnummer}&zusatz={zusatz}&plz={plz}&ortsname={ortsname}&gemeindeschluessel={gemeindeschluessel}")
  @Headers({
  "Accept: application/json",
  })
  Adresse checkAdresse1(@QueryMap(encoded=true) CheckAdresse1QueryParams queryParams);

  /**
  * Prüft, ob die angegebene Adresse existiert. Die Pflichtwerte können entweder: 1. komplett mittels adresse-Parameter, Hausnummer und Buchstabe ohne Leerzeichen dazwischen angegeben werden oder 2. als Einzelwerte über die Parameter strassenname/strassenschluessel, hausnummer, zusatz angegeben werden 
  * 
  * Note, this is equivalent to the other <code>checkAdresse1</code> that receives the query parameters as a map,
  * but this one also exposes the Http response headers
      * @param queryParams Map of query parameters as name-value pairs
      *   <p>The following elements may be specified in the query map:</p>
      *   <ul>
          *   <li>adresse - Amtliche Schreibweise des Straßennamens mit Hausnummer und ggf. Zusatz (Pflichtfeld, wenn Straßenname/Straßenschluessel + Hausnummer nicht gesetzt sind). &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße 4a&#39; (optional)</li>
          *   <li>strassenname - Amtliche Schreibweise des Straßennamens &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)</li>
          *   <li>strassenschluessel - Straßenschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 3013 (optional)</li>
          *   <li>hausnummer - Hausnummer der Straße &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;4&#39; (optional)</li>
          *   <li>zusatz - Hausnummerzusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;a&#39; (optional)</li>
          *   <li>plz - Optional: Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80638&#39; (optional)</li>
          *   <li>ortsname - Optional: Ortsname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;München&#39; (optional)</li>
          *   <li>gemeindeschluessel - Optional: Gemeindeschlüssel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;09162000&#39; (optional)</li>
      *   </ul>
          * @return Adresse
      */
      @RequestLine("GET /adresse/check?adresse={adresse}&strassenname={strassenname}&strassenschluessel={strassenschluessel}&hausnummer={hausnummer}&zusatz={zusatz}&plz={plz}&ortsname={ortsname}&gemeindeschluessel={gemeindeschluessel}")
      @Headers({
    "Accept: application/json",
      })
   ApiResponse<Adresse> checkAdresse1WithHttpInfo(@QueryMap(encoded=true) CheckAdresse1QueryParams queryParams);


   /**
   * A convenience class for generating query parameters for the
   * <code>checkAdresse1</code> method in a fluent style.
   */
  public static class CheckAdresse1QueryParams extends HashMap<String, Object> {
    public CheckAdresse1QueryParams adresse(final String value) {
      put("adresse", EncodingUtils.encode(value));
      return this;
    }
    public CheckAdresse1QueryParams strassenname(final String value) {
      put("strassenname", EncodingUtils.encode(value));
      return this;
    }
    public CheckAdresse1QueryParams strassenschluessel(final Integer value) {
      put("strassenschluessel", EncodingUtils.encode(value));
      return this;
    }
    public CheckAdresse1QueryParams hausnummer(final String value) {
      put("hausnummer", EncodingUtils.encode(value));
      return this;
    }
    public CheckAdresse1QueryParams zusatz(final String value) {
      put("zusatz", EncodingUtils.encode(value));
      return this;
    }
    public CheckAdresse1QueryParams plz(final String value) {
      put("plz", EncodingUtils.encode(value));
      return this;
    }
    public CheckAdresse1QueryParams ortsname(final String value) {
      put("ortsname", EncodingUtils.encode(value));
      return this;
    }
    public CheckAdresse1QueryParams gemeindeschluessel(final String value) {
      put("gemeindeschluessel", EncodingUtils.encode(value));
      return this;
    }
  }

  /**
   * Liefert alle Adressen, die mit den angegebenen Gebietszuordnungen übereinstimmen.
   * 
   * @param baublock Baublock &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3.3.22&#39; (optional)
   * @param erhaltungssatzung Erhaltungssatzung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;nein&#39; (optional)
   * @param gemarkung Gemarkung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;8687&#39; (optional)
   * @param kaminkehrerbezirk Kaminkehrerbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;26&#39; (optional)
   * @param plz Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80807&#39; (optional)
   * @param mittelschule Mittelschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2274&#39; (optional)
   * @param grundschule Grundschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2273&#39; (optional)
   * @param polizeiinspektion Polizeiinspektion &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;47&#39; (optional)
   * @param stimmbezirk Stimmbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1 (optional)
   * @param stimmkreis Stimmkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 104 (optional)
   * @param wahlbezirk Wahlbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1171 (optional)
   * @param wahlkreis Wahlkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 217 (optional)
   * @param stadtbezirk Stadtbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11&#39; (optional)
   * @param stadtbezirksteil Stadtbezirksteil &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3&#39; (optional)
   * @param stadtbezirksviertel Stadtbezirksviertel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3.3&#39; (optional)
   * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return MuenchenAdresseResponse
   */
  @RequestLine("GET /v2/adresse/list?baublock={baublock}&erhaltungssatzung={erhaltungssatzung}&gemarkung={gemarkung}&kaminkehrerbezirk={kaminkehrerbezirk}&plz={plz}&mittelschule={mittelschule}&grundschule={grundschule}&polizeiinspektion={polizeiinspektion}&stimmbezirk={stimmbezirk}&stimmkreis={stimmkreis}&wahlbezirk={wahlbezirk}&wahlkreis={wahlkreis}&stadtbezirk={stadtbezirk}&stadtbezirksteil={stadtbezirksteil}&stadtbezirksviertel={stadtbezirksviertel}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  MuenchenAdresseResponse listAdressen(@Param("baublock") List<String> baublock, @Param("erhaltungssatzung") List<String> erhaltungssatzung, @Param("gemarkung") List<String> gemarkung, @Param("kaminkehrerbezirk") List<String> kaminkehrerbezirk, @Param("plz") List<String> plz, @Param("mittelschule") List<String> mittelschule, @Param("grundschule") List<String> grundschule, @Param("polizeiinspektion") List<String> polizeiinspektion, @Param("stimmbezirk") List<Long> stimmbezirk, @Param("stimmkreis") List<Long> stimmkreis, @Param("wahlbezirk") List<Long> wahlbezirk, @Param("wahlkreis") List<Long> wahlkreis, @Param("stadtbezirk") List<String> stadtbezirk, @Param("stadtbezirksteil") List<String> stadtbezirksteil, @Param("stadtbezirksviertel") List<String> stadtbezirksviertel, @Param("sort") String sort, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);

  /**
   * Liefert alle Adressen, die mit den angegebenen Gebietszuordnungen übereinstimmen.
   * Similar to <code>listAdressen</code> but it also returns the http response headers .
   * 
   * @param baublock Baublock &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3.3.22&#39; (optional)
   * @param erhaltungssatzung Erhaltungssatzung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;nein&#39; (optional)
   * @param gemarkung Gemarkung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;8687&#39; (optional)
   * @param kaminkehrerbezirk Kaminkehrerbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;26&#39; (optional)
   * @param plz Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80807&#39; (optional)
   * @param mittelschule Mittelschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2274&#39; (optional)
   * @param grundschule Grundschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2273&#39; (optional)
   * @param polizeiinspektion Polizeiinspektion &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;47&#39; (optional)
   * @param stimmbezirk Stimmbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1 (optional)
   * @param stimmkreis Stimmkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 104 (optional)
   * @param wahlbezirk Wahlbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1171 (optional)
   * @param wahlkreis Wahlkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 217 (optional)
   * @param stadtbezirk Stadtbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11&#39; (optional)
   * @param stadtbezirksteil Stadtbezirksteil &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3&#39; (optional)
   * @param stadtbezirksviertel Stadtbezirksviertel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3.3&#39; (optional)
   * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return A ApiResponse that wraps the response boyd and the http headers.
   */
  @RequestLine("GET /v2/adresse/list?baublock={baublock}&erhaltungssatzung={erhaltungssatzung}&gemarkung={gemarkung}&kaminkehrerbezirk={kaminkehrerbezirk}&plz={plz}&mittelschule={mittelschule}&grundschule={grundschule}&polizeiinspektion={polizeiinspektion}&stimmbezirk={stimmbezirk}&stimmkreis={stimmkreis}&wahlbezirk={wahlbezirk}&wahlkreis={wahlkreis}&stadtbezirk={stadtbezirk}&stadtbezirksteil={stadtbezirksteil}&stadtbezirksviertel={stadtbezirksviertel}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  ApiResponse<MuenchenAdresseResponse> listAdressenWithHttpInfo(@Param("baublock") List<String> baublock, @Param("erhaltungssatzung") List<String> erhaltungssatzung, @Param("gemarkung") List<String> gemarkung, @Param("kaminkehrerbezirk") List<String> kaminkehrerbezirk, @Param("plz") List<String> plz, @Param("mittelschule") List<String> mittelschule, @Param("grundschule") List<String> grundschule, @Param("polizeiinspektion") List<String> polizeiinspektion, @Param("stimmbezirk") List<Long> stimmbezirk, @Param("stimmkreis") List<Long> stimmkreis, @Param("wahlbezirk") List<Long> wahlbezirk, @Param("wahlkreis") List<Long> wahlkreis, @Param("stadtbezirk") List<String> stadtbezirk, @Param("stadtbezirksteil") List<String> stadtbezirksteil, @Param("stadtbezirksviertel") List<String> stadtbezirksviertel, @Param("sort") String sort, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);


  /**
   * Liefert alle Adressen, die mit den angegebenen Gebietszuordnungen übereinstimmen.
   * 
   * Note, this is equivalent to the other <code>listAdressen</code> method,
   * but with the query parameters collected into a single Map parameter. This
   * is convenient for services with optional query parameters, especially when
   * used with the {@link ListAdressenQueryParams} class that allows for
   * building up this map in a fluent style.
   * @param queryParams Map of query parameters as name-value pairs
   *   <p>The following elements may be specified in the query map:</p>
   *   <ul>
   *   <li>baublock - Baublock &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3.3.22&#39; (optional)</li>
   *   <li>erhaltungssatzung - Erhaltungssatzung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;nein&#39; (optional)</li>
   *   <li>gemarkung - Gemarkung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;8687&#39; (optional)</li>
   *   <li>kaminkehrerbezirk - Kaminkehrerbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;26&#39; (optional)</li>
   *   <li>plz - Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80807&#39; (optional)</li>
   *   <li>mittelschule - Mittelschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2274&#39; (optional)</li>
   *   <li>grundschule - Grundschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2273&#39; (optional)</li>
   *   <li>polizeiinspektion - Polizeiinspektion &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;47&#39; (optional)</li>
   *   <li>stimmbezirk - Stimmbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1 (optional)</li>
   *   <li>stimmkreis - Stimmkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 104 (optional)</li>
   *   <li>wahlbezirk - Wahlbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1171 (optional)</li>
   *   <li>wahlkreis - Wahlkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 217 (optional)</li>
   *   <li>stadtbezirk - Stadtbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11&#39; (optional)</li>
   *   <li>stadtbezirksteil - Stadtbezirksteil &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3&#39; (optional)</li>
   *   <li>stadtbezirksviertel - Stadtbezirksviertel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3.3&#39; (optional)</li>
   *   <li>sort - Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)</li>
   *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
   *   <li>page - Seitennummer (optional, default to 0)</li>
   *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
   *   </ul>
   * @return MuenchenAdresseResponse
   */
  @RequestLine("GET /v2/adresse/list?baublock={baublock}&erhaltungssatzung={erhaltungssatzung}&gemarkung={gemarkung}&kaminkehrerbezirk={kaminkehrerbezirk}&plz={plz}&mittelschule={mittelschule}&grundschule={grundschule}&polizeiinspektion={polizeiinspektion}&stimmbezirk={stimmbezirk}&stimmkreis={stimmkreis}&wahlbezirk={wahlbezirk}&wahlkreis={wahlkreis}&stadtbezirk={stadtbezirk}&stadtbezirksteil={stadtbezirksteil}&stadtbezirksviertel={stadtbezirksviertel}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
  "Accept: application/json",
  })
  MuenchenAdresseResponse listAdressen(@QueryMap(encoded=true) ListAdressenQueryParams queryParams);

  /**
  * Liefert alle Adressen, die mit den angegebenen Gebietszuordnungen übereinstimmen.
  * 
  * Note, this is equivalent to the other <code>listAdressen</code> that receives the query parameters as a map,
  * but this one also exposes the Http response headers
      * @param queryParams Map of query parameters as name-value pairs
      *   <p>The following elements may be specified in the query map:</p>
      *   <ul>
          *   <li>baublock - Baublock &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3.3.22&#39; (optional)</li>
          *   <li>erhaltungssatzung - Erhaltungssatzung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;nein&#39; (optional)</li>
          *   <li>gemarkung - Gemarkung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;8687&#39; (optional)</li>
          *   <li>kaminkehrerbezirk - Kaminkehrerbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;26&#39; (optional)</li>
          *   <li>plz - Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80807&#39; (optional)</li>
          *   <li>mittelschule - Mittelschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2274&#39; (optional)</li>
          *   <li>grundschule - Grundschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2273&#39; (optional)</li>
          *   <li>polizeiinspektion - Polizeiinspektion &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;47&#39; (optional)</li>
          *   <li>stimmbezirk - Stimmbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1 (optional)</li>
          *   <li>stimmkreis - Stimmkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 104 (optional)</li>
          *   <li>wahlbezirk - Wahlbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1171 (optional)</li>
          *   <li>wahlkreis - Wahlkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 217 (optional)</li>
          *   <li>stadtbezirk - Stadtbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11&#39; (optional)</li>
          *   <li>stadtbezirksteil - Stadtbezirksteil &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3&#39; (optional)</li>
          *   <li>stadtbezirksviertel - Stadtbezirksviertel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3.3&#39; (optional)</li>
          *   <li>sort - Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)</li>
          *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
          *   <li>page - Seitennummer (optional, default to 0)</li>
          *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
      *   </ul>
          * @return MuenchenAdresseResponse
      */
      @RequestLine("GET /v2/adresse/list?baublock={baublock}&erhaltungssatzung={erhaltungssatzung}&gemarkung={gemarkung}&kaminkehrerbezirk={kaminkehrerbezirk}&plz={plz}&mittelschule={mittelschule}&grundschule={grundschule}&polizeiinspektion={polizeiinspektion}&stimmbezirk={stimmbezirk}&stimmkreis={stimmkreis}&wahlbezirk={wahlbezirk}&wahlkreis={wahlkreis}&stadtbezirk={stadtbezirk}&stadtbezirksteil={stadtbezirksteil}&stadtbezirksviertel={stadtbezirksviertel}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
      @Headers({
    "Accept: application/json",
      })
   ApiResponse<MuenchenAdresseResponse> listAdressenWithHttpInfo(@QueryMap(encoded=true) ListAdressenQueryParams queryParams);


   /**
   * A convenience class for generating query parameters for the
   * <code>listAdressen</code> method in a fluent style.
   */
  public static class ListAdressenQueryParams extends HashMap<String, Object> {
    public ListAdressenQueryParams baublock(final List<String> value) {
      put("baublock", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressenQueryParams erhaltungssatzung(final List<String> value) {
      put("erhaltungssatzung", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressenQueryParams gemarkung(final List<String> value) {
      put("gemarkung", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressenQueryParams kaminkehrerbezirk(final List<String> value) {
      put("kaminkehrerbezirk", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressenQueryParams plz(final List<String> value) {
      put("plz", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressenQueryParams mittelschule(final List<String> value) {
      put("mittelschule", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressenQueryParams grundschule(final List<String> value) {
      put("grundschule", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressenQueryParams polizeiinspektion(final List<String> value) {
      put("polizeiinspektion", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressenQueryParams stimmbezirk(final List<Long> value) {
      put("stimmbezirk", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressenQueryParams stimmkreis(final List<Long> value) {
      put("stimmkreis", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressenQueryParams wahlbezirk(final List<Long> value) {
      put("wahlbezirk", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressenQueryParams wahlkreis(final List<Long> value) {
      put("wahlkreis", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressenQueryParams stadtbezirk(final List<String> value) {
      put("stadtbezirk", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressenQueryParams stadtbezirksteil(final List<String> value) {
      put("stadtbezirksteil", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressenQueryParams stadtbezirksviertel(final List<String> value) {
      put("stadtbezirksviertel", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressenQueryParams sort(final String value) {
      put("sort", EncodingUtils.encode(value));
      return this;
    }
    public ListAdressenQueryParams sortdir(final String value) {
      put("sortdir", EncodingUtils.encode(value));
      return this;
    }
    public ListAdressenQueryParams page(final Integer value) {
      put("page", EncodingUtils.encode(value));
      return this;
    }
    public ListAdressenQueryParams pagesize(final Integer value) {
      put("pagesize", EncodingUtils.encode(value));
      return this;
    }
  }

  /**
   * Liefert alle Adressen, die mit den angegebenen Gebietszuordnungen übereinstimmen.
   * 
   * @param baublock Baublock &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3.3.22&#39; (optional)
   * @param erhaltungssatzung Erhaltungssatzung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;nein&#39; (optional)
   * @param gemarkung Gemarkung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;8687&#39; (optional)
   * @param kaminkehrerbezirk Kaminkehrerbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;26&#39; (optional)
   * @param plz Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80807&#39; (optional)
   * @param mittelschule Mittelschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2274&#39; (optional)
   * @param grundschule Grundschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2273&#39; (optional)
   * @param polizeiinspektion Polizeiinspektion &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;47&#39; (optional)
   * @param stimmbezirk Stimmbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1 (optional)
   * @param stimmkreis Stimmkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 104 (optional)
   * @param wahlbezirk Wahlbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1171 (optional)
   * @param wahlkreis Wahlkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 217 (optional)
   * @param stadtbezirk Stadtbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11&#39; (optional)
   * @param stadtbezirksteil Stadtbezirksteil &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3&#39; (optional)
   * @param stadtbezirksviertel Stadtbezirksviertel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3.3&#39; (optional)
   * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return AdresseResponse
   */
  @RequestLine("GET /adresse/list?baublock={baublock}&erhaltungssatzung={erhaltungssatzung}&gemarkung={gemarkung}&kaminkehrerbezirk={kaminkehrerbezirk}&plz={plz}&mittelschule={mittelschule}&grundschule={grundschule}&polizeiinspektion={polizeiinspektion}&stimmbezirk={stimmbezirk}&stimmkreis={stimmkreis}&wahlbezirk={wahlbezirk}&wahlkreis={wahlkreis}&stadtbezirk={stadtbezirk}&stadtbezirksteil={stadtbezirksteil}&stadtbezirksviertel={stadtbezirksviertel}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  AdresseResponse listAdressen1(@Param("baublock") List<String> baublock, @Param("erhaltungssatzung") List<String> erhaltungssatzung, @Param("gemarkung") List<String> gemarkung, @Param("kaminkehrerbezirk") List<String> kaminkehrerbezirk, @Param("plz") List<String> plz, @Param("mittelschule") List<String> mittelschule, @Param("grundschule") List<String> grundschule, @Param("polizeiinspektion") List<String> polizeiinspektion, @Param("stimmbezirk") List<Long> stimmbezirk, @Param("stimmkreis") List<Long> stimmkreis, @Param("wahlbezirk") List<Long> wahlbezirk, @Param("wahlkreis") List<Long> wahlkreis, @Param("stadtbezirk") List<String> stadtbezirk, @Param("stadtbezirksteil") List<String> stadtbezirksteil, @Param("stadtbezirksviertel") List<String> stadtbezirksviertel, @Param("sort") String sort, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);

  /**
   * Liefert alle Adressen, die mit den angegebenen Gebietszuordnungen übereinstimmen.
   * Similar to <code>listAdressen1</code> but it also returns the http response headers .
   * 
   * @param baublock Baublock &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3.3.22&#39; (optional)
   * @param erhaltungssatzung Erhaltungssatzung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;nein&#39; (optional)
   * @param gemarkung Gemarkung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;8687&#39; (optional)
   * @param kaminkehrerbezirk Kaminkehrerbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;26&#39; (optional)
   * @param plz Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80807&#39; (optional)
   * @param mittelschule Mittelschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2274&#39; (optional)
   * @param grundschule Grundschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2273&#39; (optional)
   * @param polizeiinspektion Polizeiinspektion &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;47&#39; (optional)
   * @param stimmbezirk Stimmbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1 (optional)
   * @param stimmkreis Stimmkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 104 (optional)
   * @param wahlbezirk Wahlbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1171 (optional)
   * @param wahlkreis Wahlkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 217 (optional)
   * @param stadtbezirk Stadtbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11&#39; (optional)
   * @param stadtbezirksteil Stadtbezirksteil &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3&#39; (optional)
   * @param stadtbezirksviertel Stadtbezirksviertel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3.3&#39; (optional)
   * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return A ApiResponse that wraps the response boyd and the http headers.
   */
  @RequestLine("GET /adresse/list?baublock={baublock}&erhaltungssatzung={erhaltungssatzung}&gemarkung={gemarkung}&kaminkehrerbezirk={kaminkehrerbezirk}&plz={plz}&mittelschule={mittelschule}&grundschule={grundschule}&polizeiinspektion={polizeiinspektion}&stimmbezirk={stimmbezirk}&stimmkreis={stimmkreis}&wahlbezirk={wahlbezirk}&wahlkreis={wahlkreis}&stadtbezirk={stadtbezirk}&stadtbezirksteil={stadtbezirksteil}&stadtbezirksviertel={stadtbezirksviertel}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  ApiResponse<AdresseResponse> listAdressen1WithHttpInfo(@Param("baublock") List<String> baublock, @Param("erhaltungssatzung") List<String> erhaltungssatzung, @Param("gemarkung") List<String> gemarkung, @Param("kaminkehrerbezirk") List<String> kaminkehrerbezirk, @Param("plz") List<String> plz, @Param("mittelschule") List<String> mittelschule, @Param("grundschule") List<String> grundschule, @Param("polizeiinspektion") List<String> polizeiinspektion, @Param("stimmbezirk") List<Long> stimmbezirk, @Param("stimmkreis") List<Long> stimmkreis, @Param("wahlbezirk") List<Long> wahlbezirk, @Param("wahlkreis") List<Long> wahlkreis, @Param("stadtbezirk") List<String> stadtbezirk, @Param("stadtbezirksteil") List<String> stadtbezirksteil, @Param("stadtbezirksviertel") List<String> stadtbezirksviertel, @Param("sort") String sort, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);


  /**
   * Liefert alle Adressen, die mit den angegebenen Gebietszuordnungen übereinstimmen.
   * 
   * Note, this is equivalent to the other <code>listAdressen1</code> method,
   * but with the query parameters collected into a single Map parameter. This
   * is convenient for services with optional query parameters, especially when
   * used with the {@link ListAdressen1QueryParams} class that allows for
   * building up this map in a fluent style.
   * @param queryParams Map of query parameters as name-value pairs
   *   <p>The following elements may be specified in the query map:</p>
   *   <ul>
   *   <li>baublock - Baublock &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3.3.22&#39; (optional)</li>
   *   <li>erhaltungssatzung - Erhaltungssatzung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;nein&#39; (optional)</li>
   *   <li>gemarkung - Gemarkung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;8687&#39; (optional)</li>
   *   <li>kaminkehrerbezirk - Kaminkehrerbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;26&#39; (optional)</li>
   *   <li>plz - Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80807&#39; (optional)</li>
   *   <li>mittelschule - Mittelschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2274&#39; (optional)</li>
   *   <li>grundschule - Grundschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2273&#39; (optional)</li>
   *   <li>polizeiinspektion - Polizeiinspektion &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;47&#39; (optional)</li>
   *   <li>stimmbezirk - Stimmbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1 (optional)</li>
   *   <li>stimmkreis - Stimmkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 104 (optional)</li>
   *   <li>wahlbezirk - Wahlbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1171 (optional)</li>
   *   <li>wahlkreis - Wahlkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 217 (optional)</li>
   *   <li>stadtbezirk - Stadtbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11&#39; (optional)</li>
   *   <li>stadtbezirksteil - Stadtbezirksteil &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3&#39; (optional)</li>
   *   <li>stadtbezirksviertel - Stadtbezirksviertel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3.3&#39; (optional)</li>
   *   <li>sort - Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)</li>
   *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
   *   <li>page - Seitennummer (optional, default to 0)</li>
   *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
   *   </ul>
   * @return AdresseResponse
   */
  @RequestLine("GET /adresse/list?baublock={baublock}&erhaltungssatzung={erhaltungssatzung}&gemarkung={gemarkung}&kaminkehrerbezirk={kaminkehrerbezirk}&plz={plz}&mittelschule={mittelschule}&grundschule={grundschule}&polizeiinspektion={polizeiinspektion}&stimmbezirk={stimmbezirk}&stimmkreis={stimmkreis}&wahlbezirk={wahlbezirk}&wahlkreis={wahlkreis}&stadtbezirk={stadtbezirk}&stadtbezirksteil={stadtbezirksteil}&stadtbezirksviertel={stadtbezirksviertel}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
  "Accept: application/json",
  })
  AdresseResponse listAdressen1(@QueryMap(encoded=true) ListAdressen1QueryParams queryParams);

  /**
  * Liefert alle Adressen, die mit den angegebenen Gebietszuordnungen übereinstimmen.
  * 
  * Note, this is equivalent to the other <code>listAdressen1</code> that receives the query parameters as a map,
  * but this one also exposes the Http response headers
      * @param queryParams Map of query parameters as name-value pairs
      *   <p>The following elements may be specified in the query map:</p>
      *   <ul>
          *   <li>baublock - Baublock &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3.3.22&#39; (optional)</li>
          *   <li>erhaltungssatzung - Erhaltungssatzung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;nein&#39; (optional)</li>
          *   <li>gemarkung - Gemarkung &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;8687&#39; (optional)</li>
          *   <li>kaminkehrerbezirk - Kaminkehrerbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;26&#39; (optional)</li>
          *   <li>plz - Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80807&#39; (optional)</li>
          *   <li>mittelschule - Mittelschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2274&#39; (optional)</li>
          *   <li>grundschule - Grundschule &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2273&#39; (optional)</li>
          *   <li>polizeiinspektion - Polizeiinspektion &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;47&#39; (optional)</li>
          *   <li>stimmbezirk - Stimmbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1 (optional)</li>
          *   <li>stimmkreis - Stimmkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 104 (optional)</li>
          *   <li>wahlbezirk - Wahlbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 1171 (optional)</li>
          *   <li>wahlkreis - Wahlkreis &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 217 (optional)</li>
          *   <li>stadtbezirk - Stadtbezirk &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11&#39; (optional)</li>
          *   <li>stadtbezirksteil - Stadtbezirksteil &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3&#39; (optional)</li>
          *   <li>stadtbezirksviertel - Stadtbezirksviertel &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;11.3.3&#39; (optional)</li>
          *   <li>sort - Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)</li>
          *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
          *   <li>page - Seitennummer (optional, default to 0)</li>
          *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
      *   </ul>
          * @return AdresseResponse
      */
      @RequestLine("GET /adresse/list?baublock={baublock}&erhaltungssatzung={erhaltungssatzung}&gemarkung={gemarkung}&kaminkehrerbezirk={kaminkehrerbezirk}&plz={plz}&mittelschule={mittelschule}&grundschule={grundschule}&polizeiinspektion={polizeiinspektion}&stimmbezirk={stimmbezirk}&stimmkreis={stimmkreis}&wahlbezirk={wahlbezirk}&wahlkreis={wahlkreis}&stadtbezirk={stadtbezirk}&stadtbezirksteil={stadtbezirksteil}&stadtbezirksviertel={stadtbezirksviertel}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
      @Headers({
    "Accept: application/json",
      })
   ApiResponse<AdresseResponse> listAdressen1WithHttpInfo(@QueryMap(encoded=true) ListAdressen1QueryParams queryParams);


   /**
   * A convenience class for generating query parameters for the
   * <code>listAdressen1</code> method in a fluent style.
   */
  public static class ListAdressen1QueryParams extends HashMap<String, Object> {
    public ListAdressen1QueryParams baublock(final List<String> value) {
      put("baublock", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressen1QueryParams erhaltungssatzung(final List<String> value) {
      put("erhaltungssatzung", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressen1QueryParams gemarkung(final List<String> value) {
      put("gemarkung", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressen1QueryParams kaminkehrerbezirk(final List<String> value) {
      put("kaminkehrerbezirk", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressen1QueryParams plz(final List<String> value) {
      put("plz", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressen1QueryParams mittelschule(final List<String> value) {
      put("mittelschule", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressen1QueryParams grundschule(final List<String> value) {
      put("grundschule", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressen1QueryParams polizeiinspektion(final List<String> value) {
      put("polizeiinspektion", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressen1QueryParams stimmbezirk(final List<Long> value) {
      put("stimmbezirk", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressen1QueryParams stimmkreis(final List<Long> value) {
      put("stimmkreis", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressen1QueryParams wahlbezirk(final List<Long> value) {
      put("wahlbezirk", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressen1QueryParams wahlkreis(final List<Long> value) {
      put("wahlkreis", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressen1QueryParams stadtbezirk(final List<String> value) {
      put("stadtbezirk", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressen1QueryParams stadtbezirksteil(final List<String> value) {
      put("stadtbezirksteil", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressen1QueryParams stadtbezirksviertel(final List<String> value) {
      put("stadtbezirksviertel", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public ListAdressen1QueryParams sort(final String value) {
      put("sort", EncodingUtils.encode(value));
      return this;
    }
    public ListAdressen1QueryParams sortdir(final String value) {
      put("sortdir", EncodingUtils.encode(value));
      return this;
    }
    public ListAdressen1QueryParams page(final Integer value) {
      put("page", EncodingUtils.encode(value));
      return this;
    }
    public ListAdressen1QueryParams pagesize(final Integer value) {
      put("pagesize", EncodingUtils.encode(value));
      return this;
    }
  }

  /**
   * Liefert alle Änderungen, die mit den angegebenen Datum übereinstimmen.
   * 
   * @param wirkungsdatumvon Von-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2022-01-01&#39; (optional)
   * @param wirkungsdatumbis Bis-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2022-01-14&#39; (optional)
   * @param strassenname Straßenname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)
   * @param hausnummer Hausnummer  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;4&#39; (optional)
   * @param plz Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80638&#39; (optional)
   * @param zusatz Zusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;a&#39; (optional)
   * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return AenderungResponse
   */
  @RequestLine("GET /v2/adresse/aenderung?wirkungsdatumvon={wirkungsdatumvon}&wirkungsdatumbis={wirkungsdatumbis}&strassenname={strassenname}&hausnummer={hausnummer}&plz={plz}&zusatz={zusatz}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  AenderungResponse listAenderungen(@Param("wirkungsdatumvon") Date wirkungsdatumvon, @Param("wirkungsdatumbis") Date wirkungsdatumbis, @Param("strassenname") String strassenname, @Param("hausnummer") Long hausnummer, @Param("plz") String plz, @Param("zusatz") String zusatz, @Param("sort") String sort, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);

  /**
   * Liefert alle Änderungen, die mit den angegebenen Datum übereinstimmen.
   * Similar to <code>listAenderungen</code> but it also returns the http response headers .
   * 
   * @param wirkungsdatumvon Von-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2022-01-01&#39; (optional)
   * @param wirkungsdatumbis Bis-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2022-01-14&#39; (optional)
   * @param strassenname Straßenname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)
   * @param hausnummer Hausnummer  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;4&#39; (optional)
   * @param plz Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80638&#39; (optional)
   * @param zusatz Zusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;a&#39; (optional)
   * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return A ApiResponse that wraps the response boyd and the http headers.
   */
  @RequestLine("GET /v2/adresse/aenderung?wirkungsdatumvon={wirkungsdatumvon}&wirkungsdatumbis={wirkungsdatumbis}&strassenname={strassenname}&hausnummer={hausnummer}&plz={plz}&zusatz={zusatz}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  ApiResponse<AenderungResponse> listAenderungenWithHttpInfo(@Param("wirkungsdatumvon") Date wirkungsdatumvon, @Param("wirkungsdatumbis") Date wirkungsdatumbis, @Param("strassenname") String strassenname, @Param("hausnummer") Long hausnummer, @Param("plz") String plz, @Param("zusatz") String zusatz, @Param("sort") String sort, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);


  /**
   * Liefert alle Änderungen, die mit den angegebenen Datum übereinstimmen.
   * 
   * Note, this is equivalent to the other <code>listAenderungen</code> method,
   * but with the query parameters collected into a single Map parameter. This
   * is convenient for services with optional query parameters, especially when
   * used with the {@link ListAenderungenQueryParams} class that allows for
   * building up this map in a fluent style.
   * @param queryParams Map of query parameters as name-value pairs
   *   <p>The following elements may be specified in the query map:</p>
   *   <ul>
   *   <li>wirkungsdatumvon - Von-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2022-01-01&#39; (optional)</li>
   *   <li>wirkungsdatumbis - Bis-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2022-01-14&#39; (optional)</li>
   *   <li>strassenname - Straßenname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)</li>
   *   <li>hausnummer - Hausnummer  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;4&#39; (optional)</li>
   *   <li>plz - Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80638&#39; (optional)</li>
   *   <li>zusatz - Zusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;a&#39; (optional)</li>
   *   <li>sort - Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)</li>
   *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
   *   <li>page - Seitennummer (optional, default to 0)</li>
   *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
   *   </ul>
   * @return AenderungResponse
   */
  @RequestLine("GET /v2/adresse/aenderung?wirkungsdatumvon={wirkungsdatumvon}&wirkungsdatumbis={wirkungsdatumbis}&strassenname={strassenname}&hausnummer={hausnummer}&plz={plz}&zusatz={zusatz}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
  "Accept: application/json",
  })
  AenderungResponse listAenderungen(@QueryMap(encoded=true) ListAenderungenQueryParams queryParams);

  /**
  * Liefert alle Änderungen, die mit den angegebenen Datum übereinstimmen.
  * 
  * Note, this is equivalent to the other <code>listAenderungen</code> that receives the query parameters as a map,
  * but this one also exposes the Http response headers
      * @param queryParams Map of query parameters as name-value pairs
      *   <p>The following elements may be specified in the query map:</p>
      *   <ul>
          *   <li>wirkungsdatumvon - Von-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2022-01-01&#39; (optional)</li>
          *   <li>wirkungsdatumbis - Bis-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2022-01-14&#39; (optional)</li>
          *   <li>strassenname - Straßenname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)</li>
          *   <li>hausnummer - Hausnummer  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;4&#39; (optional)</li>
          *   <li>plz - Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80638&#39; (optional)</li>
          *   <li>zusatz - Zusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;a&#39; (optional)</li>
          *   <li>sort - Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)</li>
          *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
          *   <li>page - Seitennummer (optional, default to 0)</li>
          *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
      *   </ul>
          * @return AenderungResponse
      */
      @RequestLine("GET /v2/adresse/aenderung?wirkungsdatumvon={wirkungsdatumvon}&wirkungsdatumbis={wirkungsdatumbis}&strassenname={strassenname}&hausnummer={hausnummer}&plz={plz}&zusatz={zusatz}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
      @Headers({
    "Accept: application/json",
      })
   ApiResponse<AenderungResponse> listAenderungenWithHttpInfo(@QueryMap(encoded=true) ListAenderungenQueryParams queryParams);


   /**
   * A convenience class for generating query parameters for the
   * <code>listAenderungen</code> method in a fluent style.
   */
  public static class ListAenderungenQueryParams extends HashMap<String, Object> {
    public ListAenderungenQueryParams wirkungsdatumvon(final Date value) {
      put("wirkungsdatumvon", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungenQueryParams wirkungsdatumbis(final Date value) {
      put("wirkungsdatumbis", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungenQueryParams strassenname(final String value) {
      put("strassenname", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungenQueryParams hausnummer(final Long value) {
      put("hausnummer", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungenQueryParams plz(final String value) {
      put("plz", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungenQueryParams zusatz(final String value) {
      put("zusatz", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungenQueryParams sort(final String value) {
      put("sort", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungenQueryParams sortdir(final String value) {
      put("sortdir", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungenQueryParams page(final Integer value) {
      put("page", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungenQueryParams pagesize(final Integer value) {
      put("pagesize", EncodingUtils.encode(value));
      return this;
    }
  }

  /**
   * Liefert alle Änderungen, die mit den angegebenen Datum übereinstimmen.
   * 
   * @param wirkungsdatumvon Von-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2022-01-01&#39; (optional)
   * @param wirkungsdatumbis Bis-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2022-01-14&#39; (optional)
   * @param strassenname Straßenname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)
   * @param hausnummer Hausnummer  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;4&#39; (optional)
   * @param plz Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80638&#39; (optional)
   * @param zusatz Zusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;a&#39; (optional)
   * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return AenderungResponse
   */
  @RequestLine("GET /adresse/aenderung?wirkungsdatumvon={wirkungsdatumvon}&wirkungsdatumbis={wirkungsdatumbis}&strassenname={strassenname}&hausnummer={hausnummer}&plz={plz}&zusatz={zusatz}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  AenderungResponse listAenderungen1(@Param("wirkungsdatumvon") Date wirkungsdatumvon, @Param("wirkungsdatumbis") Date wirkungsdatumbis, @Param("strassenname") String strassenname, @Param("hausnummer") Long hausnummer, @Param("plz") String plz, @Param("zusatz") String zusatz, @Param("sort") String sort, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);

  /**
   * Liefert alle Änderungen, die mit den angegebenen Datum übereinstimmen.
   * Similar to <code>listAenderungen1</code> but it also returns the http response headers .
   * 
   * @param wirkungsdatumvon Von-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2022-01-01&#39; (optional)
   * @param wirkungsdatumbis Bis-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2022-01-14&#39; (optional)
   * @param strassenname Straßenname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)
   * @param hausnummer Hausnummer  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;4&#39; (optional)
   * @param plz Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80638&#39; (optional)
   * @param zusatz Zusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;a&#39; (optional)
   * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return A ApiResponse that wraps the response boyd and the http headers.
   */
  @RequestLine("GET /adresse/aenderung?wirkungsdatumvon={wirkungsdatumvon}&wirkungsdatumbis={wirkungsdatumbis}&strassenname={strassenname}&hausnummer={hausnummer}&plz={plz}&zusatz={zusatz}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  ApiResponse<AenderungResponse> listAenderungen1WithHttpInfo(@Param("wirkungsdatumvon") Date wirkungsdatumvon, @Param("wirkungsdatumbis") Date wirkungsdatumbis, @Param("strassenname") String strassenname, @Param("hausnummer") Long hausnummer, @Param("plz") String plz, @Param("zusatz") String zusatz, @Param("sort") String sort, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);


  /**
   * Liefert alle Änderungen, die mit den angegebenen Datum übereinstimmen.
   * 
   * Note, this is equivalent to the other <code>listAenderungen1</code> method,
   * but with the query parameters collected into a single Map parameter. This
   * is convenient for services with optional query parameters, especially when
   * used with the {@link ListAenderungen1QueryParams} class that allows for
   * building up this map in a fluent style.
   * @param queryParams Map of query parameters as name-value pairs
   *   <p>The following elements may be specified in the query map:</p>
   *   <ul>
   *   <li>wirkungsdatumvon - Von-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2022-01-01&#39; (optional)</li>
   *   <li>wirkungsdatumbis - Bis-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2022-01-14&#39; (optional)</li>
   *   <li>strassenname - Straßenname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)</li>
   *   <li>hausnummer - Hausnummer  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;4&#39; (optional)</li>
   *   <li>plz - Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80638&#39; (optional)</li>
   *   <li>zusatz - Zusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;a&#39; (optional)</li>
   *   <li>sort - Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)</li>
   *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
   *   <li>page - Seitennummer (optional, default to 0)</li>
   *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
   *   </ul>
   * @return AenderungResponse
   */
  @RequestLine("GET /adresse/aenderung?wirkungsdatumvon={wirkungsdatumvon}&wirkungsdatumbis={wirkungsdatumbis}&strassenname={strassenname}&hausnummer={hausnummer}&plz={plz}&zusatz={zusatz}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
  "Accept: application/json",
  })
  AenderungResponse listAenderungen1(@QueryMap(encoded=true) ListAenderungen1QueryParams queryParams);

  /**
  * Liefert alle Änderungen, die mit den angegebenen Datum übereinstimmen.
  * 
  * Note, this is equivalent to the other <code>listAenderungen1</code> that receives the query parameters as a map,
  * but this one also exposes the Http response headers
      * @param queryParams Map of query parameters as name-value pairs
      *   <p>The following elements may be specified in the query map:</p>
      *   <ul>
          *   <li>wirkungsdatumvon - Von-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2022-01-01&#39; (optional)</li>
          *   <li>wirkungsdatumbis - Bis-Datum im Format YYYY-MM-DD &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;2022-01-14&#39; (optional)</li>
          *   <li>strassenname - Straßenname &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße&#39; (optional)</li>
          *   <li>hausnummer - Hausnummer  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;4&#39; (optional)</li>
          *   <li>plz - Postleitzahl &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;80638&#39; (optional)</li>
          *   <li>zusatz - Zusatz &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;a&#39; (optional)</li>
          *   <li>sort - Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)</li>
          *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
          *   <li>page - Seitennummer (optional, default to 0)</li>
          *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
      *   </ul>
          * @return AenderungResponse
      */
      @RequestLine("GET /adresse/aenderung?wirkungsdatumvon={wirkungsdatumvon}&wirkungsdatumbis={wirkungsdatumbis}&strassenname={strassenname}&hausnummer={hausnummer}&plz={plz}&zusatz={zusatz}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
      @Headers({
    "Accept: application/json",
      })
   ApiResponse<AenderungResponse> listAenderungen1WithHttpInfo(@QueryMap(encoded=true) ListAenderungen1QueryParams queryParams);


   /**
   * A convenience class for generating query parameters for the
   * <code>listAenderungen1</code> method in a fluent style.
   */
  public static class ListAenderungen1QueryParams extends HashMap<String, Object> {
    public ListAenderungen1QueryParams wirkungsdatumvon(final Date value) {
      put("wirkungsdatumvon", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungen1QueryParams wirkungsdatumbis(final Date value) {
      put("wirkungsdatumbis", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungen1QueryParams strassenname(final String value) {
      put("strassenname", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungen1QueryParams hausnummer(final Long value) {
      put("hausnummer", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungen1QueryParams plz(final String value) {
      put("plz", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungen1QueryParams zusatz(final String value) {
      put("zusatz", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungen1QueryParams sort(final String value) {
      put("sort", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungen1QueryParams sortdir(final String value) {
      put("sortdir", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungen1QueryParams page(final Integer value) {
      put("page", EncodingUtils.encode(value));
      return this;
    }
    public ListAenderungen1QueryParams pagesize(final Integer value) {
      put("pagesize", EncodingUtils.encode(value));
      return this;
    }
  }

  /**
   * Sucht Münchner Adressen für angegebene Query
   * 
   * @param query Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer, ggf. Buchstabe und optional Plz/Ort  &lt;br /&gt; &lt;i&gt;Beispiele&lt;/i&gt;: &#39;Marsstraße 4a&#39;, &#39;Marsstraße 4a, 80638 München&#39; (required)
   * @param plzfilter PLZ Filterobjekt. Rangefilter (von...bis) für PLZ.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;80000&#39;. Nur Adressen mit Postleitzahl &#39;80000&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;80000&#39; und &#39;80002&#39;. Nur Adressen mit einer Postleitzahl von &#39;80000&#39;,&#39;80001&#39; oder &#39;80002&#39; werden ausgegeben (optional)
   * @param hausnummerfilter Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;90&#39;. Nur Adressen mit Hausnummer &#39;90&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;90&#39; und &#39;100&#39;. Nur Adressen mit einer Hausnummer von &#39;90&#39; - &#39;100&#39; werden ausgegeben (optional)
   * @param buchstabefilter Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;a&#39;. Nur Adressen mit Hausnummerzusatz a&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;a&#39; und &#39;c&#39;. Nur Adressen mit einem Hausnummerzusatz von &#39;a&#39;, &#39;b&#39; oder &#39;c&#39; werden ausgegeben (optional)
   * @param searchtype SearchType &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;:&lt;br /&gt;&#39;AKTIV&#39; – Default. Nur aktive Adressen werden durchsucht. &lt;br /&gt;&#39;HISTORISCH&#39; – Historische Adressen werden zusätzlich durchsucht. Falls ein Treffer mit aktiver Nachfolgeradresse gefunden wird, wird die aktive Adresse in die Ergebnisliste mit aufgenommen (mit dem zusätzlichen Attribut &#39;ehemaligeAdresse&#39;). Achtung: Es können Duplikate auftreten, z.B. wenn eine aktive Nachfolgeradresse viele historische Vorgänger hat. (optional, default to AKTIV)
   * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return MuenchenAdresseResponse
   */
  @RequestLine("GET /v2/adresse/search?query={query}&plzfilter={plzfilter}&hausnummerfilter={hausnummerfilter}&buchstabefilter={buchstabefilter}&searchtype={searchtype}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  MuenchenAdresseResponse searchAdressen1(@Param("query") String query, @Param("plzfilter") List<String> plzfilter, @Param("hausnummerfilter") List<Long> hausnummerfilter, @Param("buchstabefilter") List<String> buchstabefilter, @Param("searchtype") String searchtype, @Param("sort") String sort, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);

  /**
   * Sucht Münchner Adressen für angegebene Query
   * Similar to <code>searchAdressen1</code> but it also returns the http response headers .
   * 
   * @param query Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer, ggf. Buchstabe und optional Plz/Ort  &lt;br /&gt; &lt;i&gt;Beispiele&lt;/i&gt;: &#39;Marsstraße 4a&#39;, &#39;Marsstraße 4a, 80638 München&#39; (required)
   * @param plzfilter PLZ Filterobjekt. Rangefilter (von...bis) für PLZ.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;80000&#39;. Nur Adressen mit Postleitzahl &#39;80000&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;80000&#39; und &#39;80002&#39;. Nur Adressen mit einer Postleitzahl von &#39;80000&#39;,&#39;80001&#39; oder &#39;80002&#39; werden ausgegeben (optional)
   * @param hausnummerfilter Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;90&#39;. Nur Adressen mit Hausnummer &#39;90&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;90&#39; und &#39;100&#39;. Nur Adressen mit einer Hausnummer von &#39;90&#39; - &#39;100&#39; werden ausgegeben (optional)
   * @param buchstabefilter Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;a&#39;. Nur Adressen mit Hausnummerzusatz a&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;a&#39; und &#39;c&#39;. Nur Adressen mit einem Hausnummerzusatz von &#39;a&#39;, &#39;b&#39; oder &#39;c&#39; werden ausgegeben (optional)
   * @param searchtype SearchType &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;:&lt;br /&gt;&#39;AKTIV&#39; – Default. Nur aktive Adressen werden durchsucht. &lt;br /&gt;&#39;HISTORISCH&#39; – Historische Adressen werden zusätzlich durchsucht. Falls ein Treffer mit aktiver Nachfolgeradresse gefunden wird, wird die aktive Adresse in die Ergebnisliste mit aufgenommen (mit dem zusätzlichen Attribut &#39;ehemaligeAdresse&#39;). Achtung: Es können Duplikate auftreten, z.B. wenn eine aktive Nachfolgeradresse viele historische Vorgänger hat. (optional, default to AKTIV)
   * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return A ApiResponse that wraps the response boyd and the http headers.
   */
  @RequestLine("GET /v2/adresse/search?query={query}&plzfilter={plzfilter}&hausnummerfilter={hausnummerfilter}&buchstabefilter={buchstabefilter}&searchtype={searchtype}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  ApiResponse<MuenchenAdresseResponse> searchAdressen1WithHttpInfo(@Param("query") String query, @Param("plzfilter") List<String> plzfilter, @Param("hausnummerfilter") List<Long> hausnummerfilter, @Param("buchstabefilter") List<String> buchstabefilter, @Param("searchtype") String searchtype, @Param("sort") String sort, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);


  /**
   * Sucht Münchner Adressen für angegebene Query
   * 
   * Note, this is equivalent to the other <code>searchAdressen1</code> method,
   * but with the query parameters collected into a single Map parameter. This
   * is convenient for services with optional query parameters, especially when
   * used with the {@link SearchAdressen1QueryParams} class that allows for
   * building up this map in a fluent style.
   * @param queryParams Map of query parameters as name-value pairs
   *   <p>The following elements may be specified in the query map:</p>
   *   <ul>
   *   <li>query - Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer, ggf. Buchstabe und optional Plz/Ort  &lt;br /&gt; &lt;i&gt;Beispiele&lt;/i&gt;: &#39;Marsstraße 4a&#39;, &#39;Marsstraße 4a, 80638 München&#39; (required)</li>
   *   <li>plzfilter - PLZ Filterobjekt. Rangefilter (von...bis) für PLZ.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;80000&#39;. Nur Adressen mit Postleitzahl &#39;80000&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;80000&#39; und &#39;80002&#39;. Nur Adressen mit einer Postleitzahl von &#39;80000&#39;,&#39;80001&#39; oder &#39;80002&#39; werden ausgegeben (optional)</li>
   *   <li>hausnummerfilter - Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;90&#39;. Nur Adressen mit Hausnummer &#39;90&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;90&#39; und &#39;100&#39;. Nur Adressen mit einer Hausnummer von &#39;90&#39; - &#39;100&#39; werden ausgegeben (optional)</li>
   *   <li>buchstabefilter - Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;a&#39;. Nur Adressen mit Hausnummerzusatz a&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;a&#39; und &#39;c&#39;. Nur Adressen mit einem Hausnummerzusatz von &#39;a&#39;, &#39;b&#39; oder &#39;c&#39; werden ausgegeben (optional)</li>
   *   <li>searchtype - SearchType &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;:&lt;br /&gt;&#39;AKTIV&#39; – Default. Nur aktive Adressen werden durchsucht. &lt;br /&gt;&#39;HISTORISCH&#39; – Historische Adressen werden zusätzlich durchsucht. Falls ein Treffer mit aktiver Nachfolgeradresse gefunden wird, wird die aktive Adresse in die Ergebnisliste mit aufgenommen (mit dem zusätzlichen Attribut &#39;ehemaligeAdresse&#39;). Achtung: Es können Duplikate auftreten, z.B. wenn eine aktive Nachfolgeradresse viele historische Vorgänger hat. (optional, default to AKTIV)</li>
   *   <li>sort - Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)</li>
   *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
   *   <li>page - Seitennummer (optional, default to 0)</li>
   *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
   *   </ul>
   * @return MuenchenAdresseResponse
   */
  @RequestLine("GET /v2/adresse/search?query={query}&plzfilter={plzfilter}&hausnummerfilter={hausnummerfilter}&buchstabefilter={buchstabefilter}&searchtype={searchtype}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
  "Accept: application/json",
  })
  MuenchenAdresseResponse searchAdressen1(@QueryMap(encoded=true) SearchAdressen1QueryParams queryParams);

  /**
  * Sucht Münchner Adressen für angegebene Query
  * 
  * Note, this is equivalent to the other <code>searchAdressen1</code> that receives the query parameters as a map,
  * but this one also exposes the Http response headers
      * @param queryParams Map of query parameters as name-value pairs
      *   <p>The following elements may be specified in the query map:</p>
      *   <ul>
          *   <li>query - Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer, ggf. Buchstabe und optional Plz/Ort  &lt;br /&gt; &lt;i&gt;Beispiele&lt;/i&gt;: &#39;Marsstraße 4a&#39;, &#39;Marsstraße 4a, 80638 München&#39; (required)</li>
          *   <li>plzfilter - PLZ Filterobjekt. Rangefilter (von...bis) für PLZ.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;80000&#39;. Nur Adressen mit Postleitzahl &#39;80000&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;80000&#39; und &#39;80002&#39;. Nur Adressen mit einer Postleitzahl von &#39;80000&#39;,&#39;80001&#39; oder &#39;80002&#39; werden ausgegeben (optional)</li>
          *   <li>hausnummerfilter - Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;90&#39;. Nur Adressen mit Hausnummer &#39;90&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;90&#39; und &#39;100&#39;. Nur Adressen mit einer Hausnummer von &#39;90&#39; - &#39;100&#39; werden ausgegeben (optional)</li>
          *   <li>buchstabefilter - Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Einfache Eingabe von &#39;a&#39;. Nur Adressen mit Hausnummerzusatz a&#39; oder höher werden ausgegeben.&lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: Eingabe von &#39;a&#39; und &#39;c&#39;. Nur Adressen mit einem Hausnummerzusatz von &#39;a&#39;, &#39;b&#39; oder &#39;c&#39; werden ausgegeben (optional)</li>
          *   <li>searchtype - SearchType &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;:&lt;br /&gt;&#39;AKTIV&#39; – Default. Nur aktive Adressen werden durchsucht. &lt;br /&gt;&#39;HISTORISCH&#39; – Historische Adressen werden zusätzlich durchsucht. Falls ein Treffer mit aktiver Nachfolgeradresse gefunden wird, wird die aktive Adresse in die Ergebnisliste mit aufgenommen (mit dem zusätzlichen Attribut &#39;ehemaligeAdresse&#39;). Achtung: Es können Duplikate auftreten, z.B. wenn eine aktive Nachfolgeradresse viele historische Vorgänger hat. (optional, default to AKTIV)</li>
          *   <li>sort - Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)</li>
          *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
          *   <li>page - Seitennummer (optional, default to 0)</li>
          *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
      *   </ul>
          * @return MuenchenAdresseResponse
      */
      @RequestLine("GET /v2/adresse/search?query={query}&plzfilter={plzfilter}&hausnummerfilter={hausnummerfilter}&buchstabefilter={buchstabefilter}&searchtype={searchtype}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
      @Headers({
    "Accept: application/json",
      })
   ApiResponse<MuenchenAdresseResponse> searchAdressen1WithHttpInfo(@QueryMap(encoded=true) SearchAdressen1QueryParams queryParams);


   /**
   * A convenience class for generating query parameters for the
   * <code>searchAdressen1</code> method in a fluent style.
   */
  public static class SearchAdressen1QueryParams extends HashMap<String, Object> {
    public SearchAdressen1QueryParams query(final String value) {
      put("query", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressen1QueryParams plzfilter(final List<String> value) {
      put("plzfilter", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public SearchAdressen1QueryParams hausnummerfilter(final List<Long> value) {
      put("hausnummerfilter", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public SearchAdressen1QueryParams buchstabefilter(final List<String> value) {
      put("buchstabefilter", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public SearchAdressen1QueryParams searchtype(final String value) {
      put("searchtype", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressen1QueryParams sort(final String value) {
      put("sort", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressen1QueryParams sortdir(final String value) {
      put("sortdir", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressen1QueryParams page(final Integer value) {
      put("page", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressen1QueryParams pagesize(final Integer value) {
      put("pagesize", EncodingUtils.encode(value));
      return this;
    }
  }

  /**
   * Sucht Münchner Adressen für angegebene Query
   * 
   * @param query Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer und ggf. Buchstabe  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße 4a&#39; (required)
   * @param plzfilter PLZ Filterobjekt. Rangefilter (von...bis) für PLZ (optional)
   * @param hausnummerfilter Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer (optional)
   * @param buchstabefilter Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer (optional)
   * @param searchtype SearchType &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;:&lt;br /&gt;&#39;AKTIV&#39; – Default. Nur aktive Adressen werden durchsucht. &lt;br /&gt;&#39;HISTORISCH&#39; – Historische Adressen werden zusätzlich durchsucht. Falls ein Treffer mit aktiver Nachfolgeradresse gefunden wird, wird die aktive Adresse in die Ergebnisliste mit aufgenommen (mit dem zusätzlichen Attribut &#39;ehemaligeAdresse&#39;). Achtung: Es können Duplikate auftreten, z.B. wenn eine aktive Nachfolgeradresse viele historische Vorgänger hat. (optional, default to AKTIV)
   * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return AdresseResponse
   */
  @RequestLine("GET /adresse/search?query={query}&plzfilter={plzfilter}&hausnummerfilter={hausnummerfilter}&buchstabefilter={buchstabefilter}&searchtype={searchtype}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  AdresseResponse searchAdressen3(@Param("query") String query, @Param("plzfilter") List<String> plzfilter, @Param("hausnummerfilter") List<Long> hausnummerfilter, @Param("buchstabefilter") List<String> buchstabefilter, @Param("searchtype") String searchtype, @Param("sort") String sort, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);

  /**
   * Sucht Münchner Adressen für angegebene Query
   * Similar to <code>searchAdressen3</code> but it also returns the http response headers .
   * 
   * @param query Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer und ggf. Buchstabe  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße 4a&#39; (required)
   * @param plzfilter PLZ Filterobjekt. Rangefilter (von...bis) für PLZ (optional)
   * @param hausnummerfilter Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer (optional)
   * @param buchstabefilter Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer (optional)
   * @param searchtype SearchType &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;:&lt;br /&gt;&#39;AKTIV&#39; – Default. Nur aktive Adressen werden durchsucht. &lt;br /&gt;&#39;HISTORISCH&#39; – Historische Adressen werden zusätzlich durchsucht. Falls ein Treffer mit aktiver Nachfolgeradresse gefunden wird, wird die aktive Adresse in die Ergebnisliste mit aufgenommen (mit dem zusätzlichen Attribut &#39;ehemaligeAdresse&#39;). Achtung: Es können Duplikate auftreten, z.B. wenn eine aktive Nachfolgeradresse viele historische Vorgänger hat. (optional, default to AKTIV)
   * @param sort Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)
   * @param sortdir Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)
   * @param page Seitennummer (optional, default to 0)
   * @param pagesize Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)
   * @return A ApiResponse that wraps the response boyd and the http headers.
   */
  @RequestLine("GET /adresse/search?query={query}&plzfilter={plzfilter}&hausnummerfilter={hausnummerfilter}&buchstabefilter={buchstabefilter}&searchtype={searchtype}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
    "Accept: application/json",
  })
  ApiResponse<AdresseResponse> searchAdressen3WithHttpInfo(@Param("query") String query, @Param("plzfilter") List<String> plzfilter, @Param("hausnummerfilter") List<Long> hausnummerfilter, @Param("buchstabefilter") List<String> buchstabefilter, @Param("searchtype") String searchtype, @Param("sort") String sort, @Param("sortdir") String sortdir, @Param("page") Integer page, @Param("pagesize") Integer pagesize);


  /**
   * Sucht Münchner Adressen für angegebene Query
   * 
   * Note, this is equivalent to the other <code>searchAdressen3</code> method,
   * but with the query parameters collected into a single Map parameter. This
   * is convenient for services with optional query parameters, especially when
   * used with the {@link SearchAdressen3QueryParams} class that allows for
   * building up this map in a fluent style.
   * @param queryParams Map of query parameters as name-value pairs
   *   <p>The following elements may be specified in the query map:</p>
   *   <ul>
   *   <li>query - Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer und ggf. Buchstabe  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße 4a&#39; (required)</li>
   *   <li>plzfilter - PLZ Filterobjekt. Rangefilter (von...bis) für PLZ (optional)</li>
   *   <li>hausnummerfilter - Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer (optional)</li>
   *   <li>buchstabefilter - Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer (optional)</li>
   *   <li>searchtype - SearchType &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;:&lt;br /&gt;&#39;AKTIV&#39; – Default. Nur aktive Adressen werden durchsucht. &lt;br /&gt;&#39;HISTORISCH&#39; – Historische Adressen werden zusätzlich durchsucht. Falls ein Treffer mit aktiver Nachfolgeradresse gefunden wird, wird die aktive Adresse in die Ergebnisliste mit aufgenommen (mit dem zusätzlichen Attribut &#39;ehemaligeAdresse&#39;). Achtung: Es können Duplikate auftreten, z.B. wenn eine aktive Nachfolgeradresse viele historische Vorgänger hat. (optional, default to AKTIV)</li>
   *   <li>sort - Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)</li>
   *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
   *   <li>page - Seitennummer (optional, default to 0)</li>
   *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
   *   </ul>
   * @return AdresseResponse
   */
  @RequestLine("GET /adresse/search?query={query}&plzfilter={plzfilter}&hausnummerfilter={hausnummerfilter}&buchstabefilter={buchstabefilter}&searchtype={searchtype}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
  @Headers({
  "Accept: application/json",
  })
  AdresseResponse searchAdressen3(@QueryMap(encoded=true) SearchAdressen3QueryParams queryParams);

  /**
  * Sucht Münchner Adressen für angegebene Query
  * 
  * Note, this is equivalent to the other <code>searchAdressen3</code> that receives the query parameters as a map,
  * but this one also exposes the Http response headers
      * @param queryParams Map of query parameters as name-value pairs
      *   <p>The following elements may be specified in the query map:</p>
      *   <ul>
          *   <li>query - Suchtext mit Adressteilen. Erwartet wird ein Straßenname mit Hausnummer und ggf. Buchstabe  &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: &#39;Marsstraße 4a&#39; (required)</li>
          *   <li>plzfilter - PLZ Filterobjekt. Rangefilter (von...bis) für PLZ (optional)</li>
          *   <li>hausnummerfilter - Hausnummer Filterobjekt. Rangefilter (von...bis) für Hausnummer (optional)</li>
          *   <li>buchstabefilter - Hausnummer Buchstabe Filterobjekt. Rangefilter (von...bis) für Buchstabe der Hausnummer (optional)</li>
          *   <li>searchtype - SearchType &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;:&lt;br /&gt;&#39;AKTIV&#39; – Default. Nur aktive Adressen werden durchsucht. &lt;br /&gt;&#39;HISTORISCH&#39; – Historische Adressen werden zusätzlich durchsucht. Falls ein Treffer mit aktiver Nachfolgeradresse gefunden wird, wird die aktive Adresse in die Ergebnisliste mit aufgenommen (mit dem zusätzlichen Attribut &#39;ehemaligeAdresse&#39;). Achtung: Es können Duplikate auftreten, z.B. wenn eine aktive Nachfolgeradresse viele historische Vorgänger hat. (optional, default to AKTIV)</li>
          *   <li>sort - Sortierung. Wird keine Sortierung angegeben, wird nach den Score-Wert sortiert (empfohlen) &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;HAUSNUMMER&#39;, &#39;BUCHSTABE&#39;, &#39;STRASSE&#39; (optional)</li>
          *   <li>sortdir - Sortierungsrichtung  &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;ASC&#39;, &#39;DESC&#39; (optional)</li>
          *   <li>page - Seitennummer (optional, default to 0)</li>
          *   <li>pagesize - Seitengröße. Anzahl maximal angezeigter Ergebnisse pro Seite (optional, default to 20)</li>
      *   </ul>
          * @return AdresseResponse
      */
      @RequestLine("GET /adresse/search?query={query}&plzfilter={plzfilter}&hausnummerfilter={hausnummerfilter}&buchstabefilter={buchstabefilter}&searchtype={searchtype}&sort={sort}&sortdir={sortdir}&page={page}&pagesize={pagesize}")
      @Headers({
    "Accept: application/json",
      })
   ApiResponse<AdresseResponse> searchAdressen3WithHttpInfo(@QueryMap(encoded=true) SearchAdressen3QueryParams queryParams);


   /**
   * A convenience class for generating query parameters for the
   * <code>searchAdressen3</code> method in a fluent style.
   */
  public static class SearchAdressen3QueryParams extends HashMap<String, Object> {
    public SearchAdressen3QueryParams query(final String value) {
      put("query", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressen3QueryParams plzfilter(final List<String> value) {
      put("plzfilter", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public SearchAdressen3QueryParams hausnummerfilter(final List<Long> value) {
      put("hausnummerfilter", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public SearchAdressen3QueryParams buchstabefilter(final List<String> value) {
      put("buchstabefilter", EncodingUtils.encodeCollection(value, "multi"));
      return this;
    }
    public SearchAdressen3QueryParams searchtype(final String value) {
      put("searchtype", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressen3QueryParams sort(final String value) {
      put("sort", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressen3QueryParams sortdir(final String value) {
      put("sortdir", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressen3QueryParams page(final Integer value) {
      put("page", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressen3QueryParams pagesize(final Integer value) {
      put("pagesize", EncodingUtils.encode(value));
      return this;
    }
  }

  /**
   * Sucht Münchner Adressen in angegebener Fläche
   * 
   * @param geometrie Geometrie, in der gesucht werden soll &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;Punkt&#39; mit Entfernung für die Suche innerhalb eines Kreises oder &#39;BoundingBox&#39; (required)
   * @param lat Latitude. Geographische Breite des Bezugspunktes. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
   * @param lng Longitude. Geographische Länge des Bezugspunktes. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
   * @param distanz Kreisradius. Suchdistanz zum Bezugspunkt in Metern. Wenn kein Wert gesetzt ist, wird ein Defaultwert von 50 Metern bei der Kreissuche verwendet. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 125.50 (optional)
   * @param topleftlat Latitude des linken oberen Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
   * @param topleftlng Longitude des linken oberen Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
   * @param bottomrightlat Latitude des rechten unteren Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
   * @param bottomrightlng Longitude des rechten unteren Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
   * @param format Koordinatenformat des Bezugspunktes. Wenn der Parameter fehlt, wird von UTM ausgegangen. &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;UTM&#39;, &#39;WGS&#39; (optional)
   * @return List&lt;AdresseDistanz&gt;
   */
  @RequestLine("GET /v2/adresse/search/geo?geometrie={geometrie}&lat={lat}&lng={lng}&distanz={distanz}&topleftlat={topleftlat}&topleftlng={topleftlng}&bottomrightlat={bottomrightlat}&bottomrightlng={bottomrightlng}&format={format}")
  @Headers({
    "Accept: application/json",
  })
  List<AdresseDistanz> searchAdressenGeo(@Param("geometrie") String geometrie, @Param("lat") Double lat, @Param("lng") Double lng, @Param("distanz") Double distanz, @Param("topleftlat") Double topleftlat, @Param("topleftlng") Double topleftlng, @Param("bottomrightlat") Double bottomrightlat, @Param("bottomrightlng") Double bottomrightlng, @Param("format") String format);

  /**
   * Sucht Münchner Adressen in angegebener Fläche
   * Similar to <code>searchAdressenGeo</code> but it also returns the http response headers .
   * 
   * @param geometrie Geometrie, in der gesucht werden soll &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;Punkt&#39; mit Entfernung für die Suche innerhalb eines Kreises oder &#39;BoundingBox&#39; (required)
   * @param lat Latitude. Geographische Breite des Bezugspunktes. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
   * @param lng Longitude. Geographische Länge des Bezugspunktes. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
   * @param distanz Kreisradius. Suchdistanz zum Bezugspunkt in Metern. Wenn kein Wert gesetzt ist, wird ein Defaultwert von 50 Metern bei der Kreissuche verwendet. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 125.50 (optional)
   * @param topleftlat Latitude des linken oberen Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
   * @param topleftlng Longitude des linken oberen Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
   * @param bottomrightlat Latitude des rechten unteren Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
   * @param bottomrightlng Longitude des rechten unteren Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
   * @param format Koordinatenformat des Bezugspunktes. Wenn der Parameter fehlt, wird von UTM ausgegangen. &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;UTM&#39;, &#39;WGS&#39; (optional)
   * @return A ApiResponse that wraps the response boyd and the http headers.
   */
  @RequestLine("GET /v2/adresse/search/geo?geometrie={geometrie}&lat={lat}&lng={lng}&distanz={distanz}&topleftlat={topleftlat}&topleftlng={topleftlng}&bottomrightlat={bottomrightlat}&bottomrightlng={bottomrightlng}&format={format}")
  @Headers({
    "Accept: application/json",
  })
  ApiResponse<List<AdresseDistanz>> searchAdressenGeoWithHttpInfo(@Param("geometrie") String geometrie, @Param("lat") Double lat, @Param("lng") Double lng, @Param("distanz") Double distanz, @Param("topleftlat") Double topleftlat, @Param("topleftlng") Double topleftlng, @Param("bottomrightlat") Double bottomrightlat, @Param("bottomrightlng") Double bottomrightlng, @Param("format") String format);


  /**
   * Sucht Münchner Adressen in angegebener Fläche
   * 
   * Note, this is equivalent to the other <code>searchAdressenGeo</code> method,
   * but with the query parameters collected into a single Map parameter. This
   * is convenient for services with optional query parameters, especially when
   * used with the {@link SearchAdressenGeoQueryParams} class that allows for
   * building up this map in a fluent style.
   * @param queryParams Map of query parameters as name-value pairs
   *   <p>The following elements may be specified in the query map:</p>
   *   <ul>
   *   <li>geometrie - Geometrie, in der gesucht werden soll &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;Punkt&#39; mit Entfernung für die Suche innerhalb eines Kreises oder &#39;BoundingBox&#39; (required)</li>
   *   <li>lat - Latitude. Geographische Breite des Bezugspunktes. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)</li>
   *   <li>lng - Longitude. Geographische Länge des Bezugspunktes. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)</li>
   *   <li>distanz - Kreisradius. Suchdistanz zum Bezugspunkt in Metern. Wenn kein Wert gesetzt ist, wird ein Defaultwert von 50 Metern bei der Kreissuche verwendet. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 125.50 (optional)</li>
   *   <li>topleftlat - Latitude des linken oberen Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)</li>
   *   <li>topleftlng - Longitude des linken oberen Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)</li>
   *   <li>bottomrightlat - Latitude des rechten unteren Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)</li>
   *   <li>bottomrightlng - Longitude des rechten unteren Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)</li>
   *   <li>format - Koordinatenformat des Bezugspunktes. Wenn der Parameter fehlt, wird von UTM ausgegangen. &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;UTM&#39;, &#39;WGS&#39; (optional)</li>
   *   </ul>
   * @return List&lt;AdresseDistanz&gt;
   */
  @RequestLine("GET /v2/adresse/search/geo?geometrie={geometrie}&lat={lat}&lng={lng}&distanz={distanz}&topleftlat={topleftlat}&topleftlng={topleftlng}&bottomrightlat={bottomrightlat}&bottomrightlng={bottomrightlng}&format={format}")
  @Headers({
  "Accept: application/json",
  })
  List<AdresseDistanz> searchAdressenGeo(@QueryMap(encoded=true) SearchAdressenGeoQueryParams queryParams);

  /**
  * Sucht Münchner Adressen in angegebener Fläche
  * 
  * Note, this is equivalent to the other <code>searchAdressenGeo</code> that receives the query parameters as a map,
  * but this one also exposes the Http response headers
      * @param queryParams Map of query parameters as name-value pairs
      *   <p>The following elements may be specified in the query map:</p>
      *   <ul>
          *   <li>geometrie - Geometrie, in der gesucht werden soll &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;Punkt&#39; mit Entfernung für die Suche innerhalb eines Kreises oder &#39;BoundingBox&#39; (required)</li>
          *   <li>lat - Latitude. Geographische Breite des Bezugspunktes. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)</li>
          *   <li>lng - Longitude. Geographische Länge des Bezugspunktes. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)</li>
          *   <li>distanz - Kreisradius. Suchdistanz zum Bezugspunkt in Metern. Wenn kein Wert gesetzt ist, wird ein Defaultwert von 50 Metern bei der Kreissuche verwendet. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 125.50 (optional)</li>
          *   <li>topleftlat - Latitude des linken oberen Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)</li>
          *   <li>topleftlng - Longitude des linken oberen Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)</li>
          *   <li>bottomrightlat - Latitude des rechten unteren Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)</li>
          *   <li>bottomrightlng - Longitude des rechten unteren Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)</li>
          *   <li>format - Koordinatenformat des Bezugspunktes. Wenn der Parameter fehlt, wird von UTM ausgegangen. &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;UTM&#39;, &#39;WGS&#39; (optional)</li>
      *   </ul>
          * @return List&lt;AdresseDistanz&gt;
      */
      @RequestLine("GET /v2/adresse/search/geo?geometrie={geometrie}&lat={lat}&lng={lng}&distanz={distanz}&topleftlat={topleftlat}&topleftlng={topleftlng}&bottomrightlat={bottomrightlat}&bottomrightlng={bottomrightlng}&format={format}")
      @Headers({
    "Accept: application/json",
      })
   ApiResponse<List<AdresseDistanz>> searchAdressenGeoWithHttpInfo(@QueryMap(encoded=true) SearchAdressenGeoQueryParams queryParams);


   /**
   * A convenience class for generating query parameters for the
   * <code>searchAdressenGeo</code> method in a fluent style.
   */
  public static class SearchAdressenGeoQueryParams extends HashMap<String, Object> {
    public SearchAdressenGeoQueryParams geometrie(final String value) {
      put("geometrie", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenGeoQueryParams lat(final Double value) {
      put("lat", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenGeoQueryParams lng(final Double value) {
      put("lng", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenGeoQueryParams distanz(final Double value) {
      put("distanz", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenGeoQueryParams topleftlat(final Double value) {
      put("topleftlat", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenGeoQueryParams topleftlng(final Double value) {
      put("topleftlng", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenGeoQueryParams bottomrightlat(final Double value) {
      put("bottomrightlat", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenGeoQueryParams bottomrightlng(final Double value) {
      put("bottomrightlng", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenGeoQueryParams format(final String value) {
      put("format", EncodingUtils.encode(value));
      return this;
    }
  }

  /**
   * Sucht Münchner Adressen in angegebener Fläche
   * 
   * @param geometrie Geometrie, in der gesucht werden soll &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;Punkt&#39; mit Entfernung für die Suche innerhalb eines Kreises oder &#39;BoundingBox&#39; (required)
   * @param lat Latitude. Geographische Breite des Bezugspunktes. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
   * @param lng Longitude. Geographische Länge des Bezugspunktes. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
   * @param distanz Kreisradius. Suchdistanz zum Bezugspunkt in Metern. Wenn kein Wert gesetzt ist, wird ein Defaultwert von 50 Metern bei der Kreissuche verwendet. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 125.50 (optional)
   * @param topleftlat Latitude des linken oberen Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
   * @param topleftlng Longitude des linken oberen Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
   * @param bottomrightlat Latitude des rechten unteren Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
   * @param bottomrightlng Longitude des rechten unteren Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
   * @param format Koordinatenformat des Bezugspunktes. Wenn der Parameter fehlt, wird von UTM ausgegangen. &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;UTM&#39;, &#39;WGS&#39; (optional)
   * @return List&lt;AdresseDistanz&gt;
   */
  @RequestLine("GET /adresse/search/geo?geometrie={geometrie}&lat={lat}&lng={lng}&distanz={distanz}&topleftlat={topleftlat}&topleftlng={topleftlng}&bottomrightlat={bottomrightlat}&bottomrightlng={bottomrightlng}&format={format}")
  @Headers({
    "Accept: application/json",
  })
  List<AdresseDistanz> searchAdressenGeo1(@Param("geometrie") String geometrie, @Param("lat") Double lat, @Param("lng") Double lng, @Param("distanz") Double distanz, @Param("topleftlat") Double topleftlat, @Param("topleftlng") Double topleftlng, @Param("bottomrightlat") Double bottomrightlat, @Param("bottomrightlng") Double bottomrightlng, @Param("format") String format);

  /**
   * Sucht Münchner Adressen in angegebener Fläche
   * Similar to <code>searchAdressenGeo1</code> but it also returns the http response headers .
   * 
   * @param geometrie Geometrie, in der gesucht werden soll &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;Punkt&#39; mit Entfernung für die Suche innerhalb eines Kreises oder &#39;BoundingBox&#39; (required)
   * @param lat Latitude. Geographische Breite des Bezugspunktes. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
   * @param lng Longitude. Geographische Länge des Bezugspunktes. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
   * @param distanz Kreisradius. Suchdistanz zum Bezugspunkt in Metern. Wenn kein Wert gesetzt ist, wird ein Defaultwert von 50 Metern bei der Kreissuche verwendet. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 125.50 (optional)
   * @param topleftlat Latitude des linken oberen Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
   * @param topleftlng Longitude des linken oberen Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
   * @param bottomrightlat Latitude des rechten unteren Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)
   * @param bottomrightlng Longitude des rechten unteren Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)
   * @param format Koordinatenformat des Bezugspunktes. Wenn der Parameter fehlt, wird von UTM ausgegangen. &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;UTM&#39;, &#39;WGS&#39; (optional)
   * @return A ApiResponse that wraps the response boyd and the http headers.
   */
  @RequestLine("GET /adresse/search/geo?geometrie={geometrie}&lat={lat}&lng={lng}&distanz={distanz}&topleftlat={topleftlat}&topleftlng={topleftlng}&bottomrightlat={bottomrightlat}&bottomrightlng={bottomrightlng}&format={format}")
  @Headers({
    "Accept: application/json",
  })
  ApiResponse<List<AdresseDistanz>> searchAdressenGeo1WithHttpInfo(@Param("geometrie") String geometrie, @Param("lat") Double lat, @Param("lng") Double lng, @Param("distanz") Double distanz, @Param("topleftlat") Double topleftlat, @Param("topleftlng") Double topleftlng, @Param("bottomrightlat") Double bottomrightlat, @Param("bottomrightlng") Double bottomrightlng, @Param("format") String format);


  /**
   * Sucht Münchner Adressen in angegebener Fläche
   * 
   * Note, this is equivalent to the other <code>searchAdressenGeo1</code> method,
   * but with the query parameters collected into a single Map parameter. This
   * is convenient for services with optional query parameters, especially when
   * used with the {@link SearchAdressenGeo1QueryParams} class that allows for
   * building up this map in a fluent style.
   * @param queryParams Map of query parameters as name-value pairs
   *   <p>The following elements may be specified in the query map:</p>
   *   <ul>
   *   <li>geometrie - Geometrie, in der gesucht werden soll &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;Punkt&#39; mit Entfernung für die Suche innerhalb eines Kreises oder &#39;BoundingBox&#39; (required)</li>
   *   <li>lat - Latitude. Geographische Breite des Bezugspunktes. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)</li>
   *   <li>lng - Longitude. Geographische Länge des Bezugspunktes. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)</li>
   *   <li>distanz - Kreisradius. Suchdistanz zum Bezugspunkt in Metern. Wenn kein Wert gesetzt ist, wird ein Defaultwert von 50 Metern bei der Kreissuche verwendet. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 125.50 (optional)</li>
   *   <li>topleftlat - Latitude des linken oberen Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)</li>
   *   <li>topleftlng - Longitude des linken oberen Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)</li>
   *   <li>bottomrightlat - Latitude des rechten unteren Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)</li>
   *   <li>bottomrightlng - Longitude des rechten unteren Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)</li>
   *   <li>format - Koordinatenformat des Bezugspunktes. Wenn der Parameter fehlt, wird von UTM ausgegangen. &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;UTM&#39;, &#39;WGS&#39; (optional)</li>
   *   </ul>
   * @return List&lt;AdresseDistanz&gt;
   */
  @RequestLine("GET /adresse/search/geo?geometrie={geometrie}&lat={lat}&lng={lng}&distanz={distanz}&topleftlat={topleftlat}&topleftlng={topleftlng}&bottomrightlat={bottomrightlat}&bottomrightlng={bottomrightlng}&format={format}")
  @Headers({
  "Accept: application/json",
  })
  List<AdresseDistanz> searchAdressenGeo1(@QueryMap(encoded=true) SearchAdressenGeo1QueryParams queryParams);

  /**
  * Sucht Münchner Adressen in angegebener Fläche
  * 
  * Note, this is equivalent to the other <code>searchAdressenGeo1</code> that receives the query parameters as a map,
  * but this one also exposes the Http response headers
      * @param queryParams Map of query parameters as name-value pairs
      *   <p>The following elements may be specified in the query map:</p>
      *   <ul>
          *   <li>geometrie - Geometrie, in der gesucht werden soll &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;Punkt&#39; mit Entfernung für die Suche innerhalb eines Kreises oder &#39;BoundingBox&#39; (required)</li>
          *   <li>lat - Latitude. Geographische Breite des Bezugspunktes. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)</li>
          *   <li>lng - Longitude. Geographische Länge des Bezugspunktes. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)</li>
          *   <li>distanz - Kreisradius. Suchdistanz zum Bezugspunkt in Metern. Wenn kein Wert gesetzt ist, wird ein Defaultwert von 50 Metern bei der Kreissuche verwendet. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 125.50 (optional)</li>
          *   <li>topleftlat - Latitude des linken oberen Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)</li>
          *   <li>topleftlng - Longitude des linken oberen Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)</li>
          *   <li>bottomrightlat - Latitude des rechten unteren Punktes der BoundingBox. Entspricht dem Nordwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 5334745.607 (optional)</li>
          *   <li>bottomrightlng - Longitude des rechten unteren Punktes der BoundingBox. Entspricht dem Ostwert im UTM-Format. &lt;br /&gt; &lt;i&gt;Beispiel&lt;/i&gt;: 691641.878 (optional)</li>
          *   <li>format - Koordinatenformat des Bezugspunktes. Wenn der Parameter fehlt, wird von UTM ausgegangen. &lt;br /&gt; &lt;i&gt;Mögliche Werte&lt;/i&gt;: &#39;UTM&#39;, &#39;WGS&#39; (optional)</li>
      *   </ul>
          * @return List&lt;AdresseDistanz&gt;
      */
      @RequestLine("GET /adresse/search/geo?geometrie={geometrie}&lat={lat}&lng={lng}&distanz={distanz}&topleftlat={topleftlat}&topleftlng={topleftlng}&bottomrightlat={bottomrightlat}&bottomrightlng={bottomrightlng}&format={format}")
      @Headers({
    "Accept: application/json",
      })
   ApiResponse<List<AdresseDistanz>> searchAdressenGeo1WithHttpInfo(@QueryMap(encoded=true) SearchAdressenGeo1QueryParams queryParams);


   /**
   * A convenience class for generating query parameters for the
   * <code>searchAdressenGeo1</code> method in a fluent style.
   */
  public static class SearchAdressenGeo1QueryParams extends HashMap<String, Object> {
    public SearchAdressenGeo1QueryParams geometrie(final String value) {
      put("geometrie", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenGeo1QueryParams lat(final Double value) {
      put("lat", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenGeo1QueryParams lng(final Double value) {
      put("lng", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenGeo1QueryParams distanz(final Double value) {
      put("distanz", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenGeo1QueryParams topleftlat(final Double value) {
      put("topleftlat", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenGeo1QueryParams topleftlng(final Double value) {
      put("topleftlng", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenGeo1QueryParams bottomrightlat(final Double value) {
      put("bottomrightlat", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenGeo1QueryParams bottomrightlng(final Double value) {
      put("bottomrightlng", EncodingUtils.encode(value));
      return this;
    }
    public SearchAdressenGeo1QueryParams format(final String value) {
      put("format", EncodingUtils.encode(value));
      return this;
    }
  }
}
