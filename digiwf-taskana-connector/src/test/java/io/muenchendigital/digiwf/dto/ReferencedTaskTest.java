package io.muenchendigital.digiwf.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.muenchendigital.digiwf.taskanaconnector.dto.ReferencedTask;

/** Acceptance test for ReferencedTask setters and getters. */
public class ReferencedTaskTest {

  private final String theValue = "blablabla";
  private ReferencedTask theTask;

  public ReferencedTaskTest() {
    theTask = new ReferencedTask();
    theTask.setAssignee("1");
    theTask.setBusinessProcessId("2");
    theTask.setClassificationKey("3");
    theTask.setDescription("bla");
    theTask.setCreated("4");
    theTask.setDomain("5");
    theTask.setPlanned("6");
    theTask.setDue("7");
    theTask.setId("8");
    theTask.setName("9");
    theTask.setOutboxEventId("10");
    theTask.setOutboxEventType("11");
    theTask.setOwner("12");
  }

  @Test
  void should_returnBusinessProcessId_when_BusinessProcessIdWasSet() {
    theTask.setBusinessProcessId(theValue);
    assertThat(theValue).isEqualTo(theTask.getBusinessProcessId());
  }

  @Test
  void should_returnId_when_IdWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setId(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getId());
  }

  @Test
  void should_returnOutboxEventId_when_OutboxEventIdWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setOutboxEventId(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getOutboxEventId());
  }

  @Test
  void should_returnOutboxEventType_when_OutboxEventTypeWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setOutboxEventType(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getOutboxEventType());
  }

  @Test
  void should_returnName_when_NameWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setName(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getName());
  }

  @Test
  void should_returnAssignee_when_AssigneeWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setAssignee(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getAssignee());
  }

  @Test
  void should_returnCreated_when_CreatedWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setCreated(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getCreated());
  }

  @Test
  void should_returnFollowUp_when_FollowUpWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setDue(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getDue());
  }

  @Test
  void should_returnDue_when_DueWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setDue(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getDue());
  }

  @Test
  void should_returnDescription_when_DescriptionWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setDescription(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getDescription());
  }

  @Test
  void should_returnOwner_when_OwnerWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setOwner(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getOwner());
  }

  @Test
  void should_returnPriority_when_PriorityWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setPriority(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getPriority());
  }

  @Test
  void should_returnSuspended_when_SuspendedWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setSuspended(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getSuspended());
  }

  @Test
  void should_returnSystemUrl_when_SystemUrlWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setsystemUrl(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getsystemUrl());
  }

  @Test
  void should_returnTaskDefinitionKey_when_TaskDefinitionKeyWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setTaskDefinitionKey(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getTaskDefinitionKey());
  }

  @Test
  void should_returnVariables_when_VariablesWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setVariables(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getVariables());
  }

  @Test
  void should_returnDomain_when_DomainWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setDomain(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getDomain());
  }

  @Test
  void should_returnClassificationKey_when_ClassificationKeyWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setClassificationKey(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getClassificationKey());
  }

  @Test
  void should_returnWorkbasketKey_when_WorkbasketKeyWasSet() {
    ReferencedTask referencedTask = new ReferencedTask();
    referencedTask.setWorkbasketKey(theValue);
    assertThat(theValue).isEqualTo(referencedTask.getWorkbasketKey());
  }

  @Test
  void should_returnHashCode_when_askedToDoSo() {
    assertThat(theTask.hashCode()).isNotNull();
  }

  @Test
  void should_returnString_when_toStringIsCalled() {
    assertThat(theTask.toString()).isNotNull();
  }

  @Test
  void should_checkEquality_when_equalsIsCalled() {
    ReferencedTask refTask2 = theTask;
    refTask2.setWorkbasketKey("nochnkey");
    assertThat(refTask2).isEqualTo(theTask);
    refTask2 = theTask;
    assertThat(theTask).isEqualTo(refTask2);
    assertThat(theTask).isNotEqualTo("aString");
    assertThat(theTask).isNotEqualTo(null);
    theTask = new ReferencedTask();
    theTask.setAssignee("1");
    theTask.setBusinessProcessId("2");
    theTask.setClassificationKey("3");
    theTask.setDescription("bla");
    theTask.setCreated("4");
    theTask.setDomain("5");
    theTask.setPlanned("6");
    theTask.setDue("7");
    theTask.setId("8");
    theTask.setName("9");
    theTask.setOutboxEventId("10");
    theTask.setOutboxEventType("11");
    theTask.setOwner("12");
    theTask.setWorkbasketKey("13");

    refTask2 = new ReferencedTask();
    refTask2.setAssignee("1");
    refTask2.setBusinessProcessId("2");
    refTask2.setClassificationKey("3");
    refTask2.setDescription("bla");
    refTask2.setCreated("4");
    refTask2.setDomain("5");
    refTask2.setPlanned("6");
    refTask2.setDue("7");
    refTask2.setId("8");
    refTask2.setName("9");
    refTask2.setOutboxEventId("10");
    refTask2.setOutboxEventType("11");
    refTask2.setOwner("12");
    refTask2.setWorkbasketKey("13");

    assertThat(refTask2).isEqualTo(theTask);
    refTask2.setWorkbasketKey("anotherOne");
    assertThat(refTask2).isNotEqualTo(theTask);
  }
}
