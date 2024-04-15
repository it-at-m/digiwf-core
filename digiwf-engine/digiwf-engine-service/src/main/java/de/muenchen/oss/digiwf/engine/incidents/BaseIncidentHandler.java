package de.muenchen.oss.digiwf.engine.incidents;

import org.camunda.bpm.engine.impl.incident.DefaultIncidentHandler;

public class BaseIncidentHandler extends DefaultIncidentHandler {

    public BaseIncidentHandler() {
        super("failedJob");
    }
}
