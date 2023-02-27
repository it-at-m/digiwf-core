package io.muenchendigital.digiwf.humantask.infrastructure.repository;

import io.muenchendigital.digiwf.humantask.infrastructure.entity.TaskInfoEntity;
import io.muenchendigital.digiwf.humantask.infrastructure.entity.camunda.ActRuTaskEntity;
import lombok.val;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ActRuTaskCriteriaProvider {

    public List<Order> getOrderList(final Pageable pageable, final CriteriaBuilder cb, final Root<ActRuTaskEntity> actRuTask) {
        return pageable.getSort().stream().map(sort -> {
            if (sort.getDirection().isAscending()) {
                return cb.asc(actRuTask.get(sort.getProperty()));
            } else {
                return cb.desc(actRuTask.get(sort.getProperty()));
            }
        }).collect(Collectors.toList());
    }
    public Predicate getSearchQueryPredicates(final String searchQuery, final CriteriaBuilder cb, Root<ActRuTaskEntity> actRuTask, final Join<ActRuTaskEntity, TaskInfoEntity> taskInfo) {
        val lowerSearchTerm = searchQuery.toLowerCase();
        val namePredicate = cb.like(cb.lower(actRuTask.get("name")), "%" + lowerSearchTerm + "%");
        val descriptionPredicate = cb.like(cb.lower(taskInfo.get("description")), "%" + lowerSearchTerm + "%");
        val definitionNamePredicate = cb.like(cb.lower(taskInfo.get("definitionName")), "%" + lowerSearchTerm + "%");
        return cb.or(
                namePredicate,
                descriptionPredicate,
                definitionNamePredicate
        );
    }
}
