package de.muenchen.oss.digiwf.alw.integration.adapter.in.streaming;

/**
 * This enumeration defines the BPMN error codes that can be sent by this integration.
 */
enum AlwErrorCodes {

    /**
     * Indicates that the responsibility could not be found, either because it was not returned by ALW or the returned one does not match any known
     * responsibility.
     */
    RESPONSIBILITY_NOT_FOUND,

    /**
     * Indicates that the requested AZR-Number was not valid. It must consist of 12 digits.
     */
    VALIDATION_ERROR_CODE,

    /**
     * Indicates that ALW responds with an unexpected error code.
     */
    UNEXPECTED_ERROR
}