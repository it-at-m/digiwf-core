package io.muenchendigital.digiwf.adapter.out.ldap;

import io.muenchendigital.digiwf.application.port.out.UserPort;
import io.muenchendigital.digiwf.legacy.user.domain.model.User;
import io.muenchendigital.digiwf.legacy.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LdapUserAdapter implements UserPort {

    //TODO remove legacy UserService
    private final UserService userService;

    public static Optional<HttpServletRequest> getCurrentHttpRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userService.getUserByUserName(username);
    }

    @Override
    public List<String> getGroups(String userId) {
        return userService.getGroups(userId);
    }
}
