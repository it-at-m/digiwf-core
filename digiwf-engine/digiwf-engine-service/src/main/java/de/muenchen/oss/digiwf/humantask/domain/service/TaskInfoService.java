package de.muenchen.oss.digiwf.humantask.domain.service;

import de.muenchen.oss.digiwf.humantask.domain.mapper.TaskInfoMapper;
import de.muenchen.oss.digiwf.humantask.domain.model.TaskInfo;
import de.muenchen.oss.digiwf.humantask.domain.model.TaskInfoUpdate;
import de.muenchen.oss.digiwf.humantask.infrastructure.entity.TaskInfoEntity;
import de.muenchen.oss.digiwf.humantask.infrastructure.repository.TaskInfoRepository;
import de.muenchen.oss.digiwf.humantask.process.ProcessTaskConstants;
import de.muenchen.oss.digiwf.legacy.user.domain.service.UserService;
import de.muenchen.oss.digiwf.process.definition.domain.service.ServiceDefinitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service to handle TaskInfo Objects in DigiWF.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskInfoService {

    private final TaskInfoRepository taskInfoRepository;
    private final TaskInfoMapper taskInfoMapper;
    private final ServiceDefinitionService serviceDefinitionService;
    private final UserService userService;

    public TaskInfo findByTaskId(final String taskId) {
        return this.taskInfoRepository.findById(taskId)
                .map(this.taskInfoMapper::map2Model)
                .orElseThrow();
    }

    public void createTaskInfo(final DelegateTask task) {
        final ProcessDefinition definition = this.getProcessDefinition(task);
        final String user = Optional.ofNullable(task.getAssignee())
                .map(this.userService::getUserString)
                .orElse("");

        final TaskInfo taskInfo = new TaskInfo(
                task.getId(),
                ProcessTaskConstants.TASK_DESCRIPTION_VARIABLE.from(task).getOrDefault(ProcessTaskConstants.TASK_DESCRIPTION_DIGITALWF_VARIABLE.from(task).getOrDefault("")),
                definition.getName(),
                user,
                task.getProcessInstanceId());

        this.save(taskInfo);
    }

    public void updateTaskInfo(final String taskId, final TaskInfoUpdate update) {
        final TaskInfo model = this.findByTaskId(taskId);
        final String user = Optional.ofNullable(update.getAssignee())
                .map(this.userService::getUserString)
                .orElse("");
        model.updateAssignee(user);
        this.save(model);
    }

    public void deleteTaskInfo(final String taskId) {
        this.taskInfoRepository.deleteById(taskId);
    }


    //------------------------------- helper methods -------------------------------//

    private void save(final TaskInfo taskInfo) {
        final TaskInfoEntity entity = this.taskInfoMapper.map2Entity(taskInfo);
        this.taskInfoRepository.save(entity);
    }

    private ProcessDefinition getProcessDefinition(final DelegateTask task) {
        return this.serviceDefinitionService.getServiceDefinition(task.getProcessDefinitionId());
    }
}
