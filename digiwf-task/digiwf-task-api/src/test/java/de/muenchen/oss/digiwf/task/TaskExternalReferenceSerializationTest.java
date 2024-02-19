package de.muenchen.oss.digiwf.task;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.holunda.camunda.bpm.data.CamundaBpmData;
import lombok.val;
import org.camunda.bpm.engine.variable.VariableMap;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TaskExternalReferenceSerializationTest {


    @Test
    public void should_deserialize() throws Exception {

        List<TaskExternalReference> links = Arrays.asList(
            new TaskExternalReference("url", "[München](https://www.muenchen.de/)"),
            new TaskExternalReference("zammad", "LHM11004832"),
            new TaskExternalReference("mucsdms", "[Vorgang 41134](COO.2150.307.2.41134)")
        );
        final VariableMap variables = CamundaBpmData.builder()
            .set(TaskVariables.TASK_EXTERNAL_LINKS, links)
            .build();

        ObjectMapper mapper = new ObjectMapper();
        val jsonized = mapper.writeValueAsString(variables);


        String json = "{\"app_task_external_links\":[{\"type\":\"url\",\"identity\":\"[München](https://www.muenchen.de/)\"},{\"type\":\"zammad\",\"identity\":\"LHM11004832\"},{\"type\":\"mucsdms\",\"identity\":\"[Vorgang 41134](COO.2150.307.2.41134)\"}]}";
        assertThat(jsonized).isEqualTo(json);

        val stringType = mapper.getTypeFactory().constructType(String.class);
        val listType = mapper.getTypeFactory().constructCollectionLikeType(List.class, TaskExternalReference.class);
        Map<String, List<TaskExternalReference>> map = mapper.readValue(json, mapper.getTypeFactory().constructMapLikeType(Map.class, stringType, listType));
        val appTaskExternalLinks = map.get("app_task_external_links");
        assertThat(appTaskExternalLinks).isEqualTo(links);
    }
}
