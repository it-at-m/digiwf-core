package de.muenchen.oss.digiwf.optimize;

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
        String user = servletRequest.getHeader("X-Forwarded-Preferred-Username"); // X-Forwarded-User contains username (demos@nordlb.de)
        if (user == null || user.isEmpty()) {
            logger.warn("Did not find user.");
            result.setAuthenticated(false);
        } else {
            logger.warn("User logged info {}", user);
            result.setAuthenticatedUser(user);

            result.setAuthenticated(true);
        }
        return result;
    }
}
