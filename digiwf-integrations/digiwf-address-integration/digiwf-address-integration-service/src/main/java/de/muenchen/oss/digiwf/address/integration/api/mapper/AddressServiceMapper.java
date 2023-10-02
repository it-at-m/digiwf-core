package de.muenchen.oss.digiwf.address.integration.api.mapper;

import de.muenchen.oss.digiwf.address.integration.api.configuration.MapstructConfiguration;
import de.muenchen.oss.digiwf.address.integration.api.dto.request.*;
import de.muenchen.oss.digiwf.address.integration.api.dto.response.AddressDistancesDto;
import de.muenchen.oss.digiwf.address.integration.model.request.*;
import de.muenchen.oss.digiwf.address.integration.model.response.AddressDistancesModel;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface AddressServiceMapper {

    SearchAdressesGermanyModel dto2Model(final SearchAdressenBundesweitDto searchAdressenBundesweitDto);

    CheckAddressesModel dto2Model(final CheckAdresseMuenchenDto checkAdresseMuenchenDto);

    ListAddressesModel dto2Model(final ListAdressenMuenchenDto listAdressenMuenchenDto);

    ListAddressChangesModel dto2Model(final ListAenderungenMuenchenDto listAenderungenMuenchenDto);

    SearchAddressesModel dto2Model(final SearchAdressenMuenchenDto searchAdressenMuenchenDto);

    SearchAddressesGeoModel dto2Model(final SearchAdressenGeoMuenchenDto searchAdressenGeoMuenchenDto);

    StrassenIdModel dto2Model(final StrassenIdDto strassenIdDto);

    ListStreetsModel dto2Model(final ListStrassenDto listStrassenDto);

    AddressDistancesDto model2Dto(final AddressDistancesModel addressDistancesModel);

}
