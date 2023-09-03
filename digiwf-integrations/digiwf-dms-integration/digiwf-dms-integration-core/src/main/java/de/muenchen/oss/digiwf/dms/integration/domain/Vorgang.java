package de.muenchen.oss.digiwf.dms.integration.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vorgang {

    private String coo;
    private String sachakteCoo;
    private String title;
    private VorgangArt art;

    public Vorgang(final String sachakteCoo, final String title, final VorgangArt art) {
        this.sachakteCoo = sachakteCoo;
        this.title = title;
        this.art = art;
    }

}
