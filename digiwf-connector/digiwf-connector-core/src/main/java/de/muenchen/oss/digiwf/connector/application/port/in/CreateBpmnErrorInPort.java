package de.muenchen.oss.digiwf.connector.application.port.in;


import de.muenchen.oss.digiwf.connector.domain.BpmnError;

public interface CreateBpmnErrorInPort {


    /**
     * Create a bpmn error
     *
     * @param bpmnError error parameters
     */
    void createBpmnError(BpmnError bpmnError);
}
