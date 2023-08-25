package de.muenchen.oss.digiwf.address.service.integration.repository;

import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.service.integration.gen.api.AdressenBundesweitApi;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.BundesweiteAdresseResponse;
import de.muenchen.oss.digiwf.address.service.integration.repository.AdressenBundesweitRepository;
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
class AdressenBundesweitRepositoryTest {

    @Mock
    private AdressenBundesweitApi adressenBundesweitApi;

    private AdressenBundesweitRepository adressenBundesweitRepository;

    @BeforeEach
    public void beforeEach() {
        this.adressenBundesweitRepository = new AdressenBundesweitRepository(this.adressenBundesweitApi);
        Mockito.reset(this.adressenBundesweitApi);
    }

    @Test
    void searchAdressen() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final String query = "query";
        final String plz = "plz";
        final String ortsname = "ortsname";
        final String gemeindeschluessel = "gemeindeschluessel";
        final List<Long> hausnummerfilter = List.of(1L);
        final List<String> buchstabefilter = List.of("buchstabefilter");
        final String sort = "sort";
        final String sortdir = "sortdir";
        final Integer page = 2;
        final Integer pagesize = 3;

        Mockito.when(this.adressenBundesweitApi.searchAdressen(
                query,
                plz,
                ortsname,
                gemeindeschluessel,
                hausnummerfilter,
                buchstabefilter,
                sort,
                sortdir,
                page,
                pagesize)).thenReturn(new BundesweiteAdresseResponse());

        final BundesweiteAdresseResponse result = this.adressenBundesweitRepository.searchAdressen(
                query,
                plz,
                ortsname,
                gemeindeschluessel,
                hausnummerfilter,
                buchstabefilter,
                sort,
                sortdir,
                page,
                pagesize
        );

        assertThat(result, is(new BundesweiteAdresseResponse()));
        Mockito.verify(this.adressenBundesweitApi, Mockito.times(1)).searchAdressen(
                query,
                plz,
                ortsname,
                gemeindeschluessel,
                hausnummerfilter,
                buchstabefilter,
                sort,
                sortdir,
                page,
                pagesize);

    }

    @Test
    void searchAdressenException() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final String query = "query";
        final String plz = "plz";
        final String ortsname = "ortsname";
        final String gemeindeschluessel = "gemeindeschluessel";
        final List<Long> hausnummerfilter = List.of(1L);
        final List<String> buchstabefilter = List.of("buchstabefilter");
        final String sort = "sort";
        final String sortdir = "sortdir";
        final Integer page = 2;
        final Integer pagesize = 3;

        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.adressenBundesweitApi).searchAdressen(query,
                plz,
                ortsname,
                gemeindeschluessel,
                hausnummerfilter,
                buchstabefilter,
                sort,
                sortdir,
                page,
                pagesize);
        Assertions.assertThrows(AddressServiceIntegrationClientErrorException.class, () -> this.adressenBundesweitRepository.searchAdressen(query,
                plz,
                ortsname,
                gemeindeschluessel,
                hausnummerfilter,
                buchstabefilter,
                sort,
                sortdir,
                page,
                pagesize)
        );
        Mockito.verify(this.adressenBundesweitApi, Mockito.times(1)).searchAdressen(query,
                plz,
                ortsname,
                gemeindeschluessel,
                hausnummerfilter,
                buchstabefilter,
                sort,
                sortdir,
                page,
                pagesize);
        Mockito.reset(this.adressenBundesweitApi);

        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.adressenBundesweitApi).searchAdressen(query,
                plz,
                ortsname,
                gemeindeschluessel,
                hausnummerfilter,
                buchstabefilter,
                sort,
                sortdir,
                page,
                pagesize);
        Assertions.assertThrows(AddressServiceIntegrationServerErrorException.class, () -> this.adressenBundesweitRepository.searchAdressen(query,
                plz,
                ortsname,
                gemeindeschluessel,
                hausnummerfilter,
                buchstabefilter,
                sort,
                sortdir,
                page,
                pagesize)
        );
        Mockito.verify(this.adressenBundesweitApi, Mockito.times(1)).searchAdressen(query,
                plz,
                ortsname,
                gemeindeschluessel,
                hausnummerfilter,
                buchstabefilter,
                sort,
                sortdir,
                page,
                pagesize);
        Mockito.reset(this.adressenBundesweitApi);

        Mockito.doThrow(new RestClientException("Something happened")).when(this.adressenBundesweitApi).searchAdressen(query,
                plz,
                ortsname,
                gemeindeschluessel,
                hausnummerfilter,
                buchstabefilter,
                sort,
                sortdir,
                page,
                pagesize);
        Assertions.assertThrows(AddressServiceIntegrationException.class, () -> this.adressenBundesweitRepository.searchAdressen(query,
                plz,
                ortsname,
                gemeindeschluessel,
                hausnummerfilter,
                buchstabefilter,
                sort,
                sortdir,
                page,
                pagesize)
        );
        Mockito.verify(this.adressenBundesweitApi, Mockito.times(1)).searchAdressen(query,
                plz,
                ortsname,
                gemeindeschluessel,
                hausnummerfilter,
                buchstabefilter,
                sort,
                sortdir,
                page,
                pagesize);
        Mockito.reset(this.adressenBundesweitApi);
    }

}
