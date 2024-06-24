package de.muenchen.oss.digiwf.optimize.plugin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OAuth2AuthenticationExtractorTest {
    private final OAuth2AuthenticationExtractor extractor = new OAuth2AuthenticationExtractor();

    @Test
    void noAuthHeader() {
        val servletRequest = mock(HttpServletRequest.class);
        val authResult = extractor.extractAuthenticatedUser(servletRequest);
        assertFalse(authResult.isAuthenticated());
    }

    @Test
    void illegalAuthHeader() {
        val servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getHeader("Authorization")).thenReturn("illegalJwt");
        val authResult = extractor.extractAuthenticatedUser(servletRequest);
        assertFalse(authResult.isAuthenticated());
    }
}