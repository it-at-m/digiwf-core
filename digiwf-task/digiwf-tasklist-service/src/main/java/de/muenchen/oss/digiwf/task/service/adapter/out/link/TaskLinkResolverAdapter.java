package de.muenchen.oss.digiwf.task.service.adapter.out.link;

import de.muenchen.oss.digiwf.task.service.application.port.out.links.TaskLinkResolverPort;
import de.muenchen.oss.digiwf.task.service.domain.TaskLink;
import io.holunda.polyflow.view.Task;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * An adapter for late resolution of the external references of the tasks.
 */
@Component
public class TaskLinkResolverAdapter implements TaskLinkResolverPort {

  private static final Pattern MD_LINK_REGEX = Pattern.compile("^\\[([\\w\\s]+)]\\((https?://[\\w./?=#\\-+]+)\\)$");


  @Override
  public List<TaskLink> apply(Task task) {
    return task
        .getCorrelations()
        .entrySet()
        .stream()
        .map(entry -> resolve((String) entry.getValue(), entry.getKey()))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  private TaskLink resolve(String type, String id) {
    switch(type) {
      case "url":
        return resolveUrl(id);
      default:
        return null;
    }
  }

  private TaskLink resolveUrl(String id) {
    val matcher = MD_LINK_REGEX.matcher(id);
    final String url;
    final String label;
    if (matcher.matches()) {
      url = matcher.group(2);
      label = matcher.group(1);
    } else {
      label = null;
      url = id;
    }
    return new TaskLink("url", url, label, null, null);
  }
}
