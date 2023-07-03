package io.muenchendigital.digiwf.adapter.out.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserAuthenticationProviderImplTest {
    private static final String USERNAME = "testUser";
    private static final Set<String> ROLES = new HashSet<String>() {{
        add("ROLE_USER");
    }};

    private CurrentUserAdapter userAuthenticationProvider;
    private Authentication authentication;
    private Jwt jwt;

    @BeforeEach
    public void setup() {
        // Given
        userAuthenticationProvider = new CurrentUserAdapter();
        authentication = mock(Authentication.class);
        jwt = mock(Jwt.class);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testGetLoggedInUsername() {
        // Given
        when(authentication.getPrincipal()).thenReturn(jwt);
        when(jwt.getClaims()).thenReturn(Map.of(CurrentUserAdapter.USER_ATTRIBUTE, USERNAME));

        // When
        String username = userAuthenticationProvider.getLoggedInUsername();

        // Then
        assertEquals(USERNAME, username);
    }

    @Test
    public void testGetLoggedInUserRoles() {
        // Given
        when(authentication.getPrincipal()).thenReturn(jwt);
        when(jwt.getClaims()).thenReturn(Map.of(CurrentUserAdapter.ROLES_ATTRIBUTE, ROLES));

        // When
        Set<String> roles = userAuthenticationProvider.getLoggedInUserRoles();

        // Then
        assertEquals(ROLES, roles);
    }

    @Test
    public void testUnauthenticatedUser() {
        // Given
        when(authentication.getPrincipal()).thenReturn(new Object());

        // When
        String username = userAuthenticationProvider.getLoggedInUsername();
        Set<String> roles = userAuthenticationProvider.getLoggedInUserRoles();

        // Then
        assertEquals(CurrentUserAdapter.NAME_UNAUTHENTICATED_USER, username);
        assertEquals(Set.of(), roles);
    }
}