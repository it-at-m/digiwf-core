/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.legacy.form.api.mapper;

import de.muenchen.oss.digiwf.legacy.form.api.transport.FormFieldTO;
import de.muenchen.oss.digiwf.legacy.form.api.transport.RuleTO;
import de.muenchen.oss.digiwf.legacy.form.domain.model.FieldTypes;
import de.muenchen.oss.digiwf.legacy.form.domain.model.FormField;
import de.muenchen.oss.digiwf.legacy.shared.mapper.BaseTOMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * Map between {@link FormFieldTO} and {@link FormField}
 *
 * @author martin.dietrich
 */
@Mapper
public abstract class FormFieldTOMapper implements BaseTOMapper<FormFieldTO, FormField> {

    @Value("${digiwf.form.whitelist}")
    protected String whitelist;

    static FormFieldTOMapper INSTANCE = Mappers.getMapper(FormFieldTOMapper.class);

    @Override
    public List<FormField> map(final List<FormFieldTO> to) {
        return null;
    }

    @Override
    public List<FormFieldTO> map2TO(final List<FormField> model) {
        if (model == null) {
            return null;
        }

        final List<FormFieldTO> list1 = new ArrayList<>(model.size());
        for (final FormField item : model) {
            final FormFieldTO to = INSTANCE.map2TO(item);
            if (this.needsSanitizing(to)) {
                this.addSanitaryRule(to);
            }
            list1.add(to);
        }
        return list1;
    }

    private boolean needsSanitizing(final FormFieldTO to) {
        // only apply to text and textarea
        if (!FieldTypes.TEXT_AREA.equals(to.getType()) && !FieldTypes.TEXT.equals(to.getType())) {
            return false;
        }
        // allow values formerly persisted
        if (to.isReadonly()) {
            return false;
        }
        return true;
    }

    private void addSanitaryRule(final FormFieldTO to) {
        final RuleTO sanitaryRule = new RuleTO();
        sanitaryRule.setType("sanitary");
        sanitaryRule.setValue(this.whitelist);
        to.getRules().add(sanitaryRule);
    }
}
