package de.muenchen.oss.digiwf.engine.incidents;

import org.camunda.bpm.engine.impl.incident.IncidentContext;
import org.camunda.bpm.engine.impl.persistence.entity.IncidentEntity;

public class IncidentNotifierHandlerMock extends IncidentNotifierHandler{
    @Override
    IncidentEntity superHandleIncident(final IncidentContext context, final String message) {
        return null;
    }
}
