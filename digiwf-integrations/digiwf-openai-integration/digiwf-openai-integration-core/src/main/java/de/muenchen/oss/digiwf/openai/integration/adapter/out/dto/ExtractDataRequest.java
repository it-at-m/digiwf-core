package de.muenchen.oss.digiwf.openai.integration.adapter.out.dto;

import lombok.Value;

@Value
public class ExtractDataRequest {

    private String json;
    private String fields;
}
