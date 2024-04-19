package de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto;

import lombok.Value;

@Value
public class GenerateMailDto {

    private String json;
    private String language;
    private String template;
}
