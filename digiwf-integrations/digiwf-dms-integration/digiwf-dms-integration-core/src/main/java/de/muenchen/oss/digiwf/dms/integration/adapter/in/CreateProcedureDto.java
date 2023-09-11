package de.muenchen.oss.digiwf.dms.integration.adapter.in;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreateProcedureDto {

    private String fileCOO;

    private String title;

    private String user;

}
