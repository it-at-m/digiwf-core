package de.muenchen.oss.digiwf.dms.integration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Dokument {

    private String coo;
    private String vorgangCoo;
    private String title;
    private DokumentArt art;
    private List<Schriftstueck> schriftstuecke = new ArrayList<>();

    public Dokument(final String vorgangCoo, final String title, final DokumentArt art,final List<Schriftstueck> schriftstuecke) {
        this.vorgangCoo = vorgangCoo;
        this.title = title;
        this.art = art;
        this.schriftstuecke.addAll(schriftstuecke);
    }

}
