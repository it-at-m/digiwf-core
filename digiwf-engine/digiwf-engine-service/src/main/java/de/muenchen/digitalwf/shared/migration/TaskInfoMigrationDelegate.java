package de.muenchen.digitalwf.shared.migration;


import de.muenchen.digitalwf.humantask.infrastructure.entity.TaskInfoEntity;
import de.muenchen.digitalwf.humantask.infrastructure.repository.TaskInfoRepository;
import de.muenchen.digitalwf.legacy.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Migration Delegate for TaskInfoEntities
 */
@Component
@RequiredArgsConstructor
public class TaskInfoMigrationDelegate implements JavaDelegate {

    private final TaskInfoRepository taskInfoRepository;
    private final UserService userService;

    @Override
    public void execute(final DelegateExecution execution) throws Exception {
        final List<TaskInfoEntity> taskInfoEntity = this.taskInfoRepository.findAll();
        taskInfoEntity.forEach(this::setUserName);
        this.taskInfoRepository.saveAll(taskInfoEntity);
    }

    private void setUserName(final TaskInfoEntity taskInfoEntity) {
        if (StringUtils.isBlank(taskInfoEntity.getAssignee())) {
            return;
        }
        taskInfoEntity.setAssignee(this.userService.getUserString(taskInfoEntity.getAssignee()));
    }
}
