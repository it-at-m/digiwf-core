package de.muenchen.oss.digiwf.openai.integration.adapter.out.dto;

import lombok.Value;

@Value
public class ClassifyRequest {

    private String json;
    private String options;
}
