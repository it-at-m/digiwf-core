package de.muenchen.oss.digiwf.address.service.integration.api.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ListStrassenDto extends AbstractRequestDto {

    private List<String> stadtbezirksnamen;

    private List<Long> stadtbezirksnummern;

    private String strassenname;

    private String sortdir;

    private Integer page;

    private Integer pagesize;

}
