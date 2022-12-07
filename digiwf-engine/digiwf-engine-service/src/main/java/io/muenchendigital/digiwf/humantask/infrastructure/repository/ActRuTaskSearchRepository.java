package io.muenchendigital.digiwf.humantask.infrastructure.repository;

import io.muenchendigital.digiwf.humantask.infrastructure.entity.TaskInfoEntity;
import io.muenchendigital.digiwf.humantask.infrastructure.entity.camunda.ActRuTaskEntity;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Repository
public class ActRuTaskSearchRepository {
    private final EntityManager em;

    public Page<ActRuTaskEntity> find(final String assigneeId, final String searchQuery, final Boolean followUp, final Pageable pageable) {
        val cb = em.getCriteriaBuilder();
        val cq = cb.createQuery(ActRuTaskEntity.class);

        final Root<ActRuTaskEntity> actRuTask = cq.from(ActRuTaskEntity.class);
        final Join<ActRuTaskEntity, TaskInfoEntity> taskInfo = actRuTask.join("taskInfoEntity");

        val predicates = getPredicates(assigneeId, searchQuery, followUp, cb, actRuTask, taskInfo);

        cq.where(predicates);

        val resultQuery = em.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        val result = resultQuery.getResultList();

        val countQuery = cb.createQuery(Long.class);
        val actRuTaskCount = countQuery.from(ActRuTaskEntity.class);
        // join tables for count query. predicates working
        actRuTaskCount.join("taskInfoEntity");

        countQuery
                .select(cb.count(actRuTaskCount))
                .where(predicates);

        val count = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(result, pageable, count);
    }


    private Predicate[] getPredicates(final String assigneeId, final String searchQuery, final Boolean followUp, final CriteriaBuilder cb, Root<ActRuTaskEntity> actRuTask, final Join<ActRuTaskEntity, TaskInfoEntity> taskInfo) {
        final List<Predicate> predicates = new ArrayList<>();
        final Predicate assigneeIdPredicate = cb.equal(actRuTask.get("assignee"), assigneeId);

        predicates.add(assigneeIdPredicate);

        if (searchQuery != null && !searchQuery.isBlank()) {
            predicates.add(getSearchQueryPredicates(searchQuery, cb, actRuTask, taskInfo));
        }

        // FIXME: add followUp

        return predicates.toArray(new Predicate[0]);
    }

    private Predicate getSearchQueryPredicates(final String searchQuery, final CriteriaBuilder cb, Root<ActRuTaskEntity> actRuTask, final Join<ActRuTaskEntity, TaskInfoEntity> taskInfo) {
        final Predicate namePredicate = cb.like(actRuTask.get("name"), "%" + searchQuery + "%");
        final Predicate descriptionPredicate = cb.like(taskInfo.get("description"), "%" + searchQuery + "%");
        final Predicate definitionNamePredicate = cb.like(taskInfo.get("definitionName"), "%" + searchQuery + "%");

        return cb.or(
                namePredicate,
                descriptionPredicate,
                definitionNamePredicate
        );
    }
}
