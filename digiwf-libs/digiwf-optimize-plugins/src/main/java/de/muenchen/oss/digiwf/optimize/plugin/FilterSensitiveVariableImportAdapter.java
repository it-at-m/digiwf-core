package de.muenchen.oss.digiwf.optimize.plugin;

import org.camunda.optimize.plugin.importing.variable.PluginVariableDto;
import org.camunda.optimize.plugin.importing.variable.VariableImportAdapter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Camunda VariableImportAdapter plugin to only import whitelisted variables.
 */
public class FilterSensitiveVariableImportAdapter implements VariableImportAdapter {
    private final FilterSensitiveVariableProperties properties;

    public FilterSensitiveVariableImportAdapter(FilterSensitiveVariableProperties properties) {
        this.properties = properties;
    }

    public FilterSensitiveVariableImportAdapter() {
        this(FilterSensitiveVariableProperties.fromEnv());
    }

    @Override
    public List<PluginVariableDto> adaptVariables(List<PluginVariableDto> list) {
        return list.stream()
                .filter(this::checkVariable)
                .collect(Collectors.toList());

    }

    private boolean checkVariable(PluginVariableDto pVDto) {
        return properties.isGlobalWhitelisted(pVDto.getName()) ||
                properties.isProcessWhitelisted(pVDto.getProcessDefinitionKey(), pVDto.getName());
    }
}
