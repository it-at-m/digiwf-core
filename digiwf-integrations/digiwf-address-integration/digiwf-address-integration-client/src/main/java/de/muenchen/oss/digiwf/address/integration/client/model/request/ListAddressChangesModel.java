package de.muenchen.oss.digiwf.address.integration.client.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
