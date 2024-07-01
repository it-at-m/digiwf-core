package de.muenchen.oss.digiwf.openai.integration.domain;

import lombok.Value;

@Value
public class TranslateRequest {

    private String text;
    private String language;
}
