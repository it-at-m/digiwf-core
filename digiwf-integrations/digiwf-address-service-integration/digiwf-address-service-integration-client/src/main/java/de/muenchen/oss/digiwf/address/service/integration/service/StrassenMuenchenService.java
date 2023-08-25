package de.muenchen.oss.digiwf.address.service.integration.service;

import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.service.integration.model.request.ListStrassenModel;
import de.muenchen.oss.digiwf.address.service.integration.model.request.StrassenIdModel;
import de.muenchen.oss.digiwf.address.service.integration.repository.StrassenMuenchenRepository;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.Strasse;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.StrasseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StrassenMuenchenService {

    private final StrassenMuenchenRepository strassenMuenchenRepository;

    public Strasse findStrasseById(final StrassenIdModel strassenIdModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        return this.strassenMuenchenRepository.findStrasseById(strassenIdModel.getStrasseId());
    }

    public StrasseResponse listStrassen(final ListStrassenModel listStrassenModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        return this.strassenMuenchenRepository.listStrassen(
                listStrassenModel.getStadtbezirksnamen(),
                listStrassenModel.getStadtbezirksnummern(),
                listStrassenModel.getStrassenname(),
                listStrassenModel.getSortdir(),
                listStrassenModel.getPage(),
                listStrassenModel.getPagesize()
        );
    }

}
