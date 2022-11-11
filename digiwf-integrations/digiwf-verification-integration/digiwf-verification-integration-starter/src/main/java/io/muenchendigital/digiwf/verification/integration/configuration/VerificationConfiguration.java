package io.muenchendigital.digiwf.verification.integration.configuration;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.RoutingCallback;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.message.service.CorrelateMessageService;
import io.muenchendigital.digiwf.spring.cloudstream.utils.configuration.StreamingConfiguration;
import io.muenchendigital.digiwf.verification.integration.registration.domain.service.LinkService;
import io.muenchendigital.digiwf.verification.integration.registration.domain.service.RegistrationService;
import io.muenchendigital.digiwf.verification.integration.shared.repository.VerificationRepository;
import io.muenchendigital.digiwf.verification.integration.verification.domain.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@AutoConfigureBefore({StreamingConfiguration.class})
@ComponentScan(basePackages = {"io.muenchendigital.digiwf.verification.integration"})
@EnableConfigurationProperties({VerificationProperties.class})
public class VerificationConfiguration {

    private final VerificationProperties verificationProperties;

    public static final String TYPE_HEADER_GET_VERIFICATION_LINK = "getVerificationLink";

    /**
     * Configures the {@link LinkService}
     *
     * @return configured LinkService
     */
    @Bean
    @ConditionalOnMissingBean
    public LinkService getLinkService() {
        return new LinkService(verificationProperties.getBaseAddress());
    }

    /**
     * Configures the {@link RegistrationService}
     *
     * @return configured RegistrationService
     */
    @Bean
    @ConditionalOnMissingBean
    public RegistrationService getRegistrationService(final VerificationRepository verificationRepository, final LinkService linkService) {
        return new RegistrationService(verificationRepository, linkService);
    }

    /**
     * Configures the {@link VerificationService}
     *
     * @return configured VerificationService
     */
    @Bean
    @ConditionalOnMissingBean
    public VerificationService getVerificationService(final VerificationRepository verificationRepository, final CorrelateMessageService correlateMessageService) {
        return new VerificationService(verificationRepository, correlateMessageService);
    }

    /**
     * Override the custom router of the digiwf-spring-cloudstream-utils. We only have one type we need to map.
     *
     * @return the custom router
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageRoutingCallback getEventBusRouter() {
        final Map<String, String> typeMappings = new HashMap<>();
        typeMappings.put(TYPE_HEADER_GET_VERIFICATION_LINK, TYPE_HEADER_GET_VERIFICATION_LINK);
        return new RoutingCallback(typeMappings);
    }
}
