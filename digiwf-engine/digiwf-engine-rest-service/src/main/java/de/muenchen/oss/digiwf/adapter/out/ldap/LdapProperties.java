package de.muenchen.oss.digiwf.adapter.out.ldap;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("groups-ldap")
@Data
@ConfigurationProperties(prefix = "digiwf.ldap")
public class LdapProperties {
    @NotBlank
    private String groupBase;
    @NotBlank
    private String userBase;
}
