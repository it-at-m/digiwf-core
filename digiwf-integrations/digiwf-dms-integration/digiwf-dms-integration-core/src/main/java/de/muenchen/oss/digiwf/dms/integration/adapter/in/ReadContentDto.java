package de.muenchen.oss.digiwf.dms.integration.adapter.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReadContentDto {

    private List<String> contentCoos;

    private String filePath;

    private String fileContext;

    private String user;

}
