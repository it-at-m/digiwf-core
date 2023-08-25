package de.muenchen.oss.digiwf.address.service.integration.api.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ListAdressenMuenchenDto extends AbstractRequestDto {

    private List<String> baublock;

    private List<String> erhaltungssatzung;

    private List<String> gemarkung;

    private List<String> kaminkehrerbezirk;

    private List<String> plz;

    private List<String> mittelschule;

    private List<String> grundschule;

    private List<String> polizeiinspektion;

    private List<Long> stimmbezirk;

    private List<Long> stimmkreis;

    private List<Long> wahlbezirk;

    private List<Long> wahlkreis;

    private List<String> stadtbezirk;

    private List<String> stadtbezirksteil;

    private List<String> stadtbezirksviertel;

    private String sort;

    private String sortdir;

    private Integer page;

    private Integer pagesize;

}
