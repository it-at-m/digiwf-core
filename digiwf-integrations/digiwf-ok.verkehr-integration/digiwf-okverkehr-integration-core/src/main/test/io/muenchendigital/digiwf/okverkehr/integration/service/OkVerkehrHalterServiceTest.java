package io.muenchendigital.digiwf.okverkehr.integration.service;

import io.muenchendigital.digiwf.okverkehr.integration.exception.OkVerkehrIntegrationClientErrorException;
import io.muenchendigital.digiwf.okverkehr.integration.exception.OkVerkehrIntegrationException;
import io.muenchendigital.digiwf.okverkehr.integration.exception.OkVerkehrIntegrationServerErrorException;
import io.muenchendigital.digiwf.okverkehr.integration.gen.model.HalterPersonAnfrage;
import io.muenchendigital.digiwf.okverkehr.integration.gen.model.HalterPersonAntwort;
import io.muenchendigital.digiwf.okverkehr.integration.repository.OkVerkehrHalterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OkVerkehrHalterServiceTest {

    private OkVerkehrHalterService okVerkehrHalterService;

    @Mock
    private OkVerkehrHalterRepository okVerkehrHalterRepository;

    @BeforeEach
    public void beforeEach() {
        this.okVerkehrHalterService = new OkVerkehrHalterService(this.okVerkehrHalterRepository);
        Mockito.reset(this.okVerkehrHalterRepository);
    }

    @Test
    void getHalter() throws OkVerkehrIntegrationClientErrorException, OkVerkehrIntegrationServerErrorException, OkVerkehrIntegrationException {
        final var halterPersonAnfrage = new HalterPersonAnfrage();

        Mockito.when(this.okVerkehrHalterRepository.getHalter(halterPersonAnfrage)).thenReturn(new HalterPersonAntwort());

        final HalterPersonAntwort result = this.okVerkehrHalterService.getHalter(halterPersonAnfrage);
        assertThat(result, is(new HalterPersonAntwort()));

        Mockito.verify(this.okVerkehrHalterRepository, Mockito.times(1)).getHalter(halterPersonAnfrage);
    }

}
