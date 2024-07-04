package de.muenchen.oss.digiwf.okewo.integration.application.usecase;

import de.muenchen.oss.digiwf.okewo.integration.application.port.in.SearchPersonInPort;
import de.muenchen.oss.digiwf.okewo.integration.application.port.out.OkEwoClientOutPort;
import de.muenchen.oss.digiwf.okewo.integration.client.model.SuchePersonAnfrage;
import de.muenchen.oss.digiwf.okewo.integration.client.model.SuchePersonAntwort;
import de.muenchen.oss.digiwf.okewo.integration.domain.exception.OkEwoIntegrationClientErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchPersonUseCase implements SearchPersonInPort {
    private final OkEwoClientOutPort client;

    @Override
    public SuchePersonAntwort searchPerson(SuchePersonAnfrage request) throws OkEwoIntegrationClientErrorException {
        return client.searchPerson(request);
    }
}
