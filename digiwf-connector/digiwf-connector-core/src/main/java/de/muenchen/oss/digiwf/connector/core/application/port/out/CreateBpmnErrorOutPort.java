package de.muenchen.oss.digiwf.connector.core.application.port.out;


import de.muenchen.oss.digiwf.connector.core.domain.BpmnError;

public interface CreateBpmnErrorOutPort {


    /**
     * Create a bpmn error
     *
     * @param bpmnError error parameters
     */
    void createBpmnError(BpmnError bpmnError);
}
