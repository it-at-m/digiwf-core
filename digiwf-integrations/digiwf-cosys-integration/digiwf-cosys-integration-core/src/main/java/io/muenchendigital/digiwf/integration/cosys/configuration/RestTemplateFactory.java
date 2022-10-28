package io.muenchendigital.digiwf.integration.cosys.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestTemplateFactory {

    private final CosysConfiguration cosysConfiguration;

    @Bean
    public RestTemplate defaultRestTemplate() {
        return new RestTemplate();
    }

    public RestTemplate authenticatedRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(
                (outReq, bytes, clientHttpReqExec) -> {
                    try {
                        outReq.getHeaders().set(
                                HttpHeaders.AUTHORIZATION, "Bearer " + this.generateAccessToken()
                        );
                    } catch (final Exception e) {
                        log.error("Access Token could not be created", e);
                        throw new RuntimeException("Access Token could not be created");
                    }
                    return clientHttpReqExec.execute(outReq, bytes);
                });

        return restTemplate;
    }

    public String generateAccessToken() throws OAuthSystemException, OAuthProblemException {
        final OAuthClient client = new OAuthClient(new URLConnectionClient());
        final OAuthClientRequest request = OAuthClientRequest.tokenLocation(this.cosysConfiguration.getSsoTokenRequestUrl())
                .setGrantType(GrantType.CLIENT_CREDENTIALS)
                .setClientId(this.cosysConfiguration.getSsoTokenClientId())
                .setClientSecret(this.cosysConfiguration.getSsoTokenClientSecret())
                .buildBodyMessage();
        return client.accessToken(request, OAuth.HttpMethod.POST, OAuthJSONAccessTokenResponse.class).getAccessToken();
    }

}
