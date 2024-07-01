package de.muenchen.oss.digiwf.openai.integration.domain;

import lombok.Value;

@Value
public class GenerateMailRequest {

    private String json;
    private String language;
    private String template;
}
