package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import javax.validation.constraints.NotBlank;

public interface CreateFileUseCase {

    String createFile(@NotBlank final String titel, @NotBlank final String apentryCOO, @NotBlank final String user);

}
