package de.muenchen.oss.digiwf.task.service.infra.security;

import de.muenchen.oss.digiwf.spring.security.SecurityConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(
    SecurityConfiguration.class
)
@RequiredArgsConstructor
public class TaskServiceSecurityConfiguration {

}
