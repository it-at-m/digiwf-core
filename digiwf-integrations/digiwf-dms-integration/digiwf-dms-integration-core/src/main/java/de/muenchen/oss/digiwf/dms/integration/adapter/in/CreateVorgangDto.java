package de.muenchen.oss.digiwf.dms.integration.adapter.in;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreateVorgangDto {

    private String sachakteCoo;

    private String title;

    private String user;

    private String art;

}
