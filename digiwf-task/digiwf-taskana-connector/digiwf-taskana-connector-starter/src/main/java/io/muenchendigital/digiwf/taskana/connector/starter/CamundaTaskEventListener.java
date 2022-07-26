package io.muenchendigital.digiwf.taskana.connector.starter;

import io.muenchendigital.digiwf.taskana.connector.core.TaskanaTaskListener;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class CamundaTaskEventListener {

    private final TaskanaTaskListener taskanaTaskListener;

    @EventListener
    public void delegateTask(final DelegateTask delegateTask) {
        taskanaTaskListener.notify(delegateTask);
    }


}
