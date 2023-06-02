package io.muenchendigital.digiwf.task.service.adapter.out.engine;

import io.muenchendigital.digiwf.task.service.application.port.out.engine.LegacyFormValidationPort;
import io.muenchendigital.digiwf.task.service.domain.legacy.Form;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @deprecated legacy adapter to support old schema tasks.
 * Will be removed as soon as all processes have been migrated to schema-based forms.
 */
@Component
@Deprecated
public class LegacyFormValidationAdapter implements LegacyFormValidationPort {
    @Override
    public Map<String, Object> filterVariables(Map<String, Object> variables, Form form) {
        val formKeys = form.getFormFieldMap();
        return formKeys.values().stream()
                .map(field -> new AbstractMap.SimpleEntry<>(field.getKey(),
                        this.calculateDefaultValue(variables, field.getKey(), field.getDefaultValueField())))
                .collect(HashMap::new, (m, v) -> m.put(v.getKey(), v.getValue()), HashMap::putAll);
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
