package io.muenchendigital.digiwf.okewo.integration.api.dto.request;

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
        @JsonSubTypes.Type(value = OrdnungsmerkmalDto.class, name = "getPerson"),
        @JsonSubTypes.Type(value = SearchPersonRequestDto.class, name = "searchPerson"),
        @JsonSubTypes.Type(value = OrdnungsmerkmalDto.class, name = "getPersonErweitert"),
        @JsonSubTypes.Type(value = SearchPersonErweitertRequestDto.class, name = "searchPersonErweitert"),
})
public abstract class AbstractRequestDto {

    private String eventType;

}
