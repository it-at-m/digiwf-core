package io.muenchendigital.digiwf.taskanaconnector.parselistener;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.muenchendigital.digiwf.taskanaconnector.config.StarterPluginProperties;
import io.muenchendigital.digiwf.taskanaconnector.exceptions.SystemException;

/**
 * Camunda engine plugin responsible for adding the TaskanaParseListener to the
 * ProcessEngineConfguration.
 */
public class TaskanaParseListenerProcessEnginePlugin extends AbstractProcessEnginePlugin {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(TaskanaParseListenerProcessEnginePlugin.class);
  private StarterPluginProperties pluginProperties;

  public TaskanaParseListenerProcessEnginePlugin(StarterPluginProperties pluginProperties) {
    LOGGER.info("TaskanaParseListenerProcessEnginePlugin created");
    this.pluginProperties = pluginProperties;
  }

  @Override
  public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
    initParseListeners(processEngineConfiguration);
  }

  private void initParseListeners(ProcessEngineConfigurationImpl processEngineConfiguration) {

    try {

      List<BpmnParseListener> preParseListeners =
          processEngineConfiguration.getCustomPreBPMNParseListeners();

      if (preParseListeners == null) {
        preParseListeners = new ArrayList<>();
        processEngineConfiguration.setCustomPreBPMNParseListeners(preParseListeners);
      }
      preParseListeners.add(new TaskanaParseListener(this.pluginProperties));

      LOGGER.debug("DigiWfTaskanaParseListener registered successfully");

    } catch (Exception e) {

      LOGGER.warn("Caught exception while trying to register DigiWfTaskanaParseListener", e);

      throw new SystemException(
          "An error occured while trying to register the DigiWfTaskanaParseListener."
              + " Aborting the boot of camunda.");
    }
  }  
}
