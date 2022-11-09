/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.shared.data.serialize;

import io.muenchendigital.digiwf.legacy.form.domain.model.FieldTypes;
import io.muenchendigital.digiwf.legacy.form.domain.model.FormField;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Serialization handler for date inputs.
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
public class DateInputSerializeHandler implements SerializeHandler {

    @Override
    public Boolean isResponsibleFor(final FormField formField) {
        return FieldTypes.TEXT.equals(formField.getType()) && "date".equals(formField.getExt());
    }

    @Override
    public Map<String, Object> serialize(final String key, final Object value, final FormField field) {
        final Map<String, Object> serializedVariables = new HashMap<>();

        serializedVariables.put(key, value);

        if (StringUtils.isBlank((String) value)) {
            return serializedVariables;
        }

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        val localDate = LocalDate.parse(value.toString());

        serializedVariables.put(key + "__detail_format", formatter.format(localDate));

        return serializedVariables;
    }
}
