package de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto;

import lombok.Value;

@Value
public class TranslateDto {

    private String text;
    private String language;
}
