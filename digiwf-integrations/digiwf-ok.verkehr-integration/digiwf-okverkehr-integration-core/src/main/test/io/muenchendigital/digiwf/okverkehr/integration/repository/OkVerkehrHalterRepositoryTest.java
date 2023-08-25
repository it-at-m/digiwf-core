package io.muenchendigital.digiwf.okverkehr.integration.repository;

import io.muenchendigital.digiwf.okverkehr.integration.exception.OkVerkehrIntegrationClientErrorException;
import io.muenchendigital.digiwf.okverkehr.integration.exception.OkVerkehrIntegrationException;
import io.muenchendigital.digiwf.okverkehr.integration.exception.OkVerkehrIntegrationServerErrorException;
import io.muenchendigital.digiwf.okverkehr.integration.gen.api.BusinessActionskfzhalterpersonenApi;
import io.muenchendigital.digiwf.okverkehr.integration.gen.model.HalterPersonAnfrage;
import io.muenchendigital.digiwf.okverkehr.integration.gen.model.HalterPersonAntwort;
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
class OkVerkehrHalterRepositoryTest {

    private OkVerkehrHalterRepository okVerkehrHalterRepository;

    @Mock
    private BusinessActionskfzhalterpersonenApi businessActionskfzhalterpersonenApi;

    @BeforeEach
    public void beforeEach() {
        this.okVerkehrHalterRepository = new OkVerkehrHalterRepository(this.businessActionskfzhalterpersonenApi);
        Mockito.reset(this.businessActionskfzhalterpersonenApi);
    }

    @Test
    void getHalter() throws OkVerkehrIntegrationClientErrorException, OkVerkehrIntegrationServerErrorException, OkVerkehrIntegrationException {
        final var halterPersonAnfrage = new HalterPersonAnfrage();

        Mockito.when(this.businessActionskfzhalterpersonenApi.route1(halterPersonAnfrage)).thenReturn(new HalterPersonAntwort());

        final HalterPersonAntwort result = this.okVerkehrHalterRepository.getHalter(halterPersonAnfrage);
        assertThat(result, is(new HalterPersonAntwort()));

        Mockito.verify(this.businessActionskfzhalterpersonenApi, Mockito.times(1)).route1(halterPersonAnfrage);
    }

    @Test
    void getHalterException() {
        final var halterPersonAnfrage = new HalterPersonAnfrage();

        Mockito.doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST)).when(this.businessActionskfzhalterpersonenApi).route1(halterPersonAnfrage);
        Assertions.assertThrows(OkVerkehrIntegrationClientErrorException.class, () -> this.okVerkehrHalterRepository.getHalter(halterPersonAnfrage));
        Mockito.verify(this.businessActionskfzhalterpersonenApi, Mockito.times(1)).route1(halterPersonAnfrage);
        Mockito.reset(this.businessActionskfzhalterpersonenApi);

        Mockito.doThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(this.businessActionskfzhalterpersonenApi).route1(halterPersonAnfrage);
        Assertions.assertThrows(OkVerkehrIntegrationServerErrorException.class, () -> this.okVerkehrHalterRepository.getHalter(halterPersonAnfrage));
        Mockito.verify(this.businessActionskfzhalterpersonenApi, Mockito.times(1)).route1(halterPersonAnfrage);
        Mockito.reset(this.businessActionskfzhalterpersonenApi);

        Mockito.doThrow(new RestClientException("Something happened")).when(this.businessActionskfzhalterpersonenApi).route1(halterPersonAnfrage);
        Assertions.assertThrows(OkVerkehrIntegrationException.class, () -> this.okVerkehrHalterRepository.getHalter(halterPersonAnfrage));
        Mockito.verify(this.businessActionskfzhalterpersonenApi, Mockito.times(1)).route1(halterPersonAnfrage);
        Mockito.reset(this.businessActionskfzhalterpersonenApi);
    }

}
