package io.muenchendigital.digiwf.okewo.integration.service;

import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationClientErrorException;
import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationException;
import io.muenchendigital.digiwf.okewo.integration.exception.OkEwoIntegrationServerErrorException;
import io.muenchendigital.digiwf.okewo.integration.gen.model.Person;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonAnfrage;
import io.muenchendigital.digiwf.okewo.integration.gen.model.SuchePersonAntwort;
import io.muenchendigital.digiwf.okewo.integration.repository.OkEwoPersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OkEwoPersonService {

    private final OkEwoPersonRepository okEwoPersonRepository;

    private final PropertiesService propertiesService;

    /**
     * Gets a {@link Person} by Ordnungsmerkmal.
     *
     * @param om as Ordnungsmerkmal which identifies a {@link Person}.
     * @return the {@link Person} identified by Ordnungsmerkmal.
     * @throws OkEwoIntegrationClientErrorException if the problem is with the client.
     * @throws OkEwoIntegrationServerErrorException if the problem is with OK.EWO.
     * @throws OkEwoIntegrationException            if the problem cannot be assigned directly to OK.EWO or client.
     */
    public Person getPerson(final String om) throws OkEwoIntegrationClientErrorException, OkEwoIntegrationServerErrorException, OkEwoIntegrationException {
        return this.okEwoPersonRepository.getPerson(
                om,
                this.propertiesService.getBenutzerId()
        );
    }

    /**
     * Searches for {@link Person}s identified by {@link SuchePersonAnfrage}.
     *
     * @param suchePersonAnfrage to identify {@link Person}s.
     * @return the {@link SuchePersonAntwort} with the identified {@link Person}s.
     * @throws OkEwoIntegrationClientErrorException if the problem is with the client.
     * @throws OkEwoIntegrationServerErrorException if the problem is with OK.EWO.
     * @throws OkEwoIntegrationException            if the problem cannot be assigned directly to OK.EWO or client.
     */
    public SuchePersonAntwort searchPerson(final SuchePersonAnfrage suchePersonAnfrage) throws OkEwoIntegrationClientErrorException, OkEwoIntegrationServerErrorException, OkEwoIntegrationException {
        suchePersonAnfrage.setBenutzer(this.propertiesService.getBenutzerTypeWithBenutzerId());
        return this.okEwoPersonRepository.searchPerson(suchePersonAnfrage);
    }

}
