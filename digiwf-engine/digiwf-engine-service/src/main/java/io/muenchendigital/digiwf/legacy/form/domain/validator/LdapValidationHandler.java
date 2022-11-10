/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.form.domain.validator;

import io.muenchendigital.digiwf.legacy.form.domain.model.FieldTypes;
import io.muenchendigital.digiwf.legacy.form.domain.model.FormField;
import io.muenchendigital.digiwf.legacy.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Validation Handler for ldap inputs.
 * Used to ensure ldapOus.
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
public class LdapValidationHandler implements ValidationHandler {

    private final UserService userService;

    public String getType() {
        return FieldTypes.LDAP_INPUT;
    }

    @Override
    public boolean validate(final Object value, final FormField field) {

        if (!this.getType().equals(field.getType())) {
            return true;
        }

        if (StringUtils.isBlank((String) value)) {
            return true;
        }

        if (StringUtils.isBlank(field.getLdapOus())) {
            return true;
        }

        val groups = this.userService.getGroups(value.toString());

        val ous = field.getLdapOus().toLowerCase().split(",");

        return Arrays.stream(ous).anyMatch(groups::contains);
    }
}
