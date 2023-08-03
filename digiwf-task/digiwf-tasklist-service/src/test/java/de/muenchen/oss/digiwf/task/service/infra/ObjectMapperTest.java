package de.muenchen.oss.digiwf.task.service.infra;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.digiwf.task.PolyflowObjectMapper;
import io.holunda.camunda.taskpool.api.task.TaskCreatedEngineEvent;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectMapperTest {

    private final ObjectMapper objectMapper = PolyflowObjectMapper.DEFAULT;

    @Test
    public void deserializes_event() throws IOException {
        String json = Files.readString(Path.of("src/test/resources/task-created.json"));
        var event = objectMapper.readValue(json, TaskCreatedEngineEvent.class);
        assertThat(event).isNotNull();
    }
}
