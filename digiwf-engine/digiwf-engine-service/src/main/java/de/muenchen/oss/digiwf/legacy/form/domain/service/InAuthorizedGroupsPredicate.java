/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.legacy.form.domain.service;

import de.muenchen.oss.digiwf.legacy.form.infrastructure.entity.FormEntity;
import de.muenchen.oss.digiwf.legacy.user.domain.service.UserService;
import de.muenchen.oss.digiwf.shared.security.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Check authorizations for {@link FormEntity}
 *
 * @author martin.dietrich
 */
@Component
@RequiredArgsConstructor
public class InAuthorizedGroupsPredicate implements Predicate<FormEntity> {

    private final UserAuthenticationProvider userProvider;
    private final UserService userService;

    @Override
    public boolean test(final FormEntity form) {
        if (StringUtils.isEmpty(form.getAuthorization()))
            return true;
        final String username = this.userProvider.getLoggedInUser();
        val user = this.userService.getUserByUserName(username);
        if (user.isPresent()) {
            final List<String> userGroups = this.userService.getGroups(user.get().getLhmObjectId());
            final List<String> authorizedGroups = Arrays.asList(form.getAuthorization().split(","));
            for (final String grp : userGroups) {
                if (authorizedGroups.contains(grp))
                    return true;
            }
        }
        return false;
    }

}
