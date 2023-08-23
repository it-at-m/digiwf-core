package io.muenchendigital.digiwf.okewo.integration.service;

import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationClientErrorException;
import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationException;
import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationServerErrorException;
import io.muenchendigital.digiwf.okewo.integration.gen.model.Person;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonAnfrage;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonAntwort;
import io.muenchendigital.digiwf.okewo.integration.repository.OkEwoPersonRepository;
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
class OkEwoPersonServiceTest {

    @Mock
    private OkEwoPersonRepository okEwoPersonRepository;

    private final PropertiesService propertiesService = new PropertiesService("benutzerId");

    private OkEwoPersonService okEwoPersonService;

    @BeforeEach
    public void beforeEach() {
        this.okEwoPersonService = new OkEwoPersonService(this.okEwoPersonRepository, this.propertiesService);
        Mockito.reset(this.okEwoPersonRepository);
    }

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
        suchePersonAnfrage.setBenutzer(this.propertiesService.getBenutzerTypeWithBenutzerId());

        Mockito.when(this.okEwoPersonRepository.searchPerson(suchePersonAnfrage)).thenReturn(new SuchePersonAntwort());

        final SuchePersonAntwort result = this.okEwoPersonService.searchPerson(new SuchePersonAnfrage());
        assertThat(result, is(new SuchePersonAntwort()));

        Mockito.verify(this.okEwoPersonRepository, Mockito.times(1)).searchPerson(suchePersonAnfrage);
    }

}
