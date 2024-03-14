package de.muenchen.oss.digiwf.optimize;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class OAuth2ClientProperties {
    private final String accessTokenUrl;
    private final String clientId;
    private final String clientSecret;
}
