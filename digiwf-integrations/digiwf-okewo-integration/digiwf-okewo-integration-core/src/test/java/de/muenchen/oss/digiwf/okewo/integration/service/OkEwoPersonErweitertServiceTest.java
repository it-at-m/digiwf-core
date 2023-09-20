package de.muenchen.oss.digiwf.okewo.integration.service;

import de.muenchen.oss.digiwf.okewo.integration.gen.model.SuchePersonerweitertAnfrage;
import de.muenchen.oss.digiwf.okewo.integration.gen.model.SuchePersonerweitertAntwort;
import de.muenchen.oss.digiwf.okewo.integration.exception.OkEwoIntegrationClientErrorException;
import de.muenchen.oss.digiwf.okewo.integration.exception.OkEwoIntegrationException;
import de.muenchen.oss.digiwf.okewo.integration.exception.OkEwoIntegrationServerErrorException;
import de.muenchen.oss.digiwf.okewo.integration.gen.model.PersonErweitert;
import de.muenchen.oss.digiwf.okewo.integration.repository.OkEwoPersonErweitertRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

class OkEwoPersonErweitertServiceTest {

    private final OkEwoPersonErweitertRepository okEwoPersonErweitertRepository = mock(OkEwoPersonErweitertRepository.class);

    private final PropertiesServiceTemplate propertiesServiceTemplate = new PropertiesServiceTemplate("benutzerId");

    private final OkEwoPersonErweitertService okEwoPersonErweitertService = new OkEwoPersonErweitertService(this.okEwoPersonErweitertRepository, this.propertiesServiceTemplate);

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
        suchePersonerweitertAnfrage.setBenutzer(this.propertiesServiceTemplate.getBenutzerTypeWithBenutzerId());

        Mockito.when(this.okEwoPersonErweitertRepository.searchPerson(suchePersonerweitertAnfrage)).thenReturn(new SuchePersonerweitertAntwort());

        final SuchePersonerweitertAntwort result = this.okEwoPersonErweitertService.searchPerson(new SuchePersonerweitertAnfrage());
        assertThat(result, is(new SuchePersonerweitertAntwort()));

        Mockito.verify(this.okEwoPersonErweitertRepository, Mockito.times(1)).searchPerson(suchePersonerweitertAnfrage);
    }

}