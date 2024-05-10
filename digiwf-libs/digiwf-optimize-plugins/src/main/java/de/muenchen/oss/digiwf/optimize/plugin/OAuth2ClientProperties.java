package de.muenchen.oss.digiwf.optimize.plugin;

import lombok.Builder;

import java.util.Map;

@Builder
public record OAuth2ClientProperties(String ssoIssuerUrl, String clientId,
                                     String clientSecret, String scope) {
    public static OAuth2ClientProperties fromEnv() {
        final Map<String, String> env = System.getenv();
        return OAuth2ClientProperties
                .builder()
                .ssoIssuerUrl(env.get("SSO_ISSUER_URL"))
                .clientId(env.get("SSO_OPTIMIZE_CLIENT_ID"))
                .clientSecret(env.get("SSO_OPTIMIZE_CLIENT_SECRET"))
                .scope(env.getOrDefault("SSO_SCOPE", "openid profile"))
                .build();
    }

    public String accessTokenUrl() {
        return this.ssoIssuerUrl + "/protocol/openid-connect/token";
    }

    public String jwkCertsUrl() {
        return this.ssoIssuerUrl + "/protocol/openid-connect/certs";
    }
}
