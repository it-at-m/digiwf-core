package de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto;

import lombok.Value;

@Value
public class SummarizeDto {

    private String text;
    private int length;
}
