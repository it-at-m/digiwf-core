/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.form.domain.mapper;

import camundajar.impl.com.google.gson.Gson;
import de.muenchen.oss.digiwf.legacy.form.domain.model.Form;
import de.muenchen.oss.digiwf.legacy.form.infrastructure.entity.FormEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Map between {@link FormEntity} and {@link Form}
 */
@Component
public class FormMapper {

    public FormEntity map2Entity(final Form model) {
        return FormEntity.builder()
                .key(model.getKey())
                .version("v1")
                .authorization(model.getAuthorizedGroups())
                .config(new Gson().toJson(model))
                .build();
    }

    public List<FormEntity> map2Entity(final List<Form> list) {
        return list.stream().map(this::map2Entity).collect(Collectors.toList());
    }

    public Form map(final FormEntity entity) {
        return new Gson().fromJson(entity.getConfig(), Form.class);
    }

    public List<Form> map(final List<FormEntity> list) {
        return list.stream().map(this::map).collect(Collectors.toList());
    }
}
