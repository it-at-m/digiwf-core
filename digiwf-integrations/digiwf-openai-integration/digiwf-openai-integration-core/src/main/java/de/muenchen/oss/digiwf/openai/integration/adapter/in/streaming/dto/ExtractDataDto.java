package de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto;

import lombok.Value;

@Value
public class ExtractDataDto {

    private String json;
    private String fields;
}
