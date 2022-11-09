/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.muc.process.mapper;

import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.Metadata;

import lombok.val;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MetadataProcessDataMapper {

    public List<LinkedHashMap> map2HashMap(final List<Metadata> metadata) {
        val list = new ArrayList<LinkedHashMap>();

        for (val data : metadata) {
            val props = new LinkedHashMap<String, String>();

            props.put("name", data.getName());
            props.put("type", data.getType());
            props.put("url", data.getUrl());

            list.add(props);
        }

        return list;
    }

    public List<Metadata> map(final List<LinkedHashMap<String, String>> metadata) {
        return metadata.stream()
                .map(obj -> Metadata.builder()
                        .type(obj.getOrDefault("type", ""))
                        .name(obj.getOrDefault("name", ""))
                        .url(obj.getOrDefault("url", ""))
                        .build())
                .collect(Collectors.toList());
    }
}
