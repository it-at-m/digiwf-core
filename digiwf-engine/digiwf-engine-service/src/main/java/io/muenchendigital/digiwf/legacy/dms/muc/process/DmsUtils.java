/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.muc.process;

import com.google.gson.Gson;
import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.Metadata;
import io.muenchendigital.digiwf.legacy.dms.muc.process.mapper.MetadataProcessDataMapper;
import io.muenchendigital.digiwf.legacy.dms.muc.process.readschriftstueck.ReadSchriftstueckeData;
import lombok.RequiredArgsConstructor;

import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.impl.context.Context;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Component("dms")
@RequiredArgsConstructor
public class DmsUtils {

    private final MetadataProcessDataMapper metadataMapper;

    public String mapSchriftstuecke(final String fieldString) {
        val fields = Arrays.stream(fieldString.split(";"))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());

        val execution = Context.getBpmnExecutionContext().getExecution();
        val readData = new ArrayList<ReadSchriftstueckeData>();

        for (val field : fields) {
            val value = execution.getVariable(field);

            val metadata = new ArrayList<Metadata>();

            if (value == null || StringUtils.isBlank(value.toString())) {
                continue;
            } else {

                val input = (ArrayList<LinkedHashMap<String, String>>) value;
                val metadataInput = this.metadataMapper.map(input);
                metadata.addAll(metadataInput);
            }

            val data = ReadSchriftstueckeData.builder()
                    .fieldKey(field)
                    .schriftstuecke(metadata)
                    .build();

            readData.add(data);
        }

        return new Gson().toJson(readData.toArray(new ReadSchriftstueckeData[0]));
    }

}
