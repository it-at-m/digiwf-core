package de.muenchen.oss.digiwf.address.service.integration.api.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "eventType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SearchAdressenBundesweitDto.class, name = "searchAdressenBundesweit"),
        @JsonSubTypes.Type(value = CheckAdresseMuenchenDto.class, name = "checkAdresseMuenchen"),
        @JsonSubTypes.Type(value = ListAdressenMuenchenDto.class, name = "listAdressenMuenchen"),
        @JsonSubTypes.Type(value = ListAenderungenMuenchenDto.class, name = "listAenderungenMuenchen"),
        @JsonSubTypes.Type(value = SearchAdressenMuenchenDto.class, name = "searchAdressenMuenchen"),
        @JsonSubTypes.Type(value = SearchAdressenGeoMuenchenDto.class, name = "searchAdressenGeoMuenchen"),
        @JsonSubTypes.Type(value = StrassenIdDto.class, name = "findStrasseByIdMuenchen"),
        @JsonSubTypes.Type(value = ListStrassenDto.class, name = "listStrassenMuenchen"),
})
public abstract class AbstractRequestDto {

    private String eventType;

}
