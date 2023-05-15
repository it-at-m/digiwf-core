package io.muenchendigital.digiwf.task.service.adapter.out.engine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.configuration.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @deprecated  legacy adapter to support old schema tasks.
 * Will be removed as soon as all processes have been migrated to schema-based forms.
 */
@Deprecated
@FeignClient(
    name = "${feign.client.config.legacy-task.name:legacy-task}",
    url = "${feign.client.config.legacy-task.url:${feign.client.config.default.url:http://localhost:8080/engine-rest}}",
    configuration = {ClientConfiguration.class}
)
public interface LegacyTaskClient {

  @RequestMapping(value = "/rest/task", method = {RequestMethod.PUT}, produces = {"application/json"})
  void saveTask(@Valid @RequestBody SaveTO save);

  @RequestMapping(value = "/rest/task", method = {RequestMethod.POST}, produces = {"application/json"})
  void completeTask(@Valid @RequestBody final CompleteTO completeTO);

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  static class SaveTO {

    /**
     * Id of the task.
     */
    @NotBlank
    private String taskId;

    /**
     * Variables that are saved.
     * Only variables that are contained in the associated form are valid.
     */
    private Map<String, Object> variables;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  static class CompleteTO {

    /**
     * Id of the task the should be completed.
     */
    @NotBlank
    private String taskId;

    /**
     * Variables that are set during completion.
     * Only variables that are contained in the associated form are valid.
     */
    @NotNull
    private Map<String, Object> variables;

  }
}
