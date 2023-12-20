package de.muenchen.oss.digiwf.address.integration.client.impl;

import de.muenchen.oss.digiwf.address.integration.client.api.AddressGermanyApi;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.client.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.client.gen.api.AdressenBundesweitApi;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.AddressServicePage;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.BundesweiteAdresse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.BundesweiteAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.BundesweiteAdresseResponseItem;
import de.muenchen.oss.digiwf.address.integration.client.model.request.SearchAddressesGermanyModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class AddressGermanyImplTest {

    private final AdressenBundesweitApi adressenBundesweitApi = Mockito.mock(AdressenBundesweitApi.class);
    private final AddressGermanyApi addressGermany = new AddressGermanyImpl(adressenBundesweitApi);

    private final SearchAddressesGermanyModel searchAddressesModel = SearchAddressesGermanyModel.builder().build();
    private final BundesweiteAdresseResponse bundesweiteAdresseResponse = new BundesweiteAdresseResponse();

    @BeforeEach
    void setup() {
        this.searchAddressesModel.setQuery("Sample Query");
        this.searchAddressesModel.setZip("12345");
        this.searchAddressesModel.setCityName("Sample City");
        this.searchAddressesModel.setGemeindeschluessel("456789");
        this.searchAddressesModel.setHouseNumberFilter(List.of(1L, 2L, 3L));
        this.searchAddressesModel.setLetterFilter(List.of("A", "B", "C"));
        this.searchAddressesModel.setSort("name");
        this.searchAddressesModel.setSortdir("asc");
        this.searchAddressesModel.setPage(1);
        this.searchAddressesModel.setPagesize(10);

        bundesweiteAdresseResponse.page(new AddressServicePage());
        final BundesweiteAdresseResponseItem bundesweiteAdresseResponseItem = new BundesweiteAdresseResponseItem();
        final BundesweiteAdresse bundesweiteAdresse = new BundesweiteAdresse();
        bundesweiteAdresse.setStrassenname("streetName");
        bundesweiteAdresse.setHausnummer(1L);
        bundesweiteAdresse.setOrtsname("cityName");
        bundesweiteAdresseResponseItem.setAdresse(bundesweiteAdresse);
        bundesweiteAdresseResponse.setContent(List.of(bundesweiteAdresseResponseItem));
    }

    @Test
    void testFindStreetsById_Success() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        when(this.adressenBundesweitApi.searchAdressen(
                "Sample Query",
                "12345",
                "Sample City",
                "456789",
                List.of(1L, 2L, 3L),
                List.of("A", "B", "C"),
                "name",
                "asc",
                1,
                10
        )).thenReturn(Mono.just(bundesweiteAdresseResponse));
        final BundesweiteAdresseResponse result = addressGermany.searchAddresses(this.searchAddressesModel);
        assertThat(result).isEqualTo(bundesweiteAdresseResponse);
    }

    @Test
    void testFindStreetsById_ClientErrorException() {
        when(this.adressenBundesweitApi.searchAdressen(
                "Sample Query",
                "12345",
                "Sample City",
                "456789",
                List.of(1L, 2L, 3L),
                List.of("A", "B", "C"),
                "name",
                "asc",
                1,
                10
        )).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "message"));
        assertThatThrownBy(() -> addressGermany.searchAddresses(this.searchAddressesModel))
                .isInstanceOf(AddressServiceIntegrationClientErrorException.class);
    }

    @Test
    void testFindStreetsById_ServerErrorException() {
        when(this.adressenBundesweitApi.searchAdressen(
                "Sample Query",
                "12345",
                "Sample City",
                "456789",
                List.of(1L, 2L, 3L),
                List.of("A", "B", "C"),
                "name",
                "asc",
                1,
                10
        )).thenThrow(new HttpServerErrorException(HttpStatus.BAD_REQUEST, "message"));
        assertThatThrownBy(() -> addressGermany.searchAddresses(this.searchAddressesModel))
                .isInstanceOf(AddressServiceIntegrationServerErrorException.class);
    }

    @Test
    void testFindStreetsById_RestClientException() {
        when(this.adressenBundesweitApi.searchAdressen(
                "Sample Query",
                "12345",
                "Sample City",
                "456789",
                List.of(1L, 2L, 3L),
                List.of("A", "B", "C"),
                "name",
                "asc",
                1,
                10
        )).thenThrow(new RestClientException(""));
        assertThatThrownBy(() -> addressGermany.searchAddresses(this.searchAddressesModel))
                .isInstanceOf(AddressServiceIntegrationException.class);
    }
}
