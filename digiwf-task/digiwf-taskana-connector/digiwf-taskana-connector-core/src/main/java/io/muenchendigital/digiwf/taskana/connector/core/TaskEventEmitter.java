package io.muenchendigital.digiwf.taskana.connector.core;

import org.springframework.messaging.Message;
import pro.taskana.adapter.camunda.dto.ReferencedTask;
import reactor.core.publisher.Sinks;

public class TaskEventEmitter {

    public Sinks.Many<Message<ReferencedTask>> taskCreationSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    public Sinks.Many<Message<TaskDeletionDto>> taskDeletionSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

}
