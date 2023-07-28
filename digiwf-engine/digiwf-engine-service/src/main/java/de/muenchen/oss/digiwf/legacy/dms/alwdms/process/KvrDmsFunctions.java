/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.dms.alwdms.process;

import com.google.gson.Gson;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.domain.model.AlwMetadata;
import de.muenchen.oss.digiwf.legacy.dms.alwdms.process.readschriftstuecke.KvrReadSchriftstueckeData;

import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
 * Utility methods that are used for modeling.
 *
 * @author externer.dl.horn
 */
@Component("kvrDms")
public class KvrDmsFunctions {

    /**
     * Loads the metadata for the given fields.
     *
     * @param fieldString List of field keys separated with ;
     * @return metadata
     */
    public byte[] mapSchriftstuecke(final String fieldString) {
        val fields = Arrays.stream(fieldString.split(";"))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());

        final ExecutionEntity execution = Context.getBpmnExecutionContext().getExecution();
        val readData = new ArrayList<KvrReadSchriftstueckeData>();

        for (val field : fields) {
            val value = execution.getVariable(field);

            val metadata = new ArrayList<AlwMetadata>();

            if (value == null || StringUtils.isBlank(value.toString())) {
                continue;
            } else {

                val input = (ArrayList<LinkedHashMap<String, String>>) value;
                val metadataInput = input.stream()
                        .map(obj -> AlwMetadata.builder()
                                .type(obj.getOrDefault("type", ""))
                                .name(obj.getOrDefault("name", ""))
                                .url(obj.getOrDefault("url", ""))
                                .build())
                        .collect(Collectors.toList());
                metadata.addAll(metadataInput);
            }

            val data = KvrReadSchriftstueckeData.builder()
                    .fieldKey(field)
                    .schriftstuecke(metadata)
                    .build();

            readData.add(data);
        }

        return new Gson().toJson(readData.toArray(new KvrReadSchriftstueckeData[0])).getBytes();
    }

}
