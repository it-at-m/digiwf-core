package de.muenchen.oss.digiwf.okewo.integration.service;

import de.muenchen.oss.digiwf.okewo.integration.gen.model.SuchePersonerweitertAnfrage;
import de.muenchen.oss.digiwf.okewo.integration.gen.model.SuchePersonerweitertAntwort;
import de.muenchen.oss.digiwf.okewo.integration.exception.OkEwoIntegrationClientErrorException;
import de.muenchen.oss.digiwf.okewo.integration.exception.OkEwoIntegrationException;
import de.muenchen.oss.digiwf.okewo.integration.exception.OkEwoIntegrationServerErrorException;
import de.muenchen.oss.digiwf.okewo.integration.gen.model.PersonErweitert;
import de.muenchen.oss.digiwf.okewo.integration.repository.OkEwoPersonErweitertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OkEwoPersonErweitertService {

    private final OkEwoPersonErweitertRepository okEwoPersonErweitertRepository;

    private final PropertiesServiceTemplate propertiesServiceTemplate;

    /**
     * Gets a {@link PersonErweitert} by Ordnungsmerkmal.
     *
     * @param om as Ordnungsmerkmal which identifies a {@link PersonErweitert}.
     * @return the {@link PersonErweitert} identified by Ordnungsmerkmal.
     * @throws OkEwoIntegrationClientErrorException if the problem is with the client.
     * @throws OkEwoIntegrationServerErrorException if the problem is with OK.EWO.
     * @throws OkEwoIntegrationException            if the problem cannot be assigned directly to OK.EWO or client.
     */
    public PersonErweitert getPerson(final String om) throws OkEwoIntegrationClientErrorException, OkEwoIntegrationServerErrorException, OkEwoIntegrationException {
        return this.okEwoPersonErweitertRepository.getPerson(
                om,
                this.propertiesServiceTemplate.getBenutzerId()
        );
    }

    /**
     * Searches for {@link PersonErweitert}s identified by {@link SuchePersonerweitertAnfrage}.
     *
     * @param suchePersonerweitertAnfrage to identify {@link PersonErweitert}s.
     * @return the {@link SuchePersonerweitertAntwort} with the identified {@link PersonErweitert}s.
     * @throws OkEwoIntegrationClientErrorException if the problem is with the client.
     * @throws OkEwoIntegrationServerErrorException if the problem is with OK.EWO.
     * @throws OkEwoIntegrationException            if the problem cannot be assigned directly to OK.EWO or client.
     */
    public SuchePersonerweitertAntwort searchPerson(final SuchePersonerweitertAnfrage suchePersonerweitertAnfrage) throws OkEwoIntegrationClientErrorException, OkEwoIntegrationServerErrorException, OkEwoIntegrationException {
        suchePersonerweitertAnfrage.setBenutzer(this.propertiesServiceTemplate.getBenutzerTypeWithBenutzerId());
        return this.okEwoPersonErweitertRepository.searchPerson(suchePersonerweitertAnfrage);
    }

}
