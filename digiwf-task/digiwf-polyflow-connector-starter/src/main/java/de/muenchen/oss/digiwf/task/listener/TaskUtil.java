package de.muenchen.oss.digiwf.task.listener;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.IdentityLinkType;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class TaskUtil {
  private TaskUtil() {

  }

  /**
   * Retrieves a list of identity links from a task by id.
   * @param taskService task service.
   * @param taskId id of the task.
   * @return set of identity links.
   */
  public static Set<IdentityLink> getTaskCandidates(@NonNull TaskService taskService, @NonNull String taskId) {
    return ((TaskEntity)taskService.createTaskQuery().taskId(taskId).singleResult()).getCandidates();
  }

  /**
   * Transforms a list of string to lower case.
   * @param strings list of strings.
   * @return list of low-case versions.
   */
  public static List<String> toLowerCase(List<String> strings) {
    return strings.stream().map(String::toLowerCase).collect(Collectors.toList());
  }

  /**
   * Transforms a list of identity links into a list of candidate users.
   * @param candidates set of identity links.
   * @return list of user ids.
   */
  public static List<String> getCandidateUsers(Set<IdentityLink> candidates) {
    return candidates.stream().filter(link -> link.getUserId() != null && link.getType().equals(IdentityLinkType.CANDIDATE)).map(IdentityLink::getUserId).collect(Collectors.toList());
  }

  /**
   * Transforms a list of identity links into a list of candidate groups.
   * @param candidates set of identity links
   * @return list of group ids.
   */
  public static List<String> getCandidateGroups(Set<IdentityLink> candidates) {
    return candidates.stream().map(IdentityLink::getGroupId).filter(Objects::nonNull).collect(Collectors.toList());

  }
}
