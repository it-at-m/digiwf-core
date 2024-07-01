package de.muenchen.oss.digiwf.dms.integration.adapter.in.streaming;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SearchObjectDto {

    private String searchString;

    private String user;

    /**
     * 'Fachdatum' to refine a search on
     */
    private String reference;

    /**
     * Value of 'Fachdatum'/reference
     */
    private String value;

}
