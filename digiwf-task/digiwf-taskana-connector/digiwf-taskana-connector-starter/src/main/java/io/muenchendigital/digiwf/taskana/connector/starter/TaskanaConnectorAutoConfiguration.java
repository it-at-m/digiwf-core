package io.muenchendigital.digiwf.taskana.connector.starter;

import io.muenchendigital.digiwf.taskana.connector.core.TaskDeletionDto;
import io.muenchendigital.digiwf.taskana.connector.core.TaskEventEmitter;
import io.muenchendigital.digiwf.taskana.connector.core.TaskanaTaskListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import pro.taskana.adapter.camunda.dto.ReferencedTask;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class TaskanaConnectorAutoConfiguration {

    @Bean
    public Sinks.Many<Message<ReferencedTask>> taskCreationSink() {
        return new TaskEventEmitter().taskCreationSink();
    }

    @Bean
    public Supplier<Flux<Message<ReferencedTask>>> taskCreationProducer(final Sinks.Many<Message<ReferencedTask>> sink) {
        return sink::asFlux;
    }

    @Bean
    public Sinks.Many<Message<TaskDeletionDto>> taskDeletionSink() {
        return new TaskEventEmitter().taskDeletionSink();
    }

    @Bean
    public Supplier<Flux<Message<TaskDeletionDto>>> taskDeletionProducer(final Sinks.Many<Message<TaskDeletionDto>> sink) {
        return sink::asFlux;
    }

    @Bean
    public CamundaTaskEventListener camundaTaskEventListener(final Sinks.Many<Message<TaskDeletionDto>> taskDeletionSink, final Sinks.Many<Message<ReferencedTask>> taskCreationSink) {
        return new CamundaTaskEventListener(new TaskanaTaskListener(taskDeletionSink, taskCreationSink));
    }


}
