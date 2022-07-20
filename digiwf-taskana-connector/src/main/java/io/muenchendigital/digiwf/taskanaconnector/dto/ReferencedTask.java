package io.muenchendigital.digiwf.taskanaconnector.dto;

import java.util.Objects;

/** POJO that represents a task in the external system. */
public class ReferencedTask {

  private String id;
  private String outboxEventId;
  private String outboxEventType;
  private String name;
  private String assignee;
  private String created;
  private String planned;
  private String due;
  private String description;
  private String owner;
  private String priority;
  private String suspended;
  private String systemUrl;
  private String taskDefinitionKey;
  private String businessProcessId;
  private String variables;
  private String taskState;
  // extension properties
  private String domain;
  private String classificationKey;
  private String workbasketKey;

  public String getBusinessProcessId() {
    return businessProcessId;
  }

  public void setBusinessProcessId(String businessProcessId) {
    this.businessProcessId = businessProcessId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOutboxEventId() {
    return outboxEventId;
  }

  public void setOutboxEventId(String outboxEventId) {
    this.outboxEventId = outboxEventId;
  }

  public String getOutboxEventType() {
    return outboxEventType;
  }

  public void setOutboxEventType(String outboxEventType) {
    this.outboxEventType = outboxEventType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public String getPlanned() {
    return planned;
  }

  public void setPlanned(String planned) {
    this.planned = planned;
  }

  public String getDue() {
    return due;
  }

  public void setDue(String due) {
    this.due = due;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public String getSuspended() {
    return suspended;
  }

  public void setSuspended(String suspended) {
    this.suspended = suspended;
  }

  public String getsystemUrl() {
    return systemUrl;
  }

  public void setsystemUrl(String systemUrl) {
    this.systemUrl = systemUrl;
  }

  public String getTaskDefinitionKey() {
    return taskDefinitionKey;
  }

  public void setTaskDefinitionKey(String taskDefinitionKey) {
    this.taskDefinitionKey = taskDefinitionKey;
  }

  public String getTaskState() {
    return taskState;
  }

  public void setTaskState(String taskState) {
    this.taskState = taskState;
  }

  public String getVariables() {
    return variables;
  }

  public void setVariables(String variables) {
    this.variables = variables;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getClassificationKey() {
    return classificationKey;
  }

  public void setClassificationKey(String classificationKey) {
    this.classificationKey = classificationKey;
  }

  public String getWorkbasketKey() {
    return workbasketKey;
  }

  public void setWorkbasketKey(String workbasketKey) {
    this.workbasketKey = workbasketKey;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        outboxEventId,
        outboxEventType,
        name,
        assignee,
        created,
        due,
        description,
        owner,
        priority,
        suspended,
        systemUrl,
        taskDefinitionKey,
        businessProcessId,
        variables,
        taskState,
        domain,
        classificationKey,
        workbasketKey);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ReferencedTask other = (ReferencedTask) obj;
    return Objects.equals(id, other.id)
        && Objects.equals(outboxEventId, other.outboxEventId)
        && Objects.equals(outboxEventType, other.outboxEventType)
        && Objects.equals(name, other.name)
        && Objects.equals(assignee, other.assignee)
        && Objects.equals(created, other.created)
        && Objects.equals(due, other.due)
        && Objects.equals(description, other.description)
        && Objects.equals(owner, other.owner)
        && Objects.equals(priority, other.priority)
        && Objects.equals(suspended, other.suspended)
        && Objects.equals(systemUrl, other.systemUrl)
        && Objects.equals(taskDefinitionKey, other.taskDefinitionKey)
        && Objects.equals(businessProcessId, other.businessProcessId)
        && Objects.equals(variables, other.variables)
        && Objects.equals(taskState, other.taskState)
        && Objects.equals(domain, other.domain)
        && Objects.equals(classificationKey, other.classificationKey)
        && Objects.equals(workbasketKey, other.workbasketKey);
  }

  @Override
  public String toString() {
    return "ReferencedTask [id="
        + id
        + ", outboxEventId="
        + outboxEventId
        + ", outboxEventType="
        + outboxEventType
        + ", name="
        + name
        + ", assignee="
        + assignee
        + ", created="
        + created
        + ", due="
        + due
        + ", description="
        + description
        + ", owner="
        + owner
        + ", priority="
        + priority
        + ", suspended="
        + suspended
        + ", systemUrl="
        + systemUrl
        + ", taskDefinitionKey="
        + taskDefinitionKey
        + ", businessProcessId="
        + businessProcessId
        + ", variables="
        + variables
        + ", taskState="
        + taskState
        + ", domain="
        + domain
        + ", classificationKey="
        + classificationKey
        + ", workbasketKey="
        + workbasketKey
        + "]";
  }
}
