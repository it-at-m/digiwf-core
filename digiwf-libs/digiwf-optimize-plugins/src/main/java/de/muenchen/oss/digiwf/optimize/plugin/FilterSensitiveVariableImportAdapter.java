package de.muenchen.oss.digiwf.optimize.plugin;

import org.camunda.optimize.plugin.importing.variable.PluginVariableDto;
import org.camunda.optimize.plugin.importing.variable.VariableImportAdapter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterSensitiveVariableImportAdapter implements VariableImportAdapter {

    private final List<String> globalVarWhitelist = List.of("app_process_status");
    private final Map<String, List<String>> processVarWhiteList = Map.of(
            "MobileArbeitBeantragen", List.of("Antragsteller_Referat"),
            "FahrkostenzuschussErstantrag", List.of("Antragsteller_Referat"),
            "FahrkostenzuschussVerlaengern", List.of("Antragsteller_Referat"),
            "FahrkostenzuschussBeenden", List.of("Antragsteller_Referat")
    );

    @Override
    public List<PluginVariableDto> adaptVariables(List<PluginVariableDto> list) {
        return list.stream()
                .filter(this::checkVariable)
                .collect(Collectors.toList());

    }

    private boolean checkVariable(PluginVariableDto pVDto) {
        return globalVarWhitelist.contains(pVDto.getName()) || (
                processVarWhiteList.containsKey(pVDto.getProcessDefinitionKey()) &&
                        processVarWhiteList.get(pVDto.getProcessDefinitionKey()).contains(pVDto.getName())
        );
    }
}
