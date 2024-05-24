package de.muenchen.oss.digiwf.optimize.plugin;

import lombok.Builder;
import lombok.val;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
public record FilterSensitiveVariableProperties(List<String> globalVarWhitelist,
                                                Map<String, List<String>> processVarWhiteList) {

    public static FilterSensitiveVariableProperties fromEnv() {
        final Map<String, String> env = loadEnv();
        val globalVarWhitelist = Arrays.stream(env.getOrDefault("GLOBAL_VAR_WHITELIST", "").split(","))
                .map(String::trim)
                .filter(i -> !i.isEmpty())
                .map(String::toLowerCase).toList();
        val processVarWhitelist = env.keySet().stream().filter(i -> i.startsWith("PROCESS_VAR_WHITELIST_")).collect(Collectors.toMap(
                i -> i.replaceFirst("^PROCESS_VAR_WHITELIST_", "").toLowerCase(),
                i -> Arrays.stream(env.get(i).split(",")).map(String::trim).map(String::toLowerCase).toList()
        ));
        return FilterSensitiveVariableProperties.builder()
                .globalVarWhitelist(globalVarWhitelist)
                .processVarWhiteList(processVarWhitelist)
                .build();
    }

    static Map<String, String> loadEnv() {
        return System.getenv();
    }

    public boolean isGlobalWhitelisted(String varName) {
        return this.globalVarWhitelist.contains(varName.toLowerCase());
    }

    public boolean isProcessWhitelisted(String processDefinitionKey, String varName) {
        return this.processVarWhiteList.containsKey(processDefinitionKey.toLowerCase()) &&
                this.processVarWhiteList.get(processDefinitionKey.toLowerCase()).contains(varName.toLowerCase());
    }
}
