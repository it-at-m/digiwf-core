package de.muenchen.oss.digiwf.spring.security.autoconfiguration;


import de.muenchen.oss.digiwf.spring.security.SpringSecurityProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * Auto-configuration used to configure Spring security.
 */
@EnableConfigurationProperties(SpringSecurityProperties.class)
@ComponentScan(basePackages = "de.muenchen.oss.digiwf.spring.security")
@AutoConfiguration
public class SpringSecurityAutoConfiguration {
}
