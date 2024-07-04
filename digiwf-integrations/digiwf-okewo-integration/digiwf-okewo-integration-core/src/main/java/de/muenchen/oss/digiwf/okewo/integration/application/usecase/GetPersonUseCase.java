package de.muenchen.oss.digiwf.okewo.integration.application.usecase;

import de.muenchen.oss.digiwf.okewo.integration.application.port.in.GetPersonInPort;
import de.muenchen.oss.digiwf.okewo.integration.application.port.out.OkEwoClientOutPort;
import de.muenchen.oss.digiwf.okewo.integration.client.model.Person;
import de.muenchen.oss.digiwf.okewo.integration.domain.exception.OkEwoIntegrationClientErrorException;
import de.muenchen.oss.digiwf.okewo.integration.properties.OkEwoIntegrationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetPersonUseCase implements GetPersonInPort {
    private final OkEwoIntegrationProperties properties;
    private final OkEwoClientOutPort client;

    @Override
    public Person getPerson(String om) throws OkEwoIntegrationClientErrorException {
        return client.getPerson(om, properties.getBenutzerId());
    }
}
