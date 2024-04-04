package de.muenchen.oss.digiwf.adapter.out.auth;

import de.muenchen.oss.digiwf.application.port.out.UserToGroupResolverOutPort;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserGroupResolverOutAdapter implements UserToGroupResolverOutPort {
    @Override
    public boolean isUserInOptimizeGroup(@NonNull String username) {
        // FIXME -> think of a better reolution.
        return true;
    }

    /*
        log.info("Loading Optimize users from {} Keycloak realm of {}", properties.getRealm(), properties.getServerUrl());
        var users = keycloak
            .realm(properties.getRealm())
            .roles()
            .get(properties.getOptimizeRoleName())
            .getUserMembers()
            .stream()
            .filter(user -> user.isEnabled() && user.getFirstName() != null && user.getLastName() != null)
            .map(UserRepresentation::getUsername) // username is the login email address demos@nordlb.de
            .collect(Collectors.toList());
        log.info("Loading completed.");


     */

//    @Bean
//    public Keycloak keycloak(@NonNull KeycloakConfigurationProperties keycloakConfigurationProperties) {
//        return KeycloakBuilder.builder()
//            .serverUrl(keycloakConfigurationProperties.getServerUrl())
//            .realm(keycloakConfigurationProperties.getRealm())
//            .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
//            .clientId(keycloakConfigurationProperties.getClientId())
//            .clientSecret(keycloakConfigurationProperties.getClientSecret())
////            .scope("roles openid profile") // no scopes seem to be needed
//            .build();
//    }

//    public class KeycloakConfigurationProperties {
//        /**
//         * Base URL of the keycloak installation.
//         */
//        private String serverUrl;
//        /**
//         * Realm to use.
//         */
//        private String realm;
//        /**
//         * Client id.
//         */
//        private String clientId;
//        /**
//         * Client secret.
//         */
//        private String clientSecret;
//        /**
//         * Name of the optimize role.
//         */
//        private String optimizeRoleName;
//        /**
//         * TTL of the user cache.
//         */
//        private int cacheTtlInMinutes;
//        /**
//         * Duration of periodic cache refresh.
//         */
//        private String cachePreHeatDelay = "PT1M";
//    }

}
