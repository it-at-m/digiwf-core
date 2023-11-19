package de.muenchen.oss.digiwf.dms.integration.adapter.in;


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

}
