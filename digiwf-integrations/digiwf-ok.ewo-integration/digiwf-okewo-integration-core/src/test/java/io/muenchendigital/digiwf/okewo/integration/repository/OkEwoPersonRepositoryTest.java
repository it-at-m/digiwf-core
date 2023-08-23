package io.muenchendigital.digiwf.okewo.integration.repository;

import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationClientErrorException;
import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationException;
import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationServerErrorException;
import io.muenchendigital.digiwf.okewo.integration.gen.api.PersonApi;
import io.muenchendigital.digiwf.okewo.integration.gen.model.Person;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonAnfrage;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonAntwort;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OkEwoPersonRepositoryTest {

    @Mock
    private PersonApi personApi;

    private OkEwoPersonRepository okEwoPersonRepository;

    @BeforeEach
    public void beforeEach() {
        this.okEwoPersonRepository = new OkEwoPersonRepository(this.personApi);
        Mockito.reset(this.personApi);
    }

    @Test
    void getPerson() throws OkEwoIntegrationException, OkEwoIntegrationClientErrorException, OkEwoIntegrationServerErrorException {
        final String om = "ordnungsmerkmal";
        final String benutzerId = "benutzerId";

        Mockito.when(this.personApi.deMuenchenEaiEwoRouteROUTEPROCESSGETPERSON(om, benutzerId)).thenReturn(new Person());

        final Person result = this.okEwoPersonRepository.getPerson(om, benutzerId);
        assertThat(result, is(new Person()));

        Mockito.verify(this.personApi, Mockito.times(1)).deMuenchenEaiEwoRouteROUTEPROCESSGETPERSON(om, benutzerId);
    }

    @Test
    void getPersonException() {
        final String om = "ordnungsmerkmal";
        final String benutzerId = "benutzerId";

        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.personApi).deMuenchenEaiEwoRouteROUTEPROCESSGETPERSON(om, benutzerId);
        Assertions.assertThrows(OkEwoIntegrationClientErrorException.class, () -> this.okEwoPersonRepository.getPerson(om, benutzerId));
        Mockito.verify(this.personApi, Mockito.times(1)).deMuenchenEaiEwoRouteROUTEPROCESSGETPERSON(om, benutzerId);
        Mockito.reset(this.personApi);

        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.personApi).deMuenchenEaiEwoRouteROUTEPROCESSGETPERSON(om, benutzerId);
        Assertions.assertThrows(OkEwoIntegrationServerErrorException.class, () -> this.okEwoPersonRepository.getPerson(om, benutzerId));
        Mockito.verify(this.personApi, Mockito.times(1)).deMuenchenEaiEwoRouteROUTEPROCESSGETPERSON(om, benutzerId);
        Mockito.reset(this.personApi);

        Mockito.doThrow(new RestClientException("Something happened")).when(this.personApi).deMuenchenEaiEwoRouteROUTEPROCESSGETPERSON(om, benutzerId);
        Assertions.assertThrows(OkEwoIntegrationException.class, () -> this.okEwoPersonRepository.getPerson(om, benutzerId));
        Mockito.verify(this.personApi, Mockito.times(1)).deMuenchenEaiEwoRouteROUTEPROCESSGETPERSON(om, benutzerId);
        Mockito.reset(this.personApi);
    }

    @Test
    void searchPerson() throws OkEwoIntegrationException, OkEwoIntegrationClientErrorException, OkEwoIntegrationServerErrorException {
        final SuchePersonAnfrage suchePersonAnfrage = new SuchePersonAnfrage();

        Mockito.when(this.personApi.deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSON(suchePersonAnfrage)).thenReturn(new SuchePersonAntwort());

        final SuchePersonAntwort result = this.okEwoPersonRepository.searchPerson(suchePersonAnfrage);
        assertThat(result, is(new SuchePersonAntwort()));

        Mockito.verify(this.personApi, Mockito.times(1)).deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSON(suchePersonAnfrage);
    }

    @Test
    void searchPersonException() {
        final SuchePersonAnfrage suchePersonAnfrage = new SuchePersonAnfrage();

        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.personApi).deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSON(suchePersonAnfrage);
        Assertions.assertThrows(OkEwoIntegrationClientErrorException.class, () -> this.okEwoPersonRepository.searchPerson(suchePersonAnfrage));
        Mockito.verify(this.personApi, Mockito.times(1)).deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSON(suchePersonAnfrage);
        Mockito.reset(this.personApi);

        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.personApi).deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSON(suchePersonAnfrage);
        Assertions.assertThrows(OkEwoIntegrationServerErrorException.class, () -> this.okEwoPersonRepository.searchPerson(suchePersonAnfrage));
        Mockito.verify(this.personApi, Mockito.times(1)).deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSON(suchePersonAnfrage);
        Mockito.reset(this.personApi);

        Mockito.doThrow(new RestClientException("Something happened")).when(this.personApi).deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSON(suchePersonAnfrage);
        Assertions.assertThrows(OkEwoIntegrationException.class, () -> this.okEwoPersonRepository.searchPerson(suchePersonAnfrage));
        Mockito.verify(this.personApi, Mockito.times(1)).deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSON(suchePersonAnfrage);
        Mockito.reset(this.personApi);
    }

}
