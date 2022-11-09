/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.legacy.form.domain.validator;

import io.muenchendigital.digiwf.legacy.form.domain.model.FormField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 * Check user input against whitelist.
 *
 * @author martin.dietirch
 */
@Slf4j
public class SantiyCheckHandler implements ValidationHandler {

    @Value("${digiwf.form.whitelist}")
    private String whitelist;

    @Override
    public boolean validate(final Object value, final FormField field) {
        return this.validateText(field.getKey(), value);
    }

    protected boolean validateText(final String key, final Object value) {
        if (StringUtils.isBlank((String) value)) {
            return true;
        }
        return ((String) value).matches(this.whitelist);
    }
}
