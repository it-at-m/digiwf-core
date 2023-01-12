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
        val namePredicate = cb.like(actRuTask.get("name"), "%" + searchQuery + "%");
        val descriptionPredicate = cb.like(taskInfo.get("description"), "%" + searchQuery + "%");
        val definitionNamePredicate = cb.like(taskInfo.get("definitionName"), "%" + searchQuery + "%");
        return cb.or(
                namePredicate,
                descriptionPredicate,
                definitionNamePredicate
        );
    }
}
