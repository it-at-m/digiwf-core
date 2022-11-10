/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.shared.data.serialize;

import io.muenchendigital.digiwf.legacy.form.domain.model.FieldTypes;
import io.muenchendigital.digiwf.legacy.form.domain.model.FormField;
import io.muenchendigital.digiwf.legacy.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Serialization handler for ldapt fields.
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
public class LdapInputSerializeHandler implements SerializeHandler {

    private final UserService userService;

    @Override
    public Boolean isResponsibleFor(final FormField formField) {
        return FieldTypes.LDAP_INPUT.equals(formField.getType());
    }

    @Override
    public Map<String, Object> serialize(final String key, final Object value, final FormField field) {
        final Map<String, Object> serializedVariables = new HashMap<>();

        serializedVariables.put(key, value);

        if (StringUtils.isBlank((String) value)) {
            return serializedVariables;
        }

        val user = this.userService.getUser(value.toString());
        serializedVariables.put(key + "__detail_name", user.getForename() + " " + user.getSurname());
        serializedVariables.put(key + "__detail_email", user.getEmail());
        serializedVariables.put(key + "__detail_ou", user.getOu());

        return serializedVariables;
    }
}
