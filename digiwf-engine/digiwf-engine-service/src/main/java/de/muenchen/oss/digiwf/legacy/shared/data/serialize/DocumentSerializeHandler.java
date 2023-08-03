/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.shared.data.serialize;

import de.muenchen.oss.digiwf.legacy.dms.muc.process.mapper.MetadataProcessDataMapper;
import de.muenchen.oss.digiwf.legacy.form.domain.model.FieldTypes;
import de.muenchen.oss.digiwf.legacy.form.domain.model.FormField;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serialization handler for documents.
 *
 * @author externer.dl.horn
 */
@Component
@RequiredArgsConstructor
public class DocumentSerializeHandler implements SerializeHandler {

    private final MetadataProcessDataMapper metadataMapper;

    @Override
    public Boolean isResponsibleFor(final FormField formField) {
        return FieldTypes.DOCUMENT_INPUT.equals(formField.getType());
    }

    @Override
    public Map<String, Object> serialize(final String key, final Object value, final FormField field) {
        final Map<String, Object> serializedVariables = new HashMap<>();

        serializedVariables.put(key, value);

        if (value == null) {
            return serializedVariables;
        }

        val input = (ArrayList<LinkedHashMap<String, String>>) value;
        val metadataInput = this.metadataMapper.map(input);

        val schriftstuecke = metadataInput.stream()
                .map(obj -> obj.getName() + " (" + obj.getUrl() + ")")
                .collect(Collectors.joining("; "));

        serializedVariables.put(key + "__detail_summary", schriftstuecke);

        return serializedVariables;
    }

}
