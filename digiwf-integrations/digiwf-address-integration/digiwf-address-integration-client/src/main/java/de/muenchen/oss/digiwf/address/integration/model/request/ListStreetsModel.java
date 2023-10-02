package de.muenchen.oss.digiwf.address.integration.model.request;

import lombok.Data;

import java.util.List;

@Data
public class ListStreetsModel {

    private List<String> cityDistrictNames;

    private List<Long> cityDistrictNumbers;

    private String streetName;

    private String sortdir;

    private Integer page;

    private Integer pagesize;

}
