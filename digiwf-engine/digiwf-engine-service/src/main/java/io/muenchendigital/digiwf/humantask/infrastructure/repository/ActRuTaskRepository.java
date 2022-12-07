package io.muenchendigital.digiwf.humantask.infrastructure.repository;

import io.muenchendigital.digiwf.humantask.infrastructure.entity.camunda.ActRuTaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActRuTaskRepository extends JpaRepository<ActRuTaskEntity, String> {
    Page<ActRuTaskEntity> findAllByAssignee(final String assigneeId, final Pageable pageable);

    @Query(value = "select t from ActRuTask t inner join t.actRuIdentities i where (t.assignee = :assignee) or (t.assignee is not null and lower(i.groupId) in :groupIds and i.type = 'candidate')",
    countQuery = "select count(t) from ActRuTask t inner join t.actRuIdentities i where (t.assignee = :assignee) or (t.assignee is not null and lower(i.groupId) in :groupIds and i.type = 'candidate')")
    Page<ActRuTaskEntity> findAllAssignedTasksByGroupIds(@Param("assignee") String assignee, @Param("groupIds") final List<String> lowerGroupIds, final Pageable pageable);
    @Query(value = "select t from ActRuTask t inner join t.actRuIdentities i where (t.assignee = :assignee) or (t.assignee is not null and lower(i.groupId) in :groupIds and i.type = 'candidate')",
            countQuery = "select count(t) from ActRuTask t inner join t.actRuIdentities i where (t.assignee = :assignee) or (t.assignee is not null and lower(i.groupId) in :groupIds and i.type = 'candidate')")
    Page<ActRuTaskEntity> findAllUnAssignedTasksByGroupIds(@Param("assignee") String assignee, @Param("groupIds") final List<String> lowerGroupIds, final Pageable pageable);

}
