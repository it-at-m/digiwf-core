package io.muenchendigital.digiwf.taskanaconnector.tasklistener;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.muenchendigital.digiwf.taskanaconnector.config.StarterPluginProperties;
import io.muenchendigital.digiwf.taskanaconnector.dto.ReferencedTask;
import io.muenchendigital.digiwf.taskanaconnector.dto.VariableValueDto;
import io.muenchendigital.digiwf.taskanaconnector.exceptions.SystemException;
import io.muenchendigital.digiwf.taskanaconnector.mapper.JacksonConfigurator;
import io.muenchendigital.digiwf.taskanaconnector.service.KafkaTaskProducer;

/**
 * This class is responsible for dealing with events within the lifecycle of a camunda user task.
 */
public class TaskanaTaskListener implements TaskListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(TaskanaTaskListener.class);
  private static final String TASK_STATE_COMPLETED = "COMPLETED";
  private static final String TASK_STATE_CANCELLED = "CANCELLED";
  private static final String TASK_STATE_TERMINATED = "TERMINATED";
  private static TaskanaTaskListener instance = null;

  private final ObjectMapper objectMapper = JacksonConfigurator.createAndConfigureObjectMapper();
  private boolean gotActivated = false;

  @Autowired
  private static StarterPluginProperties pluginProperties;
  private static KafkaTaskProducer kafkaProducer;


  public static TaskanaTaskListener getInstance(String outgoingTopic) {
    
    kafkaProducer = new KafkaTaskProducer(outgoingTopic);

    if (instance == null) {
      instance = new TaskanaTaskListener();
    }
    return instance;
  }

  @Override
  public void notify(DelegateTask delegateTask) {

    try {
      String engineName = Context.getProcessEngineConfiguration().getProcessEngineName();

      if (!gotActivated) {
        gotActivated = true;
        if (LOGGER.isInfoEnabled()) {
          LOGGER.info(
              String.format(
                  "TaskanaTaskListener activated successfully, connected to %s",
                  engineName));
        }
      }

      if (engineName.length() > 128) {
        throw new SystemException(
            "The configured process engine name "
                + engineName
                + " is too long. "
                + "Length must not exceed 128 characters.");
      }

      switch (delegateTask.getEventName()) {
        case "create":
          insertCreateEventIntoOutbox(delegateTask);
          break;
        case "complete":
        case "delete":
          insertCompleteOrDeleteEventIntoOutbox(delegateTask);
          break;
        default:
          break;
      }

    } catch (Exception e) {
      LOGGER.error("Unexpected Exception while trying to process a delegate task", e);
      throw new SystemException("Unexpected Exception while trying to process a delegate task", e);
    }
  }

  private void insertCreateEventIntoOutbox(DelegateTask delegateTask)
      throws Exception {

    String referencedTaskJson = getReferencedTaskJson(delegateTask);
    prepareAndExecuteStatement(delegateTask, referencedTaskJson);

  }

  private void insertCompleteOrDeleteEventIntoOutbox(
      DelegateTask delegateTask) throws Exception {

    if (delegateTask.getEventName().equals("complete")
        && taskWasCompletedByTaskanaAdapter(delegateTask)) {
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

    String payload =
        String.format("{\"id\":\"%s\",\"taskState\":\"%s\"}", delegateTask.getId(), taskState);

    prepareAndExecuteStatement(delegateTask, payload);
    
  }

  private boolean taskWasCompletedByTaskanaAdapter(DelegateTask delegateTask) {
    return delegateTask.getVariableNamesLocal().contains("completedByTaskanaAdapter");
  }

  private void prepareAndExecuteStatement(
      DelegateTask delegateTask, String payloadJson) throws Exception {

      //Timestamp eventCreationTimestamp = Timestamp.from(Instant.now());

      LOGGER.info("send kafka msg for this payload: " + payloadJson);
      kafkaProducer.sendMessage(payloadJson);
  }

  private String getReferencedTaskJson(DelegateTask delegateTask) throws JsonProcessingException {

    ReferencedTask referencedTask = new ReferencedTask();

    referencedTask.setId(delegateTask.getId());
    referencedTask.setCreated(formatDate(delegateTask.getCreateTime()));
    referencedTask.setPriority(String.valueOf(delegateTask.getPriority()));
    referencedTask.setName(delegateTask.getName());
    referencedTask.setAssignee(delegateTask.getAssignee());
    referencedTask.setPlanned(formatDate(delegateTask.getFollowUpDate()));
    referencedTask.setDue(formatDate(delegateTask.getDueDate()));
    referencedTask.setDescription(delegateTask.getDescription());
    referencedTask.setOwner(delegateTask.getOwner());
    referencedTask.setTaskDefinitionKey(delegateTask.getTaskDefinitionKey());
    referencedTask.setBusinessProcessId(delegateTask.getProcessInstanceId());
    referencedTask.setClassificationKey(
        getUserTaskExtensionProperty(delegateTask, "taskana.classification-key"));
    referencedTask.setDomain(getDomainVariable(delegateTask));
    referencedTask.setWorkbasketKey(getWorkbasketKey(delegateTask));
    referencedTask.setVariables(getProcessVariables(delegateTask));

    String referencedTaskJson = objectMapper.writeValueAsString(referencedTask);
    LOGGER.debug("Exit from getReferencedTaskJson. Returning {}.", referencedTaskJson);
    return referencedTaskJson;
  }

  private String getDomainVariable(DelegateTask delegateTask) {
    String taskDomain = getUserTaskExtensionProperty(delegateTask, "taskana.domain");
    if (taskDomain != null) {
      return taskDomain;
    }
    return getProcessModelExtensionProperty(delegateTask, "taskana.domain");
  }

  private String getProcessVariables(DelegateTask delegateTask) {

    StringBuilder variablesBuilder = new StringBuilder();
    List<String> variableNames;

    // Get Task Variables
    String taskVariablesConcatenated =
        getUserTaskExtensionProperty(delegateTask, "taskana-attributes");

    if (taskVariablesConcatenated != null) {
      variableNames = splitVariableNamesString(taskVariablesConcatenated);

    } else {
      String processVariablesConcatenated =
          getProcessModelExtensionProperty(delegateTask, "taskana-attributes");
      if (processVariablesConcatenated != null) {
        variableNames = splitVariableNamesString(processVariablesConcatenated);
      } else {
        return "{}";
      }
    }

    variableNames.forEach(
        nameOfVariableToAdd ->
            addToVariablesBuilder(
                delegateTask, objectMapper, variablesBuilder, nameOfVariableToAdd));

    if (variablesBuilder.length() > 0) {
      variablesBuilder.deleteCharAt(variablesBuilder.length() - 1).append("}");
      variablesBuilder.insert(0, "{");
    } else {
      return "{}";
    }
    return variablesBuilder.toString();
  }

  private void addToVariablesBuilder(
      DelegateTask delegateTask,
      ObjectMapper objectMapper,
      StringBuilder processVariablesBuilder,
      String nameOfprocessVariableToAdd) {

    TypedValue processVariable = delegateTask.getVariableTyped(nameOfprocessVariableToAdd);

    if (processVariable != null) {

      try {

        VariableValueDto variableValueDto =
            determineProcessVariableTypeAndCreateVariableValueDto(processVariable, objectMapper);

        String variableValueDtoJson = objectMapper.writeValueAsString(variableValueDto);

        processVariablesBuilder
            .append("\"")
            .append(nameOfprocessVariableToAdd)
            .append("\":")
            .append(variableValueDtoJson)
            .append(",");

      } catch (JsonProcessingException ex) {

        if (pluginProperties.getExceptionOrLogProcessVariableIssues()) {

          LOGGER.error("Caught exception while trying to serialize process variables to JSON", ex);

        } else {
          throw new SystemException(
              "Exception while trying to serialize process variables to JSON", ex);
        }
      }
    }
  }

  private VariableValueDto determineProcessVariableTypeAndCreateVariableValueDto(
      TypedValue processVariable, ObjectMapper objectMapper) throws JsonProcessingException {

    VariableValueDto variableValueDto = new VariableValueDto();

    if (processVariable.getType().isPrimitiveValueType()) {

      variableValueDto.setType(processVariable.getType().getName());
      variableValueDto.setValue(processVariable.getValue());

    } else {

      variableValueDto.setType(processVariable.getType().getName());

      String processVariableJsonString =
          objectMapper.writeValueAsString(processVariable.getValue());
      variableValueDto.setValue(processVariableJsonString);

      Map<String, Object> valueInfo = new HashMap<>();
      valueInfo.put("serializationDataFormat", "application/json");
      valueInfo.put("objectTypeName", processVariable.getValue().getClass());
      variableValueDto.setValueInfo(valueInfo);
    }

    return variableValueDto;
  }

  private List<String> splitVariableNamesString(String variableNamesConcatenated) {
    return Arrays.asList(variableNamesConcatenated.trim().split("\\s*,\\s*"));
  }

  private String getWorkbasketKey(DelegateTask delegateTask) {
    String workbasketKey = null;
    try {
      Object workbasketKeyObj = delegateTask.getVariable("taskana.workbasket-key");
      if (workbasketKeyObj instanceof String) {
        workbasketKey = (String) workbasketKeyObj;
      }
    } catch (Exception e) {
      LOGGER.warn(
          "Caught exception while trying to retrieve taskana.workbasket-key "
              + "for task {} in ProcessDefinition {}",
          delegateTask.getName(),
          delegateTask.getProcessDefinitionId(),
          e);
    }
    return workbasketKey;
  }

  private String getProcessModelExtensionProperty(DelegateTask delegateTask, String propertyKey) {

    String propertyValue = null;

    BpmnModelInstance model = delegateTask.getExecution().getBpmnModelInstance();

    List<CamundaProperty> processModelExtensionProperties =
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

  private String getUserTaskExtensionProperty(DelegateTask delegateTask, String propertyKey) {

    String propertyValue = null;

    ExtensionElements extensionElements =
        delegateTask.getExecution().getBpmnModelElementInstance().getExtensionElements();

    if (extensionElements == null) {
      return propertyValue;
    } else {
      CamundaProperties camundaProperties =
          extensionElements.getElementsQuery().filterByType(CamundaProperties.class).singleResult();

      List<CamundaProperty> userTaskExtensionProperties =
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

  private String formatDate(Date date) {
    if (date == null) {
      return null;
    } else {
      return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
          .withZone(ZoneId.systemDefault())
          .format(date.toInstant());
    }
  }
}
