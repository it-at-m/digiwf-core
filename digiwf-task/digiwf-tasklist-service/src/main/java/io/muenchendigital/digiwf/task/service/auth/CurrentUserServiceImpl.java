package io.muenchendigital.digiwf.task.service.auth;

import com.google.common.collect.Sets;
import io.holunda.polyflow.view.auth.User;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Retrieves current user.
 */
@Component
public class CurrentUserServiceImpl implements CurrentUserService {
    @Override
    public User getCurrentUser() {
        var user = getDefaultCurrentUser();
        if (user != null) {
            return new User(user.getName(), Sets.newHashSet(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet())));
        } else {
            throw new AuthenticationCredentialsNotFoundException("Could not detect current authorized user");
        }
    }

    /**
     * Gets auth as user.
     * @return default user or null.
     */
    private DefaultOAuth2User getDefaultCurrentUser() {
        var authentication = getCurrentAuth();
        if (authentication instanceof OAuth2AuthenticationToken && authentication.getPrincipal() instanceof DefaultOAuth2User) {
            return ((DefaultOAuth2User) authentication.getPrincipal());
        } else {
            return null;
        }
    }

    /**
     * Gets current auth.
     * @return authentication.
     */
    private Authentication getCurrentAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
