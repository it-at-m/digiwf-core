package de.muenchen.oss.digiwf.optimize;

import lombok.Builder;

import java.util.Map;

@Builder
public record OAuth2ClientProperties(String accessTokenUrl, String clientId,
                                     String clientSecret) {
    public static OAuth2ClientProperties fromEnv() {
        final Map<String, String> env = System.getenv();
        return OAuth2ClientProperties
                .builder()
                .accessTokenUrl(env.get("SSO_ISSUER_URL") + "/protocol/openid-connect/token")
                .clientId(env.get("SSO_OPTIMIZE_CLIENT_ID"))
                .clientSecret(env.get("SSO_OPTIMIZE_CLIENT_SECRET"))
                .build();
    }
}
