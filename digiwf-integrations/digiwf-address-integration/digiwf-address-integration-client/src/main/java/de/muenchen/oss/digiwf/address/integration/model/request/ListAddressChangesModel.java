package de.muenchen.oss.digiwf.address.integration.model.request;

import lombok.Data;

@Data
public class ListAddressChangesModel {

    private String effectiveDateFrom;

    private String effectiveDateTo;

    private String streetName;

    private Long houseNumber;

    private String zip;

    private String additionalInfo;

    private String sorting;

    private String sortingDir;

    private Integer pageNumber;

    private Integer pageSize;

}
