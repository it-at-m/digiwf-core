/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.shared.data.serialize;

import de.muenchen.oss.digiwf.legacy.form.domain.model.FieldTypes;
import de.muenchen.oss.digiwf.legacy.form.domain.model.FormField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * File input serialization handler.
 *
 * @author externer.dl.horn
 */
@Component
@Deprecated
@RequiredArgsConstructor
public class FileInputSerializeHandler implements SerializeHandler {


    @Override
    public Boolean isResponsibleFor(final FormField formField) {
        return FieldTypes.FILE_INPUT.equals(formField.getType());
    }

    @Override
    public Map<String, Object> serialize(final String key, final Object value, final FormField field) {
        final Map<String, Object> serializedVariables = new HashMap<>();
        //clear file values if they are still sent. Not available anymore, but no large bas64 should be persisted in the engine.
        serializedVariables.put(key, "");
        return serializedVariables;
    }
}
