package de.muenchen.oss.digiwf.optimize.plugin;

import jakarta.ws.rs.client.ClientRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.camunda.optimize.plugin.engine.rest.EngineRestFilter;

/**
 * Camunda EngineRestFilter plugin to append oAuth2 client access token to outgoing requests as authorization header.
 */
@Slf4j
public class OAuth2EngineRestFilter implements EngineRestFilter {
    private final OAuth2Client client;

    public OAuth2EngineRestFilter(OAuth2Client client) {
        this.client = client;
    }

    public OAuth2EngineRestFilter() {
        this(new OAuth2Client(OAuth2ClientProperties.fromEnv()));
    }

    @Override
    public void filter(ClientRequestContext requestContext, String engineAlias, String engineName) {
        log.trace("Entering EngineRestFilter");
        try {
            log.trace("Retrieving access token");
            var accessToken = client.getAccessToken();
            log.trace("Using token: {}", accessToken);
            requestContext.getHeaders().add("Authorization", accessToken);
        } catch (Exception e) {
            log.error("Error retrieving OAuth2 access token", e);
            throw new RuntimeException(e);
        }
    }

}
