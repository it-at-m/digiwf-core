package io.muenchendigital.digiwf.okewo.integration.service;

import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationClientErrorException;
import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationException;
import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationServerErrorException;
import io.muenchendigital.digiwf.okewo.integration.gen.model.PersonErweitert;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonerweitertAnfrage;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonerweitertAntwort;
import io.muenchendigital.digiwf.okewo.integration.repository.OkEwoPersonErweitertRepository;
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
class OkEwoPersonErweitertServiceTest {

    @Mock
    private OkEwoPersonErweitertRepository okEwoPersonErweitertRepository;

    private final PropertiesService propertiesService = new PropertiesService("benutzerId");

    private OkEwoPersonErweitertService okEwoPersonErweitertService;

    @BeforeEach
    public void beforeEach() {
        this.okEwoPersonErweitertService = new OkEwoPersonErweitertService(this.okEwoPersonErweitertRepository, this.propertiesService);
        Mockito.reset(this.okEwoPersonErweitertRepository);
    }

    @Test
    void getPerson() throws OkEwoIntegrationException, OkEwoIntegrationClientErrorException, OkEwoIntegrationServerErrorException {
        final String om = "ordnungsmerkmal";
        final String benutzerId = "benutzerId";

        Mockito.when(this.okEwoPersonErweitertRepository.getPerson(om, benutzerId)).thenReturn(new PersonErweitert());

        final PersonErweitert result = this.okEwoPersonErweitertService.getPerson(om);
        assertThat(result, is(new PersonErweitert()));

        Mockito.verify(this.okEwoPersonErweitertRepository, Mockito.times(1)).getPerson(om, benutzerId);
    }

    @Test
    void searchPerson() throws OkEwoIntegrationException, OkEwoIntegrationClientErrorException, OkEwoIntegrationServerErrorException {
        final SuchePersonerweitertAnfrage suchePersonerweitertAnfrage = new SuchePersonerweitertAnfrage();
        suchePersonerweitertAnfrage.setBenutzer(this.propertiesService.getBenutzerTypeWithBenutzerId());

        Mockito.when(this.okEwoPersonErweitertRepository.searchPerson(suchePersonerweitertAnfrage)).thenReturn(new SuchePersonerweitertAntwort());

        final SuchePersonerweitertAntwort result = this.okEwoPersonErweitertService.searchPerson(new SuchePersonerweitertAnfrage());
        assertThat(result, is(new SuchePersonerweitertAntwort()));

        Mockito.verify(this.okEwoPersonErweitertRepository, Mockito.times(1)).searchPerson(suchePersonerweitertAnfrage);
    }

}