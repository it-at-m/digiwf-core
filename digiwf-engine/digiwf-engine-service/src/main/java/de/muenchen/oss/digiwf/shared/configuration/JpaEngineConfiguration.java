package de.muenchen.oss.digiwf.shared.configuration;

import de.muenchen.oss.digiwf.EngineServiceApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(
    basePackageClasses = {
        EngineServiceApplication.class
    }
)
public class JpaEngineConfiguration {
}
