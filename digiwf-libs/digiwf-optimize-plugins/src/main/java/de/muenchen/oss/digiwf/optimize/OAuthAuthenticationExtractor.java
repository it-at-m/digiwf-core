package de.muenchen.oss.digiwf.optimize;

import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.AccessTokenType;
import jakarta.servlet.http.HttpServletRequest;
import org.camunda.optimize.plugin.security.authentication.AuthenticationExtractor;
import org.camunda.optimize.plugin.security.authentication.AuthenticationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OAuthAuthenticationExtractor implements AuthenticationExtractor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public AuthenticationResult extractAuthenticatedUser(HttpServletRequest servletRequest) {
        AuthenticationResult result = new AuthenticationResult();
        String authorization = servletRequest.getHeader("Authorization");
        if (authorization != null && !authorization.isBlank()) {
            // logger.warn("Auth header : '{}'", authorization);
            try {
                // TODO: maybe validate the token, see https://connect2id.com/products/nimbus-oauth-openid-connect-sdk/examples/oauth/token-introspection
                var token = AccessToken.parse(authorization, AccessTokenType.BEARER);
                var username = SignedJWT.parse(token.getValue()).getJWTClaimsSet().getStringClaim("preferred_username");
                logger.warn("User logged info {}", username);
                result.setAuthenticatedUser(username);
                result.setAuthenticated(true);
            } catch (ParseException | java.text.ParseException e) {
                logger.error("Could not parse token");
                result.setAuthenticated(false);
            }
        } else {
            logger.warn("No authorization header found");
            result.setAuthenticated(false);
        }

        return result;
    }
}
