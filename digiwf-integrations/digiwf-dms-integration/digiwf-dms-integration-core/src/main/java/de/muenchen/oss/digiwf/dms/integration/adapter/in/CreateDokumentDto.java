package de.muenchen.oss.digiwf.dms.integration.adapter.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreateDokumentDto {

    private String vorgangCoo;

    private String title;

    private String user;

    @NotBlank
    private String art;

    private String s3Dateien;

}
