package de.muenchen.oss.digiwf.openai.integration.adapter.out.dto;

import lombok.Value;

@Value
public class GenerateMailRequest {

    private String json;
    private String language;
    private String template;
}
