package de.muenchen.oss.digiwf.address.service.integration.service;

import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.service.integration.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.service.integration.gen.model.BundesweiteAdresseResponse;
import de.muenchen.oss.digiwf.address.service.integration.model.request.SearchAdressenBundesweitModel;
import de.muenchen.oss.digiwf.address.service.integration.repository.AdressenBundesweitRepository;
import de.muenchen.oss.digiwf.address.service.integration.service.AdressenBundesweitService;
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
class AdressenBundesweitServiceTest {

    @Mock
    private AdressenBundesweitRepository adressenBundesweitRepository;

    private AdressenBundesweitService adressenBundesweitService;

    @BeforeEach
    public void beforeEach() {
        this.adressenBundesweitService = new AdressenBundesweitService(this.adressenBundesweitRepository);
        Mockito.reset(this.adressenBundesweitRepository);
    }

    @Test
    void searchAdressen() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final SearchAdressenBundesweitModel model = new SearchAdressenBundesweitModel();
        model.setQuery("query");
        model.setPlz("plz");
        model.setOrtsname("ortsname");
        model.setGemeindeschluessel("gemeindeschluessel");
        model.setHausnummerfilter(List.of(1L));
        model.setBuchstabefilter(List.of("buchstabefilter"));
        model.setSort("sort");
        model.setSortdir("sortdir");
        model.setPage(2);
        model.setPagesize(3);

        Mockito.when(this.adressenBundesweitRepository.searchAdressen(
                model.getQuery(),
                model.getPlz(),
                model.getOrtsname(),
                model.getGemeindeschluessel(),
                model.getHausnummerfilter(),
                model.getBuchstabefilter(),
                model.getSort(),
                model.getSortdir(),
                model.getPage(),
                model.getPagesize()
        )).thenReturn(new BundesweiteAdresseResponse());

        final BundesweiteAdresseResponse result = this.adressenBundesweitService.searchAdressen(model);

        assertThat(result, is(new BundesweiteAdresseResponse()));

        Mockito.verify(this.adressenBundesweitRepository, Mockito.times(1)).searchAdressen(
                model.getQuery(),
                model.getPlz(),
                model.getOrtsname(),
                model.getGemeindeschluessel(),
                model.getHausnummerfilter(),
                model.getBuchstabefilter(),
                model.getSort(),
                model.getSortdir(),
                model.getPage(),
                model.getPagesize()
        );
    }

}
