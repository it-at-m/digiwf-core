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
public class ListAddressesModel {

    private List<String> baublock;

    private List<String> erhaltungssatzung;

    private List<String> gemarkung;

    private List<String> kaminkehrerbezirk;

    private List<String> zip;

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
