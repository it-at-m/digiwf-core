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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class ActRuGroupTaskSearchRepository extends ActRuTaskCriteriaProvider {
    private final EntityManager em;

    /**
     * returns a page of group tasks
     * @param assigneeId id of user
     * @param lowerCaseGroups list of groups in which the task is includes
     * @param searchQuery optional search query string for test search
     * @param assigned state of the task. is the task already assigned or unassigned
     * @param pageable for setup page number, page size and sort
     * @return page of results
     */
    public Page<ActRuTaskEntity> search(final String assigneeId, final List<String> lowerCaseGroups, final String searchQuery, final Boolean assigned, final Pageable pageable) {
        val cb = em.getCriteriaBuilder();
        val resultQuery = cb.createQuery(ActRuTaskEntity.class);

        final Root<ActRuTaskEntity> actRuTask = resultQuery.from(ActRuTaskEntity.class);
        final Join<ActRuTaskEntity, TaskInfoEntity> taskInfo = actRuTask.join("taskInfoEntity");
        final Join<ActRuTaskEntity, ActRuIdentityLinkEntity> identityLinks = actRuTask.join("actRuIdentities");

        val predicates = getPredicates(assigneeId, lowerCaseGroups, searchQuery, assigned, cb, actRuTask, taskInfo, identityLinks);
        val orders = this.getOrderList(pageable, cb, actRuTask);

        resultQuery
                .where(predicates)
                .orderBy(orders)
                .distinct(true);

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
            predicates.add(this.getSearchQueryPredicates(searchQuery, cb, actRuTask, taskInfo));
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
}
