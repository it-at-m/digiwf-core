package de.muenchen.oss.digiwf.optimize.plugin;

import com.nimbusds.oauth2.sdk.*;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * OAuth2 client to gather client access token.
 */
@RequiredArgsConstructor
@Slf4j
public class OAuth2Client {

    private final OAuth2ClientProperties oAuth2ClientProperties;

    private static HTTPRequest constructClientCredentialsRequest(OAuth2ClientProperties oAuth2ClientProperties) throws URISyntaxException {
        // The credentials to authenticate the client at the token endpoint
        ClientID clientID = new ClientID(oAuth2ClientProperties.clientId());
        Secret clientSecret = new Secret(oAuth2ClientProperties.clientSecret());
        ClientAuthentication clientAuth = new ClientSecretBasic(clientID, clientSecret);
        // The token endpoint
        URI tokenEndpoint = new URI(oAuth2ClientProperties.accessTokenUrl());
        // scope
        Scope scope = Scope.parse(oAuth2ClientProperties.scope());
        // Make the token request
        TokenRequest request = new TokenRequest(tokenEndpoint, clientAuth, new ClientCredentialsGrant(), scope);
        return request.toHTTPRequest();
    }

    public String getAccessToken() throws URISyntaxException, IOException, ParseException {

        HTTPRequest httpRequest = constructClientCredentialsRequest(oAuth2ClientProperties);

        TokenResponse response = TokenResponse.parse(httpRequest.send());

        if (!response.indicatesSuccess()) {
            // We got an error response...
            TokenErrorResponse errorResponse = response.toErrorResponse();
            throw new IllegalStateException(errorResponse.getErrorObject().getDescription());
        }

        AccessTokenResponse successResponse = response.toSuccessResponse();

        // Get the access token, the server may also return a refresh token
        AccessToken accessToken = successResponse.getTokens().getAccessToken();
        // FIXME -> cache it.
        // RefreshToken refreshToken = successResponse.getTokens().getRefreshToken();
        return accessToken.toAuthorizationHeader();
    }
}
