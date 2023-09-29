package de.muenchen.oss.digiwf.address.integration.service;

import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.repository.AdressenMuenchenRepository;
import de.muenchen.oss.digiwf.address.integration.gen.model.AdresseDistanz;
import de.muenchen.oss.digiwf.address.integration.gen.model.AenderungResponse;
import de.muenchen.oss.digiwf.address.integration.gen.model.MuenchenAdresse;
import de.muenchen.oss.digiwf.address.integration.gen.model.MuenchenAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.model.request.CheckAdresseMuenchenModel;
import de.muenchen.oss.digiwf.address.integration.model.request.ListAdressenMuenchenModel;
import de.muenchen.oss.digiwf.address.integration.model.request.ListAenderungenMuenchenModel;
import de.muenchen.oss.digiwf.address.integration.model.request.SearchAdressenGeoMuenchenModel;
import de.muenchen.oss.digiwf.address.integration.model.request.SearchAdressenMuenchenModel;
import de.muenchen.oss.digiwf.address.integration.model.response.AddressDistancesModel;
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
class AdressenMuenchenServiceTest {

    @Mock
    private AdressenMuenchenRepository adressenMuenchenRepository;

    private AdressenMuenchenService adressenMuenchenService;

    @BeforeEach
    public void beforeEach() {
        this.adressenMuenchenService = new AdressenMuenchenService(this.adressenMuenchenRepository);
        Mockito.reset(this.adressenMuenchenRepository);
    }

    @Test
    void checkAdresse() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final CheckAdresseMuenchenModel checkAdresseMuenchenModel = new CheckAdresseMuenchenModel();
        checkAdresseMuenchenModel.setAdresse("adresse");
        checkAdresseMuenchenModel.setStrassenname("strassenname");
        checkAdresseMuenchenModel.setStrasseId(1);
        checkAdresseMuenchenModel.setHausnummer("hausnummer");
        checkAdresseMuenchenModel.setZusatz("zusatz");
        checkAdresseMuenchenModel.setPlz("plz");
        checkAdresseMuenchenModel.setOrtsname("ortsname");
        checkAdresseMuenchenModel.setGemeindeschluessel("gemeindeschluessel");

        Mockito.when(this.adressenMuenchenRepository.checkAdresse(
                checkAdresseMuenchenModel.getAdresse(),
                checkAdresseMuenchenModel.getStrassenname(),
                checkAdresseMuenchenModel.getStrasseId(),
                checkAdresseMuenchenModel.getHausnummer(),
                checkAdresseMuenchenModel.getZusatz(),
                checkAdresseMuenchenModel.getPlz(),
                checkAdresseMuenchenModel.getOrtsname(),
                checkAdresseMuenchenModel.getGemeindeschluessel()
        )).thenReturn(new MuenchenAdresse());

        final MuenchenAdresse result = this.adressenMuenchenService.checkAdresse(checkAdresseMuenchenModel);

        assertThat(result, is(new MuenchenAdresse()));

