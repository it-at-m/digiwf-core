package de.muenchen.oss.digiwf.optimize;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.function.Supplier;

@Slf4j
public class EnvironmentAdapter implements Supplier<OAuth2ClientProperties> {

    @Override
    public OAuth2ClientProperties get() {
        final Map<String, String> env = System.getenv();
        return OAuth2ClientProperties
            .builder()
            .accessTokenUrl(env.get("SSO_ISSUER_URL") + "/protocol/openid-connect/token")
            .clientId(env.get("SSO_OPTIMIZE_CLIENT_ID"))
            .clientSecret(env.get("SSO_OPTIMIZE_CLIENT_SECRET"))
            .build();
    }
}
