package io.muenchendigital.digiwf.task.service.adapter.out.engine;

import io.muenchendigital.digiwf.task.service.domain.legacy.Form;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LegacyFormValidationAdapterTest {
    private final LegacyFormValidationAdapter legacyFormValidationAdapter = new LegacyFormValidationAdapter();

    @Test
    public void filter_variables_and_add_default_values() {

        val stringVarFormField = new Form.FormField();
        stringVarFormField.setKey("STRING_VAR");
        stringVarFormField.setType("string");
        stringVarFormField.setDefaultValueField("defaultValueField");

        val form = mock(Form.class);
        when(form.getFormFieldMap()).thenReturn(Map.ofEntries(
                Map.entry("STRING_VAR", stringVarFormField)
        ));

        final Map<String, Object> data = Map.ofEntries(
                Map.entry("STRING_VAR_NOT_IN_SCHEMA", "notInSchemaValue"),
                Map.entry("defaultValueField", "defautlValue")
        );

        val filteredData = legacyFormValidationAdapter.filterVariables(data, form);

        assertThat(filteredData).isEqualTo(Map.of("STRING_VAR", "defautlValue"));

    }

}
