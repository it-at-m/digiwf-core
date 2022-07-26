package io.muenchendigital.digiwf.taskana.connector.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import pro.taskana.adapter.camunda.dto.ReferencedTask;
import pro.taskana.adapter.camunda.dto.VariableValueDto;
import reactor.core.publisher.Sinks;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is responsible for dealing with events within the lifecycle of a camunda user task.
 */
@Slf4j
@RequiredArgsConstructor
public class TaskanaTaskListener implements TaskListener {

    private static final String TASK_STATE_COMPLETED = "COMPLETED";
    private static final String TASK_STATE_CANCELLED = "CANCELLED";
    private static final String TASK_STATE_TERMINATED = "TERMINATED";

    private final ObjectMapper objectMapper = JacksonConfigurator.createAndConfigureObjectMapper();

    private final Sinks.Many<Message<TaskDeletionDto>> taskDeletionEmitter;
    private final Sinks.Many<Message<ReferencedTask>> taskCreationEmitter;

    @Override
    public void notify(final DelegateTask delegateTask) {

        try {
            final String engineName = Context.getProcessEngineConfiguration().getProcessEngineName();

            if (engineName.length() > 128) {
                throw new RuntimeException(
                        "The configured process engine name "
                                + engineName
                                + " is too long. "
                                + "Length must not exceed 128 characters.");
            }

            switch (delegateTask.getEventName()) {
                case "create":
                    this.insertCreateEventIntoOutbox(delegateTask);
                    break;
                case "complete":
                case "delete":
                    this.insertCompleteOrDeleteEventIntoOutbox(delegateTask);
                    break;
                default:
                    break;
            }

        } catch (final Exception e) {
            log.error("Unexpected Exception while trying to process a delegate task", e);
            throw new RuntimeException("Unexpected Exception while trying to process a delegate task", e);
        }
    }

    private void insertCreateEventIntoOutbox(final DelegateTask delegateTask) {
        final ReferencedTask referencedTask = this.getReferencedTask(delegateTask);
        final Message<ReferencedTask> message = MessageBuilder.withPayload(referencedTask).build();
        this.taskCreationEmitter.tryEmitNext(message).orThrow();

    }

    private void insertCompleteOrDeleteEventIntoOutbox(
            final DelegateTask delegateTask) throws Exception {

        if (delegateTask.getEventName().equals("complete")
                && this.taskWasCompletedByTaskanaAdapter(delegateTask)) {
            return;
        }

        String taskState = TASK_STATE_COMPLETED;
        if (delegateTask.getEventName().equals("delete")) {
            if (delegateTask.getExecution().isCanceled()) {
                taskState = TASK_STATE_CANCELLED;
            } else {
                taskState = TASK_STATE_TERMINATED;
            }
        }

        final TaskDeletionDto taskDeletionDto = TaskDeletionDto.builder()
                .id(delegateTask.getId())
                .state(taskState)
                .build();

        final Message<TaskDeletionDto> message = MessageBuilder.withPayload(taskDeletionDto).build();
        this.taskDeletionEmitter.tryEmitNext(message).orThrow();
    }

    private boolean taskWasCompletedByTaskanaAdapter(final DelegateTask delegateTask) {
        return delegateTask.getVariableNamesLocal().contains("completedByTaskanaAdapter");
    }

    private ReferencedTask getReferencedTask(final DelegateTask delegateTask) {

        final ReferencedTask referencedTask = new ReferencedTask();

        referencedTask.setId(delegateTask.getId());
        referencedTask.setCreated(this.formatDate(delegateTask.getCreateTime()));
        referencedTask.setPriority(String.valueOf(delegateTask.getPriority()));
        referencedTask.setName(delegateTask.getName());
        referencedTask.setAssignee(delegateTask.getAssignee());
        referencedTask.setPlanned(this.formatDate(delegateTask.getFollowUpDate()));
        referencedTask.setDue(this.formatDate(delegateTask.getDueDate()));
        referencedTask.setDescription(delegateTask.getDescription());
        referencedTask.setOwner(delegateTask.getOwner());
        referencedTask.setTaskDefinitionKey(delegateTask.getTaskDefinitionKey());
        referencedTask.setBusinessProcessId(delegateTask.getProcessInstanceId());
        referencedTask.setClassificationKey(
                this.getUserTaskExtensionProperty(delegateTask, "taskana.classification-key"));
        referencedTask.setDomain(this.getDomainVariable(delegateTask));
        referencedTask.setWorkbasketKey(this.getWorkbasketKey(delegateTask));
        referencedTask.setVariables(this.getProcessVariables(delegateTask));

        return referencedTask;
    }

    private String getDomainVariable(final DelegateTask delegateTask) {
        final String taskDomain = this.getUserTaskExtensionProperty(delegateTask, "taskana.domain");
        if (taskDomain != null) {
            return taskDomain;
        }
        return this.getProcessModelExtensionProperty(delegateTask, "taskana.domain");
    }

