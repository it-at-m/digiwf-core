package de.muenchen.oss.digiwf.task.service.adapter.out.link;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

@ConfigurationProperties(prefix = "digiwf.task-links")
@Getter
@ToString
@RequiredArgsConstructor
public class TaskLinkConfigurationProperties {
    @NestedConfigurationProperty
    private final List<TaskLinkTypeConfig> taskLinkTypes;
}
