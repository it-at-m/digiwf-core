package de.muenchen.oss.digiwf.address.service.integration.repository;

import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.service.integration.gen.api.AdressenMnchenApi;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.AdresseDistanz;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.AenderungResponse;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.MuenchenAdresse;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.MuenchenAdresseResponse;
import de.muenchen.oss.digiwf.address.service.integration.repository.AdressenMuenchenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AdressenMuenchenRepositoryTest {

    @Mock
    private AdressenMnchenApi adressenMnchenApi;

    private AdressenMuenchenRepository adressenMuenchenRepository;

    @BeforeEach
    public void beforeEach() {
        this.adressenMuenchenRepository = new AdressenMuenchenRepository(this.adressenMnchenApi);
        Mockito.reset(this.adressenMnchenApi);
    }

    @Test
    void checkAdresse() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final String adresse = "adresse";
        final String strassenname = "strassenname";
        final Integer strasseId = 1;
        final String hausnummer = "hausnummer";
        final String zusatz = "zusatz";
        final String plz = "plz";
        final String ortsname = "ortsname";
        final String gemeindeschluessel = "gemeindeschluessel";


        Mockito.when(this.adressenMnchenApi.checkAdresse(
                adresse,
                strassenname,
                strasseId,
                hausnummer,
                zusatz,
                plz,
                ortsname,
                gemeindeschluessel
        )).thenReturn(new MuenchenAdresse());

        final MuenchenAdresse result = this.adressenMuenchenRepository.checkAdresse(
                adresse,
                strassenname,
                strasseId,
                hausnummer,
                zusatz,
                plz,
                ortsname,
                gemeindeschluessel
        );

        assertThat(result, is(new MuenchenAdresse()));

        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).checkAdresse(adresse,
                strassenname,
                strasseId,
                hausnummer,
                zusatz,
                plz,
                ortsname,
                gemeindeschluessel
        );
    }

    @Test
    void checkAdresseException() {
        final String adresse = "adresse";
        final String strassenname = "strassenname";
        final Integer strasseId = 1;
        final String hausnummer = "hausnummer";
        final String zusatz = "zusatz";
        final String plz = "plz";
        final String ortsname = "ortsname";
        final String gemeindeschluessel = "gemeindeschluessel";

        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.adressenMnchenApi).checkAdresse(
                adresse,
                strassenname,
                strasseId,
                hausnummer,
                zusatz,
                plz,
                ortsname,
                gemeindeschluessel);
        Assertions.assertThrows(AddressServiceIntegrationClientErrorException.class, () -> this.adressenMuenchenRepository.checkAdresse(
                adresse,
                strassenname,
                strasseId,
                hausnummer,
                zusatz,
                plz,
                ortsname,
                gemeindeschluessel)
        );
        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).checkAdresse(adresse,
                strassenname,
                strasseId,
                hausnummer,
                zusatz,
                plz,
                ortsname,
                gemeindeschluessel);
        Mockito.reset(this.adressenMnchenApi);

        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.adressenMnchenApi).checkAdresse(
                adresse,
                strassenname,
                strasseId,
                hausnummer,
                zusatz,
                plz,
                ortsname,
                gemeindeschluessel);
        Assertions.assertThrows(AddressServiceIntegrationServerErrorException.class, () -> this.adressenMuenchenRepository.checkAdresse(
                adresse,
                strassenname,
                strasseId,
                hausnummer,
                zusatz,
                plz,
                ortsname,
                gemeindeschluessel)
        );
        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).checkAdresse(
                adresse,
                strassenname,
                strasseId,
                hausnummer,
                zusatz,
                plz,
                ortsname,
                gemeindeschluessel);
        Mockito.reset(this.adressenMnchenApi);

        Mockito.doThrow(new RestClientException("Something happened")).when(this.adressenMnchenApi).checkAdresse(adresse,
                strassenname,
                strasseId,
                hausnummer,
                zusatz,
                plz,
                ortsname,
                gemeindeschluessel);
        Assertions.assertThrows(AddressServiceIntegrationException.class, () -> this.adressenMuenchenRepository.checkAdresse(
                adresse,
                strassenname,
                strasseId,
                hausnummer,
                zusatz,
                plz,
                ortsname,
                gemeindeschluessel)
        );
        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).checkAdresse(adresse,
                strassenname,
                strasseId,
                hausnummer,
                zusatz,
                plz,
                ortsname,
                gemeindeschluessel);
        Mockito.reset(this.adressenMnchenApi);
    }

    @Test
    void listAdressen() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final List<String> baublock = List.of("baublock");
        final List<String> erhaltungssatzung = List.of("erhaltungssatzung");
        final List<String> gemarkung = List.of("gemarkung");
        final List<String> kaminkehrerbezirk = List.of("kaminkehrerbezirk");
        final List<String> plz = List.of("plz");
        final List<String> mittelschule = List.of("mittelschule");
        final List<String> grundschule = List.of("grundschule");
        final List<String> polizeiinspektion = List.of("polizeiinspektion");
        final List<Long> stimmbezirk = List.of(1L);
        final List<Long> stimmkreis = List.of(2L);
        final List<Long> wahlbezirk = List.of(3L);
        final List<Long> wahlkreis = List.of(4L);
        final List<String> stadtbezirk = List.of("stadtbezirk");
        final List<String> stadtbezirksteil = List.of("stadtbezirksteil");
        final List<String> stadtbezirksviertel = List.of("stadtbezirksviertel");
        final String sort = "sort";
        final String sortdir = "sortdir";
        final Integer page = 98;
        final Integer pagesize = 99;

        Mockito.when(this.adressenMnchenApi.listAdressen(
                baublock,
                erhaltungssatzung,
                gemarkung,
                kaminkehrerbezirk,
                plz,
                mittelschule,
                grundschule,
                polizeiinspektion,
                stimmbezirk,
                stimmkreis,
                wahlbezirk,
                wahlkreis,
                stadtbezirk,
                stadtbezirksteil,
                stadtbezirksviertel,
                sort,
                sortdir,
                page,
                pagesize
        )).thenReturn(new MuenchenAdresseResponse());

        final MuenchenAdresseResponse result = this.adressenMuenchenRepository.listAdressen(
                baublock,
                erhaltungssatzung,
                gemarkung,
                kaminkehrerbezirk,
                plz,
                mittelschule,
                grundschule,
                polizeiinspektion,
                stimmbezirk,
                stimmkreis,
                wahlbezirk,
                wahlkreis,
                stadtbezirk,
                stadtbezirksteil,
                stadtbezirksviertel,
                sort,
                sortdir,
                page,
                pagesize
        );

        assertThat(result, is(new MuenchenAdresseResponse()));

        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).listAdressen(
                baublock,
                erhaltungssatzung,
                gemarkung,
                kaminkehrerbezirk,
                plz,
                mittelschule,
                grundschule,
                polizeiinspektion,
                stimmbezirk,
                stimmkreis,
                wahlbezirk,
                wahlkreis,
                stadtbezirk,
                stadtbezirksteil,
                stadtbezirksviertel,
                sort,
                sortdir,
                page,
                pagesize
        );
    }

    @Test
    void listAdressenException() {
        final List<String> baublock = List.of("baublock");
        final List<String> erhaltungssatzung = List.of("erhaltungssatzung");
        final List<String> gemarkung = List.of("gemarkung");
        final List<String> kaminkehrerbezirk = List.of("kaminkehrerbezirk");
        final List<String> plz = List.of("plz");
        final List<String> mittelschule = List.of("mittelschule");
        final List<String> grundschule = List.of("grundschule");
        final List<String> polizeiinspektion = List.of("polizeiinspektion");
        final List<Long> stimmbezirk = List.of(1L);
        final List<Long> stimmkreis = List.of(2L);
        final List<Long> wahlbezirk = List.of(3L);
        final List<Long> wahlkreis = List.of(4L);
        final List<String> stadtbezirk = List.of("stadtbezirk");
        final List<String> stadtbezirksteil = List.of("stadtbezirksteil");
        final List<String> stadtbezirksviertel = List.of("stadtbezirksviertel");
        final String sort = "sort";
        final String sortdir = "sortdir";
        final Integer page = 98;
        final Integer pagesize = 99;

        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.adressenMnchenApi).listAdressen(
                baublock,
                erhaltungssatzung,
                gemarkung,
                kaminkehrerbezirk,
                plz,
                mittelschule,
                grundschule,
                polizeiinspektion,
                stimmbezirk,
                stimmkreis,
                wahlbezirk,
                wahlkreis,
                stadtbezirk,
                stadtbezirksteil,
                stadtbezirksviertel,
                sort,
                sortdir,
                page,
                pagesize);
        Assertions.assertThrows(AddressServiceIntegrationClientErrorException.class, () -> this.adressenMuenchenRepository.listAdressen(
                baublock,
                erhaltungssatzung,
                gemarkung,
                kaminkehrerbezirk,
                plz,
                mittelschule,
                grundschule,
                polizeiinspektion,
                stimmbezirk,
                stimmkreis,
                wahlbezirk,
                wahlkreis,
                stadtbezirk,
                stadtbezirksteil,
                stadtbezirksviertel,
                sort,
                sortdir,
                page,
                pagesize)
        );
        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).listAdressen(baublock,
                erhaltungssatzung,
                gemarkung,
                kaminkehrerbezirk,
                plz,
                mittelschule,
                grundschule,
                polizeiinspektion,
                stimmbezirk,
                stimmkreis,
                wahlbezirk,
                wahlkreis,
                stadtbezirk,
                stadtbezirksteil,
                stadtbezirksviertel,
                sort,
                sortdir,
                page,
                pagesize);
        Mockito.reset(this.adressenMnchenApi);

        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.adressenMnchenApi).listAdressen(
                baublock,
                erhaltungssatzung,
                gemarkung,
                kaminkehrerbezirk,
                plz,
                mittelschule,
                grundschule,
                polizeiinspektion,
                stimmbezirk,
                stimmkreis,
                wahlbezirk,
                wahlkreis,
                stadtbezirk,
                stadtbezirksteil,
                stadtbezirksviertel,
                sort,
                sortdir,
                page,
                pagesize);
        Assertions.assertThrows(AddressServiceIntegrationServerErrorException.class, () -> this.adressenMuenchenRepository.listAdressen(
                baublock,
                erhaltungssatzung,
                gemarkung,
                kaminkehrerbezirk,
                plz,
                mittelschule,
                grundschule,
                polizeiinspektion,
                stimmbezirk,
                stimmkreis,
                wahlbezirk,
                wahlkreis,
                stadtbezirk,
                stadtbezirksteil,
                stadtbezirksviertel,
                sort,
                sortdir,
                page,
                pagesize)
        );
        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).listAdressen(
                baublock,
                erhaltungssatzung,
                gemarkung,
                kaminkehrerbezirk,
                plz,
                mittelschule,
                grundschule,
                polizeiinspektion,
                stimmbezirk,
                stimmkreis,
                wahlbezirk,
                wahlkreis,
                stadtbezirk,
                stadtbezirksteil,
                stadtbezirksviertel,
                sort,
                sortdir,
                page,
                pagesize);
        Mockito.reset(this.adressenMnchenApi);

        Mockito.doThrow(new RestClientException("Something happened")).when(this.adressenMnchenApi).listAdressen(baublock,
                erhaltungssatzung,
                gemarkung,
                kaminkehrerbezirk,
                plz,
                mittelschule,
                grundschule,
                polizeiinspektion,
                stimmbezirk,
                stimmkreis,
                wahlbezirk,
                wahlkreis,
                stadtbezirk,
                stadtbezirksteil,
                stadtbezirksviertel,
                sort,
                sortdir,
                page,
                pagesize);
        Assertions.assertThrows(AddressServiceIntegrationException.class, () -> this.adressenMuenchenRepository.listAdressen(
                baublock,
                erhaltungssatzung,
                gemarkung,
                kaminkehrerbezirk,
                plz,
                mittelschule,
                grundschule,
                polizeiinspektion,
                stimmbezirk,
                stimmkreis,
                wahlbezirk,
                wahlkreis,
                stadtbezirk,
                stadtbezirksteil,
                stadtbezirksviertel,
                sort,
                sortdir,
                page,
                pagesize)
        );
        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).listAdressen(baublock,
                erhaltungssatzung,
                gemarkung,
                kaminkehrerbezirk,
                plz,
                mittelschule,
                grundschule,
                polizeiinspektion,
                stimmbezirk,
                stimmkreis,
                wahlbezirk,
                wahlkreis,
                stadtbezirk,
                stadtbezirksteil,
                stadtbezirksviertel,
                sort,
                sortdir,
                page,
                pagesize);
        Mockito.reset(this.adressenMnchenApi);
    }

    @Test
    void listAenderungen() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final String wirkungsdatumvon = "wirkungsdatumvon";
        final String wirkungsdatumbis = "wirkungsdatumbis";
        final String strassenname = "strassenname";
        final Long hausnummer = 77L;
        final String plz = "plz";
        final String zusatz = "zusatz";
        final String sort = "sort";
        final String sortdir = "sortdir";
        final Integer page = 87;
        final Integer pagesize = 86;

        Mockito.when(this.adressenMnchenApi.listAenderungen(wirkungsdatumvon,
                wirkungsdatumbis,
                strassenname,
                hausnummer,
                plz,
                zusatz,
                sort,
                sortdir,
                page,
                pagesize)).thenReturn(new AenderungResponse());

        final AenderungResponse result = this.adressenMuenchenRepository.listAenderungen(wirkungsdatumvon,
                wirkungsdatumbis,
                strassenname,
                hausnummer,
                plz,
                zusatz,
                sort,
                sortdir,
                page,
                pagesize);

        assertThat(result, is(new AenderungResponse()));

        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).listAenderungen(wirkungsdatumvon,
                wirkungsdatumbis,
                strassenname,
                hausnummer,
                plz,
                zusatz,
                sort,
                sortdir,
                page,
                pagesize);
    }

    @Test
    void listAenderungenException() {
        final String wirkungsdatumvon = "wirkungsdatumvon";
        final String wirkungsdatumbis = "wirkungsdatumbis";
        final String strassenname = "strassenname";
        final Long hausnummer = 77L;
        final String plz = "plz";
        final String zusatz = "zusatz";
        final String sort = "sort";
        final String sortdir = "sortdir";
        final Integer page = 87;
        final Integer pagesize = 86;

        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.adressenMnchenApi).listAenderungen(
                wirkungsdatumvon,
                wirkungsdatumbis,
                strassenname,
                hausnummer,
                plz,
                zusatz,
                sort,
                sortdir,
                page,
                pagesize);
        Assertions.assertThrows(AddressServiceIntegrationClientErrorException.class, () -> this.adressenMuenchenRepository.listAenderungen(
                wirkungsdatumvon,
                wirkungsdatumbis,
                strassenname,
                hausnummer,
                plz,
                zusatz,
                sort,
                sortdir,
                page,
                pagesize)
        );
        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).listAenderungen(wirkungsdatumvon,
                wirkungsdatumbis,
                strassenname,
                hausnummer,
                plz,
                zusatz,
                sort,
                sortdir,
                page,
                pagesize);
        Mockito.reset(this.adressenMnchenApi);

        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.adressenMnchenApi).listAenderungen(
                wirkungsdatumvon,
                wirkungsdatumbis,
                strassenname,
                hausnummer,
                plz,
                zusatz,
                sort,
                sortdir,
                page,
                pagesize);
        Assertions.assertThrows(AddressServiceIntegrationServerErrorException.class, () -> this.adressenMuenchenRepository.listAenderungen(
                wirkungsdatumvon,
                wirkungsdatumbis,
                strassenname,
                hausnummer,
                plz,
                zusatz,
                sort,
                sortdir,
                page,
                pagesize)
        );
        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).listAenderungen(
                wirkungsdatumvon,
                wirkungsdatumbis,
                strassenname,
                hausnummer,
                plz,
                zusatz,
                sort,
                sortdir,
                page,
                pagesize);
        Mockito.reset(this.adressenMnchenApi);

        Mockito.doThrow(new RestClientException("Something happened")).when(this.adressenMnchenApi).listAenderungen(wirkungsdatumvon,
                wirkungsdatumbis,
                strassenname,
                hausnummer,
                plz,
                zusatz,
                sort,
                sortdir,
                page,
                pagesize);
        Assertions.assertThrows(AddressServiceIntegrationException.class, () -> this.adressenMuenchenRepository.listAenderungen(
                wirkungsdatumvon,
                wirkungsdatumbis,
                strassenname,
                hausnummer,
                plz,
                zusatz,
                sort,
                sortdir,
                page,
                pagesize)
        );
        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).listAenderungen(wirkungsdatumvon,
                wirkungsdatumbis,
                strassenname,
                hausnummer,
                plz,
                zusatz,
                sort,
                sortdir,
                page,
                pagesize);
        Mockito.reset(this.adressenMnchenApi);
    }

    @Test
    void searchAdressen() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final String query = "query";
        final List<String> plzfilter = List.of("plzfilter");
        final List<Long> hausnummerfilter = List.of(55L);
        final List<String> buchstabefilter = List.of("buchstabefilter");
        final String searchtype = "searchtype";
        final String sort = "sort";
        final String sortdir = "sortdir";
        final Integer page = 67;
        final Integer pagesize = 68;

        Mockito.when(this.adressenMnchenApi.searchAdressen1(query,
                plzfilter,
                hausnummerfilter,
                buchstabefilter,
                searchtype,
                sort,
                sortdir,
                page,
                pagesize)).thenReturn(new MuenchenAdresseResponse());

        final MuenchenAdresseResponse result = this.adressenMuenchenRepository.searchAdressen(query,
                plzfilter,
                hausnummerfilter,
                buchstabefilter,
                searchtype,
                sort,
                sortdir,
                page,
                pagesize);

        assertThat(result, is(new MuenchenAdresseResponse()));

        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).searchAdressen1(query,
                plzfilter,
                hausnummerfilter,
                buchstabefilter,
                searchtype,
                sort,
                sortdir,
                page,
                pagesize);
    }

    @Test
    void searchAdressenException() {
        final String query = "query";
        final List<String> plzfilter = List.of("plzfilter");
        final List<Long> hausnummerfilter = List.of(55L);
        final List<String> buchstabefilter = List.of("buchstabefilter");
        final String searchtype = "searchtype";
        final String sort = "sort";
        final String sortdir = "sortdir";
        final Integer page = 67;
        final Integer pagesize = 68;


        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.adressenMnchenApi).searchAdressen1(query,
                plzfilter,
                hausnummerfilter,
                buchstabefilter,
                searchtype,
                sort,
                sortdir,
                page,
                pagesize);
        Assertions.assertThrows(AddressServiceIntegrationClientErrorException.class, () -> this.adressenMuenchenRepository.searchAdressen(query,
                plzfilter,
                hausnummerfilter,
                buchstabefilter,
                searchtype,
                sort,
                sortdir,
                page,
                pagesize)
        );
        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).searchAdressen1(query,
                plzfilter,
                hausnummerfilter,
                buchstabefilter,
                searchtype,
                sort,
                sortdir,
                page,
                pagesize);
        Mockito.reset(this.adressenMnchenApi);

        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.adressenMnchenApi).searchAdressen1(
                query,
                plzfilter,
                hausnummerfilter,
                buchstabefilter,
                searchtype,
                sort,
                sortdir,
                page,
                pagesize);
        Assertions.assertThrows(AddressServiceIntegrationServerErrorException.class, () -> this.adressenMuenchenRepository.searchAdressen(
                query,
                plzfilter,
                hausnummerfilter,
                buchstabefilter,
                searchtype,
                sort,
                sortdir,
                page,
                pagesize)
        );
        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).searchAdressen1(
                query,
                plzfilter,
                hausnummerfilter,
                buchstabefilter,
                searchtype,
                sort,
                sortdir,
                page,
                pagesize);
        Mockito.reset(this.adressenMnchenApi);

        Mockito.doThrow(new RestClientException("Something happened")).when(this.adressenMnchenApi).searchAdressen1(
                query,
                plzfilter,
                hausnummerfilter,
                buchstabefilter,
                searchtype,
                sort,
                sortdir,
                page,
                pagesize);
        Assertions.assertThrows(AddressServiceIntegrationException.class, () -> this.adressenMuenchenRepository.searchAdressen(
                query,
                plzfilter,
                hausnummerfilter,
                buchstabefilter,
                searchtype,
                sort,
                sortdir,
                page,
                pagesize)
        );
        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).searchAdressen1(
                query,
                plzfilter,
                hausnummerfilter,
                buchstabefilter,
                searchtype,
                sort,
                sortdir,
                page,
                pagesize);
        Mockito.reset(this.adressenMnchenApi);
    }

    @Test
    void searchAdressenGeo() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final String geometrie = "geometrie";
        final Double lat = 89.0;
        final Double lng = 88.0;
        final Double distanz = 87.0;
        final Double topleftlat = 86.0;
        final Double topleftlng = 85.0;
        final Double bottomrightlat = 84.0;
        final Double bottomrightlng = 83.0;
        final String format = "format";

        Mockito.when(this.adressenMnchenApi.searchAdressenGeo(
                geometrie,
                lat,
                lng,
                distanz,
                topleftlat,
                topleftlng,
                bottomrightlat,
                bottomrightlng,
                format
        )).thenReturn(List.of(new AdresseDistanz()));

        final List<AdresseDistanz> result = this.adressenMuenchenRepository.searchAdressenGeo(
                geometrie,
                lat,
                lng,
                distanz,
                topleftlat,
                topleftlng,
                bottomrightlat,
                bottomrightlng,
                format
        );

        assertThat(result, is(List.of(new AdresseDistanz())));

        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).searchAdressenGeo(
                geometrie,
                lat,
                lng,
                distanz,
                topleftlat,
                topleftlng,
                bottomrightlat,
                bottomrightlng,
                format
        );
    }

    @Test
    void searchAdressenGeoException() {
        final String geometrie = "geometrie";
        final Double lat = 89.0;
        final Double lng = 88.0;
        final Double distanz = 87.0;
        final Double topleftlat = 86.0;
        final Double topleftlng = 85.0;
        final Double bottomrightlat = 84.0;
        final Double bottomrightlng = 83.0;
        final String format = "format";

        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.adressenMnchenApi).searchAdressenGeo(geometrie,
                lat,
                lng,
                distanz,
                topleftlat,
                topleftlng,
                bottomrightlat,
                bottomrightlng,
                format);
        Assertions.assertThrows(AddressServiceIntegrationClientErrorException.class, () -> this.adressenMuenchenRepository.searchAdressenGeo(geometrie,
                lat,
                lng,
                distanz,
                topleftlat,
                topleftlng,
                bottomrightlat,
                bottomrightlng,
                format)
        );
        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).searchAdressenGeo(geometrie,
                lat,
                lng,
                distanz,
                topleftlat,
                topleftlng,
                bottomrightlat,
                bottomrightlng,
                format);
        Mockito.reset(this.adressenMnchenApi);

        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.adressenMnchenApi).searchAdressenGeo(
                geometrie,
                lat,
                lng,
                distanz,
                topleftlat,
                topleftlng,
                bottomrightlat,
                bottomrightlng,
                format);
        Assertions.assertThrows(AddressServiceIntegrationServerErrorException.class, () -> this.adressenMuenchenRepository.searchAdressenGeo(
                geometrie,
                lat,
                lng,
                distanz,
                topleftlat,
                topleftlng,
                bottomrightlat,
                bottomrightlng,
                format)
        );
        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).searchAdressenGeo(
                geometrie,
                lat,
                lng,
                distanz,
                topleftlat,
                topleftlng,
                bottomrightlat,
                bottomrightlng,
                format);
        Mockito.reset(this.adressenMnchenApi);

        Mockito.doThrow(new RestClientException("Something happened")).when(this.adressenMnchenApi).searchAdressenGeo(
                geometrie,
                lat,
                lng,
                distanz,
                topleftlat,
                topleftlng,
                bottomrightlat,
                bottomrightlng,
                format);
        Assertions.assertThrows(AddressServiceIntegrationException.class, () -> this.adressenMuenchenRepository.searchAdressenGeo(
                geometrie,
                lat,
                lng,
                distanz,
                topleftlat,
                topleftlng,
                bottomrightlat,
                bottomrightlng,
                format)
        );
        Mockito.verify(this.adressenMnchenApi, Mockito.times(1)).searchAdressenGeo(
                geometrie,
                lat,
                lng,
                distanz,
                topleftlat,
                topleftlng,
                bottomrightlat,
                bottomrightlng,
                format);
        Mockito.reset(this.adressenMnchenApi);

    }

}
