package de.muenchen.oss.digiwf.optimize.plugin;

import org.camunda.optimize.plugin.importing.variable.PluginVariableDto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


class FilterSensitiveVariableImportAdapterTest {

    @Test
    public void globalFilterTest() {
        List<PluginVariableDto> input = Arrays.asList(
                new PluginVariableDto("1", "FilterMe", "String", "0", null, null, "FilterProcessKey", "FilterProcessID", "1", 1L, null, null),
                new PluginVariableDto("1", "app_process_status", "String", "0", null, null, "FilterProcessKey", "FilterProcessID", "1", 1L, null, null)
        );

        List<PluginVariableDto> output = new FilterSensitiveVariableImportAdapter().adaptVariables(input);

        assertThat(output).hasSize(1);
        assertThat(output.get(0).getName()).isEqualTo("app_process_status");
    }

    @Test
    public void processFilterTest() {

        List<PluginVariableDto> input = Arrays.asList(
                new PluginVariableDto("1", "FilterMe", "String", "0", null, null, "FilterProcessKey", "FilterProcessID", "1", 1L, null, null),
                new PluginVariableDto("1", "Other_Var", "String", "0", null, null, "MobileArbeitBeantragen", "MobileArbeitBeantragenID", "1", 1L, null, null),
                new PluginVariableDto("1", "Antragsteller_Referat", "String", "0", null, null, "MobileArbeitBeantragen", "MobileArbeitBeantragenID", "1", 1L, null, null),
                new PluginVariableDto("1", "Antragsteller_Referat", "String", "0", null, null, "OtherProcess", "OtherProcessID", "1", 1L, null, null)
        );

        List<PluginVariableDto> output = new FilterSensitiveVariableImportAdapter().adaptVariables(input);

        assertThat(output).hasSize(1);
        assertThat(output)
                .extracting(PluginVariableDto::getName, PluginVariableDto::getProcessDefinitionKey)
                .containsExactly(
                        tuple("Antragsteller_Referat", "MobileArbeitBeantragen")
                );
    }
}
