package io.muenchendigital.digiwf.connector.bpmnerror.api;

/**
 * Service to create bpmn errors in digiwf.
 */
public interface BpmnErrorService {

    /**
     * Create a bpmn error
     *
     * @param bpmnError error parameters
     */
    void createBpmnError(BpmnError bpmnError);

}
