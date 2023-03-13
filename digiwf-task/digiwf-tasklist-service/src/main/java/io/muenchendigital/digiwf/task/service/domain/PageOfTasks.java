package io.muenchendigital.digiwf.task.service.domain;

import lombok.Data;

import java.util.List;

@Data
public class PageOfTasks {
  private final List<TaskWithSchema> tasks;
  private final Integer totalElementsCount;
  private final PagingAndSorting pagingAndSorting;
}
