package de.muenchen.oss.digiwf.optimize.plugin;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;


class FilterSensitiveVariablePropertiesTest {
    @Test
    public void testFromEnv() {
        try (val propertiesMock = mockStatic(FilterSensitiveVariableProperties.class)) {
            propertiesMock.when(FilterSensitiveVariableProperties::fromEnv).thenCallRealMethod();
            propertiesMock.when(FilterSensitiveVariableProperties::builder).thenCallRealMethod();
            propertiesMock.when(FilterSensitiveVariableProperties::loadEnv).thenReturn(Map.of(
                    "GLOBAL_VAR_WHITELIST", "app_process_status, test_var",
                    "PROCESS_VAR_WHITELIST_MobileArbeitBeantragen", "Antragsteller_Referat,test_var2",
                    "PROCESS_VAR_WHITELIST_FahrkostenzuschussErstantrag", "Antragsteller_Referat",
                    "PROCESS_VAR_WHITELISTasd", "Antragsteller_Referat"
            ));

            val properties = FilterSensitiveVariableProperties.fromEnv();
            assertThat(properties.globalVarWhitelist()).hasSize(2).contains("app_process_status", "test_var");
            assertThat(properties.processVarWhiteList()).hasSize(2)
                    .containsEntry(
                            "MobileArbeitBeantragen".toLowerCase(),
                            List.of("Antragsteller_Referat".toLowerCase(), "test_var2".toLowerCase())
                    )
                    .containsEntry(
                            "FahrkostenzuschussErstantrag".toLowerCase(),
                            List.of("Antragsteller_Referat".toLowerCase())
                    );
        }
    }

    @Test
    public void testEmptyEnv() {
        try (val propertiesMock = mockStatic(FilterSensitiveVariableProperties.class)) {
            propertiesMock.when(FilterSensitiveVariableProperties::fromEnv).thenCallRealMethod();
            propertiesMock.when(FilterSensitiveVariableProperties::builder).thenCallRealMethod();
            propertiesMock.when(FilterSensitiveVariableProperties::loadEnv).thenReturn(Map.of(
                    "PROCESS_VAR_WHITELISTasd", "Antragsteller_Referat"
            ));

            val properties = FilterSensitiveVariableProperties.fromEnv();
            assertThat(properties.globalVarWhitelist()).hasSize(0);
            assertThat(properties.processVarWhiteList()).hasSize(0);
        }
    }
}