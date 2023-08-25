package de.muenchen.oss.digiwf.address.service.integration.repository;

import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.service.integration.gen.api.StraenMnchenApi;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.Strasse;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.StrasseResponse;
import de.muenchen.oss.digiwf.address.service.integration.repository.StrassenMuenchenRepository;
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
class StrassenMuenchenRepositoryTest {

    @Mock
    private StraenMnchenApi straenMnchenApi;

    private StrassenMuenchenRepository strassenMuenchenRepository;

    @BeforeEach
    public void beforeEach() {
        this.strassenMuenchenRepository = new StrassenMuenchenRepository(this.straenMnchenApi);
        Mockito.reset(this.straenMnchenApi);
    }

    @Test
    void findStrasseById() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final Long strasseId = 99L;

        Mockito.when(this.straenMnchenApi.findStrasseByNummer(strasseId)).thenReturn(new Strasse());

        final Strasse result = this.strassenMuenchenRepository.findStrasseById(strasseId);

        assertThat(result, is(new Strasse()));

        Mockito.verify(this.straenMnchenApi, Mockito.times(1)).findStrasseByNummer(strasseId);
    }

    @Test
    void findStrasseByIdException() {
        final Long strasseId = 99L;

        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.straenMnchenApi).findStrasseByNummer(
                strasseId);
        Assertions.assertThrows(AddressServiceIntegrationClientErrorException.class, () -> this.strassenMuenchenRepository.findStrasseById(
                strasseId)
        );
        Mockito.verify(this.straenMnchenApi, Mockito.times(1)).findStrasseByNummer(strasseId);
        Mockito.reset(this.straenMnchenApi);

        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.straenMnchenApi).findStrasseByNummer(
                strasseId);
        Assertions.assertThrows(AddressServiceIntegrationServerErrorException.class, () -> this.strassenMuenchenRepository.findStrasseById(
                strasseId)
        );
        Mockito.verify(this.straenMnchenApi, Mockito.times(1)).findStrasseByNummer(
                strasseId);
        Mockito.reset(this.straenMnchenApi);

        Mockito.doThrow(new RestClientException("Something happened")).when(this.straenMnchenApi).findStrasseByNummer(strasseId);
        Assertions.assertThrows(AddressServiceIntegrationException.class, () -> this.strassenMuenchenRepository.findStrasseById(
                strasseId)
        );
        Mockito.verify(this.straenMnchenApi, Mockito.times(1)).findStrasseByNummer(strasseId);
        Mockito.reset(this.straenMnchenApi);
    }

    @Test
    void listStrassen() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final List<String> stadtbezirksnamen = List.of("stadtbezirksnamen");
        final List<Long> stadtbezirksnummern = List.of(98L);
        final String strassenname = "strassenname";
        final String sortdir = "sortdir";
        final Integer page = 9;
        final Integer pagesize = 10;


        Mockito.when(this.straenMnchenApi.listStrassen(
                stadtbezirksnamen,
                stadtbezirksnummern,
                strassenname,
                sortdir,
                page,
                pagesize
        )).thenReturn(new StrasseResponse());

        final StrasseResponse result = this.strassenMuenchenRepository.listStrassen(
                stadtbezirksnamen,
                stadtbezirksnummern,
                strassenname,
                sortdir,
                page,
                pagesize
        );

        assertThat(result, is(new StrasseResponse()));

        Mockito.verify(this.straenMnchenApi, Mockito.times(1)).listStrassen(
                stadtbezirksnamen,
                stadtbezirksnummern,
                strassenname,
                sortdir,
                page,
                pagesize
        );
    }

    @Test
    void listStrassenException() {
        final List<String> stadtbezirksnamen = List.of("stadtbezirksnamen");
        final List<Long> stadtbezirksnummern = List.of(98L);
        final String strassenname = "strassenname";
        final String sortdir = "sortdir";
        final Integer page = 9;
        final Integer pagesize = 10;

        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.straenMnchenApi).listStrassen(
                stadtbezirksnamen,
                stadtbezirksnummern,
                strassenname,
                sortdir,
                page,
                pagesize
        );
        Assertions.assertThrows(AddressServiceIntegrationClientErrorException.class, () -> this.strassenMuenchenRepository.listStrassen(
                        stadtbezirksnamen,
                        stadtbezirksnummern,
                        strassenname,
                        sortdir,
                        page,
                        pagesize
                )
        );
        Mockito.verify(this.straenMnchenApi, Mockito.times(1)).listStrassen(
                stadtbezirksnamen,
                stadtbezirksnummern,
                strassenname,
                sortdir,
                page,
                pagesize
        );
        Mockito.reset(this.straenMnchenApi);

        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.straenMnchenApi).listStrassen(
                stadtbezirksnamen,
                stadtbezirksnummern,
                strassenname,
                sortdir,
                page,
                pagesize
        );
        Assertions.assertThrows(AddressServiceIntegrationServerErrorException.class, () -> this.strassenMuenchenRepository.listStrassen(
                        stadtbezirksnamen,
                        stadtbezirksnummern,
                        strassenname,
                        sortdir,
                        page,
                        pagesize
                )
        );
        Mockito.verify(this.straenMnchenApi, Mockito.times(1)).listStrassen(
                stadtbezirksnamen,
                stadtbezirksnummern,
                strassenname,
                sortdir,
                page,
                pagesize
        );
        Mockito.reset(this.straenMnchenApi);

        Mockito.doThrow(new RestClientException("Something happened")).when(this.straenMnchenApi).listStrassen(
                stadtbezirksnamen,
                stadtbezirksnummern,
                strassenname,
                sortdir,
                page,
                pagesize
        );
        Assertions.assertThrows(AddressServiceIntegrationException.class, () -> this.strassenMuenchenRepository.listStrassen(
                        stadtbezirksnamen,
                        stadtbezirksnummern,
                        strassenname,
                        sortdir,
                        page,
                        pagesize
                )
        );
        Mockito.verify(this.straenMnchenApi, Mockito.times(1)).listStrassen(stadtbezirksnamen,
                stadtbezirksnummern,
                strassenname,
                sortdir,
                page,
                pagesize
        );
        Mockito.reset(this.straenMnchenApi);
    }

}
