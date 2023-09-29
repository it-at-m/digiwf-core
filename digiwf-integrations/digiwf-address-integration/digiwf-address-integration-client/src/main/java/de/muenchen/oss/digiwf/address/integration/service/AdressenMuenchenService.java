package de.muenchen.oss.digiwf.address.integration.service;

import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationClientErrorException;
import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationException;
import de.muenchen.oss.digiwf.address.integration.exception.AddressServiceIntegrationServerErrorException;
import de.muenchen.oss.digiwf.address.integration.model.request.CheckAdresseMuenchenModel;
import de.muenchen.oss.digiwf.address.integration.model.request.ListAdressenMuenchenModel;
import de.muenchen.oss.digiwf.address.integration.model.request.ListAenderungenMuenchenModel;
import de.muenchen.oss.digiwf.address.integration.model.request.SearchAdressenMuenchenModel;
import de.muenchen.oss.digiwf.address.integration.repository.AdressenMuenchenRepository;
import de.muenchen.oss.digiwf.address.integration.gen.model.AdresseDistanz;
import de.muenchen.oss.digiwf.address.integration.gen.model.AenderungResponse;
import de.muenchen.oss.digiwf.address.integration.gen.model.MuenchenAdresse;
import de.muenchen.oss.digiwf.address.integration.gen.model.MuenchenAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.model.request.SearchAdressenGeoMuenchenModel;
import de.muenchen.oss.digiwf.address.integration.model.response.AddressDistancesModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdressenMuenchenService {

    private final AdressenMuenchenRepository adressenMuenchenRepository;

    public MuenchenAdresse checkAdresse(final CheckAdresseMuenchenModel checkAdresseMuenchenModel) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException {
        return this.adressenMuenchenRepository.checkAdresse(
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

    public MuenchenAdresseResponse listAdressen(final ListAdressenMuenchenModel listAdressenMuenchenModel) throws AddressServiceIntegrationClientErrorException, AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException {
        return this.adressenMuenchenRepository.listAdressen(
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

    public AenderungResponse listAenderungen(final ListAenderungenMuenchenModel listAenderungenMuenchenModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        return this.adressenMuenchenRepository.listAenderungen(
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

    public MuenchenAdresseResponse searchAdressen(final SearchAdressenMuenchenModel searchAdressenMuenchenModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        return this.adressenMuenchenRepository.searchAdressen(
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

    public AddressDistancesModel searchAdressenGeo(final SearchAdressenGeoMuenchenModel searchAdressenGeoMuenchenModel) throws AddressServiceIntegrationServerErrorException, AddressServiceIntegrationException, AddressServiceIntegrationClientErrorException {
        final var addressDistancesModel = new AddressDistancesModel();
        final List<AdresseDistanz> addressDistances = this.adressenMuenchenRepository.searchAdressenGeo(
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
        addressDistancesModel.setAdresseDistances(addressDistances);
        return addressDistancesModel;
    }

}
