package de.muenchen.oss.digiwf.humantask.infrastructure.repository;

import de.muenchen.oss.digiwf.humantask.infrastructure.entity.TaskInfoEntity;
import de.muenchen.oss.digiwf.humantask.infrastructure.entity.camunda.ActRuTaskEntity;
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
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Repository
public class ActRuTaskSearchRepository extends ActRuTaskCriteriaProvider {
    private final EntityManager em;

    /**
     * returns a page of user tasks
     * @param assigneeId id of user
     * @param searchQuery optional search query string for test search
     * @param followUp boolean flag for showing a task what should be listed again
     * @param pageable for setup page number, page size and sort
     * @return
     */

    public Page<ActRuTaskEntity> search(final String assigneeId, final String searchQuery, final Boolean followUp, final Pageable pageable) {
        val cb = em.getCriteriaBuilder();
        val resultQuery = cb.createQuery(ActRuTaskEntity.class);

        final Root<ActRuTaskEntity> actRuTask = resultQuery.from(ActRuTaskEntity.class);
        final Join<ActRuTaskEntity, TaskInfoEntity> taskInfo = actRuTask.join("taskInfoEntity");

        val predicates = getPredicates(assigneeId, searchQuery, followUp, cb, actRuTask, taskInfo);
        val orders = this.getOrderList(pageable, cb, actRuTask);

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

        countQuery
                .select(cb.count(actRuTaskCount))
                .where(predicates);

        val count = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(result, pageable, count);
    }

    private Predicate[] getPredicates(
            final String assigneeId,
            @Nullable final String searchQuery,
            final Boolean isFollowUp,
            final CriteriaBuilder cb,
            final Root<ActRuTaskEntity> actRuTask,
            final Join<ActRuTaskEntity, TaskInfoEntity> taskInfo
    ) {
        final List<Predicate> predicates = new ArrayList<>();
        final Predicate assigneeIdPredicate = cb.equal(actRuTask.get("assignee"), assigneeId);

        predicates.add(assigneeIdPredicate);

        if (searchQuery != null && !searchQuery.isBlank()) {
            predicates.add(this.getSearchQueryPredicates(searchQuery, cb, actRuTask, taskInfo));
        }

        if (isFollowUp) {
            predicates.add(getFollowUpPredicate(cb, actRuTask));
        }

        return predicates.toArray(new Predicate[0]);
    }

    private Predicate getFollowUpPredicate(final CriteriaBuilder cb, Root<ActRuTaskEntity> actRuTask) {
        val today = new Date();
        return cb.or(
                cb.isNull(actRuTask.get("followUpDate")),
                cb.lessThanOrEqualTo(actRuTask.get("followUpDate"), today)
        );
    }
}
