package de.muenchen.oss.digiwf.address.integration.adapter.in.streaming;

import de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.dto.*;
import de.muenchen.oss.digiwf.address.integration.client.model.request.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AddressMapper {


    @Mapping(target = "zip", source = "plz")
    @Mapping(target = "cityName", source = "ortsname")
    @Mapping(target = "houseNumberFilter", source = "hausnummerfilter")
    @Mapping(target = "letterFilter", source = "buchstabefilter")
    SearchAddressesGermanyModel dto2Model(SearchAdressenDeutschlandDto searchAdressenDeutschlandDto);


    @Mapping(target = "address", source = "adresse")
    @Mapping(target = "streetName", source = "strassenname")
    @Mapping(target = "streetId", source = "strasseId")
    @Mapping(target = "houseNumber", source = "hausnummer")
    @Mapping(target = "additionalInfo", source = "zusatz")
    @Mapping(target = "zip", source = "plz")
    @Mapping(target = "cityName", source = "ortsname")
    CheckAddressesModel dto2Model(CheckAdresseMuenchenDto checkAdresseMuenchenDto);

    @Mapping(target = "zip", source = "plz")
    ListAddressesModel dto2Model(ListAdressenMuenchenDto listAdressenMuenchenDto);

    @Mapping(target = "effectiveDateFrom", source = "wirkungsdatumvon")
    @Mapping(target = "effectiveDateTo", source = "wirkungsdatumbis")
    @Mapping(target = "streetName", source = "strassenname")
    @Mapping(target = "houseNumber", source = "hausnummer")
    @Mapping(target = "zip", source = "plz")
    @Mapping(target = "additionalInfo", source = "zusatz")
    @Mapping(target = "sorting", source = "sort")
    @Mapping(target = "sortingDir", source = "sortdir")
    @Mapping(target = "pageNumber", source = "page")
    @Mapping(target = "pageSize", source = "pagesize")
    ListAddressChangesModel dto2Model(ListAenderungenMuenchenDto listAenderungenMuenchenDto);

    @Mapping(target = "zipFilter", source = "plzfilter")
    @Mapping(target = "houseNumberFilter", source = "hausnummerfilter")
    @Mapping(target = "letterFilter", source = "buchstabefilter")
    SearchAddressesModel dto2Model(SearchAdressenMuenchenDto searchAdressenMuenchenDto);

    @Mapping(target = "geometry", source = "geometrie")
    @Mapping(target = "distance", source = "distanz")
    SearchAddressesGeoModel dto2Model(SearchAdressenGeoMuenchenDto searchAdressenGeoMuenchenDto);

    @Mapping(target = "cityDistrictNames", source = "stadtbezirksnamen")
    @Mapping(target = "cityDistrictNumbers", source = "stadtbezirksnummern")
    @Mapping(target = "streetName", source = "strassenname")
    ListStreetsModel dto2Model(ListStrassenDto listStrassenDto);

}
