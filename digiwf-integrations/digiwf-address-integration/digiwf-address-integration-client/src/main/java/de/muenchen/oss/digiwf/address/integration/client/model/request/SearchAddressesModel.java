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
public class SearchAddressesModel {

    private String query;

    private List<String> zipFilter;

    private List<Long> houseNumberFilter;

    private List<String> letterFilter;

    private String searchtype;

    private String sort;

    private String sortdir;

    private Integer page;

    private Integer pagesize;

}
