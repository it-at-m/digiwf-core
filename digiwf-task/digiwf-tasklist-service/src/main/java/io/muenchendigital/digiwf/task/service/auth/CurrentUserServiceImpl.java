package io.muenchendigital.digiwf.task.service.auth;

import com.google.common.collect.Sets;
import io.holunda.polyflow.view.auth.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Service to resolve currently logged-in user.
 */
@Component
@RequiredArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {
    
    @Override
    public User getCurrentUser() {
        var authentication = getCurrentAuth();
        if (authentication instanceof JwtAuthenticationToken && authentication.getPrincipal() instanceof Jwt) {
            var jwt = (Jwt) authentication.getPrincipal();
            var authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
            return new User((String) jwt.getClaims().get("lhmObjectID"), Sets.newHashSet(authorities));
        } else {
            throw new AuthenticationCredentialsNotFoundException("Could not detect current authorized user");
        }
    }


    /**
     * Gets current auth.
     *
     * @return authentication.
     */
    private Authentication getCurrentAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
