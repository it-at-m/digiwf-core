package de.muenchen.oss.digiwf.optimize;

import jakarta.ws.rs.client.ClientRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.camunda.optimize.plugin.engine.rest.EngineRestFilter;

@Slf4j
public class OAuth2EngineRestFilter implements EngineRestFilter {
    private final OAuth2Client client = new OAuth2Client(OAuth2ClientProperties.fromEnv());

    @Override
    public void filter(ClientRequestContext requestContext, String engineAlias, String engineName) {
        log.debug("Entering EngineRestFilter");
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
