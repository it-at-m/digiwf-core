package de.muenchen.oss.digiwf.connector.adapter.camunda.rest.in;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class CamundaClientConfigurationTest {

    private CamundaClientConfiguration config;
    private List<String> initialFilteredVariables;

    @BeforeEach
    void setUp() {
        initialFilteredVariables = Arrays.asList("var1", "var2", "var3");
        config = new CamundaClientConfiguration(initialFilteredVariables);
    }

    @Test
    void testGetFilters() {
        List<String> expectedFilters = Arrays.asList("var1", "var2", "var3",
                CamundaClientConfiguration.MESSAGE_NAME,
                CamundaClientConfiguration.TOPIC_NAME,
                CamundaClientConfiguration.TYPE_NAME);
        List<String> actualFilters = config.getFilters();

        assertThat(actualFilters).containsAll(expectedFilters);
    }
}
