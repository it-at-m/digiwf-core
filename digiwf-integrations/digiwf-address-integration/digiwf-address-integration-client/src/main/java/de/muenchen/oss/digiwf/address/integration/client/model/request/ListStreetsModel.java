package de.muenchen.oss.digiwf.address.integration.client.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListStreetsModel {

    private List<String> cityDistrictNames;

    private List<Long> cityDistrictNumbers;

    private String streetName;

    private String sortdir;

    private Integer page;

    private Integer pagesize;

}
