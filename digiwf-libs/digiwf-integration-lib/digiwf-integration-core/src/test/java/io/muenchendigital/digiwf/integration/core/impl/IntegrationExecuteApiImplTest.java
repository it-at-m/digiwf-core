package io.muenchendigital.digiwf.integration.core.impl;

import io.muenchendigital.digiwf.integration.core.api.TechnicalError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class IntegrationExecuteApiImplTest {

    private final IntegrationExecuteApiImpl integrationExecuteApi = new IntegrationExecuteApiImpl();

    // test data
    private final Map<String, Object> event = Map.of("test", "test");


    @BeforeEach
    void setUp() throws NoSuchMethodException {
        this.integrationExecuteApi.register(this.getExampleIntegration());
        this.integrationExecuteApi.register(this.getExampleIntegrationWithoutReturn());
        this.integrationExecuteApi.register(this.getTechnicalErrorIntegraton());
        this.integrationExecuteApi.register(this.getIncidentIntegration());
    }

    @Test
    void testExecuteIntegration() {
        // to improve testability, we use a dummy integration that just returns the event
        final Object result = this.integrationExecuteApi.execute("exampleIntegration", this.event);
        Assertions.assertEquals(this.event, result);
    }

    @Test
    void testExecuteIntegrationWithMoreComplexEvent() {
        // to improve testability, we use a dummy integration that just returns the event
        final Map<String, Object> event = Map.of(
                "test", "test",
                "test2", Map.of("test3", "test3"),
                "test4", Map.of("test5", Map.of("test6", "test6"))
        );
        final Object result = this.integrationExecuteApi.execute("exampleIntegration", event);
        Assertions.assertEquals(event, result);
    }

    @Test
    void testExecuteIntegrationWithoutReturn() {
        // to improve testability, we use a dummy integration that just returns the event
        final Object result = this.integrationExecuteApi.execute("voidExampleIntegration", this.event);
        Assertions.assertEquals(new HashMap<>(), result);
    }

    @Test
    void testExecuteIntegrationThrowsRuntimeExceptionIfNoIntegrationIsRegisteredForType() {
        Assertions.assertThrows(RuntimeException.class, () ->
                this.integrationExecuteApi.execute("non-existing-type", this.event));
    }

    @Test
    void testExecuteIntegrationThrowsTechnicalError() throws NoSuchMethodException {
        Assertions.assertThrows(TechnicalError.class, () ->
                this.integrationExecuteApi.execute("technicalErrorIntegration", this.event));
    }

    @Test
    void testExecuteIntegrationThrowsIncident() throws NoSuchMethodException {
        Assertions.assertThrows(Exception.class, () ->
                this.integrationExecuteApi.execute("incidentIntegration", this.event));
    }

    // helpers

    private Integration getExampleIntegration() throws NoSuchMethodException {
        class ExampleIntegration {
            public Map<String, Object> exampleIntegration(final Map<String, Object> event) {
                return event;
            }
        }
        return new Integration(
                "exampleIntegration",
                3000L,
                new ExampleIntegration(),
                ExampleIntegration.class.getMethod("exampleIntegration", Map.class),
                Map.class,
                Map.class
        );
    }

    private Integration getExampleIntegrationWithoutReturn() throws NoSuchMethodException {
        class ExampleIntegration {
            public void exampleIntegration(final Map<String, Object> event) {

            }
        }
        return new Integration(
                "voidExampleIntegration",
                3000L,
                new ExampleIntegration(),
                ExampleIntegration.class.getMethod("exampleIntegration", Map.class),
                Map.class,
                Map.class
        );
    }

    private Integration getTechnicalErrorIntegraton() throws NoSuchMethodException {
        class TechnicalErrorIntegration {
            public Map<String, Object> technicalError(final Map<String, Object> event) {
                throw new TechnicalError("process-instance", "error-code", "error-message");
            }
        }
        return new Integration(
                "technicalErrorIntegration",
                3000L,
                new TechnicalErrorIntegration(),
                TechnicalErrorIntegration.class.getMethod("technicalError", Map.class),
                Map.class,
                Map.class
        );
    }

    private Integration getIncidentIntegration() throws NoSuchMethodException {
        class IncidentIntegration {
            public Map<String, Object> incident(final Map<String, Object> event) {
                throw new RuntimeException("Incident");
            }
        }
        return new Integration(
                "incidentIntegration",
                3000L,
                new IncidentIntegration(),
                IncidentIntegration.class.getMethod("incident", Map.class),
                Map.class,
                Map.class
        );
    }

}
