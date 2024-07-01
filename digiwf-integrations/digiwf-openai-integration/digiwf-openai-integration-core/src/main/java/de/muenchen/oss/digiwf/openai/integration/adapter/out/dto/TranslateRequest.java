package de.muenchen.oss.digiwf.openai.integration.adapter.out.dto;

import lombok.Value;

@Value
public class TranslateRequest {

    private String text;
    private String language;
}
