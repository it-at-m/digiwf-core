package io.muenchendigital.digiwf.task.service.domain;

import lombok.Data;

@Data
public class PagingAndSorting {
  private final Integer pageIndex;
  private final Integer pageSize;
  private final String sort;

  public String getSanitizedSort() {
    if (sort == null) {
      return "+createdDate";
    } else {
      if (sort.charAt(0) != '+' || sort.charAt(0) != '-') {
        throw new IllegalArgumentException("Sort argument must start with '+' for ascending or '-' for descending");
      }
      return sort;
    }
  }
}
