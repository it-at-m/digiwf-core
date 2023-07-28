/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.shared.data.serialize;

import de.muenchen.oss.digiwf.legacy.form.domain.model.FieldTypes;
import de.muenchen.oss.digiwf.legacy.form.domain.model.FormField;
import de.muenchen.oss.digiwf.legacy.form.domain.model.Item;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serialization handler for select fields.
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
public class SelectSerializeHandler implements SerializeHandler {

    @Override
    public Boolean isResponsibleFor(final FormField formField) {
        return FieldTypes.SELECT.equals(formField.getType());
    }

    @Override
    public Map<String, Object> serialize(final String key, final Object value, final FormField field) {
        final Map<String, Object> serializedVariables = new HashMap<>();

        serializedVariables.put(key, value);

        if (field.isMultiple()) {
            serializedVariables.putAll(this.serializeMultipleSelectField(key, value, field));
            return serializedVariables;
        }

        if (StringUtils.isBlank((String) value)) {
            return serializedVariables;
        }

        val item = field.getItems()
                .stream()
                .filter(obj -> obj.getValue().equals(value))
                .findFirst()
                .map(Item::getName)
                .orElseThrow(() -> new IllegalArgumentException("Value not present in field: " + value));
        serializedVariables.put(key + "__detail_name", item);

        return serializedVariables;
    }

    private Map<String, Object> serializeMultipleSelectField(final String key, final Object value, final FormField field) {
        final Map<String, Object> complementVariables = new HashMap<>();

        if (value == null || value instanceof String) {
            return complementVariables;
        }

        val items = (List<String>) value;

        final String values = field.getItems()
                .stream()
                .filter(obj -> items.contains(obj.getValue()))
                .map(Item::getName)
                .collect(Collectors.joining(", "));

        complementVariables.put(key + "__detail_name", values);

        return complementVariables;
    }
}