        Mockito.verify(this.adressenMuenchenRepository, Mockito.times(1)).checkAdresse(
                checkAdresseMuenchenModel.getAdresse(),
                checkAdresseMuenchenModel.getStrassenname(),
                checkAdresseMuenchenModel.getStrasseId(),
                checkAdresseMuenchenModel.getHausnummer(),
                checkAdresseMuenchenModel.getZusatz(),
                checkAdresseMuenchenModel.getPlz(),
                checkAdresseMuenchenModel.getOrtsname(),
                checkAdresseMuenchenModel.getGemeindeschluessel()
        );
    }

    @Test
    void listAdressen() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final ListAdressenMuenchenModel listAdressenMuenchenModel = new ListAdressenMuenchenModel();
        listAdressenMuenchenModel.setBaublock(List.of("baublock"));
        listAdressenMuenchenModel.setErhaltungssatzung(List.of("erhaltungssatzung"));
        listAdressenMuenchenModel.setGemarkung(List.of("gemarkung"));
        listAdressenMuenchenModel.setKaminkehrerbezirk(List.of("kaminkehrerbezirk"));
        listAdressenMuenchenModel.setPlz(List.of("plz"));
        listAdressenMuenchenModel.setMittelschule(List.of("mittelschule"));
        listAdressenMuenchenModel.setGrundschule(List.of("grundschule"));
        listAdressenMuenchenModel.setPolizeiinspektion(List.of("polizeiinspektion"));
        listAdressenMuenchenModel.setStimmbezirk(List.of(1L));
        listAdressenMuenchenModel.setStimmkreis(List.of(2L));
        listAdressenMuenchenModel.setWahlbezirk(List.of(3L));
        listAdressenMuenchenModel.setWahlkreis(List.of(4L));
        listAdressenMuenchenModel.setStadtbezirk(List.of("stadtbezirk"));
        listAdressenMuenchenModel.setStadtbezirksteil(List.of("stadtbezirksteil"));
        listAdressenMuenchenModel.setStadtbezirksviertel(List.of("stadtbezirksviertel"));
        listAdressenMuenchenModel.setSort("sort");
        listAdressenMuenchenModel.setSortdir("sortdir");
        listAdressenMuenchenModel.setPage(98);
        listAdressenMuenchenModel.setPagesize(99);


        Mockito.when(this.adressenMuenchenRepository.listAdressen(
                listAdressenMuenchenModel.getBaublock(),
                listAdressenMuenchenModel.getErhaltungssatzung(),
                listAdressenMuenchenModel.getGemarkung(),
                listAdressenMuenchenModel.getKaminkehrerbezirk(),
                listAdressenMuenchenModel.getPlz(),
                listAdressenMuenchenModel.getMittelschule(),
                listAdressenMuenchenModel.getGrundschule(),
                listAdressenMuenchenModel.getPolizeiinspektion(),
                listAdressenMuenchenModel.getStimmbezirk(),
                listAdressenMuenchenModel.getStimmkreis(),
                listAdressenMuenchenModel.getWahlbezirk(),
                listAdressenMuenchenModel.getWahlkreis(),
                listAdressenMuenchenModel.getStadtbezirk(),
                listAdressenMuenchenModel.getStadtbezirksteil(),
                listAdressenMuenchenModel.getStadtbezirksviertel(),
                listAdressenMuenchenModel.getSort(),
                listAdressenMuenchenModel.getSortdir(),
                listAdressenMuenchenModel.getPage(),
                listAdressenMuenchenModel.getPagesize()
        )).thenReturn(new MuenchenAdresseResponse());

        final MuenchenAdresseResponse result = this.adressenMuenchenService.listAdressen(listAdressenMuenchenModel);

        assertThat(result, is(new MuenchenAdresseResponse()));

        Mockito.verify(this.adressenMuenchenRepository, Mockito.times(1)).listAdressen(
                listAdressenMuenchenModel.getBaublock(),
                listAdressenMuenchenModel.getErhaltungssatzung(),
                listAdressenMuenchenModel.getGemarkung(),
                listAdressenMuenchenModel.getKaminkehrerbezirk(),
                listAdressenMuenchenModel.getPlz(),
                listAdressenMuenchenModel.getMittelschule(),
                listAdressenMuenchenModel.getGrundschule(),
                listAdressenMuenchenModel.getPolizeiinspektion(),
                listAdressenMuenchenModel.getStimmbezirk(),
                listAdressenMuenchenModel.getStimmkreis(),
                listAdressenMuenchenModel.getWahlbezirk(),
                listAdressenMuenchenModel.getWahlkreis(),
                listAdressenMuenchenModel.getStadtbezirk(),
                listAdressenMuenchenModel.getStadtbezirksteil(),
                listAdressenMuenchenModel.getStadtbezirksviertel(),
                listAdressenMuenchenModel.getSort(),
                listAdressenMuenchenModel.getSortdir(),
                listAdressenMuenchenModel.getPage(),
                listAdressenMuenchenModel.getPagesize()
        );
    }

    @Test
    void listAenderungen() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final ListAenderungenMuenchenModel listAenderungenMuenchenModel = new ListAenderungenMuenchenModel();
        listAenderungenMuenchenModel.setWirkungsdatumvon("wirkungsdatumvon");
        listAenderungenMuenchenModel.setWirkungsdatumbis("wirkungsdatumbis");
        listAenderungenMuenchenModel.setStrassenname("strassenname");
        listAenderungenMuenchenModel.setHausnummer(77L);
        listAenderungenMuenchenModel.setPlz("plz");
        listAenderungenMuenchenModel.setZusatz("zusatz");
        listAenderungenMuenchenModel.setSort("sort");
        listAenderungenMuenchenModel.setSortdir("sortdir");
        listAenderungenMuenchenModel.setPage(87);
        listAenderungenMuenchenModel.setPagesize(86);


        Mockito.when(this.adressenMuenchenRepository.listAenderungen(
                listAenderungenMuenchenModel.getWirkungsdatumvon(),
                listAenderungenMuenchenModel.getWirkungsdatumbis(),
                listAenderungenMuenchenModel.getStrassenname(),
                listAenderungenMuenchenModel.getHausnummer(),
                listAenderungenMuenchenModel.getPlz(),
                listAenderungenMuenchenModel.getZusatz(),
                listAenderungenMuenchenModel.getSort(),
                listAenderungenMuenchenModel.getSortdir(),
                listAenderungenMuenchenModel.getPage(),
                listAenderungenMuenchenModel.getPagesize()
        )).thenReturn(new AenderungResponse());

        final AenderungResponse result = this.adressenMuenchenService.listAenderungen(listAenderungenMuenchenModel);

        assertThat(result, is(new AenderungResponse()));

        Mockito.verify(this.adressenMuenchenRepository, Mockito.times(1)).listAenderungen(
                listAenderungenMuenchenModel.getWirkungsdatumvon(),
                listAenderungenMuenchenModel.getWirkungsdatumbis(),
                listAenderungenMuenchenModel.getStrassenname(),
                listAenderungenMuenchenModel.getHausnummer(),
                listAenderungenMuenchenModel.getPlz(),
                listAenderungenMuenchenModel.getZusatz(),
                listAenderungenMuenchenModel.getSort(),
                listAenderungenMuenchenModel.getSortdir(),
                listAenderungenMuenchenModel.getPage(),
                listAenderungenMuenchenModel.getPagesize()
        );
    }

    @Test
    void searchAdressen() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final SearchAdressenMuenchenModel searchAdressenMuenchenModel = new SearchAdressenMuenchenModel();
        searchAdressenMuenchenModel.setQuery("query");
        searchAdressenMuenchenModel.setPlzfilter(List.of("plzfilter"));
        searchAdressenMuenchenModel.setHausnummerfilter(List.of(55L));
        searchAdressenMuenchenModel.setBuchstabefilter(List.of("buchstabefilter"));
        searchAdressenMuenchenModel.setSearchtype("searchtype");
        searchAdressenMuenchenModel.setSort("sort");
        searchAdressenMuenchenModel.setSortdir("sortdir");
        searchAdressenMuenchenModel.setPage(67);
        searchAdressenMuenchenModel.setPagesize(68);

        Mockito.when(this.adressenMuenchenRepository.searchAdressen(
                searchAdressenMuenchenModel.getQuery(),
                searchAdressenMuenchenModel.getPlzfilter(),
                searchAdressenMuenchenModel.getHausnummerfilter(),
                searchAdressenMuenchenModel.getBuchstabefilter(),
                searchAdressenMuenchenModel.getSearchtype(),
                searchAdressenMuenchenModel.getSort(),
                searchAdressenMuenchenModel.getSortdir(),
                searchAdressenMuenchenModel.getPage(),
                searchAdressenMuenchenModel.getPagesize()
        )).thenReturn(new MuenchenAdresseResponse());

        final MuenchenAdresseResponse result = this.adressenMuenchenService.searchAdressen(searchAdressenMuenchenModel);

        assertThat(result, is(new MuenchenAdresseResponse()));

        Mockito.verify(this.adressenMuenchenRepository, Mockito.times(1)).searchAdressen(
                searchAdressenMuenchenModel.getQuery(),
                searchAdressenMuenchenModel.getPlzfilter(),
                searchAdressenMuenchenModel.getHausnummerfilter(),
                searchAdressenMuenchenModel.getBuchstabefilter(),
                searchAdressenMuenchenModel.getSearchtype(),
                searchAdressenMuenchenModel.getSort(),
                searchAdressenMuenchenModel.getSortdir(),
                searchAdressenMuenchenModel.getPage(),
                searchAdressenMuenchenModel.getPagesize()
        );
    }

    @Test
    void searchAdressenGeo() throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final SearchAdressenGeoMuenchenModel searchAdressenGeoMuenchenModel = new SearchAdressenGeoMuenchenModel();
        searchAdressenGeoMuenchenModel.setGeometrie("geometrie");
        searchAdressenGeoMuenchenModel.setLat(89.0);
        searchAdressenGeoMuenchenModel.setLng(88.0);
        searchAdressenGeoMuenchenModel.setDistanz(87.0);
        searchAdressenGeoMuenchenModel.setTopleftlat(86.0);
        searchAdressenGeoMuenchenModel.setTopleftlng(85.0);
        searchAdressenGeoMuenchenModel.setBottomrightlat(84.0);
        searchAdressenGeoMuenchenModel.setBottomrightlng(83.0);
        searchAdressenGeoMuenchenModel.setFormat("format");

        Mockito.when(this.adressenMuenchenRepository.searchAdressenGeo(
                searchAdressenGeoMuenchenModel.getGeometrie(),
                searchAdressenGeoMuenchenModel.getLat(),
                searchAdressenGeoMuenchenModel.getLng(),
                searchAdressenGeoMuenchenModel.getDistanz(),
                searchAdressenGeoMuenchenModel.getTopleftlat(),
                searchAdressenGeoMuenchenModel.getTopleftlng(),
                searchAdressenGeoMuenchenModel.getBottomrightlat(),
                searchAdressenGeoMuenchenModel.getBottomrightlng(),
                searchAdressenGeoMuenchenModel.getFormat()
        )).thenReturn(List.of(new AdresseDistanz()));

        final AddressDistancesModel result = this.adressenMuenchenService.searchAdressenGeo(searchAdressenGeoMuenchenModel);

        final AddressDistancesModel expected = new AddressDistancesModel();
        expected.setAdresseDistances(List.of(new AdresseDistanz()));

        assertThat(result, is(expected));

        Mockito.verify(this.adressenMuenchenRepository, Mockito.times(1)).searchAdressenGeo(
                searchAdressenGeoMuenchenModel.getGeometrie(),
                searchAdressenGeoMuenchenModel.getLat(),
                searchAdressenGeoMuenchenModel.getLng(),
                searchAdressenGeoMuenchenModel.getDistanz(),
                searchAdressenGeoMuenchenModel.getTopleftlat(),
                searchAdressenGeoMuenchenModel.getTopleftlng(),
                searchAdressenGeoMuenchenModel.getBottomrightlat(),
                searchAdressenGeoMuenchenModel.getBottomrightlng(),
                searchAdressenGeoMuenchenModel.getFormat()
        );
    }

}
