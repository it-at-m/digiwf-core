package io.muenchendigital.digiwf.service.instance.process.listener;

import io.muenchendigital.digiwf.service.instance.process.properties.S3Properties;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

import static io.muenchendigital.digiwf.service.instance.process.ProcessConstants.PROCESS_S3_ASYNC_CONFIG;
import static io.muenchendigital.digiwf.service.instance.process.ProcessConstants.PROCESS_S3_SYNC_CONFIG;

@RequiredArgsConstructor
@Component
public class TaskListener {

    private final TaskService taskService;
    private final S3Properties s3Properties;

    @EventListener
    public void delegateTask(final DelegateTask delegateTask) {
        // Note: As soon as we move to another tasklist solution like taskana or polyflow we should move this to a configEnricher
        switch (delegateTask.getEventName()) {
            case "create":
                final Map<String, Object> taskVariables = delegateTask.getVariables();
                final String s3Topic = taskVariables.containsKey(PROCESS_S3_ASYNC_CONFIG) ? (String) taskVariables.get(PROCESS_S3_ASYNC_CONFIG) : this.s3Properties.getTopic();
                final String s3HttpApi = taskVariables.containsKey(PROCESS_S3_SYNC_CONFIG) ? (String) taskVariables.get(PROCESS_S3_SYNC_CONFIG) :  this.s3Properties.getHttpAPI();
                this.taskService.setVariable(delegateTask.getId(), PROCESS_S3_ASYNC_CONFIG, s3Topic);
                this.taskService.setVariable(delegateTask.getId(), PROCESS_S3_SYNC_CONFIG, s3HttpApi);
                break;
            case "assignment":
            case "complete":
                break;
        }
    }

}
