package io.muenchendigital.digiwf.task.service.client;

import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Encapsulation of the Camunda remote client.
 */
@Component
public class RemoteCamundaClientService {
    private final TaskService taskService;

    public RemoteCamundaClientService(@Qualifier("remote") TaskService taskService) {
        this.taskService = taskService;
    }
}
