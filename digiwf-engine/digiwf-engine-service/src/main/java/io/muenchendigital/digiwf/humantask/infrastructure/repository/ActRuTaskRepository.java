package io.muenchendigital.digiwf.humantask.infrastructure.repository;

import io.muenchendigital.digiwf.humantask.infrastructure.entity.ActRuTaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActRuTaskRepository extends JpaRepository<ActRuTaskEntity, String> {
    public List<ActRuTaskEntity> findAllByAssignee(final String assigneeId);
    public Page<ActRuTaskEntity> findAllByAssignee(final String assigneeId, final Pageable pageable);
}
