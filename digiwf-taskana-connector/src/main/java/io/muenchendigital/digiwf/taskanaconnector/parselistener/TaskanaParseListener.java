package io.muenchendigital.digiwf.taskanaconnector.parselistener;

import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.camunda.bpm.engine.impl.util.xml.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.muenchendigital.digiwf.taskanaconnector.config.StarterPluginProperties;
import io.muenchendigital.digiwf.taskanaconnector.tasklistener.TaskanaTaskListener;

/** This class is responsible for adding the TaskanaTaskListener to all user tasks. */
public class TaskanaParseListener extends AbstractBpmnParseListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(TaskanaParseListener.class);

  private boolean gotActivated = false;
  private StarterPluginProperties pluginProperties;

  public TaskanaParseListener(StarterPluginProperties pluginProperties) {
    LOGGER.info("TaskanaParseListener created");
    this.pluginProperties = pluginProperties;
  }

  @Override
  public void parseUserTask(Element userTaskElement, ScopeImpl scope, ActivityImpl activity) {

    if (!gotActivated) {
      gotActivated = true;
      LOGGER.info("TaskanaParseListener activated successfully");
    }

    ActivityBehavior behavior = activity.getActivityBehavior();
    if (behavior instanceof UserTaskActivityBehavior) {

      TaskDefinition userTask = ((UserTaskActivityBehavior) behavior).getTaskDefinition();

      userTask.addTaskListener(TaskListener.EVENTNAME_CREATE, TaskanaTaskListener.getInstance(pluginProperties.getOutgoingTopic()));
      userTask.addTaskListener(TaskListener.EVENTNAME_COMPLETE, TaskanaTaskListener.getInstance(pluginProperties.getOutgoingTopic()));
      userTask.addTaskListener(TaskListener.EVENTNAME_DELETE, TaskanaTaskListener.getInstance(pluginProperties.getOutgoingTopic()));
    }
  }
}
