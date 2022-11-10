/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.shared.data;

import io.muenchendigital.digiwf.legacy.form.domain.model.Form;
import io.muenchendigital.digiwf.legacy.form.domain.model.FormField;
import io.muenchendigital.digiwf.legacy.shared.data.serialize.SerializeHandler;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service to handle data serialization and deserialization
 *
 * @author externer.dl.horn
 */
@Service
@Deprecated
@RequiredArgsConstructor
public class DataService {

    private final List<SerializeHandler> serializeHandler;

    /**
     * Serializes the given variables.
     *
     * @param form      Form to identify the variable types
     * @param variables variables the should be serialized
     * @return serialized variables
     */
    public Map<String, Object> serializeVariables(final Form form, final Map<String, Object> variables) {
        final Map<String, Object> serializedVariables = new HashMap<>();
        for (val entry : variables.entrySet()) {
            val formField = form.getFormField(entry.getKey()).orElse(null);
            if (formField == null) {
                continue;
            }
            serializedVariables.putAll(this.serialize(this.serializeHandler, entry.getKey(), entry.getValue(), formField));
        }
        return serializedVariables;
    }


    /**
     * Filters the given variables.
     *
     * @param form      Form to identify the variable states
     * @param variables variables the should be filtered
     * @return
     */
    public Map<String, Object> filterReadonly(final Form form, final Map<String, Object> variables) {
        return form.getFormFieldMap().values().stream()
                .filter(field -> !field.isReadonly())
                .collect(HashMap::new, (m, v) -> m.put(v.getKey(), variables.getOrDefault(v.getKey(), "")), HashMap::putAll);
    }

    /**
     * Calculates the given variables.
     *
     * @param form      Form to identify the configured default values
     * @param variables variables the should be calculated
     * @return
     */
    public Map<String, Object> calculateDefaultValues(final Form form, final Map<String, Object> variables) {
        val formKeys = form.getFormFieldMap();
        return formKeys.values().stream()
                .map(field -> new AbstractMap.SimpleEntry<>(field.getKey(),
                        this.calculateDefaultValue(variables, field.getKey(), field.getDefaultValueField())))
                .collect(HashMap::new, (m, v) -> m.put(v.getKey(), v.getValue()), HashMap::putAll);
    }

    //HELPER METHODS
    private Map<String, Object> serialize(final List<SerializeHandler> handler, final String key, final Object value, final FormField formField) {
        val responsibleHandler = handler.stream().filter(obj -> obj.isResponsibleFor(formField)).findFirst();

        if (responsibleHandler.isPresent()) {
            return responsibleHandler.get().serialize(key, value, formField);
        } else {
            return Collections.singletonMap(key, value);
        }
    }

    private Object calculateDefaultValue(final Map<String, Object> variables, final String fieldKey, final String defaultValueField) {
        if (variables.containsKey(fieldKey)) {
            return variables.get(fieldKey);
        }

        if (StringUtils.isNoneBlank(defaultValueField) && variables.containsKey(defaultValueField)) {
            return variables.get(defaultValueField);
        }
        return null;
    }

}
