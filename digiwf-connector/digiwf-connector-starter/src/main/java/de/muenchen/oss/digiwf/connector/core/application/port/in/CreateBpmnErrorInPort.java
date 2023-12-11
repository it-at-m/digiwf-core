package de.muenchen.oss.digiwf.connector.core.application.port.in;


import de.muenchen.oss.digiwf.connector.core.domain.BpmnError;

public interface CreateBpmnErrorInPort {


    /**
     * Create a bpmn error
     *
     * @param bpmnError error parameters
     */
    void createBpmnError(BpmnError bpmnError);
}