    private String getProcessVariables(final DelegateTask delegateTask) {

        final StringBuilder variablesBuilder = new StringBuilder();
        final List<String> variableNames;

        // Get Task Variables
        final String taskVariablesConcatenated =
                this.getUserTaskExtensionProperty(delegateTask, "taskana-attributes");

        if (taskVariablesConcatenated != null) {
            variableNames = this.splitVariableNamesString(taskVariablesConcatenated);

        } else {
            final String processVariablesConcatenated =
                    this.getProcessModelExtensionProperty(delegateTask, "taskana-attributes");
            if (processVariablesConcatenated != null) {
                variableNames = this.splitVariableNamesString(processVariablesConcatenated);
            } else {
                return "{}";
            }
        }

        variableNames.forEach(
                nameOfVariableToAdd ->
                        this.addToVariablesBuilder(
                                delegateTask, this.objectMapper, variablesBuilder, nameOfVariableToAdd));

        if (variablesBuilder.length() > 0) {
            variablesBuilder.deleteCharAt(variablesBuilder.length() - 1).append("}");
            variablesBuilder.insert(0, "{");
        } else {
            return "{}";
        }
        return variablesBuilder.toString();
    }

    private void addToVariablesBuilder(
            final DelegateTask delegateTask,
            final ObjectMapper objectMapper,
            final StringBuilder processVariablesBuilder,
            final String nameOfprocessVariableToAdd) {

        final TypedValue processVariable = delegateTask.getVariableTyped(nameOfprocessVariableToAdd);

        if (processVariable != null) {

            try {

                final VariableValueDto variableValueDto =
                        this.determineProcessVariableTypeAndCreateVariableValueDto(processVariable, objectMapper);

                final String variableValueDtoJson = objectMapper.writeValueAsString(variableValueDto);

                processVariablesBuilder
                        .append("\"")
                        .append(nameOfprocessVariableToAdd)
                        .append("\":")
                        .append(variableValueDtoJson)
                        .append(",");

            } catch (final JsonProcessingException ex) {
                throw new RuntimeException(
                        "Exception while trying to serialize process variables to JSON", ex);
            }
        }
    }

    private VariableValueDto determineProcessVariableTypeAndCreateVariableValueDto(
            final TypedValue processVariable, final ObjectMapper objectMapper) throws JsonProcessingException {

        final VariableValueDto variableValueDto = new VariableValueDto();

        if (processVariable.getType().isPrimitiveValueType()) {

            variableValueDto.setType(processVariable.getType().getName());
            variableValueDto.setValue(processVariable.getValue());

        } else {

            variableValueDto.setType(processVariable.getType().getName());

            final String processVariableJsonString =
                    objectMapper.writeValueAsString(processVariable.getValue());
            variableValueDto.setValue(processVariableJsonString);

            final Map<String, Object> valueInfo = new HashMap<>();
            valueInfo.put("serializationDataFormat", "application/json");
            valueInfo.put("objectTypeName", processVariable.getValue().getClass());
            variableValueDto.setValueInfo(valueInfo);
        }

        return variableValueDto;
    }

    private List<String> splitVariableNamesString(final String variableNamesConcatenated) {
        return Arrays.asList(variableNamesConcatenated.trim().split("\\s*,\\s*"));
    }

    private String getWorkbasketKey(final DelegateTask delegateTask) {
        String workbasketKey = null;
        try {
            final Object workbasketKeyObj = delegateTask.getVariable("taskana.workbasket-key");
            if (workbasketKeyObj instanceof String) {
                workbasketKey = (String) workbasketKeyObj;
            }
        } catch (final Exception e) {
            log.warn(
                    "Caught exception while trying to retrieve taskana.workbasket-key "
                            + "for task {} in ProcessDefinition {}",
                    delegateTask.getName(),
                    delegateTask.getProcessDefinitionId(),
                    e);
        }
        return workbasketKey;
    }

    private String getProcessModelExtensionProperty(final DelegateTask delegateTask, final String propertyKey) {

        String propertyValue = null;

        final BpmnModelInstance model = delegateTask.getExecution().getBpmnModelInstance();

        final List<CamundaProperty> processModelExtensionProperties =
                model.getModelElementsByType(CamundaProperty.class).stream()
                        .filter(camundaProperty -> camundaProperty.getCamundaName() != null)
                        .filter(camundaProperty -> camundaProperty.getCamundaName().equals(propertyKey))
                        .collect(Collectors.toList());

        if (processModelExtensionProperties.isEmpty()) {
            return propertyValue;
        } else {
            propertyValue = processModelExtensionProperties.get(0).getCamundaValue();
        }

        return propertyValue;
    }

    private String getUserTaskExtensionProperty(final DelegateTask delegateTask, final String propertyKey) {

        String propertyValue = null;

        final ExtensionElements extensionElements =
                delegateTask.getExecution().getBpmnModelElementInstance().getExtensionElements();

        if (extensionElements == null) {
            return propertyValue;
        } else {
            final CamundaProperties camundaProperties =
                    extensionElements.getElementsQuery().filterByType(CamundaProperties.class).singleResult();

            final List<CamundaProperty> userTaskExtensionProperties =
                    camundaProperties.getCamundaProperties().stream()
                            .filter(camundaProperty -> camundaProperty.getCamundaName() != null)
                            .filter(camundaProperty -> camundaProperty.getCamundaName().equals(propertyKey))
                            .collect(Collectors.toList());

            if (userTaskExtensionProperties.isEmpty()) {
                return propertyValue;
            } else {
                propertyValue = userTaskExtensionProperties.get(0).getCamundaValue();
            }
        }

        return propertyValue;
    }

    private String formatDate(final Date date) {
        if (date == null) {
            return null;
        } else {
            return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                    .withZone(ZoneId.systemDefault())
                    .format(date.toInstant());
        }
    }
}
