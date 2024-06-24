package de.muenchen.oss.digiwf.optimize.plugin;

import org.camunda.optimize.plugin.importing.variable.PluginVariableDto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


class FilterSensitiveVariableImportAdapterTest {

    private final FilterSensitiveVariableProperties properties = FilterSensitiveVariableProperties.builder()
            .globalVarWhitelist(List.of("app_process_status".toLowerCase()))
            .processVarWhiteList(Map.of(
                    "MobileArbeitBeantragen".toLowerCase(), List.of("Antragsteller_Referat".toLowerCase()),
                    "FahrkostenzuschussErstantrag".toLowerCase(), List.of("Antragsteller_Referat".toLowerCase()),
                    "FahrkostenzuschussVerlaengern".toLowerCase(), List.of("Antragsteller_Referat".toLowerCase()),
                    "FahrkostenzuschussBeenden".toLowerCase(), List.of("Antragsteller_Referat".toLowerCase())
            ))
            .build();

    @Test
    public void globalFilterTest() {
        List<PluginVariableDto> input = Arrays.asList(
                new PluginVariableDto("1", "FilterMe", "String", "0", null, null, "FilterProcessKey", "FilterProcessID", "1", 1L, null, null),
                new PluginVariableDto("1", "app_process_status", "String", "0", null, null, "FilterProcessKey", "FilterProcessID", "1", 1L, null, null)
        );

        List<PluginVariableDto> output = new FilterSensitiveVariableImportAdapter(properties).adaptVariables(input);

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

        List<PluginVariableDto> output = new FilterSensitiveVariableImportAdapter(properties).adaptVariables(input);

        assertThat(output).hasSize(1);
        assertThat(output)
                .extracting(PluginVariableDto::getName, PluginVariableDto::getProcessDefinitionKey)
                .containsExactly(
                        tuple("Antragsteller_Referat", "MobileArbeitBeantragen")
                );
    }
}
