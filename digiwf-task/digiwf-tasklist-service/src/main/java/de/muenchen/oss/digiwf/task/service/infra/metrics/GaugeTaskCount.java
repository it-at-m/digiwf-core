package de.muenchen.oss.digiwf.task.service.infra.metrics;

import io.holunda.polyflow.view.TaskQueryClient;
import io.holunda.polyflow.view.query.task.TaskCountByApplicationQuery;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GaugeTaskCount {

  private final TaskQueryClient taskQueryClient;
  private final CollectorRegistry collectorRegistry;
  private Gauge taskCountGauge;

  @PostConstruct
  public void init() {
    this.taskCountGauge = Gauge.build()
        .name("polyflow_tasklist_task_count")
        .help("Current task count per application")
        .labelNames("applicationName")
        .register(collectorRegistry);
  }

  @Scheduled(fixedDelayString = "${polyflow.metrics.delay.taskcount}")
  @SneakyThrows
  public void countTasks() {
    var counts = taskQueryClient.query(new TaskCountByApplicationQuery()).get();
    counts.forEach(count ->
        taskCountGauge.labels(count.getApplication()).set(count.getTaskCount())
    );
  }
}
