package de.muenchen.oss.digiwf.okewo.integration.service;

import de.muenchen.oss.digiwf.okewo.integration.gen.model.SuchePersonAnfrage;
import de.muenchen.oss.digiwf.okewo.integration.gen.model.SuchePersonAntwort;
import de.muenchen.oss.digiwf.okewo.integration.exception.OkEwoIntegrationClientErrorException;
import de.muenchen.oss.digiwf.okewo.integration.exception.OkEwoIntegrationException;
import de.muenchen.oss.digiwf.okewo.integration.exception.OkEwoIntegrationServerErrorException;
import de.muenchen.oss.digiwf.okewo.integration.gen.model.Person;
import de.muenchen.oss.digiwf.okewo.integration.repository.OkEwoPersonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

class OkEwoPersonServiceTest {

    private final OkEwoPersonRepository okEwoPersonRepository = mock(OkEwoPersonRepository.class);

    private final PropertiesServiceTemplate propertiesServiceTemplate = new PropertiesServiceTemplate("benutzerId");

    private final OkEwoPersonService okEwoPersonService = new OkEwoPersonService(this.okEwoPersonRepository, this.propertiesServiceTemplate);

    @Test
    void getPerson() throws OkEwoIntegrationException, OkEwoIntegrationClientErrorException, OkEwoIntegrationServerErrorException {
        final String om = "ordnungsmerkmal";
        final String benutzerId = "benutzerId";

        Mockito.when(this.okEwoPersonRepository.getPerson(om, benutzerId)).thenReturn(new Person());

        final Person result = this.okEwoPersonService.getPerson(om);
        assertThat(result, is(new Person()));

        Mockito.verify(this.okEwoPersonRepository, Mockito.times(1)).getPerson(om, benutzerId);
    }

    @Test
    void searchPerson() throws OkEwoIntegrationException, OkEwoIntegrationClientErrorException, OkEwoIntegrationServerErrorException {
        final SuchePersonAnfrage suchePersonAnfrage = new SuchePersonAnfrage();
        suchePersonAnfrage.setBenutzer(this.propertiesServiceTemplate.getBenutzerTypeWithBenutzerId());

        Mockito.when(this.okEwoPersonRepository.searchPerson(suchePersonAnfrage)).thenReturn(new SuchePersonAntwort());

        final SuchePersonAntwort result = this.okEwoPersonService.searchPerson(new SuchePersonAnfrage());
        assertThat(result, is(new SuchePersonAntwort()));

        Mockito.verify(this.okEwoPersonRepository, Mockito.times(1)).searchPerson(suchePersonAnfrage);
    }

}
