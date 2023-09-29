package de.muenchen.oss.digiwf.address.integration.api.mapper;

import de.muenchen.oss.digiwf.address.integration.api.configuration.MapstructConfiguration;
import de.muenchen.oss.digiwf.address.integration.api.dto.response.AddressDistancesDto;
import de.muenchen.oss.digiwf.address.integration.model.response.AddressDistancesModel;
import de.muenchen.oss.digiwf.address.integration.api.dto.request.CheckAdresseMuenchenDto;
import de.muenchen.oss.digiwf.address.integration.api.dto.request.ListAdressenMuenchenDto;
import de.muenchen.oss.digiwf.address.integration.api.dto.request.ListAenderungenMuenchenDto;
import de.muenchen.oss.digiwf.address.integration.api.dto.request.ListStrassenDto;
import de.muenchen.oss.digiwf.address.integration.api.dto.request.SearchAdressenBundesweitDto;
import de.muenchen.oss.digiwf.address.integration.api.dto.request.SearchAdressenGeoMuenchenDto;
import de.muenchen.oss.digiwf.address.integration.api.dto.request.SearchAdressenMuenchenDto;
import de.muenchen.oss.digiwf.address.integration.api.dto.request.StrassenIdDto;
import de.muenchen.oss.digiwf.address.integration.model.request.CheckAdresseMuenchenModel;
import de.muenchen.oss.digiwf.address.integration.model.request.ListAdressenMuenchenModel;
import de.muenchen.oss.digiwf.address.integration.model.request.ListAenderungenMuenchenModel;
import de.muenchen.oss.digiwf.address.integration.model.request.ListStrassenModel;
import de.muenchen.oss.digiwf.address.integration.model.request.SearchAdressenBundesweitModel;
import de.muenchen.oss.digiwf.address.integration.model.request.SearchAdressenGeoMuenchenModel;
import de.muenchen.oss.digiwf.address.integration.model.request.SearchAdressenMuenchenModel;
import de.muenchen.oss.digiwf.address.integration.model.request.StrassenIdModel;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface AddressServiceMapper {

    SearchAdressenBundesweitModel dto2Model(final SearchAdressenBundesweitDto searchAdressenBundesweitDto);

    CheckAdresseMuenchenModel dto2Model(final CheckAdresseMuenchenDto checkAdresseMuenchenDto);

    ListAdressenMuenchenModel dto2Model(final ListAdressenMuenchenDto listAdressenMuenchenDto);

    ListAenderungenMuenchenModel dto2Model(final ListAenderungenMuenchenDto listAenderungenMuenchenDto);

    SearchAdressenMuenchenModel dto2Model(final SearchAdressenMuenchenDto searchAdressenMuenchenDto);

    SearchAdressenGeoMuenchenModel dto2Model(final SearchAdressenGeoMuenchenDto searchAdressenGeoMuenchenDto);

    StrassenIdModel dto2Model(final StrassenIdDto strassenIdDto);

    ListStrassenModel dto2Model(final ListStrassenDto listStrassenDto);

    AddressDistancesDto model2Dto(final AddressDistancesModel addressDistancesModel);

}
