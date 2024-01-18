package de.muenchen.oss.digiwf.connector.core.application.port.in;


import de.muenchen.oss.digiwf.connector.core.domain.BpmnError;
import jakarta.validation.Valid;

public interface CreateBpmnErrorInPort {


    /**
     * Create a bpmn error
     *
     * @param bpmnError error parameters
     */
    void createBpmnError(@Valid BpmnError bpmnError);
}
