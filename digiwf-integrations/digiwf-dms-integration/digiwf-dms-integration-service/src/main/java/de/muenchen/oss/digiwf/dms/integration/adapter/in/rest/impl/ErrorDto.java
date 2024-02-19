package de.muenchen.oss.digiwf.dms.integration.adapter.in.rest.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {

    private final String errorCode;
    private final String errorMessage;

}
