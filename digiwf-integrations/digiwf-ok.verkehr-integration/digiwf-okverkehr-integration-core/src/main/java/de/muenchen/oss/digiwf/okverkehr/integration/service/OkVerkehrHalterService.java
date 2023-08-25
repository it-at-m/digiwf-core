package de.muenchen.oss.digiwf.okverkehr.integration.service;

import de.muenchen.oss.digiwf.okverkehr.integration.gen.model.HalterPersonAnfrage;
import de.muenchen.oss.digiwf.okverkehr.integration.gen.model.HalterPersonAntwort;
import de.muenchen.oss.digiwf.okverkehr.integration.exception.OkVerkehrIntegrationClientErrorException;
import de.muenchen.oss.digiwf.okverkehr.integration.exception.OkVerkehrIntegrationException;
import de.muenchen.oss.digiwf.okverkehr.integration.exception.OkVerkehrIntegrationServerErrorException;
import de.muenchen.oss.digiwf.okverkehr.integration.repository.OkVerkehrHalterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class OkVerkehrHalterService {

    private final OkVerkehrHalterRepository okVerkehrHalterRepository;

    public HalterPersonAntwort getHalter(final HalterPersonAnfrage request) throws OkVerkehrIntegrationClientErrorException, OkVerkehrIntegrationServerErrorException, OkVerkehrIntegrationException {
        return okVerkehrHalterRepository.getHalter(request);
    }
    
}
