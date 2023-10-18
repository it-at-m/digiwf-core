package de.muenchen.oss.digiwf.address.integration.client.model.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ListStreetsModel {

    private List<String> cityDistrictNames;

    private List<Long> cityDistrictNumbers;

    private String streetName;

    private String sortdir;

    private Integer page;

    private Integer pagesize;

}
