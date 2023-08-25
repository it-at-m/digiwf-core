package de.muenchen.oss.digiwf.address.service.integration.service;

import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.Strasse;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.StrasseResponse;
import de.muenchen.oss.digiwf.address.service.integration.model.request.ListStrassenModel;
import de.muenchen.oss.digiwf.address.service.integration.model.request.StrassenIdModel;
import de.muenchen.oss.digiwf.address.service.integration.repository.StrassenMuenchenRepository;
import de.muenchen.oss.digiwf.address.service.integration.service.StrassenMuenchenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class StrassenMuenchenServiceTest {

    @Mock
    private StrassenMuenchenRepository strassenMuenchenRepository;

    private StrassenMuenchenService strassenMuenchenService;

    @BeforeEach
    public void beforeEach() {
        this.strassenMuenchenService = new StrassenMuenchenService(this.strassenMuenchenRepository);
        Mockito.reset(this.strassenMuenchenRepository);
    }

    @Test
    void findStrasseById() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final StrassenIdModel strassenIdModel = new StrassenIdModel();
        strassenIdModel.setStrasseId(99L);

        Mockito.when(this.strassenMuenchenRepository.findStrasseById(strassenIdModel.getStrasseId())).thenReturn(new Strasse());

        final Strasse result = this.strassenMuenchenService.findStrasseById(strassenIdModel);

        assertThat(result, is(new Strasse()));

        Mockito.verify(this.strassenMuenchenRepository, Mockito.times(1)).findStrasseById(strassenIdModel.getStrasseId());

    }

    @Test
    void listStrassen() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final ListStrassenModel listStrassenModel = new ListStrassenModel();
        listStrassenModel.setStadtbezirksnamen(List.of("stadtbezirksnamen"));
        listStrassenModel.setStadtbezirksnummern(List.of(98L));
        listStrassenModel.setStrassenname("strassenname");
        listStrassenModel.setSortdir("sortdir");
        listStrassenModel.setPage(9);
        listStrassenModel.setPagesize(10);

        final StrassenIdModel strassenIdModel = new StrassenIdModel();
        strassenIdModel.setStrasseId(99L);

        Mockito.when(this.strassenMuenchenRepository.listStrassen(
                listStrassenModel.getStadtbezirksnamen(),
                listStrassenModel.getStadtbezirksnummern(),
                listStrassenModel.getStrassenname(),
                listStrassenModel.getSortdir(),
                listStrassenModel.getPage(),
                listStrassenModel.getPagesize()
        )).thenReturn(new StrasseResponse());

        final StrasseResponse result = this.strassenMuenchenService.listStrassen(listStrassenModel);

        assertThat(result, is(new StrasseResponse()));

        Mockito.verify(this.strassenMuenchenRepository, Mockito.times(1)).listStrassen(
                listStrassenModel.getStadtbezirksnamen(),
                listStrassenModel.getStadtbezirksnummern(),
                listStrassenModel.getStrassenname(),
                listStrassenModel.getSortdir(),
                listStrassenModel.getPage(),
                listStrassenModel.getPagesize()
        );
    }

}
