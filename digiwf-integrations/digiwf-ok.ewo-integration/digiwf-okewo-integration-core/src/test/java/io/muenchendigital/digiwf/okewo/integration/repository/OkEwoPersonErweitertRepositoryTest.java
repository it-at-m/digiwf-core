package io.muenchendigital.digiwf.okewo.integration.repository;

import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationClientErrorException;
import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationException;
import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationServerErrorException;
import io.muenchendigital.digiwf.okewo.integration.gen.api.PersonErweitertApi;
import io.muenchendigital.digiwf.okewo.integration.gen.model.PersonErweitert;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonerweitertAnfrage;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonerweitertAntwort;
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
class OkEwoPersonErweitertRepositoryTest {

    @Mock
    private PersonErweitertApi personErweitertApi;

    private OkEwoPersonErweitertRepository okEwoPersonErweitertRepository;

    @BeforeEach
    public void beforeEach() {
        this.okEwoPersonErweitertRepository = new OkEwoPersonErweitertRepository(this.personErweitertApi);
        Mockito.reset(this.personErweitertApi);
    }

    @Test
    void getPerson() throws OkEwoIntegrationException, OkEwoIntegrationClientErrorException, OkEwoIntegrationServerErrorException {
        final String om = "ordnungsmerkmal";
        final String benutzerId = "benutzerId";

        Mockito.when(this.personErweitertApi.deMuenchenEaiEwoRouteROUTEPROCESSGETPERSONERWEITERT(om, benutzerId)).thenReturn(new PersonErweitert());

        final PersonErweitert result = this.okEwoPersonErweitertRepository.getPerson(om, benutzerId);
        assertThat(result, is(new PersonErweitert()));

        Mockito.verify(this.personErweitertApi, Mockito.times(1)).deMuenchenEaiEwoRouteROUTEPROCESSGETPERSONERWEITERT(om, benutzerId);
    }

    @Test
    void getPersonException() {
        final String om = "ordnungsmerkmal";
        final String benutzerId = "benutzerId";

        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.personErweitertApi).deMuenchenEaiEwoRouteROUTEPROCESSGETPERSONERWEITERT(om, benutzerId);
        Assertions.assertThrows(OkEwoIntegrationClientErrorException.class, () -> this.okEwoPersonErweitertRepository.getPerson(om, benutzerId));
        Mockito.verify(this.personErweitertApi, Mockito.times(1)).deMuenchenEaiEwoRouteROUTEPROCESSGETPERSONERWEITERT(om, benutzerId);
        Mockito.reset(this.personErweitertApi);

        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.personErweitertApi).deMuenchenEaiEwoRouteROUTEPROCESSGETPERSONERWEITERT(om, benutzerId);
        Assertions.assertThrows(OkEwoIntegrationServerErrorException.class, () -> this.okEwoPersonErweitertRepository.getPerson(om, benutzerId));
        Mockito.verify(this.personErweitertApi, Mockito.times(1)).deMuenchenEaiEwoRouteROUTEPROCESSGETPERSONERWEITERT(om, benutzerId);
        Mockito.reset(this.personErweitertApi);

        Mockito.doThrow(new RestClientException("Something happened")).when(this.personErweitertApi).deMuenchenEaiEwoRouteROUTEPROCESSGETPERSONERWEITERT(om, benutzerId);
        Assertions.assertThrows(OkEwoIntegrationException.class, () -> this.okEwoPersonErweitertRepository.getPerson(om, benutzerId));
        Mockito.verify(this.personErweitertApi, Mockito.times(1)).deMuenchenEaiEwoRouteROUTEPROCESSGETPERSONERWEITERT(om, benutzerId);
        Mockito.reset(this.personErweitertApi);
    }

    @Test
    void searchPerson() throws OkEwoIntegrationException, OkEwoIntegrationClientErrorException, OkEwoIntegrationServerErrorException {
        final SuchePersonerweitertAnfrage suchePersonerweitertAnfrage = new SuchePersonerweitertAnfrage();

        Mockito.when(this.personErweitertApi.deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSONERWEITERT(suchePersonerweitertAnfrage)).thenReturn(new SuchePersonerweitertAntwort());

        final SuchePersonerweitertAntwort result = this.okEwoPersonErweitertRepository.searchPerson(suchePersonerweitertAnfrage);
        assertThat(result, is(new SuchePersonerweitertAntwort()));

        Mockito.verify(this.personErweitertApi, Mockito.times(1)).deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSONERWEITERT(suchePersonerweitertAnfrage);
    }

    @Test
    void searchPersonException() {
        final SuchePersonerweitertAnfrage suchePersonerweitertAnfrage = new SuchePersonerweitertAnfrage();

        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.personErweitertApi).deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSONERWEITERT(suchePersonerweitertAnfrage);
        Assertions.assertThrows(OkEwoIntegrationClientErrorException.class, () -> this.okEwoPersonErweitertRepository.searchPerson(suchePersonerweitertAnfrage));
        Mockito.verify(this.personErweitertApi, Mockito.times(1)).deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSONERWEITERT(suchePersonerweitertAnfrage);
        Mockito.reset(this.personErweitertApi);

        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.personErweitertApi).deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSONERWEITERT(suchePersonerweitertAnfrage);
        Assertions.assertThrows(OkEwoIntegrationServerErrorException.class, () -> this.okEwoPersonErweitertRepository.searchPerson(suchePersonerweitertAnfrage));
        Mockito.verify(this.personErweitertApi, Mockito.times(1)).deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSONERWEITERT(suchePersonerweitertAnfrage);
        Mockito.reset(this.personErweitertApi);

        Mockito.doThrow(new RestClientException("Something happened")).when(this.personErweitertApi).deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSONERWEITERT(suchePersonerweitertAnfrage);
        Assertions.assertThrows(OkEwoIntegrationException.class, () -> this.okEwoPersonErweitertRepository.searchPerson(suchePersonerweitertAnfrage));
        Mockito.verify(this.personErweitertApi, Mockito.times(1)).deMuenchenEaiEwoRouteROUTEPROCESSSEARCHPERSONERWEITERT(suchePersonerweitertAnfrage);
        Mockito.reset(this.personErweitertApi);
    }

}
