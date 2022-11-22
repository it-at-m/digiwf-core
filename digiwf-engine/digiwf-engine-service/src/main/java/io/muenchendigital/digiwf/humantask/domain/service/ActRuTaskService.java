package io.muenchendigital.digiwf.humantask.domain.service;

import io.muenchendigital.digiwf.humantask.domain.mapper.ActRuTaskMapper;
import io.muenchendigital.digiwf.humantask.domain.model.ActRuTask;
import io.muenchendigital.digiwf.humantask.infrastructure.repository.ActRuTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActRuTaskService {

    private final ActRuTaskRepository actRuTaskRepository;
    private final ActRuTaskMapper actRuTaskMapper;

    public Page<ActRuTask> getActRuTaskEntityByAssigneeId(final String assigneeId, final Pageable pageable) {
        val result = this.actRuTaskRepository.findAllByAssignee(assigneeId, pageable);
        return new PageImpl<ActRuTask>(result.getContent().stream().map(actRuTaskMapper::map2Model).collect(Collectors.toList()), result.getPageable(), result.getTotalElements());
    }

    public List<ActRuTask> getActRuTasksIds(List<String> taskIds) {
        return this.actRuTaskRepository.findAllById(taskIds).stream().map(actRuTaskMapper::map2Model).collect(Collectors.toList());
    }
}
