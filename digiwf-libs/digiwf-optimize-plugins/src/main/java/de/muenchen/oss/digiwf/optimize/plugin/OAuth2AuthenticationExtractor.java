package de.muenchen.oss.digiwf.optimize.plugin;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSourceBuilder;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.DefaultJOSEObjectTypeVerifier;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.AccessTokenType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.val;
import org.camunda.optimize.plugin.security.authentication.AuthenticationExtractor;
import org.camunda.optimize.plugin.security.authentication.AuthenticationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Camunda AuthenticationExtractor plugin to extract authentication from incoming requests
 * by parsing authorization header to oAuth2 access token and verifying it.
 */
public class OAuth2AuthenticationExtractor implements AuthenticationExtractor {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final OAuth2ClientProperties properties = OAuth2ClientProperties.fromEnv();

    @Override
    public AuthenticationResult extractAuthenticatedUser(HttpServletRequest servletRequest) {
        logger.trace("Entering OAuth2AuthenticationExtractor");
        AuthenticationResult result = new AuthenticationResult();
        String authorization = servletRequest.getHeader("Authorization");
        if (authorization != null && !authorization.isBlank()) {
            try {
                // parse token
                val token = AccessToken.parse(authorization, AccessTokenType.BEARER);
                // build verifier
                val jwtProcessor = new DefaultJWTProcessor<>();
                jwtProcessor.setJWSTypeVerifier(new DefaultJOSEObjectTypeVerifier<>(JOSEObjectType.JWT));
                val keySource = JWKSourceBuilder
                        .create(new URL(properties.jwkCertsUrl()))
                        .retrying(true)
                        .build();
                jwtProcessor.setJWSKeySelector(new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, keySource));
                // validate
                val claimsSet = jwtProcessor.process(token.getValue(), null);
                // extract username
                val username = claimsSet.getStringClaim("preferred_username");
                logger.debug("Extracted username {} from token", username);
                // set authentication
                result.setAuthenticatedUser(username);
                result.setAuthenticated(true);
            } catch (ParseException | java.text.ParseException e) {
                logger.error("Error while parsing token", e);
                result.setAuthenticated(false);
            } catch (MalformedURLException | BadJOSEException | JOSEException e) {
                logger.error("Error while validating token", e);
                result.setAuthenticated(false);
            }
        } else {
            logger.warn("No authorization header found");
            result.setAuthenticated(false);
        }
        return result;
    }
}
