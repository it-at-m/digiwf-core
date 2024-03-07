package de.muenchen.oss.digiwf.alw.integration.adapter.in.streaming;

/**
 * Bpmn Error codes that this integration could send.
 */
enum AlwErrorCodes {

    /**
     * Responsibility could not be found either because it was not returned by ALW or the returned one does not match any known responsibility.
     */
    RESPONSIBILITY_NOT_FOUND,

    /**
     * Requested AZR-Number was not valid. It has to consist of 12 digits.
     */
    VALIDATION_ERROR_CODE,

    /**
     * ALW responds with an unexpected error code.
     */
    OTHER
}
