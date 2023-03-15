package io.muenchendigital.digiwf.task.service.application.port.out.engine;

import java.util.Map;

/**
 * Port to send commands to the process engine.
 */
public interface TaskCommandPort {


  void completeTask(String taskId, Map<String, Object> payload);
}
