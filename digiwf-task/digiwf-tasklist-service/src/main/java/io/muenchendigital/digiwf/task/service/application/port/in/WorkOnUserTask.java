package io.muenchendigital.digiwf.task.service.application.port.in;

import io.muenchendigital.digiwf.task.service.domain.TaskWithSchema;

public interface WorkOnUserTask {

  TaskWithSchema loadUserTask(String taskId);

}
