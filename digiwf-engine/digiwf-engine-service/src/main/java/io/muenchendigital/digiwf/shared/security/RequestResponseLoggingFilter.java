/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.shared.security;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * This filter logs the username for requests.
 */
@Component
@Order(1)
@Slf4j
public class RequestResponseLoggingFilter implements Filter {

    @Getter
    private static final String NAME_UNAUTHENTICATED_USER = "unauthenticated";

    private static final String TOKEN_USER_NAME = "user_name";

    private static final String REQUEST_LOGGING_MODE_ALL = "all";

    private static final String REQUEST_LOGGING_MODE_CHANGING = "changing";

    private static final List<String> CHANGING_METHODS = Arrays.asList("POST", "PUT", "PATCH", "DELETE");

    /**
     * The property or a zero length string if no property is available.
     */
    @Value("${security.logging.requests:}")
    private String requestLoggingMode;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        log.debug("Initializing filter: {}", this);
    }

    /**
     * The method logs the username extracted out of the {@link SecurityContext}.
     * Additionally to the username, the kind of HTTP-Request and the targeted URI is logged.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (this.checkForLogging(httpRequest)) {
            log.info("User {} executed {} on URI {}",
                    getUsername(),
                    httpRequest.getMethod(),
                    httpRequest.getRequestURI()
            );
        }
        chain.doFilter(request, response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        log.debug("Destructing filter: {}", this);
    }

    /**
     * The method checks if logging the username should be done.
     *
     * @param httpServletRequest The request to check for logging.
     * @return True if logging should be done otherwise false.
     */
    private boolean checkForLogging(final HttpServletRequest httpServletRequest) {
        return this.requestLoggingMode.equals(REQUEST_LOGGING_MODE_ALL)
                || (this.requestLoggingMode.equals(REQUEST_LOGGING_MODE_CHANGING)
                && CHANGING_METHODS.contains(httpServletRequest.getMethod()));
    }

    /**
     * The method extracts the username out of the {@link OAuth2Authentication}.
     *
     * @return The username or a placeholder if there is no {@link OAuth2Authentication} available.
     */
    private static String getUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            final OAuth2Authentication oauth2Authentication = (OAuth2Authentication) authentication;
            final HashMap details = (HashMap) oauth2Authentication.getUserAuthentication().getDetails();
            return (String) details.get(TOKEN_USER_NAME);
        } else {
            return NAME_UNAUTHENTICATED_USER;
        }
    }

}
