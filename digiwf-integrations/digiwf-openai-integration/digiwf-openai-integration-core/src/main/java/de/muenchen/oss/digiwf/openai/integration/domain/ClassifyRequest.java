package de.muenchen.oss.digiwf.openai.integration.domain;

import lombok.Value;

@Value
public class ClassifyRequest {

    private String json;
    private String options;
}
