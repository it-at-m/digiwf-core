package io.muenchendigital.digiwf.humantask.domain.service;

import io.muenchendigital.digiwf.humantask.domain.mapper.ActRuTaskMapper;
import io.muenchendigital.digiwf.humantask.domain.model.ActRuTask;
import io.muenchendigital.digiwf.humantask.infrastructure.repository.ActRuGroupTaskSearchRepository;
import io.muenchendigital.digiwf.humantask.infrastructure.repository.ActRuTaskSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActRuTaskService {
    private final ActRuTaskMapper actRuTaskMapper;
    private final ActRuGroupTaskSearchRepository actRuGroupTaskSearchRepository;
    private final ActRuTaskSearchRepository actRuTaskSearchRepository;

    public Page<ActRuTask> getActRuTaskEntityByAssigneeId(final String assigneeId, @Nullable final String query, final Boolean followUp, final Pageable pageable) {
        return this.actRuTaskSearchRepository.search(assigneeId, query, followUp, pageable).map(actRuTaskMapper::map2Model);
    }

    public Page<ActRuTask> getAssignedGroupTasks(final String userId, final List<String> groups, @Nullable final String query, final Pageable pageable) {
        return this.getGroupTasks(userId, groups, true, query, pageable);
    }

    public Page<ActRuTask> getUnassignedGroupTasks(final String userId, final List<String> groups, @Nullable final String query, final Pageable pageable) {
        return this.getGroupTasks(userId, groups, false, query, pageable);
    }

    private Page<ActRuTask> getGroupTasks(final String userId, final List<String> groups, final Boolean assigned, @Nullable final String query, final Pageable pageable) {
        val lowerCaseGroups = groups.stream().map(String::toLowerCase).collect(Collectors.toList());
        return this.actRuGroupTaskSearchRepository.search(userId, lowerCaseGroups, query, assigned, pageable).map(actRuTaskMapper::map2Model);
    }
}
