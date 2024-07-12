package de.muenchen.oss.digiwf.email.integration.application.port.in;

import de.muenchen.oss.digiwf.email.integration.domain.model.paths.TemplateMailPaths;
import de.muenchen.oss.digiwf.email.integration.domain.model.paths.TextMailPaths;
import jakarta.validation.Valid;

public interface SendMailPathsInPort {

    void sendMailWithText(@Valid final TextMailPaths mail);

    void sendMailWithTemplate(@Valid final TemplateMailPaths mail);

}
