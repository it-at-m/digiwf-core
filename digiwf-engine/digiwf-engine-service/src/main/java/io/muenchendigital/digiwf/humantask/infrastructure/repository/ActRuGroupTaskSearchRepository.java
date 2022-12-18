package io.muenchendigital.digiwf.humantask.infrastructure.repository;

import io.muenchendigital.digiwf.humantask.infrastructure.entity.TaskInfoEntity;
import io.muenchendigital.digiwf.humantask.infrastructure.entity.camunda.ActRuIdentityLinkEntity;
import io.muenchendigital.digiwf.humantask.infrastructure.entity.camunda.ActRuTaskEntity;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// FIXME: improve: https://www.baeldung.com/spring-data-criteria-queries#specifications
@Repository
@AllArgsConstructor
public class ActRuGroupTaskSearchRepository {
    private final EntityManager em;

    public Page<ActRuTaskEntity> search(final String assigneeId, final List<String> lowerCaseGroups, final String searchQuery, final Boolean assigned, final Pageable pageable) {
        val cb = em.getCriteriaBuilder();
        val resultQuery = cb.createQuery(ActRuTaskEntity.class);

        final Root<ActRuTaskEntity> actRuTask = resultQuery.from(ActRuTaskEntity.class);
        final Join<ActRuTaskEntity, TaskInfoEntity> taskInfo = actRuTask.join("taskInfoEntity");
        final Join<ActRuTaskEntity, ActRuIdentityLinkEntity> identityLinks = actRuTask.join("actRuIdentities");

        val predicates = getPredicates(assigneeId, lowerCaseGroups, searchQuery, assigned, cb, actRuTask, taskInfo, identityLinks);
        val orders = getOrderList(pageable, cb, actRuTask);

        resultQuery
                .where(predicates)
                .orderBy(orders);

        val result = em.createQuery(resultQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        val countQuery = cb.createQuery(Long.class);
        val actRuTaskCount = countQuery.from(ActRuTaskEntity.class);
        // join tables for count query. created predicates are working again
        actRuTaskCount.join("taskInfoEntity");
        actRuTaskCount.join("actRuIdentities");

        countQuery
                .select(cb.count(actRuTaskCount))
                .where(predicates);

        val count = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(result, pageable, count);
    }

    private Predicate[] getPredicates(
            final String assigneeId,
            final List<String> lowerCaseGroups,
            @Nullable final String searchQuery,
            final Boolean isAssigned,
            final CriteriaBuilder cb,
            final Root<ActRuTaskEntity> actRuTask,
            final Join<ActRuTaskEntity, TaskInfoEntity> taskInfo,
            final Join<ActRuTaskEntity, ActRuIdentityLinkEntity> identityLinks
    ) {
        final List<Predicate> predicates = new ArrayList<>();
        if (isAssigned) {
            predicates.add(getAssignedPredicate(assigneeId, lowerCaseGroups, cb, actRuTask, identityLinks));
        } else {
            predicates.add(getUnAssignedPredicate(lowerCaseGroups, cb, actRuTask, identityLinks));
        }

        if (searchQuery != null && !searchQuery.isBlank()) {
            predicates.add(getSearchQueryPredicates(searchQuery, cb, actRuTask, taskInfo));
        }


        return predicates.toArray(new Predicate[0]);
    }

    private Predicate getAssignedPredicate(final String assigneeId, final List<String> lowerCaseGroups, final CriteriaBuilder cb, Root<ActRuTaskEntity> actRuTask, final Join<ActRuTaskEntity, ActRuIdentityLinkEntity> identityLinks) {
        final CriteriaBuilder.In<String> inClause = cb.in(identityLinks.get("groupId"));
        for (String lowerCaseGroup : lowerCaseGroups) {
            inClause.value(lowerCaseGroup);
        }
        return cb.or(
                cb.equal(actRuTask.get("assignee"), assigneeId),
                cb.and(
                        cb.isNotNull(actRuTask.get("assignee")),
                        inClause,
                        cb.equal(identityLinks.get("type"), "candidate")
                )
        );
    }

    private Predicate getUnAssignedPredicate(final List<String> lowerCaseGroups, final CriteriaBuilder cb, Root<ActRuTaskEntity> actRuTask, final Join<ActRuTaskEntity, ActRuIdentityLinkEntity> identityLinks) {
        final CriteriaBuilder.In<String> inClause = cb.in(identityLinks.get("groupId"));
        for (String lowerCaseGroup : lowerCaseGroups) {
            inClause.value(lowerCaseGroup);
        }
        return cb.and(
                cb.isNull(actRuTask.get("assignee")),
                inClause,
                cb.equal(identityLinks.get("type"), "candidate")
        );
    }

    private Predicate getSearchQueryPredicates(final String searchQuery, final CriteriaBuilder cb, Root<ActRuTaskEntity> actRuTask, final Join<ActRuTaskEntity, TaskInfoEntity> taskInfo) {
        val namePredicate = cb.like(actRuTask.get("name"), "%" + searchQuery + "%");
        val descriptionPredicate = cb.like(taskInfo.get("description"), "%" + searchQuery + "%");
        val definitionNamePredicate = cb.like(taskInfo.get("definitionName"), "%" + searchQuery + "%");
        return cb.or(
                namePredicate,
                descriptionPredicate,
                definitionNamePredicate
        );
    }

    private List<Order> getOrderList(final Pageable pageable, final CriteriaBuilder cb, final Root<ActRuTaskEntity> actRuTask) {
        if (pageable.getSort().isEmpty()) { // FIXME: necessary?
            return List.of();
        }
        return pageable.getSort().stream().map(sort -> {
            if (sort.getDirection().isAscending()) {
                return cb.asc(actRuTask.get(sort.getProperty()));
            } else {
                return cb.desc(actRuTask.get(sort.getProperty()));
            }
        }).collect(Collectors.toList());
    }
}
